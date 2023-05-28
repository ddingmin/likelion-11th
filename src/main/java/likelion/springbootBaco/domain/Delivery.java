package likelion.springbootBaco.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static likelion.springbootBaco.domain.Delivery.DeliveryStatus.ESTABLISHED;
import static lombok.AccessLevel.PROTECTED;

@Entity
// Entity임을 선언해주는 어노테이션이다. DB와 쉬운 데이터 송수신을 위해 테이블과 1대1 매핑되는 class를 만들고 이를 의미한다고 생각하면 된다.
@NoArgsConstructor(access = PROTECTED)
// NoArgsConstructor 어노테이션에 대한 설명은 Address에서 다뤘으며,
// access 값은 해당 객체에 접근 범위를 의미한다.
// 대부분 PROTECTED로 두어 사용하며, 이는 의미없는 객체 생성을 막기위한 수단이다.

@Getter
// Getter 필드의 접근자를 생성해주는 역할을 한다.
// 객체지향 프로그램으로써 보안적 측면으로 객체의 값들을 직접적으로 접근해서는 안된다.
// 따라서 필드의 값을 가져오기 위해서 get 메소드를 만들어 사용하곤 하는데, 이를 자동으로 만들어주는 역할을 한다.
public class Delivery {
    @Id @GeneratedValue
    // @Id 어노테이션은 현재 테이블의 유일 키인 PK를 의미한다.
    // @GeneratedValue 어노테이션은 PK를 데이터 베이스로부터 할당 받겠다는 의미이다.
    private Long id;

    @OneToOne(mappedBy = "delivery")
    // order와 1대1 관계를 의미한다.
    // mappedBy를 통해 관계의 주인을 지정해준다.
    private Order order;

    @Enumerated(STRING)
    // 속성을 Enum 타입으로 사용함을 의미한다.
    // 이때 Enum 타입은 기본적으로 Ordinary 형태로 사용되지만 직관적으로 보기 위해 STRING 타입으로 지정한 것을 알 수 있다.
    private DeliveryStatus deliveryStatus;

    private String city;
    private String state;
    private String street;
    private String zipcode;


    public static Delivery createDelivery(Order order, String city, String state, String street, String zipcode) {
        // 수업 시간에 설명해주신 정적 팩토리 메소드이다.
        // 특정 객체를 생성하기 위해서 보통은 해당 클래스의 생성자를 통해 생성하게 된다. (new 사용)
        // 1. 기본 생성자와 유사해보이지만 메소드 형태로 객체를 생성하기 때문에 이름을 지정해 줄 수 있다.
        // 기본 배달객체, 로켓배송, 직구배송 객체 등등, 여러 객체를 보통 Delivery 하나의 이름의 생성자를 통해 생성하지만
        // 이를 모두 createRocketDelivery, createOverSeaDelivery 등등 시각적으로 어떠한 객체를 생성하는지 한 눈에 알 수 있도록 생성할 수 있다.
        // 2. 메소드가 static 으로 선언되었기 때문에 호출할 때 마다 새로운 객체를 생성할 필요가 없다.
        // 3. 하위 객체로 생성할 수 있다. 상속을 사용하는 객체일 때 상속받은 객체를 생성할 수있다.
        // 단점으로는 메소드 접근 권한을 public이나 protected로 생성해야 하며, 해당 메소드를 찾기 어렵다고 한다.
        Delivery delivery = new Delivery();
        delivery.order = order;
        delivery.deliveryStatus = ESTABLISHED;
        delivery.city = city;
        delivery.state = state;
        delivery.street = street;
        delivery.zipcode = zipcode;
        return delivery;
        // 실제로 메소드 내에서 새 객체를 만들어 값들을 대입한 뒤 반환하는 형태이다.
    }


    public enum DeliveryStatus {
        // 위에서 사용한 Enum 타입이다.
        // 보통 지정된 상태를 직관적으로 나타내어 보기 편하도록 사용한다.
        ESTABLISHED, PROGRESS, DONE
    }
}
