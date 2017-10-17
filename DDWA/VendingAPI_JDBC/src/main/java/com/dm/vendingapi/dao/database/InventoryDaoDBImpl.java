package com.dm.vendingapi.dao.database;

import com.dm.vendingapi.dao.FileIOException;
import com.dm.vendingapi.dao.InventoryDao;
import com.dm.vendingapi.dao.NoItemInventoryException;
import com.dm.vendingapi.dto.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class InventoryDaoDBImpl implements InventoryDao{

    private JdbcTemplate jdbcTemplate;
    private static final String GET_QUERY
            = "SELECT * FROM inventory WHERE ProductName = ? LIMIT 1";
    private static final String QTY_QUERY
            = "SELECT COUNT(*) FROM inventory WHERE ProductName = ?";
    private static final String DELETE_QUERY
            = "DELETE FROM inventory WHERE InventoryID = ?";


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Product vendItem(String productName) throws NoItemInventoryException {

        try {

            Product p =  jdbcTemplate.queryForObject(GET_QUERY,
                    new ProductMapper(), productName);

            jdbcTemplate.update(DELETE_QUERY, p.getInventoryID());

            return p;

        } catch (NullPointerException | EmptyResultDataAccessException ex) {
            throw new NoItemInventoryException(productName + ": Item not in inventory... ");
        }
    }

    @Override
    public int getProductQuantity(String productName) {
        try {

            return
            jdbcTemplate.queryForObject(QTY_QUERY,Integer.class,productName);

        } catch (NullPointerException | EmptyResultDataAccessException ex) {
            // there were no results for the given contact id - we just
            // want to return null in this case
            return 0;
        }
    }

    @Override
    public Product getProduct(String productName) {
        try {
            return jdbcTemplate.queryForObject(GET_QUERY,
                    new ProductMapper(), productName);
        } catch (EmptyResultDataAccessException ex) {
            // there were no results for the given contact id - we just
            // want to return null in this case
            return null;
        }
    }

    @Override
    public void setInventory(Map<String, List<Product>> inventory) {}
    @Override
    public void writeInventoryToFile(String filename) throws FileIOException {}
    @Override
    public Map<String, List<Product>> readInventoryFromFile(String filename) throws FileIOException {
        return null;
    }

    private static final class ProductMapper implements RowMapper<Product> {
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product p = new Product();

            p.setInventoryID(rs.getInt("InventoryID"));
            p.setProductName(rs.getString("ProductName"));
            p.setBestBy(LocalDate.parse(rs.getString("ProductDate"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            p.setMessage(rs.getString("ProductMessage"));
            p.setInformation(rs.getString("ProductInformation"));
            return p;
        }
    }



}
