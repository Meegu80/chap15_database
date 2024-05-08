package com.javalab.school.execution;

import com.javalab.school.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 학생 목록을 조회하는 프로그램
 *
 * [데이터베이스 접속 순서]
 * 1. JDBC 드라이버 로딩 : Class.forName("oracle.jdbc.OracleDriver");
 * 2. 접속 정보 설정 : DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
 * 3. SQL문 실행 : Connection 객체를 이용해서 PreparedStatement 객체 생성
 * 4. SQL문 실행 : PreparedStatement 객체를 이용해서 ResultSet 객체 생성
 * 5. 결과 처리 : ResultSet 객체를 이용해서 결과 처리
 * 6. 접속 종료 : Connection 객체를 이용해서 접속 종료
 * 7. 자원 해제 : ResultSet, PreparedStatement, Connection 객체를 이용해서 자원 해제
 * 8. 예외 처리 : try-catch-finally 블록을 이용해서 예외 처리
 *
 */
public class StudentSelect {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            // 학생 목록 메소드 호출
            displayStudents(conn);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // end of main

    private static void displayStudents(Connection conn) {
        // 학생 목록 저장용 ArrayList
        List<Student> studentList = new ArrayList<Student>();

        System.out.println("등록된 학생 목록:");
        // 쿼리문 작성
        String sql = "SELECT s.student_id, s.name, s.year, s.address, s.department_id " +
                    "FROM student s " +
                    "ORDER BY s.student_id";

        // PreparedStatement 객체 선언
        PreparedStatement pstmt = null ;
        ResultSet rs = null;
        try  {
            // Connection 구현 객체의 prepareStatement 메소드에 쿼리문을 전달해서
            // 쿼리문을 실행할 수 있도록 준비를 하는 PreparedStatement 객체를 생성한다.
            // PreparedStatement 객체에 동적으로 전달받는 파라미터(?)가 있다면 이를 세팅하는
            // 과정을 거쳐야 한다.
            pstmt = conn.prepareStatement(sql);
            // PreparedStatement 객체를 통해서 쿼리를 실행하고 결과를 ResultSet 객체를
            // 통해서 반환 받는다.
            rs = pstmt.executeQuery(); // 조회할때는 executeQuery() 메소드 사용

            // while문을 이용해서 ResultSet 객체에 담긴 결과를 하나씩 꺼내서 출력
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String name = rs.getString("name");
                int year = rs.getInt("year");
                String address = rs.getString("address");
                int departmentId = rs.getInt("department_id");
                System.out.println(studentId + "\t" + name + "\t" + year + "\t" + address + "\t" + departmentId);
                // ResultSet 에 있는 행들을 하나씩 학생 객체
                Student s = new Student(studentId, name, year, address, departmentId);
                studentList.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // ResultSet, PreparedStatement, Connection 순으로 닫기
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("-----------------------------------------------------------------------");
    } // end of displayStudents
}   // end of class
