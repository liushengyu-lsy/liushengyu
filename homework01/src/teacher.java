import java.sql.*;
import java.util.Scanner;

public class teacher {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        Scanner sc=new Scanner(System.in);
        try {
            conn = DriverManager.getConnection("jdbc:mysql:///test?useSSL=false&allowPublicKeyRetrieval=true", "root", "liushengyu167167");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while(true)
        {
            System.out.println("1.查询所有学生信息;\n2.修改学生手机号;\n3.查询所有课程;\n4.修改课程学分;\n5.查询某课程的学生名单;\n6.查询某学生的选课情况;\n7.退出;\n请选择操作（输入1-7）：");

            int symbol=sc.nextInt();
            if(symbol==1)
            {    //查询所有学生信息；

                try {
                    String selectSql = "SELECT * FROM students";
                    ResultSet resultSet = stmt.executeQuery(selectSql);
                    // 打印表头
                    System.out.println("ID     \tName    \tphonenumber");
                    System.out.println("---------------------");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("student_name");
                        String Pnumber = resultSet.getString("phonenumber");
                        System.out.println(id + "\t" + name + "\t" + Pnumber);
                }
                }catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            else if(symbol==2)
            {
              //修改学生手机号码；
                System.out.println("请输入你要修改的学生的id");
                int modifyid = sc.nextInt();
                System.out.println("请输入你要修改的学生的手机号码");
                String modifynumber = sc.next();
                try {
                    String updateSql = "UPDATE students SET phonenumber = ? WHERE id = ?";
                    PreparedStatement preparedStatement = conn.prepareStatement(updateSql);
                    preparedStatement.setString(1, modifynumber);
                    preparedStatement.setInt(2, modifyid);
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("学生信息已更新");
                    }
                    else {
                        System.out.println("未找到该学生");
                    }

                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            else if(symbol==3)
            {       //查询所有课程
                try {
                    String selectSql = "SELECT * FROM courses";
                    ResultSet resultSet = stmt.executeQuery(selectSql);
                    // 打印表头
                    System.out.println("course_name     \tpoint");
                    System.out.println("---------------------");
                    while (resultSet.next()) {
                        String id = resultSet.getString("course_name");

                       int point = resultSet.getInt("point");
                        System.out.println(id + "\t" +"             "  + "\t" + point);
                    }

                }catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            else if(symbol==4)
            {
               //修改课程学分

                System.out.println("请输入你要修改的课程名字");
                String modifyCourseName= sc.next();
                System.out.println("请输入你要修改的学分");
                int modifypoint= sc.nextInt();
                 // 检查课程是否存在
                String checkSql = "SELECT COUNT(*) FROM courses WHERE course_name = ?";
                try (PreparedStatement checkStatement = conn.prepareStatement(checkSql)) {
                    checkStatement.setString(1, modifyCourseName);
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                        if (resultSet.next() && resultSet.getInt(1) > 0) {
                            // 课程存在，执行更新操作
                            try (PreparedStatement updateStatement = conn.prepareStatement("update courses set point = ? where course_name = ?")) {
                                updateStatement.setInt(1, modifypoint);
                                updateStatement.setString(2, modifyCourseName);
                                int rowsUpdated = updateStatement.executeUpdate();
                                if (rowsUpdated > 0) {
//
                                    System.out.println("课程信息已更新");
                                }
                            }
                        } else {
                            System.out.println("未找到该课程");
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("执行 SQL 语句时出现错误: " + e.getMessage());
                }

            }
            else if(symbol==5)
            { //查询某课程的学生名单
                System.out.println("请输入你要查询的课程名字");
                String selectcourse= sc.next();
                String sql2="select * from choosecourses where course_name = ?";
                try {
                    PreparedStatement preparedstatment=conn.prepareStatement(sql2);
                    preparedstatment.setString(1,selectcourse);
                    ResultSet resultSet=preparedstatment.executeQuery();
                    while(resultSet.next())
                    {   if(resultSet.getString("course_name").equals(selectcourse))
                        System.out.println("课程名："+resultSet.getString("course_name")+" 学生名字："+resultSet.getString("student_name"));
                    }
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            else if(symbol==6)
            {   //查询某学生的选课情况
                System.out.println("请输入你要查询的学生名字");
                String selectname= sc.next();
                String sql2="select * from choosecourses where student_name = ?";
                try {
                    PreparedStatement preparedstatment=conn.prepareStatement(sql2);
                    preparedstatment.setString(1,selectname);
                    ResultSet resultSet=preparedstatment.executeQuery();
                    while(resultSet.next())
                    {   if(resultSet.getString("student_name").equals(selectname))
                        System.out.println(" 学生名字："+resultSet.getString("student_name")+"\t课程名：\t"+resultSet.getString("course_name"));
                    }
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            else if(symbol==7)
            { //退出
            break;
            }
            else
            {
                System.out.println("输入错误,请重新选择");
                continue;
            }
        }


    }
}
