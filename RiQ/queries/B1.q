PREFIX opar:<http://www.openarchives.org/ore/terms/>
PREFIX collect: <http://purl.org/collections/nl/am/> 

SELECT * WHERE
{
	GRAPH ?g {

		?sub0 collect:acquisitionDate "1980-05-16" .
		?sub0 collect:acquisitionMethod collect:t-14382 .
		?sub0 collect:associationSubject ?obj0 .
		?sub0 collect:contentMotifGeneral collect:t-8782 .
		?sub0 collect:creditLine collect:t-14773 .
		?sub0 collect:material collect:t-3249 .
		?sub0 collect:objectCategory collect:t-15606 .
		?sub0 collect:objectName collect:t-10444 .
		?sub0 collect:objectNumber "KA 17150" .
		?sub0 collect:priref "23182" .
		?sub0 collect:productionDateEnd "1924" .
		?sub0 collect:productionDateStart "1924" .
		?sub0 collect:productionPlace collect:t-624 .
		?sub0 collect:title "Plate commemorating the first Amsterdam-Batavia flight"@en .
		?sub0 opar:proxyFor collect:physical-23182 .
		?sub0 opar:proxyIn collect:aggregation-23182 .
		?sub0 collect:relatedObjectReference ?sub1 .
		?sub1 collect:relatedObjectReference ?sub0 .
	}
}
