#!/bin/bash
clear
echo Your current direction
echo $(pwd)
echo Enter a path
read path
#echo Entering directory
line=$(find -mindepth 1 -type d | wc -l)

echo Number of directories: $line
cd d1
mkdir cesar
rm -d cesar
