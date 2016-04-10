from django.conf.urls import patterns, include, url

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
	url(r'^$', 'execute.views.land'),
	url(r'^getquery/$', 'execute.views.getQueryList'),
	url(r'^timings/$', 'execute.views.getTimings'),
	url(r'^results/$', 'execute.views.getResults'),
	url(r'^graph/$', 'execute.views.getQueryGraph'),
	url(r'^getstatus/$', 'execute.views.getStatus'),
)
