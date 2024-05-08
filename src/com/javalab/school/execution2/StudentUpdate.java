package com.javalab.school.execution2;

import com.javalab.school.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 학생 데이터를 수정하는 클래스
 */
public class StudentUpdate {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        conn = DatabaseConnection.getConnection();
        updateStudent(conn, scanner);
    } // end of main

    private static void updateStudent(Connection conn, Scanner scanner) {
		// PreparedStatement 객체 선언
        PreparedStatement pstmt = null;
        try {
            System.out.println("수정할 학생의 ID를 입력하세요: ");
            String id = scanner.nextLine();
            // 사용자로부터 입력 받기
            System.out.print("새 주민번호: ");
            String jumin = scanner.nextLine();
            System.out.print("새 이름: ");
            String name = scanner.nextLine();
            System.out.print("새 학년: ");
            int year = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리
            System.out.print("새 주소: ");
            String address = scanner.nextLine();
            System.out.print("새 학과 코드: ");
            int departmentId = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            // SQL 쿼리문 작성
            String sql = "UPDATE student SET jumin = ?, name = ?, year = ?, address = ?, department_id = ? WHERE student_id = ?";
            pstmt = conn.prepareStatement(sql);

            // PreparedStatement에 파라미터 설정
            pstmt.setString(1, jumin);
            pstmt.setString(2, name);
            pstmt.setInt(3, year);
            pstmt.setString(4, address);
            pstmt.setInt(5, departmentId);
            pstmt.setString(6, id);

            // SQL 실행
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("학생 정보가 성공적으로 업데이트 되었습니다.");
            } else {
                System.out.println("해당 ID의 학생을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
			
		}
    }
}
