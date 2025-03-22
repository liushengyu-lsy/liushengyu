import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class student {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        Scanner sc=new Scanner(System.in);
        String username=JDBC.getname();
        try {
            conn = DriverManager.getConnection("jdbc:mysql:///test?useSSL=false&allowPublicKeyRetrieval=true", "root", "liushengyu167167");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while(true)
        {
            System.out.println("======学生菜单======");
            System.out.println(("1.查看可选课程\t2.选择课程\t3.退选课程\t4.查看已选课程\t5.修改手机号\t6.退出\t"));
            int symbol=sc.nextInt();
            if(symbol==1)
            {
                try {

                    String sql2 = "SELECT course_name FROM choosecourses";

                    ResultSet resultSet2 = stmt.executeQuery(sql2);

                    // 将 choosecourses 的课程名存入一个 list
                    List<String> choosecourses = new ArrayList<>();
                    while (resultSet2.next()) {
                        String set2Name = resultSet2.getString("course_name");
                        choosecourses.add(set2Name);
                    }
                       resultSet2.close();
                    //将courses的课程名存入一个list下
                    String sql1 = "SELECT course_name FROM courses";
                    ResultSet resultSet1 = stmt.executeQuery(sql1);
                    List<String> courses = new ArrayList<>();
                    while (resultSet1.next()) {
                        String set1Name = resultSet1.getString("course_name");
                        courses.add(set1Name);
                    }
                    resultSet1.close();
                    // 定义一个 count 记录报名某课程的人数
                    int count = 0;
                    int leftcount=0;
                    for(int i=0;i<courses.size();i++)
                    {
                        for(int j=0;j<choosecourses.size();j++)
                        {
                            if(courses.get(i).equals(choosecourses.get(j)))
                            {
                                count++;
                            }
                        }
                        if(count<=4)
                        {   leftcount=5-count;
                            System.out.println("可选课程名："+courses.get(i)+"\t剩余报名人数："+leftcount);
                        }
                        count=0;
                    }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
           else if(symbol==2)
            {   //定义一个变量记录学生选课次数
                int flag=0;
                try {//查询该学生已选的课程的次数
                    String sql2 = "SELECT course_name FROM choosecourses where student_name =?";
                   //将username作为参数传入sql2中
                    PreparedStatement preparedStatement3 = conn.prepareStatement(sql2);
                    preparedStatement3.setString(1,username);
                    ResultSet resultSet2 = preparedStatement3.executeQuery();
                    // 将 choosecourses 的课程名存入一个 list
                    List<String> choosecourses = new ArrayList<>();
                    while (resultSet2.next())
                    {
                        String set2Name = resultSet2.getString("course_name");
                        choosecourses.add(set2Name);
                    }
                    resultSet2.close();
//                    // 开始记录该学生已经报名的课程的次数
//                    for(int i=0;i<choosecourses.size();i++)
//                    {
//                        if(choosecourses.get(i).equals(username))
//                        {
//                            flag++;
//                        }
//                    }
                    if(choosecourses.size()<5)
                    {
                        System.out.println("你还可以选择"+(5- choosecourses.size())+"门课程");
                    }
                    else {
                        System.out.println("你已经选择了5门课程,不能再选择了");
                        break;
                    }

                    //学生选课次数小于5次，进行选课
                    while (flag<=5) {
                        System.out.println("请输入你要选择的课程名");
                        String choose = sc.next();
                       //接下来是判断课程是否选择过
                        while(true) {

                            int Symbol = 0;
                            for (int i = 0; i < choosecourses.size(); ) {

                                if (choosecourses.get(i).equals(choose)) {
                                    System.out.println("该课程你已经选过了,请重新选择");
                                    Symbol = 1;
                                    System.out.println("请输入你要选择的课程名");
                                    choose = sc.next();
                                    break;
                                } else {
                                    i++;
                                }

                            }
                            if (Symbol == 0) {
                                break;
                            }
                            else
                            {
                                continue;
                            }
                        }
                        String sql3 = "insert into choosecourses (course_name,student_name) values (?,?)";
                        PreparedStatement preparedstatement2 = conn.prepareStatement(sql3);
                        preparedstatement2.setString(1, choose);
                        preparedstatement2.setString(2, username);
                        int rowsAffected = preparedstatement2.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("选课信息插入成功");
                        } else {
                            System.out.println("选课信息插入失败");
                        }
                        flag++;
                        System.out.println("是否继续选课？\n1.继续\n2.退出");
                        int symbol2=sc.nextInt();
                        if(symbol2==1)
                        {
                            continue;
                        }
                        else if(symbol2==2)
                        {
                            break;
                        }
                    }
                }catch (SQLException e) {
                        throw new RuntimeException(e);
                    }



            }
          else if(symbol==3)
            {  //退选课程
                while (true) {
                    try {
                        String sql="select course_name from choosecourses where student_name =?";
                        if(sql==null)
                        {
                            System.out.println("你还没有选择课程喔");
                            break;
                        }
                        else {
                            PreparedStatement preparedStatement3 = conn.prepareStatement(sql);
                            preparedStatement3.setString(1, username);
                            ResultSet resultSet3 = preparedStatement3.executeQuery();
                            System.out.println("你已选的课程如下：");
                            while (resultSet3.next())
                            {
                                System.out.println(resultSet3.getString("course_name"));
                            }

                            resultSet3.close();
                            System.out.println("请输入你要退选的课程名");
                            String deletecourse = sc.next();
                            String sql2 = "delete from choosecourses where course_name =? and student_name =?";
                            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
                            preparedStatement2.setString(1, deletecourse);
                            preparedStatement2.setString(2, username);
                            int rowsUpdated = preparedStatement2.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("退选成功");
                            } else {
                                System.out.println("退选失败");
                            }
                            System.out.println("是否继续退选？\n1.继续\n2.退出");
                            int symbol3=sc.nextInt();
                            if(symbol3==1)
                            {
                                continue;
                            }
                            else if(symbol3==2)
                            {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
          else if(symbol==4)
            {
                String sql2="select * from choosecourses where student_name = ?";
                try {
                    PreparedStatement preparedstatment=conn.prepareStatement(sql2);
                    preparedstatment.setString(1,username);
                    ResultSet resultSet=preparedstatment.executeQuery();
                    while(resultSet.next())
                    {   if(resultSet.getString("student_name").equals(username))
                        System.out.println(" 学生名字："+resultSet.getString("student_name")+"\t课程名：\t"+resultSet.getString("course_name"));
                    }
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
          else if(symbol==5)
            {


                try {
                    while (true) {
                        System.out.println("请输入你要修改的学生的手机号码");
                     String modifynumber = sc.next();
                      String updateSql = "UPDATE students SET phonenumber = ? WHERE student_name=?";
                        PreparedStatement preparedStatement = conn.prepareStatement(updateSql);
                        preparedStatement.setString(1, modifynumber);
                        preparedStatement.setString(2, username);
                        int rowsUpdated = preparedStatement.executeUpdate();
                        if ((rowsUpdated > 0)&&(modifynumber.length()==11)) {
                            System.out.println("手机号已经成功修改");
                      break;
                        }
                        else {
                            System.out.println("手机号输入格式不对>-<, 请重新输入喔");
                        }
                    }
                    break;
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
          else if(symbol==6)
            {
            break;
            }

        }
    }


}
