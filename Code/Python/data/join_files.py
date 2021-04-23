import os

"""p = os.getcwd() + "/" # current dir

dirres = p + "res/"

for dname in os.listdir(p):
	if dname[:4] == "data":
		d = p+dname+"/"
		for fname in os.listdir(d):
			with open(dirres+fname, "a") as fw:
				with open(d+fname, "r") as fr:
					for line in fr:
						print(line, end="", file=fw)"""

def join(path):
    dirres = path + "res/"
    for fname in os.listdir(dirres):
        os.remove(dirres+fname)

    for dname in os.listdir(path):
	    if dname[:4].lower() == "data":
		    d = path+dname+"/"
		    for fname in os.listdir(d):
			    with open(dirres+fname, "a") as fw:
				    with open(d+fname, "r") as fr:
					    for line in fr:
						    print(line, end="", file=fw)
    

def test():
    print(os.getcwd() + "/")
    
