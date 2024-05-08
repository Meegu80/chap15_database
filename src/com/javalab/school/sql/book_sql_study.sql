show user;
-- drop table book cascade constraints;
CREATE TABLE book (
                      book_id   NUMBER(2),
                      book_name VARCHAR2(40),
                      publisher VARCHAR2(40),
                      price     NUMBER(8),
                      CONSTRAINT pk_book PRIMARY KEY ( book_id )
);

-- drop table customer cascade constraints;
CREATE TABLE customer (
                          cust_id NUMBER(2),
                          name    VARCHAR2(40),
                          address VARCHAR2(50),
                          phone   VARCHAR2(20),
                          CONSTRAINT pk_customer PRIMARY KEY ( cust_id )
);

--8. orders table create
-- drop table orders  cascade constraints;
CREATE TABLE orders (
                        order_id   NUMBER(2),
                        cust_id    NUMBER(2),
                        book_id    NUMBER(2),
                        saleprice  NUMBER(8),
                        order_date DATE,
                        CONSTRAINT pk_orders PRIMARY KEY ( order_id ),
                        CONSTRAINT fk_orders_cust FOREIGN KEY ( cust_id )
                            REFERENCES customer ( cust_id ),
                        CONSTRAINT fk_orders_book FOREIGN KEY ( book_id )
                            REFERENCES book ( book_id )
);

Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (1,'측구의 역사','굿스포츠',7000);
Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (2,'즉구아는 여자','나무수',13000);
Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (3,'측구의 이해','대한미디어',22000);
Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (4,'골프 바이블','대한미디어',35000);
Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (5,'피겨 교본','굿스포크',8000);
Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (6,'역도 단계벌기술','굿스포크',6000);
Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (7,'역도 단계벌기술','굿스포크',20000);
Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (8,'야구를 부탁해','이상미디어',13000);
Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (9,'올림픽 이야기','이상미디어',7500);
Insert into BOOK (BOOK_ID,BOOK_NAME,PUBLISHER,PRICE) values (10,'olympic champions','pearson',13000);

Insert into CUSTOMER (CUST_ID,NAME,ADDRESS,PHONE) values (1,'박지성','영구맨체스터','000-5000-0001');
Insert into CUSTOMER (CUST_ID,NAME,ADDRESS,PHONE) values (2,'김연아','대한민국 서울','000-6000-0001');
Insert into CUSTOMER (CUST_ID,NAME,ADDRESS,PHONE) values (3,'장미란','대한민국 강원도','000-7000-0001');
Insert into CUSTOMER (CUST_ID,NAME,ADDRESS,PHONE) values (4,'추신수','미국 클리블랜드','000-8000-0001');
Insert into CUSTOMER (CUST_ID,NAME,ADDRESS,PHONE) values (5,'박세리','대한민국 대전',null);

Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (1,1,1,6000,to_date('2020-07-01','YYYY-MM-DD'));
Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (2,1,3,21000,to_date('2020-07-03','YYYY-MM-DD'));
Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (3,2,5,8000,to_date('2020-07-03','YYYY-MM-DD'));
Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (4,3,6,6000,to_date('2020-07-04','YYYY-MM-DD'));
Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (5,4,7,20000,to_date('2020-07-05','YYYY-MM-DD'));
Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (6,1,2,12000,to_date('2020-07-07','YYYY-MM-DD'));
Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (7,4,8,13000,to_date('2020-07-07','YYYY-MM-DD'));
Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (8,3,10,12900,to_date('2020-07-08','YYYY-MM-DD'));
Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (9,2,10,7000,to_date('2020-07-09','YYYY-MM-DD'));
Insert into ORDERS (ORDER_ID,CUST_ID,BOOK_ID,SALEPRICE,ORDER_DATE) values (10,3,8,13000,to_date('2020-07-10','YYYY-MM-DD'));

/** 쿼리 테스트 **/
-- 1. 쇼핑몰 전체 고객의 주문 데이터를 조회하시오. 주문이 없는 고객도 출력하시오.
SELECT cs.cust_id, cs.name, cs.address, cs.phone,
       os.ORDER_ID, os.CUST_id, os.book_id, os.saleprice,
       os.order_date
FROM customer cs
         LEFT OUTER JOIN  orders os ON cs.cust_id = os.CUST_ID;

-- 2. 가격이 가장 비싼 도서와 가장 저렴한 도서를 조회하시오.
--  where 절에 다중행 서브쿼리
SELECT b.*
FROM book b
WHERE b.price IN (
    SELECT max(price)
    FROM BOOK
    UNION
    SELECT min(price)
    FROM BOOK
);

-- 3. 출판사별로 도서 합계액을 구하고 금액이 큰 출판사로 정렬하시오.
SELECT b.publisher, sum(b.price)
FROM book b
GROUP BY b.publisher
ORDER BY sum(b.price) desc;

