package org.diyone;

import org.diyone.mmybaits.datasource.DataSourceFactory;
import org.diyone.mmybaits.datasource.PooledDataSourceFactory;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MMybaitsTest {
    public static void main(String[] args) throws NoSuchMethodException {
        test();
    }




    private static void test(){
        DataSourceFactory dataSourceFactory = new PooledDataSourceFactory();
        DataSource dataSource = dataSourceFactory.getDataSource();
        try(Connection connection = dataSource.getConnection();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from books where  id = 1");
            while (resultSet.next()){
                System.out.println(resultSet.getString("name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
