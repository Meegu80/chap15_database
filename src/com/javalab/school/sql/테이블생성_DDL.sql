
/* takes 테이블에 생성된 모든 제약 조회 */
SELECT constraint_name
FROM user_constraints
WHERE table_name = 'TAKES';


/* 학과 테이블 */
-- drop table department cascade CONSTRAINTS; -- cascade constraints : 참조하는 하위 테이블이 있어도 강제 삭제
create table department(
   department_id number primary key,
   name varchar2(20) not null,
   office varchar2(20) not null
);

/* 학생 테이블 */
create table student(
    student_id varchar2(20) primary key, -- primary key : 기본키 설정
    jumin varchar2(20) not null, -- not null : null 값 비허용
    name varchar2(10) not null,
    year number(4) not null,
    address varchar2(100),
    department_id number(5)
);

/* 학생 테이블에 외래키 제약 설정*/
alter table student
    add constraint fk_student_department_id
        foreign key(department_id)
            references department(department_id);

/* 교수 테이블 생성 */
create table professor(
  professor_id varchar2(20) primary key,
  jumin varchar2(20) not null,
  name varchar2(20) not null,
  department_id number(5),
  grade varchar2(20),
  hiredate date default sysdate -- 기본으로 현재 날짜를 저장
);

/* 교수 테이블에 학과 외래키 설정 */
alter table professor
    add constraint fk_professor_department_id
        foreign key(department_id)
            references department(department_id);


/* 강좌, 강의, 과목 테이블 */
--drop table takes;
create table course(
   course_id varchar2(20) constraint pk_course_id primary key,  /* 강좌번호 */
   name varchar2(20) not null,  /* 강좌명 */
   credit number(2) not null, /* 취득 학점 */
   description varchar2(100)
);




/* 클래스(수업) */
-- drop table class;
create table class(
  class_id varchar2(20) constraint pk_class_id primary key,
  course_id varchar2(20), /* 어떤 강좌의 수업인가? */
  year number not null,   /* 학년도 */
  semester varchar2(10) not null,   /* 학기(봄/여름/가을) */
  professor_id varchar2(20), /* 수업 담당 교수 */
  classroom varchar2(50),    /* 수업이 진행되는 교실 */
  enroll number default 0,   /* 수강신청 인원 */
  constraint fk_class_course_id foreign key(course_id) references course(course_id),
  constraint fk_class_professor_id foreign key(professor_id) references professor(professor_id)
);

-- 성적 테이블의 컬럼명 변경 subject -> class_id
alter table takes rename column subject to class_id;

-- 성적 테이블의 subject -> class_id 변경에 따른 외래키 설정
alter table takes add constraint fk_takes_class_id foreign key(class_id)
references class(class_id);

-- takes 테이블 복사본 테이블 생성
--create table takes2
--as
--select * from takes;

/* 성적 테이블 */
--drop table takes;
create table takes(
  student_id varchar2(20),
  class_id varchar2(20),
  score varchar2(20),
  CONSTRAINT pk_takes primary key (student_id, class_id), -- 복합키(composite key)
  CONSTRAINT fk_takes_student_id FOREIGN KEY (student_id) REFERENCES student(student_id),
  constraint fk_takes_class_id foreign key(class_id)references class(class_id)-- 외래키
);


/* 수강(Enrollment) */
create table enrollment(
   enroll_id number constraint pk_enrollment_id primary key,    /* 수강신청번호 */
   student_id varchar2(20), /* 수강신청한 학생 */
   class_id varchar2(20), /* 수강신청한 수업 */
   status varchar2(20) DEFAULT '대기',  /* 수강신청 상태: 담당교수가 승인하기전 대기, 담당교수승인, 등록, 취소 등 */
   grade varchar2(5) default null, /* 성적 A+, A, B */
   enrollment_date date DEFAULT sysdate,  /* 수강신청 날짜 */
   constraint fk_enrollment_student_id foreign key(student_id) references student(student_id),
   constraint fk_enrollment_class_id foreign key(class_id) references class(class_id)
);


