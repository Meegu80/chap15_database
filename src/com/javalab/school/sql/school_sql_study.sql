/****** 선택(select) ****/

--날짜 형식 지정(현재의 세션에 적용)
ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD HH24:MI:SS';

select *
from tbl_student;

select to_char(hiredate, 'yyyy-mm-dd')
from professor;

where stu_id = '1292001';
select *
from tbl_professor
where  to_char(hiredate, 'yyyy-mm-dd') > '2007-01-01';

SELECT *
FROM tbl_professor
where extract(year from hiredate) = '2005';

select *
from tbl_professor
where to_char(hiredate, 'yyyy') = '2005';



-- 다중 조인 조건
SELECT *
FROM tbl_professor
WHERE hiredate >= TO_DATE('2000-01-01', 'YYYY-MM-DD')
  AND    grade = '부교수';

-- 널처리
-- 92501	620505-1200546	박철재	925	교수	2007-08-01 00:00:00
-- 학과를 널(null)로 만듦.
select *
from tbl_professor;

-- 학과가 널 값인 교수, 아직 학과가 안정해진 교수
select *
from tbl_professor
where dept_id is null;

-- 학과가 정해진 교수 조회
select *
from tbl_professor
where dept_id is not null;

/****** 프로젝션 ****/

select stu_id, name
from tbl_student;

select distinct dept_id
from tbl_student;


-- 다중 조인 조건
SELECT *
FROM tbl_professor
WHERE  grade = '부교수'
  AND hiredate >= TO_DATE('2000-01-01', 'YYYY-MM-DD');

/**** 재명명(Alias) 연산 ****/
select s.*
from tbl_student s;

-- 오라클에서는 작은 따옴표는 문자열 리터럴을 표시할 사용함.
--select s.stu_id as '학생번호'
--from tbl_student s;

-- 별칭(학생 테이블에서 학년을 +1해서 조회)
select s.stu_id, s.name, s.year, s.year + 1
from tbl_student s;

select s.stu_id, s.name, s.year, (s.year + 1) "학년"
from tbl_student s;

/** 정렬 **/
-- 학년별로 조회(오름차순)
select *
from tbl_student
order by year asc;

-- 학년별로 오름차순, 주민번호는 내림차순 정렬
select *
from tbl_student
order by year asc, jumin desc;

select s.stu_id as 학생번호
from tbl_student s;

-- 학생 중에서 서울 사는 학생 선택
select s.*
from tbl_student s
where s.address = '서울';

/** 비교 연산자 **/
-- 2 ~ 3 학년 학생을 조회하시오.
select s.*
from tbl_student s
where s.year between 2 and 3;

select s.*
from tbl_student s
where s.year in (2, 3);

select s.*
from tbl_student s
where s.year >= 2
  and s.year <=3;

-- 3학년 이외의 학년 학생들을 조회하시오.
select s.*
from tbl_student s
where s.year != 3;

select s.*
from tbl_student s
where s.year <> 3;

select s.*
from tbl_student s
where s.year not in(3);

select s.*
from tbl_student s
where not s.year = 3;

/** in 연산자 **/
-- 학생들 중에서 주소가 '서울' 그리고 '대전' 인 학생들을 조회하시오.
select s.*
from tbl_student s
where s.address in ('서울', '대전');

/** Like 와 와일드 카드(%) 연산자 **/
-- 이름에 '김'이라는 단어가 들어간 학생들
select s.*
from tbl_student s
where s.name like '%김%';

select s.*
from tbl_student s
where s.name like '%' || '김' || '%';

-- 성이 '김'인 학생들(이름의 첫글자가 '김')
select s.*
from tbl_student s
where s.name like '김%';

-- 이름의 끝 글자가 '현'으로 끝나는 학생
select s.*
from tbl_student s
where s.name like '%현';

-- 와일드카드 %만 있을 경우 : 이름에 어떤 단어가 오든지 상관없음
select s.*
from tbl_student s
where s.name like '%';

/** null 값 **/
-- 특정 컬럼의 값이 비어있는 상태로 '0'과는 다르다.
-- 널 값 테스틀 ㄹ위한 테이블 생성

