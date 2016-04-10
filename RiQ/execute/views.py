from django.shortcuts import render
from django.shortcuts import render_to_response
from django.http import HttpResponse
from indexinfo.models import indexdata,graphdata,queryfilenametable
from django.template import RequestContext
from execute.forms import ExecuteForm
from run.views import *
from thread import start_new_thread
import json
from os import remove
import ConfigParser
import os
import sys
import glob
import time
def land(request):
	indexdataobject = indexdata.objects.all()
	querynamedataobject = queryfilenametable.objects.all()
	queryInfo = {}
	if request.method == 'GET':
		form = ExecuteForm()
		context = {'form': form,'TITLE' : 'Execute Sparql'}
		return render_to_response('execute.html', context,context_instance=RequestContext(request))

	elif request.method == 'POST':

		print request.POST
		try:
			IndexName 	= request.POST.__getitem__('indexname')
			QueryId	= request.POST.__getitem__('queries')
			query = request.POST.__getitem__('qtext')
			queryInfo['index']=IndexName
			queryInfo['name']=QueryId
			updateDatasetName(IndexName)


		except:
			return HttpResponse("Form Not Valid!", status=500,content_type='plain/text')


		#DETERMINE CACHE/OPT PARAMETERS
		try:
			TypeCache = request.POST.__getitem__('typecache')
		except:
			TypeCache = 'none'#was warm
		try:
			optimizeType = request.POST.__getitem__('optimizationtype')
		except:
			optimizeType = 'opt'

		if(TypeCache=='warm'):
			TypeCache = '-c none'#was warm
			queryInfo['cache']='Warm'
		else:
			TypeCache = '-c cold'
			queryInfo['cache']='Cold'

		if(optimizeType=='opt'):
			optimizeType = '-O'
			queryInfo['opt']='Enabled'
		else:
			optimizeType = ''
			queryInfo['opt']='Disabled'


		qi = open('queries/temp.info', 'w')
		qi.write(json.dumps(queryInfo).encode('utf-8'));
		qi.close()

		s = open('status/execute', 'w')
		s.write('Started..\n')
		s.close()
		if QueryId=='BTC10' or QueryId=='BTC11':
			tools=[True,False,False]
		else:
			tools=[True,True,True]
		#	return HttpResponse("Results from previous\n   runs will be shown!", status=200,content_type='plain/text')

		args = " "+TypeCache+" "+optimizeType+" "


		removePreviousRunFiles('execute')
			
		r=runMultiToolQuery('temp.q',query.encode(sys.stdout.encoding),args,tools)
	
          	s = open('status/execute', 'a')
                s.write('Done\n')
                s.close()
		#start_new_thread(runQuery,(query.encode(sys.stdout.encoding),args,'temp.q','riq')

		return HttpResponse('Query Received!', status=200,content_type='plain/text')

def updateDatasetName(IndexName):
			DATASET_PREFIX_DIR =  os.path.join(os.path.abspath(os.pardir),'RIS/indexing/RIS.RUN/data/')
			FILE = ''

			if IndexName == 'BTC':
				FILE = 'btc-2012-split-clean'
			elif IndexName == 'LOGD':
				FILE = 'dbpedia'
			elif IndexName == 'D10':
				FILE = 'd10-small-sample'
			RIQ_CONF =  ('config-files/riq.conf')

			config = ConfigParser.RawConfigParser()
			config.optionxform=str
			config.read(RIQ_CONF)

			config.set('Dataset', 'NAME',FILE)

			#Write new configuration
			f = open('riqtemp.conf','w')
			config.write(f)
			f.close()

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

			remove('riqtemp.conf')

def getTimings(request):

	qId = request.GET['queryId']
	c = request.GET['cache']
	o = request.GET['opt']
	times = {}

	print 'Getting timings..'
	print 'Query: '+qId
	print 'Cache: '+c
	print 'Optimization: '+o
   
    	a=open('status/execute','rb')
        lines = a.readlines()
        a.close()
	print lines
        if lines:
		
                slast_line = lines[-2]
                last_line = lines[-1].rstrip('\n')
		
                if last_line == 'Done':
			print 'Time is Ready'
                        t = slast_line.rstrip('\n').split(':')[1].split(',')
                        tr= t[0].split('/')
               		times['riqf'] = tr[1]
                	times['riq'] =  tr[0]
	
			if qId == 'CUSTOM':
				times['type'] = c
				times['virt'] = t[2]
				times['jena'] = t[1]


			elif qId == 'BTC10':
				if c=='cold':
					times['type'] = 'cold'
					if o=='opt':
						#times['riqf'] = '6.42'
						#times['riq'] = '16.29'
						times['virt'] = '39.18'
						times['jena'] = '3564.4'
					elif o=='nopt':
						#times['riqf'] = '11.83'
						#times['riq'] = '495.81'
						times['virt'] = '39.18'
						times['jena'] = '3564.4'

				elif c=='warm':
					times['type'] = 'warm'
					if o=='opt':
						#times['riq'] = '6.79'
						#times['riqf'] = '0.66'
						times['virt'] = '0.16'
						times['jena'] = '369.81'
					elif o=='nopt':
						#times['riq'] = '355.07'
						#times['riqf'] = '0.95'
						times['virt'] = '0.16'
						times['jena'] = '369.81'
			elif qId == 'BTC11':
				if c=='cold':
					times['type'] = 'cold'
					if o=='opt':
						#times['riq'] = '158.18'
						#times['riqf'] = '5.7'
						times['virt'] = '237.58'
						times['jena'] = '2050.62'
					elif o=='nopt':
						#times['riq'] = '163.05'
						#times['riqf'] = '5.45'
						times['virt'] = '237.58'
						times['jena'] = '2050.62'
				elif c=='warm':
					times['type'] = 'warm'
					if o=='opt':
						#times['riq'] = '76.68'
						#times['riqf'] = '0.61'
						times['virt'] = '120.28'
						times['jena'] = '2102.06'
					elif o=='nopt':
						#times['riq'] = '90.09'
						#times['riqf'] = '0.43'
						times['virt'] = '120.28'
						times['jena'] = '2102.06'


                elif last_line == 'Error':
                        times['riqf'] = '10'
                        times['riq'] =  '5'
                        times['virt'] = '10'
                        times['jena'] = '10'
        else:
                        times['riq']  = '15'
                        times['riqf'] = '15'
                        times['virt'] = '15'
                        times['jena'] = '15'
	print times
	return HttpResponse(json.dumps(times), content_type="application/json")


