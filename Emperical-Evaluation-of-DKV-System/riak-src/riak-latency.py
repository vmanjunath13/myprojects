# riak is the pip3 module we have used to do experiments.
# $ sudo apt-get install python3-setuptools
# $ pip3 --no-cache-dir install riak

import sys
import socket
import random
import string

from datetime import datetime as dt
from riak import RiakClient

BYTES = 82
NUM_OF_OPS = 10
RIAK_PORT = 8098

def calc_performance_metrics(start_dt, end_dt):
	delta = end_dt - start_dt
	return delta.seconds + delta.microseconds / 1000000

def run_exp_insert(bucket, random_str_arr):
	length = len(random_str_arr)

	total_latency = 0
	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r Insert operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")
		pair = random_str_arr[i]
		start = dt.now()
		obj = bucket.new(pair[0:10], data={"val": pair[10:]})
		obj.store()
		end = dt.now()
		total_latency += calc_performance_metrics(start, end)

	print("\r Insert operations >> 100% completed")
	print("Insert Latency " + str(total_latency/NUM_OF_OPS) + " secs")
	return total_latency/NUM_OF_OPS

def run_exp_lookup(bucket, random_str_arr):
	length = len(random_str_arr)

	total_latency = 0
	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r Lookup operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")
		pair = random_str_arr[i]
		start = dt.now()
		bucket.get(pair[0:10])
		end = dt.now()
		total_latency += calc_performance_metrics(start, end)

	print("\r Lookup operations >> 100% completed")
	print("Lookup Latency " + str(total_latency/NUM_OF_OPS) + " secs")
	return total_latency/NUM_OF_OPS

def run_exp_delete(bucket, random_str_arr):
	length = len(random_str_arr)
	start = dt.now()

	total_latency = 0
	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r Delete operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")

		pair = random_str_arr[i]
		start = dt.now()
		bucket.delete(pair[0:10])
		end = dt.now()
		total_latency += calc_performance_metrics(start, end)

	print("\r Delete operations >> 100% completed")
	print("Delete Latency " + str(total_latency / NUM_OF_OPS) + " secs")
	return total_latency/NUM_OF_OPS

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

def run_cluster_server(riak_conf):
	riak_hosts = []
	with open(riak_conf, "r") as fin:
		for line in fin:
			riak_hosts.append({"host": line.replace("\n", ""), "http_port": RIAK_PORT})

	cluster = RiakClient(nodes=riak_hosts)
	bucket = cluster.bucket("HashedData")

	random_str_arr = []
	random_str_arr = run_exp_init(socket.gethostname())
	insert_latency = run_exp_insert(bucket, random_str_arr)
	lookup_latency = run_exp_lookup(bucket, random_str_arr)
	delete_latency = run_exp_delete(bucket, random_str_arr)

	overall_latency = (insert_latency + lookup_latency + delete_latency) / 3
	print("Overall Latency: " + str(overall_latency) + " secs")

if __name__ == "__main__":
	args = sys.argv[1:]

	if len(args) < 1:
		print("Invalid arguements passed!! Please use the command python3 riak-letency.py riak.conf")
		sys.exit(1)

	run_cluster_server(args[0])
	print("Experiment completed.....")