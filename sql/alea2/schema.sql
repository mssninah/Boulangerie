CREATE TABLE vente (
    id_vente SERIAL PRIMARY KEY,        -- Identifiant unique de la vente
    id_user INT,                        -- Identifiant de l'utilisateur (client) si connecté
    vente_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Date et heure de la vente
    total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0, -- Montant total de la vente
    FOREIGN KEY (id_user) REFERENCES boulangerie_user(id_user) -- Lien avec l'utilisateur (facultatif)
);


CREATE TABLE vente_details (
    id_vente INT NOT NULL,              -- Identifiant de la vente (clé étrangère)
    id_recipe INT NOT NULL,             -- Identifiant de la recette vendue (clé étrangère)
    quantity INT NOT NULL CHECK (quantity > 0), -- Quantité vendue
    unit_price DECIMAL(10, 2) NOT NULL CHECK (unit_price >= 0), -- Prix unitaire
    PRIMARY KEY (id_vente, id_recipe),  -- Clé primaire composite
    FOREIGN KEY (id_vente) REFERENCES vente(id_vente) ON DELETE CASCADE, -- Suppression en cascade
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe) ON DELETE CASCADE -- Suppression en cascade
);



INSERT INTO vente (id_user, vente_date, total_amount)
VALUES 
    (1, '2025-01-07 10:30:00', 45.00), -- Vente 1
    (2, '2025-01-07 14:00:00', 60.50), -- Vente 2
    (NULL, '2025-01-08 09:15:00', 25.00); -- Vente 3 (Utilisateur non connecté)


-- Détails pour la vente 1
INSERT INTO vente_details (id_vente, id_recipe, quantity, unit_price)
VALUES 
    (1, 2, 2, 12.50), -- 2 Chocolate Croissants à 12.50 chacun
    (1, 3, 1, 20.00); -- 1 Lemon Cake à 20.00

-- Détails pour la vente 2
INSERT INTO vente_details (id_vente, id_recipe, quantity, unit_price)
VALUES 
    (2, 4, 3, 10.50), -- 3 Croissants au Beurre à 10.50 chacun
    (2, 5, 2, 12.50); -- 2 Madeleines à 12.50 chacune

-- Détails pour la vente 3
INSERT INTO vente_details (id_vente, id_recipe, quantity, unit_price)
VALUES 
    (3, 1, 1, 25.00); -- 1 Pain au Beurre à 25.00



CREATE VIEW liste_ventes AS
SELECT 
    v.id_vente,
    v.vente_date,
    u.firstname || ' ' || u.lastname AS user_name,
    vd.id_recipe,
    r.title AS recipe_name,
    vd.quantity AS qtt,
    vd.unit_price AS pu,
    (vd.quantity * vd.unit_price) AS sub_total,
    v.total_amount AS total_vente
FROM 
    vente v
LEFT JOIN 
    boulangerie_user u ON v.id_user = u.id_user
LEFT JOIN 
    vente_details vd ON v.id_vente = vd.id_vente
LEFT JOIN 
    recipe r ON vd.id_recipe = r.id_recipe;



ALTER TABLE ingredient ADD COLUMN is_nature BOOLEAN NOT NULL DEFAULT TRUE;

UPDATE ingredient
SET is_nature = FALSE
WHERE ingredient_name IN ('Chocolate', 'Vanilla Extract');


CREATE OR REPLACE VIEW recipe_nature AS
SELECT r.id_recipe,
       r.title,
       CASE 
           WHEN EXISTS (
               SELECT 1
               FROM recipe_ingredient ri
               JOIN ingredient i ON ri.id_ingredient = i.id_ingredient
               WHERE ri.id_recipe = r.id_recipe
                 AND i.is_nature = 'f'
           ) THEN FALSE  -- If any ingredient is not nature, mark the recipe as non-nature
           ELSE TRUE  -- If all ingredients are nature, mark the recipe as nature
       END AS is_nature
FROM recipe r;




SELECT * FROM recipe_nature;


CREATE VIEW vente_filtre AS
SELECT 
    v.id_vente,
    v.vente_date,
    u.firstname || ' ' || u.lastname AS user_name,
    r.title AS recipe,
    c.category_name,
    vn.is_nature,
    vd.quantity,
    vd.unit_price,
    (vd.quantity * vd.unit_price) AS sub_total,
    v.total_amount
FROM 
    vente v
LEFT JOIN 
    boulangerie_user u ON v.id_user = u.id_user
JOIN 
    vente_details vd ON v.id_vente = vd.id_vente
JOIN 
    recipe r ON vd.id_recipe = r.id_recipe
JOIN 
    category c ON r.id_category = c.id_category
JOIN 
    recipe_nature vn ON r.id_recipe = vn.id_recipe;


SELECT * 
FROM vente_filtre
WHERE category_name = 'Pastries' AND is_nature = TRUE;
