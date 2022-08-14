BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "products" (
	"IDproduct"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT NOT NULL,
	"category"	TEXT,
	"unit"	TEXT,
	"picture"	TEXT
);
CREATE TABLE IF NOT EXISTS "recipes" (
	"IDrecipes"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"namerecipes"	TEXT,
	"category"	TEXT,
	"preparationtime"	INTEGER,
	"description"	TEXT,
	"image"	TEXT
);
CREATE TABLE IF NOT EXISTS "fridge" (
	"ownerID"	INTEGER,
	"productID"	INTEGER,
	"description"	TEXT,
	"quantity"	INTEGER,
	FOREIGN KEY("ownerID") REFERENCES "users"("IDuser") ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("productID") REFERENCES "products"("IDproduct")
);
CREATE TABLE IF NOT EXISTS "diet_time" (
	"dietID"	INTEGER,
	"weektime"	INTEGER,
	"recipeID"	INTEGER,
	FOREIGN KEY("dietID") REFERENCES "diet"("IDdiet") ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("recipeID") REFERENCES "recipes"("IDrecipes") ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "diet_users" (
	"dietID"	INTEGER,
	"userID"	INTEGER,
	FOREIGN KEY("userID") REFERENCES "users"("IDuser") ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("dietID") REFERENCES "diet"("IDdiet") ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "ingridients" (
	"recipeID"	INTEGER,
	"productID"	INTEGER,
	FOREIGN KEY("recipeID") REFERENCES "recipes"("IDrecipes") ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("productID") REFERENCES "products"("IDproduct") ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "diet" (
	"IDdiet"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"namediet"	TEXT,
	"category"	TEXT,
	"description"	TEXT
);
CREATE TABLE IF NOT EXISTS "shopping_connections" (
	"listID"	INTEGER,
	"memberID"	INTEGER,
	FOREIGN KEY("listID") REFERENCES "shopping_lists"("IDlist") ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("memberID") REFERENCES "users"("IDuser") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "shopping_lists" (
	"IDlist"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"namelist"	TEXT NOT NULL,
	"creatorID"	INTEGER NOT NULL,
	FOREIGN KEY("creatorID") REFERENCES "users"("IDuser") ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "shopping_list" (
	"listID"	INTEGER,
	"productID"	INTEGER,
	"description"	TEXT,
	"quantity"	INTEGER,
	FOREIGN KEY("productID") REFERENCES "products"("IDproduct"),
	FOREIGN KEY("listID") REFERENCES "shopping_lists"("IDlist") ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "users" (
	"IDuser"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"username"	TEXT NOT NULL UNIQUE,
	"email"	TEXT NOT NULL,
	"password"	TEXT NOT NULL
);
INSERT INTO "products" VALUES (1,'egg','dairy','1 item',NULL);
INSERT INTO "products" VALUES (2,'milk','dairy','1 litr',NULL);
INSERT INTO "products" VALUES (3,'spaghetti','grain products','500g',NULL);
INSERT INTO "products" VALUES (4,'chicken','meat','500g',NULL);
INSERT INTO "products" VALUES (5,'all-purpose flour','grain products','1000g',NULL);
INSERT INTO "products" VALUES (6,'butter','oils&fats','100g',NULL);
INSERT INTO "products" VALUES (7,'vegetable oil','oils&fats','100g',NULL);
INSERT INTO "products" VALUES (8,'salt','flavoring','100g',NULL);
INSERT INTO "products" VALUES (9,'pancetta','meat','500g',NULL);
INSERT INTO "products" VALUES (10,'parmesan cheese','dairy','500g',NULL);
INSERT INTO "products" VALUES (11,'olive oil','oils&fats','1000g',NULL);
INSERT INTO "products" VALUES (12,'potato','vegetables','1000g',NULL);
INSERT INTO "products" VALUES (13,'bread','bakedgoods','100g',NULL);
INSERT INTO "products" VALUES (14,'tortilla','bakedgoods','100g',NULL);
INSERT INTO "recipes" VALUES (1,'Pancakes','breakfast',30,NULL,NULL);
INSERT INTO "recipes" VALUES (2,'Scrambled Eggs','breakfast',10,NULL,NULL);
INSERT INTO "recipes" VALUES (3,'Roast Chicken','main',60,NULL,NULL);
INSERT INTO "recipes" VALUES (4,'Spaghetti Carbonara','main',60,NULL,NULL);
INSERT INTO "recipes" VALUES (5,'French Fries','main',60,NULL,NULL);
INSERT INTO "diet_time" VALUES (1,1,1);
INSERT INTO "ingridients" VALUES (1,1);
INSERT INTO "ingridients" VALUES (1,2);
INSERT INTO "ingridients" VALUES (1,5);
INSERT INTO "ingridients" VALUES (1,7);
INSERT INTO "ingridients" VALUES (1,8);
INSERT INTO "ingridients" VALUES (2,1);
INSERT INTO "ingridients" VALUES (2,6);
INSERT INTO "ingridients" VALUES (2,8);
INSERT INTO "ingridients" VALUES (3,4);
INSERT INTO "ingridients" VALUES (3,6);
INSERT INTO "ingridients" VALUES (3,8);
INSERT INTO "ingridients" VALUES (4,1);
INSERT INTO "ingridients" VALUES (4,2);
INSERT INTO "ingridients" VALUES (4,3);
INSERT INTO "ingridients" VALUES (4,8);
INSERT INTO "ingridients" VALUES (4,9);
INSERT INTO "ingridients" VALUES (4,10);
INSERT INTO "ingridients" VALUES (4,11);
INSERT INTO "ingridients" VALUES (5,7);
INSERT INTO "ingridients" VALUES (5,8);
INSERT INTO "ingridients" VALUES (5,12);
INSERT INTO "diet" VALUES (1,'TestDiet','general',NULL);
INSERT INTO "shopping_connections" VALUES (2,1);
INSERT INTO "shopping_connections" VALUES (2,3);
INSERT INTO "shopping_connections" VALUES (1,6);
INSERT INTO "shopping_connections" VALUES (2,2);
INSERT INTO "shopping_lists" VALUES (1,'List Test',6);
INSERT INTO "shopping_lists" VALUES (2,'Cholybka',2);
INSERT INTO "shopping_list" VALUES (2,8,NULL,2);
INSERT INTO "shopping_list" VALUES (2,6,NULL,1);
INSERT INTO "shopping_list" VALUES (2,2,NULL,1);
INSERT INTO "shopping_list" VALUES (2,1,NULL,3);
INSERT INTO "shopping_list" VALUES (1,3,'mniam',1);
INSERT INTO "users" VALUES (1,'qwerty','asdasdasd@eg.com','123456');
INSERT INTO "users" VALUES (2,'pawel123','pawel@gmail.com','123456');
INSERT INTO "users" VALUES (3,'sadadasd','assarrrr@gmail.com','123456');
INSERT INTO "users" VALUES (4,'ugabuga','sadasd@gmail.com','sdfsdfsdf');
INSERT INTO "users" VALUES (5,'cholibka','xadxasda@gmail.com','password');
INSERT INTO "users" VALUES (6,'jajojajo','gsfgdsfg@gmail.com','123456');
COMMIT;
