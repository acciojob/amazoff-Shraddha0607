package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        String id = order.getId();
        orderMap.put(id, order);
        orderToPartnerMap.put(id, "not assigned");
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId, deliveryPartner);
        partnerToOrderMap.put(partnerId, new HashSet<String>());
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            partnerToOrderMap.get(partnerId).add(orderId);    // add order in partnerId, to show order is assigned to this partner
            orderToPartnerMap.put(orderId, partnerId);     // mark order assigned

            DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
           int currOrder =  deliveryPartner.getNumberOfOrders();
           int newTotalOrder = currOrder +1;
           deliveryPartner.setNumberOfOrders(newTotalOrder);
           partnerMap.put(partnerId, deliveryPartner);

        }
    }

    public Order findOrderById(String orderId)throws Exception{
        // your code here

            if(orderMap.containsKey(orderId)){
                return orderMap.get(orderId);
            }
            else{
                throw new Exception("Order Id is invalid!!");
            }

    }

    public DeliveryPartner findPartnerById(String partnerId) throws Exception{
        // your code here
        if(partnerMap.containsKey(partnerId)){
            return partnerMap.get(partnerId);
        }
        else{
            throw new Exception("Partner Id is invalid!!");
        }
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        int totalOrder = partnerMap.get(partnerId).getNumberOfOrders();
        return totalOrder;
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        List<String> allOrdersRelatedToPartner = new ArrayList<>();
        for(String order :  partnerToOrderMap.keySet()){
            allOrdersRelatedToPartner.add(order);
        }
        return allOrdersRelatedToPartner;
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        List<String> allOrders = new ArrayList<>();
        for(String orderId : orderMap.keySet() ){
            allOrders.add(orderId);
        }
        return allOrders;
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        partnerMap.remove(partnerId);
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        orderMap.remove(orderId);
        String partnerId = orderToPartnerMap.get(orderId);
        if(partnerId.equals("not assigned")){

        }
        else{
            HashSet<String > allOrders = partnerToOrderMap.get(partnerId);
           for(String assignedOrderId : allOrders){
               if(assignedOrderId.equals(orderId)) allOrders.remove(assignedOrderId);
           }
        }

    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        int total_unassignedOrders = 0;
        for(String orderStatus : orderToPartnerMap.values()){
            if(orderStatus.equals("not assigned")){
                total_unassignedOrders++;
            }
        }
        return total_unassignedOrders;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        return 0;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        return "";
    }
}