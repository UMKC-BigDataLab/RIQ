from django.shortcuts import render
from django.shortcuts import render_to_response
from django.http import HttpResponse
import json
import os
import subprocess
from subprocess import call
from PIL import Image
import glob

SITE_ROOT = os.path.dirname(os.path.realpath(__file__))
head, tail = os.path.split(SITE_ROOT)

def land(request):
	print("SRoot: ",SITE_ROOT)
	print('HEAD: ',head,' TAIL: ',tail)
	return render_to_response('visualize.html', { 'TITLE': 'Visualize Query'})


def getQueryInfo(request):
	print ('Getting query info from: '+head+'/queries/temp.info')
	with open(head+'/queries/temp.info') as json_file:

	#print ('Getting query info from: /queries/temp.info')
	#with open('/queries/temp.info') as json_file:
		json_data = json.load(json_file)

	#generate candidates file
	qId= json_data['name']
	#o =  'opt' if json_data['opt']=='Enabled' else 'nopt'
	c =  'warm' if json_data['cache']=='Warm' else 'cold'
	o = 'opt'
	if qId == 'CUSTOM':
		filename = 'temp.q'

	elif qId == 'BTC10':
		filename = '.dbpedia.g.q20.'+o+'.'+c

	elif qId == 'BTC11':
		filename = '.dbpedia.g.aux.q3.'+o+'.'+c


	#############     generate candidates file    #############
	DIR =  os.path.join(os.path.abspath(os.pardir),'RIS/indexing/RIS.RUN/log/')
	#DIR =  os.path.join(os.path.abspath(os.pardir),'RIS/indexing/RIS.RUN/log/')
	candidatefile = ''
	file_dir_extension = os.path.join(DIR, '*'+filename+'*filter.candidates')
	print 'Looking for candidates in: '+ file_dir_extension 
	print 'Cand Log File: '
	for name in glob.glob(file_dir_extension):
			print name
			candidatefile = name


	candidatelog = open(candidatefile)
	print 'Candidates file opened!'
	#demodir =  os.path.join(os.path.abspath(os.pardir),'anask/RiQ/')
	demodir =  os.path.join(os.path.abspath(os.pardir),'RiQ/')
	print 'Openning: '+demodir+"output/candidatedataindecimal.txt" 
	candidatelogindecimal = open(demodir+"output/candidatedataindecimal.txt", 'w')
	for line in candidatelog:
		binarytodecimal = str(int(line, 2))
		candidatelogindecimal.write('Candidate' + binarytodecimal + '\n')

	candidatelog.close()
	candidatelogindecimal.close()
	###########################################################

	bgpfile = DIR+'query.btc-2012-split-clean'+ filename +'.filter.1.log'
	print 'LOG FILE: '+bgpfile
	print 'LOG FILE FOUND: '+str(os.path.exists(bgpfile))
	################## generate non candidate log #############
	# create visualize non-candidate graph data
	bStartRead = 0
	b = open(bgpfile,'r')
	#print 'Create Vis ncgd at: '+head+'/output/visualizenoncandidatedata.log'
	print 'Create Vis ncgd at: /output/visualizenoncandidatedata.log'
	v = open(head+'/output/visualizenoncandidatedata.log', 'w')
	#v = open('/output/visualizenoncandidatedata.log', 'w')
	for line in b:
		if line.startswith('eval_tree: 0') == True & bStartRead == 0:
			bStartRead = 1
		if line.startswith('print_eval_tree:') == True & bStartRead == 1:
			v.write(line[line.find(': ')+1:])
			break
	b.close()
	v.close()
	###########################################################

	################## generate candidate log #################
	# create visualize candidate graph data
	expression = ''
	bStartRead = 0
	b = open(bgpfile,'r')
	y = open(head+'/output/visualizecandidatedata.log', 'w')
	#y = open('/output/visualizecandidatedata.log', 'w')
	for line in b:
		if line.startswith('eval_tree: 1') == True & bStartRead == 0:
			bStartRead = 1
		if line.startswith('print_eval_tree:') == True & bStartRead == 1:
			expression =  line[line.find(': ')+1:]
		if line.startswith('candidate GRAPHID:') == True & bStartRead == 1:
			y.write(line)
			y.write(expression)

	b.close()
	y.close()
	###########################################################

	return HttpResponse(json.dumps(json_data), content_type="application/json")

def getExecuteQueryInfo():
	#{"opt": "Enabled", "index": "BTC", "cache": "Cold", "name": "BTC10"}
	with open('queries/temp.info') as json_file:
		json_data = json.load(json_file)
	return json_data

def getQuery(request):
	info = getExecuteQueryInfo()
	query={}

	if info['name']=='CUSTOM':
		with open('queries/temp.q') as f:
			query['query']=f.read()
	elif info['name']=='BTC10':
			query['query']="""PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
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
	elif info['name']=='BTC11':
		query['query']="""PREFIX dc: <http://purl.org/dc/elements/1.1/>
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

	return HttpResponse(json.dumps(query), content_type="application/json")


