/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.exceptions.MissingFileException;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.model.Order;
import com.dm.floor13.model.Product;
import com.dm.floor13.model.State;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Danimaetrix
 */
public class FileHandlerTest {
    int orderNumberLength = 5;
    String rootDir = "./data/orders";

    public FileHandlerTest() {
    }

    @Test
    public void testReadTaxesFromFile() throws Exception {
    }

    @Test
    public void testReadPricingFromFile() throws Exception {
    }

    @Test
    public void testWriteAllOrdersToFile() throws Exception {
        String dir = rootDir+"/test_files";

        FileHandler files = new FileHandler();
        List<Order> orders = new ArrayList<>();

        orders = files.readAllOrders(dir, orderNumberLength);

        files.writeAllOrders(orders, rootDir + "/test_files/output/output_data.csv");

        List<Order> ordersFromOutput = files.readAllOrders(rootDir + "/test_files/output",orderNumberLength);

        assertEquals(orders.get(0).hashCode(), ordersFromOutput.get(0).hashCode());
        assertTrue(orders.get(0).equals(ordersFromOutput.get(0)));

    }

    @Test
    public void testReadAllOrders() throws Exception {
        String dir = rootDir+"/test_files";

        FileHandler files = new FileHandler();
        List<Order> orders = new ArrayList<>();
        orders = files.readAllOrders(dir,orderNumberLength);

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
    public void testReadFileExceptions() {
        String dir = rootDir+"/test_files/broken_files/";

        FileHandler files = new FileHandler();
        List<Order> orders = new ArrayList<>();

        try {
            orders = files.readOrdersFromSingleFile(dir + "broken_lines.txt",orderNumberLength);
            assertEquals(orders.get(0).getOrderNumber(), "44592");
        } catch (FileSkipException | MissingFileException e) {
            fail("No exceptions should be thrown");
        }

        assertEquals(files.getSkippedLineCount(), 2);

        try {
            orders = files.readOrdersFromSingleFile(dir + "empty_file.txt",orderNumberLength);
            fail("Exceptions should be thrown");
        } catch (FileSkipException | MissingFileException e) {

        }

        try {
            orders = files.readOrdersFromSingleFile(dir + "wholly_corrupt.txt",orderNumberLength);
            fail("Exceptions should be thrown");
        } catch (FileSkipException | MissingFileException e) {

        }

    }

    @Test
    public void testReadMultiFileExceptions() {
        String dir = rootDir+"/test_files/broken_files/";

        FileHandler files = new FileHandler();
        List<Order> orders = new ArrayList<>();

        orders = files.readAllOrders(dir,orderNumberLength);
        
        assertEquals(2,files.getSkippedFileCount());
        assertEquals(6,files.getSkippedLineCount());
        

    }

    @Test
    public void testAuditLogToFile() throws Exception {
    }

}
