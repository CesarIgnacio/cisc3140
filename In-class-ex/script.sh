#! /bin/bash

i=Bob

echo $i

j=Bob$i$i

echo $j

echo $j i $i John Doe

i="John Doe"
j="It's Wednesday"
k='He said, "Hello World"'

echo $i
echo $j
echo $k
echo ""

echo "$i"
echo '$i'
echo ""

echo 'They say, "Dreams don'"'t work unless you do."'"'

dq='"'
foo="Dreams don't work unless you do."
echo They say, $dq$foo$dq

for f in "*.csv"; do
	wc -l $f;
done

