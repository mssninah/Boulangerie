CREATE TABLE sexe (
    id SERIAL PRIMARY KEY NOT,
    nom VARCHAR(50) NOT NULL
);

CREATE TABLE status (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

ALTER TABLE sexe
ALTER COLUMN id SET NOT NULL;

ALTER TABLE status
ALTER COLUMN id SET NOT NULL;

-------------
--data

INSERT INTO status (nom) VALUES 
('admin'), 
('client'), 
('vendeur');

INSERT INTO sexe (nom) VALUES
('homme'),
('femme');

ALTER TABLE vente
ADD COLUMN id_client INTEGER,
ADD COLUMN id_vendeur INTEGER;

ALTER TABLE vente
ADD CONSTRAINT vente_id_client_fkey FOREIGN KEY (id_client) REFERENCES boulangerie_user(id_user),
ADD CONSTRAINT vente_id_vendeur_fkey FOREIGN KEY (id_vendeur) REFERENCES boulangerie_user(id_user);

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