PREFIX ub: <file:///home/vsfgd/datasets/lubm/univ-bench.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT *  WHERE
{
	GRAPH ?g{
		?professor1 rdf:type  ub:FullProfessor .
		?professor1 ub:undergraduateDegreeFrom <http://www.University584.edu> .
		?professor1 ub:mastersDegreeFrom <http://www.University584.edu> .
		?professor1 ub:doctoralDegreeFrom <http://www.University429.edu> .
		?professor1 ub:worksFor ?univ2 .
		?professor1 ub:name ?name1 .
		?professor1 ub:emailAddress ?Email1 .
		?professor1 ub:telephone ?phone1 .
		?professor1 ub:researchInterest ?ResearchInt1 .
		?professor1 ub:teacherOf ?course1 .
		?publication1 ub:publicationAuthor ?professor1 .

		?professor2 rdf:type  ub:AssociateProfessor .
		?professor2 ub:undergraduateDegreeFrom <http://www.University584.edu> .
		?professor2 ub:mastersDegreeFrom <http://www.University584.edu> .
		?professor2 ub:doctoralDegreeFrom <http://www.University9999.edu> .
		?professor2 ub:worksFor ?univ2 .
		?professor2 ub:name ?name2 .
		?professor2 ub:emailAddress ?Email2 .
		?professor2 ub:telephone ?phone2 .
		?professor2 ub:researchInterest ?ResearchInt2 .
		?professor2 ub:teacherOf ?course2 .
		?publication2 ub:publicationAuthor ?professor2 .
	}
}
