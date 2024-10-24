# reward_test용 더미데이터
CREATE TABLE IF NOT EXISTS crowd_reward_123(
	reward_id int auto_increment primary key,
    crowd_id int not null,
    reward_title varchar(255) not null,
    reward_content varchar(255) not null,
    reward_amount int not null,
    reward_limit int not null,
    is_deleted BOOL default false
    );

INSERT INTO crowd_reward_123 (crowd_id, reward_title, reward_content, reward_amount, reward_limit)
	 VALUES
	 (123, '슈퍼얼리버드1', '기본후원1', 1000, 5),
	 (123, '슈퍼얼리버드2', '기본후원2', 10000, 5),
	 (123, '슈퍼얼리버드3', '기본후원3', 50000, 5);

# sponsor_test용 더미데이터
CREATE TABLE IF NOT EXISTS crowd_sponsor_456(
	sponsor_id int auto_increment primary key,
    crowd_id int not null,
    purchase_id int not null,
    is_deleted BOOL default false
    );

# sponsor_test용 더미데이터
INSERT INTO crowd_sponsor_456 (crowd_id, purchase_id)
VALUES
 		(456, 100),
		(456, 200),
        (456, 300);

DROP TABLE crowd_reward_123;
DROP TABLE crowd_sponsor_456;