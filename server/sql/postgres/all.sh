#!/bin/bash

set +x

DB_NAME=hc_mobile
USERNAME=hc_mobile

psql -U ${USERNAME} -f ALL.SQL -L all.log ${DB_NAME}

