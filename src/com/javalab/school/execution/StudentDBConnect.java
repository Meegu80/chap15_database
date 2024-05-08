package com.javalab.school.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 데이터베이스 접속 테스트 클래스
 * 1. JDBC 드라이버 로딩 : Class.forName("oracle.jdbc.OracleDriver");
 * 2. 접속 정보 설정 : DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234"); *
 */
public class StudentDBConnect {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        // 오라클 DB에 접속하기 위한 커넥션 객체
        Connection conn = null;
        try {
            // JDBC 드라이버 로딩(오라클 ojdbc7.jar 파일 필요)
            // JDBC 드라이버가 있어야 자바 프로그래에서 오라클 데이터베이스에 접속할 수 있음
            // 오라클 드라이버를 로딩하면 JDBC 드라이버가 DriverManager에 등록됨.
            Class.forName("oracle.jdbc.OracleDriver");

            // 오라클 DB에 접속하기 위한 커넥션 객체 얻기
            // DriverManager에게서 커넥션을 요청해서 얻어내야 하다.
            // 오라클 데이터베이스 접속 문자열 : jdbc:oracle:thin:@localhost:1521:orcl
            // user : 오라클 사용자 계정
            // password : 오라클 사용자 계정 비밀번호
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");

            System.out.println("DB 접속 성공");
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
}
