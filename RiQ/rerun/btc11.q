PREFIX dc: <http://purl.org/dc/elements/1.1/> 
PREFIX foaf: <http://xmlns.com/foaf/0.1/> 
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> 
PREFIX space: <http://purl.org/net/schemas/space/> 
PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> 
PREFIX dbpedia-prop: <http://dbpedia.org/property/> 
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> 
 
SELECT * 
WHERE { 
     GRAPH ?g { 
          ?var5 dbpedia-owl:thumbnail ?var4 . 
          ?var5 rdf:type dbpedia-owl:Person . 
          ?var5 rdfs:label ?var . 
          ?var5 foaf:page ?var8 . 
          OPTIONAL { ?var5 foaf:homepage ?var10 . } 
          } 
} 

