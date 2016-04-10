from django.conf.urls import patterns, include, url

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
	url(r'^$', 'linked.views.land'),
	url(r'^getquery/$', 'linked.views.getQueryList'),
	url(r'^getstatus/$', 'linked.views.getStatus'),
	url(r'^getresults/$', 'linked.views.getResults'),
	url(r'^gettimings/$', 'linked.views.getTimings'),
)
