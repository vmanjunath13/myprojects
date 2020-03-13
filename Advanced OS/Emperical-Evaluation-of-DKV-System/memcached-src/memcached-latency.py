import sys
import socket
import random
import string

from multiprocessing import Process
from datetime import datetime as dt
from pymemcache.client.hash import HashClient


BYTES = 73
PORT = 11155
NUM_OF_OPS = 100000
MSG_MAX_SIZE = 16
MEMCACHED_PORT = 11211

def calc_performance_metrics(start_dt, end_dt):
	delta = end_dt - start_dt
	return delta.seconds + delta.microseconds / 1000000

def run_exp_insert(cluster, random_str_arr):
	length = len(random_str_arr)

	total_latency = 0
	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r insert operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")
		pair = random_str_arr[i]
		start = dt.now()
		cluster.set(pair[0:10], pair[10:])
		end = dt.now()
		total_latency += calc_performance_metrics(start, end)

	print("\r insert operations >> 100% completed")
	print("Insert Latency " + str(total_latency/NUM_OF_OPS) + " secs")


def run_exp_lookup(cluster, random_str_arr):
	length = len(random_str_arr)

	total_latency = 0
	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r lookup operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")

		pair = random_str_arr[i]
		start = dt.now()
		val = cluster.get(pair[0:10])
		end = dt.now()
		total_latency += calc_performance_metrics(start, end)

	print("\r lookup operations >> 100% completed")
	print("Lookup Latency " + str(total_latency/NUM_OF_OPS) + " secs")


def run_exp_delete(cluster, random_str_arr):
	length = len(random_str_arr)

	total_latency = 0
	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r delete operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")

		pair = random_str_arr[i]
		start = dt.now()
		cluster.delete(pair[0:10])
		end = dt.now()
		total_latency += calc_performance_metrics(start, end)

	print("\r delete operations >> 100% completed")
	print("Delete Latency " + str(total_latency / NUM_OF_OPS) + " secs")

def prepare_random_string(length, alpha_numeric_str):
	res = ""
	for _ in range(0, length):
		res += random.choice(alpha_numeric_str)
	return res

def run_exp_init(hostname):
	random_str_arr = []
	alpha_numeric_str = string.ascii_lowercase + string.ascii_uppercase + string.digits

	i = 0
	while i < NUM_OF_OPS:
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r random key-value generation >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")

		pair = prepare_random_string(BYTES, alpha_numeric_str)
		if pair not in random_str_arr:
			random_str_arr.append(pair + hostname)
			i += 1

	print("\r random key-value generation >> 100%.")
	return random_str_arr

def run_server(memcached_conf):
	random_str_arr = []

	memcahched_srvs = []
	with open(memcached_conf, "r") as fin:
		for line in fin:
			memcahched_srvs.append([line.replace("\n", ""), MEMCACHED_PORT])

	cluster = HashClient(memcahched_srvs)

	with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
		sock.bind(("0.0.0.0", PORT))
		sock.listen(1)
		conn, addr = sock.accept()

		with conn:
			print("Connected to leader:" + str(addr))
			while True:
				data = conn.recv(MSG_MAX_SIZE)
				conn.sendall(b'OK')

				if not data:
					break

				if data == b'terminate':
					break
				elif data == b'init':
					random_str_arr = run_exp_init(socket.gethostname())
				elif data == b'insert':
					run_exp_insert(cluster, random_str_arr)
				elif data == b'lookup':
					run_exp_lookup(cluster, random_str_arr)
				elif data == b'delete':
					run_exp_delete(cluster, random_str_arr)
				elif data == b'barrier':
					continue
				else:
					print("[FATAL] Unexpected error encountered.")
					break
		conn.close()

def run_one_node_server(memcached_conf):
	random_str_arr = []

	memcahched_srvs = []
	with open(memcached_conf, "r") as fin:
		for line in fin:
			memcahched_srvs.append([line.replace("\n", ""), MEMCACHED_PORT])

	cluster = HashClient(memcahched_srvs)

	random_str_arr = run_exp_init(socket.gethostname())
	run_exp_insert(cluster, random_str_arr)
	run_exp_lookup(cluster, random_str_arr)
	run_exp_delete(cluster, random_str_arr)

