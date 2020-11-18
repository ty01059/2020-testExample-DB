# a5 데이터베이스 삭제/생성/선택
DROP DATABASE IF EXISTS a5;
CREATE DATABASE a5;
USE a5;

# 부서(dept) 테이블 생성 및 홍보부서 기획부서 추가
CREATE TABLE dept (
id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
department CHAR(50) NOT NULL
);

INSERT INTO dept
SET department = "홍보";

INSERT INTO dept
SET department = "기획";

SELECT * FROM dept;

# 사원(emp) 테이블 생성 및 홍길동사원(홍보부서), 홍길순사원(홍보부서), 임꺽정사원(기획부서) 추가
CREATE TABLE emp (
id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` CHAR(50) NOT NULL,
department CHAR(50) NOT NULL
);

ALTER TABLE emp ADD COLUMN `position` CHAR(50) NOT NULL;
ALTER TABLE emp MODIFY COLUMN `position` CHAR(50) NOT NULL AFTER `name`;

INSERT INTO emp
SET `name` = "홍길동",
`position` = "사원",
department = "홍보";

INSERT INTO emp
SET `name` = "홍길순",
`position` = "사원",
department = "홍보";

INSERT INTO emp
SET `name` = "임꺽정",
`position` = "사원",
department = "기획";

SELECT * FROM emp;

# 홍보를 마케팅으로 변경
UPDATE dept
SET department = "마케팅"
WHERE department = "홍보";

# 마케팅을 홍보로 변경
UPDATE dept
SET department = "홍보"
WHERE department = "마케팅";

# 홍보를 마케팅으로 변경
UPDATE dept
SET department = "마케팅"
WHERE department = "홍보";
# 구조를 변경하기로 결정(사원 테이블에서, 이제는 부서를 이름이 아닌 번호로 기억)
UPDATE emp
SET department = "1"
WHERE department = "홍보";

UPDATE emp
SET department = "2"
WHERE department = "기획";

# 사장님께 드릴 인명록을 생성
SELECT * FROM emp;

# 사장님께서 부서번호가 아니라 부서명을 알고 싶어하신다.
# 그래서 dept 테이블 조회법을 알려드리고 혼이 났다.

# 사장님께 드릴 인명록을 생성(v2, 부서명 포함, ON 없이)
UPDATE emp
SET department = "홍보"
WHERE department = "1";

UPDATE emp
SET department = "기획"
WHERE department = "2";

ALTER TABLE emp ADD COLUMN deptId INT UNSIGNED NOT NULL;

UPDATE emp
SET deptId = 1
WHERE department = "홍보";
UPDATE emp
SET deptId = 2
WHERE department = "기획";

SELECT * FROM emp;

# 이상한 데이터가 생성되어서 혼남

# 사장님께 드릴 인명록을 생성(v3, 부서명 포함, 올바른 조인 룰(ON) 적용)
# 보고용으로 좀 더 편하게 보여지도록 고쳐야 한다고 지적받음
SELECT *
FROM emp INNER JOIN dept ON emp.deptId = dept.id;

# 사장님께 드릴 인명록을 생성(v4, 사장님께서 보시기에 편한 칼럼명(AS))
SELECT emp.id AS '번호', emp.name AS '이름', emp.position AS '직책', dept.department AS '부서' 
FROM emp 
INNER JOIN dept 
ON emp.deptId = dept.id;
