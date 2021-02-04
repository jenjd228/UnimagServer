INSERT INTO user (id,birthday,email,fio,max_date,password,points,registration_date,secure_kod,university) VALUES (1,'15.03.2002','e','User',123456,'345',1234,1234456,'wertyu',null);

INSERT INTO partner (id,image_name,title,description,price) VALUES (1,'nike.png','Nike','Скидка 10% на первую покупку\n(Акция действет ежемесячно)',200);
INSERT INTO partner (id,image_name,title,description,price) VALUES (2,'adidas.png','Adidas','Верните до 15% при покупке от 3000 рублей\nАкция дейстительна до 27.09.2020',150);
INSERT INTO partner (id,image_name,title,description,price) VALUES (3,'red_bull.png','Red Bull','Скидка 5%\nПри подписке на срок более 3х месяцев скидка 8% на любую покупку',250);

INSERT INTO pick_up_point (id,pick_up_point) VALUES (1,'Улица Евгения Коцабенко');
INSERT INTO pick_up_point (id,pick_up_point) VALUES (2,'Улица Пашмена');
INSERT INTO pick_up_point (id,pick_up_point) VALUES (3,'Улица Турниковая');

INSERT INTO catalog (id,hash,category,date,descriptions,main_image,price,title) VALUES
(1,'hashe4gerge4','Souvenirs',NOW(),'Красивый блокнот - залог успеха любого студента. А если он выполнен еще и в деревянном стиле - то тогда друзья точно оценят Ваш вкус. Задумайтесь над этим','https://docs.google.com/uc?id=1DTJvR7GrZziGlgsB1sPcvzMDjReosXsj',500,'Блокнот ЮФУ с деревянной обложкой'),
(2,'hashe4gerge5','Clothes',NOW(),'Вы мечтали когда-нибудь об универсальной одежде, в которой можно выйти и в город, и в университет? Тогда это теплое синее худи точно осуществит вашу мечту! Сшитое из специальных тканей, оно будет легко прилегать к вашему телу, не досталяя ни малейшего дискомфорта. А универсальный дизайн точно выделит вас среди других студентов. Успейтие приобрести!','https://docs.google.com/uc?id=1XA_04gUUX2F_MGFwCSiuj07gffw5hAMd',1600,'Синее худи'),
(3,'hashe4gergef','Souvenirs',NOW(),'Магнитик на холодильнике - это часто кусочек истории вашей жизни, глядя на который погружаешься в теплые воспоминания. Так подарите себе и своим близким такые незабываемые воспоминания!','https://docs.google.com/uc?id=1yBfjOmEanPhJ6VrcEOzCjbfw0q8Cp2YN',200,'Магнит с главным корпусом'),
(4,'hashe4getge5','Clothes',NOW(),'Этот лонгслив будет смотреться прекрасно везде, где бы Вы не находились: на лекциях в университете, на соревнованиях по дартсу или же просто на улице. Приобретая такой лонгслив, Вы приобретает не просто элемент одежды в свой гардероб -  Вы приобретаете уникальную возможность выделить себя в толпе студентов!','https://docs.google.com/uc?id=1Eildzzx7aPAOog_LbxurjibeOXcUsPlJ',1200,'Черный лонгслив'),
(5,'hasss4gerge5','Souvenirs',NOW(),'Что может быть лучше запаха кофе по утрам? Только запах кофе из кружки с логотипом Южного Федерального Университета! Не упустите возможность встретить следующее утро в объятях новой атмосферы, создаваемой данной кружкой!','https://docs.google.com/uc?id=1KTfJMxTB5k32cQivlSsa0sNiZo56qY37',600,'Красная кружка с логотипом ЮФУ');

INSERT INTO images (product_id,image_name) VALUES
(1,'https://docs.google.com/uc?id=1DTJvR7GrZziGlgsB1sPcvzMDjReosXsj'),
(1,'https://docs.google.com/uc?id=1-wpe2iCO7Cpnizig7VJQrzp-fmBOgknJ'),
(1,'https://docs.google.com/uc?id=1K8bEFZHlRV52mhCdDVTwLv3E6hgeR5M1'),
(1,'https://docs.google.com/uc?id=1IygGGMVALl1r2cBqPH_zzfitlo7jH_d6'),
(2,'https://docs.google.com/uc?id=1XA_04gUUX2F_MGFwCSiuj07gffw5hAMd'),
(2,'https://docs.google.com/uc?id=1S0Yel35eItz3pYrQKj8h9nxZHvvWzy68'),
(2,'https://docs.google.com/uc?id=1eAnF1so0NQW6CwWiJ_t7kehU8YCWhl45'),
(3,'https://docs.google.com/uc?id=1yBfjOmEanPhJ6VrcEOzCjbfw0q8Cp2YN'),
(3,'https://docs.google.com/uc?id=1IfXMzbHM1zVnKOTMjBVdVzC89-dQ_gLZ'),
(3,'https://docs.google.com/uc?id=1hUSjh_Y9WyErY7RRPXZ4HQTKHqQ-mjlZ'),
(4,'https://docs.google.com/uc?id=1Eildzzx7aPAOog_LbxurjibeOXcUsPlJ'),
(4,'https://docs.google.com/uc?id=1R53pDPIgEZxGIrNJ-WOOOl3uElaUQkLB'),
(4,'https://docs.google.com/uc?id=1BvhdDpUTrgLIOUm01FqJJwvEouViHJLl'),
(5,'https://docs.google.com/uc?id=1KTfJMxTB5k32cQivlSsa0sNiZo56qY37'),
(5,'https://docs.google.com/uc?id=1MR8CXxehdtzKuTnNJx4E3zOK_eh03d_6'),
(5,'https://docs.google.com/uc?id=1lJKHqCaXKyiiHaAPg3ZbThlIXaUK0Ifr');