# RIQ dependencies

## Raptor (RDF parsing)

```
apt-get install libraptor2-dev
```

## Rasqal (SPARQL parsing)

* If using package
```
apt-get install librasqal3-dev
```
* If building from GitHub (latest code)
```
apt-get install automake libtool gtk-doc-tools flex bison
```
GitHub code requires Bison 3
```
wget http://ftp.gnu.org/gnu/bison/bison-3.0.2.tar.gz
tar zxvf *.tar.gz
cd bison*
./confgiure
make
sudo make install
```

## Google's sparse_hash_map (used with LSH)
```
apt-get install libsparsehash-dev
```

## Berkeley DB (for indexing only)

* Use static lib from repo
* Otherwise (might not work with some versions)
```
apt-get install libdb++-dev
```

## ~~hashlibpp~~
```
cd RIS/indexing/hashlibpp/trunk/src
make
```

## System settings

Edit
```
vi /etc/security/limits.conf
```

limits.conf:
```
*               soft    nofile          8192
*               hard    nofile          8192
```

Log out and verify:
```
ulimit -n
```
