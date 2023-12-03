#!/bin/bash

for ttfw in {1..10} 20
do
  for ss in $(seq -f "%0.2f" 0.2 0.2 2)
  do
    echo $ttfw $ss
    qsub -v timeToFinishWeight=$ttfw,searchSteps=$ss job-script.sh
  done
done