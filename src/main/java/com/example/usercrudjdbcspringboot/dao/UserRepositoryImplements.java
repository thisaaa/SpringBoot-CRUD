package com.example.usercrudjdbcspringboot.dao;

import com.example.usercrudjdbcspringboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepositoryImplements  implements UserRepository{

    private static final String INSERT_USER_QUERY="INSERT INTO USER(id,fName,lName,age,city) values(?,?,?,?,?)";
    private static final String UPDATE_USER_BY_ID_QUERY="UPDATE USER SET fName=? WHERE id=?";
    private static final String GET_USER_BY_ID_QUERY="SELECT * FROM USER WHERE id=?";
    private static final String DELETE_USER_BY_ID_QUERY="DELETE FROM USER WHERE id=?";
    private static final String GET_USERS_QUERY="SELECT * FROM USER";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User saveUser(User user) {
        jdbcTemplate.update(INSERT_USER_QUERY,user.getId(),user.getFName(),user.getLName(),user.getAge(),user.getCity());
        return user;
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY,user.getFName(),user.getId());
        return user;
    }

    @Override
    public User getById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY,(rs, rowNum) -> {

            return new User(rs.getInt("id"),rs.getString("fName"),rs.getString("lName"),rs.getInt("age"),rs.getString("city"));
        },id);
    }

    @Override
    public String deleteById(int id) {
        jdbcTemplate.update(DELETE_USER_BY_ID_QUERY,id);
        return "successfully deleted id = "+id;
    }

    @Override
    public List<User> allUser() {
        return jdbcTemplate.query(GET_USERS_QUERY,(rs, rowNum) -> {
            return new User(rs.getInt("id"),rs.getString("fName"),rs.getString("lName"),rs.getInt("age"),rs.getString("city"));
        });
    }
}
