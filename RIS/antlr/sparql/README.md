# Antlr

## Install
```
cd /usr/local/lib
wget http://antlr.org/download/antlr-4.4-complete.jar
export CLASSPATH=".:/usr/local/lib/antlr-4.4-complete.jar:$CLASSPATH"
alias antlr4='java -jar /usr/local/lib/antlr-4.4-complete.jar'
alias grun='java org.antlr.v4.runtime.misc.TestRig'
```

## Compile
```
cd RIQ/antlr/sparql
antlr4 SparqlParser.g4 SparqlLexer.g4
javac *.java
```

## Run
```
grun Sparql query -ps btc.q1.ps btc.q1
```

## Convert ps to png
```
convert btc.q1.ps btc.q1.png
```
