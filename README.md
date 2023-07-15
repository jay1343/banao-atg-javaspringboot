# banao-atg-javaspringboot

Configuration of Database is below :

psql -U postgres
CREATE DATABASE 'banao-atg';
\connect banao-atg;
CREATE SEQUENCE users_seq;
alter default privileges grant all on tables to postgres;
alter default privileges grant all on sequences to postgres;

CREATE TABLE users (
    user_id INTEGER PRIMARY KEY NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL
);
 you also find the banao-atg_db.sql in case


Now let's start with the routing.
use http://localhost:8080/api/users/login for login and http://localhost:8080/api/users/register.

after login if normal user the will show simple page with his name.
and for Admin we list all users.

I also used JWT token and Sessions for session tracking and authentication.
there are some parts that I would have been did better than current just because I didnt have much knowledge of thymeleaf.
I have exposure to the angular so it could have been better.
And there are configurations I can use for routing to pages and the differntiate between roles using xml file.
