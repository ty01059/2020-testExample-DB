# 기존에 a2 데이터베이스가 존재 한다면 삭제
DROP DATABASE IF EXISTS a2;

# 새 데이터베이스(`a2`) 생성
CREATE DATABASE a2;

# 새 데이터베이스(`a2`) 선택
USE a2;

# article 테이블 생성(id, regDate, title, body)
CREATE TABLE article (
id INT,
regDate DATETIME,
title CHAR(200),
`body` TEXT);

# article 테이블 조회(*)
SELECT *
FROM article;

# article 테이블에 data insert (regDate = NOW(), title = '제목', body = '내용')
INSERT INTO article
SET regDate = NOW(),
title = '제목', `body` = '내용';

# article 테이블에 data insert (regDate = NOW(), title = '제목', body = '내용')
INSERT INTO article
SET regDate = NOW(),
title = '제목', `body` = '내용';

# article 테이블 조회(*)
## id가 NULL인 데이터 생성이 가능하네?
SELECT *
FROM article;

# id 데이터는 꼭 필수 이기 때문에 NULL을 허용하지 않게 바꾼다.(alter table, not null)
ALTER TABLE article MODIFY COLUMN id INT NOT NULL;

## 기존의 NULL값 때문에 경고가 뜬다.
## 기존의 NULL값이 0으로 바뀐다.
UPDATE article
set id = 0
where id is null;

# article 테이블 조회(*)
select *
from article;

# 생각해 보니 모든 행(row)의 id 값은 유니크 해야한다.(ADD PRIMARY KEY(id))
## 오류가 난다. 왜냐하면 기존의 데이터 중에서 중복되는게 있기 때문에
alter table article add primary key(id);

# id가 0인 것 중에서 1개를 id 1로 바꾼다.
update article
set id = 1
where id = 0
limit 1;

# article 테이블 조회(*)
SELECT *
FROM article;

# id가 0인것을 id 2로 바꾼다.
UPDATE article
SET id = 2
WHERE id = 0;

# 생각해 보니 모든 행(row)의 id 값은 유니크 해야한다.(ADD PRIMARY KEY(id))
## 이제 적용이 잘 된다.
ALTER TABLE article ADD PRIMARY KEY(id);

# id 칼럼에 auto_increment 를 건다.
## auto_increment 를 걸기전에 해당 칼럼은 무조건 key 여야 한다.
ALTER TABLE article modify column id int not null auto_increment;

# article 테이블 구조확인(desc)
desc article;

# 나머지 칼럼 모두에도 not null을 적용해주세요.
ALTER TABLE article MODIFY COLUMN regDate datetime NOT NULL;
ALTER TABLE article MODIFY COLUMN title char(200) NOT NULL;
ALTER TABLE article MODIFY COLUMN `body` text NOT NULL;

# id 칼럼에 UNSIGNED 속성을 추가하세요.
ALTER TABLE article MODIFY COLUMN id INT UNSIGNED NOT NULL AUTO_INCREMENT;

# 작성자(writer) 칼럼을 title 칼럼 다음에 추가해주세요.
ALTER TABLE article add COLUMN writer char(200) after title;

# 작성자(writer) 칼럼의 이름을 nickname 으로 변경해주세요.(ALTER TABLE article CHANGE oldName, newName)
ALTER TABLE article change column writer nickname char(200);

# nickname 칼럼의 위치를 body 밑으로 보내주세요.(MODIFY COLUMN nickname)
ALTER TABLE article modify column nickname char(200) after `body`;

# hit 조회수 칼럼 추가 한 후 삭제해주세요.
ALTER TABLE article add column hit int(100);
ALTER TABLE article drop column hit;

# hit 조회수 칼럼을 다시 추가
ALTER TABLE article ADD COLUMN hit INT(100);

select *
from article;
desc article;
# 기존의 비어있는 닉네임 채워넣기(무명)
update article
set nickname = '무명'
where nickname is null;

# article 테이블에 데이터 추가(regDate = NOW(), title = '제목3', body = '내용3', nickname = '홍길순', hit = 10)
insert into article set regDate = NOW(), title = '제목3', body = '내용3', nickname = '홍길순', hit = 10;

# article 테이블에 데이터 추가(regDate = NOW(), title = '제목4', body = '내용4', nickname = '홍길동', hit = 55)
INSERT INTO article SET regDate = NOW(), title = '제목4', body = '내용4', nickname = '홍길동', hit = 55;

# article 테이블에 데이터 추가(regDate = NOW(), title = '제목5', body = '내용5', nickname = '홍길동', hit = 10)
INSERT INTO article SET regDate = NOW(), title = '제목5', body = '내용5', nickname = '홍길동', hit = 10;

# article 테이블에 데이터 추가(regDate = NOW(), title = '제목6', body = '내용6', nickname = '임꺽정', hit = 100)
INSERT INTO article SET regDate = NOW(), title = '제목6', body = '내용6', nickname = '임꺽정', hit = 100;

# 조회수 가장 많은 게시물 3개 만 보여주세요., 힌트 : ORDER BY, LIMIT
select *
from article
order by hit desc
limit 3;

# 작성자명이 '홍길'로 시작하는 게시물만 보여주세요., 힌트 : LIKE '홍길%'
SELECT *
FROM article
where nickname like '홍길%';

# 조회수가 10 이상 55 이하 인것만 보여주세요., 힌트 : WHERE 조건1 AND 조건2
SELECT *
FROM article
where hit >= 10 and hit <= 55;

# 작성자가 '무명'이 아니고 조회수가 50 이하인 것만 보여주세요., 힌트 : !=
SELECT *
FROM article
WHERE nickname != '무명' AND hit <= 50;

# 작성자가 '무명' 이거나 조회수가 55 이상인 게시물을 보여주세요. 힌트 : OR
SELECT *
FROM article
WHERE nickname = '무명' or hit >= 55;