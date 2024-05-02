package com.aloha.springswagger.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Board {

    @Schema(description = "글 번호 입니다. (GET, PUT, DELETE 요청 시, 필요)")
    private int no;      
    @Schema(description = "제목 입니다. (POST, PUT 요청 시, 필요)")
    private String title;
    @Schema(description = "작성자 입니다. (POST, PUT 요청 시, 필요)")
    private String writer;
    @Schema(description = "내용 입니다. (POST, PUT 요청 시, 필요)")
    private String content;
    @Schema(description = "등록 일자 입니다. (DEFAULT : NOW() SYSDATE 현재 시각으로 등록")
    private Date regDate;
    @Schema(description = "수정 일자 입니다. (DEFAULT : NOW() SYSDATE 현재 시각으로 등록")
    private Date updDate;
    @Schema(description = "조회수 입니다. (DEFAULT : 0 , 사용자가 조회 시, 1씩 증가합니다)")
    private int views;

    public Board(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
}