def run_cli(conf_file):
	socks = []
	print("Starting the Memcached-Experiment peer as: leader ...completed.")

	print("<Press Enter to connect to the followers>")
	with open(conf_file, "r") as fin:
		for line in fin:
			host = line.replace("\n", "")
			try:
				socks.append(socket.socket(socket.AF_INET, socket.SOCK_STREAM))
			except OSError as msg:
				print(msg)
				continue


			try:
				socks[-1].connect((host, PORT))
			except OSError as msg:
				socks[-1].close()
				print(msg)
				continue

	input("<Press Enter to connect to initialize random key-value pairs>")

	#Initialize experiment data
	for sock in socks:
		sock.sendall(b'init')
		data = sock.recv(MSG_MAX_SIZE)

	for sock in socks:
		sock.sendall(b'barrier')
		data = sock.recv(MSG_MAX_SIZE)

	input("<Press Enter to connect to start Latency experiment>")

	# run insert experiment
	total_delta_sec = 0
	for sock in socks:
		start = dt.now()
		sock.sendall(b'insert')
		data = sock.recv(MSG_MAX_SIZE)
		end = dt.now()
		delta = end - start
		total_delta_sec += delta.seconds + delta.microseconds / 1000000

	for sock in socks:
		sock.sendall(b'barrier')
		data = sock.recv(MSG_MAX_SIZE)

	insert_latency = total_delta_sec/NUM_OF_OPS
	print("Overall Insert Latency:" + str( total_delta_sec / NUM_OF_OPS ) + " seconds")

	# run lookup experiment
	total_delta_sec = 0
	for sock in socks:
		start = dt.now()
		sock.sendall(b'lookup')
		data = sock.recv(MSG_MAX_SIZE)
		end = dt.now()
		delta = end - start
		total_delta_sec += delta.seconds + delta.microseconds / 1000000

	for sock in socks:
		sock.sendall(b'barrier')
		data = sock.recv(MSG_MAX_SIZE)

	lookup_latency = total_delta_sec/NUM_OF_OPS
	print("Overall Lookup Latency:" + str( total_delta_sec / NUM_OF_OPS ) + " seconds")

	# run delete experiment
	start = dt.now()
	for sock in socks:
		start = dt.now()
		sock.sendall(b'delete')
		data = sock.recv(MSG_MAX_SIZE)
		end = dt.now()
		delta = end - start
		total_delta_sec += delta.seconds + delta.microseconds / 1000000

	for sock in socks:
		sock.sendall(b'barrier')
		data = sock.recv(MSG_MAX_SIZE)

	delete_latency = total_delta_sec / NUM_OF_OPS
	print("Overall Delete Latency:" + str( total_delta_sec / NUM_OF_OPS ) + " seconds")

	end = dt.now()

	delta = end - start
	delta_sec = delta.seconds + delta.microseconds / 1000000
	print("Overall elapsed time:" + str(delta_sec) + " seconds")
	overall_latency = (insert_latency + lookup_latency + delete_latency)/3
	print("Overall Latency:" + str(overall_latency) + " seconds ")

	# terminate experiment
	for sock in socks:
		sock.sendall(b'terminate')
		data = sock.recv(MSG_MAX_SIZE)
		sock.close()

if __name__ == "__main__":
	args = sys.argv[1:]

	if len(args) < 2:
		print("Invalid arguments passed!! Please use the command python3 memcached.py <leader/follower> memcached.conf [peer.conf]")
		sys.exit(1)

	role = args[0]
	memcached_conf = args[1]
	peers_conf = ""

	# check validity of arguements
	if role == "leader":
		if len(args) == 2:
			print("Started running on one node")
			proc = Process(target=run_one_node_server, args=(memcached_conf,))
			proc.start()
			proc.join()
			sys.exit(2)
		elif len(args) == 3:
			peers_conf = args[2]
			proc = Process(target=run_server, args=(memcached_conf,))
			proc.start()
			run_cli(peers_conf)
			proc.join()
			sys.exit(2)
	else:
		proc = Process(target=run_server, args=(memcached_conf,))
		proc.start()
		print("Follower: Memcached-Experiment peer started...")
		proc.join()

	print("Experiment completed.....")
	sys.exit(0)