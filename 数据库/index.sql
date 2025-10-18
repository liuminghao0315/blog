-- DROP TABLE IF EXISTS  `blog`;
-- CREATE TABLE `blog`(
-- `id` VARCHAR(50),
-- `title` VARCHAR (50),
-- `author_id` VARCHAR(50),
-- `content` VARCHAR(2000),
-- `summary` VARCHAR(200),
-- `gmt_created` datetime,
-- `gmt_modified` datetime,
-- PRIMARY KEY ( `id` )
-- )ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS  `user`;
-- CREATE TABLE `user`(
-- `id` VARCHAR(50),
-- `name` VARCHAR (50),
-- `password` VARCHAR(50),
-- `authority` ENUM('ADMINISTRATOR', 'USER', 'GUESS'),
-- `gmt_created` datetime,
-- `gmt_modified` datetime,
-- PRIMARY KEY ( `id` )
-- )ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS  `user_profile`;
-- CREATE TABLE `user_profile`(
-- `id` VARCHAR(50),
-- `user_id` VARCHAR (50),
-- `avatar_url` VARCHAR(255),
-- `gender` ENUM('Male', 'Female', 'Other',"Blank"),
-- `signature` TEXT,
-- `birthday` date,
-- `gmt_created` datetime,
-- `gmt_modified` datetime,
-- PRIMARY KEY ( `id` )
-- )ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS  `comment`;
-- CREATE TABLE `comment`(
-- `id` VARCHAR(50),
-- `article_id` VARCHAR (50),
-- `author_id` VARCHAR(50),
-- `parent_id` VARCHAR (50),
-- `root_id` VARCHAR(50),
-- `content` TEXT,
-- `gmt_created` datetime,
-- `gmt_modified` datetime,
-- PRIMARY KEY ( `id` )
-- )ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS  `article_likes`;
-- CREATE TABLE `article_likes`(
-- `id` VARCHAR(50),
-- `article_id` VARCHAR (50),
-- `user_id` VARCHAR(50),
-- `increment_num` INT CHECK (`increment_num` IN (-1, 0, 1)),
-- `gmt_created` datetime,
-- `gmt_modified` datetime,
-- PRIMARY KEY ( `id` )
-- )ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS  `comment_likes`;
-- CREATE TABLE `comment_likes`(
-- `id` VARCHAR(50),
-- `comment_id` VARCHAR (50),
-- `user_id` VARCHAR(50),
-- `increment_num` INT CHECK (`increment_num` IN (-1, 1)),
-- `gmt_created` datetime,
-- `gmt_modified` datetime,
-- PRIMARY KEY ( `id` )
-- )ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- DROP TABLE IF EXISTS  `favorite`;
-- CREATE TABLE `favorite`(
-- `id` VARCHAR(50),
-- `user_id` VARCHAR (50),
-- `article_id` VARCHAR(50),
-- `gmt_created` datetime,
-- `gmt_modified` datetime,
-- PRIMARY KEY ( `id` )
-- )ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- INSERT INTO `blog` (`id`, `title`, `author_id`, `content`, `summary`, `gmt_created`, `gmt_modified`) VALUES
-- ('1', '人工智能在医疗领域的应用', '1', '# 人工智能在医疗领域的应用\n人工智能正在迅速改变医疗行业，从疾病预测到个性化治疗。\n\n## 智能诊断\nAI可以帮助医生提高诊断的准确性。\n\n- 机器学习算法分析病历\n- 自动化图像识别技术', '探讨人工智能如何在医疗领域应用，提升诊断准确率。', '2025-09-10 09:00:00', '2025-09-10 09:10:00'),
-- ('2', '未来交通与无人驾驶', '1', '# 无人驾驶的未来\n无人驾驶技术正变得越来越成熟，将改变我们的出行方式。\n\n## 技术进展\n当前，多个公司在自动驾驶方面取得了显著的突破。\n\n- 自动驾驶算法的优化\n- 新型传感器的应用', '分析无人驾驶技术的未来发展和对交通行业的影响。', '2025-09-10 10:00:00', '2025-09-10 10:05:00'),
-- ('3', '气候变化与全球变暖', '2', '# 全球变暖的挑战\n气候变化是当今世界面临的最大挑战之一。\n\n## 温室气体排放\n二氧化碳是主要的温室气体之一，对全球气候产生影响。\n\n- 燃烧化石燃料的影响\n- 减少碳排放的必要性', '讨论气候变化的原因、影响及应对策略。', '2025-09-10 11:00:00', '2025-09-10 11:05:00'),
-- ('4', '区块链技术的未来', '2', '# 区块链的未来\n区块链技术在许多领域都有广泛应用，尤其是在金融和供应链管理中。\n\n## 去中心化应用\n区块链的核心优势是去中心化，使数据更加安全。\n\n- 数字货币\n- 智能合约', '分析区块链技术的潜力及其对各行业的影响。', '2025-09-10 12:00:00', '2025-09-10 12:10:00'),
-- ('5', '人工智能与未来工作', '3', '# 人工智能与未来工作\n讨论人工智能在各行各业中的应用，以及未来对就业模式的影响。\n\n## 自动化与就业\n人工智能将导致部分职位的消失，但也会创造新的岗位。\n\n- 自动化对生产力的影响\n- 新兴行业的崛起', 'AI 对未来工作的影响。', '2025-09-10 08:50:00', '2025-09-10 08:55:00'),
-- ('6', '社交媒体对社会的影响', '4', '# 社交媒体的双刃剑\n社交媒体的普及既带来了信息的传播，也引发了隐私问题。\n\n## 信息传播的速度\n社交平台使信息能够迅速传播，影响社会各个层面。\n\n- 假新闻的传播\n- 信息过载的挑战', '讨论社交媒体对社会各方面的深远影响。', '2025-09-10 14:00:00', '2025-09-10 14:10:00'),
-- ('7', '数字货币与未来金融', '5', '# 数字货币与未来金融\n数字货币正在成为金融行业的新兴力量，可能颠覆传统金融体系。\n\n## 中央银行数字货币\n许多国家正在研究或试行中央银行数字货币（CBDC）。\n\n- 数字货币的优点\n- 对传统银行系统的挑战', '探讨数字货币对未来金融体系的潜在影响。', '2025-09-10 15:00:00', '2025-09-10 15:05:00'),
-- ('8', '量子计算的前景', '4', '# 量子计算的前景\n量子计算有可能彻底改变科学研究和技术发展。\n\n## 超越经典计算机\n量子计算机能够处理经典计算机无法完成的任务。\n\n- 量子算法的突破\n- 量子计算机的实际应用', '量子计算将推动计算技术的革命。', '2025-09-10 16:00:00', '2025-09-10 16:05:00'),
-- ('9', '生物多样性保护', '3', '# 生物多样性保护\n保护生物多样性是全球面临的重大任务，关系到生态平衡和人类未来。\n\n## 物种灭绝的危机\n人类活动已导致许多物种的灭绝风险增加。\n\n- 生态保护区的建立\n- 可持续发展政策', '讨论生物多样性保护的重要性及当前面临的挑战。', '2025-09-10 17:00:00', '2025-09-10 17:10:00'),
-- ('10', '未来教育的变革', '2', '# 未来教育的变革\n随着科技的发展，教育方式正在发生巨大的变化。\n\n## 在线教育\n远程学习技术让教育资源得以全球共享。\n\n- 虚拟课堂\n- 互动学习平台', '分析未来教育将如何改变传统教学模式。', '2025-09-10 18:00:00', '2025-09-10 18:05:00');


