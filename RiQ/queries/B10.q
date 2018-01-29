PREFIX res: <http://dbpedia.org/resource/>
PREFIX onto: <http://dbpedia.org/ontology/>

SELECT *
WHERE {
	GRAPH ?g {
		{ ?city onto:areaLand ?area .
		?city onto:areaCode ?code . }
		UNION
		{ ?city onto:timeZone ?zone .
		?city onto:abstract ?abstract . }
		?city onto:country res:United_States .
		?city onto:postalCode ?postal .
		OPTIONAL { ?city onto:areaWater ?water . }
		OPTIONAL { ?city onto:populationTotal ?popu . }
	}
}
