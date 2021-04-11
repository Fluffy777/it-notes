/**
 * TODO: тестові дані (на поточний момент)
 */

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
                           user_id INTEGER REFERENCES users (user_id),
                           role_id INTEGER REFERENCES roles (role_id),
                           PRIMARY KEY (user_id, role_id)
);

CREATE SEQUENCE users_seq;
CREATE SEQUENCE roles_seq;

INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');

-- email: fadmin@example.com
-- password: checkPassword123
INSERT INTO users (first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES ('Fadmin', 'Ladmin', 'M', 'fadmin@example.com', '$2y$10$eKPBoe7825jzrYXiZnWm5OPr.n2J2iR.4qOiBzYiPj.7K/htKSv.6', SYSDATE, NULL, 'Admin', 'Sumy, Ukraine');

-- email: fuser@example.com
-- password: otherPassword123
INSERT INTO users (first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES ('Fuser', 'Luser', 'F', 'fuser@example.com', '$2y$10$j29nTf.rjVRBxENkp5gfZewL5Wj5PecEwMReVCiWO9IHuGVL9yGa6', SYSDATE, TO_DATE('12/12/2002', 'DD/MM/YYYY'), 'User', 'Kyiv, Ukraine');

INSERT INTO user_role VALUES (1 /*Fadmin Ladmin*/, 1 /*ADMIN*/);
INSERT INTO user_role VALUES (1 /*Fadmin Ladmin*/, 2 /*USER*/);
INSERT INTO user_role VALUES (2 /*Fuser Luser*/, 2 /*USER*/);
COMMIT;

CREATE TABLE categories (
                            category_id INTEGER PRIMARY KEY,
                            name VARCHAR2(20) NOT NULL UNIQUE
);

CREATE TABLE articles (
                          article_id INTEGER PRIMARY KEY,
                          category_id INTEGER NOT NULL REFERENCES categories (category_id),
                          user_id INTEGER NOT NULL REFERENCES users (user_id),
                          name VARCHAR2(100) NOT NULL,
                          content VARCHAR2(2000) NOT NULL,
                          modification_date DATE NOT NULL,
                          views INTEGER DEFAULT 0 NOT NULL
);

CREATE SEQUENCE categories_seq;
CREATE SEQUENCE articles_seq;



CREATE TABLE comments (
                          comment_id INTEGER PRIMARY KEY,
                          article_id INTEGER NOT NULL REFERENCES articles (article_id),
                          parent_id INTEGER REFERENCES comments (comment_id),
                          user_id INTEGER NOT NULL REFERENCES users (user_id),
                          content VARCHAR2(256) NOT NULL,
                          timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);
