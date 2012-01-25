@if "%DEBUG%" == "" @echo off

set MAIN_PROJECT_PATH=..\server\mobile-dispatcher\src\main\java
set TEST_PROJECT_PATH=..\server\dispatcher-test\src\main\java

echo "Generating java source into %MAIN_PROJECT_PATH% "

protoc --java_out="%MAIN_PROJECT_PATH%" commons.proto
protoc --java_out="%MAIN_PROJECT_PATH%" banners.proto
protoc --java_out="%MAIN_PROJECT_PATH%" locations.proto
protoc --java_out="%MAIN_PROJECT_PATH%" currency.proto
protoc --java_out="%MAIN_PROJECT_PATH%" news.proto
protoc --java_out="%MAIN_PROJECT_PATH%" partners.proto

protoc --java_out="%MAIN_PROJECT_PATH%" protocol.proto

protoc --java_out="%TEST_PROJECT_PATH%" commons.proto
protoc --java_out="%TEST_PROJECT_PATH%" banners.proto
protoc --java_out="%TEST_PROJECT_PATH%" locations.proto
protoc --java_out="%TEST_PROJECT_PATH%" currency.proto
protoc --java_out="%TEST_PROJECT_PATH%" news.proto
protoc --java_out="%TEST_PROJECT_PATH%" partners.proto

protoc --java_out="%TEST_PROJECT_PATH%" protocol.proto
