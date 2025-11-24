# SmartShop - API de Gestion Commerciale B2B

**SmartShop** est une application backend REST (API uniquement) développée pour **MicroTech Maroc**, distributeur B2B de matériel informatique à Casablanca.  
Elle permet de gérer un portefeuille de plus de **650 clients professionnels** avec :

- un système de fidélité à remises progressives (BASIC → SILVER → GOLD → PLATINUM),
- des commandes multi-produits,
- des paiements fractionnés multi-moyens (Espèces, Chèque, Virement),
- une traçabilité financière complète,
- une authentification basée sur sessions HTTP.

> **Aucune interface graphique n'est fournie.**  
Les tests se font via **Postman** ou **Swagger UI**.

---

## 🚀 Fonctionnalités principales

### 👥 Gestion des Clients
- CRUD complet
- Suivi automatique :
  - nombre total de commandes
  - montant cumulé
- Mise à jour automatique du niveau de fidélité selon le total cumulé :
  - BASIC → SILVER → GOLD → PLATINUM

### 📦 Gestion des Produits
- CRUD complet
- Soft delete pour préserver l’historique
- Contrôle du stock avant validation de commande

### 🛒 Gestion des Commandes
- Commandes multi-produits
- Vérification du stock avant ajout
- Application automatique :
  - remise fidélité
  - codes promo (ex. : `PROMO-XXXX`)
- Calculs :
  - Montant HT
  - Remises
  - TVA 20% (sur montant après remise)
  - Montant TTC avec arrondi à 2 décimales
- Statuts : `PENDING` → `CONFIRMED`, `CANCELED`, `REJECTED`
- Historique immuable

### 💳 Paiements Fractionnés
- Plusieurs paiements pour une seule commande
- Multiples moyens :
  - Espèces
  - Chèque
  - Virement bancaire
- Limite légale : **20 000 DH par paiement**

### 🔐 Authentification & Sécurité
- Sessions HTTP (login / logout)
- Rôles :
  - **ADMIN** → gestion complète
  - **CLIENT** → accès uniquement à ses données

---

## 🛠️ Technologies & Stack

- **Java 17**
- **Spring Boot 3.x**
- Spring Data JPA (Hibernate)
- Spring Web MVC
- PostgreSQL (ou MySQL)
- MapStruct
- Lombok
- Bean Validation (Jakarta Validation)
- JUnit 5 + Mockito
- Swagger (SpringDoc ou SpringFox)

---

## 📦 Dépendances Maven (noms uniquement)

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `postgresql` *(ou `mysql-connector-j`)*  
- `lombok`
- `mapstruct`
- `spring-boot-starter-test`
- `modelmapper` *(optionnel)*
- `jjwt-api` *(non utilisé — authentification par session)*
- `springdoc-openapi-starter-webmvc-ui` *(ou springfox-swagger2 + swagger-ui)*

---

## 📁 Structure du Projet

src/
├── main/
│ ├── java/
│ │ └── ma/microtech/smartshop/
│ │ ├── config/ # Config, MapStruct, Swagger
│ │ ├── controller/ # Endpoints REST
│ │ ├── dto/ # Data Transfer Objects
│ │ ├── entity/ # Entités JPA
│ │ │ ├── User
│ │ │ ├── Client
│ │ │ ├── Product
│ │ │ ├── Commande
│ │ │ ├── OrderItem
│ │ │ └── Paiement
│ │ ├── enum/ # UserRole, CustomerTier, etc.
│ │ ├── exception/ # Exceptions + ControllerAdvice
│ │ ├── mapper/ # MapStruct mappers
│ │ ├── repository/ # Interfaces JPA
│ │ ├── service/ # Logique métier
│ │ └── SmartShopApplication.java
│ └── resources/
│ ├── application.yml
│ └── data.sql # Seed optionnel
└── test/ # Tests unitaires 
