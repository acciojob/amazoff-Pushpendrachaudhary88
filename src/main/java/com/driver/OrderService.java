
package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

 // @Autowired
 OrderRepository orderRepository = new OrderRepository();

 public void addOrder(Order order) {
  orderRepository.addOrder(order);
 }

 public void addPartner(String partnerId) {

  //DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
  orderRepository.addPartner(partnerId);

 }

 public void addOrderPartnerPair(String orderId, String partnerId) {
  orderRepository.addOrderPartnerPair(orderId, partnerId);
 }

 public Order getOrderById(String orderId) {

  return orderRepository.getOrderById(orderId);
 }

 public DeliveryPartner getPartnerById(String partnerId) {
  return orderRepository.getpartnerById(partnerId);
 }

 public Integer getOrderCountByPartnerId(String partnerId) {
  return orderRepository.getOrderCountByPartnerId(partnerId);
 }


 public List<String> getOrderByPartnerId(String partnerId) {
  return orderRepository.getOrderByParameter(partnerId);
 }

 public List<String> getAllOrder() {
  return orderRepository.getAllOrder();
 }

 public Integer getCountOfUnassignedOrders() {
  return orderRepository.getCountOfUnassignedOrder();
 }

 public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
  return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
 }

 public String getLastDeliveryTimeByPartnerId(String partnerId) {
  return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
 }

 public void deletePartnerById(String partnerId) {
  orderRepository.deletePartnerById(partnerId);
 }

 public void deleteOrderById(String orderId) {
  orderRepository.deleteOrderById(orderId);
 }
}
