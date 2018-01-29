SELECT  *
WHERE
  { GRAPH ?g
      { ?var6 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://dbpedia.org/ontology/PopulatedPlace> .
        ?var6 <http://dbpedia.org/ontology/abstract> ?var1 .
        ?var6 <http://www.w3.org/2000/01/rdf-schema#label> ?var2 .
        ?var6 <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?var3 .
        ?var6 <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?var4
        {   { ?var6 <http://www.w3.org/2000/01/rdf-schema#label> "Brunei"@en }
          UNION
            { { { ?var5 <http://dbpedia.org/property/redirect> ?var6 .
                  ?var5 <http://www.w3.org/2000/01/rdf-schema#label> "Brunei"@en
                  OPTIONAL
                    { ?var6 <http://xmlns.com/foaf/0.1/homepage> ?var10 }
                }
                OPTIONAL
                  { ?var6 <http://dbpedia.org/ontology/populationTotal> ?var12 }
              }
              OPTIONAL
                { ?var6 <http://dbpedia.org/ontology/thumbnail> ?var14 }
            }
        }
      }
  }
