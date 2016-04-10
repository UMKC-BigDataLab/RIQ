#!/bin/bash

echo 'Clearing Cache'
echo 3 >/proc/sys/vm/drop_caches