-- 4. 출판사별로 도서 합계액을 구하고 금액이 큰 출판사로 정렬하되
-- 굿스포크 출판사는 대상에서 제외하고
-- 합계액이 10000원이 못넘는 출판사도 조회 목록에서 제외하시오.
SELECT b.publisher, sum(b.price)
FROM book b
Where b.publisher <> '굿스포크'
GROUP BY b.publisher
Having sum(b.price) > 10000
ORDER BY sum(b.price) desc;

-- 5. 13000원 ~ 22000원 사이의 도서를 갖고 있는 출판사 이름을
-- 중복 없이 조회하시오.
select distinct b.publisher
from book b
where b.price between 13000 and 22000;

-- 6. 모든 도서의 평균 가격보다 큰 금액의 도서들만 조회하시오.
select b.*
from book b
where b.price > (select avg(price) from book);

-- 7. 다음 그림과 같이 고객별 판매액을 출력하세요.
-- 고객명을 갖고 올때 customer 테이블과 조인 사용
select os.cust_id, cs.name, sum(os.saleprice) "sum"
from orders os
         LEFT OUTER join customer cs ON os.cust_id = cs.cust_id
group by os.cust_id, cs.name
order by os.cust_id;


?-- 8. 다음 그림과 같이 고객별 판매액을 출력하세요.
-- 고객명 갖고 올때 select절에 스칼라 서브쿼리 사용
select os.cust_id,
       (select cs.name from customer cs where os.cust_id = cs.cust_id) name,
       sum(os.saleprice) "sum"
from orders os
group by os.cust_id
order by os.cust_id;

?-- 9. 고객별 날짜별 판매량 합계, group by 절대 두개 컬럼
SELECT os.cust_id, os.order_date,
       (SELECT cs.name FROM customer cs WHERE os.cust_id = cs.cust_id) AS name,
       SUM(os.saleprice) AS "sum"
FROM orders os
GROUP BY os.cust_id, os.order_date
ORDER BY os.cust_id;

-- 10.  고객 번호가 2 이하인 고객의 이름과 판매액을 출력하세요.
-- 1)  두 개의 테이블을 이너조인 + 오라클 SQL
--     두 테이블 간의 조인 조건을 where 절에 명시
select os.cust_id, cs.name, sum(saleprice) "sum"
from orders os, customer cs
where os.cust_id = cs.cust_id
  and os.cust_id <= 2
group by os.cust_id, cs.name
order by os.cust_id;

-- 2) 두 개의 테이블을 (ANSI 조인)
--     두 테이블 간의 조인 조건을 from 절에 명시
select os.cust_id, cs.name, sum(saleprice) "sum"
from orders os inner join customer cs on os.cust_id = cs.cust_id
where os.cust_id <= 2
group by os.cust_id, cs.name
order by os.cust_id;
?
-- 3) from절에 사용되는 서브쿼리(중첩질의, 인라인뷰)를 사용해서 구현
select os.cust_id, cs.name, sum(saleprice)
from orders os,
     (select cust_id, name from customer where cust_id <=2) cs
where os.cust_id = cs.cust_id
group by os.cust_id, cs.name
order by os.cust_id;

-- 4) with 절을 사용해서 서브쿼리와 같은 효과를 냄.
with
    cs as (select cust_id, name from customer where cust_id <=2)
select os.cust_id, cs.name, sum(saleprice)
from orders os, cs
where os.cust_id = cs.cust_id
group by os.cust_id, cs.name
order by os.cust_id;

-- 11. 가격이 20,000원 이상의 고가의 도서를 주문한 고객 명단과 전체 주문 합계액을 조회하시오.
select os.cust_id, cs.name, sum(os.saleprice)
from customer cs
         join orders os on cs.cust_id = os.cust_id
         join book b on os.book_id = b.book_id
where b.price >= 20000
group by os.cust_id, cs.name;

-- 12. 고객별 총 판매액을 구하되 주문액이 가장 큰 고객을 조회하시오.
-- 조회 방향(direction)을 바꿔가면서 최대/최소 판매 고객을 추출할 수 있다.
select os.cust_id, cs.name, sum(os.saleprice) as sum
from orders os
    LEFT OUTER join customer cs ON os.cust_id = cs.cust_id
group by os.cust_id, cs.name
order by sum asc
    fetch first 1 rows only;

-- 13. 도서를 한 번이라도 주문한 적이 있는 고객 명단을 출력하시오.
-- 1) 주문 테이블에서 고객 추출
select distinct os.cust_id
from orders os;

-- 2) 고객 테이블과 조인
select cs. cust_id, cs.name
from customer cs
where cs.cust_id in(
    select distinct os.cust_id
    from orders os
)
group by cs. cust_id, cs.name;

