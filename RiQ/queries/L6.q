PREFIX ub: <file:///home/vsfgd/datasets/lubm/univ-bench.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT ?x ?y ?z WHERE {
	GRAPH ?g {
		?x rdf:type ub:UndergraduateStudent .
		?y rdf:type ub:Department .
		?x ub:memberOf ?y .
		?y ub:subOrganizationOf <http://www.University0.edu> .
		?x ub:emailAddress ?z .
	}
}
