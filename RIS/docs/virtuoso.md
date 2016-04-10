# Virtuoso

Follow [Setting up a local DBpedia 2014 mirror with Virtuoso 7.1.0](https://joernhees.de/blog/2014/11/10/setting-up-a-local-dbpedia-2014-mirror-with-virtuoso-7-1-0/)

## Config

* Path
`/var/lib/virtuoso/db/virtuoso.ini`

* Changes
```
; settings for 16GB RAM on jafar/aladdin

; 1/4th of NumberOfBuffers
MaxCheckpointRemap = 340000
NumberOfBuffers = 1360000
MaxDirtyBuffers = 1000000
; speed up loading (indexing)
ShortenLongURIs = 1
DirsAllowed = ., /usr/share/virtuoso/vad, /usr/local/data/datasets
```

## Running

* Start/stop (when running RIQ/TDB, etc.)
```
sudo /etc/init.d/virtuoso-opensource start
sudo /etc/init.d/virtuoso-opensource stop
```

## Indexing

* Generate list of files to load
```
for g in * ; do echo "ld_dir_all('$(pwd)/$g', '*.*', 'http://$g');" ; done >
~/tmp/lddirall.isql
```

* Load files for indexing
```
isql 1111 dba password errors=stdout < ~/tmp/lddirall.isql > ~/tmp/lddirall.log
```

* Check if scheduled for loading
```
isql
SELECT * FROM DB.DBA.LOAD_LIST;
SELECT ll_graph, ll_file FROM DB.DBA.LOAD_LIST;
```

* Start indexing
```
isql
# takes a very long time!
rdf_loader_run();
checkpoint;
commit WORK;
checkpoint;
EXIT;
```

### RIQ integration

```
cd ~/RIS/indexing/RIS.RUN/index/GRAPHIDS.vrt.n3
for i in `ls ../GRAPHIDS.n3`; do mkdir `basename $i .nq`; done
for i in `ls ../GRAPHIDS.n3`; do ln -s \
/home/vsfgd/RIS/indexing/RIS.RUN/index/GRAPHIDS.n3/$i ./`basename $i .nq`/; done
```

## Querying

```
isql 1111 dba password errors=stdout < query > query.log
```

