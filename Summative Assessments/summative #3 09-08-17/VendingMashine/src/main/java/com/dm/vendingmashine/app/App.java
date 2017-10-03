/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.app;

import com.dm.vendingmashine.controller.VendingMashineController;
import com.dm.vendingmashine.dao.VendingMashineInventoryDao;
import com.dm.vendingmashine.dao.VendingMashineInventoryDaoImpl;
import com.dm.vendingmashine.dao.VendingMashinePricingDao;
import com.dm.vendingmashine.dao.VendingMashinePricingDaoImpl;
import com.dm.vendingmashine.servicelayer.VendingMashineService;
import com.dm.vendingmashine.servicelayer.VendingMashineServiceImpl;
import com.dm.vendingmashine.ui.UserIo;
import com.dm.vendingmashine.ui.UserIoConsoleImpl;
import com.dm.vendingmashine.ui.VendingMashineView;
import com.dm.vendingmashine.ui.VendingMashineViewPrettyImpl;

/**
 *
 * @author danimaetrix
 */
public class App {

    public static void main(String[] args)  {
        
        // IO injection
        UserIo io = new UserIoConsoleImpl();
        VendingMashineView view = new VendingMashineViewPrettyImpl(io);
        
        // Inject service layer with DAO implementations
        VendingMashineInventoryDao daoInv = new VendingMashineInventoryDaoImpl();
        VendingMashinePricingDao daoPrices = new VendingMashinePricingDaoImpl();
        VendingMashineService service = new VendingMashineServiceImpl(daoInv, daoPrices);        
        
        // Inject controller with view and service layer implementations
        VendingMashineController controller = new VendingMashineController(view, service);        
   
        controller.run();



    }
}
