PREFIX dbp: <http://dbpedia.org/property/>
PREFIX dbpedia: <http://dbpedia.org/resource/>
PREFIX dbo: <http://dbpedia.org/ontology/>

SELECT ?spouse WHERE {
    dbpedia:William_Shakespeare dbo:child ?child.
    ?child dbp:spouse ?spouse.
}
