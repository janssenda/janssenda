package com.dm.impulsereactor.dao;

import com.dm.impulsereactor.Exceptions.DuplicateEntryException;
import com.dm.impulsereactor.Exceptions.SQLUpdateException;
import com.dm.impulsereactor.model.Speaker;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Named
public class SpeakerDao {
    private static final String [] COLUMNS = {"SpeakerID","SpeakerModel","SpeakerBrand","SpeakerName"};
    private JdbcTemplate jdbcTemplate;
    private QueryRunner sc;

    @Inject
    public SpeakerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sc = new QueryRunner(jdbcTemplate);
    }

    private static final String ADD_SPEAKER_QUERY =
            "INSERT INTO speakers (SpeakerModel, SpeakerBrand, SpeakerName) VALUES(?,?,?)";
    private static final String EXIST_QUERY =
            "SELECT COUNT(*) FROM speakers WHERE SpeakerID = ?";
    private static final String REMOVE_SPEAKER_QUERY =
            "DELETE FROM speakers WHERE SpeakerID = ?";
    private static final String UPDATE_SPEAKER_QUERY =
            "UPDATE speakers SET SpeakerModel = ?, SpeakerBrand = ?, SpeakerName = ? WHERE SpeakerID = ?";


    /**
     * @param flags - set search flags. 't' for partial match, and 'f' for exact.  Enter
     *              one letter for each search variable (i.e., 'ftff', or null to use default
     * @param args - search terms.  In order: SpeakerID, SpeakerModel, SpeakerBrand, SpeakerName
     */
    public List<Speaker> searchSpeakers(String flags, String... args) {
        return (List<Speaker>)(Object) sc.searchDatabase(new SpeakerMapper(), "speakers", COLUMNS, flags, args);
    }

    @Transactional
    public Speaker addSpeaker(Speaker spk) throws SQLUpdateException, DuplicateEntryException {

        if (ifExists(spk.getSpeakerID())){
            throw new DuplicateEntryException("This ID already exists");
        }

        try {
            if (jdbcTemplate.update(ADD_SPEAKER_QUERY, spk.getSpeakerModel(), spk.getSpeakerBrand(), spk.getSpeakerName()) > 0) {
                spk.setSpeakerID(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", String.class));
                return spk;
            }

            throw new SQLUpdateException("There was a problem adding the data or row does not exist");

        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    public boolean removeSpeaker(String speakerID) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(REMOVE_SPEAKER_QUERY, speakerID) <= 0) {
                throw new SQLUpdateException("Data could not be removed or row does not exist");
            } return true;
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    public boolean updateSpeaker(Speaker spk) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(UPDATE_SPEAKER_QUERY,
                    spk.getSpeakerModel(),
                    spk.getSpeakerBrand(),
                    spk.getSpeakerName(),
                    spk.getSpeakerID()) <= 0){
                throw new SQLUpdateException("Data could not be changed or row does not exist");
            } else return true;

        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    public boolean ifExists(String speakerID) {
        return (jdbcTemplate.queryForObject(EXIST_QUERY, Integer.class, speakerID) > 0);
    }

    private static final class SpeakerMapper implements RowMapper<Speaker> {
        public Speaker mapRow(ResultSet rs, int rowNum) throws SQLException {
            Speaker s = new Speaker();
            s.setSpeakerID(rs.getString(COLUMNS[0]));
            s.setSpeakerModel(rs.getString(COLUMNS[1]));
            s.setSpeakerBrand(rs.getString(COLUMNS[2]));
            s.setSpeakerName(rs.getString(COLUMNS[3]));
            return s;
        }
    }
}