def getCandQuery(request):
	info = getExecuteQueryInfo()

	cand=request.GET['cand']

	#convert int to binary
	result = str(bin(int(cand.strip("Candidate"))))
	binaryValue = result[2:]
	if len(binaryValue) == 15:
		binaryValue = '0' + result[2:]

	query={}
	query['binary']=binaryValue


	#create filename pattern
	with open('queries/temp.info') as json_file:
		json_data = json.load(json_file)

	#generate candidates file
	qId= json_data['name']
	o =  'opt' if json_data['opt']==u'Enabled' else 'nopt'
	c =  'warm' if json_data['cache']==u'Warm' else 'cold'

	if qId == 'CUSTOM':
		filename = 'temp.q'

	elif qId == 'BTC10':
		filename = 'q20.'+o+'.'+c

	elif qId == 'BTC11':
		filename = 'aux.q3.'+o+'.'+c

	DIR  = os.path.join(os.path.abspath(os.pardir))
	optDir = DIR+'/RIS/indexing/RIS.RUN/log/'
	optQuery = optDir+'*'+filename+'*'+'.rqmod/*'+binaryValue+'*.rqmod'



	for name in glob.glob(optQuery):
			print name
			optQuery = name

	with open(optQuery) as f:
		query['query']=f.read()

	return HttpResponse(json.dumps(query), content_type="application/json")

def getQueryCandidates(request):
	q = open('output/candidatedataindecimal.txt')
	candidates = {}
	i=0
	for line in q:
		candidates[i]=line.rstrip()
		i=i+1
	q.close
	jobj = {}
	jobj['candidates']=candidates

	return HttpResponse(json.dumps(jobj), content_type="application/json")


def getQueryTree(request):
	DIR  = os.path.join(os.path.abspath(os.pardir))
	imgFlag = request.GET['exec']
	if(imgFlag=='yes'):
		cmd = [ "javac "+DIR+"/RIS/antlr/sparql/*.java"]
		p = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
		p_stdout = p.stdout.read()
		p_stderr = p.stderr.read()
		print p_stderr


		cmd = [ "cd "+DIR+"/RIS/antlr/sparql ; pwd ; java org.antlr.v4.runtime.misc.TestRig Sparql query -ps "+DIR+"/RiQ/output/temp.ps "+DIR+"/RiQ/queries/temp.q"]
		p = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
		p_stdout = p.stdout.read()
		p_stderr = p.stderr.read()
		print p_stdout
		print p_stderr

		cmd = [ "convert  -background white -alpha remove "+DIR+"/RiQ/output/temp.ps "+DIR+"/RiQ/output/temp.png"]
		p = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
		p_stdout = p.stdout.read()
		p_stderr = p.stderr.read()
		print p_stdout
		print p_stderr


	try:
		with open(DIR+"/RiQ/output/temp.png", "rb") as f:
			return HttpResponse(f.read(), content_type="image/png")
	except IOError:
		img = Image.new('RGBA', (500, 300), (140,183,193,0))
		response = HttpResponse(content_type="image/jpeg")
		img.save(response, "JPEG")
		return response

def getOptimizedQueryTree(request):
	DIR  = os.path.join(os.path.abspath(os.pardir))
	imgFlag = 	request.GET['new']
	candidate = request.GET['cand']

	if(imgFlag=='yes'):
		binaryCandidate = str(bin(int(candidate.strip("Candidate"))))[2:]

		optDir = DIR+'/RIS/indexing/RIS.RUN/log/'
		optQuery = optDir+'dbpedia.g.q4.' + '0000001000000000'+'.rqmod'


		cmd = [ "cd "+DIR+"/RIS/antlr/sparql ; pwd ; java org.antlr.v4.runtime.misc.TestRig Sparql query -ps "+DIR+"/RiQ/output/tempOpt.ps "+optQuery]
		p = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
		p_stdout = p.stdout.read()
		p_stderr = p.stderr.read()
		print p_stdout
		print p_stderr



		cmd = [ "convert  -background white -alpha remove "+DIR+"/RiQ/output/tempOpt.ps "+DIR+"/RiQ/output/tempOpt.png"]
		p = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
		p_stdout = p.stdout.read()
		p_stderr = p.stderr.read()
		print p_stdout
		print p_stderr


	try:
		with open(DIR+"/RiQ/output/tempOpt.png", "rb") as f:
			return HttpResponse(f.read(), content_type="image/png")
	except IOError:
		img = Image.new('RGBA', (500, 300), (140,183,193,0))
		response = HttpResponse(content_type="image/jpeg")
		img.save(response, "JPEG")
		return response

