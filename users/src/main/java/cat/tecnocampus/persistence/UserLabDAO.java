package cat.tecnocampus.persistence;

import cat.tecnocampus.domain.UserLab;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by josep on 8/9/17.
 */
@Repository
public class UserLabDAO {
    private JdbcTemplate jdbcTemplate;

    private final String INSERT_USER = "insert into user_lab values(?, ?, ?, ?, ?, ?)";
    private final String FIND_ALL = "Select * from user_lab";
    private final String FIND_BY_USERNAME = "Select * from user_lab where username = ?";
    private final String DELETE = "delete from user_lab where username = ?";
    private final String EXISTS_USER = "SELECT COUNT(*) FROM user_lab WHERE username = ?";


    private UserLab userMapper(ResultSet resultSet) throws SQLException {
        UserLab userLab = new UserLab.UserLabBuilder(resultSet.getString("username"), resultSet.getString("email"))
                .name(resultSet.getString("name"))
                .secondName(resultSet.getString("second_name"))
                .password(resultSet.getString("password"))
                .build();
        return userLab;
    }

    private RowMapper<UserLab> mapper = (resultSet, i) -> {
        return userMapper(resultSet);
    };

    public UserLabDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(UserLab userLab) {
        return jdbcTemplate.update(INSERT_USER, userLab.getUsername(), userLab.getName(), userLab.getSecondName(),
                userLab.getEmail(), userLab.getPassword(), userLab.getEnabled());

    }

    public List<UserLab> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    public UserLab findByUsername(String userName) {
        return jdbcTemplate.queryForObject(FIND_BY_USERNAME, mapper, userName);
    }

    public int delete(String username) {
        return jdbcTemplate.update(DELETE, username);
    }

    public boolean existsUser(String username) {
        return jdbcTemplate.queryForObject(EXISTS_USER, new Object[]{username}, Integer.class) != 0;
    }
}
