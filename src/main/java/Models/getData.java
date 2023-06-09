package Models;

import Database.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getData {
    public static String username;
    public static String authority;
    public static String name;
    public static Manager manager;
    public static Employee employee;
    public static Customer customer;
    public static String messageReturn;
    public static void setManager(){
        Connection connection;
        connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM MANAGER WHERE MANAGER_USERNAME = ? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                manager = new Manager(
                        resultSet.getString("MANAGER_ID"),
                        resultSet.getString("MANAGER_USERNAME"),
                        resultSet.getString("MANAGER_NAME"),
                        resultSet.getDate("MANAGER_BIRTH"),
                        resultSet.getString("MANAGER_GENDER"),
                        resultSet.getString("MANAGER_ADDRESS"),
                        resultSet.getString("MANAGER_PHONE"),
                        resultSet.getString("MANAGER_EMAIL"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setEmployee(){
        Connection connection;
        connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE_USERNAME = ? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                employee = new Employee(
                        resultSet.getString("EMPLOYEE_ID"),
                        resultSet.getString("EMPLOYEE_USERNAME"),
                        resultSet.getString("EMPLOYEE_NAME"),
                        resultSet.getDate("EMPLOYEE_BIRTH"),
                        resultSet.getString("EMPLOYEE_GENDER"),
                        resultSet.getString("EMPLOYEE_ADDRESS"),
                        resultSet.getString("EMPLOYEE_PHONE"),
                        resultSet.getString("EMPLOYEE_EMAIL"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCustomer(){
        Connection connection;
        connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM customer WHERE CUSTOMER_USERNAME = ? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                customer = new Customer(
                        resultSet.getString("CUSTOMER_ID"),
                        resultSet.getString("CUSTOMER_USERNAME"),
                        resultSet.getString("CUSTOMER_NAME"),
                        resultSet.getDate("CUSTOMER_BIRTH"),
                        resultSet.getString("CUSTOMER_GENDER"),
                        resultSet.getString("CUSTOMER_ADDRESS"),
                        resultSet.getString("CUSTOMER_PHONE"),
                        resultSet.getString("CUSTOMER_EMAIL"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
