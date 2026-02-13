#!/bin/bash

echo "Starting Test Execution..."

mvn clean test -DsuiteXmlFile=master.xml

echo "Execution Completed."

