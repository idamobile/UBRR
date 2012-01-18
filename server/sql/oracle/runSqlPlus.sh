#!/bin/bash

username=hc_mobile
password=idamobile
SID=XE

set -x
export NLS_LANG=.AL32UTF8
sqlplus ${username}/${password}@${SID}