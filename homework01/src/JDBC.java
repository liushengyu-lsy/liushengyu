import java.sql.*;
import java.util.Scanner;

public class JDBC {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql:///test?useSSL=false&allowPublicKeyRetrieval=true", "root", "liushengyu167167");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 使用 try-with-resources 语句管理资源
        try {
            String sql = "update users set name='李四'  where id=11";
            int rows = stmt.executeUpdate(sql);
            System.out.println("成功更新了 " + rows + " 条记录");

            while (true) {
                switch (menu()) {
                    case 1 -> login(conn);
                    case 2 -> register(conn);
                    case 3 -> {
                        System.out.println("欢迎下次使用");
                        return;
                    }
                    default -> System.out.println("输入错误");
                }
            }
        } catch (SQLException e) {
            // 详细处理异常
            System.err.println("执行 SQL 语句时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static int menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===========================");
        System.out.println("欢迎使用学生信息管理系统");
        System.out.println("===========================");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 退出");
        int firstChoice = sc.nextInt();
        return firstChoice;
    }

    public static void register(Connection conn)
    {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入用户名");
            String username = sc.next();
            System.out.println("请输入密码");
            String password = sc.next();
            System.out.println("请再次输入密码");
            String passwordAgain = sc.next();

            if(password.equals(passwordAgain))
            {
                System.out.println("请输入确认身份（学生/老师）");
                String confirmname = sc.next();
                System.out.println("注册成功");
                try (Statement stmt = conn.createStatement()) {
                    String sql = "INSERT INTO users (name, password,confirm_name) VALUES (?, ?,?)";
                    PreparedStatement haha =conn.prepareStatement(sql);
                    haha.setString(1, username);
                    haha.setString(2, password);
                    haha.setString(3, confirmname);
                    haha.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("注册失败: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            }
            else
            {
                System.out.println("两次输入密码不一致");
                continue;
            }
        }
    }

    public static void login(Connection conn) {
        while (true) {

           String username=getname();


            String password=getpassword();

            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE name = ? AND password = ?")) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("登录成功");
                    String confirmname = rs.getString("confirm_name");
                    if (confirmname == null) {
                        System.out.println("请完善个人信息");
                        return;
                    } else if (confirmname.equals("学生")) {
                        System.out.println("欢迎您，" + rs.getString("name") + "同学");

                        //学生系统
                        student.main(null);


                        break;
                    } else {
                        System.out.println("欢迎您，" + rs.getString("name") + "老师");

                        //管理员系统
                        teacher tea = new teacher();
                        tea.main(null);


                        break;
                    }


                } else {
                     Scanner sc = new Scanner(System.in);
                    System.out.println("用户名或密码错误");
                    System.out.println("是否重新登录或者修改密码？");
                    System.out.println("1. 重新登录");
                    System.out.println("2. 修改密码");
                    int choice = sc.nextInt();
                    while (true)
                        if (choice == 1) {
                            break;
                        }
                        //修改密码
                        else if (choice == 2) {

                        } else {
                            System.out.println("输入错误,请重新选择");
                            continue;
                        }
                }
            } catch (SQLException e) {
                System.err.println("登录失败: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static String getname() {

        System.out.println("请输入用户名");
        Scanner sc = new Scanner(System.in);
        String username = sc.next();
        return username;
    }

    public static String getpassword() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入密码");
        String password = sc.next();
        return password;
    }

}