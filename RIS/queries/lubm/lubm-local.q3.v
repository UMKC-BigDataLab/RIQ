# Q3 (BIG: 21 joins)
# Querying for two colleagues (professors) who also studied in the same college

SELECT ?professor1 ?univ2 ?name1 ?Email1 ?phone1 ?ResearchInt1 ?course1 ?publication1 ?professor2 ?name2 ?Email2 ?phone2 ?ResearchInt2
WHERE {
GRAPH ?g {
    ?professor1 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>  <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#FullProfessor> .

    ?professor1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#undergraduateDegreeFrom> <http://www.University584.edu> .
    ?professor1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#mastersDegreeFrom> <http://www.University584.edu> .

    ?professor1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#doctoralDegreeFrom> <http://www.University429.edu> .

    ?professor1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#worksFor> ?univ2 .
    ?professor1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#name> ?name1 .
    ?professor1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#emailAddress> ?Email1 .
    ?professor1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#telephone> ?phone1 .
    ?professor1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#researchInterest> ?ResearchInt1 .
    ?professor1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#teacherOf> ?course1 .
    ?publication1 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#publicationAuthor> ?professor1 .

    ?professor2 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>  <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#AssociateProfessor> .
    ?professor2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#undergraduateDegreeFrom> <http://www.University584.edu> .
    ?professor2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#mastersDegreeFrom> <http://www.University584.edu> .
    ?professor2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#doctoralDegreeFrom> <http://www.University9999.edu> .

    ?professor2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#worksFor> ?univ2 .
    ?professor2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#name> ?name2 .
    ?professor2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#emailAddress> ?Email2 .
    ?professor2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#telephone> ?phone2 .
    ?professor2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#researchInterest> ?ResearchInt2 .
    ?professor2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#teacherOf> ?course2 .
    ?publication2 <http://www.lehigh.edu/home/vsfgd/datasets/lubm/univ-bench.owl#publicationAuthor> ?professor2 .
}
}
