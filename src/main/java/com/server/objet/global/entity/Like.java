package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "like_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "p_idx")
    private Product product;

    @Column
    private Long count;

    @Column(name = "create_at")
    private LocalDateTime createAt;

}
