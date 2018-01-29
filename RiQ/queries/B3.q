PREFIX fbase: <http://rdf.freebase.com/ns/>

SELECT * WHERE
{
	GRAPH ?g {
		?person1 fbase:people.place_lived.person ?personName1 .
		?person1 fbase:people.place_lived.location ?location .
		?person2 fbase:people.place_lived.person ?personName2 .
		?person2 fbase:people.place_lived.location ?location .
		?location fbase:location.location.containedby fbase:en.iraq .
	}
}
