from django.shortcuts import render
import json
import time
import subprocess
import sys, os
import traceback
import json
import fyzz
import glob
from subprocess import call
from django.shortcuts import render
import json
import time
import subprocess
import sys, os
import json
import fyzz
import glob
import shutil
import time
import urllib
from threading import Thread
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

def runMultiToolQuery(filename,query,args,tools):
		print '\n\nFilename: '+filename
		times = ['-1','-1','-1']

		if(filename=='temp.q'):
			status='execute'
		else:
			status='linked'
		sFile = open('status/'+status,'a')
		if (tools[0]==True):
			sFile.write('Running Query via RIQ..\n')
			sFile.flush()
			start = time.time()
			results1 = runQuery(query, args,filename,'riq')
			end = time.time()
			sFile.write('RIQ Finished.\n')
			if (results1.startswith("error")):
				sFile.write('Error RIQ\n')
				sFile.close()
				return 'error'
			if(status=='linked'):
				times[0] = getRiqTime(filename,args)[0]
			else:
				rtime = getRiqTime(filename,args)
				times[0] = str(rtime[0])+'/'+str(rtime[1])

		if (tools[1]==True):
			if 'cold' in args:
				print 'Run jena in Cold'
				clearCache()
			sFile.write('Running Query via JenaTDB..\n')
			sFile.flush()
			start = time.time()
			results2 = runQuery(query, args,filename,'jena')
			end = time.time()
			sFile.write('Jena Finished.\n')
			times[1] = str(round(end - start,3))
			if (results2.startswith("error")):
				sFile.write('Error JenaTDB')
				sFile.close()
				return 'error'
            
		if (tools[2]==True):
			if 'cold' in args:
				print 'Run virt in Cold'
				clearCache()
                        sFile.write('Running Query via Virtuoso..\n')
                        sFile.flush()
                        start = time.time()
                        results3 = runQuery(query, args,filename,'virt')
                        end = time.time()
			print 'Virtuoso: '+results3
                        sFile.write('Virtuoso Finished.\n')
                        times[2] = str(round(end - start-100,3))
                        if (results3.startswith("error")):
                                sFile.write('Error Virtuoso')
                                sFile.close()
                                return 'error'
			
			#Thread(target=stopVirt, args=()).start()

		tTime = str(times[0])+','+str(times[1])+','+str(times[2])
		sFile.write('Times:'+tTime+'\n')
		sFile.close()
		return 'Finished'

def getRiqTime(filename,args):
	cache = 'warm'
	if 'none' in args:
		cache = 'none'
	elif 'cold' in args:
		cache = 'cold'

	opt = 'nopt'
	if '-O' in args:
		opt='opt'
	print 'RIQ Times..'
        DIR  = os.path.join(os.path.abspath(os.pardir))
        file_dir_extension = os.path.join(DIR+"/RIS/indexing/RIS.RUN/log/", '*'+filename+'*.'+opt+'.'+cache+'.all.json')
	t = '-1'        
	print 'Matching timing file: '+file_dir_extension
        try:
		jfile= ''
                for name in glob.glob(file_dir_extension):
                        print name
			jfile = name

		jfile = open(jfile,'r')                        
		jdata = json.load(jfile)
		jfile.close()
		rTime = jdata['refine']['avg_refine_t']
		fTime = jdata['filter']['avg_filter_t']
		t = str(round(float(rTime)+float(fTime),3))
      	except Exception as E:
                print 'Riq Time Error:'
                print E	

	return [t,fTime]