-- INSERT INTO `user` (`id`, `name`, `password`, `authority`, `gmt_created`, `gmt_modified`) VALUES
-- ('1', '胡大伟', '123456', 'ADMINISTRATOR', '2025-09-13 08:00:00', '2025-09-13 08:00:00'),
-- ('2', '李久熙', 'password', 'USER', '2025-09-12 09:30:00', '2025-09-12 09:30:00'),
-- ('3', '史蒂夫', 'mima', 'USER', '2025-09-11 10:15:00', '2025-09-11 10:15:00'),
-- ('4', '王东', '246801', 'ADMINISTRATOR', '2025-09-10 11:45:00', '2025-09-10 11:45:00'),
-- ('5', '金克斯', '666666', 'USER', '2025-09-09 14:00:00', '2025-09-09 14:00:00');


-- INSERT INTO `user_profile` (`id`, `user_id`, `avatar_url`, `gender`, `signature`, `birthday`, `gmt_created`, `gmt_modified`) VALUES
-- ('1', '1', 'https://fastly.picsum.photos/id/547/200/200.jpg?hmac=04fFD0MMF_hIH8HFysMTH_z8R7CwblctCIrBpdzouJs', 'Male', '热爱科技与科学的探索。', '1990-05-12', NOW(), NOW()),
-- ('2', '2', 'https://fastly.picsum.photos/id/302/200/200.jpg?hmac=pq7hvNyk4pwuEe5cs2qejMNTc7S1kgev72rC8bSHdJE', 'Female', '热衷于数据科学与人工智能。', '1992-08-22', NOW(), NOW()),
-- ('3', '3', 'https://fastly.picsum.photos/id/905/200/200.jpg?hmac=PxOdpEd_gpj1SeeC1DMysEuacd5-lp9jjs1wfD4ds74', 'Other', '通过编程探索宇宙的奥秘。', '1988-11-30', NOW(), NOW()),
-- ('4', '4', 'https://fastly.picsum.photos/id/998/300/200.jpg?hmac=ABcJlM1FNcyP3AfTFRqfa64HxavGYS_EloO3wVKI6WY', 'Female', '创造与创新，解决问题是我的动力。', '1995-02-18', NOW(), NOW()),
-- ('5', '5', 'https://fastly.picsum.photos/id/520/200/200.jpg?hmac=gq6GVKg64GMqsvk_d6gzXZ7L1htska1jEdgBnAwm4xU', 'Male', '程序员，思想者，梦想家。', '1994-07-07', NOW(), NOW());



