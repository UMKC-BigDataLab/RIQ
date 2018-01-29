PREFIX ub: <file:///home/vsfgd/datasets/lubm/univ-bench.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT ?x ?y ?z WHERE {
	GRAPH ?g {
		?x rdf:type ub:GraduateStudent .
		?y rdf:type ub:AssistantProfessor .
		?z rdf:type ub:GraduateCourse .
		?x ub:advisor ?y .
		?y ub:teacherOf ?z .
		?x ub:takesCourse ?z .
	}
}
