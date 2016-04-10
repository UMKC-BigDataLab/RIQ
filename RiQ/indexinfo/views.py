from django.shortcuts import render_to_response, redirect
from django.shortcuts import render
from django.shortcuts import render_to_response
from django.template import RequestContext
from django.http import HttpResponse, HttpResponseRedirect
from django import forms
from datetime import datetime
from os import remove
import ConfigParser
from pexpect import pxssh
import subprocess
import locale
import csv
import sys
import requests
import json
import os
import getpass
import time
from indexinfo.models import indexdata,graphdata,queryfilenametable
from indexinfo.forms import IndexConstructionForm

def land(request):
	print request.POST
	if request.method == 'POST':
		form = IndexConstructionForm(request.POST)

		if form.is_valid():
# 			print form.cleaned_data['dataset']
# 			print form.cleaned_data['lhskparameter']
# 			print form.cleaned_data['lhslparameter']
# 			print form.cleaned_data['maximumgraphs']
# 			print form.cleaned_data['bloomerror']
# 			print form.cleaned_data['graphindex']
			currentDate = datetime.now()

			indexName = form.cleaned_data['dataset'] + 'K' + form.cleaned_data['lhskparameter'] + 'L' + form.cleaned_data['lhslparameter'] + currentDate.strftime("%Y-%m-%d %H%M%S")

			#indexdataobject = indexdata.objects.create(Dataset=form.cleaned_data['dataset'], LHSKParameter=form.cleaned_data['lhskparameter'], LHSLParameter=form.cleaned_data['lhslparameter'],MaximumGraphs=form.cleaned_data['maximumgraphs'], BloomError=form.cleaned_data['bloomerror'], BloomCapacity= form.cleaned_data['bloomcapacity'],  GraphIndex= form.cleaned_data['graphindex'], IndexName = indexName, CreateDate = currentDate )
			DsName=form.cleaned_data['dataset']

			if DsName == 'BTC':#use static values
				DS     = 'btc-2012-split-clean'
				LSH_K  = '4'
				LSH_L  = '6'
				MAX_CC = '25000'
				MIN_CC = '2500'
				CAP    = '1000000'
				ERR    = '0.05'


			else:
				DS     = 'd10-small-sample'
				LSH_K  = form.cleaned_data['lhskparameter']
				LSH_L  = form.cleaned_data['lhslparameter']
				MAX_CC = form.cleaned_data['maximumgraphs']
				MIN_CC = '0'
				CAP    = form.cleaned_data['bloomcapacity']
				ERR    = form.cleaned_data['bloomerror']



			RIQ_CONF =  os.path.join(os.path.abspath(os.pardir),'RIS/indexing/RIS.RUN/riq.conf')
			print ('Setting Configuration: '+RIQ_CONF)
			config = ConfigParser.RawConfigParser()
			config.optionxform=str
			config.read(RIQ_CONF)
			config.set('DEFAULT', 'USER', 'vsfgd') # getpass.getuser()
			config.set('Dataset', 'NAME',DS)

			config.set('LSH','LSHK',LSH_K)
			config.set('LSH','LSHL',LSH_L)
			config.set('Limits', 'MAXCCSIZE',MAX_CC)
			#config.set('Section=GRAPHS', 'GRAPHS2INDEX',form.cleaned_data['graphindex'])
			config.set('Dablooms', 'CAPACITY',MIN_CC)
			config.set('Dablooms', 'ERROR_RATE',ERR)

			print('Setting.. Done.')

			# Write prefix for the dataset and set its conf name
			DATASET_PREFIX_DIR =  os.path.join(os.path.abspath(os.pardir),'RIS/indexing/RIS.RUN/data/')

			#Write new configuration
			f = open('riqtemp.conf','w')
			config.write(f)
			f.close()

			#print 'locale encoding: ' + locale.getpreferredencoding()
			# read the riqtemp.conf
			# create riq.conf
			q = open('riqtemp.conf')
			r = open('config-files/riq.conf', 'w')
			for line in q:
				if line.find(' = ') != -1:
					r.write(line.replace(' = ', '='))
				else:
					r.write(line)
			q.close()
			r.close()

			# remove riqtemp.conf
			remove('riqtemp.conf')
			if DsName == 'BTC':
				pvstatus ='{"PV Index Size":"6.5GB" ,"Avg graph size":"141 quads","Written graphs":"9,596,021","Max graph size":"2,942,127 quads","Total size":" 1,356,032,456 quads","group":{"union_t":22701,"total_t":28506.7,"build_graph_t":12.5072,"pv_lsh_t":2590.33},"split":{"split_t":2045.89},"pv_t":"16700","cbf":{"cbf_t":2475.6}}'

			else:
				pvstatus = constructPVs(DATASET_PREFIX_DIR,FILE)

			return render_to_response('index.html', {'form': form, 'TITLE' : 'Index Construction','IndexName' : indexName, 'PVStatus':pvstatus}, context_instance=RequestContext(request))
		else:
			print 'form is invalid'
			print form.errors
			return render_to_response('index.html', {'form': form,'TITLE' : 'Index Construction'}, context_instance=RequestContext(request))
	else:
		form = IndexConstructionForm()
		context = {'form': form,'TITLE' : 'Index Construction'}
		return render_to_response('index.html', context, context_instance=RequestContext(request))

# Assuming RIQ's code is in the same main directory as the demo
def constructPVs(prefix, infile):
	#Construct PVs
	RIQ_DIR  = os.path.join(os.path.abspath(os.pardir),'RIS')
	cmd = [ RIQ_DIR+"/indexing/code/rdf2spovec/rdf2spovec", '-f','nquads', '-i', prefix+infile+".nq", '-o', prefix+infile+".sigv2"]
	start = time.time()
	p = subprocess.Popen(cmd, shell=False, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
	p_stdout = p.stdout.read()
	p_stderr = p.stderr.read()
	end = time.time()
	pvdur = {u'pv_t' : str(end - start)}
	json_data = {u'content':'none'}

	#Run indexing
	cmdi = [ RIQ_DIR+"/indexing/RIS/scripts/run_riq_index.py", "-v" ,"-C", "../RiQ/config-files/riq.conf"]
	pi = subprocess.Popen(cmdi, shell=False, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
	pi_stdout = pi.stdout.read()
	pi_stderr = pi.stderr.read()
	print(pi_stdout)

	#Get results
	if (len(p_stderr)==0):
		with open(RIQ_DIR+"/indexing/RIS.RUN/log/index."+infile+".all.json") as json_file:
			json_data = json.load(json_file)
			json_data.update(pvdur)
		p_stdout = p_stdout.splitlines()
		for s in p_stdout:
			if s.startswith(('Written graphs','Avg graph', 'Max graph', 'Total size','Total URIs')):
				s=s.split(":")
				j =  {s[0]:s[1]}
				json_data.update(j)

	else:
		return "ERRORs: FOUND % DURATION: "+str(dur)

	return json.dumps(json_data)
