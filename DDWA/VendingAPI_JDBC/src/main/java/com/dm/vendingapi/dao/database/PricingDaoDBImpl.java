package com.dm.vendingapi.dao.database;

import com.dm.vendingapi.dao.FileIOException;
import com.dm.vendingapi.dao.PricingDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PricingDaoDBImpl implements PricingDao{

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, String> loadPricingFromFile(String filename) throws FileIOException {

        Map<String, String> priceMap = new LinkedHashMap<>();

        List<String[]> Products = jdbcTemplate.query(
                "SELECT * FROM prices", new PriceMapper());

        Products.forEach((p) -> {
            priceMap.put(p[0], p[1]);
        });

        return priceMap;
    }


    private static final class PriceMapper implements RowMapper<String[]> {

        public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                String[] currentLine = new String[2];

            currentLine[0] = rs.getString("ProductName");
            currentLine[1] = rs.getString("ProductPrice");
            
            return currentLine;
        }
    }
}
