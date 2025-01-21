INSERT INTO commission (valuee, date_debut, date_fin, id_vendeur)
SELECT 
    5.00 AS valuee,  -- Pourcentage de la commission (5%)
    '2025-01-01'::TIMESTAMP AS date_debut,  -- Date de début de validité
    '2025-01-31'::TIMESTAMP AS date_fin,    -- Date de fin de validité
    v.id_user AS id_vendeur
FROM 
    boulangerie_user v
JOIN 
    vente ve ON ve.id_user = v.id_user
WHERE 
    ve.vente_date BETWEEN '2025-01-01' AND '2025-01-31';
