CREATE DATABASE boulangerie;

\c boulangerie;

CREATE TABLE sexe (
    id SERIAL PRIMARY KEY NOT NULL,
    nom VARCHAR(50) NOT NULL
);

CREATE TABLE status (
    id SERIAL PRIMARY KEY NOT NULL,
    nom VARCHAR(50) NOT NULL
);

CREATE TABLE boulangerie_user (
    id_user SERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    user_password VARCHAR(100) NOT NULL
);

ALTER TABLE boulangerie_user 
ADD COLUMN id_sexe INTEGER, 
ADD COLUMN id_status INTEGER,
ADD CONSTRAINT fk_sexe FOREIGN KEY (id_sexe) REFERENCES sexe(id),
ADD CONSTRAINT fk_status FOREIGN KEY (id_status) REFERENCES status(id);

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
    created_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_category) REFERENCES category(id_category)
);


CREATE TABLE ingredient (
    id_ingredient SERIAL PRIMARY KEY,
    ingredient_name VARCHAR(255) NOT NULL,
    unit VARCHAR(50) NOT NULL, -- For example, grams, milliliters, teaspoons, etc.
    price INT NOT NULL DEFAULT 0 
);

ALTER TABLE ingredient ADD COLUMN is_nature BOOLEAN NOT NULL DEFAULT TRUE;

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

CREATE TABLE vente (
    id_vente SERIAL PRIMARY KEY,        -- Identifiant unique de la vente
    id_user INT,                        -- Identifiant de l'utilisateur (client) si connecté
    vente_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Date et heure de la vente
    total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0, -- Montant total de la vente
    FOREIGN KEY (id_user) REFERENCES boulangerie_user(id_user) -- Lien avec l'utilisateur (facultatif)
);

ALTER TABLE vente
ADD COLUMN id_client INTEGER,
ADD COLUMN id_vendeur INTEGER;

ALTER TABLE vente
ADD CONSTRAINT vente_id_client_fkey FOREIGN KEY (id_client) REFERENCES boulangerie_user(id_user),
ADD CONSTRAINT vente_id_vendeur_fkey FOREIGN KEY (id_vendeur) REFERENCES boulangerie_user(id_user);

CREATE TABLE vente_details (
    id_vente INT NOT NULL,              -- Identifiant de la vente (clé étrangère)
    id_recipe INT NOT NULL,             -- Identifiant de la recette vendue (clé étrangère)
    quantity INT NOT NULL CHECK (quantity > 0), -- Quantité vendue
    unit_price DECIMAL(10, 2) NOT NULL CHECK (unit_price >= 0), -- Prix unitaire
    PRIMARY KEY (id_vente, id_recipe),  -- Clé primaire composite
    FOREIGN KEY (id_vente) REFERENCES vente(id_vente) ON DELETE CASCADE, -- Suppression en cascade
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe) ON DELETE CASCADE -- Suppression en cascade
);

CREATE TABLE price_history (
    id_price SERIAL PRIMARY KEY,               -- Identifiant unique pour chaque variation de prix
    id_recipe INT NOT NULL,                    -- Identifiant de la recette
    price DECIMAL(10, 2) NOT NULL,             -- Prix de la recette
    starts_date DATE NOT NULL DEFAULT CURRENT_DATE, -- Date de début de validité
    end_date DATE                              -- Date de fin de validité (NULL si encore actif)
);


CREATE TABLE commission (
    id_commission SERIAL PRIMARY KEY,    -- Identifiant unique de la commission
    valuee DECIMAL(5, 2) NOT NULL CHECK (valuee >= 0 AND valuee <= 100), -- Pourcentage de la commission
    date_debut TIMESTAMP NOT NULL,       -- Date de début de validité de la commission
    date_fin TIMESTAMP NOT NULL,         -- Date de fin de validité de la commission
    id_vendeur INT NOT NULL,             -- Identifiant du vendeur (lien avec le vendeur)
    FOREIGN KEY (id_vendeur) REFERENCES boulangerie_user(id_user) -- Clé étrangère vers le vendeur
);

CREATE TABLE history_price (
    id SERIAL PRIMARY KEY,
    id_recipe INT NOT NULL,
    new_price NUMERIC(10,2) NOT NULL,
    change_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe)
);