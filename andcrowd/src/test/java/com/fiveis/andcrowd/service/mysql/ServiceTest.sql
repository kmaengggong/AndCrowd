INSERT INTO and_table (and_id, user_id, and_category_id, funding_id, and_title, and_header_img, and_content, and_end_date, need_num_mem, and_img1, and_img2, and_img3, and_img4, and_img5, published_at, updated_at, and_like_count, and_view_count, and_status, ad_membership_num, is_deleted)
	VALUES(NULL, 1, 1, 123, "andtitle", "headerimgdir", "andcontent", '2023-08-06 09:45:00', 5, null, null, null, null, null, now(), now(), 3, 10, 0, 0, false);


CREATE TABLE and_qna_321(
  and_qna_id INT AUTO_INCREMENT PRIMARY KEY ,
  and_id INT NOT NULL ,
  user_id INT NOT NULL ,
  and_qna_title VARCHAR(200) NOT NULL ,
  and_qna_content VARCHAR(2000) NOT NULL ,
  published_at DATETIME DEFAULT now() ,
  updated_at DATETIME DEFAULT now() ,
  is_deleted BOOL default false
);

INSERT INTO and_qna_321 (and_id, user_id, and_qna_title, and_qna_content)
VALUES
	(321, 1, 'QnA Title 1', 'QnA Content 1'),
	(321, 2, 'QnA Title 2', 'QnA Content 2'),
	(321, 3, 'QnA Title 3', 'QnA Content 3');

DROP TABLE and_qna_321;


CREATE TABLE and_qna_reply_321(
	and_reply_id INT AUTO_INCREMENT PRIMARY KEY ,
	and_qna_id INT NOT NULL ,
	and_id INT NOT NULL ,
	user_id INT NOT NULL ,
	and_reply_content VARCHAR(2000) NOT NULL ,
	published_at DATETIME DEFAULT now() ,
	updated_at DATETIME DEFAULT now() ,
	is_deleted BOOL default false
);

INSERT INTO and_qna_reply_321 (and_reply_id, and_qna_id, and_id, user_id, and_reply_content)
VALUES
	(1, 1, 123, 1, 'QnA Reply Content 1'),
	(2, 2, 123, 2, 'QnA Reply Content 2'),
	(3, 3, 123, 3, 'QnA Reply Content 3'),
	(4, 1, 123, 2, 'QnA Reply Content 1-1');
    
DROP TABLE and_qna_reply_321;


CREATE TABLE and_role_321 (
   and_role_id INT AUTO_INCREMENT PRIMARY KEY ,
   and_id INT NOT NULL ,
   and_role VARCHAR(2000) NOT NULL ,
   and_role_limit INT NOT NULL ,
   is_deleted BOOL default false
);

INSERT INTO and_role_321 (and_role_id, and_id, and_role, and_role_limit)
VALUES
	(1, 123, '역할 1', 10),
	(2, 123, '역할 2', 20),
	(3, 123, '역할 3', 30);

DROP TABLE and_role_321;


CREATE TABLE and_applicant_321 (
	and_apply_id INT AUTO_INCREMENT PRIMARY KEY ,
	and_id INT NOT NULL ,
	user_id INT NOT NULL ,
	and_role_id INT NOT NULL ,
	and_apply_content VARCHAR(2000) NOT NULL ,
    and_apply_status INT default 0,
	is_deleted BOOL default false
);    

INSERT INTO and_applicant_321 (and_apply_id, and_id, user_id, and_role_id, and_apply_content)
VALUES
	(1, 123, 11, 3, '신청서 내용 1'),
	(2, 123, 12, 2, '신청서 내용 2'),
	(3, 123, 13, 1, '신청서 내용 3'),
	(10, 123, 11, 1, '신청서 내용 3');

DROP TABLE dynamic_and_applicant_321;

CREATE TABLE and_board_1111 (
	and_board_id INT AUTO_INCREMENT PRIMARY KEY ,
	and_id INT NOT NULL,
	user_id INT NOT NULL,
	and_board_tag INT,
	and_board_title VARCHAR(200) NOT NULL,
	and_board_content VARCHAR(2000) NOT NULL,
	and_img VARCHAR(255),
	published_at DATETIME DEFAULT now(),
	updated_at DATETIME DEFAULT now(),
	and_board_view_count INT DEFAULT 0 ,
	is_deleted BOOL default false
);