-- 3) 도서를 한 번이라도 주문한 적이 있는 고객 명단과 주문합계액을 출력하시오.
select cs. cust_id, cs.name, sum(os.saleprice) sum
from customer cs
    join orders os on cs.cust_id = os.cust_id
group by cs. cust_id, cs.name
order by sum desc;

-- 14. 한 번도 주문한 적이 없는 고객 명단을 출력하시오.

select cs.cust_id
from customer cs
where cs.cust_id not in(select distinct cust_id from orders);

-- 15. 상관부속질의 : 메인쿼리의 행을 이용하여 서브쿼리를 계산한다.
-- 출판사별로 출판사의 평균 도서 가격보다 비싼 도서를 구하시오.
-- 상위 쿼리의 결과를 하위 쿼리에 하나씩 전달해서 하위쿼리를
-- 계산해서 다시 상위쿼리와 비교
select b.book_name
from book b
where b.price > (select avg(b2.price)
                 from book b2
                 where b2.publisher = b.publisher);

/*** 뷰(View) 의 생성***/
Create View view_book
as select *
   from book
   where book_name like '%축구%';

select *
from view_book;

/********************************PL/SQL**********************************/
--1. 화면에 값 출력해주는 옵션 활성화
-- 세션에서 한 번 실행하면 세션 끝낼떄까지 사용 가능
set serveroutput on;

--2. 화면에 변수값 출력 단순 예제
-- 다음 PLSQL 블럭은 실행할 때마다 컴파일함.
declare
v_변수 number(4) := 1250;         --변수 선언과 초기화
    v_ename VARCHAR2(10);           -- 변수 선언
    v_con constant number(4) := 23;  -- 상수 선언과 초기화
    v_default number(2) default 10;               -- 기본값
begin
     V_ENAME := 'SCOTT'; -- 변수 초기화
     dbms_output.put_line('v_변수 : ' || v_변수);
     dbms_output.put_line('v_ename : ' || v_ename);
     dbms_output.put_line('상수 v_con : ' || v_con);
     dbms_output.put_line('v_default : ' || v_default);
end;

-- 2. 참조형(열) 자료형 : 특정 테이블의 해당 컬럼의 자료형과
--    동일한 자료형이 됨.

DECLARE
 -- dept의 deptno 열과 동일한 자료형으로 지정됨.
V_DEPTNO DEPT.DEPTNO%TYPE := 50;
BEGIN
   dbms_output.put_line('V_DEPTNO : ' || V_DEPTNO);
END;

-- 3. 참조형 변수 : 특정테이블의 행과 같은 형태의 자료형이 됨.
declare
    -- DEPT 테이블의 한 행을 참조할 수 있는 참조변수 선언
    -- 하나의 행에 대한 정보를 담을 수 있는 변수.
    -- select ~ into 참조변수
v_dept_row DEPT%ROWTYPE;
begin
select deptno, dname, location into v_dept_row
from DEPT
where DEPTNO = 40;
dbms_output.put_line('DEPTNO : ' || v_dept_row.DEPTNO);
   dbms_output.put_line('DNAME : ' || v_dept_row.DNAME);
   dbms_output.put_line('location : ' || v_dept_row.location);
end;

--4 if~else 제어문
declare
v_number number := 14;
begin
   if mod(v_number, 2) = 1 then
      dbms_output.put_line('v_number는 홀수입니다!');
else
      dbms_output.put_line('v_number는 짝수입니다!');
end if;
end;

--5. if~elseif~else
declare
v_score number := 87;
begin
   if v_score >= 90 then
      dbms_output.put_line('a학점');
   elsif v_score >= 80 then
      dbms_output.put_line('b학점');
   elsif v_score >= 70 then
      dbms_output.put_line('c학점');
   elsif v_score >= 60 then
      dbms_output.put_line('d학점');
else
      dbms_output.put_line('f학점');
end if;
end;


--6. 반복문 loop~end : 일단 반복한 후에 조건 비교해서
-- 참이면 계속 실행.
declare
v_num number := 0;
begin
   loop
dbms_output.put_line('현재 v_num : ' || v_num);
      v_num := v_num + 1;
      exit when v_num > 4;
end loop;
end;

-- while 반복문 : 반복 수행 여부를 결정하는 조건식을 먼저 지정하고
-- 조건식이 참일 경우 반복
declare
v_num number := 0;
begin
   while v_num < 4 loop
      dbms_output.put_line('현재 v_num : ' || v_num);
      v_num := v_num + 1;
end loop;
end;

-- for 반복문 : 반복의 횟수를 지정할 수 있음.
begin
for i in 0..4 loop
      dbms_output.put_line('현재 i의 값 : ' || i);
end loop;
end;

-- 7. 커서

