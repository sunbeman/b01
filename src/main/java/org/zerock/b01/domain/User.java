package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uno;

    @Column(length = 100, nullable = false, unique = true)
    private String id;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false, unique = true)
    private String nickname;
}