from django.conf.urls import patterns, include, url

urlpatterns = patterns('',
	url(r'^$', include('home.urls')),
	url(r'^(?i)home/', include('home.urls')),
	url(r'^(?i)index/', include('indexinfo.urls')),
	url(r'^(?i)execute/', include('execute.urls')),
	url(r'^(?i)linked/', include('linked.urls')),
	url(r'^(?i)visualize/', include('visualize.urls')),
	url(r'^(?i)endpoints/', include('endpoints.urls')),
)



