package com.dm.impulsereactor.dao;

import com.dm.impulsereactor.Exceptions.DuplicateEntryException;
import com.dm.impulsereactor.Exceptions.SQLUpdateException;
import com.dm.impulsereactor.model.Cab;
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
public class CabDao {
    private static final String [] COLUMNS = {"CabID","CabName","Brand","Size"};
    private JdbcTemplate jdbcTemplate;
    private QueryRunner sc;

    @Inject
    public CabDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sc = new QueryRunner(jdbcTemplate);
    }

    private static final String ADD_CAB_QUERY =
            "INSERT INTO cabs (Brand, CabName, Size) VALUES(?,?,?)";
    private static final String EXIST_QUERY =
            "SELECT COUNT(*) FROM cabs WHERE CabID = ?";
    private static final String REMOVE_CAB_QUERY =
            "DELETE FROM cabs WHERE CabID = ?";
    private static final String UPDATE_CAB_QUERY =
            "UPDATE cabs SET Brand = ?, CabName = ?, Size = ? WHERE CabID = ?";


    /**
     * @param flags - set search flags. 't' for partial match, and 'f' for exact.  Enter
     *              one letter for each search variable (i.e., 'ftff', or null to use default
     * @param args - search terms.  In order: CabID, CabName, Brand, Size
     */
    public List<Cab> searchCabs(String flags, String... args) {
        return (List<Cab>)(Object) sc.searchDatabase(new CabMapper(), "cabs", COLUMNS, flags, args);
    }
    
    @Transactional
    public Cab addCab(Cab cab) throws SQLUpdateException, DuplicateEntryException {

        if (ifExists(cab.getCabID())){
            throw new DuplicateEntryException("This ID already exists");
        }

        try {
            if (jdbcTemplate.update(ADD_CAB_QUERY, cab.getBrand(), cab.getCabName(), cab.getSize()) > 0) {
                cab.setCabID(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", String.class));
                return cab;
            }

            throw new SQLUpdateException("There was a problem adding the data or row does not exist");

        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    public boolean removeCab(String cabID) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(REMOVE_CAB_QUERY, cabID) <= 0) {
                throw new SQLUpdateException("Data could not be removed or row does not exist");
            } return true;
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    public boolean updateCab(Cab cab) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(UPDATE_CAB_QUERY,
                    cab.getBrand(),
                    cab.getCabName(),
                    cab.getSize(),
                    cab.getCabID()) <= 0){
                throw new SQLUpdateException("Data could not be changed or row does not exist");
            } else return true;

        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    public boolean ifExists(String cabID) {
        return (jdbcTemplate.queryForObject(EXIST_QUERY, Integer.class, cabID) > 0);
    }

    private static final class CabMapper implements RowMapper<Cab> {
        public Cab mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cab c = new Cab();
            c.setCabID(rs.getString(COLUMNS[0]));
            c.setCabName(rs.getString(COLUMNS[1]));
            c.setBrand(rs.getString(COLUMNS[2]));
            c.setSize(rs.getString(COLUMNS[3]));
            return c;
        }
    }
}




