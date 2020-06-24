USE master
IF EXISTS(SELECT * FROM sys.databases where name='HKTTStoreDB')
DROP DATABASE HKTTStoreDB
GO

CREATE DATABASE HKTTStoreDB
GO
USE HKTTStoreDB
GO

CREATE TABLE [Category](
   [Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
   [Name] [nvarchar](250) NOT NULL,
   [ModifiedDate] datetime NOT NULL
)​

CREATE TABLE [SubCategory](
   [Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
   [Name] [nvarchar](250) NOT NULL,
   [CategoryId] int NOT NULL FOREIGN KEY REFERENCES Category(Id),
   [ModifiedDate] datetime NOT NULL
)​

CREATE TABLE [Manufacturer](
   [Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
   [Name] [nvarchar](250) NOT NULL,
   [Address] [nvarchar](250) NOT NULL,
   [Email] [varchar](250) NOT NULL,
   [Phone] [varchar](20) NOT NULL,
   [ModifiedDate] datetime NOT NULL
)​

CREATE TABLE [Unit](
   [Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
   [Name] [nvarchar](250) NOT NULL,
   [ModifiedDate] datetime NOT NULL
)​

CREATE TABLE [Product]
(
	[Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[Name] nvarchar(250) NOT NULL,
	[OriginalPrice] float NOT NULL,
	[SellingPrice] float NOT NULL,
	[Description] nvarchar(250) NOT NULL,
	[CategoryId] int NOT NULL FOREIGN KEY REFERENCES Category(Id),
	[SubCategoryId] int NOT NULL FOREIGN KEY REFERENCES SubCategory(Id),
	[UnitId] int NOT NULL FOREIGN KEY REFERENCES Unit(Id),
	[ManufacturerId] int NOT NULL FOREIGN KEY REFERENCES Manufacturer(Id),
	[MadeIn] nvarchar(250) NOT NULL,
	[ImageURL] nvarchar(250) NOT NULL,
	[ModifiedDate] datetime NOT NULL,
	[IsActive] int NOT NULL
)

CREATE TABLE [Promotion](
   [Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
   [ProductId] int NOT NULL FOREIGN KEY REFERENCES [Product](Id),
   [ImageURL] nvarchar(250) NOT NULL,
   [ModifiedDate] datetime NOT NULL
)​


CREATE TABLE [Customer](
   [Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
   [Name] [nvarchar](250) NOT NULL,
   [Email] [varchar](250) NOT NULL,
   [Phone] [varchar](20) NOT NULL,
   [Password] [varchar](250) NOT NULL,
   [Address] [nvarchar](250) NOT NULL,
   [ModifiedDate] datetime NOT NULL,
   [LoginAttemptCount] int NOT NULL,
   [IsActive] int NOT NULL
)​


CREATE TABLE [Orders]
(
	[Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[OrderCode] varchar(50) NOT NULL,
	[CustomerId] int NOT NULL FOREIGN KEY REFERENCES [Customer](Id),
	[ShipName] nvarchar(250) NOT NULL,
	[ShipPhone] nvarchar(250) NOT NULL,
	[ShipAddress] nvarchar(250) NOT NULL,
	[ShipNote] nvarchar(250) NULL,
	[OrderDate] datetime NOT NULL,
	[Status] int NOT NULL
)

CREATE TABLE [OrderDetail]
(
	[Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[OrderId] int NOT NULL  FOREIGN KEY REFERENCES [Orders](Id),
	[ProductId] int NOT NULL FOREIGN KEY REFERENCES [Product](Id),
	[ProductName] nvarchar(250) NOT NULL,
	[OriginalPrice] float NOT NULL,
	[SellingPrice] float NOT NULL,
	[Description] nvarchar(250) NOT NULL,
	[UnitName] nvarchar(250) NOT NULL,
	[ManufacturerName] nvarchar(250) NOT NULL,
	[MadeIn] nvarchar(250) NOT NULL,
	[ImageURL] nvarchar(250) NOT NULL,
	[Quantity] int NOT NULL
)


CREATE TABLE [Cart]
(
	[Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[CustomerId] int NOT NULL FOREIGN KEY REFERENCES [Customer](Id),
)

CREATE TABLE [CartDetail]
(
	[Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[CartId] int NOT NULL  FOREIGN KEY REFERENCES [Cart](Id),
	[ProductId] int NOT NULL FOREIGN KEY REFERENCES [Product](Id),
	[Quantity] int NOT NULL
)


CREATE TABLE [Employee](
   [Id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
   [Username] [varchar](50) NOT NULL,
   [Password] [varchar](250) NOT NULL,
   [Name] [nvarchar](250) NOT NULL,
   [Email] [varchar](250) NOT NULL,
   [Phone] [varchar](20) NOT NULL,
   [IsActive] int NOT NULL,
   [ModifiedDate] datetime NOT NULL
)​

INSERT INTO [Employee] ([Username], [Password], [Name], [Email], [Phone], [IsActive], [ModifiedDate])
VALUES ('admin', '123456', N'Pham Huynh', 'huynh@gmail.com', '0987654321', 1, '2020-06-01');


INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Foods', '2020-06-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Drinks', '2020-06-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Diapers, Milks, Baby items', '2020-06-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Cooking oil, Spices', '2020-06-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Personal care', '2020-06-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Cleaning home', '2020-06-01')



INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Fish',1, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Meat',1, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Vegetable',1, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Fruit',1, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Noodles, porridge',1, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Beer, alcoholic drinks',2, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Coffee, tea',2, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Diaper',3, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Milk',3, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Cooking oil',4, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Sauces',4, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Spices',4, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Shampoo, hair care',5, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Paper towels, wet tissues',5, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Detergents',6, '2020-06-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Spray room, Insecticides',6, '2020-06-01')



INSERT INTO [Unit] (Name, [ModifiedDate]) VALUES (N'250G', '2020-06-01')
INSERT INTO [Unit] (Name, [ModifiedDate]) VALUES (N'500G', '2020-06-01')
INSERT INTO [Unit] (Name, [ModifiedDate]) VALUES (N'1Kg', '2020-06-01')
INSERT INTO [Unit] (Name, [ModifiedDate]) VALUES (N'1 tray', '2020-06-01')
INSERT INTO [Unit] (Name, [ModifiedDate]) VALUES (N'1 pack', '2020-06-01')
INSERT INTO [Unit] (Name, [ModifiedDate]) VALUES (N'1 box', '2020-06-01')


INSERT INTO [Manufacturer]([Name], [Address], [Email], [Phone], [ModifiedDate])​ 
VALUES (N'Naturland', N'01 Sáng tạo', 'itdgroud.com.vn', '0123456789' ,'2020-06-01'),
		(N'Lotte', N'01 Sáng tạo', 'lotte.com.vn', '0123456789' ,'2020-06-01')

/* Fish */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Organic Pangasius Fish Fillet',129000,115000, N'Binca Organic Pangasius fillet contains absolutely no GMO (free), absolutely no pesticides, herbicides and antibiotics', 1, 1, 1, 1, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046352/fish1_ailvp8.png' , '2020-06-01',1),
	   (N'Organic Pangasius Fish Steaks',79000,79000, N'Binca Organic Pangasius fillet contains absolutely no GMO (free), absolutely no pesticides, herbicides and antibiotics', 1, 1, 1, 1, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046364/fish2_ugznnt.jpg' , '2020-06-01',1),
	   (N'Emperor Fillet',105000,105000, N'Crayfish, also known as sea carp, looks quite similar to common carp, often exploited in the wild. Crayfish toned meat, white delicious and especially rich in nutrients', 1, 1, 2, 1, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046372/fish3_ln7qhr.jpg' , '2020-06-01',1),
	   (N'Largehead Hairtail Fillet',92000,92000, N'Choice L 500G Cut Fish is a popular cooking ingredient in Asian countries. Despite its thin body, the pit fish is relatively firm, can be used in the form of fresh, chilled and dried.', 1, 1, 2, 1, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053933/fish4_ufybup.jpg' , '2020-06-01',1),
	   (N'Large frozen cooked shrimp',134000,100000, N'Large Size Frozen Steamed Shrimp (16-25 pieces) Choice L 400G consists of naturally delicious fresh shrimp, carefully selected and carefully prepared. ', 1, 1, 2, 1, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046386/fish5_xnkh6p.jpg' , '2020-06-01',1),
	   (N'Shrimp ring',121000,110000, N'Shrimp Ring has been peeled, steamed, just thawed or steamed to enjoy immediately.', 1, 1, 1, 1, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046393/fish6_k9fljx.jpg' , '2020-06-01',1);

/* Meat */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'French Rack Iberico',225000,200000, N'It is the only pork that helps reduce the risk of cardiovascular disease, control blood sugar.', 1, 2, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053933/meat1_utp2gm.jpg' , '2020-06-01',1),
	   (N'Boneless Collar Iberico',207000,207000, N'It is the only pork that helps reduce the risk of cardiovascular disease, control blood sugar.', 1, 2, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046408/meat2_pzfzbi.jpg' , '2020-06-01',1),
	   (N'Spare Ribs Iberico',165000,165000, N' A quality, clean, tasty and extremely nutritious meat that is the only pork with good fats.', 1, 2, 2, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046408/meat2_pzfzbi.jpg' , '2020-06-01',1),
	   (N'Sirloin Iberico',190000,180000, N'Sirloin Iberico back meat is the only pork that has a GOOD FAT for the human body with a high content of Oleic acid up to 52% of the fat.', 1, 2, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046423/meat4_jgchge.jpg' , '2020-06-01',1),
	   (N'Flank steak New Zealand',172000,172000, N'Soft strip meat, characteristic soft, crunchy, sweet.', 1, 2, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053966/meat5_gzrrtj.jpg' , '2020-06-01',1),
	   (N'Rib eye roll New Zealand',125000,125000, N'Is the meat located on the side of the cow.', 1, 2, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053966/meat6_kz9u5i.jpg' , '2020-06-01',1),
	   (N'Tenderloin New Zealand',212000,180000, N'Is the tenderloin or ham tenderloin or fillet is the best and most delicious beef', 1, 2, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053967/meat7_ctjzvf.jpg' , '2020-06-01',1),
	   (N'Beef hamburger New Zealand',37800,37800, N'Main ingredients: 65% New Zealand beef, 35% bucket fat, onion, whipped cream, pepper, salt, thyme leaves.', 1, 2, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053967/meat8_x6zfss.jpg' , '2020-06-01',1),
	   (N'Chuck roll New Zealand',95000,90000, N'Taken from the flesh adjacent to the shoulder, armpit and front legs of an American cow.', 1, 2, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053967/meat9_tx8jrx.jpg' , '2020-06-01',1),
	   (N'Chicken swings CP',40000,34000, N'Products without antibiotics, without weight gain, have been quarantined and certified for food safety and hygiene.', 1, 2, 2, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053967/meat10_rv8lj1.jpg' , '2020-06-01',1),
	   (N'Chicken breast fillet',45000,40000, N'Chicken breast is a fresh, fat-free breast meat, suitable for users of all ages.', 1, 2, 2, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053966/meat11_r5mqus.jpg' , '2020-06-01',1);

/* Vegetable */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Tomato',16900,16900, N'Tomatoes contain many beneficial nutrients such as carotene, lycopene, vitamins and potassium.', 1, 3, 2, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046439/vet1_iftg4s.jpg' , '2020-06-01',1),
	   (N'Carrot',17900,17900, N'Carrots are rich in protid, lipid, glucid, fiber, trace elements and vitamins, the highest of which is carotene content.', 1, 3, 2, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046451/vet2_pcha2o.gif' , '2020-06-01',1),
	   (N'Bayby eggplant',19900,19900, N'Japanese Eggplant Baby Dalatgap 200G is dark purple in color, size and shape like an egg. This is a fleshy berry, very thin skin.', 1, 3, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592053967/vet3_tu4zmk.jpg' , '2020-06-01',1),
	   (N'Chinese broccoli',29900,24900, N'Cauliflower is a very healthy food because almost every plant nutrient is added to the flower.', 1, 3, 1, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592046459/vet3_gvzu04.jpg' , '2020-06-01',1);


/* Fruit */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Mekostar Green Skin Grapefruit',150000,110000, N'Grapefruit has a rosy red intestine. Cool sweetness.', 1, 4, 2, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230068/aphrjop4qquri0by3vdh.jpg' , '2020-06-01',1),
	   (N'Strawberry',139000,139000, N'Imported Korean strawberries. Big, round, fragrant, sweet fruits. Suitable as a gift or for children to use.', 1, 4, 2, 1, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230093/sh1x1aryj9viy4vkcpfy.jpg' , '2020-06-01',1),
	   (N'Box of 3 Orange Navel',110000,110000, N'Cam Navel with succulent orange cloves with a sweet taste and almost no seeds are suitable for making desserts suitable for all ages.', 1, 4, 2, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230116/dp4izdnane1z9wetzlmw.jpg' , '2020-06-01',1),
	   (N'Kiwi Gold 4 Fruit Box 500G',119000,119000, N'The sweet, tropical aroma makes it a great summer dessert.', 1, 4, 2, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230140/zijaspufxbowzxzbsygt.jpg' , '2020-06-01',1),
	   (N'Green Grapes And Red Grapes',34900,34900, N'Phan Rang Green Grapes and Red Grapes have long been a typical trademark of the sunny Phan Rang land.', 1, 4, 2, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230165/a27flmnwboutkjfinqln.jpg' , '2020-06-01',1);

/* Noodles, porridge */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Ottogi Black Noodles',54900,54900, N'Ottogi Noodles in Black Sauce 135G is produced based on a modern line, a closed process, ensuring food safety and hygiene.', 1, 5, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230227/c9u59athedueleixb0by.jpg' , '2020-06-01',1),
	   (N'Safoco Fresh Rice Vermicelli Pack of 300G',17900,16000, N'Safoco fresh vermicelli is processed into dry fiber but gives a constant quality when processing like fresh vermicelli fibers.', 1, 5, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230249/ieg06zlssiiopykwej7e.jpg' , '2020-06-01',1),
	   (N'Hao Hao Spicy and Sour Shrimp Noodles',8100,7300, N'Hao Hao Cup Noodles Spicy and sour shrimp with main ingredients are carefully selected and produced on a closed modern line without using toxic additives, so it is absolutely safe for consumers health.', 1, 5, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230271/okuakaegjbsegv737uqu.jpg' , '2020-06-01',1),
	   (N'DongWon Spicy Rice Cake',121000,121000, N'DongWon Spicy Rice Cake.', 1, 5, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230296/mnkqh3bnwxemwvoyqnnr.jpg' , '2020-06-01',1),
	   (N'Yorihada Seaweed Soup',59000,59000, N'Yorihada Seaweed Soup 500g pack is prepared and packaged handy, giving homemakers more convenient in preparing nutritious dishes for the whole family without spending too much time.', 1, 5, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592230320/lu6m0ne0z371kemcitxg.jpg' , '2020-06-01',1);
	  

/* Beer, alcoholic drinks */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Budweiser Beer 330ML (24 Cans)',417600,417600, N'Budweiser Beer 330ML (24 Cans) is a type of beer made with malt along with premium hops from the United States and Europe.', 2, 6, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592229828/vyte0dnhoahrqq5om1oj.jpg' , '2020-06-01',1),
		(N'Coronita Beer 210ML (24 Bottles)',504000,504000, N'Originating from Mexico, Corona Beer has quickly been favored by people in North America, Europe, Australia and Asia.', 2, 6, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592229874/qsysl7b7inoze4grmnbz.jpg' , '2020-06-01',1),
		(N'Heineken Sleek Beer 330ML (24 Cans)',420000,420000, N'Heineken Beer products have a shiny golden beer color and smooth, smooth white foam to prove a perfect beer.', 2, 6, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592229921/rv0rsp0tqfykry1wwsj9.jpg' , '2020-06-01',1),
		(N'Pepsi Sleek Carbonated Soft Drink 330ML (6 Cans)',51000,51000, N'Pepsi is a well-known brand of Coca-Cola flavored beverage globally.', 2, 6, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592229965/vxg88op2hdwknx5vy6fy.jpg' , '2020-06-01',1),
		(N'7UP Sleek Carbonated Soft Drink 330ML (6 Cans)',51000,40000, N'7UP Sleek Soft Drink with moderate sweetness and cool lemon flavor, 7 Up carbonated soft drink helps you quench thirst quickly.', 2, 6, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592229996/qxayu712om1glithzyqr.jpg' , '2020-06-01',1);


/* Coffee, tea */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Dried Artichoke Langfarm Cotton Bucket 225g',219000,219000, N'Dried Artichoke Langfarm Cotton Bucket 225G is made from herbal materials.', 2, 7, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742666/gfkxzvm7mkbsuqtsw2bm.jpg' , '2020-06-01',1),
		(N'Honey Ginger Tea Powder 144G',134300,100000, N'Honey Ginger Tea Powder 144G is a product manufactured using modern technology, preserving the delicious taste typical of ginger with many good uses for health.', 2, 7, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742709/wxypvlm19etroqg7rwwh.jpg' , '2020-06-01',1),
		(N'Instant Coffee Vinacafe Gold',78500,67300, N'Gold Orinal Vinacafe has a much stronger flavor than regular coffee.', 2, 7, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742773/yrumqybiau7o84wwduxh.jpg' , '2020-06-01',1),
		(N'Nescafe Gold Blend Coffee 100G',160000,160000, N'Nescafe Gold Jar 100G is a special blend of selected Robusta & Arabica coffee beans, giving the full aroma and the most pure coffee flavor.', 2, 7, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742805/zcn04ducfhvstcjcutr1.jpg' , '2020-06-01',1);

/* Diaper */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Bobby Newborn Pads',162000,149000, N'Minimizing contact between the diaper and the umbilical cord section, protecting the newborn umbilical area is always dry.', 3, 8, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742850/kotp3liob5kt7c07wscf.jpg' , '2020-06-01',1),
		(N'Huggies Dry NB1 Pads',142000,125000, N'Understanding the delicate and delicate newborn skin, the famous Huggies brand has launched a line of newborn pads', 3, 8, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742894/cchc3fyyjgfuhenv9jmj.jpg' , '2020-06-01',1),
		(N'Bobby Fresh Newborn Newborn Pads',134000,120900, N'Bobby Fresh Newborn Baby Underwear Set 2 Pack of 60 Pieces (Baby Over 1 Month) with technology application from Unicharm Corporation (Japan).', 3, 8, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742914/yznuz8qzzzajbvdtdyqm.jpg' , '2020-06-01',1),
		(N'Pamper Japanese Diaper Stickers',355000,315000, N'Safe material according to American and European standards with soft cotton ingredients such as premium silk.', 3, 8, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742937/cghefzmkdjn4ljqnhjyu.jpg' , '2020-06-01',1);
																						
/* Milk */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'TH True Milk Sterilized Fresh Milk',34900,34900, N'Full milk from fresh cows milk (97%), refined sugar (2.8%), stabilizer, used for food', 3, 9, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742969/ccc2wbiuemafidn5v5wl.jpg' , '2020-06-01',1),
		(N'Southern Star Condensed Milk',18600,18600, N'Green Southern Star Condensed Milk is trusted and commonly used to make flan cake, yogurt, smoothies', 3, 9, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592742997/uy5m7bvklmirqmjmyfxx.jpg' , '2020-06-01',1),
		(N'Barley Drink Nestle Milo',26500,26500, N'Barley Drink Nestle Milo Instant Drink with Protomalt formula is an outstanding nutritional extract from whole wheat germ, milk, cocoa powder.', 3, 9, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592743013/epzyor4dc2s49xczs3zt.jpg' , '2020-06-01',1),
		(N'Optimum Gold Prepared Milk Powder',56600,56600, N'New Optimum Gold, quality recognized by UKAS - UK, with this formula for absorption is supplemented with 20% DHA from pure algae.', 3, 9, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592743013/epzyor4dc2s49xczs3zt.jpg' , '2020-06-01',1);


/* Cooking oil */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Meizan Premium Cooking Oil Gold Bottle 1L',42100,42100, N'Meizan Gold 1L cooking oil is the result of efforts to research, improve and upgrade Meizan high quality vegetable oil products.', 4, 10, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592743700/bkqrbhaoebtaw7obrlmw.jpg' , '2020-06-01',1),
		(N'Tuong An Gold Cooking Oil Bottle 1L',47500,47500, N'Derived from Palm oil, Rapeseed, Sunflower seeds and Vitamin A supplements.', 4, 10, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592743714/efwipe1ft4nwe5xww2tj.jpg' , '2020-06-01',1);

/* Sauces */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Maggi Oyster Sauce Bottle 350G',22500,22500, N'Maggi Oyster Sauce is a sauce made from pure oysters combined with other spices to create a specific flavor for stir-fries.', 4, 11, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592743727/rnebelwj1zmwohnqsiht.jpg' , '2020-06-01',1),
		(N'Nam Ngu Dipping Sauce',35200,35200, N'Nam Ngu 3 In 1 Fish Sauce is made from 100% pure fresh anchovies with a strong sea flavor.', 4, 11, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592743738/kszyobjsefxayo5c9uby.jpg' , '2020-06-01',1);


/* Spices */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Premium Refined Sugar Choice L 1KG',20200,20200, N'Premium Refined Sugar is manufactured on modern technological processes, ensuring product quality and safety for users.', 4, 12, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592744346/oplshufrxakuxbt3ibb5.jpg' , '2020-06-01',1),
		(N'Ajinomoto Monosodium glutamate',29000,28500, N'Produced by natural fermentation from natural raw materials such as molasses molasses and tapioca starch.', 4, 12, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592744360/cjaqlgggpnwrqj3ttwun.png' , '2020-06-01',1);
	
/* Shampoo, hair care */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Lifebuoy Hand Wash',31000,31000, N'With active 5 essence and milk essence, 10% Protection Lifebuoy Hand Wash (177ml) helps kill 99.9% of bacteria in just 10 seconds.', 5, 13, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592744370/uxna0yidl21h43uefpjb.jpg' , '2020-06-01',1),
		(N'Lifebuoy Hand Cleaner Activated Carbon and Mint',36000,36000, N'With a pH balanced formula containing silver ions and two 100% natural ingredients, including Activated Carbon and Cool Mint.', 5, 13, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592744378/oq34l8i3fbuzvjuiiepb.jpg' , '2020-06-01',1);
	

/* Paper towels, wet tissues */

/* Detergents */
INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Extreme Laundry Powder Has Choice L Soffy',262000,200000, N'Choice L Soffy Extreme Laundry Powder with a gentle formula for skin, suitable for all fabrics, gives your clothes a fresh, natural fragrance', 6, 15, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592744744/zvhcl4yfoz2ggvomuaew.jpg' , '2020-06-01',1),
		(N'Surf Laundry Liquid Perfume Morning',114000,114000, N'Detergent Surf Mist Morning Mist Concentrates more concentrated, will penetrate deep into each fabric, washing easier,', 6, 15, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592744753/lb2qoz2afda9bflc1dou.jpg' , '2020-06-01',1),
		(N'Vim Cleaning Toilets',38000,30500, N'Vim Whitening Toilet & Toilet Cleansing Gel Bright White Lavender Cool Bottle 880ml helps to clean the toilet and toilet, kill pathogenic bacteria.', 6, 15, 5, 2, N'Viet Nam', 'https://res.cloudinary.com/sakata1301/image/upload/v1592744761/wnvkbz7ryogw5ezjohqe.jpg' , '2020-06-01',1);
	      
/* Spray room, Insecticides */


INSERT INTO [Promotion] ([ProductId], [ImageURL], [ModifiedDate]) 
VALUES (42, 'https://res.cloudinary.com/sakata1301/image/upload/v1590663513/promotion4_i5d1gz.jpg', '2020-06-01'),
		(58, 'https://res.cloudinary.com/sakata1301/image/upload/v1590663544/promotion5_sflxt8.jpg', '2020-06-01'),
		(30, 'https://res.cloudinary.com/sakata1301/image/upload/v1590663351/promotion1_bh69sk.jpg', '2020-06-01'),
		(32, 'https://res.cloudinary.com/sakata1301/image/upload/v1592745248/ap01l5eq6ax7pwtsjnoc.jpg', '2020-06-01'),
		(27, 'https://res.cloudinary.com/sakata1301/image/upload/v1592745265/hs0kgiunvtelnsvu9by9.jpg', '2020-06-01');


INSERT INTO [Customer] ([Name], [Email], [Phone],[Password], [Address], [ModifiedDate], [LoginAttemptCount], [IsActive])​ 
VALUES (N'Le Ngoc Dang Khoa', 'lndkhoa95@gmail.com', '0938367752', '123456', N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh','2020-06-01', 0, 1),
	   (N'Ha Minh Tan', 'tan@gmail.com', '0392649291', '123456',N'39 Cô Bắc, Quận 1, TP Hồ Chí Minh', '2020-06-01', 0, 1),
	   (N'Pham Huynh', 'huynh@gmail.com', '0981213348', '123456',N'225 Nguyễn Thị Thập, Quận 7, TP Hồ Chí Minh', '2020-06-01', 0, 1),
	   (N'Hoang Thanh Tai', 'tai@gmail.com', '0387695509', '123456',N'145 Nguyễn Văn Linh, Quận 7, TP Hồ Chí Minh', '2020-06-01', 0, 1),
	   (N'Khoa Le', 'khoa@gmail.com', '0938367751', '123456',N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh', '2020-06-01', 0, 1)


INSERT [dbo].[Cart] ( [CustomerId]) VALUES (1)
INSERT [dbo].[Cart] ( [CustomerId]) VALUES (2)
INSERT [dbo].[Cart] ( [CustomerId]) VALUES (3)
INSERT [dbo].[Cart] ( [CustomerId]) VALUES (4)
INSERT [dbo].[Cart] ( [CustomerId]) VALUES (5)

SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (1, N'20200621203548889_1', 1, N'Lê Ngọc Đăng Khoa', N'0938367752', N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-04-10T20:35:48.890' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (2, N'20200621203612100_1', 1, N'Lê Ngọc Đăng Khoa', N'0938367752', N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-04-11T20:36:12.100' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (3, N'20200621203645145_2', 2, N'Hà Minh Tân', N'0392649291', N'39 Cô Bắc, Quận 1, TP Hồ Chí Minh', N'', CAST(N'2020-05-12T20:36:45.147' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (4, N'20200621203703064_2', 2, N'Hà Minh Tân', N'0392649291', N'39 Cô Bắc, Quận 1, TP Hồ Chí Minh', N'', CAST(N'2020-05-13T20:37:03.063' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (5, N'20200621203734893_3', 3, N'Phạm Huỳnh', N'0981213348', N'225 Nguyễn Thị Thập, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-14T20:37:34.893' AS DateTime), 0)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (6, N'20200621203748954_3', 3, N'Phạm Huỳnh', N'0981213348', N'225 Nguyễn Thị Thập, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-15T20:37:48.953' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (7, N'20200621203811301_4', 4, N'Hoàng Thanh Tài', N'0387695509', N'145 Nguyễn Văn Linh, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-16T20:38:11.300' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (8, N'20200621203822118_4', 4, N'Hoàng Thanh Tài', N'0387695509', N'145 Nguyễn Văn Linh, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-16T20:38:22.117' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (9, N'20200621203835880_4', 4, N'Hoàng Thanh Tài', N'0387695509', N'145 Nguyễn Văn Linh, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-17T20:38:35.880' AS DateTime), 0)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (10, N'20200621203848239_4', 4, N'Hoàng Thanh Tài', N'0387695509', N'145 Nguyễn Văn Linh, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-17T20:38:48.240' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (11, N'20200621203952158_5', 5, N'Khoa Lê', N'0938367751', N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-17T20:39:52.160' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (12, N'20200621204017851_1', 1, N'Lê Ngọc Đăng Khoa', N'0938367752', N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T20:40:17.850' AS DateTime), 3)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (13, N'20200621204055167_1', 1, N'Lê Ngọc Đăng Khoa', N'0938367752', N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T20:40:55.167' AS DateTime), 2)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (14, N'20200621211353137_4', 4, N'Hoàng Thanh Tài', N'0387695509', N'145 Nguyễn Văn Linh, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:13:53.137' AS DateTime), 2)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (15, N'20200621211436995_2', 2, N'Hà Minh Tân', N'0392649291', N'39 Cô Bắc, Quận 1, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:14:36.997' AS DateTime), 2)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (16, N'20200621211517108_2', 2, N'Hà Minh Tân', N'0392649291', N'39 Cô Bắc, Quận 1, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:15:17.110' AS DateTime), 2)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (17, N'20200621211537489_2', 2, N'Hà Minh Tân', N'0392649291', N'39 Cô Bắc, Quận 1, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:15:37.490' AS DateTime), 2)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (18, N'20200621211627020_3', 3, N'Phạm Huỳnh', N'0981213348', N'225 Nguyễn Thị Thập, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:16:27.020' AS DateTime), 2)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (19, N'20200621211646328_3', 3, N'Phạm Huỳnh', N'0981213348', N'225 Nguyễn Thị Thập, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:16:46.330' AS DateTime), 1)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (20, N'20200621211704894_3', 3, N'Phạm Huỳnh', N'0981213348', N'225 Nguyễn Thị Thập, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:17:04.893' AS DateTime), 1)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (21, N'20200621211719514_3', 3, N'Phạm Huỳnh', N'0981213348', N'225 Nguyễn Thị Thập, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:17:19.513' AS DateTime), 1)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (22, N'20200621211749036_1', 1, N'Lê Ngọc Đăng Khoa', N'0938367752', N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:17:49.037' AS DateTime), 1)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (23, N'20200621211809109_1', 1, N'Lê Ngọc Đăng Khoa', N'0938367752', N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:18:09.110' AS DateTime), 1)
INSERT [dbo].[Orders] ([Id], [OrderCode], [CustomerId], [ShipName], [ShipPhone], [ShipAddress], [ShipNote], [OrderDate], [Status]) VALUES (24, N'20200621211949955_1', 1, N'Lê Ngọc Đăng Khoa', N'0938367752', N'502 Huỳnh Tấn Phát, Quận 7, TP Hồ Chí Minh', N'', CAST(N'2020-06-21T21:19:49.957' AS DateTime), 1)
SET IDENTITY_INSERT [dbo].[Orders] OFF
GO
SET IDENTITY_INSERT [dbo].[OrderDetail] ON 

INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (1, 1, 38, N'Honey Ginger Tea Powder 144G', 134300, 100000, N'Honey Ginger Tea Powder 144G is a product manufactured using modern technology, preserving the delicious taste typical of ginger with many good uses for health.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592742709/wxypvlm19etroqg7rwwh.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (2, 1, 57, N'Extreme Laundry Powder Has Choice L Soffy', 262000, 200000, N'Choice L Soffy Extreme Laundry Powder with a gentle formula for skin, suitable for all fabrics, gives your clothes a fresh, natural fragrance', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744744/zvhcl4yfoz2ggvomuaew.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (3, 1, 54, N'Ajinomoto Monosodium glutamate', 29000, 28500, N'Produced by natural fermentation from natural raw materials such as molasses molasses and tapioca starch.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744360/cjaqlgggpnwrqj3ttwun.png', 2)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (4, 2, 32, N'Budweiser Beer 330ML (24 Cans)', 417600, 417600, N'Budweiser Beer 330ML (24 Cans) is a type of beer made with malt along with premium hops from the United States and Europe.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592229828/vyte0dnhoahrqq5om1oj.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (5, 2, 34, N'Heineken Sleek Beer 330ML (24 Cans)', 420000, 420000, N'Heineken Beer products have a shiny golden beer color and smooth, smooth white foam to prove a perfect beer.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592229921/rv0rsp0tqfykry1wwsj9.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (6, 3, 1, N'Organic Pangasius Fish Fillet', 129000, 115000, N'Binca Organic Pangasius fillet contains absolutely no GMO (free), absolutely no pesticides, herbicides and antibiotics', N'250G', N'Naturland', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592046352/fish1_ailvp8.png', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (7, 3, 4, N'Largehead Hairtail Fillet', 92000, 92000, N'Choice L 500G Cut Fish is a popular cooking ingredient in Asian countries. Despite its thin body, the pit fish is relatively firm, can be used in the form of fresh, chilled and dried.', N'500G', N'Naturland', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592053933/fish4_ufybup.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (8, 3, 23, N'Strawberry', 139000, 139000, N'Imported Korean strawberries. Big, round, fragrant, sweet fruits. Suitable as a gift or for children to use.', N'500G', N'Naturland', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592230093/sh1x1aryj9viy4vkcpfy.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (9, 3, 25, N'Kiwi Gold 4 Fruit Box 500G', 119000, 119000, N'The sweet, tropical aroma makes it a great summer dessert.', N'500G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592230140/zijaspufxbowzxzbsygt.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (10, 4, 55, N'Lifebuoy Hand Wash', 31000, 31000, N'With active 5 essence and milk essence, 10% Protection Lifebuoy Hand Wash (177ml) helps kill 99.9% of bacteria in just 10 seconds.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744370/uxna0yidl21h43uefpjb.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (11, 4, 56, N'Lifebuoy Hand Cleaner Activated Carbon and Mint', 36000, 36000, N'With a pH balanced formula containing silver ions and two 100% natural ingredients, including Activated Carbon and Cool Mint.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744378/oq34l8i3fbuzvjuiiepb.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (12, 5, 36, N'7UP Sleek Carbonated Soft Drink 330ML (6 Cans)', 51000, 40000, N'7UP Sleek Soft Drink with moderate sweetness and cool lemon flavor, 7 Up carbonated soft drink helps you quench thirst quickly.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592229996/qxayu712om1glithzyqr.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (13, 6, 51, N'Maggi Oyster Sauce Bottle 350G', 22500, 22500, N'Maggi Oyster Sauce is a sauce made from pure oysters combined with other spices to create a specific flavor for stir-fries.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592743727/rnebelwj1zmwohnqsiht.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (14, 6, 22, N'Mekostar Green Skin Grapefruit', 150000, 110000, N'Grapefruit has a rosy red intestine. Cool sweetness.', N'500G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592230068/aphrjop4qquri0by3vdh.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (15, 6, 57, N'Extreme Laundry Powder Has Choice L Soffy', 262000, 200000, N'Choice L Soffy Extreme Laundry Powder with a gentle formula for skin, suitable for all fabrics, gives your clothes a fresh, natural fragrance', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744744/zvhcl4yfoz2ggvomuaew.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (16, 7, 58, N'Surf Laundry Liquid Perfume Morning', 114000, 114000, N'Detergent Surf Mist Morning Mist Concentrates more concentrated, will penetrate deep into each fabric, washing easier,', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744753/lb2qoz2afda9bflc1dou.jpg', 3)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (17, 8, 55, N'Lifebuoy Hand Wash', 31000, 31000, N'With active 5 essence and milk essence, 10% Protection Lifebuoy Hand Wash (177ml) helps kill 99.9% of bacteria in just 10 seconds.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744370/uxna0yidl21h43uefpjb.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (18, 9, 35, N'Pepsi Sleek Carbonated Soft Drink 330ML (6 Cans)', 51000, 51000, N'Pepsi is a well-known brand of Coca-Cola flavored beverage globally.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592229965/vxg88op2hdwknx5vy6fy.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (19, 9, 36, N'7UP Sleek Carbonated Soft Drink 330ML (6 Cans)', 51000, 40000, N'7UP Sleek Soft Drink with moderate sweetness and cool lemon flavor, 7 Up carbonated soft drink helps you quench thirst quickly.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592229996/qxayu712om1glithzyqr.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (20, 10, 49, N'Meizan Premium Cooking Oil Gold Bottle 1L', 42100, 42100, N'Meizan Gold 1L cooking oil is the result of efforts to research, improve and upgrade Meizan high quality vegetable oil products.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592743700/bkqrbhaoebtaw7obrlmw.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (21, 10, 50, N'Tuong An Gold Cooking Oil Bottle 1L', 47500, 47500, N'Derived from Palm oil, Rapeseed, Sunflower seeds and Vitamin A supplements.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592743714/efwipe1ft4nwe5xww2tj.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (22, 11, 52, N'Nam Ngu Dipping Sauce', 35200, 35200, N'Nam Ngu 3 In 1 Fish Sauce is made from 100% pure fresh anchovies with a strong sea flavor.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592743738/kszyobjsefxayo5c9uby.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (23, 11, 53, N'Premium Refined Sugar Choice L 1KG', 20200, 20200, N'Premium Refined Sugar is manufactured on modern technological processes, ensuring product quality and safety for users.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744346/oplshufrxakuxbt3ibb5.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (24, 11, 54, N'Ajinomoto Monosodium glutamate', 29000, 28500, N'Produced by natural fermentation from natural raw materials such as molasses molasses and tapioca starch.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744360/cjaqlgggpnwrqj3ttwun.png', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (25, 12, 59, N'Vim Cleaning Toilets', 38000, 30500, N'Vim Whitening Toilet & Toilet Cleansing Gel Bright White Lavender Cool Bottle 880ml helps to clean the toilet and toilet, kill pathogenic bacteria.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744761/wnvkbz7ryogw5ezjohqe.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (26, 12, 8, N'Boneless Collar Iberico', 207000, 207000, N'It is the only pork that helps reduce the risk of cardiovascular disease, control blood sugar.', N'250G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592046408/meat2_pzfzbi.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (27, 12, 10, N'Sirloin Iberico', 190000, 180000, N'Sirloin Iberico back meat is the only pork that has a GOOD FAT for the human body with a high content of Oleic acid up to 52% of the fat.', N'250G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592046423/meat4_jgchge.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (28, 12, 14, N'Beef hamburger New Zealand', 37800, 37800, N'Main ingredients: 65% New Zealand beef, 35% bucket fat, onion, whipped cream, pepper, salt, thyme leaves.', N'250G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592053967/meat8_x6zfss.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (29, 13, 50, N'Tuong An Gold Cooking Oil Bottle 1L', 47500, 47500, N'Derived from Palm oil, Rapeseed, Sunflower seeds and Vitamin A supplements.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592743714/efwipe1ft4nwe5xww2tj.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (30, 13, 52, N'Nam Ngu Dipping Sauce', 35200, 35200, N'Nam Ngu 3 In 1 Fish Sauce is made from 100% pure fresh anchovies with a strong sea flavor.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592743738/kszyobjsefxayo5c9uby.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (31, 13, 41, N'Bobby Newborn Pads', 162000, 149000, N'Minimizing contact between the diaper and the umbilical cord section, protecting the newborn umbilical area is always dry.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592742850/kotp3liob5kt7c07wscf.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (32, 14, 59, N'Vim Cleaning Toilets', 38000, 30500, N'Vim Whitening Toilet & Toilet Cleansing Gel Bright White Lavender Cool Bottle 880ml helps to clean the toilet and toilet, kill pathogenic bacteria.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744761/wnvkbz7ryogw5ezjohqe.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (33, 14, 55, N'Lifebuoy Hand Wash', 31000, 31000, N'With active 5 essence and milk essence, 10% Protection Lifebuoy Hand Wash (177ml) helps kill 99.9% of bacteria in just 10 seconds.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744370/uxna0yidl21h43uefpjb.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (34, 15, 9, N'Spare Ribs Iberico', 165000, 165000, N' A quality, clean, tasty and extremely nutritious meat that is the only pork with good fats.', N'500G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592046408/meat2_pzfzbi.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (35, 15, 16, N'Chicken swings CP', 40000, 34000, N'Products without antibiotics, without weight gain, have been quarantined and certified for food safety and hygiene.', N'500G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592053967/meat10_rv8lj1.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (36, 15, 28, N'Safoco Fresh Rice Vermicelli Pack of 300G', 17900, 16000, N'Safoco fresh vermicelli is processed into dry fiber but gives a constant quality when processing like fresh vermicelli fibers.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592230249/ieg06zlssiiopykwej7e.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (37, 15, 30, N'DongWon Spicy Rice Cake', 121000, 121000, N'DongWon Spicy Rice Cake.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592230296/mnkqh3bnwxemwvoyqnnr.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (38, 16, 53, N'Premium Refined Sugar Choice L 1KG', 20200, 20200, N'Premium Refined Sugar is manufactured on modern technological processes, ensuring product quality and safety for users.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744346/oplshufrxakuxbt3ibb5.jpg', 5)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (39, 17, 38, N'Honey Ginger Tea Powder 144G', 134300, 100000, N'Honey Ginger Tea Powder 144G is a product manufactured using modern technology, preserving the delicious taste typical of ginger with many good uses for health.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592742709/wxypvlm19etroqg7rwwh.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (40, 17, 37, N'Dried Artichoke Langfarm Cotton Bucket 225g', 219000, 219000, N'Dried Artichoke Langfarm Cotton Bucket 225G is made from herbal materials.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592742666/gfkxzvm7mkbsuqtsw2bm.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (41, 17, 39, N'Instant Coffee Vinacafe Gold', 78500, 67300, N'Gold Orinal Vinacafe has a much stronger flavor than regular coffee.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592742773/yrumqybiau7o84wwduxh.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (42, 18, 30, N'DongWon Spicy Rice Cake', 121000, 121000, N'DongWon Spicy Rice Cake.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592230296/mnkqh3bnwxemwvoyqnnr.jpg', 3)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (43, 18, 5, N'Large frozen cooked shrimp', 134000, 100000, N'Large Size Frozen Steamed Shrimp (16-25 pieces) Choice L 400G consists of naturally delicious fresh shrimp, carefully selected and carefully prepared. ', N'500G', N'Naturland', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592046386/fish5_xnkh6p.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (44, 18, 46, N'Southern Star Condensed Milk', 18600, 18600, N'Green Southern Star Condensed Milk is trusted and commonly used to make flan cake, yogurt, smoothies', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592742997/uy5m7bvklmirqmjmyfxx.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (45, 18, 47, N'Barley Drink Nestle Milo', 26500, 26500, N'Barley Drink Nestle Milo Instant Drink with Protomalt formula is an outstanding nutritional extract from whole wheat germ, milk, cocoa powder.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592743013/epzyor4dc2s49xczs3zt.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (46, 19, 22, N'Mekostar Green Skin Grapefruit', 150000, 110000, N'Grapefruit has a rosy red intestine. Cool sweetness.', N'500G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592230068/aphrjop4qquri0by3vdh.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (47, 19, 19, N'Carrot', 17900, 17900, N'Carrots are rich in protid, lipid, glucid, fiber, trace elements and vitamins, the highest of which is carotene content.', N'500G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592046451/vet2_pcha2o.gif', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (48, 19, 21, N'Chinese broccoli', 29900, 24900, N'Cauliflower is a very healthy food because almost every plant nutrient is added to the flower.', N'250G', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592046459/vet3_gvzu04.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (49, 20, 32, N'Budweiser Beer 330ML (24 Cans)', 417600, 417600, N'Budweiser Beer 330ML (24 Cans) is a type of beer made with malt along with premium hops from the United States and Europe.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592229828/vyte0dnhoahrqq5om1oj.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (50, 21, 58, N'Surf Laundry Liquid Perfume Morning', 114000, 114000, N'Detergent Surf Mist Morning Mist Concentrates more concentrated, will penetrate deep into each fabric, washing easier,', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744753/lb2qoz2afda9bflc1dou.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (51, 21, 59, N'Vim Cleaning Toilets', 38000, 30500, N'Vim Whitening Toilet & Toilet Cleansing Gel Bright White Lavender Cool Bottle 880ml helps to clean the toilet and toilet, kill pathogenic bacteria.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744761/wnvkbz7ryogw5ezjohqe.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (52, 22, 53, N'Premium Refined Sugar Choice L 1KG', 20200, 20200, N'Premium Refined Sugar is manufactured on modern technological processes, ensuring product quality and safety for users.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744346/oplshufrxakuxbt3ibb5.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (53, 22, 54, N'Ajinomoto Monosodium glutamate', 29000, 28500, N'Produced by natural fermentation from natural raw materials such as molasses molasses and tapioca starch.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744360/cjaqlgggpnwrqj3ttwun.png', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (54, 23, 51, N'Maggi Oyster Sauce Bottle 350G', 22500, 22500, N'Maggi Oyster Sauce is a sauce made from pure oysters combined with other spices to create a specific flavor for stir-fries.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592743727/rnebelwj1zmwohnqsiht.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (55, 23, 36, N'7UP Sleek Carbonated Soft Drink 330ML (6 Cans)', 51000, 40000, N'7UP Sleek Soft Drink with moderate sweetness and cool lemon flavor, 7 Up carbonated soft drink helps you quench thirst quickly.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592229996/qxayu712om1glithzyqr.jpg', 4)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (56, 24, 58, N'Surf Laundry Liquid Perfume Morning', 114000, 114000, N'Detergent Surf Mist Morning Mist Concentrates more concentrated, will penetrate deep into each fabric, washing easier,', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592744753/lb2qoz2afda9bflc1dou.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (57, 24, 39, N'Instant Coffee Vinacafe Gold', 78500, 67300, N'Gold Orinal Vinacafe has a much stronger flavor than regular coffee.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592742773/yrumqybiau7o84wwduxh.jpg', 1)
INSERT [dbo].[OrderDetail] ([Id], [OrderId], [ProductId], [ProductName], [OriginalPrice], [SellingPrice], [Description], [UnitName], [ManufacturerName], [MadeIn], [ImageURL], [Quantity]) VALUES (58, 24, 40, N'Nescafe Gold Blend Coffee 100G', 160000, 160000, N'Nescafe Gold Jar 100G is a special blend of selected Robusta & Arabica coffee beans, giving the full aroma and the most pure coffee flavor.', N'1 pack', N'Lotte', N'Viet Nam', N'https://res.cloudinary.com/sakata1301/image/upload/v1592742805/zcn04ducfhvstcjcutr1.jpg', 1)
SET IDENTITY_INSERT [dbo].[OrderDetail] OFF
GO