INSERT INTO and_board_1111 (and_id, user_id, and_board_title, and_board_content)
VALUES
	(1111, 1, 'Board Title 1', 'Board Content 1'),
	(1111, 2, 'Board Title 2', 'Board Content 2'),
	(1111, 3, 'Board Title 3', 'Board Content 3');

DROP TABLE and_board_1111;

CREATE TABLE and_member_1111 (
	member_id INT AUTO_INCREMENT PRIMARY KEY,
	and_id INT NOT NULL,
	user_id INT NOT NULL,
	is_deleted BOOL DEFAULT false
);

INSERT INTO and_member_1111 (and_id, user_id)
VALUES
	(1111, 1),
	(1111, 2),
	(1111, 3);

DROP TABLE and_member_1111;



/* CROWD */
INSERT INTO crowd VALUES
(null, 1, 1, 1, '1번본문', now(), 10000, 'user/img/img.png', 'user/img/img.png', 'user/img/img.png', 'user/img/img.png', 'user/img/img.png', 1, '1번제목', 'user/img/img.png', 0, 100, now(), now(), 1, 0),
(null, 2, 2, 2, '2번본문', now(), 20000, 'user/img/img2.png', 'user/img/img2.png', 'user/img/img2.png', 'user/img/img2.png', 'user/img/img2.png', 2, '2번제목', 'user/img/img2.png', 0, 200, now(), now(), 2, 0),
(null, 3, 3, 3, '3번본문', now(), 30000, 'user/img/img3.png', 'user/img/img3.png', 'user/img/img3.png', 'user/img/img3.png', 'user/img/img3.png', 3, '3번제목', 'user/img/img3.png', 0, 300, now(), now(), 3, 0),
(null, 4, 4, 4, '4번본문', now(), 40000, 'user/img/img4.png', 'user/img/img4.png', 'user/img/img4.png', 'user/img/img4.png', 'user/img/img4.png', 4, '4번제목', 'user/img/img4.png', 0, 400, now(), now(), 4, 0);

/* CROWD_CATEGORY */
INSERT INTO crowd_category VALUES
(null, "게임"),
(null, "제조"),
(null, "프로그램"),
(null, "운동");

/* CROWD_BOARD */
INSERT INTO crowd_board_1 VALUES
(null, 1, 0, "1번글제목", "1번글본문", "user/img/img1.png", now(), now(), 0, false),
(null, 1, 0, "2번글제목", "2번글본문", "user/img/img2.png", now(), now(), 0, true),
(null, 1, 0, "3번글제목", "3번글본문", "user/img/img3.png", now(), now(), 0, false);

/* CROWD_QNA */
INSERT INTO crowd_qna_1 VALUES
(null, 1, "1번글제목", "1번글본문", now(), now(), false),
(null, 1, "2번글제목", "2번글본문", now(), now(), true),
(null, 1, "3번글제목", "3번글본문", now(), now(), false);

/* CROWD_QNA_REPLY */
INSERT INTO crowd_qna_reply_1 VALUES
(null, 1, "1번댓글", now(), false),
(null, 1, "2번댓글", now(), true),
(null, 1, "3번댓글", now(), false),
(null, 2, "4번댓글", now(), true),
(null, 3, "5번댓글", now(), true);

/* CROWD_ORDER_DETAILS */
INSERT INTO crowd_order_details VALUES
   (null, 1, 0, '서울시 강남구', '23-08-01', '김자바', '01012345678', '카드결제', 1, 1, 1),
    (null, 2, 0, '경기도 안산시', '23-08-02', '김파이', '01045678900', '카드결제', 1, 2, 2),
    (null, 3, 0, '서울시 강북구', '23-08-01', '김코드', '01088889999', '계좌송금', 1, 3, 3);

/* REWARD */
INSERT INTO reward VALUES
   (null, 1, '슈퍼얼리버드1', '기본후원1', 1000, 5),
    (null, 1, '슈퍼얼리버드2', '기본후원2', 10000, 5),
    (null, 1, '슈퍼얼리버드3', '기본후원3', 50000, 5);

/* SPONSOR */
INSERT INTO sponsor VALUES
      (null, 1, 1),
        (null, 1, 2),
        (null, 1, 3);