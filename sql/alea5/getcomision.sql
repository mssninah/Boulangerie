

--get info user connected



---chaque user obtien commisssion 5% sur une peridode donnee


CREATE TABLE commission (
    id_commission SERIAL PRIMARY KEY,    -- Identifiant unique de la commission
    valuee DECIMAL(5, 2) NOT NULL CHECK (valuee >= 0 AND valuee <= 100), -- Pourcentage de la commission
    date_debut TIMESTAMP NOT NULL,       -- Date de début de validité de la commission
    date_fin TIMESTAMP NOT NULL,         -- Date de fin de validité de la commission
    id_vendeur INT NOT NULL,             -- Identifiant du vendeur (lien avec le vendeur)
    FOREIGN KEY (id_vendeur) REFERENCES boulangerie_user(id_user) -- Clé étrangère vers le vendeur
);


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



