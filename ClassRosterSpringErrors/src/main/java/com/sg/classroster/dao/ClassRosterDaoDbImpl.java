/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.dao;

import com.sg.classroster.dto.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author kylerudy
 */
public class ClassRosterDaoDbImpl implements ClassRosterDao {
    
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM student";
    private static final String SELECT_STUDENT = "SELECT * FROM student WHERE studentid = ?";
    private static final String REMOVE_STUDENT = "DELETE FROM student WHERE studentid = ?";
    private static final String ADD_STUDENT = "INSERT INTO student (studentid, first_name, last_name, cohort) "
            + "VALUES ( ?, ?, ?, ?)";
    
    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student addStudent(String studentId, Student student) throws ClassRosterPersistenceException {
        jdbcTemplate.update(ADD_STUDENT, studentId, student.getFirstName(), 
                student.getLastName(), student.getCohort());
        return student;
    }

    @Override
    public List<Student> getAllStudents() throws ClassRosterPersistenceException {
        return jdbcTemplate.query(SELECT_ALL_STUDENTS, new StudentMapper());
    }

    @Override
    public Student getStudent(String studentId) throws ClassRosterPersistenceException {
        try {
            return jdbcTemplate.queryForObject(SELECT_STUDENT, new StudentMapper(), studentId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Student removeStudent(String studentId) throws ClassRosterPersistenceException {
        Student student = getStudent(studentId);
        jdbcTemplate.update(REMOVE_STUDENT, studentId);
        return student;
    }
    
    private static final class StudentMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet rs, int i) throws SQLException {
            Student student = new Student(rs.getString("studentid"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setCohort(rs.getString("cohort"));
            return student;
        }
        
    }
    
}
