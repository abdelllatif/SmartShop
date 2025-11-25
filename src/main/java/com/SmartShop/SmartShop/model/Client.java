package com.SmartShop.SmartShop.model;


import com.SmartShop.SmartShop.enums.CustomerTier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Enumerated(EnumType.STRING)
    private CustomerTier niveauFidelite;
    @OneToOne(mappedBy = "client")
    private User user;
}
