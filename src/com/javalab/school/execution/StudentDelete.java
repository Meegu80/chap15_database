package com.javalab.school.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 학생 데이터를 삭제하는 클래스
 */
public class StudentDelete {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");

            System.out.println("DB 접속 성공");

            deleteStudent(conn, scanner);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // end of main

    private static void deleteStudent(Connection conn, Scanner scanner) {
        PreparedStatement pstmt = null;
        try {
            System.out.println("삭제할 학생의 ID를 입력하세요: ");
            String id = scanner.nextLine();

            // SQL 쿼리문 작성
            String sql = "DELETE FROM student WHERE student_id = ?";

            // PreparedStatement 생성
            pstmt = conn.prepareStatement(sql);

            // PreparedStatement에 파라미터 설정
            pstmt.setString(1, id);

            // SQL 실행
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("학생 정보가 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("해당 ID의 학생을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
