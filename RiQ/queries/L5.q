PREFIX ub: <file:///home/vsfgd/datasets/lubm/univ-bench.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT * WHERE {
	GRAPH ?g{
		?x rdf:type ub:FullProfessor .
		?y rdf:type ub:Department .
		?x ub:worksFor ?y .
		?y ub:subOrganizationOf <http://www.University0.edu> .
	}
}
