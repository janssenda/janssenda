package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.OrganizationDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Organization;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Named
public class OrganizationDaoImpl implements OrganizationDao {


    private JdbcTemplate jdbcTemplate;

    @Inject
    public OrganizationDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_ORG_QUERY =
            "INSERT INTO organizations (OrgName, Description) VALUES(?,?)";
    private static final String EXIST_QUERY =
            "SELECT COUNT(*) FROM organizations WHERE OrgID = ?";

    private static final String REMOVE_ORG_QUERY =
            "DELETE FROM organizations WHERE OrgID = ?";

    private static final String GET_ORG_QUERY =
            "SELECT * FROM organizations";

    private static final String GET_ORG_QUERY_MULTI =
            "SELECT * FROM organizations WHERE 1 = 1 " +
                    "AND (@OrgID IS NULL OR OrgID = @OrgID) " +
                    "AND (@OrgName IS NULL OR OrgName = @OrgName) " +
                    "AND (@Description IS NULL OR Description = @Description) ";

    private static final String UPDATE_ORG_QUERY =
            "UPDATE organizations SET OrgName = ?, Description = ? WHERE OrgID = ?";

    @Override
    @Transactional
    public Organization addOrg(Organization org) throws SQLUpdateException, DuplicateEntryException {

        if (ifExists(org.getOrgID())){
            throw new DuplicateEntryException("Organization exists, update instead.");
        }

        try {
            if (jdbcTemplate.update(ADD_ORG_QUERY, org.getOrgName(), org.getDescription()) > 0) {
                org.setOrgID(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
                return org;
            }

            throw new SQLUpdateException("There was a problem adding the data or row does not exist");

        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean removeOrg(int orgID) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(REMOVE_ORG_QUERY, Integer.toString(orgID)) <= 0) {
                throw new SQLUpdateException("Data could not be removed or row does not exist");
            } return true;
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean updateOrg(Organization org) throws SQLUpdateException {

        try {
            if (jdbcTemplate.update(UPDATE_ORG_QUERY,
                    org.getOrgName(),
                    org.getDescription(),
                    org.getOrgID()) <= 0){
                throw new SQLUpdateException("Data could not be changed or row does not exist");
            } else return true;

        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public List<Organization> getAllOrgs() {
        return jdbcTemplate.query(GET_ORG_QUERY, new OrgMapper());
    }

    @Override
    public List<Organization> getFromOrgs(String... args) {
        String[] allArgs = {null, null, null};
        System.arraycopy(args, 0, allArgs, 0, args.length);
        return getFromOrgs(allArgs[0], allArgs[1], allArgs[2]);
    }

    @Override
    @Transactional
    public List<Organization> getFromOrgs(String orgID, String orgName, String description) {

        if (orgID == null || orgID.isEmpty()) { orgID = null;}
        if (orgName == null || orgName.isEmpty()) { orgName = null; }
        if (description == null || description.isEmpty()) {description = null;}

        String setup = "SET @OrgID = ?, @OrgName = ?, @Description = ?; ";

        try {
            Connection c = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(setup);

            ps.setString(1, orgID);
            ps.setString(2, orgName);
            ps.setString(3, description);


            String qdata = ps.toString().split(":")[1].trim();
            qdata = qdata.split("]")[0].trim();
            c.close();

            // Execute the prepared statement string to set the variables
            jdbcTemplate.execute(qdata);

            // Search for orgs based on the given criteria,
            return jdbcTemplate.query(GET_ORG_QUERY_MULTI, new OrgMapper());

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean ifExists(int orgID) {
        return (jdbcTemplate.queryForObject(EXIST_QUERY, Integer.class, Integer.toString(orgID)) > 0);
    }

    private static final class OrgMapper implements RowMapper<Organization> {

        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization o = new Organization();
            o.setOrgID(rs.getInt("OrgId"));
            o.setOrgName(rs.getString("OrgName"));
            o.setDescription(rs.getString("Description"));
            return o;
        }
    }




}
