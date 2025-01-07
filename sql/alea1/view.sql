SELECT 
    r.*
FROM 
    recipe r
JOIN 
    recipe_ingredient ri ON r.id_recipe = ri.id_recipe
WHERE 
    ri.id_ingredient = 1; -- Remplacez `1` par l'ID de l'ingrédient recherché


