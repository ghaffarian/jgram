# jGraM (Java Graph Mining) Library

jGraM is a [Java](https://en.wikipedia.org/wiki/Java_(programming_language)) library for Graph-Mining. Graph mining is the task of recognizing and extracting 
interesting patterns from a database of graphs. Graph mining includes many techniques and sub-areas, where implementation of some of the most important algorithms is provided in this library. The main sub-areas of graph-mining that are targeted in jGraM are:
 - Graph Matching
 - Frequent Subgraph Mining
 - Graph Clustering
 - Graph Kernels
 - Graph Learning

jGraM is based on the [JGraphT](https://jgrapht.org) library for Java. 
jGraM can take graphs as input in several forms, including:
 - [Graph objects](https://jgrapht.org/javadoc/org/jgrapht/Graph.html) of JGraphT
 - [DOT](https://en.wikipedia.org/wiki/DOT_\(graph_description_language\)) files
 - [JSON](https://en.wikipedia.org/wiki/JSON) files
 - Graph Databases (e.g. [Neo4J](https://neo4j.com))

jGraM is intended to fully support output formats of the [PROGEX tool](https://github.com/ghaffarian/progex).

The development of PROGEX is done using the [Netbeans IDE](https://netbeans.org), 
with an [Apache Maven](https://maven.apache.org) project structure.

This is an internal project by members of our lab from [Amirkabir University of Technology](http://aut.ac.ir), 
as a sub-project of the PhD thesis of [Seyed Mohammad Ghaffarian](http://linkedin.com/in/smghaffarian).


## Why We Developed jGraM?

Surprisingly, searching the Internet for an open-source graph-mining library in a popular 
programming-language had no result! Since graph-mining algorithms were a requirement for 
the experiments of my PhD thesis, I had no choice but to start creating a suitable graph-mining 
library by myself.
