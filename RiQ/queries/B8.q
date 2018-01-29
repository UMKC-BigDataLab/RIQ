PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX onto: <http://dbpedia.org/ontology/>

SELECT * WHERE {
	GRAPH ?g {
		?var6 a onto:PopulatedPlace .
		?var6 onto:abstract ?var1 .
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
			OPTIONAL { ?var6 foaf:depiction ?var8 .}
			OPTIONAL { ?var6 foaf:homepage ?var10 }
			OPTIONAL { ?var6 onto:populationTotal ?var12 }
			OPTIONAL { ?var6 onto:thumbnail ?var14 }
		}
	}
}
