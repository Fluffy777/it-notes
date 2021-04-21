/**
 * Структура таблиць у базі даних Oracle.
 */
CREATE TABLE users (
                       user_id INTEGER PRIMARY KEY,
                       enabled CHAR(1) DEFAULT 'Y' NOT NULL CHECK (enabled IN ('Y', 'N')),
                       first_name VARCHAR2(50) NOT NULL,
                       last_name VARCHAR2(50) NOT NULL,
                       gender CHAR(1) NOT NULL CHECK (gender IN ('M', 'F')),
                       email VARCHAR2(320) NOT NULL UNIQUE,
                       password VARCHAR2(60) NOT NULL,
                       rday DATE NOT NULL,
                       bday DATE,
                       description VARCHAR2(512),
                       address VARCHAR2(128),
                       icon VARCHAR2(255)
);

CREATE TABLE roles (
                       role_id INTEGER PRIMARY KEY,
                       name VARCHAR2(20) NOT NULL UNIQUE
);

CREATE TABLE user_role (
                           user_id INTEGER REFERENCES users (user_id) ON DELETE CASCADE,
                           role_id INTEGER REFERENCES roles (role_id) ON DELETE CASCADE,
                           PRIMARY KEY (user_id, role_id)
);

CREATE SEQUENCE users_seq;
CREATE SEQUENCE roles_seq;



CREATE TABLE categories (
                            category_id INTEGER PRIMARY KEY,
                            name VARCHAR2(20) NOT NULL UNIQUE
);

CREATE TABLE articles (
                          article_id INTEGER PRIMARY KEY,
                          category_id INTEGER NOT NULL REFERENCES categories (category_id) ON DELETE CASCADE,
                          user_id INTEGER NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
                          name VARCHAR2(100) NOT NULL,
                          content VARCHAR2(2000) NOT NULL,
                          modification_date DATE NOT NULL,
                          views INTEGER DEFAULT 0 NOT NULL
);

CREATE SEQUENCE categories_seq;
CREATE SEQUENCE articles_seq;



CREATE TABLE comments (
                          comment_id INTEGER PRIMARY KEY,
                          article_id INTEGER NOT NULL REFERENCES articles (article_id) ON DELETE CASCADE,
                          local_id INTEGER NOT NULL,
                          parent_id INTEGER REFERENCES comments (comment_id) ON DELETE CASCADE,
                          user_id INTEGER NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
                          content VARCHAR2(256) NOT NULL,
                          modification_date DATE NOT NULL
);
CREATE SEQUENCE comments_seq;