create table sawon(
                      id number(6,0) primary key,
                      name varchar2(20) not null,
                      sal number(10) default 0,
                      bonus number(10)
);
insert into sawon(id, name, sal, bonus) values(1, '홍', 300, 10);
insert into sawon(id, name, sal) values(2, '김', 400);
insert into sawon(id, name, sal, bonus) values(3, '이', 500, 20);
insert into sawon(id, name, sal, bonus) values(4, '박', 600, 30);
insert into sawon(id, name, sal) values(5, '정', 700);
insert into sawon(id, name, sal, bonus) values(6, '최', 800, 40);
commit;

select *
from sawon;

-- 널값 조회
-- [오류] 보너스가 없는 사원들 조회
select s.id, s.name, s.sal, s.bonus
from sawon s
where bonus = null;

-- [OK] 보너스가 없는 사원들 조회
select s.id, s.name, s.sal, bonus
from sawon s
where bonus is null;

-- [OK] 보너스가 있는 사원들 조회
select s.id, s.name, s.sal, bonus
from sawon s
where bonus is not null;

-- 연봉 계산(sal * 12)
select s.id, s.name, s.sal, s.bonus, (s.sal * 12) annual_salary
from sawon s;

-- 연봉 계산(sal * 12 + bonus)
-- 보너스(bonus)가 널값이 사원의 연봉이 널값 처리됨.
-- 널값은 값이 아무것도 없기 때문에 연산을 하게되면
-- 결과가 널이 되어버림.
select s.id, s.name, s.sal, s.bonus,
       (s.sal * 12 + bonus) 연봉
from sawon s;

-- 보너스가 널값인 경우에는 널처리를 해줘야 함.
-- nvl(bonus, 0)
select s.id, s.name, s.sal, s.bonus,
       (s.sal * 12 + nvl(bonus,0)) 연봉
from sawon s;

/** 오라클 함수 테스트 **/

-- emp 테이블 생성
--DROP TABLE EMP;
CREATE TABLE emp (
                     empno    number(4) NOT NULL,
                     ename    VARCHAR2(20) NOT NULL,
                     job      VARCHAR2(20) NOT NULL,
                     mgr      number(4),
                     hiredate DATE NOT NULL,
                     sal      number(7, 2) NOT NULL,
                     comm     number(7, 2),
                     deptno   number(2) NOT NULL,
                     PRIMARY KEY ( empno )
);

--DROP TABLE dept;
CREATE TABLE dept (
                      deptno   number(2) NOT NULL,
                      dname    VARCHAR2(100) NOT NULL,
                      location VARCHAR2(100) NOT NULL,
                      PRIMARY KEY ( deptno )
);​

INSERT INTO emp VALUES (7369, 'SMITH', 'CLERK', 7902, '1980-12-17', 800, NULL, 20);
INSERT INTO emp VALUES (7499, 'ALLEN', 'SALESMAN', 7698, '1981-02-20', 1600, 300, 30);
INSERT INTO emp VALUES (7521, 'WARD', 'SALESMAN', 7698, '1981-02-22', 1250, 500, 30);
INSERT INTO emp VALUES (7566, 'JONES', 'MANAGER', 7839, '1981-04-02', 2975, NULL, 20);
INSERT INTO emp VALUES (7654, 'MARTIN', 'SALESMAN', 7698, '1981-09-28', 1250, 1400, 30);
INSERT INTO emp VALUES (7698, 'BLAKE', 'MANAGER', 7839, '1981-05-01', 2850, NULL, 30);
INSERT INTO emp VALUES (7782, 'CLARK', 'MANAGER', 7839, '1981-06-09', 2450, NULL, 10);
INSERT INTO emp VALUES (7788, 'SCOTT', 'ANALYST', 7566, '1982-12-09', 3000, NULL, 20);
INSERT INTO emp VALUES (7839, 'KING', 'PRESIDENT', NULL, '1981-11-17', 5000, NULL, 10);
INSERT INTO emp VALUES (7844, 'TURNER', 'SALESMAN', 7698, '1981-09-08', 1500, 0, 30);
INSERT INTO emp VALUES (7876, 'ADAMS', 'CLERK', 7788, '1983-01-12', 1100, NULL, 20);
INSERT INTO emp VALUES (7900, 'JAMES', 'CLERK', 7698, '1981-12-03', 950, NULL, 30);
INSERT INTO emp VALUES (7902, 'FORD', 'ANALYST', 7566, '1981-12-03', 3000, NULL, 20);
INSERT INTO emp VALUES (7934, 'MILLER', 'CLERK', 7782, '1982-01-23', 1300, NULL, 10);​