-- INSERT INTO `comment` 
-- (`id`, `article_id`, `author_id`, `parent_id`, `root_id`, `content`, `gmt_created`, `gmt_modified`) VALUES
-- ('1' ,     '1',           '2',       NULL,         NULL,   '这篇文章很有见地！感谢分享。', '2025-09-16 10:00:00', '2025-09-16 10:00:00'),
-- ('2' ,     '1',           '3',       '1',         '1',     '我同意你的观点，但我认为还有更多需要考虑的方面。', '2025-09-16 10:05:00', '2025-09-16 10:05:00'),
-- ('3' ,     '1',           '4',       '1',         '1',     '我有些不同的意见，是否可以进一步探讨？', '2025-09-16 10:55:00', '2025-09-16 10:55:00'),
-- ('4' ,     '1',           '5',       '2',         '1',     '我要抬杠？', '2025-09-16 10:55:00', '2025-09-16 10:55:00');


-- INSERT INTO `article_likes` (`id`, `article_id`, `user_id`, `increment_num`, `gmt_created`, `gmt_modified`)
-- VALUES
-- ('1', '1', '1', 1, NOW(), NOW()),
-- ('2', '1', '2', -1, NOW(), NOW()),
-- ('3', '1', '3', 1, NOW(), NOW()),
-- ('4', '2', '1', -1, NOW(), NOW()),
-- ('5', '2', '2', 1, NOW(), NOW()),
-- ('6', '2', '3', -1, NOW(), NOW()),
-- ('7', '3', '4', 1, NOW(), NOW()),
-- ('8', '3', '5', -1, NOW(), NOW()),
-- ('9', '3', '1', 1, NOW(), NOW()),
-- ('10', '4', '2', -1, NOW(), NOW()),
-- ('11', '4', '3', 1, NOW(), NOW()),
-- ('12', '4', '4', -1, NOW(), NOW()),
-- ('13', '5', '5', 1, NOW(), NOW()),
-- ('14', '5', '1', -1, NOW(), NOW()),
-- ('15', '5', '2', 1, NOW(), NOW()),
-- ('16', '6', '3', -1, NOW(), NOW()),
-- ('17', '6', '4', 1, NOW(), NOW()),
-- ('18', '6', '5', -1, NOW(), NOW()),
-- ('19', '7', '1', 1, NOW(), NOW()),
-- ('20', '7', '2', -1, NOW(), NOW()),
-- ('21', '7', '3', 1, NOW(), NOW()),
-- ('22', '8', '4', -1, NOW(), NOW()),
-- ('23', '8', '5', 1, NOW(), NOW()),
-- ('24', '8', '1', -1, NOW(), NOW()),
-- ('25', '9', '2', 1, NOW(), NOW()),
-- ('26', '9', '3', -1, NOW(), NOW()),
-- ('27', '9', '4', 1, NOW(), NOW()),
-- ('28', '10', '5', -1, NOW(), NOW()),
-- ('29', '10', '1', 1, NOW(), NOW()),
-- ('30', '10', '2', -1, NOW(), NOW());

