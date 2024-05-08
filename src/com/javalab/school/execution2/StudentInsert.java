package com.javalab.school.execution2;

import com.javalab.school.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 학생 데이터를 등록하는 클래스
 */
public class StudentInsert {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        conn = DatabaseConnection.getConnection(); // 커넥션 전담 객체에서 커넥션 얻기
        registerStudent(conn, scanner);
    } // end of main

    private static void registerStudent(Connection conn, Scanner scanner) {
        System.out.println("[새 학생 입력]");
        System.out.print("학생 ID: ");
        String id = scanner.nextLine();
        System.out.print("주민번호: ");
        String jumin = scanner.nextLine();
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("학년: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("주소: ");
        String address = scanner.nextLine();
        System.out.print("학과 코드: ");
        int department = scanner.nextInt();
        scanner.nextLine();

        String sql = "INSERT INTO student (student_id, jumin, name, year, address, department_id) " +
                " VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try  {
            // 커넥션 객체로부터 PreparedStatement 객체를 얻어냄
            // PreparedStatement.prepareStatement(실행할 쿼리문) : 쿼리 실행
            pstmt = conn.prepareStatement(sql);
            // 쿼리문의 ? 자리에 값을 채워넣음
            pstmt.setString(1, id);
            pstmt.setString(2, jumin);
            pstmt.setString(3, name);
            pstmt.setInt(4, year);
            pstmt.setString(5, address);
            pstmt.setInt(6, department);
            // 쿼리문의 파라미터인 ? 를 채운 후 쿼리 실행
            int affectedRow = pstmt.executeUpdate(); // 쿼리 실행 저장/수정/삭제는 executeUpdate() 메소드 사용
            System.out.println("학생이 성공적으로 등록되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            scanner.close();
//            if(conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
