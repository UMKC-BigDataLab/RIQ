PREFIX ub: <file:///home/vsfgd/datasets/lubm/univ-bench.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT ?x ?y ?z WHERE {
	GRAPH ?g {
		?y ub:teacherOf ?z .
		?y rdf:type ub:FullProfessor .
		?z rdf:type ub:Course .
		?x ub:advisor ?y .
		?x rdf:type ub:UndergraduateStudent .
		?x ub:takesCourse ?z .
	}
}
