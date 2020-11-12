## 기본 테스트 내용
DROP DATABASE IF EXISTS a1;

CREATE DATABASE a1;

USE a1;

CREATE TABLE article (
id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
regDate DATETIME NOT NULL,
title CHAR(200) NOT NULL,
`body` TEXT NOT NULL);

DESC article;

INSERT INTO article
SET regDate = NOW(),
title = '제목1', `body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
title = '제목2', `body` = '내용2';

DESC article;

SELECT * FROM article;

ALTER TABLE article ADD COLUMN updatedate DATETIME;

UPDATE article
SET updatedate = NOW();

ALTER TABLE article MODIFY COLUMN updatedate DATETIME NOT NULL;

## 내용추가
UPDATE article
SET title = '제목4', `body` = '내용3'
WHERE id = 2;