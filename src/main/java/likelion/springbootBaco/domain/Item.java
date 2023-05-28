package likelion.springbootBaco.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "item")
    // 일대다 관계를 의미한다. 현재 클래스 기준 좌측, 현재 Item 클래스가 일의 관계를 의미한다.
    // 판매하는 아이템 하나를 생각하면, 주문된 아이템들은 각 주문마다 존재한다고 생각하면 쉽다.
    // 따라서 관계의 주인은 항상 Many가 가진다.
    private List<OrderItem> orderItem = new ArrayList<>();

    private String brand;
    private String name;
    private Integer price;
    private Integer stock;

    /**
     * 비즈니스 로직
     */

    @Comment("재고 추가")
    public void addStock(int quantity) {
        this.stock += quantity;
    }
    // 일정 수량 quantity을 파라미터로 입력받아 더해주는 로직이다.

    @Comment("재고 감소")
    public void removeStock(int stockQuantity) {
        // 일정 수량을 입력받아 감소해준다. 이때 현재 재고보다 더 많은 재고를 뺄 수 없으니, 예외처리를 해주었다.
        int restStock = this.stock - stockQuantity;
        if (restStock < 0) {
            throw new IllegalStateException("need more stock");
        }
        this.stock = restStock;
    }
}
