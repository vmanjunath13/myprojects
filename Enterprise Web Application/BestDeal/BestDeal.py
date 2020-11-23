import contextlib
import csv
import datetime
import os
import pymysql

transaction_query = """select * from transactions;"""

connection = pymysql.connect(host='localhost', user='root', password='root', db='bestdealdatabase')

with contextlib.closing(connection):
    with connection.cursor() as cursor:
        cursor.execute(transaction_query)

        txn_results = cursor.fetchall()

transaction_output_file = 'D:\\Masters\\CSP584\\Assignments\\Assignment-4\\transactiongen.csv'
with open(transaction_output_file, 'w', newline='') as csvfile:
    csv_writer = csv.writer(csvfile, lineterminator='\n')
    csv_writer.writerows(txn_results)



