PREFIX ub: <file:///home/vsfgd/datasets/lubm/univ-bench.owl#> 
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 

SELECT * WHERE { 
	GRAPH ?g { 
		?x rdf:type ub:UndergraduateStudent . 
		?y rdf:type ub:University .
		?z rdf:type ub:Department . 
		?x ub:memberOf ?z .
		?z ub:subOrganizationOf ?y .
		?x ub:undergraduateDegreeFrom <http://University0.edu> . 
	}
}

