package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DatabaseConnector {

    // JDBC连接URL、用户名和密码
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/javatest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "wEtzO1jaka/p";

    // 数据库连接对象
    private Connection connection;

    // 构造函数，在创建类的实例时初始化数据库连接
    public DatabaseConnector() {
        try {
            // 注册JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("数据库连接成功");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("数据库连接失败");
        }
    }

    // 获取数据库连接对象的方法
    public Connection getConnection() {
        return connection;
    }

    // 关闭数据库连接的方法
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("数据库连接已关闭");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 执行查询并返回结果集的方法
    public ResultSet executeQuery(String query) {
        try {
            // 创建 PreparedStatement 对象
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();
            // 处理查询结果

            // 返回结果集
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // 或者抛出自定义异常，根据具体情况处理错误
        }
    }
    /*
    * 更新数据库表内单个字段数据
    * @updata：String 数据库语句
    * @newValue: String 需要更新的数据
    * @updataif: string 查询条件
    * */
    public boolean executeUpdata (String updata, String newValue, String updataif) {
//        String updateQuery = "UPDATE  SET uuid = ? WHERE openid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updata)) {
            // 创建 PreparedStatement 对象
            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, updataif);
            // 执行查询
            int rowsAffected = preparedStatement.executeUpdate();
            boolean a = false;
            if (rowsAffected > 0) {
                a = true;
            } else {
                a = false;
                System.out.println("No data found for update.");
            }
//            ResultSet resultSet = preparedStatement.executeQuery();
            // 处理查询结果

            // 返回结果集
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 或者抛出自定义异常，根据具体情况处理错误
        }
    }
    // 关闭 ResultSet 和 PreparedStatement 的方法
    public void closeResultSetAndStatement(ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 创建DatabaseConnector类的实例
        DatabaseConnector connector = new DatabaseConnector();

        // 使用连接对象进行数据库操作...

        // 关闭数据库连接
        connector.closeConnection();
    }
}
