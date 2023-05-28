package likelion.springbootBaco.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "orders") // 이거 안하면 에러
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    // 다대일 관계를 의미한다. 앞선 설명들과 마찬가지로 order가 다의 관계가 된다.
    // 주문은 한 사람의 주문에 종속된다. 따라서 다의 관계로 지정되었다.
    @JoinColumn(name = "member_id")
    // 한 사람의 주문이기때문에 주문이 연관관계의 주인이 된다.
    // 따라서 member의 필드를 채우기 위한 JoinColumn 이다.
    private Member member;

    @OneToOne(fetch = LAZY, cascade = ALL)
    // cascade 옵션은 일대일 관계이기 때문에 해당 속성이 변하게 된다면,
    // 연관관계에서도 변해야 한다. 이를 모두 수정해주는 옵션이다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        // 멤버의 주문 리스트에 주문한 현재 주문을 추가한다.
        member.getOrderList().add(this);
    }

    public static Order createOrder(Member member, OrderItem... orderItems) {
        // Order를 생성하는 정적 팩토리 메소드

        Order order = new Order();
        order.setMember(member);
        order.orderDate = LocalDateTime.now();
        // 주문 시간을 대입한다.
        order.orderStatus = OrderStatus.ORDERED;
        // 주문이 생성되면 ORDERED 타입을 갖는다.
        order.delivery = Delivery.createDelivery(order, member.getAddress().getCity(),
                member.getAddress().getState(),
                member.getAddress().getStreet(),
                member.getAddress().getZipcode());

        // 리스트의 존재하는 객체들을 순차적으로 접근할 수 있는 이터레이터 형태의 for문이다.
        // orderItem은 가변인자로 받아주었다. 이 문법은 처음봤는데 파라미터를 ~ n개만큼 전달할 경우 사용한다고 한다.
        // 사실 직접 리스트나 배열을 선언해 orderItem들을 담아 전달하는 방식이 익숙했다.
        // 이러한 귀찮은 선언, 값 담기의 과정을 가변 인자가 대신해 준다고 한다.
        for (OrderItem orderItem : orderItems) {
            order.orderItemList.add(orderItem);
            orderItem.setOrder(order);
        }
        return order;
    }

    public void cancel() {
        // 주문 취소에 대한 로직이다.
        // 해당 주문 상태가 이미 완료 상태라면, 배송 취소 로직을 수행해서는 안되기때문에 예외처리를 해준 모습이다.
        if (delivery.getDeliveryStatus() == Delivery.DeliveryStatus.DONE) {
            throw new IllegalStateException("배송 완료했다 양아치야");
        }
        this.orderStatus = OrderStatus.CANCELED;
        // 리스트의 존재하는 객체들을 순차적으로 접근할 수 있는 이터레이터 형태의 for문이다.
        // 해당 주문의 존재하는 모든 주문된 상품들을 취소시킨다.
        for (OrderItem orderItem : orderItemList) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        // 리스트의 존재하는 객체들을 순차적으로 접근할 수 있는 이터레이터 형태의 for문이다.
        // 총 주문 금액을 계산해 반환한다.
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
