DELETE
FROM user;

INSERT INTO user (id, name, age, is_enable, source, gender, head_url)
VALUES (1, 'Jone', 18, 1, 'jd', 1, '/1.jpg'),
       (2, 'Jack', 20, 2, 'bd', 1, '/2.jpg'),
       (3, 'Tom', 28, 2, 'tx', 1, '/3.jpg'),
       (4, 'Sandy', 21, 2, 'bd', 2, '/4.jpg'),
       (5, 'Billie', 24, 1, 'jd', 2, '/5.jpg');

INSERT INTO dict (id, label, value, dict_key)
VALUES (1, '京东', 'jd', 'USER_SOURCE'),
       (2, '百度', 'bd', 'USER_SOURCE'),
       (3, '腾讯', 'tx', 'USER_SOURCE'),
       (4, '男', '1', 'GENDER'),
       (5, '女', '2', 'GENDER');