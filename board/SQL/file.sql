-- Active: 1714115451817@@127.0.0.1@3306@joeun
CREATE TABLE `file` (
  `no` int NOT NULL AUTO_INCREMENT,
  `parent_table` varchar(45) NOT NULL,
  `parent_no` int NOT NULL,
  `file_name` text NOT NULL,
  `origin_name` text,
  `file_path` text NOT NULL,
  `file_size` int NOT NULL DEFAULT '0',
  `reg_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `file_code` int NOT NULL DEFAULT '0',         # 파일 종류 코드
  PRIMARY KEY (`no`)
) COMMENT='파일';

select * from file;

-- 테이블 데이터 삭제
truncate board;
truncate file;

-- board, file 테이블 조인 조회
SELECT b.*
      ,f.no file_no
      ,f.file_name
      ,f.file_path
      ,f.file_code
FROM board b LEFT JOIN ( 
                         SELECT *
                         FROM file
                         WHERE parent_table = "board"
                           AND file_code = 1 ) f
                            ON (b.no = f.parent_no);
-- WHERE f.parent_table = "board"
--   AND f.file_code = 1;              # file_code = 1(대표 썸네일)

-- 게시글 목록 페이징
SELECT *
FROM board
LIMIT 30, 10;

SELECT *
FROM board
LIMIT #{index}, #{rows};

-- 샘플 데이터 등록
INSERT INTO board(title, writer, content)
VALUES ('제목01', '작성자01', '내용01'),
       ('제목02', '작성자02', '내용02'),
       ('제목03', '작성자03', '내용03'),
       ('제목04', '작성자04', '내용04'),
       ('제목05', '작성자05', '내용05');


INSERT INTO board (title, writer, content)
SELECT title, writer, content
FROM board;

SELECT * FROM board;

-- 게시글 목록 검색
SELECT *
FROM board
WHERE title LIKE '%검색어%'
   OR writer LIKE '%검색어%'
   OR content LIKE '%검색어%';

SELECT *
FROM board
WHERE title LIKE '%제목%'
LIMIT 0, 10;


-- 댓글
-- * 게시글(board)에 종속된 테이블
CREATE TABLE reply (
  no            INT NOT NULL AUTO_INCREMENT PRIMARY KEY,      -- 댓글 번호
  board_no      INT NOT NULL,                                 -- 글 번호
  parent_no     INT NOT NULL,                                 -- 부모 번호
  writer        VARCHAR(100) NOT NULL,                        -- 작성자
  content       TEXT NOT NULL,                                -- 내용
  reg_date      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일자
  upd_date      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 수정 일자
  -- ⭐외래키 지정
  FOREIGN KEY (board_no) REFERENCES board(no)
                          ON DELETE CASCADE   -- CASCADE, RESTRICT, SET NULL
                          ON UPDATE CASCADE
);

-- 댓글 샘플 데이터
-- 글 번호 : 273
INSERT INTO reply (board_no, parent_no, writer, content)
VALUES (273, 0, '김조은', '댓글 내용1')
      ,(273, 0, '김조은', '댓글 내용2')
      ,(273, 0, '김조은', '댓글 내용3')
      ,(273, 0, '김조은', '댓글 내용4')
      ,(273, 0, '김조은', '댓글 내용5');


SELECT *
FROM reply
WHERE board_no = 273;

DROP TABLE reply;
-- 데이터 초기화 후, 테이블 생성 시 외래키 지정
TRUNCATE board;
TRUNCATE reply;

-- 댓글 (reply) 테이블에 외래키 추가
ALTER TABLE reply
ADD CONSTRAINT FK_REPLY_BOARD_NO
FOREIGN KEY (board_no)
REFERENCES board(no)
ON DELETE CASCADE;    -- 게시글 삭제 시, 종속된 댓글 삭제

-- 글 번호 : 1
SELECT * FROM board WHERE no = 2;

-- 1번 글의 댓글
SELECT * FROM reply WHERE board_no = 2;

