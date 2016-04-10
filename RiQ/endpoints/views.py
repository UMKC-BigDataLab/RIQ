from django.http import HttpResponse
import sys
from run.views import runMultiToolQuery, getQueryResults
def land(request):
	print 'Received Request'
	toolstr = '100'
	if request.method == 'GET':
                        if 'tools' in request.GET:
                                        toolstr = str(request.GET['tools'])

                        query =  request.GET['query']
                        print 'RIQ ENDPOINT RECEIVED GET:'
                        
        elif request.method == 'POST':
                        if 'tools' in request.POST:
                                        toolstr = str(request.POST.get(['tools']))

                        query =  request.POST.get(['query'])
                        print 'RIQ ENDPOINT RECEIVED POST:'
        print query
	result ='error'

       	try:
                                tools=[]
                               	if(toolstr[0]=='1'):
                                                tools.append(True)
                                else:
                                                tools.append(False)
                                if(toolstr[1]=='1'):
                                                tools.append(True)
                                else:
                                                tools.append(False)
                                if(toolstr[2]=='1'):
                                                tools.append(True)
                                else:
                                                tools.append(False)
                                
                                print 'Issuing Multi-tool Query..'
                                result = runMultiToolQuery("tempLinked.q",query,'-c cold -O',tools)
                                print 'Multi-tool Query Finished!'
				print 'Results: '
				print result
        except Exception, err:
                        print sys.exc_info()[0]

        if (result.startswith("error")):
                                output = 'Endpoint Error'
				print output
        else:
				if tools[2]==True:
                                	output = getQueryResults('tempLinked.q','virt','cold')			
				elif tools[1]==True:
                                	output = getQueryResults('tempLinked.q','jena','cold')			
                                else:
					output = getQueryResults('tempLinked.q','riq','cold')			
				
	return HttpResponse(content=output,content_type='xml; charset=utf-8')

