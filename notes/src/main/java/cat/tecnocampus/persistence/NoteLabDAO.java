package cat.tecnocampus.persistence;

import cat.tecnocampus.domain.NoteLab;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class NoteLabDAO {
    private JdbcTemplate jdbcTemplate;

    private final String INSERT_NOTE = "insert into note_lab (title, content, date_creation, date_edit, owner, userChecked) values(?, ?, ?, ?, ?, ?)";
    private final String FIND_ALL = "select * from note_lab";
    private final String FIND_BY_USERNAME = "select * from note_lab where owner = ? order by date_edit desc";
    private final String FIND_BY_ID = "select * from note_lab where id = ?";
    private final String FIND_ALL_CLIENT_DOWN = "select * from note_lab where userChecked = 3";
    private final String UPDATE_NOTE = "update note_lab set title = ?, content = ?, date_edit = ? where id = ?";
    private final String DELETE_NOTE = "delete note_lab where id = ? AND owner = ?";
    private final String DELETE_USER_NOTES = "delete note_lab where owner = ?";
    private final String EXISTS_NOTE = "select count(*) from note_lab where title = ? and date_creation = ?";
    private final String SET_OWNER_EXIST = "update note_lab set userChecked = 1 WHERE id = ?";


    private RowMapper<NoteLab> mapper = (resultSet, i) -> {
        NoteLab noteLab = new NoteLab.NoteLabBuilder(resultSet.getString("title"), resultSet.getString("content"), resultSet.getString("owner"))
                .dateCreation(resultSet.getTimestamp("date_creation").toLocalDateTime())
                .dateEdit(resultSet.getTimestamp("date_edit").toLocalDateTime())
                .id(resultSet.getLong("id"))
                .build();
        return noteLab;
    };

    public NoteLabDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NoteLab> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    public List<NoteLab> findByUsername(String username) {
        return jdbcTemplate.query(FIND_BY_USERNAME, new Object[]{username}, mapper);
    }

    public NoteLab findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id},mapper);
    }


    public NoteLab insert(NoteLab noteLab, String userLab, int existsUser) {
        jdbcTemplate.update(
                INSERT_NOTE,
                noteLab.getTitle(),
                noteLab.getContent(),
                Timestamp.valueOf(noteLab.getDateCreation()),
                Timestamp.valueOf(noteLab.getDateEdit()),
                userLab,
                existsUser);
        noteLab.setId(jdbcTemplate.queryForObject( "select last_insert_id()" , Long.class));
        noteLab.setOwner(userLab);
        return noteLab;
    }

    public int updateNote(Long id, NoteLab note) {
        return jdbcTemplate.update(UPDATE_NOTE,
                note.getTitle(), note.getContent(), note.getDateEdit(), id);
    }

    public int deleteNote(Long id, String username) {
        return jdbcTemplate.update(DELETE_NOTE, id, username);
    }

    public int deleteUserNotes(String username) {
        return jdbcTemplate.update(DELETE_USER_NOTES, username);
    }

    public boolean existsNote(NoteLab note) {
        int countOfNotes = jdbcTemplate.queryForObject(
                EXISTS_NOTE, Integer.class, note.getTitle(), Timestamp.valueOf(note.getDateCreation()));
        return countOfNotes > 0;
    }

    public List<NoteLab> findAllnotCheckedOwner() {
        return jdbcTemplate.query(FIND_ALL_CLIENT_DOWN, mapper);
    }

    public int setOwnerExists(Long id) {
        return jdbcTemplate.update(SET_OWNER_EXIST, id);
    }


}