-- INSERT INTO article_likes (id, article_id, user_id, increment_num, gmt_created, gmt_modified)
-- VALUES
-- ('9XITM88THfIlld4MJKDdFDYxJ0QvPwtd0HzFAlqe20bcauSNkY', '1', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('MNg9faUg2zaNjBUDydddsiHlzULNFSYeoHAjNGrjK6O9I1tRHZ', '10','system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('Pe1gL1iHx6GZ0TyZYnvrqAUzOGTRUEXLjnEl5OC2nbHFvoDiIT', '2', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('kagicKwF6tU8lNJ7ujJKlQRuKs9dciIORjYyxum4o5Hgc4mxSU', '3', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('MiyHxY0NL3sDZcAgcm9MXBIOYVoTrvaSCvs5ORvX2voxhNTJfq', '4', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('XNLi1EgZoJFQtMW55cP2gj4Lb1th34Sspx9ElEsJPLKGwuoP3x', '5', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('hIpJTpKi6utjVsdgRGkzBfwpHJ43yuAo8F0id2A9n8qkmqTz13', '6', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('NgtLxKmIebg4R8tNp8MgahRY13k4DtmDxvxn4kwRRExVpRxVP5', '7', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('rN9ZgbyNAxUCEY3PVJ9GQ4rBgqAbnIxttdgBelxUf626SCoBBb', '8', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('1ljsuHSorDJuPETmFwOiLHyNw7fHmoQbBakh5SvzHleT1S6trj', '9', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('MwJSIoUBiKNAjQ7Qqc8tZdTA0GmQJvm4KEMgm9ZaXFvlTDa1Nb', 'I4Jvgtx0M-X2BXiRSaB3n', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('ajsdkjawdklasjdawdjaksdjawlkdjaskjdlaksdksajxaksnn', '0ze5rMSp3eYl8lJUsLxQs', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00'),
-- ('6LdF7g7pLwphJVSmWJBOEewrUYCMhCSxc61CaUzUaPKOS06TF3', 'KpqVdSiy0zSoBPqrtk2aK', 'system', 0, '2025-09-25 00:00:00', '2025-09-25 00:00:00');




-- INSERT INTO `comment_likes` (`id`, `comment_id`, `user_id`, `increment_num`, `gmt_created`, `gmt_modified`) VALUES
-- ('1' , '1', '1', -1, NOW(), NOW()),
-- ('2' , '2', '2', -1, NOW(), NOW()),
-- ('3' , '3', '3', -1, NOW(), NOW()),
-- ('4' , '1', '4', -1, NOW(), NOW()),
-- ('5' , '2', '5', -1, NOW(), NOW()),
-- ('6' , '1', '1', -1, NOW(), NOW()),
-- ('7' , '3', '2', -1, NOW(), NOW()),
-- ('8' , '2', '3', -1, NOW(), NOW()),
-- ('9' , '1', '4', -1, NOW(), NOW()),
-- ('10', '3', '5', -1, NOW(), NOW()),
-- ('11', '2', '1', -1, NOW(), NOW()),
-- ('12', '3', '2', -1, NOW(), NOW()),
-- ('13', '1', '3', -1, NOW(), NOW()),
-- ('14', '2', '4', -1, NOW(), NOW()),
-- ('15', '3', '5', -1, NOW(), NOW()),
-- ('16', '1', '2', -1, NOW(), NOW()),
-- ('17', '2', '3', -1, NOW(), NOW()),
-- ('18', '3', '4', -1, NOW(), NOW()),
-- ('19', '1', '5', -1, NOW(), NOW()),
-- ('20', '2', '1', -1, NOW(), NOW()),
-- ('21', '3', '2', -1, NOW(), NOW()),
-- ('22', '1', '3', -1, NOW(), NOW()),
-- ('23', '2', '4', -1, NOW(), NOW()),
-- ('24', '3', '5', -1, NOW(), NOW()),
-- ('25', '1', '1', -1, NOW(), NOW()),
-- ('26', '2', '2', -1, NOW(), NOW()),
-- ('27', '3', '3', -1, NOW(), NOW()),
-- ('28', '1', '4', -1, NOW(), NOW()),
-- ('29', '2', '5', -1, NOW(), NOW());
-- ('30', '4', '1', -1, NOW(), NOW()),
-- ('31', '4', '3', -1, NOW(), NOW()),
-- ('32', '4', '4', -1, NOW(), NOW()),
-- ('33', '4', '5', -1, NOW(), NOW()),
-- ('34', '4', '1', -1, NOW(), NOW());


-- INSERT INTO `favorite` (`id`, `user_id`, `article_id`, `gmt_created`, `gmt_modified`)
-- VALUES
-- ('1', '1', '3', NOW(), NOW()),
-- ('2', '2', '5', NOW(), NOW()),
-- ('3', '3', '8', NOW(), NOW()),
-- ('4', '4', '2', NOW(), NOW()),
-- ('5', '5', '7', NOW(), NOW()),
-- ('6', '1', '10', NOW(), NOW()),
-- ('7', '2', '4', NOW(), NOW()),
-- ('8', '3', '6', NOW(), NOW()),
-- ('9', '4', '1', NOW(), NOW()),
-- ('10', '5', '9', NOW(), NOW());


DROP TABLE IF EXISTS  `blogs_images`;
CREATE TABLE `blogs_images`(
`id` VARCHAR(50),
`author_id` VARCHAR (50),
`blog_id` VARCHAR(50),
`file_name` VARCHAR(40),
`status` INT CHECK (`status` IN (0, 1, 2)),
`gmt_created` datetime,
`gmt_modified` datetime,
PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

