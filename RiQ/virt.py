import subprocess
import urllib
import subprocess
import thread
import time
import sys
from threading import Thread

def fun():
		
		with open ("queries/tempLinked.q", "r") as qFile:
    			query = qFile.read().replace('\n', '')
		qFile.close()

		myParameters = {'query': query, 'format': 'application/sparql-results+xml','timeout':'90000000','debug':'on','CXML_redir_for_subjs':'121'}

		myPort = "8890"
		myURL = "http://localhost:%s/sparql?%s" % (myPort, urllib.urlencode(myParameters)) 
		data = urllib.urlopen(myURL).read()
		print data

def out():
	print 'Hello World'        

def clr():
        call(["static/scripts/run_clear_cache.sh"])
def start ():

	cmd = ["virtuoso-t","-f"]
        print 'Starting Virtuoso..'
        p = subprocess.Popen(cmd, cwd="/var/lib/virtuoso/db", shell=False, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        p_stdout = p.stdout.read()
        p_stderr = p.stderr.read()
        if len(p_stderr)>0:
             print 'Virtuoso Start Error:'
             print(p_stderr)
	print p_stdout

def stop ():

        cmd = ["pkill","virtuoso"]
        print 'Stopping Virtuoso..'
        p = subprocess.Popen(cmd, cwd="/var/lib/virtuoso/db", shell=False, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        p_stdout = p.stdout.read()
        p_stderr = p.stderr.read()
        if len(p_stderr)>0:
             print 'Virtuoso Stop Error:'
             print(p_stderr)
        print p_stdout

def runq(filename):
		print 'Reading query for virtuoso..'
                with open ("queries/"+filename, "r") as qFile:
                     query = qFile.read().replace('\n', ' ')
                qFile.close()

                myParameters = {'query': query, 'format': 'application/sparql-results+xml','timeout':'90000000','debug':'on'}
                print 'Issuing request..'

                myPort = "8890"
                myURL = "http://localhost:%s/sparql?%s" % (myPort, urllib.urlencode(myParameters))
                data = urllib.urlopen(myURL).read()
                print 'received response'
                return data

def main(fname):

	
	#t = Thread(target=start, args=()).start()
	
	#time.sleep(40)
	#print runq(fname)
	#stop()
	print 'Done'
	return 'Hello World'

if __name__ == "__main__":
    main()
