INSERT INTO partner (id,image_name,title,description,price) VALUES (1,'nike.png','Nike','Скидка 10% на первую покупку\n(Акция действет ежемесячно)',200);
INSERT INTO partner (id,image_name,title,description,price) VALUES (2,'adidas.png','Adidas','Верните до 15% при покупке от 3000 рублей\nАкция дейстительна до 27.09.2020',150);
INSERT INTO partner (id,image_name,title,description,price) VALUES (3,'red_bull.png','Red Bull','Скидка 5%\nПри подписке на срок более 3х месяцев скидка 8% на любую покупку',250);

INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (1,'1.jpg','Clothes',1600,'Свитшот','Свитшот утепленный','2016-11-09 11:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (2,'q.jpg','Clothes',1700,'Худи утепленное','Толстовка (худи), утепленное, цвет синий.','2016-11-09 10:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (3,'6.jpg','Clothes',1500,'Толстовка женская','Толстовка женская. Материал: 92%-хлопок, 8%-лайкра','2016-10-09 14:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (4,'4.jpg','Clothes',1500,'Толстовка женская','Толстовка женская на молнии. Материал: 92%-хлопок, 8%-лайкра','2017-11-09 11:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (5,'29.jpg','Souvenirs',1200,'Часы ЮФУ ясень','Часы изготовлены из массива ясеня','2016-11-07 11:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (6,'18.jpg','Souvenirs',500,'Кружка керамическая','Кружка керамическая','2016-11-08 12:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (7,'5.jpg','Souvenirs',1200,'Футболка Юфу','Футболка Юфу','2016-11-08 10:48:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (8,'16.jpg','Souvenirs',900,'Флешка ЮФУ ясень','Флешка из массива ясеня','2016-11-02 12:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (9,'27.jpg','Souvenirs',300,'Блокнот Юфу','Блокнот Юфу','2016-11-08 17:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (10,'7.jpg','Souvenirs',1200,'Худи','Черное худи','2016-11-08 20:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (11,'22.jpg','Souvenirs',1000,'Комплект ручек','Комплект из 2-х ручек','2016-11-08 13:44:44');

INSERT INTO orders (id,user_id,order_id,data_of_order,status) VALUES (1,1,12323123,1602944662186,'Не доставлено');
INSERT INTO orders (id,user_id,order_id,data_of_order,status) VALUES (2,2,123231231,1602944662186,'Не доставлено');

INSERT INTO order_2_product (id,order_id,product_id) VALUES (1,"12323123",2);
INSERT INTO order_2_product (id,order_id,product_id) VALUES (2,"12323123",1);
INSERT INTO order_2_product (id,order_id,product_id) VALUES (3,"12323123",3);
INSERT INTO order_2_product (id,order_id,product_id) VALUES (4,"12323123",5);

INSERT INTO images (id,product_id,image_name) VALUES (1,1,"1.jpg");
INSERT INTO images (id,product_id,image_name) VALUES (2,1,"1.jpg");
INSERT INTO images (id,product_id,image_name) VALUES (3,1,"1.jpg");


INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (12,'5.jpg','Souvenirs',1234200,'Футболка Юфу','Футболка Юфу','2015-11-08 10:48:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (13,'16.jpg','Souvenirs',9002,'Флешка ЮФУ ясень','Флешка из массива ясеня','2011-11-02 12:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (14,'27.jpg','Souvenirs',3003,'Блокнот Юфу','Блокнот Юфу','2013-11-08 17:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (15,'7.jpg','Souvenirs',100,'Худи','Черное худи','2016-09-08 20:44:44');
INSERT INTO catalog (id,image_name,category,price,title,descriptions,date) VALUES (16,'22.jpg','Souvenirs',13000,'Комплект ручек','Комплект из 2-х ручек','2010-11-08 13:44:44');