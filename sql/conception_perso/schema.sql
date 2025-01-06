CREATE DATABASE boulangerie;

\c boulangerie;


--gestion employe et connexion
    CREATE TABLE boulangerie_user (
        id_user SERIAL PRIMARY KEY,
        firstname VARCHAR(100) NOT NULL,
        lastname VARCHAR(100) NOT NULL,
        email VARCHAR(100) NOT NULL,
        user_password VARCHAR(100) NOT NULL
    );


    CREATE TABLE type_post(
        id_type_post
        nom 
    )

    create table role_user(
        id_role_user
        id_type_post
        id_user 
        date_debut
        date_fin
    )

--gestion produit , un produit == un recipe 
    CREATE TABLE category (
        id_category SERIAL PRIMARY KEY,
        category_name VARCHAR(255) NOT NULL
    );

    CREATE TABLE recipe (
        id_recipe SERIAL PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        recipe_description TEXT,
        id_category INT NOT NULL,
        cook_time TIME NOT NULL,
        created_by VARCHAR(255) NOT NULL,
        price_unit 
        created_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (id_category) REFERENCES category(id_category)
    );

   create table stock_repice(
        id_stock_recipe
        id_recipe
        qtt,
        date_last_update
        id_user
   )

   create table mvt_stock_recipe(
        id_mvt_stock_recipe
        id_stock_recipe
        statut ('in', 'out')
        qtt
        mvt_description
        justificatif(ex : id facture, num production)
        date_mvt

   )

   --gestion vente produit --qtt in recipe 
        commande
        CREATE TABLE commande (
            id SERIAL PRIMARY KEY,
            date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            id_client INT REFERENCES client(id),
            
        );

        details_commande
        CREATE TABLE details_commande (
            id SERIAL PRIMARY KEY,
            id_commande INT REFERENCES commande(id),
            id_produit INT REFERENCES produit(id),
            quantite INT NOT NULL,
            prix_unitaire DECIMAL(10, 2) NOT NULL
        ); 

        total_commande (vue calculée)
        CREATE VIEW vue_total_commande_all AS
        SELECT
            c.*,
            SUM(dc.quantite * dc.prix_unitaire) AS total_commande
        FROM
            commande c
            JOIN details_commande dc ON c.id = dc.id_commande
        GROUP BY c.id;


        create table statut_commande(
            id_statut_commande
            id_commande
        )


CREATE TABLE unitx(
    id_unit SERIAL PRIMARY KEY, 
    nom_unit VARCHAR(255) NOT NULL
);

CREATE TABLE ingredient (
    id_ingredient SERIAL PRIMARY KEY,
    ingredient_name VARCHAR(255) NOT NULL,
    unit INT,
    price INT NOT NULL DEFAULT 0 
);

CREATE TABLE recipe_ingredient (
    id_recipe INT,
    id_ingredient INT,
    quantity DECIMAL(10,2), -- To store the amount needed for each recipe
    PRIMARY KEY (id_recipe, id_ingredient),
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe),
    FOREIGN KEY (id_ingredient) REFERENCES ingredient(id_ingredient)
);

CREATE TABLE step (
    id_step SERIAL PRIMARY KEY,
    id_recipe INT NOT NULL,
    step_number INT NOT NULL,
    instruction TEXT NOT NULL,
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe)
);

CREATE TABLE review (
    id_review SERIAL PRIMARY KEY,
    id_user INT NOT NULL,
    id_recipe INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    review_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES boulangerie_user(id_user),
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe)
);
