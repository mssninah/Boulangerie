ALTER TABLE price_history
ADD COLUMN id_user INT;


-- Create the trigger function
CREATE OR REPLACE FUNCTION update_end_date()
RETURNS TRIGGER AS $$
BEGIN
    -- Update the end_date of the previous price for the same id_recipe
    UPDATE price_history
    SET end_date = NEW.starts_date
    WHERE id_recipe = NEW.id_recipe
      AND end_date IS NULL
      AND id_price <> NEW.id_price;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create the trigger
CREATE TRIGGER set_end_date
AFTER INSERT ON price_history
FOR EACH ROW
EXECUTE FUNCTION update_end_date();

INSERT INTO price_history (id_recipe, price, starts_date, id_user)
VALUES (5, 6.75, '2025-01-10', NULL);