def runQuery(query,args,filename,tool):

	createQueryFile(query,filename)
	DIR  = os.path.join(os.path.abspath(os.pardir))
	args = args.split()

	print 'Running Tool Query via '+tool
	if tool == 'riq':
		cmd = [ DIR+"/RIS/indexing/RIS/scripts/run_riq_query.py", "-C", "config-files/riq.conf","-q", "queries/"+filename, "-f" ,"xml"]
		if 'cold' in args:
			for a in args:
				cmd.append(a)
		elif '-O' in args:
			cmd.append('-O')
		print 'Command: '+str(cmd)
 		p = subprocess.Popen(cmd, shell=False, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
 		p_stdout = p.stdout.read()
 		p_stderr = p.stderr.read()
 		print(p_stdout)
 		if len(p_stderr)>0:
 			print(p_stderr)
		output = p_stdout.split('\n')
		for s in output:
			if s.startswith("error"):
				return s
		print 'RIQ Finished'
	elif tool == 'jena':
		#cmd = [ DIR+"/RIS/scripts/run_query_all.sh", "/mnt/data2/datasets/btc-2012-split-clean/btc-2012-split-clean.nq.tdb", "queries/"+filename,"tdb"]
		#for a in args:
                #        cmd.append(a)
		cmd = ['/home/vsfgd/Jena/apache-jena-2.11.1/bin/tdbquery','--loc','/mnt/data2/datasets/btc-2012-split-clean/btc-2012-split-clean.nq.tdb','--file', 'queries/'+filename,'--results','xml']
		print 'Issuing Jena CMND:'
		print cmd
 		p = subprocess.Popen(cmd, shell=False, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
 		p_stdout = p.stdout.read()
 		p_stderr = p.stderr.read()
                if len(p_stderr)>0:
                        print(p_stderr)
                        return 'error'
		xml =  p_stdout.split('<?xml version="1.0"?>')
		if (len(xml)>1):	
			p_stdout='<?xml version="1.0"?>'+xml[1]
		fout = open('output/jena/'+filename,'w')
 		fout.write(p_stdout)
		fout.close()
		print 'Jena Finished'
	
        elif tool == 'virt':
		"""
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
                """
       		t = Thread(target=start, args=()).start()

        	time.sleep(100)
        	data=runq(filename)
        	stop()
               	fout = open('output/virt/'+filename,'w')
                fout.write(data)
                fout.close()

		print 'Virtuoso Finished'
		
	return 'done'
def clearCache():
	call(["static/scripts/run_clear_cache.sh"])

def getQueryResults(filename,tool,cache):
	print 'Getting Results..'
	DIR  = os.path.join(os.path.abspath(os.pardir))
        rFile = ''
        if tool=='jena' or tool=='virt':
                file_dir_extension = os.path.join(DIR+"/RiQ/output/"+tool, filename)
	else:
        	file_dir_extension = os.path.join(DIR+"/RIS/indexing/RIS.RUN/log/", '*'+filename+'*.'+cache+'.*results')
        
	print 'locating results file: '+file_dir_extension

	try:
		for name in glob.glob(file_dir_extension):
			print 'Found file: '+name
			rFile = name

 		with open (rFile, "r") as f:
                                        line=f.readline()
                                        while 'WARN' in line:
                                               line=f.readline()
                                        
					i=1
                                        lines = line
                                        while line and i < 5000 :
                                                line = f.readline()
                                                lines = lines + line
                                        	if filename=='temp.q':
                                                	i = i + 1
                                        if i>4998:
                                                lines = lines + ' Showing first 5000 lines'
		f.close()

		return lines
	except Exception as E:
		print 'Results Error:'
		print E
		# Error, return empty xml #
	f =  DIR+'/RiQ/queries/'+filename
	outf = open(f,'r')
	qStr=outf.read()
	outf.close()
	selected_vars = getVars(qStr)
	data =  getXML(selected_vars)
	return data

def createQueryFile(queryStr,filename):
        f = open('queries/'+filename,'w')
        f.write(queryStr)
        f.close()

def getVars (query):
	vrs=[]
	ast = fyzz.parse(query)
	if ast.selected == ['*']:
		varNDXs = [i for i in range(len(query)) if query.startswith('?', i)]
		for v in varNDXs:
			var = query[v+1:v+2]
			if var not in vrs:
				vrs.append(var)
	else:
		for v in ast.selected:
			vrs.append(v.name)
	return vrs

def getXML(variables):
	xmlDoc="""<?xml version="1.0"?><sparql xmlns="http://www.w3.org/2005/sparql-results#"><head>"""
	for v in variables:
		xmlDoc=xmlDoc+'<variable name="'+v+'"/>'
	xmlDoc = xmlDoc + '</head></sparql>'
	return xmlDoc

def removePreviousRunFiles(caller):
	DIR  = os.path.join(os.path.abspath(os.pardir))

	if caller=='execute':
		filename='temp.q'
		#os.remove(DIR+'/RiQ/status/execute')

	log_dir_extension = os.path.join(DIR+"/RIS/indexing/RIS.RUN/log/", '*'+filename+'*')
	print 'removing files in: '+log_dir_extension
	try:
		for name in glob.glob(log_dir_extension):
			if os.path.isfile(name):
				os.remove(name)
			else:
				shutil.rmtree(name)
	except Exception, err:
		 print sys.exc_info()[0]
