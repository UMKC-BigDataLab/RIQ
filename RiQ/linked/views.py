from django.shortcuts import render
from django.shortcuts import render_to_response
from django.http import HttpResponse
import requests
from run.views import createQueryFile
import subprocess

# def land(request):
# 	return render_to_response('linked.html', { 'TITLE': 'Linked Data'})

from django.shortcuts import render
from django.shortcuts import render_to_response
from django.http import HttpResponse
from indexinfo.models import indexdata,graphdata,queryfilenametable
from django.template import RequestContext
from linked.forms import LinkedForm
import json
from os import remove
import ConfigParser
import os
import sys
from thread import start_new_thread

def land(request):
	indexdataobject = indexdata.objects.all()
	querynamedataobject = queryfilenametable.objects.all()
	if request.method == 'GET':
		form = LinkedForm()
		context = {'form': form,'TITLE' : 'Execute Sparql'}
		return render_to_response('linked.html', context,context_instance=RequestContext(request))

	elif request.method == 'POST':

		print request.POST
		QueryId = 'CUSTOM'
		try:
			format  = request.POST.__getitem__('format')
			QueryId = request.POST.__getitem__('queries')
			query = request.POST.__getitem__('qtext')
			settings = request.POST.getlist('settings')
		except:
				print "Linked Form Error:", sys.exc_info()[0]
				return HttpResponse("Form Not Valid!", status=500,content_type='plain/text')
		tools=''

		if QueryId != 'F1' and  QueryId != 'F2':
	
			if u'riq' in settings:
                	        tools = '1'
			else:
                        	tools = '0'

			if u'jena' in settings:
        	                tools = tools + '1'
			else:
                	        tools = tools + '0'
			if u'virt' in settings:
                	        tools = tools + '1'
			else:
                        	tools = tools + '0'
		else :
			tools='100'

		query = query.replace(' <http://134.193.129.222:8080/endpoints/>',' <http://134.193.129.222:8080/endpoints/?tools='+tools+'>')
		executeQuery(query,format)
		return HttpResponse('Query Received!', status=200,content_type='plain/text')

