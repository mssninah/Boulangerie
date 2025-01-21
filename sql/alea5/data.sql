-- Insertion d'une commission de 5% pour chaque utilisateur depuis le 1er janvier 2024 jusqu'à NULL
INSERT INTO commission (valuee, date_debut, date_fin, id_vendeur)
SELECT
    5.00 AS valuee,  -- Commission de 5%
    '2024-01-01'::timestamp AS date_debut,  -- Date de début fixée au 1er janvier 2024
    '2030-01-01' AS date_fin,  -- Date de fin non définie (NULL)
    id_user AS id_vendeur
FROM
    boulangerie_user;
