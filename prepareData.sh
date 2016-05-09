#!/bin/bash

### Accounts ###################
curl -X POST --header "Content-Type: application/json" --header "Accept: application/json" -d "{
  \"login\": \"Dani\",
  \"name\": \"Daniel Seidewitz\",
  \"token\": \"daniToken\"
}" "http://localhost:8082/account"

curl -X POST --header "Content-Type: application/json" --header "Accept: application/json" -d "{
  \"login\": \"Philli\",
  \"name\": \"Philipp Lamp\",
  \"token\": \"philliToken\"
}" "http://localhost:8082/account"

curl -X POST --header "Content-Type: application/json" --header "Accept: application/json" -d "{
  \"login\": \"Hanni\",
  \"name\": \"Hanno Kersting\",
  \"token\": \"hanniToken\"
}" "http://localhost:8082/account"


### Rooms #####################
curl -X POST --header "Content-Type: application/json" --header "Accept: application/json" -d "{
  \"name\": \"Turmzimmer\",
  \"floor\": \"5\",
  \"location\": \"hinten rechts\"
}" "http://localhost:8083/room"

curl -X POST --header "Content-Type: application/json" --header "Accept: application/json" -d "{
  \"name\": \"Prinzessinnenzimmer\",
  \"floor\": \"5\",
  \"location\": \"mitte\"
}" "http://localhost:8083/room"