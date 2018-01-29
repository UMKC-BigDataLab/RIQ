PREFIX geo:<http://aims.fao.org/aos/geopolitical.owl#>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT * WHERE
{
	GRAPH ?g {
		?union geo:nameShortEN ?unionShortName .
		?union geo:hasMember ?country1 .
		?union rdf:type geo:economic_region .

		?continent1 geo:hasMember ?country1 .
		?continent1 rdf:type geo:geographical_region .
		?continent1 geo:nameShortEN "Africa"^^<http://www.w3.org/2001/XMLSchema#string> .

		?continent2 geo:hasMember ?country2 .
		?continent2 rdf:type geo:geographical_region .
		?continent2 geo:nameShortEN "Asia"^^<http://www.w3.org/2001/XMLSchema#string> .

		?country1 geo:nameOfficialEN ?officialName1 .
		?country1 geo:isInGroup ?union .
		?country1 geo:isInGroup ?continent1 .
		?country1 geo:isInGroup geo:World .
		?country1 rdf:type geo:self_governing .
		?country1 geo:hasBorderWith ?country2 .

		?country2 geo:nameOfficialEN ?officialName2 .
		?country2 geo:isInGroup ?continent2 .
		?country2 geo:isInGroup geo:World .
		?country2 rdf:type geo:self_governing .
		?country2 geo:hasBorderWith ?country1 .
	}
}
