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



CREATE VIEW ingredient_parfum AS 
SELECT * 
FROM ingredient 
WHERE is_nature = FALSE;

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


CREATE OR REPLACE VIEW voir_comissions AS
SELECT
    c.id_commission,
    c.id_vendeur,
    v.id_vente,
    v.total_amount,
    c.valuee AS pourcentage_commission,
    v.vente_date,
    (v.total_amount * c.valuee / 100) AS montant_commission
FROM
    vente v
INNER JOIN
    commission c ON v.id_user = c.id_vendeur
WHERE
    v.vente_date BETWEEN c.date_debut AND c.date_fin;

CREATE OR REPLACE VIEW comissions_sexe AS
SELECT 
    s.nom AS sexe,
    SUM(vc.montant_commission) AS total_commission
FROM 
    voir_comissions vc
INNER JOIN 
    boulangerie_user u ON vc.id_vendeur = u.id_user
INNER JOIN 
    sexe s ON u.id_sexe = s.id
GROUP BY 
    s.nom;


