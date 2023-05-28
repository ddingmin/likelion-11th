package likelion.springbootBaco.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

    @Embedded
    // Embedded 어노테이션은 address가 다중 필드값으로 존재하며 객체화되어 Member의 포함되어 있음을 의미한다.
    // 실제로 address 클래스에는 @Embeddable 어노테이션이 존재한다.
    private Address address;

    public static Member createMember(String name, Address address) {
        // 앞서 설명했던 정적 팩토리 메소드이다.
        Member member = new Member();
        member.name = name;
        member.address = address;
        return member;
    }
}
