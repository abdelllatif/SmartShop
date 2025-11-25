package com.SmartShop.SmartShop.model;


import com.SmartShop.SmartShop.enums.CustomerTier;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CustomerTier niveauFidelite=CustomerTier.BASIC;
    @OneToOne(mappedBy = "client")
    @JsonBackReference
    private User user;
}
