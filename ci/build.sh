#!/bin/bash

source ./ci/common.sh

if [ -n "$RUN_ITS" ]; then
  info "Running unit and IT tests..."
  (mvn -Pclover.all -DskipITs=false -q install &> $WORKDIR/target/tests.log) &
  PID=$!
  show_spinner "$PID"

  wait $PID ## sets exit code from command

  if [ $? -ne 0 ]; then
    EXIT_CODE=$?
    error "Tests failed"
    ./ci/junit-errors-to-stdout.sh
    exit $EXIT_CODE
  fi
fi

if [ -z "$RUN_ITS" ]; then
  info "Running unit tests..."
  (mvn -q install > $WORKDIR/target/tests.log) &
  PID=$!
  show_spinner "$PID"

  wait $PID ## sets exit code from command

  if [ $? -ne 0 ]; then
    EXIT_CODE=$?
    error "Tests failed"
    ./ci/junit-errors-to-stdout.sh
    exit $EXIT_CODE
  fi
fi

if [ -n "$BUILD_DOCS" ]; then
  ./ci/build_docs.sh
  if [ $? -ne 0 ]; then
    # Only exit since build_docs.sh would handle its own error messages
    exit $?
  fi
fi

