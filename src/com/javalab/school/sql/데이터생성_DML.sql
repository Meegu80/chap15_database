/********* 데이터 삽입 ***************/
/* 학과 데이터 삽입 */
INSERT INTO department(department_id, name, office) VALUES(920, '컴퓨터공학과', '201호');
INSERT INTO department(department_id, name, office) VALUES(923, '산업공학과', '207호');
INSERT INTO department(department_id, name, office) VALUES(925, '전자공학과', '308호');

/* 학생 데이터 삽입 */
INSERT INTO student(student_id, jumin, name, year, address, department_id) VALUES('1292001', '900424-1825409', '김광식', 3, '서울', 920);
INSERT INTO student(student_id, jumin, name, year, address, department_id) VALUES('1292002', '900305-2730021', '김정현', 3, '서울', 923);
INSERT INTO student(student_id, jumin, name, year, address, department_id) VALUES('1292003', '891021-2308302', '김현정', 4, '대전', 925);
INSERT INTO student(student_id, jumin, name, year, address, department_id) VALUES('1292301', '880902-2704012', '김정숙', 2, '대구', 923);
INSERT INTO student(student_id, jumin, name, year, address, department_id) VALUES('1292303', '910715-1524390', '박광수', 3, '광주', 920);
INSERT INTO student(student_id, jumin, name, year, address, department_id) VALUES('1292305', '921011-1809003', '김우주', 4, '부산', 925);
INSERT INTO student(student_id, jumin, name, year, address, department_id) VALUES('1292501', '900825-1506390', '박철수', 3, '대전', 923);
INSERT INTO student(student_id, jumin, name, year, address, department_id) VALUES('1292502', '911011-1809003', '백태성', 3, '서울', 925);

/* 교수 데이터 삽입 */
INSERT INTO professor(professor_id, jumin, name, department_id, grade, hiredate) VALUES('92001', '590327-1839240', '이태규', 920, '교수', TO_DATE('1997-03-02', 'yyyy-MM-dd'));
INSERT INTO professor(professor_id, jumin, name, department_id, grade, hiredate) VALUES('92002', '690702-1350026', '고희석', 923, '교수', TO_DATE('2003-05-10', 'yyyy-MM-dd'));
INSERT INTO professor(professor_id, jumin, name, department_id, grade, hiredate) VALUES('92301', '741011-2765501', '최성희', 920, '부교수', TO_DATE('2005-03-11', 'yyyy-MM-dd'));
INSERT INTO professor(professor_id, jumin, name, department_id, grade, hiredate) VALUES('92302', '750728-1102458', '김태석', 923, '부교수', TO_DATE('1999-08-26', 'yyyy-MM-dd'));
INSERT INTO professor(professor_id, jumin, name, department_id, grade, hiredate) VALUES('92501', '620505-1200546', '박철재', 925, '교수', TO_DATE('2007-01-05', 'yyyy-MM-dd'));
INSERT INTO professor(professor_id, jumin, name, department_id, grade, hiredate) VALUES('92502', '740101-1830264', '장민석', 920, '조교수', TO_DATE('2005-07-18', 'yyyy-MM-dd'));

/* 강좌(course), 강의, 과목 데이터 삽입 */
INSERT INTO course (course_id, name, credit) VALUES ('C101', '전산개론', 3);
INSERT INTO course (course_id, name, credit) VALUES ('C102', '자료구조', 3);
INSERT INTO course (course_id, name, credit) VALUES ('C103', '데이터베이스', 4);
INSERT INTO course (course_id, name, credit) VALUES ('C301', '운영체제', 3);
INSERT INTO course (course_id, name, credit) VALUES ('C302', '컴퓨터구조', 3);
INSERT INTO course (course_id, name, credit) VALUES ('C303', '이산수학', 4);
INSERT INTO course (course_id, name, credit) VALUES ('C304', '계산이론', 4);
INSERT INTO course (course_id, name, credit) VALUES ('C501', '인공지능', 3);
INSERT INTO course (course_id, name, credit) VALUES ('C502', '웹프로그래밍', 2);
COMMIT;

/* 수업(class) 데이터 삽입 */
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C101-01', 'C101', 2012, '1', '92301', '301호', 40);
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C102-01', 'C102', 2012, '1', '92001', '209호', 30);
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C103-01', 'C103', 2012, '1', '92501', '208호', 30);
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C103-02', 'C103', 2012, '1', '92301', '301호', 30);
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C501-01', 'C501', 2012, '1', '92501', '103호', 45);
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C501-02', 'C501', 2012, '1', '92502', '204호', 25);
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C301-01', 'C301', 2012, '2', '92502', '301호', 30);
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C302-01', 'C302', 2012, '2', '92501', '209호', 45);
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C502-01', 'C502', 2012, '2', '92001', '209호', 30);
INSERT INTO class (class_id, course_id, year, semester, professor_id, classroom, enroll) VALUES
('C502-02', 'C502', 2012, '2', '92301', '103호', 26);

-- 성정 데이터 삽입
INSERT INTO takes (student_id, class_id, score) VALUES('1292001', 'C101-01', 'B+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292001', 'C103-01', 'A+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292001', 'C301-01', 'A');
INSERT INTO takes (student_id, class_id, score) VALUES('1292002', 'C102-01', 'A');
INSERT INTO takes (student_id, class_id, score) VALUES('1292002', 'C103-01', 'B+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292002', 'C502-01', 'C+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292003', 'C103-02', 'B');
INSERT INTO takes (student_id, class_id, score) VALUES('1292003', 'C501-02', 'A+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292301', 'C102-01', 'C+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292303', 'C102-01', 'C');
INSERT INTO takes (student_id, class_id, score) VALUES('1292303', 'C103-02', 'B+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292303', 'C501-01', 'A+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292303', 'C502-01', 'B');
INSERT INTO takes (student_id, class_id, score) VALUES('1292305', 'C102-01', 'B');
INSERT INTO takes (student_id, class_id, score) VALUES('1292305', 'C103-01', 'C+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292305', 'C501-02', 'A');
INSERT INTO takes (student_id, class_id, score) VALUES('1292305', 'C301-01', 'A+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292305', 'C502-01', 'A+');
INSERT INTO takes (student_id, class_id, score) VALUES('1292501', 'C101-01', 'B');
INSERT INTO takes (student_id, class_id, score) VALUES('1292501', 'C102-01', 'B');
commi;

/** 수강(takes) 데이터 삽입 */
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(1, '1292001', 'C101-01', '대기', SYSDATE);
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(2, '1292002', 'C102-01', '대기', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(3, '1292003', 'C103-01', '대기', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(4, '1292301', 'C301-01', '대기', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(5, '1292303', 'C302-01', '대기', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(6, '1292305', 'C303-01', '대기', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(7, '1292501', 'C304-01', '승인', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(8, '1292502', 'C501-01', '승인', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(9, '1292001', 'C502-01', '승인', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(10, '1292002', 'C103-02', '승인', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(11, '1292003', 'C501-02', '승인', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(12, '1292301', 'C302-01', '승인', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(13, '1292303', 'C502-02', '승인', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(14, '1292305', 'C103-01', '승인', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(15, '1292501', 'C302-01', '취소', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(16, '1292502', 'C501-01', '취소', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(17, '1292001', 'C502-01', '취소', SYSDATE),
INSERT INTO enrollment (enroll_id, student_id, class_id, status, enrollment_date) VALUES(18, '1292002', 'C103-02', '취소', SYSDATE);

commit;