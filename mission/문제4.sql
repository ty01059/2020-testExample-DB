# a6 DB 삭제/생성/선택
DROP DATABASE IF EXISTS a6;
CREATE DATABASE a6;
USE a6;

# 부서(홍보, 기획)
CREATE TABLE dept (
id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
department CHAR(50) NOT NULL
);

INSERT INTO dept
SET department = "홍보";

INSERT INTO dept
SET department = "기획";

# 사원(홍길동/홍보/5000만원, 홍길순/홍보/6000만원, 임꺽정/기획/4000만원)
CREATE TABLE emp (
id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` CHAR(50) NOT NULL,
`position` CHAR(50) NOT NULL,
department CHAR(50) NOT NULL,
salary INT(100) NOT NULL
);

INSERT INTO emp
SET `name` = "홍길동",
`position` = "사원",
department = "홍보",
salary = 5000;

INSERT INTO emp
SET `name` = "홍길순",
`position` = "사원",
department = "홍보",
salary = 6000;

INSERT INTO emp
SET `name` = "임꺽정",
`position` = "사원",
department = "기획",
salary = 4000;

ALTER TABLE emp ADD COLUMN deptId INT UNSIGNED NOT NULL;

UPDATE emp
SET deptId = 1
WHERE department = "홍보";
UPDATE emp
SET deptId = 2
WHERE department = "기획";

SELECT * FROM emp;

# 사원 수 출력
SELECT COUNT(*) FROM emp;

# 가장 큰 사원 번호 출력
SELECT MAX(id) FROM emp;

# 가장 고액 연봉
SELECT MAX(salary) FROM emp;

# 가장 저액 연봉
SELECT MIN(salary) FROM emp;

# 회사에서 1년 고정 지출(인건비)
SELECT SUM(salary) FROM emp;

# 부서별, 1년 고정 지출(인건비)
SELECT department, SUM(salary) FROM emp
GROUP BY department;

# 부서별, 최고연봉
SELECT department, MAX(salary) FROM emp
GROUP BY department;

# 부서별, 최저연봉
SELECT department, MIN(salary) FROM emp
GROUP BY department;

# 부서별, 평균연봉
SELECT department, AVG(salary) FROM emp
GROUP BY department;

# 부서별, 부서명, 사원리스트, 평균연봉, 최고연봉, 최소연봉, 사원수 
## V1(조인 안한 버전)
SELECT department, GROUP_CONCAT(`name` SEPARATOR ', '), AVG(salary), MAX(salary), MIN(salary), COUNT(department)
FROM emp
GROUP BY department;

SELECT * FROM emp;
SELECT * FROM dept;
## V2(조인해서 부서명까지 나오는 버전)
SELECT dept.department AS '부서명', emp.name AS '이름', AVG(emp.salary) AS '평균연봉', MAX(emp.salary) AS '최고연봉', MIN(emp.salary) AS '최소연봉', COUNT(emp.department) AS '사원수'
FROM emp INNER JOIN dept ON emp.deptId = dept.id
GROUP BY emp.department;