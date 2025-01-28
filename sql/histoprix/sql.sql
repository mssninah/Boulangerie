CREATE TABLE history_price (
    id SERIAL PRIMARY KEY,
    id_recipe INT NOT NULL,
    new_price NUMERIC(10,2) NOT NULL,
    change_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe)
);
