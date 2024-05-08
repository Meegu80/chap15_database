package com.javalab.school.execution;


import com.javalab.school.dao.DatabaseConnection;
import com.javalab.school.data.DataRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class SchoolManagementEx {
    // 본 클래스가 로드 되는 시점에 DB 연결시키고 커넥션 객체의 참조 주소 보관.
    private static Connection conn = DatabaseConnection.getConnection();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            mainaMenu();    // 메인 메뉴 표시

        }
    } // end of main

    private static void mainaMenu(){
        System.out.println("=============================");
        System.out.println("학사 행정관리 시스템");
        System.out.println("=============================");
        System.out.println("1. 학생 관련 업무");
        System.out.println("2. 교수 관련 업무");
        System.out.println("3. 학과 관련 업무");
        System.out.println("4. 성적 관련 업무");
        System.out.println("5. 강의 관련 업무");
        System.out.println("6. 수업 관련 업무");
        System.out.println("7. 수강 신청 관련 업무");
        System.out.println("9. 종료");
        System.out.println("=============================");
        System.out.print("메뉴 선택: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                studentMenu();
                break;
            case 2:
                professorMenu();
                break;
            case 3:
                departmentMenu();
                break;
            case 4:
                takesMenu();
                break;
            case 5:
                courseMenu();
                break;
            case 6:
                classMenu();
                break;
            case 7:
                enrollmentMenu();
                break;
            case 9:
                System.out.println("프로그램을 종료합니다.");
                exit();
                return;
            default:
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
        }
    }

    private static void studentMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=============================");
            System.out.println("1. 학생 등록");
            System.out.println("2. 학생 조회");
            System.out.println("3. 학생 정보 수정");
            System.out.println("4. 학생 정보 삭제");
            System.out.println("5. 메인 메뉴로 가기");
            System.out.println("=============================");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    registerStudent(scanner);
                    break;
                case 2:
                    displayStudents();
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    deleteStudent(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 학생 등록
     */
    private static void registerStudent(Scanner scanner) {
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

        PreparedStatement pstmt = null;
        try  {
            String sql = "INSERT INTO student (student_id, jumin, name, year, address, department_id) VALUES (?, ?, ?, ?, ?, ?)";

            // 쿼리문을 실행할 수 있도록 준비를 하는 PreparedStatement 객체를 생성한다.
            // PreparedStatement 객체를 생성할 때는 SQL 쿼리문을 전달한다.
            pstmt = conn.prepareStatement(sql);
            // 쿼리문에 있는 ?표에 전달할 파라미터를 세팅한다.
            pstmt.setString(1, id); // 첫번째 ?에 문자열값 세팅
            pstmt.setString(2, jumin);  // 두번째 ?에 문자열값 세팅
            pstmt.setString(3, name);
            pstmt.setInt(4, year);  // 세번째 ?에는 int값 세팅
            pstmt.setString(5, address);
            pstmt.setInt(6, department);
            // 쿼리문 실행
            int i = pstmt.executeUpdate();  // 저장된 건수를 받아온다.(1-성공, 0-실패)
            if (i > 0) {
                System.out.println("학생이 성공적으로 등록되었습니다.");
            } else {
                System.out.println("학생 등록에 실패했습니다.");
            }
        }  catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 성적 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }finally {
            // PreparedStatement 객체가 사용중이면 즉 널이 아니면 닫기
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 학생 목록 조회
     */
    private static void displayStudents() {
        // 쿼리문을 실행해줄 PreparedStatement 객체 선언
        PreparedStatement pstmt = null;
        // 쿼리문 실행 결과를 저장할 ResultSet 객체 선언
        ResultSet rs = null;
        try {
            System.out.println("등록된 학생 목록:");
            // 쿼리문 작성, 쿼리문을 연결할 때는 한 칸씩 띄어쓰기
            String sql = "SELECT s.student_id, s.name, s.year, s.address, s.department_id, d.name department_name " +
                    " FROM student s LEFT JOIN department d ON s.department_id = d.department_id " +
                    " ORDER BY s.student_id";

            // 쿼리문을 실행할 수 있도록 준비를 하는 PreparedStatement 객체를 생성
            pstmt = conn.prepareStatement(sql);
            // 쿼리문을 실행하고 결과를 ResultSet 객체에 저장
            rs = pstmt.executeQuery();
            // ResultSet 객체에 있는 결과를 하나씩 꺼내서 출력
            // 처음에 있는 행을 읽기 위해서 next() 메소드를 호출
            while (rs.next()) {
                // 문법 : getString(컬럼명) 또는 getString(컬럼인덱스), 숫자를 읽을 때는 getInt(컬럼명)
                String studentId = rs.getString("student_id");
                String name = rs.getString("name");
                int year = rs.getInt("year");
                String address = rs.getString("address");
                int departmentId = rs.getInt("department_id");
                String departmentName = rs.getString("department_name");
                System.out.printf("%-10s %-15s %-5d %-20s %-10d %-15s\n", studentId, name, year, address, departmentId, departmentName);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // ResultSet, PreparedStatement 순으로 닫기
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
    }

    /**
     * 학생 정보 수정
     */
    private static void updateStudent(Scanner scanner) {
            System.out.println("수정할 학생의 ID를 입력하세요: ");
            String id = scanner.nextLine();
            // 사용자로부터 수정할 값 입력 받기
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

        // PreparedStatement 선언
        PreparedStatement pstmt = null;
        try {
            // SQL 쿼리문 작성
            String sql = "UPDATE student SET jumin = ?, name = ?, year = ?, address = ?, department_id = ? WHERE student_id = ?";

            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);

            // PreparedStatement에 파라미터 설정
            pstmt.setString(1, jumin);
            pstmt.setString(2, name);
            pstmt.setInt(3, year);
            pstmt.setString(4, address);
            pstmt.setInt(5, departmentId);
            pstmt.setString(6, id);

            // SQL 실행후 수정된 행의 수 리턴
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("학생 정보가 성공적으로 업데이트 되었습니다.");
            } else {
                System.out.println("해당 ID의 학생을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 학생 정보 삭제
     */
    private static void deleteStudent( Scanner scanner) {
        System.out.println("삭제할 학생의 ID를 입력하세요: ");
        String id = scanner.nextLine();

        // 사용자로부터 삭제할 학생 ID 입력 받기
        PreparedStatement pstmt = null;
        try {

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

    /**
     * 교수 관련 메뉴
     */
    private static void professorMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=============================");
            System.out.println("1. 교수 등록");
            System.out.println("2. 교수 조회");
            System.out.println("3. 교수 정보 수정");
            System.out.println("4. 교수 정보 삭제");
            System.out.println("5. 메인 메뉴로 가기");
            System.out.println("=============================");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    registerProfessor(scanner);
                    break;
                case 2:
                    displayProfessors();
                    break;
                case 3:
                    updateProfessor(scanner);
                    break;
                case 4:
                    deleteProfessor(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 교수 등록
     */
    private static void registerProfessor(Scanner scanner) {
        System.out.println("[새 교수 등록]");
        System.out.print("교수 ID: ");
        String id = scanner.nextLine();
        System.out.print("주민번호: ");
        String jumin = scanner.nextLine();
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("학과 코드: ");
        int department = scanner.nextInt();
        scanner.nextLine();
        System.out.print("직급: ");
        String grade = scanner.nextLine();
        System.out.print("채용 날짜(예: 2024-01-01): ");
        String hiredate = scanner.nextLine();

        String sql = "INSERT INTO professor (professor_id, jumin, name, department_id, grade, hiredate) VALUES (?, ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'))";

        // try~with~resources 구문을 사용하면 close()를 명시적으로 호출하지 않아도 자동 close
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, jumin);
            pstmt.setString(3, name);
            pstmt.setInt(4, department);
            pstmt.setString(5, grade);
            pstmt.setString(6, hiredate);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("교수가 성공적으로 등록되었습니다.");
            } else {
                System.out.println("교수 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 교수 목록 조회
     */
    private static void displayProfessors() {
        System.out.println("[등록된 교수 목록 조회]");
        String sql = "SELECT p.professor_id, p.name, p.grade, p.hiredate, d.name as department_name FROM professor p JOIN department d ON p.department_id = d.department_id ORDER BY p.professor_id";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("professor_id");
                String name = rs.getString("name");
                String grade = rs.getString("grade");
                Date hiredate = rs.getDate("hiredate");
                String departmentName = rs.getString("department_name");
                System.out.printf("%-10s %-15s %-10s %-20s %-15s\n", id, name, grade, hiredate.toString(), departmentName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 교수 정보 수정
     */
    private static void updateProfessor(Scanner scanner) {
        System.out.println("[교수 정보 수정]");
        System.out.print("수정할 교수의 ID를 입력하세요: ");
        String id = scanner.nextLine();
        System.out.print("새 주민번호: ");
        String jumin = scanner.nextLine();
        System.out.print("새 이름: ");
        String name = scanner.nextLine();
        System.out.print("새 학과 코드: ");
        int department = scanner.nextInt();
        scanner.nextLine();
        System.out.print("새 직급: ");
        String grade = scanner.nextLine();
        System.out.print("새 채용 날짜(예: 2024-01-01): ");
        String hiredate = scanner.nextLine();

        String sql = "UPDATE professor SET jumin = ?, name = ?, department_id = ?, grade = ?, hiredate = TO_DATE(?, 'YYYY-MM-DD') WHERE professor_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jumin);
            pstmt.setString(2, name);
            pstmt.setInt(3, department);
            pstmt.setString(4, grade);
            pstmt.setString(5, hiredate);
            pstmt.setString(6, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("교수 정보가 성공적으로 업데이트 되었습니다.");
            } else {
                System.out.println("해당 ID의 교수를 찾을 수 없습니다.");
            }
        }  catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 교수 정보 삭제
     */
    private static void deleteProfessor(Scanner scanner) {
        System.out.println("[교수 정보 삭제]");
        System.out.print("삭제할 교수의 ID를 입력하세요: ");
        String id = scanner.nextLine();

        String sql = "DELETE FROM professor WHERE professor_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("교수 정보가 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("해당 ID의 교수를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 학과 관련 메뉴
     */
    private static void departmentMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=============================");
            System.out.println("1. 학과 등록");
            System.out.println("2. 학과 조회");
            System.out.println("3. 학과 정보 수정");
            System.out.println("4. 학과 정보 삭제");
            System.out.println("5. 메인 메뉴로 가기");
            System.out.println("=============================");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    registerDepartment(scanner);
                    break;
                case 2:
                    displayDepartments();
                    break;
                case 3:
                    updateDepartment(scanner);
                    break;
                case 4:
                    deleteDepartment(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 학과 등록
     */
    private static void registerDepartment(Scanner scanner) {
        System.out.println("[학과 등록]");
        System.out.print("학과 코드: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("학과 이름: ");
        String name = scanner.nextLine();
        System.out.print("교실 위치: ");
        String office = scanner.nextLine();

        String sql = "INSERT INTO department (department_id, name, office) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, office);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("학과가 성공적으로 등록되었습니다.");
            } else {
                System.out.println("학과 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 학과 목록 조회
     */
    private static void displayDepartments() {
        System.out.println("[등록된 학과 목록 조회]");
        String sql = "SELECT department_id, name, office FROM department ORDER BY department_id";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("department_id");
                String name = rs.getString("name");
                String office = rs.getString("office");
                System.out.printf("%-10d %-20s %-10s\n", id, name, office);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 학과 정보 수정
     */
    private static void updateDepartment(Scanner scanner) {
        System.out.println("[학과 정보 수정]");
        System.out.print("수정할 학과의 코드를 입력하세요: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("새 학과 이름: ");
        String name = scanner.nextLine();
        System.out.print("새 교실 위치: ");
        String office = scanner.nextLine();

        String sql = "UPDATE department SET name = ?, office = ? WHERE department_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, office);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("학과 정보가 성공적으로 업데이트 되었습니다.");
            } else {
                System.out.println("해당 코드의 학과를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 학과 정보 삭제
     */
    private static void deleteDepartment(Scanner scanner) {
        System.out.println("[학과 정보 삭제]");
        System.out.print("삭제할 학과의 코드를 입력하세요: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM department WHERE department_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("학과 정보가 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("해당 코드의 학과를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 성적 관련 메뉴
     */
    private static void takesMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=============================");
            System.out.println("1. 성적 등록");
            System.out.println("2. 성적 조회");
            System.out.println("3. 성적 정보 수정");
            System.out.println("4. 성적 정보 삭제");
            System.out.println("5. 메인 메뉴로 가기");
            System.out.println("=============================");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    registerTakes(scanner);
                    break;
                case 2:
                    displayTakes();
                    break;
                case 3:
                    updateTakes(scanner);
                    break;
                case 4:
                    deleteTakes(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }
    /**
     * 성적 등록
     */
    private static void registerTakes(Scanner scanner) {
        System.out.println("[성적 등록]");
        System.out.print("학생 ID: ");
        String studentId = scanner.nextLine();
        System.out.print("수업 코드: ");
        String classId = scanner.nextLine();
        System.out.print("성적: ");
        String score = scanner.nextLine();

        String sql = "INSERT INTO takes (student_id, class_id, score) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt =conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, classId);
            pstmt.setString(3, score);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("성적이 성공적으로 등록되었습니다.");
            } else {
                System.out.println("성적 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 성적 목록 조회
     */
    private static void displayTakes() {
        System.out.println("[등록된 성적 목록 조회]");
        String sql = "SELECT t.student_id, t.class_id, t.score, s.name FROM takes t INNER JOIN student s ON t.student_id = s.student_id INNER JOIN class c on t.class_id = c.class_id ORDER BY t.student_id";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String classId = rs.getString("class_id");
                String score = rs.getString("score");
                String name = rs.getString("name");
                System.out.printf("%-10s %-15s %-10s %-5s\n", studentId, name, classId, score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 성적 정보 수정
     */
    private static void updateTakes(Scanner scanner) {
        System.out.println("[성적 정보 수정]");
        System.out.print("학생 ID를 입력하세요: ");
        String studentId = scanner.nextLine();
        System.out.print("수업 ID: ");
        String classId = scanner.nextLine();
        System.out.print("새 성적: ");
        String newScore = scanner.nextLine();

        String sql = "UPDATE takes SET score = ? WHERE class_id = ? AND class_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newScore);
            pstmt.setString(2, studentId);
            pstmt.setString(3, classId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("성적 정보가 성공적으로 업데이트 되었습니다.");
            } else {
                System.out.println("해당 학생 ID와 과목 코드의 성적을 찾을 수 없습니다.");
            }
        }  catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 수정에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 성적 정보 삭제
     */
    private static void deleteTakes(Scanner scanner) {
        System.out.println("[성적 정보 삭제]");
        System.out.print("삭제할 학생 ID를 입력하세요: ");
        String studentId = scanner.nextLine();
        System.out.print("삭제할 수업 ID를 입력하세요: ");
        String classId = scanner.nextLine();

        String sql = "DELETE FROM takes WHERE class_id = ? AND student_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, classId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("성적 정보가 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("해당 학생 ID와 과목 코드의 성적을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 코스(강좌, 강의, 과목) 메뉴
     */
    private static void courseMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=============================");
            System.out.println("1. 강의 등록");
            System.out.println("2. 강의 조회");
            System.out.println("3. 강의 정보 수정");
            System.out.println("4. 강의 정보 삭제");
            System.out.println("5. 메인 메뉴로 가기");
            System.out.println("=============================");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    registerCourse(scanner);
                    break;
                case 2:
                    displayCourses();
                    break;
                case 3:
                    updateCourse(scanner);
                    break;
                case 4:
                    deleteCourse(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 강의 등록
     */
    private static void registerCourse(Scanner scanner) {
        System.out.println("강의 ID: ");
        String courseId = scanner.nextLine();

        System.out.println("강의명 : ");
        String name = scanner.nextLine();

        System.out.print("취득 학점 : 예) 3학점인 경우 3 입력");
        int credit = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        String sql = "insert into course (course_id, name, credit) values (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            pstmt.setString(2, name);
            pstmt.setInt(3, credit);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("강좌가 성공적으로 등록되었습니다.");
            } else {
                System.out.println("강좌 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 등록된 강좌(강의, 과목) 목록을 화면에 출력
     */
    private static void displayCourses() {
        String sql = "select course_id, name, credit from course";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // ResultSet 객체를 사용하기 전에 isBeforeFirst()를 호출하여
            // 결과가 있는지 확인하는 데 사용
            if (!rs.isBeforeFirst()) {
                System.out.println("등록된 강의가 없습니다.");
                return;
            }
            System.out.println("등록된 강의 목록:");
            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                String name = rs.getString("name");
                int credit = rs.getInt("credit");
                System.out.println(courseId + " " + name + " " + credit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 강의 정보를 수정
     */
    private static void updateCourse(Scanner scanner) {
        // 등록된 강의 목록 보여주기
        displayCourses();
        System.out.println();

        System.out.println("수정할 강좌 ID를 입력하세요: ");
        String courseId = scanner.nextLine();
        scanner.nextLine(); // 개행 문자 소비

        System.out.println("새로운 강좌명을 입력하세요: ");
        String name = scanner.nextLine();

        System.out.println("새로운 취득 학점을 입력하세요: ");
        int credit = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 소비

        // 데이터베이스에서 강의 정보 수정
        String sql = "update course set name = ?, credit = ? where course_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);       // 강좌명
            pstmt.setInt(2, credit);        // 취득학점
            pstmt.setString(3, courseId);   // 강좌id

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("강좌 정보가 성공적으로 수정되었습니다.");
            } else {
                System.out.println("강좌 정보 수정에 실패했습니다.");
            }
        }  catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 수정에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 강의 정보를 삭제
     */
    private static void deleteCourse(Scanner scanner) {
        System.out.println("삭제할 강좌 ID를 입력하세요: ");
        String courseId = scanner.nextLine();
        scanner.nextLine(); // 개행 문자 소비

        // 데이터베이스에서 강의 정보 삭제
        String sql = "delete from course where course_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("강좌가 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("강좌 정보 삭제에 실패했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 수업 관련 메뉴
     */
    private static void classMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=============================");
            System.out.println("1. 수업 등록");
            System.out.println("2. 수업 조회");
            System.out.println("3. 수업 정보 수정");
            System.out.println("4. 수업 정보 삭제");
            System.out.println("5. 메인 메뉴로 가기");
            System.out.println("=============================");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    registerClass(scanner);
                    break;
                case 2:
                    displayClass();
                    break;
                case 3:
                    updateClass(scanner);
                    break;
                case 4:
                    deleteClass(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 수업 등록
     */
    public static void registerClass(Scanner scanner) {
        System.out.println("강좌(과목)id : ");
        String courseId = scanner.nextLine();
        System.out.println("수업 연도 : ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("수업 학기  예) 1, 2, 여름, 겨울 : ");
        String semester = scanner.nextLine();
        System.out.println("담당 교수id : ");
        String professorId = scanner.nextLine();
        System.out.println("교실 : ");
        String classroom = scanner.nextLine();
        System.out.println("수강 인원 : ");
        int enroll = scanner.nextInt();
        scanner.nextLine();
        System.out.println("수업 id : ");
        String classId = scanner.nextLine();

        String sql = "insert into class (class_id, year, semester, professor_id, course_id, class_date, classroom) values (?, ?, ?, ?, ?, ?, to_date(?, 'yyyy-mm-dd'), ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classId);
            pstmt.setInt(2, year);
            pstmt.setString(3, semester);
            pstmt.setString(4, professorId);
            pstmt.setString(5, courseId);
            pstmt.setString(6, classroom);
            pstmt.setInt(7, enroll);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("수업이 성공적으로 저장되었습니다.");
            } else {
                System.out.println("수업정보 저장에 실패했습니다.");
            }
        }  catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 수업 조회
     */
    private static void displayClass() {
        String sql = "select class_id, course_id, year, semester, professor_id, classroom from class order by class_id";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("등록된 수업 목록:");
            while(rs.next()) {
                int classId = rs.getInt("class_id");
                int year = rs.getInt("year");
                String semester = rs.getString("semester");
                String professorId = rs.getString("professor_id");
                int courseId = rs.getInt("course_id");
                String classroom = rs.getString("classroom");
                System.out.println(classId + " " +  courseId + " " + year + " " +  semester + " " +  professorId + " " +  classroom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 수업 수정
     */
    private static void updateClass(Scanner scanner) {
        // 수업의 키가 되는 수업 id 입력
        System.out.println("수업 id : ");
        String classId = scanner.nextLine();
        System.out.println("새 강좌(과목)id : ");
        String courseId = scanner.nextLine();
        System.out.println("새 수업 연도 : ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("새 수업 학기  예) 1, 2, 여름, 겨울 : ");
        String semester = scanner.nextLine();
        System.out.println("새 담당 교수id : ");
        String professorId = scanner.nextLine();
        System.out.println("새 교실 : ");
        String classroom = scanner.nextLine();
        System.out.println("새 수강 인원 : ");
        int enroll = scanner.nextInt();
        scanner.nextLine();

        String sql = "update class set course_id = ?,  year = ?, semester = ?, professor_id = ?, classroom = ? where class_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            pstmt.setInt(2, year);
            pstmt.setString(3, semester);
            pstmt.setString(4, professorId);
            pstmt.setString(5, classroom);
            pstmt.setString(6, classId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("수업이 정상적으로 수정되었습니다.");
            } else {
                System.out.println("수업정보 수정에 실패했습니다.");
            }
        }  catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 수정에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 수업 삭제
     */
    private static void deleteClass(Scanner scanner) {
        System.out.print("삭제할 수업의 ID를 입력하세요: ");
        String classId = scanner.nextLine();

        String sql = "delete from class where class_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("수업이 정상적으로 삭제되었습니다.");
            } else {
                System.out.println("수업 삭제에 실패했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 수강 신청 관련 메뉴
     */
    private static void enrollmentMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=============================");
            System.out.println("1. 수강 신청");
            System.out.println("2. 수강 조회");
            System.out.println("3. 수강 취소");
            System.out.println("5. 메인 메뉴로 가기");
            System.out.println("=============================");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    registerEnrollment(scanner);
                    break;
                case 2:
                    displayEnrollment();
                    break;
                case 3:
                    cancelEnrollment(scanner);
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 수강 등록
     */
    public static void registerEnrollment(Scanner scanner) {
        System.out.println("수업 ID: ");
        String classId = scanner.nextLine();

        System.out.println("학생 ID: ");
        String studentId = scanner.nextLine();

        System.out.println("수업 학기  예) 1, 2, 여름, 겨울 : ");
        String semester = scanner.nextLine();

        int enrollId = getMaxEnrollmentId(); // 수강신청 최대 번호 + 1 해서 새로운 번호 생성

        String sql = "insert into enrollment (enroll_id, student_id, class_id, semester, grade) values (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, enrollId);
            pstmt.setString(2, studentId);
            pstmt.setString(3, classId);
            pstmt.setString(4, semester);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("수강 신청이 성공적으로 완료되었습니다.");
            } else {
                System.out.println("수강 신청에 실패했습니다.");
            }
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 등록에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 최대 Class ID 조회
     */
    private static int getMaxEnrollmentId() {
        String sql = "select max(enroll_id) as max_id from enrollment";
        int enrollId = 1; // 기본 1로 세팅
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                enrollId = rs.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 만약에 최대 값이 없다면 기본값 1 리턴
        return enrollId;
    }

    /**
     * 수강 조회
     */
    private static void displayEnrollment() {
        String sql = "select enroll_id, student_id, class_id, grade, enrollment_date from enrollment";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("등록된 수강 내역:");
            while (rs.next()) {
                int enrollmentId = rs.getInt("enroll_id");
                String studentId = rs.getString("student_id");
                String courseId = rs.getString("class_id");
                String grade = rs.getString("grade");
                LocalDateTime enrollmentDate = rs.getTimestamp("enrollment_date").toLocalDateTime();
                System.out.println(enrollmentId + " " + studentId + " " +  courseId + " " +  grade + " " +  enrollmentDate);
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 수강 정보 수정
     */
    public static void updateEnrollment(Scanner scanner) {
        // 수정할 때 필요한 키
        System.out.println("학생 ID: ");
        String studentId = scanner.nextLine();
        scanner.nextLine();
        // 수정할 때 필요한 키
        System.out.println("수업 ID: ");
        String classId = scanner.nextLine();
        System.out.println("새로운 성적: ");
        String grade = scanner.nextLine();
        System.out.println("수강 상태 변경: ex) 취소 or 승인 ");
        String status = scanner.nextLine();

        String sql = "update enrollment set grade=?, status=? where student_id = ?, class_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, grade);
            pstmt.setString(2, status);
            pstmt.setString(3, studentId);
            pstmt.setString(4, classId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("수강 정보가 성공적으로 업데이트 되었습니다.");
            } else {
                System.out.println("수장 정보 수정에 실패했습니다.");
            }
        }  catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("무결성 제약 조건 위반으로 수정에 실패했습니다.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 수강 취소
     */
    private static void cancelEnrollment(Scanner scanner) {
        System.out.print("학생 ID: ");
        String studentId = scanner.nextLine();

        System.out.println("수업 ID: ");
        String classId = scanner.nextLine();

        // 해당 ID의 수강 내역 삭제
        String deleteSql = "delete from enrollment where student_id = ? and, class_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, classId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("수강 내역이 삭제되었습니다.");
            } else {
                System.out.println("수강 내역을 삭제하는 데 실패했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 프로그램 종료
     * - 데이터베이스 연결을 닫고 프로그램을 종료한다.
     */
    public static void exit() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
        System.out.println("** 프로그램 종료 **");
        System.exit(0);
    }

}   // end of class SchoolManagementEx