INSERT INTO dept VALUES (10, 'ACCOUNTING', 'NEW YORK');
INSERT INTO dept VALUES (20, 'RESEARCH', 'DALLAS');
INSERT INTO dept VALUES (30, 'SALES', 'CHICAGO');
INSERT INTO dept VALUES (40, 'OPERATIONS', 'BOSTON');
commit;

-- upper(), lower(), initcap()
select empno, upper(ename) upper_name,
       lower(ename) lower_name, initcap(ename) initcap_name
from emp;

-- 조건에서 lower()함수 사용
select *
from emp
where lower(ename) = 'smith';

--문자열 길이 확인 length(), lengthb()
select length('한글') "한글글자수", lengthb('한글') "한글바이트수"
from dual;

-- substr(jumin 8,1) : 900424-1825409
-- 시작 인덱스는 0이 아닌 1부터
select substr(jumin,8,1) 성별구분문자
from tbl_student;

900305-2730021

-- substr(jumin 8) : 900424-1825409
-- 시작 인덱스 8부터 문자열 끝까지
select substr(jumin,8) "주민번호 뒷자리"
from tbl_student;

-- replace()
select replace(jumin, '-', ' '), replace(jumin, '-')
from tbl_student;

-- concat() 함수 : 컬럼연결
select concat(name, year)
from tbl_student;

-- || 연산자 : 컬럼 연결
select name || ' ' || year || '학년' as "이름과 학년 연결"
from tbl_student;

-- trim()
select trim(' 안녕 친구 '), lengthb(trim(' 안녕 친구 '))
from dual;

-- round()
select round(1234.567),
       round(1234.567, 1) "둘째자리서 반올림",
       round(1234.567, 2) "셋째자리서 반올림"
from dual;

--sysdate() : 현재 날짜 시간
select sysdate, sysdate-1 어제, sysdate+1 내일
from dual;

SELECT
    TO_CHAR(sysdate, 'YYYY/MM/DD') AS "현재",
    TO_CHAR(sysdate-1, 'YYYY/MM/DD') AS "어제",
    TO_CHAR(sysdate+1, 'YYYY/MM/DD') AS "내일"
FROM dual;

select sysdate, add_months(sysdate, 3)
from dual;





/* 합집합 Union*/
/*
  tbl_student와 tbl_professor의 합집합을 구하려면
  두 테이블의 구조가 동일해야 한다. 즉, 조회하는 컬럼의 수가 같고
  해당 컬럼들의 데이터 타입도 일치해야 한다.
*/
SELECT stu_id, name FROM tbl_student
UNION
SELECT prof_id, name FROM tbl_professor;

-- union all
SELECT * FROM tbl_student
where address = '서울'
UNION all
SELECT * FROM tbl_student
where address = '대전'
UNION all
SELECT * FROM tbl_student
where address = '광주';

-- intersect(교집합)
SELECT * FROM tbl_student
intersect
SELECT * FROM tbl_student
where address = '대전';














































/** 이너 조인 **/

-- 오라클 이너조인
select s.*, d.dept_name, d.office
from tbl_student s, tbl_department d
where s.dept_id = d.dept_id;

-- ANSI(표준) 이너조인
select s.*, d.dept_name, d.office
from tbl_student s inner join tbl_department d
                              on s.dept_id = d.dept_id;

select *
from tbl_professor;

select *
from tbl_department;

-- 교수 + 학과
select p.*, d.*
from tbl_professor p inner join tbl_department d
                                on p.dept_id = d.dept_id;

--distinct 학생 테이블에서 학생들의 주소지를 조회
select distinct address
from tbl_student;

-- 교과목(tbl_course)중에서 강의(tbl_class)가 한번이라도 개설된 교과목은?
SELECT c.course_id
FROM tbl_course c
         INNER JOIN tbl_class cl ON c.course_id = cl.course_id
order by c.course_id;

-- 교과목(course) 중에서 강좌(class)가 개설된 교과목 조회(이너조인)
-- 강좌가 개설되어 있지 않은 교과목은 누락됨.
select c.*, l.*
from tbl_course c, tbl_class l
where c.course_id = l.course_id;

/* 외부 조인 */
-- 교과목은 개설된 강좌가 없어도 모두 나오도록 조회
-- 모든 교과목의 강좌 개설 현황을 보고자 할 경우