def executeQuery(query,outputformat):
	print 'Thread started'

	f = open('status/linked','w')
	f.write('started\n')
	f.close()
	filename = 'tempLinked.q'
	createQueryFile(query,filename)
	#reqData = {'query':query,'output':outputformat}
	#headers = {'content-type': 'application/x-www-form-urlencoded'}
	#resp = requests.post('http://134.193.128.130:3030/btc/query',params=reqData,headers=headers )
	#/tdbquery --loc=/mnt/data2/datasets/btc-2012-split-clean/btc-2012-split-clean.nq.tdb --query=/home/anask/RiQ/queries/tempLinked.q -v --optimize=off


	cmd = ['/home/vsfgd/Jena/apache-jena-2.11.1/bin/tdbquery','--loc','/mnt/data2/datasets/btc-2012-split-clean/btc-2012-split-clean.nq.tdb','--file', 'queries/'+filename,'--results',outputformat,'--optimize','off']
        print 'Issuing Linked Query:'
        print cmd
        p = subprocess.Popen(cmd, shell=False, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        p_stdout = p.stdout.read()
        p_stderr = p.stderr.read()
        if len(p_stderr)>0:
             print 'Linked Query Error:'
             print(p_stderr)
       
        print 'Linked Query Finished'
	xml =  p_stdout.split('<?xml version="1.0"?>')
        if (len(xml)>1):
                        p_stdout='<?xml version="1.0"?>'+xml[1]
	f = open('output/tempLinked.txt','w')
	f.write(p_stdout)
	f.close()

        f = open('status/linked','a')
        f.write('Done')
        f.close()

def getStatus(request):

                a=open('status/linked','rb')
                lines = a.readlines()
                a.close()
		print 'Linked Status: '+str(lines)

                if lines:
                        first_line = lines[:1]
                        last_line = lines[-1]

                if last_line == 'Done':
                        return HttpResponse("true")
                elif last_line == 'Error':
                        return HttpResponse("error")

                return HttpResponse("false")
def getTimings(request):
	qid = request.GET['qid']
	times = {}
	
 	a=open('status/linked','rb')
	lines = a.readlines()
	a.close()

	if lines:
                slast_line = lines[-2]
                last_line = lines[-1]

        	if last_line == 'Done':
			t = slast_line.rstrip('\n').split(':')[1].split(',')
			times['riq']  = t[0]
       			if qid =='CUSTOM':
                        	times['virt'] = t[2]
                        	times['jena'] = t[1]
                	elif qid=='F1':
				times['virt'] = '39.18'
				times['jena'] = '3564.4'
			elif qid=='F2':     
				times['virt'] = '237.58'
				times['jena'] = '2050.62'
  
        	elif last_line == 'Error':
                
			times['riq']  = '5'
			times['virt'] = '5'
			times['jena'] = '5'
	else:
	                times['riq']  = '10'
                        times['virt'] = '10'
                        times['jena'] = '10'

	return HttpResponse(json.dumps(times), content_type="application/json")


def getResults(request):

	rf =  os.path.join(os.path.abspath(os.pardir),'RiQ/output/tempLinked.txt')
        with open (rf, "r") as f:
                        N=10000
                        line = f.readline()
                        i = 0
                        while line and i < N:
                                
                                line = line + f.readline()
                                i = i + 1
			if i > 998:
				line = line + 'Showing first 10000 lines.' 
	return HttpResponse(line, content_type="plain/text")


def getQueryList(request):
	queryname = request.GET['name'].lower()
	query = """SELECT * 
WHERE {
	SERVICE <http://134.193.129.222:8080/endpoints/> {
		GRAPH ?g {
			?s ?p "Brunei"@en .
			?s <http://dbpedia.org/property/leaderName> ?leader.
		}
	}
 
	SERVICE <http://dbpedia.org/sparql> {
		?s <http://dbpedia.org/property/establishedDate> ?est .
		?s <http://dbpedia.org/ontology/capital> ?cap .
	}
}
"""

	if(queryname == 'f1'):
		query = """PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>

SELECT * 
WHERE {
	SERVICE <http://134.193.129.222:8080/endpoints/> {
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
}
"""

	elif(queryname == 'f2'):
		query ="""
PREFIX dc: <http://purl.org/dc/elements/1.1/>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
PREFIX space: <http://purl.org/net/schemas/space/>
PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
PREFIX dbpedia-prop: <http://dbpedia.org/property/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT * 
WHERE {
	SERVICE <http://134.193.129.222:8080/endpoints/> {
	GRAPH ?g {
		?var5 dbpedia-owl:thumbnail ?var4 .
		?var5 rdf:type dbpedia-owl:Person .
		?var5 rdfs:label ?var .
		?var5 foaf:page ?var8 .
		OPTIONAL { ?var5 foaf:homepage ?var10 . }
	}	
	}
}
"""
	elif(queryname == 'f3'):
		query ="""
SELECT ?union ?unionShortName ?continent1 ?country1 ?officialName1 ?continent2 ?country2 ?officialName2 WHERE
{
SERVICE <http://134.193.129.222:8080/endpoints/> {

  GRAPH ?g {
    ?union <http://aims.fao.org/aos/geopolitical.owl#nameShortEN> ?unionShortName .
    ?union <http://aims.fao.org/aos/geopolitical.owl#hasMember> ?country1 .
    ?union <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://aims.fao.org/aos/geopolitical.owl#economic_region> .

    ?continent1 <http://aims.fao.org/aos/geopolitical.owl#hasMember> ?country1 .
    ?continent1 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://aims.fao.org/aos/geopolitical.owl#geographical_region> .
    ?continent1 <http://aims.fao.org/aos/geopolitical.owl#nameShortEN> "Africa"^^<http://www.w3.org/2001/XMLSchema#string> .

    ?continent2 <http://aims.fao.org/aos/geopolitical.owl#hasMember> ?country2 .
    ?continent2 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://aims.fao.org/aos/geopolitical.owl#geographical_region> .
    ?continent2 <http://aims.fao.org/aos/geopolitical.owl#nameShortEN> "Asia"^^<http://www.w3.org/2001/XMLSchema#string> .

    ?country1 <http://aims.fao.org/aos/geopolitical.owl#nameOfficialEN> ?officialName1 .
    ?country1 <http://aims.fao.org/aos/geopolitical.owl#isInGroup> ?union .
    ?country1 <http://aims.fao.org/aos/geopolitical.owl#isInGroup> ?continent1 .
    ?country1 <http://aims.fao.org/aos/geopolitical.owl#isInGroup> <http://aims.fao.org/aos/geopolitical.owl#World> .
    ?country1 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://aims.fao.org/aos/geopolitical.owl#self_governing> .
    ?country1 <http://aims.fao.org/aos/geopolitical.owl#hasBorderWith> ?country2 .

    ?country2 <http://aims.fao.org/aos/geopolitical.owl#nameOfficialEN> ?officialName2 .
    ?country2 <http://aims.fao.org/aos/geopolitical.owl#isInGroup> ?continent2 .
    ?country2 <http://aims.fao.org/aos/geopolitical.owl#isInGroup> <http://aims.fao.org/aos/geopolitical.owl#World> .
    ?country2 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://aims.fao.org/aos/geopolitical.owl#self_governing> .
    ?country2 <http://aims.fao.org/aos/geopolitical.owl#hasBorderWith> ?country1 .
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