-- 7.1 단일행 데이터 저장(커서 불필요)
 declare
v_dept_row dept%rowtype;
begin
select deptno, dname, location into v_dept_row
from dept
where deptno = 40;
dbms_output.put_line('deptno : ' || v_dept_row.deptno);
   dbms_output.put_line('dname : ' || v_dept_row.dname);
   dbms_output.put_line('location : ' || v_dept_row.location);
end;

-- 7.2 단일행 데이터를 저장하는 커서 사용
-- 커서 사용 순서 : 커서선언 - 커서열기-커서에서 읽은 데이터사용 - 커서닫기
declare
   -- 커서 데이터를 입력할 변수 선언
v_dept_row dept%rowtype;

   -- 명시적 커서 선언(declaration)
   -- 이런 쿼리문을 조회하고 그 결과를 담을 커서 선언
cursor v_커서변수 is
select deptno, dname, location
from dept
where deptno = 40;

begin
   -- 커서 열기(open)
open v_커서변수;

-- 커서로부터 읽어온 데이터 사용(fetch)
fetch v_커서변수 into v_dept_row;

dbms_output.put_line('=======================');
   dbms_output.put_line('deptno : ' || v_dept_row.deptno);
   dbms_output.put_line('dname : ' || v_dept_row.dname);
   dbms_output.put_line('location : ' || v_dept_row.location);

   -- 커서 닫기(close)
close v_커서변수;

end;

--7.3 여러행이 조회되는 경우 사용하는 Loop문
declare
   -- 커서 데이터를 입력할 참조 변수 선언
   -- dept 테이블의 한 행을 담을 수 있는 변수
v_dept_row dept%rowtype;

   -- 명시적 커서 선언(declaration)
   -- 조회 결과를 반복해서 돌릴 수 있는 커서 선언
cursor v_커서변수 is
select deptno, dname, location
from dept;

begin
   -- 커서 열기(open)
open v_커서변수;

loop
-- 커서로부터 읽어온 데이터 사용(fetch)
fetch v_커서변수 into v_dept_row;

      -- 커서의 모든 행을 읽어오기 %notfound 속성 지정
      -- 추출된 행이 있으면 false, 없으면 true
      exit when v_커서변수%notfound;

      dbms_output.put_line('deptno : ' || v_dept_row.deptno);
      dbms_output.put_line('dname : ' || v_dept_row.dname);
      dbms_output.put_line('location : ' || v_dept_row.location);
end loop;

   -- 커서 닫기(close)
close v_커서변수;

end;


-- 7.4 for loop문을 통한 커서 반복하기
declare
   -- 명시적 커서 선언(declaration)
cursor v_커서변수 is
select deptno, dname, location
from dept;

begin
   -- 커서 for loop 시작 (자동 open, fetch, close)
   -- v_루프인덱스 : 커서에 저장된 각 행이 저장되는 변수
for v_루프인덱스 in v_커서변수 loop
      dbms_output.put_line('deptno : ' || v_루프인덱스.deptno);
      dbms_output.put_line('dname : ' || v_루프인덱스.dname);
      dbms_output.put_line('location : ' || v_루프인덱스.location);
end loop;
end;


--8. 저장 프로시저

-- 8.1
create or replace procedure pro_noparam
is
   v_empno number(4) := 7788;
   v_ename varchar2(10);
begin
   v_ename := 'scott';
   dbms_output.put_line('v_empno : ' || v_empno);
   dbms_output.put_line('v_ename : ' || v_ename);
end;

--8.2 파라미터가 있는 프로시저
create or replace procedure pro_param_in
(
   --프로시저 실행에 필요한 값을 받는 파라미터
   -- in 단어는 생략 가능함.
   param1 in number,
   param2 number,
   param3 number := 3,
   param4 number default 4
)
is

begin
   dbms_output.put_line('param1 : ' || param1);
   dbms_output.put_line('param2 : ' || param2);
   dbms_output.put_line('param3 : ' || param3);
   dbms_output.put_line('param4 : ' || param4);
end;

-- 프로시저 실행하기
begin
    pro_param_in(1, 2, 9, 8);
end;


create or replace procedure pro_param_in
(
   param1 in number,
   param2 number,
   param3 number := 3,
   param4 number default 4
)
is

begin
   dbms_output.put_line('param1 : ' || param1);
   dbms_output.put_line('param2 : ' || param2);
   dbms_output.put_line('param3 : ' || param3);
   dbms_output.put_line('param4 : ' || param4);
end;
/


CREATE OR REPLACE PROCEDURE update_sal
     /* IN  Parameter */
     (v_empno    IN    NUMBER)
     IS
BEGIN
UPDATE emp
SET sal = sal  * 1.1
WHERE empno = v_empno;
COMMIT;
END update_sal;

