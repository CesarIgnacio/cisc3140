#!/bin/bash

clear

echo ""
echo "Enter 9 numbers in 9 separate lines."

total=0
num=0

for ((a=1; a <= 9; a++))
do
	read num
	total="$((total+num))"
done

if [ "$total" > "10" ]
then
	total="$((total+10))"
	echo Becuse the sum of these numbers is grater than 10 we added 10 more
fi

echo The final total is equal to: $total

echo "The end"
