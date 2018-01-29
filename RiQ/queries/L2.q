PREFIX ub: <file:///home/vsfgd/datasets/lubm/univ-bench.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT * WHERE
{
	GRAPH ?g{
		?student1 ub:advisor ?professor .
		?student1 ub:name ?student1name .
		?student1 rdf:type ub:UndergraduateStudent .

		?student2 ub:advisor ?professor .
		?student2 ub:name ?student2name .
		?student2 rdf:type ub:GraduateStudent .

		?professor rdf:type ub:FullProfessor .
		?professor ub:name "FullProfessor7" .
		?professor ub:teacherOf ?course.
		?professor ub:undergraduateDegreeFrom ?undergradUnv  .
		?professor ub:mastersDegreeFrom ?msUnv .
		?professor ub:doctoralDegreeFrom ?phdUnv .
		?professor ub:worksFor <http://www.Department17.University1001.edu> .
		?professor ub:emailAddress ?email .
		?professor ub:telephone ?phone .
		?professor ub:researchInterest ?research .

		?publication ub:publicationAuthor ?professor .
		?publication ub:publicationAuthor ?student2 .
	}
}
