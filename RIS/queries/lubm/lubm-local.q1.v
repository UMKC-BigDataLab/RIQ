# Q1-mod (BIG: 18 joins)
# Querying for a professor who is an advisor to 2 students and has a publication
# with one of them

#PREFIX ub: <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#>
#PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT ?professor ?course ?email ?phone ?research ?msUnv ?phdUnv ?student1name ?student2name ?student1 ?student2 ?publication
WHERE {
GRAPH ?g {
   # List of students
   ?student1 <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#advisor> ?professor .
   ?student1 <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#name> ?student1name .
   ?student1 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#UndergraduateStudent> .

   ?student2 <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#advisor> ?professor .
   ?student2 <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#name> ?student2name .
   ?student2 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#GraduateStudent> .

   # Professor details
   ?professor <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#FullProfessor> .
   #?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#name> ?profname .
   ?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#name> "FullProfessor7" .
   ?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#teacherOf> ?course.
   ?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#undergraduateDegreeFrom> ?undergradUnv  .
   ?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#mastersDegreeFrom> ?msUnv .
   ?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#doctoralDegreeFrom> ?phdUnv .
   #?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#worksFor> ?profUnv .
   ?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#worksFor> <http://www.Department17.University1001.edu> .
   ?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#emailAddress> ?email .
   ?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#telephone> ?phone .
   ?professor <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#researchInterest> ?research .

   # Publication
   ?publication <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#publicationAuthor> ?professor .
   ?publication <http://l1/home/vsfgd/datasets/lubm/univ-bench.owl#publicationAuthor> ?student2 .
}
}
