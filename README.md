# `RIQ`: RDF Indexing on Quadruples

## Summary

`RIQ` is a new approach for fast processing of SPARQL queries on large
datasets containing RDF quadruples (or quads). 
(These queries are also called named graph queries.)
`RIQ` employs a *decrease-and-conquer*
strategy: Rather than indexing the entire RDF dataset, `RIQ` identifies
groups of similar RDF graphs and indexes each group separately. During
query processing, `RIQ` uses novel filtering index to first identify
candidate groups that may contain matches for the query. On these
candidates, it executes optimized queries using a conventional SPARQL
processor (e.g., Jena TDB) to produce the final results.

## Publications

* Anas Katib, Vasil Slavov, Praveen Rao. ``RIQ: Fast Processing of SPARQL
Queries on RDF Quadruples." In the Journal of Web Semantics (JWS), Elsevier,
http://dx.doi.org/10.1016/j.websem.2016.03.005, 2016. (in press)

* Vasil Slavov, Anas Katib, Praveen Rao, Srivenu Paturi, Dinesh
Barenkala. ``Fast Processing of SPARQL Queries on RDF Quadruples."
[*Proceedings of the 17th International Workshop on the Web and
Databases*](http://webdb2014.eecs.umich.edu/) (**WebDB 2014**),
Snowbird, UT, June 2014.
([paper](http://r.web.umkc.edu/raopr/riq-webdb2014.pdf, http://arxiv.org/pdf/1506.01333v1.pdf))


## Acknowledgments

This work was supported by the National Science Foundation under Grant No. 1115871.
