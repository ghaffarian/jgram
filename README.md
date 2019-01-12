# JGRAM (Java Graph Mining) Library

JGRAM is a [Java](https://en.wikipedia.org/wiki/Java_(programming_language)) library for *Graph-Mining*. Graph-Mining is the task of recognizing and extracting 
interesting patterns from a database of graphs. Graph-Mining differs from data-mining in the sense that data-mining is only concerned with patterns in data values, while graphs are connected and structured-data, and the structure is as important as data-values themself.

Graph-Mining includes many techniques and sub-areas, where implementation of some of the most important algorithms will be provided in this library. The main sub-areas of graph-mining that are targeted in JGRAM are:
 - Graph Matching
 - Frequent Subgraph Mining
 - Graph Clustering
 - Graph Kernels
 - Graph Learning

JGRAM is based on the [Graphs library](https://github.com/ghaffarian/graphs) (a graph library in Java) and can process graph objects from that library, produced by any other program (such as [PROGEX](https://github.com/ghaffarian/progex)). JGRAM can also take graphs as input in several external formats; including:
 - [DOT](https://en.wikipedia.org/wiki/DOT_\(graph_description_language\)) files (output from PROGEX)
 - [JSON](https://en.wikipedia.org/wiki/JSON) files (output from PROGEX)
 - Graph Databases (e.g. [Neo4J](https://neo4j.com))

This is an internal project by members of our lab from [Amirkabir University of Technology](http://aut.ac.ir), as a sub-project of the PhD thesis of [Seyed Mohammad Ghaffarian](http://linkedin.com/in/smghaffarian). Seyed Mohammad Ghaffarian is the owner, designer, and main developer of JGRAM.

The development of JGRAM is done using the [Netbeans IDE](https://netbeans.org), with an [Apache Maven](https://maven.apache.org) project structure, under the terms of the [LGPL-v3.0](https://www.gnu.org/licenses/lgpl-3.0.en.html) license.


## Why We Developed JGRAM?

Surprisingly, searching the Internet for an open-source graph-mining library in a popular 
programming-language (such as Java or Python) which includes algorithms of the aforementioned areas (such as subgraph-mining and graph-clustering) had no results! Since graph-mining algorithms were a requirement for the experiments of my PhD thesis, I had no choice but to start creating a suitable graph-mining library by myself.


## Getting Started

> **JGRAM IS STILL IN EARLY DEVELOPMENT STAGE** and *NOT SUITABLE TO BE USED IN ANY MANNER*. \
> As soon as we publish a usable release, this section will be updated with proper instructions.
