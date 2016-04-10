# Virtuoso DB Querier

Notes:

	There are to programs*:
		1. Virtuoso database(s) starter: responisble for configuring and starting a set of
			databases.
		2. Virtuoso querier: responisble for querying the set of runing databases.



	[killall virtuoso-t]*

	* Note: the programs do not stop the virtuoso db instances.
			The user should issue the command [killall virtuoso-t] when done using the
			querier.


## Compile

```
javac  -Djava.ext.dirs=libs edu/umkc/sce/dbis/anask/*.java
```

```
javac  -Djava.ext.dirs=libs eduDBS/umkc/sce/dbis/anask/*.java
```

## Create a manifest file (see MANIFEST\* example files)

## Create jar file

```
jar -cvfm querier_virt.jar MANIFEST.MF edu/umkc/sce/dbis/anask/*.class
```

```
jar -cvfm db_starter.jar MANIFEST_DBS.MF eduDBS/umkc/sce/dbis/anask/*.class
```

## Running

```
java -jar querier_virt.jar -h

```

```
java -jar db_starter.jar -index /usr/local/dbs -candidates cnds.txt

java -jar db_starter.jar -index /usr/local/Cellar/virtuoso/7.1.0/var/lib/virtuoso/db -candidates /Users/anask/Downloads/RIQ/Data10/dir/ndF.txt

```


```
java -jar querier_virt.jar -vc -results csv -index /dbs -candidates /dir/cnds.txt -query q1.q -t 2


java -jar querier_virt.jar -c -results csv -index /usr/local/Cellar/virtuoso/7.1.0/var/lib/virtuoso/db -candidates /Users/anask/Downloads/RIQ/Data10/dir/ndF.txt -query /Users/anask/Downloads/RIQ/Data10/nq1.q -t 2
```

