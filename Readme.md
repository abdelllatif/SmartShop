```markdown
# SmartShop - API de Gestion Commerciale B2B

**SmartShop** est une application backend REST pure (API uniquement) développée pour **MicroTech Maroc**, distributeur B2B de matériel informatique à Casablanca.  
Elle permet de gérer un portefeuille de plus de 650 clients professionnels avec un système de fidélité à remises progressives (BASIC → SILVER → GOLD → PLATINUM), des commandes multi-produits, des paiements fractionnés multi-moyens (Espèces, Chèque, Virement) et une traçabilité financière complète.

Aucune interface graphique n’est fournie : tous les tests et démonstrations se font via **Postman** ou **Swagger**.

## Fonctionnalités principales
- Gestion complète des clients (CRUD) avec suivi automatique du nombre de commandes et du montant cumulé
- Système de fidélité automatique avec calcul du niveau et application de remises conditionnelles
- Gestion des produits (CRUD + soft delete)
- Création et validation de commandes multi-produits
  - Vérification du stock
  - Application automatique des remises (fidélité + code promo PROMO-XXXX)
  - Calcul HT → remise → TVA 20% (sur montant après remise) → TTC
  - Arrondi systématique à 2 décimales
- Paiements fractionnés multi-moyens (limite légale 20 000 DH par paiement)
- Gestion des statuts de commande (PENDING → CONFIRMED / CANCELED / REJECTED)
- Historique immuable et traçabilité totale
- Authentification par sessions HTTP (login/logout)
- Système de rôles : **ADMIN** (gestion complète) et **CLIENT** (consultation uniquement de ses données)

## Technologies & Stack
- Java 17
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- Spring Web MVC
- PostgreSQL (ou MySQL)
- Lombok
- MapStruct
- Validation (Bean Validation)
- JUnit 5 + Mockito

## Dépendances Maven (noms uniquement)
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- postgresql (ou mysql-connector-j)
- lombok
- mapstruct
- spring-boot-starter-test
- modelmapper (optionnel)
- jjwt-api (non utilisé ici – uniquement session)
- springfox-swagger2 & springfox-swagger-ui (ou springdoc-openapi-v3 selon choix)

## Structure du projet
```
src/
├── main/
│   ├── java/ma/microtech/smartshop/
│   │   ├── config/          → Config, MapStruct, Swagger
│   │   ├── controller/      → Endpoints REST
│   │   ├── dto/             → Data Transfer Objects
│   │   ├── entity/          → Entités JPA (User, Client, Product, Commande, OrderItem, Paiement)
│   │   ├── enum/            → Tous les enums (UserRole, CustomerTier, OrderStatus, PaymentStatus, PaymentMethod)
│   │   ├── exception/       → Exceptions personnalisées + ControllerAdvice
│   │   ├── mapper/          → MapStruct mappers
│   │   ├── repository/      → Interfaces JPA
│   │   ├── service/         → Logique métier
│   │   └── SmartShopApplication.java
│   └── resources/
│       ├── application.yml
│       └── data.sql (seed optionnel)
└── test/
```

## Lancement rapide
1. Cloner le repo
2. Configurer `application.yml` (URL base de données, port 8080 par défaut)
3. Lancer avec `./mvnw spring-boot:run` ou via votre IDE
4. API disponible sur `http://localhost:8080`
5. Documentation Swagger (si activée) : `http://localhost:8080/swagger-ui.html`

## Collection Postman
Une collection complète est fournie dans le dossier `/postman/SmartShop Collection.postman_collection.json`  
(Contient tous les endpoints avec exemples : login, CRUD, création commande + paiements fractionnés, validation, etc.)

## Auteur
**Abdellatif Hissoune**  
Projet réalisé dans le cadre de la formation développeur full-stack – 2025

**Prêt pour la soutenance du 28/11/2025**  
Tout est fonctionnel, testé et respecte strictement le cahier des charges.
```