def getParseTree(request):
	cand = request.GET['cand']
	if cand == 'false':

		# open visualize non-candidate graph data
		expression=''
		v = open('output/visualizenoncandidatedata.log')
		for line in v:
		   print line
		   expression = line
		   break
		v.close
		print 'Expression Tree: '+expression
		expression = replaceKeyWords(expression)
		print expression
		#expression = "( Root( LC ( MC ) ( RC ( RCLC:1 ) ( RCLC:0 ) ) ( ANC ( ANCLC ) ( ANCRC ( ANCRCGC ) ) ) ))"
		expression, nodeMap = parseExpression(expression)

		tree = toTree(expression)
		myjson=printTree(tree, tree[''][0], nodeMap, 1, None)
		data = json.dumps(myjson)

	else:
		#convert int to binary
		result = str(bin(int(cand.strip("Candidate"))))

		binaryValue = result[2:]
		print 'length of binaryValue: ' + str(len(binaryValue))
		if len(binaryValue) == 15:
			binaryValue = '0' + result[2:]

		print "binaryValue: " + binaryValue

		#assuming binary value is always in the file
		expression = ''
		v = open('output/visualizecandidatedata.log')
		for line in v:
		   if line.find(binaryValue) != -1:
			   print 'found matching candidate'
			   expression = next(v)
			   break
		v.close
		expression = replaceKeyWords(expression)
		print "expression: " + expression
		expression, nodeMap = parseExpression(expression)
		tree = toTree(expression)
		myjson=printTree(tree, tree[''][0], nodeMap, 1, None)
		data = json.dumps(myjson)

	return HttpResponse(data, content_type="application/json")

def replaceKeyWords(expression):
		expression = expression.replace(" UNION:0", " :0 (UNION:0)");
		expression = expression.replace(" UNION:1", " :1 (UNION:1)");
		expression = expression.replace(" FILTER:0", " :0 (FILTER:0)");
		expression = expression.replace(" FILTER:1", " :1 (FILTER:1)");
		expression = expression.replace(" OPTIONAL:0", " :0 (OPTIONAL:0)");
		expression = expression.replace(" OPTIONAL:1", " :1 (OPTIONAL:1)");
		return expression

def parseExpression(expression):
    nodeMap = dict()
    counter = 1
    node = ""
    retExp =""
    for char in expression:
        if char == '(' or char == ')' :
            if (len(node) > 0):
                nodeMap[str(counter)] = node;
                retExp += str(counter)
                counter +=1
            retExp += char
            node =""
        elif char == ' ': continue
        else :
            node += char
    return retExp,nodeMap

def toTree(expression):
    tree = dict()
    msg =""
    stack = list()
    for char in expression:
        if(char == '('):
            stack.append(msg)
            msg = ""
        elif char == ')':
            parent = stack.pop()
            if parent not in tree:
                tree[parent] = list()
            tree[parent].append(msg)
            msg = parent
        else:
            msg += char
    return tree

def printTree(tree, node, nodeMap, childIndex, parentNode):
	jsonstr = '{"name": "%s"' % (nodeMap[node])
	#print '{"name": "%s"' % (nodeMap[node])
	#print str(childIndex) + ' childIndex, node: ' + (nodeMap[node])
	if node in tree:
		#print ',"children" : ['
		jsonstr += ',"children" : ['
	else:
		if childIndex > 0:
			#print '},' #+ ' -----> childIndex = ' + str(childIndex)
			jsonstr += '},' #+ ' -----> childIndex = ' + str(childIndex)
		else:
			#print '}' #+ ' -----> childIndex = ' + str(childIndex)
			jsonstr += '}' #+ ' -----> childIndex = ' + str(childIndex)
		return jsonstr
	#print 'child count of the node for ' + nodeMap[node] + ' = ' + str(len(tree[node]))
	parent = node
	childIndex = len(tree[node])
	for child in tree[node]:
		childIndex = childIndex - 1
		jsonstr += printTree(tree, child, nodeMap, childIndex, parent)
	if node in tree:
		#print ']'
		jsonstr += ']'
	#need to check if nodeMap[node] is the last child of the parent. If not, then add comma
	parentNodeName = ''
	#print parentNode
	if parentNode is not None:
		parentNodeName = nodeMap[parentNode]
		#print 'parentNodeName: ' + parentNodeName
		#print 'length of parent node: ' + str(len(tree[parentNode]))
		#print 'index of node in parent list: ' + str(tree[parentNode].index(node))
		if len(tree[parentNode])-1 > tree[parentNode].index(node):
			#print '},'
			jsonstr += '},'
		else:
			#print '}'
			jsonstr += '}'
		#print ' ----> end of node ' + nodeMap[node] + '  parentNode = ' + parentNodeName
	else:
		#print '}'
		jsonstr += '}'
		#print 'THIS IS ROOT NODE OF THE TREE...!'
		#print jsonstr
	#print '}' + ' ----> end of node ' + nodeMap[node] + '  parentNode = ' + parentNodeName
	return jsonstr
