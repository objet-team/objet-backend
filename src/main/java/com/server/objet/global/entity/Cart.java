package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn
    private Goods goods;

    @Column(name = "count")
    private Integer cnt;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    public void updateCount(Integer newCount) {
        this.cnt = newCount;
    }

}
