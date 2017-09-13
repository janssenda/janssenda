/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dao;

import com.dm.vendingmashine.dto.Product;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Danimaetrix
 */
public class InventoryDaoImplTest {

//    InventoryDaoStubImpl dao = new InventoryDaoStubImpl();
    InventoryDaoStubImpl dao;

    public InventoryDaoImplTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.dao = ctx.getBean("inventoryDaoStub", InventoryDaoStubImpl.class);
    }

    @Test
    public void testReadInventoryFromFile() throws Exception {
        // All we do in this test is stream the fake inventory into 
        // the sorted map.  We verify that the map contains only one key,
        // and 3 objects for that key. (Only Coke, and 3 coke objects in the inventory)

        Map<String, List<Product>> inventory = dao.readInventoryFromFileNormal("XYZ");

        // Make sure the lambda stream creates the map properly
        assertEquals(inventory.size(), 1);
        assertEquals(inventory.get("Coke1").size(), 3);
    }

    @Test
    public void testWriteInventoryToFile() throws Exception {
        // This just reads our fake inventory and prints it to the
        // screen instead of a file, so we can do a visual check to make 
        // sure it looks how it should

        dao.readInventoryFromFile("x");
        dao.writeInventoryToFile("XYZ");
    }

    @Test
    public void testVendItem() {
        // Verifies that items are vended in the correct order
        // no inventory test is below

        try {
            dao.readInventoryFromFile("x");
            Product testP = dao.getProduct("Coke");
            Product testP2 = dao.vendItem("Coke");

            assertEquals(testP, testP2);

            testP = dao.getProduct("Coke");
            testP2 = dao.vendItem("Coke");

            assertEquals(testP, testP2);
        } catch (NoItemInventoryException e) {

        }

    }

    @Test
    public void testGetProductQuantity_testNoItemException() {
        // We check our quantities initially, and then vend
        // each item.  We check each time to make sure that
        // The next one in line is the next one to be vended.
        // Once we reach zero, we test our exception by trying
        // to vend one more.

        dao.readInventoryFromFile("x");
        assertEquals(3, dao.getProductQuantity("Coke"));

        // Vend some items to reduce quantity and recheck each time
        try {
            dao.vendItem("Coke");
            assertEquals(2, dao.getProductQuantity("Coke"));

            dao.vendItem("Coke");
            assertEquals(1, dao.getProductQuantity("Coke"));

            dao.vendItem("Coke");
            assertEquals(0, dao.getProductQuantity("Coke"));
        } catch (NoItemInventoryException e) {
            // these are fine as inventory is > 0
        }

        // Make another attempt to vend item once quantity is zero....
        try {
            dao.vendItem("Coke");
            fail("Exception was not caught...");
        } catch (NoItemInventoryException e) {
            // Exception caught appropriately
        }

    }

    @Test
    public void testGetProduct() {
        // In this test we get the first product twice to make sure it
        // is not affected by our call. Then we vend it and get the next 
        // one to ensure that the first one has now been released by
        // the vend call and the next one is first in line

        dao.readInventoryFromFile("x");

        // Make sure that we get the next product in line only
        Product testP = dao.getProduct("Coke");
        assertEquals(testP.getProductName(), "Coke1");

        testP = dao.getProduct("Coke");
        assertEquals(testP.getProductName(), "Coke1");

        // Vend one and check to verify the next one in line is #2
        try {
            dao.vendItem("Coke");
            testP = dao.getProduct("Coke");
            assertEquals(testP.getProductName(), "Coke2");
        } catch (NoItemInventoryException e) {
            fail("Exception should not have been thrown...");
        }

    }

}
