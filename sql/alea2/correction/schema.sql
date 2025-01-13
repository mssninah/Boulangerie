
CREATE TABLE price_history (
    id_price SERIAL PRIMARY KEY,               -- Identifiant unique pour chaque variation de prix
    id_recipe INT NOT NULL,                    -- Identifiant de la recette
    price DECIMAL(10, 2) NOT NULL,             -- Prix de la recette
    starts_date DATE NOT NULL DEFAULT CURRENT_DATE, -- Date de début de validité
    end_date DATE                              -- Date de fin de validité (NULL si encore actif)
);


-- Insérer les prix initiaux pour chaque recette
INSERT INTO price_history (id_recipe, price, starts_date, end_date)
VALUES
    (1, 3.50, '2025-01-05', NULL), -- Pain au Beurre
    (2, 4.00, '2025-01-05', NULL), -- Chocolate Croissant
    (3, 5.50, '2025-01-05', NULL), -- Lemon Cake
    (4, 3.75, '2025-01-06', NULL), -- Croissant au Beurre
    (5, 2.25, '2025-01-06', NULL); -- Madeleine


CREATE OR REPLACE VIEW get_actual_price AS
SELECT 
    r.id_recipe,
    r.title,
    r.recipe_description,
    r.id_category,
    r.cook_time,
    r.created_by,
    r.created_date,
    p.price AS actual_price
FROM 
    recipe r
JOIN 
    price_history p
ON 
    r.id_recipe = p.id_recipe
WHERE 
    p.end_date IS NULL;


SELECT * FROM get_actual_price;
