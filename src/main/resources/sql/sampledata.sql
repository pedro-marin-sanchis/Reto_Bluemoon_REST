-- Insert mock data into public.product
INSERT INTO public.product (description, disabled, img, name, price) VALUES
                                                                         ('Product 1 Description', false, 'image1.jpg', 'Product 1', 29.99),
                                                                         ('Product 2 Description', false, 'image2.jpg', 'Product 2', 39.99),
                                                                         ('Product 3 Description', true, 'image3.jpg', 'Product 3', 49.99);

-- Insert mock data into public.silver_type
INSERT INTO public.silver_type (current_price, disabled, name) VALUES
                                                                   (50.0, false, 'Type 1'),
                                                                   (55.0, false, 'Type 2'),
                                                                   (60.0, true, 'Type 3');

-- Insert mock data into public."user"
INSERT INTO public."user" (address, balance, email, name, password_hash, surnames, username) VALUES
                                                                                                 ('Computer Hardware', 100.0, 'bluemood_admin@bluemoon.com', 'Blue Moon', '$2y$10$mYRmCJmc5jcD2dniHqJISOfQRqTXCayVibX/t2kynDPyL0X0cUOpy', 'Admin', 'bluemoon_admin'),
                                                                                                 ('User 2 Address', 200.0, 'user2@example.com', 'User 2', 'hash2', 'Surname 2', 'username2'),
                                                                                                 ('User 3 Address', 300.0, 'user3@example.com', 'User 3', 'hash3', 'Surname 3', 'username3');

-- Insert mock data into public."order"
INSERT INTO public."order" (accepted, address, date, delivered, user_id) VALUES
                                                                             (true, 'Order 1 Address', '2024-01-31 12:00:00', true, 1),
                                                                             (false, 'Order 2 Address', '2024-02-01 14:30:00', false, 2),
                                                                             (true, 'Order 3 Address', '2024-02-02 10:45:00', false, 3);

-- Insert mock data into public.trade
INSERT INTO public.trade (date, validated, user_id) VALUES
                                                        ('2024-01-30 09:30:00', true, 1),
                                                        ('2024-02-01 11:15:00', false, 2),
                                                        ('2024-02-02 15:00:00', true, 3);

-- Insert mock data into public.tradeable
INSERT INTO public.tradeable (description, sell_price, weight, silver_type_id, trade_id) VALUES
                                                                                             ('Tradeable 1', 25.0, 10.0, 1, 1),
                                                                                             ('Tradeable 2', 30.0, 15.0, 2, 1),
                                                                                             ('Tradeable 3', 35.0, 20.0, 3, 2);

-- Insert mock data into public.cart_item
INSERT INTO public.cart_item (quantity, product_id, user_id) VALUES
                                                                 (2, 1, 1),
                                                                 (1, 2, 1),
                                                                 (3, 3, 2),
                                                                 (2, 1, 3);

-- Insert mock data into public.product_order
INSERT INTO public.product_order (quantity, order_id, product_id) VALUES
                                                                      (2, 1, 1),
                                                                      (1, 1, 2),
                                                                      (3, 2, 3),
                                                                      (2, 3, 1);
