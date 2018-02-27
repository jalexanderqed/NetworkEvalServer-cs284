#!/bin/bash

mvn compile && mvn exec:java -Dexec.mainClass="main.java.ninja.jalexander.${1}"
