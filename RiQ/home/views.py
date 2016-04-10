from django.shortcuts import render
from django.shortcuts import render_to_response
from django.http import HttpResponse
from datetime import datetime
from dateutil import tz

def home(request):

	from_zone = tz.tzutc()
	to_zone = tz.gettz('America/Chicago')

	utc = datetime.utcnow()
	utc = utc.replace(tzinfo=from_zone)
	central = utc.astimezone(to_zone)

	f = open('alog.txt','a')
	f.write(str(central)+'\n')
	f.close()

	return render_to_response('home.html', { 'TITLE': 'RIQ'})


