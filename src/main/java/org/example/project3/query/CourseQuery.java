package org.example.project3.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CourseQuery {
    private CourseQuery() {}

    public static ResultSet retrieveCourse(Connection conn) throws SQLException {
        String query = "SELECT c.id, c.name, c.remaining, c.duration, c.level, s.day, s.hour FROM course c JOIN session s ON c.id = s.course";
        PreparedStatement pstmt = conn.prepareStatement(query);
        return pstmt.executeQuery();
    }
}
