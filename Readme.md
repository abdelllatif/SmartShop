
# SmartShop - API de Gestion Commerciale B2B

**SmartShop** est une API REST backend (sans interface graphique) développée pour **MicroTech Maroc**, distributeur B2B de matériel informatique basé à Casablanca.

Elle gère un portefeuille de **plus de 650 clients professionnels** avec un système complet de fidélité, commandes multi-produits, paiements fractionnés et traçabilité financière totale.

> **Aucune interface graphique fournie**  
> Tests via **Postman** ou **Swagger UI**

---

## 🚀 Fonctionnalités principales

### 👥 Gestion des Clients
- CRUD complet
- Suivi automatique du nombre de commandes et du montant cumulé
- Niveau de fidélité mis à jour automatiquement :
  - `BASIC` → `SILVER` → `GOLD` → `PLATINUM`

### 📦 Gestion des Produits
- CRUD complet
- Soft delete (suppression logique) pour préserver l'historique
- Contrôle strict du stock avant validation de commande

### 🛒 Gestion des Commandes
- Commandes multi-produits
- Vérification du stock en temps réel
- Application automatique des remises :
  - Remise selon niveau de fidélité du client
  - Codes promo (ex: `PROMO-2025`)
- Calculs automatiques :
  - Montant HT
  - Remise appliquée
  - TVA 20% (calculée après remise)
  - Montant TTC arrondi à 2 décimales
- Cycle de vie : `PENDING` → `CONFIRMED` | `CANCELED` | `REJECTED`
- Historique immuable des commandes

### 💳 Paiements Fractionnés
- Plusieurs paiements possibles par commande
- Moyens de paiement supportés :
  - Espèces
  - Chèque
  - Virement bancaire
- Respect de la limite légale marocaine : **20 000 DH maximum par paiement**

### 🔐 Authentification & Sécurité
- Authentification par **sessions HTTP** (login/logout)
- Deux rôles :
  | Rôle    | Droits                                      |
  |---------|---------------------------------------------|
  | ADMIN   | Accès complet à toute l'application         |
  | CLIENT  | Accès uniquement à ses propres données     |

---

## 🛠️ Stack Technique

| Technologie                      | Version / Remarque                  |
|----------------------------------|-------------------------------------|
| Java                             | 17                                  |
| Spring Boot                      | 3.x                                 |
| Spring Data JPA (Hibernate)      |                                     |
| Spring Web MVC                   |                                     |
| Base de données                  | PostgreSQL (ou MySQL)               |
| MapStruct                        | Mapping DTO ↔ Entity                |
| Lombok                           | Réduction du code boilerplate       |
| Jakarta Bean Validation          | Validation des données              |
| JUnit 5 + Mockito                | Tests unitaires & d'intégration     |
| SpringDoc OpenAPI                | Documentation Swagger UI            |

---

## 📦 Dépendances Maven

| Dépendance                      | Description                                   | Obligatoire |
|--------------------------------|-----------------------------------------------|-------------|
| spring-boot-starter-web        | API REST, contrôleurs, endpoints HTTP         | ✅ Oui      |
| spring-boot-starter-data-jpa   | ORM Hibernate + Repositories JPA              | ✅ Oui      |
| spring-boot-starter-validation | Validation des DTO avec Jakarta Validation    | ✅ Oui      |
| postgresql / mysql-connector-j | Driver Base de données                        | ✅ Oui      |
| lombok                         | Réduction du boilerplate (Getters/Setters…)   | ✅ Oui      |
| mapstruct                      | Mapping DTO ↔ Entities                        | ✅ Oui      |
| spring-boot-starter-test       | JUnit 5 + Mockito pour les tests              | ✅ Oui      |
| springfox / springdoc-openapi  | Documentation Swagger                         | ✅ Oui      |



> Note : `jjwt` n’est **pas utilisé** (authentification par session uniquement)

---

## 📁 Structure du projet

```
src/
├── main/
│   ├── java/ma/microtech/smartshop/
│   │   ├── config/          # Config, MapStruct, Swagger
│   │   ├── controller/      # Endpoints REST
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # Entités JPA
│   │   │   ├── User.java
│   │   │   ├── Client.java
│   │   │   ├── Product.java
│   │   │   ├── Commande.java
│   │   │   ├── OrderItem.java
│   │   │   └── Paiement.java
│   │   ├── enum/            # UserRole, CustomerTier, PaymentMethod...
│   │   ├── exception/       # Exceptions globales + ControllerAdvice
│   │   ├── mapper/          # MapStruct mappers
│   │   ├── repository/      # Interfaces JPA Repository
│   │   ├── service/         # Logique métier
│   │   └── SmartShopApplication.java
│   └── resources/
│       ├── application.yml
│       └── data.sql         # Données de seed (optionnel)
└── test/                    # Tests unitaires et d'intégration
```

---

## 🚀 Lancement rapide


- API accessible sur : `http://localhost:8080`
- Swagger UI : `http://localhost:8080/swagger-ui.html`
- API docs (OpenAPI) : `http://localhost:8080/v3/api-docs`

---

