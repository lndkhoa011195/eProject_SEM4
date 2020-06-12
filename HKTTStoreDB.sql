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


INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Baby Care', '2020-05-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Personal Care', '2020-05-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Grocery', '2020-05-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Household', '2020-05-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Women', '2020-05-01')
INSERT INTO [Category] (Name, [ModifiedDate]) VALUES (N'Men', '2020-05-01')

INSERT INTO [Unit] (Name, [ModifiedDate]) VALUES (N'Kg', '2020-05-01')


INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Baby Food',1, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Diapers & Wipes',1, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Baby Accessories',1, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Skin Care',2, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Hair Care',2, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Oral Care',2, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Bath, Face & Hand Wash',2, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Cheese',3, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Cakes & Toast',3, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Beverages',3, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Dal & Pulses',3, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Rice',3, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Kitchen Appliances',4, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Vacuum and Floor care',4, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Jewellery',5, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Make-up',5, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Perfume',5, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Perfume',6, '2020-05-01')
INSERT INTO [SubCategory] (Name, CategoryId, [ModifiedDate]) VALUES (N'Accessories',6, '2020-05-01')


INSERT INTO [Manufacturer]([Name], [Address], [Email], [Phone], [ModifiedDate])​ VALUES (N'Công ty ITD', N'01 Sáng tạo', 'itdgroud.com.vn', '0123456789' ,'2020-05-01')

INSERT INTO [Product]([Name], [OriginalPrice],[SellingPrice], [Description], [CategoryId], [SubCategoryId], [UnitId], [ManufacturerId], [MadeIn], [ImageURL], [ModifiedDate], [IsActive])​ 
VALUES (N'Kitchen Appliance',20000,20000, N'Abc', 1, 1, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635375/botPhanemBe_ujvxan.jpg' , '2020-05-01',1),
		(N'Apple mazane',20000,18000, N'Abc', 2, 2, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635461/dauAnNeptune_wwzlsl.jpg' , '2020-05-01',1),
		(N'Holle',20000,20000, N'Abc', 2, 2, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635521/dauGoiDauclearMan_a543zj.jpg' , '2020-05-01',1),
		(N'Cheese',20000,20000, N'Abc', 1, 3, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635584/dauthomnamDior_rk7xbe.jpg' , '2020-05-01',1),
		(N'Nestle',20000,18000, N'Abc', 2, 4, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635619/nuoctayTrang_av1mbo.png' , '2020-05-01',1),
		(N'Toy',20000,20000, N'Abc', 3, 1, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635693/nuoctuongMage_oen1co.jpg' , '2020-05-01',1),
		(N'Conditioner',20000,20000, N'Abc', 3, 2, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635747/phantrandiem_ozgp7f.jpg' , '2020-05-01',1),
		(N'LUX VELVET TOUCH',20000,18000, N'Abc', 6, 3, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635779/sonMac_zeyikd.jpg' , '2020-05-01',1),
		(N'Arhar Dal 1 Kg',20000,20000, N'Abc', 6, 1, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635838/suaTamEmbe_nq5gko.jpg' , '2020-05-01',1),
		(N'Diaper',20000,18000, N'Abc', 6, 5, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635894/taEmbe_lodjyz.jpg' , '2020-05-01',1),
		(N'Watch',20000,20000, N'Abc', 5, 1, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635963/botngot_orrcdp.jpg' , '2020-05-01',1),
		(N'Calibre',20000,18000, N'Abc', 3, 3, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635375/botPhanemBe_ujvxan.jpg' , '2020-05-01',1),
		(N'Perfume',20000,18000, N'Abc', 2, 4, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635461/dauAnNeptune_wwzlsl.jpg' , '2020-05-01',1),
		(N'Burberry',20000,20000, N'Abc', 5, 6, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635521/dauGoiDauclearMan_a543zj.jpg' , '2020-05-01',1),
		(N'Nail Polish',20000,20000, N'Abc', 2, 2, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635584/dauthomnamDior_rk7xbe.jpg' , '2020-05-01',1),
		(N'Lipstick',20000,18000, N'Abc', 4, 3, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635619/nuoctayTrang_av1mbo.png' , '2020-05-01',1),
		(N'Contour kit',20000,20000, N'Abc', 1, 2, 1, 1, N'USA', 'https://res.cloudinary.com/sakata1301/image/upload/v1590635693/nuoctuongMage_oen1co.jpg' , '2020-05-01',1)

INSERT INTO [Promotion] ([ProductId], [ImageURL], [ModifiedDate]) 
VALUES (1, 'https://res.cloudinary.com/sakata1301/image/upload/v1590663351/promotion1_bh69sk.jpg', '2020-05-01'),
		(4, 'https://res.cloudinary.com/sakata1301/image/upload/v1590663440/promotion2_naoafu.jpg', '2020-05-01'),
		(2, 'https://res.cloudinary.com/sakata1301/image/upload/v1590663478/promotion3_codsyq.png', '2020-05-01'),
		(6, 'https://res.cloudinary.com/sakata1301/image/upload/v1590663513/promotion4_i5d1gz.jpg', '2020-05-01'),
		(10, 'https://res.cloudinary.com/sakata1301/image/upload/v1590663544/promotion5_sflxt8.jpg', '2020-05-01')


INSERT INTO [Customer] ([Name], [Email], [Phone],[Password], [Address], [ModifiedDate], [LoginAttemptCount], [IsActive])​ 
VALUES (N'Khoa', 'lndkhoa95@gmail.com', '0938367752', '123456', N'Ho Chi Minh','2020-05-15', 0, 1),
		 (N'Tan', 'khoa@gmail.com', '0938367751', '123456',N'Ha Noi', '2020-05-15', 0, 1)


