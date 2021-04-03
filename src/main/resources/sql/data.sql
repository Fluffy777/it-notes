/**
 * TODO: тестові дані (на поточний момент)
 */

/**
 * Структура таблиць у базі даних Oracle.
 */

CREATE TABLE users (
	user_id INTEGER PRIMARY KEY,
	role_id INTEGER NOT NULL,
	first_name VARCHAR2(50) NOT NULL,
	last_name VARCHAR2(50) NOT NULL,
	gender CHAR(1),
	email VARCHAR2(255) NOT NULL,
	password VARCHAR2(60) NOT NULL,
	rday DATE NOT NULL,
	bday DATE,
	description VARCHAR2(512),
	address VARCHAR2(128),
	icon BLOB
);

CREATE TABLE articles (
	article_id INTEGER PRIMARY KEY,
	category_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	name VARCHAR2(100) NOT NULL,
	content CLOB,
	modification_date DATE NOT NULL
);

CREATE TABLE roles (
	role_id INTEGER PRIMARY KEY,
	name VARCHAR2(20) NOT NULL UNIQUE
);

CREATE TABLE comments (
	comment_id INTEGER PRIMARY KEY,
	article_id INTEGER NOT NULL,
	parent_id INTEGER,
	user_id INTEGER NOT NULL,
	content VARCHAR2(256) NOT NULL
);

CREATE TABLE categories (
	category_id INTEGER PRIMARY KEY,
	name VARCHAR2(20) NOT NULL UNIQUE
);

ALTER TABLE articles ADD  FOREIGN KEY (user_id) REFERENCES users (user_id);
ALTER TABLE comments ADD  FOREIGN KEY (user_id) REFERENCES users (user_id);
ALTER TABLE comments ADD  FOREIGN KEY (article_id) REFERENCES articles (article_id);
ALTER TABLE users ADD  FOREIGN KEY (role_id) REFERENCES roles (role_id);
ALTER TABLE comments ADD  FOREIGN KEY (parent_id) REFERENCES comments (comment_id);
ALTER TABLE articles ADD  FOREIGN KEY (category_id) REFERENCES categories (category_id);

/**
 * Початкові дані.
 */
CREATE SEQUENCE users_seq;
CREATE SEQUENCE articles_seq;
CREATE SEQUENCE roles_seq;
CREATE SEQUENCE comments_seq;
CREATE SEQUENCE categories_seq;

INSERT INTO roles VALUES (roles_seq.nextval, 'admin');
INSERT INTO roles VALUES (roles_seq.nextval, 'user');

INSERT INTO users VALUES (users_seq.nextval, 1/*admin*/, 'Fadmin', 'Ladmin', 'm', 'fluffy@gmail.com', 'admin123', SYSDATE, NULL, 'Admin of this site', 'Ukraine', NULL);
INSERT INTO users VALUES (users_seq.nextval, 2/*user*/, 'Fuser', 'Luser', 'f', 'somebody@gmail.com', 'user123', SYSDATE, NULL, 'User of this site', 'Ukraine', NULL);

INSERT INTO categories VALUES (categories_seq.nextval, 'Java');
INSERT INTO categories VALUES (categories_seq.nextval, 'Lua');
INSERT INTO categories VALUES (categories_seq.nextval, 'C++');
INSERT INTO categories VALUES (categories_seq.nextval, 'C#');

INSERT INTO articles VALUES (articles_seq.nextval, 1/*Java*/, 1/*admin*/, 'JRE, JDK installation', 'Link for JRE, JDK', SYSDATE);
INSERT INTO articles VALUES (articles_seq.nextval, 1/*Java*/, 1/*admin*/, 'Hello world application', 'class MyClass { public static void Main (String[] args) { ... } }', SYSDATE);
INSERT INTO articles VALUES (articles_seq.nextval, 1/*Java*/, 1/*admin*/, 'Spring', 'Framework', SYSDATE);
INSERT INTO articles VALUES (articles_seq.nextval, 1/*Java*/, 1/*admin*/, 'Spring Boot', 'Addon for framework', SYSDATE);

INSERT INTO articles VALUES (articles_seq.nextval, 2/*Lua*/, 1/*admin*/, 'Hello world', 'print "Hello, world"', SYSDATE);
INSERT INTO articles VALUES (articles_seq.nextval, 2/*Lua*/, 1/*admin*/, 'Tables', 'a = {}; a.var1 = 10; a.var2 = 20; print (a.var1 + a.var2);', SYSDATE);

INSERT INTO articles VALUES (articles_seq.nextval, 3/*C++*/, 1/*admin*/, 'Hello world', '#include <iostream> int main () {std::cout << "Hello, world!"; return 0;}', SYSDATE);
INSERT INTO articles VALUES (articles_seq.nextval, 3/*C++*/, 1/*admin*/, 'Calculation', 'int a = 10; int b = 20; std::cout << (a + b) << std::endl;', SYSDATE);

INSERT INTO articles VALUES (articles_seq.nextval, 4/*C#*/, 1/*admin*/, 'Hello, world', 'using System; class Program { ... }', SYSDATE);

INSERT INTO comments VALUES (comments_seq.nextval, 1, NULL, 2/*user*/, 'Good explanation');
INSERT INTO comments VALUES (comments_seq.nextval, 2, NULL, 2/*user*/, 'Nice one');
INSERT INTO comments VALUES (comments_seq.nextval, 5, NULL, 2/*user*/, 'It''s not informative enough');
INSERT INTO comments VALUES (comments_seq.nextval, 5, 3/*previous comment*/, 1/*admin*/, 'No');
INSERT INTO comments VALUES (comments_seq.nextval, 8, NULL, 2/*user*/, 'Good article');
INSERT INTO comments VALUES (comments_seq.nextval, 9, NULL, 1/*admin*/, 'Any mistakes?');
INSERT INTO comments VALUES (comments_seq.nextval, 9, 6/*previous comment*/, 2/*user*/, 'Yeah: ...');
INSERT INTO comments VALUES (comments_seq.nextval, 9, 7/*previous comment*/, 1/*admin*/, 'Corrected, thanks');
COMMIT;

DROP TABLE comments PURGE;
DROP TABLE articles PURGE;
DROP TABLE categories PURGE;
DROP TABLE users PURGE;
DROP TABLE roles PURGE;

DROP SEQUENCE comments_seq;
DROP SEQUENCE articles_seq;
DROP SEQUENCE categories_seq;
DROP SEQUENCE users_seq;
DROP SEQUENCE roles_seq;

