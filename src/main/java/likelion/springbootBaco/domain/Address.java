package likelion.springbootBaco.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
// Entity가 여러 필드를 하나의 객체로 묶어 관리할 때 사용한다.
// 즉 Address 클래스는 city, state, street, zipcode의 필드로 이루어져 있으며,
// Entity로 따로 구성하지 않고 종속된 상태를 의미한다.

@Data
// Lombok 라이브러리 어노테이션으로 Getter, Setter 등등 따로 구현하지 않아도 사용할 수 있도록 한다.
// (@Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode)

@AllArgsConstructor
// 파라미터가 모두 존재하는 생성자를 만들어준다.

@NoArgsConstructor
// 파라미터가 없는 생성자를 만들어준다.

public class Address {
    private String city;
    private String state;
    private String street;
    private String zipcode;
}
