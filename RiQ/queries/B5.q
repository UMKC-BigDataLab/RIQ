PREFIX fbase: <http://rdf.freebase.com/ns/>

SELECT * WHERE
{
	GRAPH ?g {
		?filmPerformance fbase:film.performance.actor ?actor .
		?filmPerformance fbase:film.performance.film ?film .
		?film fbase:type.object.name ?name .
		?film fbase:film.film.initial_release_date ?initialReleaseDate .
		?film fbase:film.film.language ?lang .
		?film fbase:film.film.genre ?genre .
	}
}
