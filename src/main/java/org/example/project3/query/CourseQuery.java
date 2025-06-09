package org.example.project3.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.project3.exceptions.DbOperationException;

public class CourseQuery {

    public static ResultSet retrieveCourse(Connection conn) throws SQLException {
        String query = "c.name, c.slots, c.remaining, c.duration, c.level, s.day, s.hour FROM course c, session s HWERE and c.id = s.course";
        PreparedStatement pstmt = conn.prepareStatement(query);
        return pstmt.executeQuery();
    }
}
