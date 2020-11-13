## 기본 테스트 내용
DROP DATABASE IF EXISTS a1;

CREATE DATABASE a1;

USE a1;

CREATE TABLE article (
id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updatedate DATETIME NOT NULL,
title CHAR(200) NOT NULL,
`body` TEXT NOT NULL,
memberId INT UNSIGNED NOT NULL,
boardId INT UNSIGNED NOT NULL);

DESC article;

INSERT INTO article
SET regDate = NOW(),
updatedate = NOW(),
title = '제목1',
`body` = '내용1',
memberId = 1,
boardId = 1;

INSERT INTO article
SET regDate = NOW(),
updatedate = NOW(),
title = '제목2',
`body` = '내용2',
memberId = 2,
boardId = 2;

INSERT INTO article
SET regDate = NOW(),
updatedate = NOW(),
title = '제목3',
`body` = '내용3',
memberId = 2,
boardId = 2;

DESC article;

SELECT * FROM article;

## 멤버테이블 추가
DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
memberId CHAR(200) NOT NULL,
`password` CHAR(200) NOT NULL,
`name` CHAR(200) NOT NULL);

DESC `member`;

SELECT * FROM `member`;

INSERT INTO `member`
SET memberId = 'aa',
`password` = 'aa',
`name` = '멤버1';

INSERT INTO `member`
SET memberId = 'bb',
`password` = 'bb',
`name` = '멤버2';