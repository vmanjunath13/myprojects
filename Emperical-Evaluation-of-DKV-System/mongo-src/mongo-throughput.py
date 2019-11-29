import sys
import socket
import random
import string

from multiprocessing import Process
from datetime import datetime as dt
import pymongo

BYTES = 82
NUM_OF_OPS = 1000

def run_exp_insert(db, random_str_arr):
	length = len(random_str_arr)
	start_dt = dt.now()

	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r insert operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")
		pair = random_str_arr[i]
		db.MyHashCollection.insert_one({"key": pair[0:10], "val": pair[10:]})

	print("\r insert operations >> 100% completed")
	end_dt = dt.now()
	delta = end_dt - start_dt
	delta_sec = delta.seconds + delta.microseconds / 1000000
	print("Insert Operation Throughput " + str( NUM_OF_OPS / delta_sec ) + " OPs/s")

	return NUM_OF_OPS / delta_sec

def run_exp_lookup(db, random_str_arr):
	length = len(random_str_arr)
	start_dt = dt.now()

	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r lookup operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")
		pair = random_str_arr[i]
		val = db.MyHashCollection.find_one({"key": pair[0:10]})

	print("\r lookup operations >> 100% completed")
	end_dt = dt.now()
	delta = end_dt - start_dt
	delta_sec = delta.seconds + delta.microseconds / 1000000
	print("Lookup Throughput " + str( NUM_OF_OPS / delta_sec ) + " OPs/s")

	return NUM_OF_OPS / delta_sec

def run_exp_delete(db, random_str_arr):
	length = len(random_str_arr)
	start_dt = dt.now()

	for i in range(0, length):
		if (i*100/NUM_OF_OPS)%10 == 0:
			print("\r delete operations >> " + str(int(i*100/NUM_OF_OPS)) + "% completed")

		pair = random_str_arr[i]
		db.MyHashCollection.delete_one({"key": pair[0:10]})

	end_dt = dt.now()
	delta = end_dt - start_dt
	delta_sec = delta.seconds + delta.microseconds / 1000000
	print("Delete Throughput " + str( NUM_OF_OPS / delta_sec ) + " OPs/s")

	return NUM_OF_OPS / delta_sec

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
	insert_throuput = run_exp_insert(db, random_str_arr)
	lookup_throuput = run_exp_lookup(db, random_str_arr)
	delete_throuput = run_exp_delete(db, random_str_arr)

	overall_throuput = (insert_throuput + lookup_throuput + delete_throuput) / 3
	print("Overall Throughput: " + str(overall_throuput) + " OPs/s")

if __name__ == "__main__":
	args = sys.argv[1:]

	if len(args) < 1:
		print("Invalid arguements passed!! Please use the command python3 mongo-throuput.py mongos.conf")
		sys.exit(1)

	run_cluster_server(args[0])
	print("Experiment completed.....")