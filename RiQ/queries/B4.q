PREFIX fbase: <http://rdf.freebase.com/ns/>

SELECT * WHERE
{
	GRAPH ?g {
		?sub fbase:location.location.events ?x .
		?sub fbase:location.location.geolocation ?y .
		?sub fbase:location.location.people_born_here ?z .
		?sub fbase:location.location.people_born_here ?w .
		?sub fbase:location.location.containedby ?t .
	}
}
