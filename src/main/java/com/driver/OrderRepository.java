
package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    HashMap<String, Order> ordersMap;
    HashMap<String, DeliveryPartner> deliveryPartnerMap;
    HashMap<String, List<String>> partnerOrdersMap;
    HashMap<String, String> orderPartnerPairMap;

    public OrderRepository(){
        this.ordersMap = new HashMap<>();
        this.deliveryPartnerMap= new HashMap<>();
        this.partnerOrdersMap = new HashMap<>();
        this.orderPartnerPairMap= new HashMap<>();
    }


    public void addOrder(Order order) {
        ordersMap.put(order.getId(), order);
    }


    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        deliveryPartnerMap.put(partnerId, deliveryPartner);
    }


    public void addOrderPartnerPair(String orderId, String partnerId) {
        //get orderlist of partner and update with current order
        List<String> orderList = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        orderList.add(orderId);
        partnerOrdersMap.put(partnerId, orderList);

        //update orderpartner pair
        orderPartnerPairMap.put(orderId,partnerId);

        //update the number of orders in partner object
        DeliveryPartner deliveryPartner = deliveryPartnerMap.get(partnerId);
        deliveryPartner.setNumberOfOrders(orderList.size());
    }

    public Order getOrderById(String orderId) {
        return ordersMap.get(orderId);
    }

    public DeliveryPartner getpartnerById(String partnerId) {
        return deliveryPartnerMap.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        int countOfOrder;
        countOfOrder = deliveryPartnerMap.get(partnerId).getNumberOfOrders();
        return countOfOrder;
    }

    public List<String> getOrderByParameter(String partnerId) {
        return partnerOrdersMap.get(partnerId);
    }


    public void deleteOrderById(String orderId) {
        ordersMap.remove(orderId);

        if(orderPartnerPairMap.containsKey(orderId)){
            String partnerId = orderPartnerPairMap.get(orderId);

            orderPartnerPairMap.remove(orderId);

            partnerOrdersMap.get(partnerId).remove(orderId);

            deliveryPartnerMap.get(partnerId).setNumberOfOrders(partnerOrdersMap.get(partnerId).size());
        }
    }

    public void deletePartnerById(String partnerId) {
        List<String> orderList = partnerOrdersMap.get(partnerId);

        for(String orderID : orderList){
            orderPartnerPairMap.remove(orderID);
        }

        partnerOrdersMap.remove(partnerId);

        deliveryPartnerMap.remove(partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int lastOrderTime=0;
        List<String> orderList = partnerOrdersMap.get(partnerId);

        for(String orderId:orderList){
            lastOrderTime = Math.max(lastOrderTime, ordersMap.get(orderId).getDeliveryTime());
        }
        return Order.getDeliveryTimeAsString(lastOrderTime);
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int countOfOrderLeft=0;
        int timeInt = Order.getDeliveryTimeAsInt(time);
        List<String>orderList= partnerOrdersMap.get(partnerId);

        for(String orderId:orderList){
            if(ordersMap.get(orderId).getDeliveryTime()>timeInt){
                countOfOrderLeft++;
            }
        }
        return countOfOrderLeft;
    }

    public Integer getCountOfUnassignedOrder() {
        return ordersMap.size()-orderPartnerPairMap.size();
    }

    public List<String> getAllOrder() {
        return new ArrayList<>(ordersMap.keySet());
    }



}
