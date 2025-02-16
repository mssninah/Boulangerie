-- Insert into the 'category' table
INSERT INTO category (category_name) VALUES
    ('Breads'), 
    ('Cakes'), 
    ('Pastries'), 
    ('Cookies'), 
    ('Pies');

-- Insert into the 'boulangerie_user' table
INSERT INTO boulangerie_user (firstname, lastname, email, user_password) VALUES  
    ('Paul', 'Dupont', 'paul.dupont@example.com', 'password123'),
    ('Marie', 'Lemoine', 'marie.lemoine@example.com', 'password456'),
    ('Andry', 'Rakotomalala', 'andry.rakotomalala@example.com', 'password789'),
    ('Thomis', 'Rasolomandimby', 'thomis.rasolomandimby@gmail.com', 'thomis');

-- Insert into the 'ingredient' table
INSERT INTO ingredient (ingredient_name, unit, price) VALUES  
    ('Flour', 'grams', 1000),
    ('Sugar', 'grams', 500),
    ('Butter', 'grams', 1500),
    ('Eggs', 'pieces', 300),
    ('Yeast', 'grams', 50),
    ('Vanilla Extract', 'milliliters', 300),
    ('Salt', 'grams', 50);

-- Insert into the 'recipe' table
INSERT INTO recipe (title, recipe_description, id_category, cook_time, created_by) VALUES  
    ('Baguette', 'A traditional French baguette with a crispy crust and soft interior.',  1, '02:00:00', 'Paul Dupont'),
    ('Chocolate Croissant', 'A buttery and flaky croissant filled with rich chocolate.',  3, '00:45:00', 'Marie Lemoine'),
    ('Lemon Cake', 'A light and fluffy cake with a refreshing lemon flavor.',  2, '01:30:00', 'Andry Rakotomalala');

-- Insert into the 'recipe_ingredient' table
INSERT INTO recipe_ingredient (id_recipe, id_ingredient, quantity) VALUES  
    (1,  1,  500),
    (1,  2,  10),
    (1,  5,  5),
    (2,  1,  200),
    (2,  2,  100),
    (2,  3,  50),
    (3,  1,  300),
    (3,  2,  150),
    (3,  4,  3),
    (3,  7,  2);

-- Insert into the 'step' table
INSERT INTO step (id_recipe, step_number, instruction) VALUES  
    (1,  1, 'Mix the flour, yeast, and salt in a large bowl.'),
    (1,  2, 'Gradually add water and knead the dough until smooth.'),
    (1,  3, 'Let the dough rise for 1 hour, then shape into baguettes.'),
    (1,  4, 'Bake in a preheated oven at 220°C for 25 minutes.'),
    (2,  1, 'Roll out the croissant dough and cut into triangles.'),
    (2,  2, 'Place a piece of chocolate on each triangle and roll it up.'),
    (2,  3, 'Bake in the oven at 180°C for 15 minutes.'),
    (3,  1, 'Preheat the oven to 180°C.'),
    (3,  2, 'Mix the flour, sugar, eggs, and lemon juice into a batter.'),
    (3,  3, 'Pour the batter into a greased pan and bake for 45 minutes.');

-- Insert into the 'review' table
INSERT INTO review (id_user, id_recipe, rating, comment, review_date) VALUES
    (1, 1, 5, 'A perfect baguette, just like in France!', '2024-01-15'),
    (2, 2, 4, 'The croissant was delicious, but could use more chocolate.', '2024-01-20'),
    (3, 3, 5, 'The lemon cake was amazing! Perfect balance of sweetness and tartness.', '2024-01-25');

INSERT INTO recipe (id_recipe, title, recipe_description, id_category, cook_time, created_by, created_date) VALUES
(4, 'Croissant au Beurre', 'A classic French croissant with a buttery, flaky texture.', 3, '01:00:00', 'Jean Dupont', '2025-01-06'),
(5, 'Madeleine', 'A light, small cake with a distinctive shell-like shape and a hint of lemon.', 2, '00:30:00', 'Sophie Renard', '2025-01-06');

INSERT INTO ingredient (id_ingredient, ingredient_name, unit, price) VALUES
(8, 'Chocolate', 'grams', 1200),  -- New ingredient for Chocolate Croissant
(9, 'Milk', 'milliliters', 800);  -- New ingredient for Croissant au Beurre

INSERT INTO recipe_ingredient (id_recipe, id_ingredient, quantity) VALUES
(4, 1, 400.00),  -- Flour for the Butter Croissant
(4, 2, 50.00),   -- Sugar for the Butter Croissant
(4, 3, 150.00),  -- Butter for the Butter Croissant
(5, 1, 300.00),  -- Flour for the Madeleine
(5, 2, 100.00),  -- Sugar for the Madeleine
(5, 3, 100.00),  -- Butter for the Madeleine
(5, 4, 2.00),    -- Eggs for the Madeleine
(5, 7, 5.00);    -- Salt for the Madeleine

INSERT INTO recipe_ingredientINSERT INTO recipe_ingredient (id_recipe, id_ingredient, quantity) VALUES
(1, 3, 1000.00); (id_recipe, id_ingredient, quantity) VALUES
(2, 8, 1000.00);

INSERT INTO recipe_ingredient (id_recipe, id_ingredient, quantity) VALUES
(1, 3, 1000.00);

-- Insérer les prix initiaux pour chaque recette
INSERT INTO price_history (id_recipe, price, starts_date, end_date)
VALUES
    (1, 3.50, '2025-01-05', NULL), -- Pain au Beurre
    (2, 4.00, '2025-01-05', NULL), -- Chocolate Croissant
    (3, 5.50, '2025-01-05', NULL), -- Lemon Cake
    (4, 3.75, '2025-01-06', NULL), -- Croissant au Beurre
    (5, 2.25, '2025-01-06', NULL); -- Madeleine

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

INSERT INTO vente (id_user, vente_date, total_amount)
VALUES 
    (1, '2025-01-07 10:30:00', 45.00), -- Vente 1
    (2, '2025-01-07 14:00:00', 60.50), -- Vente 2
    (NULL, '2025-01-08 09:15:00', 25.00); -- Vente 3 (Utilisateur non connecté)


-- Insertion d'une commission de 5% pour chaque utilisateur depuis le 1er janvier 2024 jusqu'à NULL
INSERT INTO commission (valuee, date_debut, date_fin, id_vendeur)
SELECT
    5.00 AS valuee,  -- Commission de 5%
    '2024-01-01'::timestamp AS date_debut,  -- Date de début fixée au 1er janvier 2024
    '2030-01-01' AS date_fin,  -- Date de fin non définie (NULL)
    id_user AS id_vendeur
FROM
    boulangerie_user;


INSERT INTO status (nom) VALUES 
('admin'), 
('client'), 
('vendeur');

INSERT INTO sexe (nom) VALUES
('homme'),
('femme');