import json
import os

file_path = os.path.abspath(__file__)
abs_path = os.path.dirname(file_path)

config = open(abs_path+'/google_customSearch_APIkey.json').read()
config = json.loads(config)


print(config['API_KEY'], config['cx'])