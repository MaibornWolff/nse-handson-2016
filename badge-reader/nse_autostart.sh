export HANDSON_SERVER=ms-handson.mwea.de
cd badge-reader/build/libs
java -jar badge-reader.jar --consulHost=ms-handson.mwea.de --deviceId=$1