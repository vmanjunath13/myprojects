import sys
import socket
import random
import string

from multiprocessing import Process
from datetime import datetime as dt
import pymongo

BYTES = 82
NUM_OF_OPS = 100

def calc_performance_metrics(start_dt, end_dt):
	delta = end_dt - start_dt
	return delta.seconds + delta.microseconds / 1000000

def run_exp_insert(db, random_str_arr):
	length = len(random_str_arr)

	total_latency = 0
	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r insert operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")
		pair = random_str_arr[i]
		start = dt.now()
		db.MyHashCollection.insert_one({"key": pair[0:10], "val": pair[10:]})
		end = dt.now()
		total_latency += calc_performance_metrics(start, end)

	print("\r insert operations >> 100% completed")
	print("Insert Latency " + str(total_latency/NUM_OF_OPS) + " secs")
	return total_latency/NUM_OF_OPS

def run_exp_lookup(db, random_str_arr):
	length = len(random_str_arr)

	total_latency = 0
	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r lookup operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")
		pair = random_str_arr[i]
		start = dt.now()
		val = db.MyHashCollection.find_one({"key": pair[0:10]})
		end = dt.now()
		total_latency += calc_performance_metrics(start, end)

	print("\r lookup operations >> 100% completed")
	print("Lookup Latency " + str(total_latency/NUM_OF_OPS) + " secs")
	return total_latency/NUM_OF_OPS

def run_exp_delete(db, random_str_arr):
	length = len(random_str_arr)
	start = dt.now()

	total_latency = 0
	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r delete operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")

		pair = random_str_arr[i]
		start = dt.now()
		db.MyHashCollection.delete_one({"key": pair[0:10]})
		end = dt.now()
		total_latency += calc_performance_metrics(start, end)

	print("\r delete operations >> 100% completed")
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

def run_cluster_server(mongo_mongos_conf):
	mongo_conn_string = ""
	with open(mongo_mongos_conf, "r") as fin:
		for line in fin:
			mongo_conn_string += line.replace("\n", "")
			

	client = pymongo.MongoClient(mongo_conn_string)
	db = client.hash_grp1

	random_str_arr = []
	random_str_arr = run_exp_init(socket.gethostname())
	insert_latency = run_exp_insert(db, random_str_arr)
	lookup_latency = run_exp_lookup(db, random_str_arr)
	delete_latency = run_exp_delete(db, random_str_arr)

	overall_latency = (insert_latency + lookup_latency + delete_latency) / 3
	print("Overall latency" + str(overall_latency) + " secs")

if __name__ == "__main__":
	args = sys.argv[1:]

	if len(args) < 1:
		print("Invalid arguements passed!! Please use the command python3 mongo-throuput.py mongos.conf")
		sys.exit(1)

	run_cluster_server(args[0])
	print("Experiment completed.....")