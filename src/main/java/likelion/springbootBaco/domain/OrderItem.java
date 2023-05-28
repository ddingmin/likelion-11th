package likelion.springbootBaco.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer price;
    private Integer count;

    /**
     * 스태틱 팩토리 메서드
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int orderCount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.price = orderPrice;
        orderItem.count = orderCount;
        // 연관관계 편의 메서드
        item.removeStock(orderCount);
        return orderItem;
    }

    public void setOrder(Order order) {
        // 주문의 주문 아이템 리스트에 파라미터의 주문을 추가한다.
        this.order = order;
        order.getOrderItemList().add(this);
    }

    public void setItem(Item item) {
        // 주문의 주문된 아이템 리스트에 파라미터의 아이템을 추가한다.
        this.item = item;
        item.getOrderItem().add(this);
    }

    /**
     * 비즈니스 로직
     */
    public int getTotalPrice() {
        return this.getPrice() * this.getCount();
    }

    public void cancel() {
        this.getItem().addStock(count);
    }
    // 취소했으므로, 해당 수량을 다시 올려준다.
}
