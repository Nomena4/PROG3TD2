insert into dish (id, name, dish_type, selling_price)
values (1, 'Salaide fraîche', 'STARTER', 3500.0),
       (2, 'Poulet grillé', 'MAIN', 12000.0),
       (3, 'Riz aux légumes', 'MAIN', null),
       (4, 'Gâteau au chocolat ', 'DESSERT', 8000.0),
       (5, 'Salade de fruits', 'DESSERT', null);


insert into ingredient (id, name, category, price)
values (1, 'Laitue', 'VEGETABLE', 800.0),
       (2, 'Tomate', 'VEGETABLE', 600.0),
       (3, 'Poulet', 'ANIMAL', 4500.0),
       (4, 'Chocolat ', 'OTHER', 3000.0),
       (5, 'Beurre', 'DAIRY', 2500.0);



update dish
set price = 2000.0
where id = 1;

update dish
set price = 6000.0
where id = 2;


insert into stock_movement(id, id_ingredient, quantity, type, unit, creation_datetime)
values (1, 1, 5.0, 'IN', 'KG', '2024-01-05 08:00'),
       (2, 1, 0.2, 'OUT', 'KG', '2024-01-06 12:00'),
       (3, 2, 4.0, 'IN', 'KG', '2024-01-05 08:00'),
       (4, 2, 0.15, 'OUT', 'KG', '2024-01-06 12:00'),
       (5, 3, 10.0, 'IN', 'KG', '2024-01-04 09:00'),
       (6, 3, 1.0, 'OUT', 'KG', '2024-01-06 13:00'),
       (7, 4, 3.0, 'IN', 'KG', '2024-01-05 10:00'),
       (8, 4, 0.3, 'OUT', 'KG', '2024-01-06 14:00'),
       (9, 5, 2.5, 'IN', 'KG', '2024-01-05 10:00'),
       (10, 5, 0.2, 'OUT', 'KG', '2024-01-06 14:00');


insert into dish_ingredient (id, id_dish, id_ingredient, required_quantity, unit)
values (1, 1, 1, 0.2, 'KG'),
       (2, 1, 2, 0.15, 'KG'),
       (3, 2, 3, 1.0, 'KG'),
       (4, 4, 4, 0.3, 'KG'),
       (5, 4, 5, 0.2, 'KG');
-- SQL Migration Script for Payment Status and Sale Features

-- Step 1: Add payment_status column to order table
ALTER TABLE "order"
    ADD COLUMN payment_status VARCHAR(10) NOT NULL DEFAULT 'UNPAID';

-- Add constraint to ensure only valid values
ALTER TABLE "order"
    ADD CONSTRAINT check_payment_status
        CHECK (payment_status IN ('UNPAID', 'PAID'));

-- Step 2: Create sale table
CREATE TABLE sale (
                      id SERIAL PRIMARY KEY,
                      sale_datetime TIMESTAMP NOT NULL,
                      order_id INTEGER NOT NULL UNIQUE,
                      CONSTRAINT fk_sale_order FOREIGN KEY (order_id)
                          REFERENCES "order"(id) ON DELETE CASCADE
);

-- Add index on order_id for performance
CREATE INDEX idx_sale_order_id ON sale(order_id);

-- Optional: Add comments for documentation
COMMENT ON TABLE sale IS 'Table storing sales associated with paid orders';
COMMENT ON COLUMN sale.sale_datetime IS 'Date and time when the sale was created';
COMMENT ON COLUMN sale.order_id IS 'Foreign key to the order table (one-to-one relationship)';
COMMENT ON COLUMN "order".payment_status IS 'Payment status of the order: UNPAID or PAID';