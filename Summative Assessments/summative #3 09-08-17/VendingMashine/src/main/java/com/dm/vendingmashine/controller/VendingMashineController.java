/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.controller;

import com.dm.vendingmashine.dao.FileIOException;
import com.dm.vendingmashine.dao.NoItemInventoryException;
import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.servicelayer.InsufficientFundsException;
import com.dm.vendingmashine.servicelayer.VendingMashineService;
import com.dm.vendingmashine.ui.VendingMashineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author danimaetrix
 */
public class VendingMashineController {

    private final Money userMoney;

    // Dependency Injection
    VendingMashineService service;
    VendingMashineView view;

    //service.vtesting ();
    public VendingMashineController(VendingMashineView view, VendingMashineService service) {
        this.service = service;
        this.view = view;
        this.userMoney = new Money(BigDecimal.valueOf(0));
    }

    // Main program loop
    public void run() {
        
        // Make sure that the inventory was loaded correctly
        String errmsg = service.checkForFileIOErrors();

        if (errmsg != null) {
            view.displayExceptionMessage(errmsg);
        } else {

            // Our main loop - this will continue until user has finished
            drinkMenuSelection(userMoney);
            // Dispense change once loop is complete
            view.showChange(userMoney);

            // Update the inventory file with changes
            try {
                service.updateInventory();
            } catch (FileIOException e) {
                view.displayExceptionMessage("Error updating inventory file: " + e.getMessage());
            }
        }
        view.waitOnUser();
        exitProgram();

    }
    // End main program loop
    // Main drink menu screen

    private boolean drinkMenuSelection(Money userMoney) {
        boolean repeat = true;
        List<String[]> priceList;

        // Loop the drink menu continuously
        while (repeat) {
            // The price list is obtained with <Sold Out> indicators where necessary
            priceList = service.returnPriceArrayWithStatus();
            view.cls();
            
            // We use a generated menu to allow for dynamic changes
            // of inventory and merchandising
            view.generateMenu(priceList);
            //break;
            int choice = view.getUserDrinkSelection(userMoney, priceList.size());

            switch (choice) {
                case 0:
                    // User wants to get chagne and leave
                    return false;
                case 1:
                    // User wants to add more money                    
                    view.userAddMoney(userMoney);
                    break;
                default:
                    
                    String name = priceList.get(choice - 2)[0];

                    // Money is validated explicitly instead of with an exception
                    if (!service.validateMoney(userMoney, name)) {
                        view.insufficientFundsBanner();

                    // Check to see whether the item is sold out    
                    } else if (service.isSoldOut(name)) {
                        view.soldOutBanner();

                    // If conditions are okay, continue to vend    
                    } else {

                        // Verify that the user has added enough money and
                        // complete the transaction
                        try {
                            userMoney = service.calculateChange(userMoney, name);
                        } catch (InsufficientFundsException e) {
                            view.displayExceptionMessage(e.getMessage());
                            break;
                        }
                        
                        // Everything should be fine here - attempt to vend product.
                        // There should be no problems.
                        try {
                            view.showTheProduct(service.getProduct(name));
                            service.vendProduct(name);
                        } catch (NoItemInventoryException e) {
                            view.displayExceptionMessage(e.getMessage());
                            break;
                        }
                        
                    }
                    view.waitOnUser();
                    break;
            }
        }

        return true;
    }

    private void exitProgram() {
        view.showExitMessage();
    }

}
