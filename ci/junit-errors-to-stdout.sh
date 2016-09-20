#!/bin/bash

source ./ci/common.sh

if [ "$TRAVIS" = "true" ]; then
  echo 'Installing xml-twig-tools and xsltproc....'
  sudo apt-get install -qq -y --force-yes xml-twig-tools xsltproc > /dev/null
fi

echo 'Formatting results...'
FILES=$(find "$WORKDIR" -path '*/target/*-reports/junitreports/*.xml' | xargs --no-run-if-empty xml_grep --files --cond 'testsuite[@failures > 0 or @errors > 0]')
if [ -n "$FILES" ]; then
  for file in $FILES; do
    echo "Formatting $file"
    if [ -f "$file" ]; then
      echo '====================================================='
      xsltproc "./ci/junit-xml-format-errors.xsl" "$file"
    fi
  done
  echo -e '\n====================================================='
fi
