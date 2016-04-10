# Query2

#PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
#PREFIX ub: <http://www.lehigh.edu/home/vsfgd/univ-bench.owl#>
#PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>

SELECT ?student1 ?student2 ?publication ?undergradUni ?dept
WHERE {
GRAPH ?g {
  ?student1 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#GraduateStudent> .
  ?student1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#undergraduateDegreeFrom> ?undergradUni .

  ?student1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#memberOf> ?dept .
  ?dept <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#Department> .
  ?dept <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#subOrganizationOf> <http://www.University1167.edu> .


  ?student2 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#GraduateStudent> .
  ?student2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#undergraduateDegreeFrom> ?undergradUni .

  ?undergradUni <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#University> .

  ?publication <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#Publication> .
  ?publication <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#publicationAuthor> ?student1 .
  ?publication <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#publicationAuthor> ?student2 .
}
}
