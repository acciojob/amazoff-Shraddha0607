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
            orderToPartnerMap.put(orderId,  partnerId);     // mark order assigned

//            DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
//           int currOrder =  deliveryPartner.getNumberOfOrders()+1;
//
//           deliveryPartner.setNumberOfOrders(currOrder);
//           partnerMap.put(partnerId, deliveryPartner);

            DeliveryPartner dp=partnerMap.get(partnerId);
            dp.setNumberOfOrders(dp.getNumberOfOrders()+1);

        }
    }

    public Order findOrderById(String orderId){
        // your code here
        Order order = null;
            if(orderMap.containsKey(orderId)){
                order= orderMap.get(orderId);
            }
            return order;
    }

    public DeliveryPartner findPartnerById(String partnerId) {
        // your code here
        DeliveryPartner deliveryPartner = null;
        if(partnerMap.containsKey(partnerId)){
            deliveryPartner= partnerMap.get(partnerId);
        }
        return deliveryPartner;
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        int totalOrder = partnerMap.get(partnerId).getNumberOfOrders();
        return totalOrder;
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        List<String> allOrdersRelatedToPartner = new ArrayList<>();
        HashSet<String> hs = new HashSet<>();
        hs = partnerToOrderMap.get(partnerId);
        for(String order :  hs){
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
        HashSet<String> hs = partnerToOrderMap.get(partnerId);
        for(String assignedOrder : hs){
            orderToPartnerMap.put(assignedOrder, "not assigned");
        }
        partnerToOrderMap.remove(partnerId);

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
        int ans=0;
        String[] arr=timeString.split(":");
        int gt=Integer.parseInt(arr[0])*60+Integer.parseInt(arr[1]);

        HashSet<String>hs=partnerToOrderMap.get(partnerId);

        for(String orderid:hs){
            Order order=orderMap.get(orderid);

            if(orderid!=null && gt>order.getDeliveryTime()){
                ans++;
            }
        }
        return ans;

    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
//        int lastTime = Integer.MIN_VALUE;
//        HashSet<String>hs=partnerToOrderMap.get(partnerId);
//
//        for(String orderid:hs){
//            Order order=orderMap.get(orderid);
//
////
//            lastTime = Math.max(lastTime, order.getDeliveryTime());
//        }
        return "";
    }
}