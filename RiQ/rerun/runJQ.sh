#!/bin/bash

qfile=$1

echo 'dbis.504' | sudo -S echo "Processing "$qfile

sudo sh -c  " sync; echo 3 > /proc/sys/vm/drop_caches"

/usr/bin/time -v sudo /home/vsfgd/Jena/apache-jena-2.11.1/bin/tdbquery --loc /mnt/data2/datasets/btc-2012-split-clean/btc-2012-split-clean.nq.tdb --file $qfile --results xml &> output/"$qfile".cold.txt 

echo 'dbis.504' | sudo -S echo -n "" $qfile

/usr/bin/time -v sudo /home/vsfgd/Jena/apache-jena-2.11.1/bin/tdbquery --loc /mnt/data2/datasets/btc-2012-split-clean/btc-2012-split-clean.nq.tdb --file $qfile --results xml &> output/"$qfile".warm.txt 
 

echo ''
