package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Repository
public class OrderRepository {

    HashMap<String,Order> ordersMap;
    HashMap<String,DeliveryPartner> deliveryPartnerMap;
    HashMap<String,List<String>> partnerOrdersMap;
    HashMap<String,String> orderPartnerPairMap;

    public OrderRepository() {
        this.ordersMap = new HashMap<>();
        this.deliveryPartnerMap = new HashMap<>();
        this.partnerOrdersMap = new HashMap<>();
        this.orderPartnerPairMap = new HashMap<>();
    }

    public void addOrder(Order order) {
        ordersMap.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        deliveryPartnerMap.put(partnerId,deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        List<String> orderList = partnerOrdersMap.getOrDefault(partnerId,new ArrayList<>());
        orderList.add(orderId);
        partnerOrdersMap.put(partnerId,orderList);

        orderPartnerPairMap.put(orderId,partnerId);

        DeliveryPartner deliveryPartner = deliveryPartnerMap.get(partnerId);
        deliveryPartner.setNumberOfOrders(orderList.size());
    }

    public Order getOrderById(String orderId) {
        return ordersMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerMap.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        int countOfOrder;
        countOfOrder=deliveryPartnerMap.get(partnerId).getNumberOfOrders();
        return countOfOrder;
    }

    public List<String> getOrderByPartnerId(String partnerId) {
        return partnerOrdersMap.get(partnerId);
    }

    public List<String> getAllOrder() {
        return new ArrayList<>(ordersMap.keySet());

    }

    public Integer getCountOfUnassignedOrder() {
        return ordersMap.size()-orderPartnerPairMap.size();

    }

    public Integer getOrderLeftAfterTimeByPartnerId(String time, String partnerId) {
        int countOfOrderLeft = 0;
        int timeInt = Order.getDeliveryTimeAsInt(time);
        List<String> orderList = partnerOrdersMap.get(partnerId);

        for(String orderId: orderList){
            if(ordersMap.get(orderId).getDeliveryTime()>timeInt){
                countOfOrderLeft++;
            }
        }
        return countOfOrderLeft;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int lastOrderTime=0;
        List<String> orderList = partnerOrdersMap.get(partnerId);

        for(String orderId: orderList){
            lastOrderTime=Math.max(lastOrderTime,ordersMap.get(orderId).getDeliveryTime());
        }
        return Order.getDeliveryTimeString(lastOrderTime);
    }

    public void deletePartnerById(String partnerId) {
        List<String> orderList = partnerOrdersMap.get(partnerId);

        for(String orderId : orderList){
            orderPartnerPairMap.remove(orderId);
        }
        partnerOrdersMap.remove(partnerId);
        deliveryPartnerMap.remove((partnerId));
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


}
