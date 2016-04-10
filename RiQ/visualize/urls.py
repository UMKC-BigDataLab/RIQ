from django.conf.urls import patterns, include, url

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
	url(r'^$', 'visualize.views.land'),
	url(r'^info/$', 'visualize.views.getQueryInfo'),
	url(r'^candidates/$', 'visualize.views.getQueryCandidates'),
	url(r'^querytree/$', 'visualize.views.getQueryTree'),
	url(r'^candidatetree/$', 'visualize.views.getOptimizedQueryTree'),
	url(r'^query/$', 'visualize.views.getQuery'),
	url(r'^candquery/$', 'visualize.views.getCandQuery'),
	url(r'^parse/$', 'visualize.views.getParseTree'),
)
