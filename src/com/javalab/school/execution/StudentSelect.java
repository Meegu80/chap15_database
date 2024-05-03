package com.javalab.school.execution;

import java.sql.*;

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
 */
public class StudentSelect {
    // 오라클 db에 접속해서 하기 위한 정보
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
    }


    private static void displayStudents(Connection conn) {
        System.out.println("등록된 학생 목록:");
        String sql = "SELECT s.student_id, s.name, s.year, s.address, s.department_id " +
                "FROM student s " +
                "ORDER BY s.student_id";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String name = rs.getString("name");
                int year = rs.getInt("year");
                String address = rs.getString("address");
                int departmentId = rs.getInt("department_id");
                System.out.println(studentId + "\t" + name + "\t" + year + "\t"
                        + address + "\t" + departmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------------------------------");
    } // end of di
}// splayStudents