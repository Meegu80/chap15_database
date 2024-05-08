package com.javalab.school.execution2;

import com.javalab.school.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 학생 목록을 조회하는 프로그램
 */
public class StudentSelect {
    public static void main(String[] args) {
        Connection conn = null;
        conn = DatabaseConnection.getConnection(); // 커넥션 전담 객체에서 커넥션 얻기
        displayStudents(conn); // 학생 조회 메소드 호출
    } // end of main

    private static void displayStudents(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try  {
            String sql = "SELECT s.student_id, s.name, s.year, s.address, s.department_id " +
                    "FROM student s " +
                    "ORDER BY s.student_id";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("등록된 학생 목록:");
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String name = rs.getString("name");
                int year = rs.getInt("year");
                String address = rs.getString("address");
                int departmentId = rs.getInt("department_id");
                System.out.println(studentId + "\t" + name + "\t" + year + "\t" + address + "\t" + departmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                // 자원 해제 - 자원이 가장 늦게 만들어진 순서로 해제
                // ResultSet 해제
                if (rs != null) rs.close();
                // PreparedStatement 해제
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("-----------------------------------------------------------------------");
    } // end of displayStudents
}   // end of class