--[문제] 이너조인으로 연결할 경우의 문제점
-- Ansi 이너조인(객체지향언어, 이산수학 과목 누락)
select co.*, cl.class_id, cl.year, cl.semester, cl.division
from tbl_course co inner join tbl_class cl
                              on co.course_id = cl.course_id;

-- 오라클 외부조인
select co.*, cl.class_id, cl.year, cl.semester, cl.division
from tbl_course co, tbl_class cl
where co.course_id = cl.course_id(+);

-- Ansi 외부 조인(left outer join)
select co.*, cl.class_id, cl.year, cl.semester, cl.division
from tbl_course co left outer join tbl_class cl
                                   on co.course_id = cl.course_id;

SELECT c.course_id
FROM tbl_course c
         LEFT JOIN tbl_class cl ON c.course_id = cl.course_id
WHERE cl.course_id IS NULL
order by c.course_id;




-- 교과목 별로 개설된 강좌의 갯수를 카운트
select c.course_id, c.title, credit, count(l.course_id)
from tbl_course c, tbl_class l
where c.course_id = l.course_id
group by c.course_id, c.title, credit;

SELECT co.course_id, co.title, co.credit, COUNT(cl.class_id)
FROM tbl_course co LEFT JOIN tbl_class cl ON co.course_id = cl.course_id
GROUP BY co.course_id, co.title, co.credit;

select s.*
from tbl_student s;

-- 학생 + 성적을 이너조인
select s.*, t.*
from tbl_student s
         inner join tbl_takes t on s.stu_id = t.stu_id;

-- 오른쪽외부조인(Right outer join)
create table member(
                       member_id varchar2(10) primary key,
                       name varchar2(20)
);

create table board(
                      board_id varchar2(10) primary key,
                      title varchar2(100) not null,
                      member_id varchar2(10)
);

insert into member(member_id, name) values('1', '홍');
insert into member(member_id, name) values('2', '김');
insert into member(member_id, name) values('3', '정');

insert into board(board_id, title, member_id) values('1', '안녕', '1');
insert into board(board_id, title, member_id) values('2', '방가', '2');
insert into board(board_id, title, member_id) values('3', '오래만', '3');
insert into board(board_id, title, member_id) values('4', '잘가', '4');


select *
from member;

select *
from board;

-- Right Outer Join
select m.*, b.*
from member m
         right outer join board b on m.member_id = b.board_id;

-- Full Outer Join
select m.*, b.*
from member m
         full outer join board b on m.member_id = b.board_id;

/** 셀프조인 Self Join **/
--  셀프조인 예제 테이블 생성
create table employees(
                          emp_id number(6,0) primary key,
                          name varchar2(10) not null,
                          manager_id number(6,0)
);

-- 샘플 데이터 삽입
insert into employees(emp_id, name, manager_id)
values(1, '사장', null);
insert into employees(emp_id, name, manager_id)
values(2, '김부장', 1);
insert into employees(emp_id, name, manager_id)
values(3, '이부장', 1);

insert into employees(emp_id, name, manager_id)
values(4, '최과장', 2);
insert into employees(emp_id, name, manager_id)
values(5, '오과장', 2);

insert into employees(emp_id, name, manager_id)
values(6, '서과장', 3);
insert into employees(emp_id, name, manager_id)
values(7, '정과장', 3);

insert into employees(emp_id, name, manager_id)
values(8, '김대리', 6);
insert into employees(emp_id, name, manager_id)
values(9, '박대리', 6);

select *
from employees;


declare
-- 커서 데이터를 입력할 변수 선언
v_dept_row dept%rowtype;

   -- 명시적 커서 선언(declaration)
cursor c1 is
select deptno, dname, loc
from dept;

begin
   -- 커서 열기(open)
open c1;

loop
-- 커서로부터 읽어온 데이터 사용(fetch)
fetch c1 into v_dept_row;

      -- 커서의 모든 행을 읽어오기 위해 %notfound 속성 지정
      exit when c1%notfound;

      dbms_output.put_line('deptno : ' || v_dept_row.deptno
                        || ', dname : ' || v_dept_row.dname
                        || ', loc : ' || v_dept_row.loc);
end loop;

   -- 커서 닫기(close)
close c1;

end;
/
/
/

-- 상관의 이름을 조회하고 싶다. 자기 테이블과 조인을 한다.
select e1.emp_id, e1.name, e1.manager_id, e2.name "상관이름"
from employees e1
         left outer join employees e2 on e1.manager_id = e2.emp_id
order by e1.emp_id;
