#!/bin/bash

for ss in {1..10}
do
  for ws in $(seq -f "%0.2f" 100 10 200)
  do
    echo $ws $ss
    qsub -v windowSize=$ws,searchSteps=$ss p2-job-script.sh
  done
done