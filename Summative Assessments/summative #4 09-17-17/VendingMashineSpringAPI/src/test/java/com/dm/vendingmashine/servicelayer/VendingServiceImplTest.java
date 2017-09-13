/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.servicelayer;

import com.dm.vendingmashine.dao.FileIOException;
import com.dm.vendingmashine.dao.InventoryDao;
import com.dm.vendingmashine.dao.InventoryDaoStubImpl;
import com.dm.vendingmashine.dao.NoItemInventoryException;
import com.dm.vendingmashine.dao.PricingDao;
import com.dm.vendingmashine.dao.PricingDaoStubImpl;
import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Danimaetrix
 */
public class VendingServiceImplTest {

//    // Instansiate our fake DAO's and the service layer
//    InventoryDaoStubImpl daoInv = new InventoryDaoStubImpl();
//    PricingDaoStubImpl daoPrice = new PricingDaoStubImpl();
//    VendingServiceImpl service = new VendingServiceImpl(daoInv, daoPrice);
    VendingService service;
    InventoryDao daoInv;
    PricingDao daoPrice;

    public VendingServiceImplTest() {
        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        this.service = ctx.getBean("vendingService",VendingServiceImpl.class );        
        this.daoInv = ctx.getBean("inventoryDaoStub", InventoryDaoStubImpl.class);
        this.daoPrice = ctx.getBean("pricingDaoStub", PricingDaoStubImpl.class);     
        
    }

    @Before
    public void setUp() throws FileIOException {
        daoInv.readInventoryFromFile("XYZ");
        daoPrice.loadPricingFromFile("XYZ");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testReturnPriceArrayWithStatus() {
        // The list contents themselves are a pass-through into the array,
        // so they do not need to be checked.  Here, we are only interested
        // in making sure that the array is appended with the correct
        // modifier to indicate its status
        
        List<String[]>testList = service.returnPriceArrayWithStatus();
        
        // Make sure our Coke inventory contains no sold out status
        assertEquals(testList.get(0)[2],"");
        
        // The next item should be sold out, since we only added Coke. 
        // It's status should NOT be blank
        assertNotEquals(testList.get(1)[2],"");  
        
    }
//
//    @Test
//    public void testVendProduct() throws Exception {
//        // This is a redundant test since the method is pass-through
//        // It is implemented as a final check on a core function
//        
//        // Make sure vend order decreases quantity by 1
//        int before = daoInv.getProductQuantity("Coke");
//        Product p = service.vendProduct("Coke");
//        int after = daoInv.getProductQuantity("Coke");
//        assertEquals(1, before - after);
//
//        // Verify that the first item vended is Coke #1
//        assertEquals(p.getProductName(), "Coke1");
//
//        // Verify that the second item vended is Coke #2
//        p = service.vendProduct("Coke");
//        assertEquals(p.getProductName(), "Coke2");
//    }
//
//    @Test
//    public void testIsSoldOut() {
//        // We empty the inventory of coke in this test to 
//        // make sure the sold out status updates
//
//        // Expect false -- we initialized with 3 available
//        assertEquals(service.isSoldOut("Coke"), false);
//
//        try {
//            service.vendProduct("Coke");
//            service.vendProduct("Coke");
//            service.vendProduct("Coke");
//
//        } catch (NoItemInventoryException e) {
//            fail("We should not reach this");
//        }
//
//        // Expect true -- we vended all 3 coke items
//        assertEquals(service.isSoldOut("Coke"), true);
//    }

    @Test
    public void testValidateMoney() {
        // Simple comparison of user's money to cost of object
        
        BigDecimal cash = new BigDecimal("2.50");
        Money m = new Money(cash);

        // Overpayment -- expect valid
        assertTrue(service.validateMoney(m, "Coke"));

        // Underpayment -- expect invalid
        m.setTotalmoney(BigDecimal.valueOf(1.49));
        assertFalse(service.validateMoney(m, "Coke"));

        // Exact payment -- expect valid
        m.setTotalmoney(BigDecimal.valueOf(1.50));
        assertTrue(service.validateMoney(m, "Coke"));

    }

    @Test
    public void testCalculateChange() {
        // Change breakdown is tested in the money test.  
        // Here, we just verify that correct change is being
        // given.
        
        BigDecimal cash = new BigDecimal("2.50");
        Money m = new Money(cash);

        // Try to overpay - expect 1.00 back
        try {
            m = service.calculateChange(m, "Coke");
        } catch (InsufficientFundsException e) {
            fail("this should not be reached");
        }

        assertEquals(m.getTotalmoney(), BigDecimal.valueOf(1).setScale(2));

        m.setTotalmoney(BigDecimal.valueOf(1.50));

        // Try to use exact change - expect 0.00 back
        try {
            m = service.calculateChange(m, "Coke");
        } catch (InsufficientFundsException e) {
            fail("this should not be reached");
        }

        assertEquals(m.getTotalmoney(), BigDecimal.valueOf(0).setScale(2));

        // Try to underpay -- expect exception
        m.setTotalmoney(BigDecimal.ZERO);
        try {
            service.calculateChange(m, "Coke");
            fail("this should not be reached");
        } catch (InsufficientFundsException e) {
            // Exception caught sucessfully
        }

    }

    @Test
    public void testUpdateInventory() throws Exception {

        // trivial pass through - no testing required
    }

    @Test
    public void testCheckForFileIOErrors() {

        // trivial pass through - no testing required
    }

    @Test
    public void testReturnPriceMap() {

        // trivial pass through - no testing required
    }

    @Test
    public void testGetProduct() {

        // trivial pass through - no testing required
    }

}
