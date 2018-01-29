PREFIX ub: <file:///home/vsfgd/datasets/lubm/univ-bench.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT *
WHERE {
	GRAPH ?g {
		?student1 ub:undergraduateDegreeFrom ?undergradUni .    
		?student1 ub:memberOf ?dept .   
		?student2 ub:undergraduateDegreeFrom ?undergradUni .    
		?student1 ub:advisor ?professor .
		?publication ub:publicationAuthor ?student1 .   
		?publication ub:publicationAuthor ?student2 . 
		?publication ub:publicationAuthor ?professor. 
		?professor ub:name "AssociateProfessor5" .
		?professor ub:telephone ?tpnu . 
		?professor ub:emailAddress ?emAddr . 
		?professor ub:undergraduateDegreeFrom ?bsdg . 
		?professor ub:teacherOf ?course . 
		?professor ub:worksFor ?dept . 
		?professor ub:researchInterest ?researchInt .
		?professor ub:mastersDegreeFrom ?msdg .
		?professor ub:doctoralDegreeFrom ?phddg .
		?student1 rdf:type  ub:GraduateStudent .   
		?dept rdf:type ub:Department .   
		?dept ub:subOrganizationOf <http://www.University10.edu> .    
		?student2 rdf:type ub:GraduateStudent . 
		?undergradUni rdf:type ub:University .    
		?publication rdf:type ub:Publication .   
		?student2 ub:memberOf ?dept .   
	}
}
