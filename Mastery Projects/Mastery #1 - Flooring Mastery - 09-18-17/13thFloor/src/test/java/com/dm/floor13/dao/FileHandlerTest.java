/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.model.Order;
import com.dm.floor13.model.Product;
import com.dm.floor13.model.State;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Danimaetrix
 */
public class FileHandlerTest {

    public FileHandlerTest() {
    }

    @Test
    public void testReadTaxesFromFile() throws Exception {
    }

    @Test
    public void testReadPricingFromFile() throws Exception {
    }

    @Test
    public void testWriteOrdersToFile() throws Exception {
    }

    @Test
    public void testReadAllOrdersFromFile() throws Exception {
        String dir = "./orders/test_files";

        FileHandler files = new FileHandler();
        List<Order> orders = new ArrayList<>();
        try {
            orders = files.readAllOrdersFromFile(dir);
        } catch (FileIOException e) {
            fail("File read failed");
        }

        assertEquals(8, orders.size());

        Order order = orders.get(0);
        Product p = order.getProduct();
        State st = order.getState();

        assertEquals("37282", order.getOrderNumber());
        assertEquals("01/07/2002", order.getDate()
                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        assertEquals("Westcar", order.getLastName());
        assertEquals("Biddie", order.getFirstName());
        assertEquals("AZ", st.getStateCode());
        assertEquals("634", order.getArea().toString());
        assertEquals("Tile", p.getProductName());
        assertEquals("5.155", st.getTaxrate().toString());
        assertEquals("1.75", p.getCostpersqft().toString());
        assertEquals("2.1", p.getLaborpersqft().toString());

        BigDecimal d1, d2, d3;

        d1 = order.getArea().multiply(p.getCostpersqft());
        assertEquals(d1, order.getMaterialCost());

        d2 = order.getArea().multiply(p.getLaborpersqft());
        assertEquals(d2, order.getLaborCost());

        d3 = (d1.add(d2));
        d3 = d3.multiply(((st.getTaxrate().scaleByPowerOfTen(-2)).add(BigDecimal.ONE)));
        assertEquals(d3, order.getTotalCost());
        
    }

    @Test
    public void testReadOrdersFromFile() throws Exception {
    }

    @Test
    public void testAuditLogToFile() throws Exception {
    }

}
