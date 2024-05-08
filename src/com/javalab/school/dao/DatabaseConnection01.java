package com.javalab.school.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection01 {
    public static void main(String[] args) {
        try {
            // JDBC 드라이버 클래스의 경로
            String driverClassName = "oracle.jdbc.OracleDriver";

            // 드라이버 클래스를 동적으로 로드, 로딩된 드라이버는 자동으로 DriverManager에 등록됨.
            Class.forName(driverClassName);

            // 연결할 데이터베이스의 정보
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "school";
            String password = "1234";

            // DriverManager를 통해 Connection 객체를 생성
            // 매개변수로 연결할 데이터베이스의 URL, 사용자 이름, 비밀번호를 전달
            Connection connection = DriverManager.getConnection(dbUrl, username, password);

            // 연결 성공 메시지 출력
            System.out.println("데이터베이스 커넥션 객체 생성 성공");

            // 연결 종료
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
