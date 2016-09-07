package at.fhhgb.caas.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 11.05.2015.
 */
public class Order implements Serializable, Comparable<Order> {

    private static final long serialVersionUID = -1985286299989975961L;

    private List<Meal> mealList = new ArrayList<>();

    private String orderID;

    private String personId;
    private String deliverHour;
    private double totalPrice;

    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(String personId) {
        this(personId, null);
    }

    public Order(String personId, String orderID) {
        this.personId = personId;
        this.orderID = orderID;
        this.orderStatus = OrderStatus.OPEN;
    }

    public void addMeal(Meal meal) {
        getMealList().add(meal);
        totalPrice += meal.getPrice();
    }

    public void removeMeal(int pos) {
        if (pos <= 0 || pos >= getMealList().size())
            return;
        getMealList().remove(pos);
    }

    public void setDeliverHour(String deliverHour) {
        this.deliverHour = deliverHour;
    }

    public String getDeliverHour() {
        return deliverHour;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPersonId() {
        return personId;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderID != null ? !orderID.equals(order.orderID) : order.orderID != null) return false;
        if (personId != null ? !personId.equals(order.personId) : order.personId != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = orderID.hashCode();
        result = 31 * result + (mealList != null ? mealList.hashCode() : 0);
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (deliverHour != null ? deliverHour.hashCode() : 0);
        temp = Double.doubleToLongBits(totalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Order o) {
        return orderID.compareTo(o.orderID);
    }

}
