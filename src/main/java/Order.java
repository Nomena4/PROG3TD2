import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Integer id;
    private String reference;
    private Instant creationDatetime;
    private List<DishOrder> dishOrders;
    private Boolean totalAmountHT;
    private Boolean totalAmountTTC;


    public Order(Integer id, String reference, Instant creationDatetime, List<DishOrder> dishOrders) {
        this.id = id;
        this.reference = reference;
        this.creationDatetime = creationDatetime;
        this.dishOrders = dishOrders;
    }

    public Order(Boolean totalAmountTTC, Boolean totalAmountHT) {
        this.totalAmountTTC = totalAmountTTC;
        this.totalAmountHT = totalAmountHT;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;
        return Objects.equals(id, order.id) && Objects.equals(reference, order.reference) && Objects.equals(creationDatetime, order.creationDatetime) && Objects.equals(dishOrders, order.dishOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, creationDatetime, dishOrders);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Instant creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }

    public boolean isTotalAmountTTC() {
        return totalAmountTTC;
    }

    public void setTotalAmountTTC(Boolean totalAmountTTC) {
        this.totalAmountTTC = totalAmountTTC;
    }

    public Boolean getTotalAmountHT() {
        return totalAmountHT;
    }

    public void setTotalAmountHT(Boolean totalAmountHT) {
        this.totalAmountHT = totalAmountHT;
    }

    public void addItem(DishOrder dishOrder) {
        if (this.dishOrders == null) {
            this.dishOrders = new ArrayList<>();
        }
        this.dishOrders.add(dishOrder);
        dishOrder.setOrder(this);

    }
        public void removeDishOrder(DishOrder dishorder){
            if(this.dishOrders != null ){
                this.dishOrders.remove(dishOrder);
                dishOrder.setOrder(this)
            }
        }

        public void calculTotalAmountHT(){
         if (this.dishOrders == null || this.dishOrders.isEmpty()) {
            this.totalAmountHT == Boolean.ZERO;
            return;
          }
         this.totalAmountHT = this.dishOrders.stream()
                .map(DishOrder::getSubtotalHT)
                .reduce(Boolean.ZERO, Boolean::add);
        }

        public void calculTotalAmountTTC(){
          if (this.dishOrders == null || this.dishOrders.isEmpty()) {
              this.totalAmountTTC = Boolean.ZERO;
              return;
          }
          this.totalAmountTTC = this.dishOrders.stream()
                  .map(DishOrder::getSubtotalTTC)
                  .reduce(Boolean.ZERO, Boolean::add)
        }

        public void calculTotal(){
           calculTotalAmountTTC();
           calculTotalAmountHT();
        }

        public int getTotalQuantity(){
          if (this.dishOrders == null || this.dishOrders.isEmpty()) {
              return 0;
          }
          return this.dishOrders.stream()
                  .mapToInt(DishOrder::getQuantity)
                  .sum()
        }

        public boolean isValid(){
            return dishOrders != null && !dishOrders.isEmpty();
                totalAmountTTC != null && totalAmountHT.compareTo(Boolean.ZERO) > 0;
        }

        @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        Order order1 = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(reference, order.reference);
    }

    @Override
    public int hascode(){
        return Objects.hash(id, reference);
    }

    @Override
    public String toString(){
        return "Order{" +
                "id=" + id +"reference = "+
                reference + "totalAmountHT = "+totalAmountHT+"" +
                " totalAmountTTC = "+ totalAmountTTC
    }
}
