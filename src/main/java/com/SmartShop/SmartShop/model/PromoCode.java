package com.SmartShop.SmartShop.model;

import com.SmartShop.SmartShop.enums.PromoStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @ManyToOne
    private Client client;

    @Enumerated(EnumType.STRING)
    private PromoStatus status = PromoStatus.ACTIVE;

    private boolean used = false;
}
