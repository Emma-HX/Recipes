INSERT INTO users (user_id, username, password_hash, email) VALUES
(1, 'alice', 'hash1', 'alice@example.com'),
(2, 'bob', 'hash2', 'bob@example.com'),
(3, 'carol', 'hash3', 'carol@example.com'),
(4, 'david', 'hash4', 'david@example.com'),
(5, 'eve', 'hash5', 'eve@example.com'),
(6, 'frank', 'hash6', 'frank@example.com'),
(7, 'grace', 'hash7', 'grace@example.com'),
(8, 'heidi', 'hash8', 'heidi@example.com'),
(9, 'ivan', 'hash9', 'ivan@example.com'),
(10, 'judy', 'hash10', 'judy@example.com');

INSERT INTO recipes (recipe_id, user_id, title, description, image_path, prepSteps) VALUES
(1, 1, 'Pancakes', 'Fluffy pancakes', '/images/1.jpg', 'Mix and cook'),
(2, 2, 'Spaghetti', 'Classic spaghetti', '/images/1.jpg', 'Boil and mix'),
(3, 3, 'Salad', 'Healthy green salad', '/images/1.jpg', 'Chop and toss'),
(4, 4, 'Omelette', 'Cheese omelette', '/images/1.jpg', 'Beat and fry'),
(5, 5, 'Soup', 'Tomato soup', '/images/1.jpg', 'Boil and blend'),
(6, 6, 'Burger', 'Beef burger', '/images/1.jpg', 'Grill and assemble'),
(7, 7, 'Pizza', 'Homemade pizza', '/images/1.jpg', 'Bake with toppings'),
(8, 8, 'Rice Bowl', 'Chicken rice bowl', '/images/1.jpg', 'Cook and serve'),
(9, 9, 'Smoothie', 'Fruit smoothie', '/images/1.jpg', 'Blend all'),
(10, 10, 'Cookies', 'Chocolate chip cookies', '/images/1.jpg', 'Mix and bake');


INSERT INTO units (unit_id, unit_name) VALUES
(1, 'grams'),
(2, 'ml'),
(3, 'cups'),
(4, 'tablespoons'),
(5, 'teaspoons'),
(6, 'pieces'),
(7, 'slices'),
(8, 'pinch'),
(9, 'liters'),
(10, 'oz');


INSERT INTO recipe_ingredients (id, recipe_id, ingredient_name, quantity, unit_id) VALUES
(1, 1, 'Flour', 200.00, 1),
(2, 2, 'Spaghetti noodles', 150.00, 1),
(3, 3, 'Lettuce', 100.00, 6),
(4, 4, 'Eggs', 3.00, 6),
(5, 5, 'Tomatoes', 5.00, 6),
(6, 6, 'Beef patty', 1.00, 6),
(7, 7, 'Mozzarella', 200.00, 1),
(8, 8, 'Chicken breast', 1.00, 6),
(9, 9, 'Banana', 1.00, 6),
(10, 10, 'Chocolate chips', 100.00, 1);


INSERT INTO categories (category_id, name) VALUES
(1, 'Breakfast'),
(2, 'Lunch'),
(3, 'Dinner'),
(4, 'Snack'),
(5, 'Dessert'),
(6, 'Vegan'),
(7, 'Vegetarian'),
(8, 'Quick Meal'),
(9, 'Healthy'),
(10, 'Baking');


INSERT INTO recipe_categories (recipe_id, category_id) VALUES
(1, 1),
(2, 2),
(3, 9),
(4, 1),
(5, 2),
(6, 2),
(7, 3),
(8, 2),
(9, 9),
(10, 5);
