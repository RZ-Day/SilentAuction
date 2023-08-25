#!/bin/bash
export PGPASSWORD='postgres1'
BASEDIR=$(dirname $0)
DATABASE=final_capstone
cat $BASEDIR/dropdb.sql | docker exec -i pg_container psql -U postgres &&
docker exec -i pg_container createdb -U postgres $DATABASE 
cat $BASEDIR/schema.sql | docker exec -i pg_container psql -U postgres -d $DATABASE &&
cat $BASEDIR/data.sql | docker exec -i pg_container psql -U postgres -d $DATABASE &&
cat $BASEDIR/user.sql | docker exec -i pg_container psql -U postgres -d $DATABASE
