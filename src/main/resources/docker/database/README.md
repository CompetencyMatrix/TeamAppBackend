# Command to create database in the container without docker-compose:

docker exec -it postgres_container bash
psql -U postgres
CREATE DATABASE teamapp;