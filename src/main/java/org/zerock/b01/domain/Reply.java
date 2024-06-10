package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "board") //보드를 추가하고 출력시 에러(reply 테이블만 검색해서)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    //보드 테이블이 구지 필요가 없어 LAZY 사용
    @ManyToOne(fetch = FetchType.LAZY)//보드까지 출력하고자 하면 fetch = FetchType.EAGER
    private Board board;//1대 1

    private String replyText;

    private String replyer;



    public void changeText(String text) {

        this.replyText = text;

    }


}
