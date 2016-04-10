# Jena TDB (API)

Using Jena TDB (API) for querying multiple TDB indices at the same time

## Compile

* Single-threaded
```
javac  -Djava.ext.dirs=libs edu/umkc/sce/dbis/anask/Querier.java
```

* Multi-threaded

```
javac  -Djava.ext.dirs=libs edu/umkc/sce/dbis/anask/*.java
```

## Create a manifest file (see MANIFEST\* example files)

## Create jar file

* Single-threaded

```
jar -cvfm querier.jar MANIFEST.MF edu/umkc/sce/dbis/anask/Querier.class

```

* Multi-threaded

```
jar -cvfm querier_t.jar MANIFEST_T.MF edu/umkc/sce/dbis/anask/TQ*.class \
edu/umkc/sce/dbis/anask/Exec*.class edu/umkc/sce/dbis/anask/S*.class
```

## Running

```
java -jar querier.jar -h
```

```
java -Dlog4j.configuration=file:jena-log4j.properties -jar querier.jar \
-results xml -index ~/RIS/indexing/RIS.RUN/index/GRAPHIDS.tdb/ \
-query ~/RIS/queries/var/foaf.q1 -candidates foaf.q1.candidates \
-optqueries ~/RIS/indexing/RIS.RUN/log/foaf.q1.rqmod
```
