#!/bin/bash
set -x

MAIN_PROJECT_PATH=../server/mobile-dispatcher/src/main/java/
TEST_PROJECT_PATH=../server/dispatcher-test/src/main/java/

protoc --java_out=$MAIN_PROJECT_PATH news.proto
protoc --java_out=$MAIN_PROJECT_PATH commons.proto
protoc --java_out=$MAIN_PROJECT_PATH currency.proto
protoc --java_out=$MAIN_PROJECT_PATH banners.proto
protoc --java_out=$MAIN_PROJECT_PATH locations.proto
protoc --java_out=$MAIN_PROJECT_PATH partners.proto
protoc --java_out=$MAIN_PROJECT_PATH protocol.proto

protoc --java_out=$TEST_PROJECT_PATH news.proto
protoc --java_out=$TEST_PROJECT_PATH commons.proto
protoc --java_out=$TEST_PROJECT_PATH currency.proto
protoc --java_out=$TEST_PROJECT_PATH banners.proto
protoc --java_out=$TEST_PROJECT_PATH locations.proto
protoc --java_out=$TEST_PROJECT_PATH partners.proto
protoc --java_out=$TEST_PROJECT_PATH protocol.proto