def getResults(request):
	qId = request.GET['queryId']
	c = request.GET['cache']
	o = request.GET['opt']

	if c=='warm':
		c='none'

	#if qId == 'CUSTOM':
	data = getQueryResults('temp.q','riq',c)
	return HttpResponse(content=data,content_type='xml; charset=utf-8')
	#elif qId == 'BTC10':
	#	data = getQueryResults('q20.','riq',c)
	#	return HttpResponse(content=data,content_type='xml; charset=utf-8')
	#elif qId == 'BTC11':
	#	data = getQueryResults('q3.','riq',c)
	#	return HttpResponse(content=data,content_type='xml; charset=utf-8')

def getStatus(request):

		a=open('status/execute','rb')
		lines = a.readlines()
		a.close()
		if lines:
			first_line = lines[:1]
			last_line = lines[-1].rstrip()

		if last_line == 'Done':
			return HttpResponse("true", status=200,content_type='plain/text')
		elif last_line == 'Error':
			return HttpResponse("error", status=200,content_type='plain/text')
		print last_line
		return HttpResponse("false", status=200,content_type='plain/text')

def getQueryGraph(request):

	qId = request.GET['queryId']
	c = request.GET['cache']
	o = request.GET['opt']

	if qId == 'CUSTOM':
		filename = 'temp.q'

	elif qId == 'BTC10':
		filename = 'q20.'+o+'.'+c

	elif qId == 'BTC11':
		filename = 'aux.q3.'+o+'.'+c

	DIR =  os.path.join(os.path.abspath(os.pardir),'RIS/indexing/RIS.RUN/log/')
        bgpfile = ''
        file_dir_extension = os.path.join(DIR, '*'+filename+'*filter.1.log')
	print 'Getting TPs in the query from:'
        for name in glob.glob(file_dir_extension):
		print name
                bgpfile = name

	rlog = open(bgpfile)
	bStartRead = 0
	d3records = {}
	d3record = {}
	#graphdataobject.subject =  set the value of subject
	i=0
	for line in rlog:

		if line.startswith('structure format:') == True & bStartRead == 0:
				bStartRead = 1

		if (line.startswith('subject') == True) & bStartRead == 1:
				d3record['subject'] = line[line.find(': ')+1:].rstrip('\n')


		if (line.startswith('predicate') == True) & bStartRead == 1:
				d3record['predicate'] = line[line.find(': ')+1:].rstrip('\n')


		if (line.startswith('object') == True) & bStartRead == 1:
				d3record['object'] = line[line.find(': ')+1:].rstrip('\n')
				d3records[i] = d3record
				d3record = {}
				i=i+1

		if line.find('parse_tree size:') != -1:
				break


	rlog.close()

	return HttpResponse(json.dumps(d3records), content_type="application/json")


def getQueryList(request):
	queryname = request.GET['name'].lower()
	query = """SELECT *
WHERE {
	GRAPH ?g{
		?s ?p	"Brunei"@en .
	}
}
"""
	if(queryname == 'btc11'):
		query = """PREFIX dc: <http://purl.org/dc/elements/1.1/>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
PREFIX space: <http://purl.org/net/schemas/space/>
PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
PREFIX dbpedia-prop: <http://dbpedia.org/property/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT *
WHERE {
	GRAPH ?g {
		?var5 dbpedia-owl:thumbnail ?var4 .
		?var5 rdf:type dbpedia-owl:Person .
		?var5 rdfs:label ?var .
		?var5 foaf:page ?var8 .
		OPTIONAL { ?var5 foaf:homepage ?var10 . }
	}
}
"""

	elif(queryname == 'btc10'):
		query ="""PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>

SELECT *
WHERE {
	GRAPH ?g {
		?var6 a <http://dbpedia.org/ontology/PopulatedPlace> .
		?var6 <http://dbpedia.org/ontology/abstract> ?var1 .
		?var6 rdfs:label ?var2 .
		?var6 geo:lat ?var3 .
		?var6 geo:long ?var4 .
		{
			?var6 rdfs:label "Brunei"@en .
		}
		UNION
		{
			?var5 <http://dbpedia.org/property/redirect> ?var6 .
			?var5 rdfs:label "Brunei"@en .
			OPTIONAL { ?var6 foaf:homepage ?var10 }
			OPTIONAL { ?var6 <http://dbpedia.org/ontology/populationTotal> ?var12 }
			OPTIONAL { ?var6 <http://dbpedia.org/ontology/thumbnail> ?var14 }
		}
	}
}
"""

	return HttpResponse(query,  content_type="text/plain")

class D3GraphData:
  def __init__(self):
    self.subject = None
    self.predicate = None
    self.object = None

class Candidate:
  def __init__(self):
    self.name = None
