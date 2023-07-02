package model.databases;

import java.sql.*;

public class SQLiteDatabase {
    private Connection connect() {
        String url = "jdbc:sqlite:C://ap/SSSIT.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            return null;
        }
        return conn;
    }

    public void insert(String name, String message) {
        String sql = "INSERT INTO databases(name, message) VALUES(?,?)";

        try{
            Connection conn = this.connect();
            if (conn == null) return;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String get(){
        String sql = "SELECT * FROM employees";

        try {
            Connection conn = this.connect();
            if (conn == null) return null;
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                if (rs.getString("name").equals("database"))
                    return rs.getString("message");
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }
}
