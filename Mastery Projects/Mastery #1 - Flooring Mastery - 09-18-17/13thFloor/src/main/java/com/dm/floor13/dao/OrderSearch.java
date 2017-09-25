/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.model.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderSearch {

    Map<String, List<Order>> orderMap;

    OrderSearch(Map<String, List<Order>> orderMap) {
        this.orderMap = orderMap;
    }

    public List<Order> searchMap(SearchMethod method, Object key) {

        switch (method) {
            case PRODUCT_NAME:
                return searchByProduct((String) key);
            case DATE_AFTER:
                return searchByDateAfter((LocalDate) key);
            case DATE_BEFORE:
                return searchByDateBefore((LocalDate) key);
            case DATE:
                return searchByDate((LocalDate) key);
            case NAME:
                return searchByName((String) key);
            case STATE:
                return searchByState((String) key);
            case AREA_LT:
                return searchByAreaLT((BigDecimal) key);
            case AREA_GT:
                return searchByAreaGT((BigDecimal) key);
            case KEYWORD:
                return searchByKeyword((String) key);
            case ORDER_NUMBER:
                return searchByOrderNumber((String) key);
            case ALL:
                return searchAll();
            default:
                return null;
        }

    }

    public List<Order> searchByOrderNumber(String key) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getOrderNumber().startsWith(key))
                .sorted((o1, o2) -> -o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchByKeyword(String key) {        
                
        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.containsKeyWord(key))
                .sorted((o1, o2) -> -o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchAll() {

        return orderMap.values().stream().map(o -> o.get(0))
                .sorted((o1, o2) -> -o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchByAreaGT(BigDecimal area) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getArea().compareTo(area) >= 0)
                .sorted((o1, o2) -> -o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchByAreaLT(BigDecimal area) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getArea().compareTo(area) <= 0)
                .sorted((o1, o2) -> -o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchByState(String state) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getState().getStateCode().toLowerCase().contains(state.toLowerCase()))
                .sorted((o1, o2) -> -o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchByName(String name) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getLastName().toLowerCase().startsWith(name.toLowerCase())
                || o.getFirstName().toLowerCase().startsWith(name.toLowerCase()))
                .sorted((o1, o2) -> -o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchByFirstName(String firstName) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getFirstName().equals(firstName))
                .sorted((o1, o2) -> -o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchByProduct(String productName) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getProduct()
                .getProductName().toLowerCase().contains(productName.toLowerCase()))
                .sorted((o1, o2) -> -o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchByDate(LocalDate date) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getDate().equals(date))
                .sorted((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()))
                .collect(Collectors.toList());

    }

    public List<Order> searchByDateAfter(LocalDate date) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getDate().compareTo(date) >= 0)
                .sorted((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()))
                .collect(Collectors.toList());

    }

    public List<Order> searchByDateBefore(LocalDate date) {

        return orderMap.values().stream().map(o -> o.get(0))
                .filter((o) -> o.getDate().compareTo(date) <= 0)
                .sorted((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()))
                .collect(Collectors.toList());

    }

}
