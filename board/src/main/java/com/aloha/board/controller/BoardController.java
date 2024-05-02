package com.aloha.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.board.dto.Board;
import com.aloha.board.dto.Files;
import com.aloha.board.dto.Option;
import com.aloha.board.dto.Page;
import com.aloha.board.service.BoardService;
import com.aloha.board.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;




/**
 * /board 경로로 요청 왔을 때 처리
 * [GET] - /board/list      : 게시글 목록 화면
 * [GET] - /board/read      : 게시글 조회 화면
 * [GET] - /board/insert    : 게시글 등록 화면
 * [POST] - /board/insert   : 게시글 등록 처리
 * [GET] - /board/update    : 게시글 수정 화면
 * [POST] - /board/update   : 게시글 수정 처리
 * [POST] - /board/delete   : 게시글 삭제 처리
 */
@Slf4j                      // 로그 어노테이션
@Controller                 // 컨트롤러 스프링 빈으로 등록
@RequestMapping("/board")   // 클래스 레벨 요청 경로 매핑
                            // - /board/~ 경로의 요청은 이 컨트롤러에서 처리
public class BoardController {
    
    // * 데이터 요청과 화면 출력
    // Controller -> Service (데이터 요청)
    // Controller <- Service (데이터 전달)
    // Controller -> Model   (모델 등록)
    // View <- Model         (데이터 출력)
    @Autowired                         // 의존성 자동 주입
    private BoardService boardService; // @Service를 --Impl에 등록

    @Autowired
    private FileService fileService;

    /**
     * 게시글 목록 조회 화면
     * @return
     * @throws Exception 
     */
    @GetMapping("/list")
    public String list(Model model, Page page, 
                    // @RequestParam(value = "keyword", defaultValue = "") String keyword) throws Exception {
                        Option option
                        )  throws Exception {
        // 데이터 요청
        // List<Board> boardList = boardService.list(page);         // 페이징
        // List<Board> boardList = boardService.search(keyword);    // 검색
        // List<Board> boardList = boardService.search(option);     // 검색
        List<Board> boardList = boardService.list(page,option);     // 페이징 + 검색

        // 페이징
        log.info("page : " + page);
        // 검색
        // log.info("keyword : " + keyword);
        log.info("option : " + option);

        // 모델 등록
        model.addAttribute("boardList", boardList);
        model.addAttribute("page", page);
        model.addAttribute("option", option);

        // 동적으로 옵션값을 가져오는 경우
        List<Option> optionList = new ArrayList<Option>();
        optionList.add(new Option("전체", 0));
        optionList.add(new Option("제목", 1));
        optionList.add(new Option("내용", 2));
        optionList.add(new Option("제목 + 내용", 3));
        optionList.add(new Option("작성자", 4));
        model.addAttribute("optionList", optionList);

        // 뷰 페이지 지정
        return "/board/List";       // resources/templates/board/list.html
    }
    
    /**
     * 게시글 조회 화면
     * - /board/read?no=(파라미터 값)
     * @param no
     * @return
     * @throws Exception 
     */
    // @RequestParam("파라미터 명")
    // 스프링부트 v3.2 이하, 생략해도 자동 매핑
    // 스프링부트 v3.2 이상, 필수로 명시해야 매핑
    @GetMapping("/read")
    public String read(@RequestParam("no") int no, 
                                        Model model, 
                                        Files file) throws Exception {
        // 데이터 요청
        Board board = boardService.select(no);

        // 조회수 증가
        int views = boardService.view(no);

        // 파일 목록 요청
        file.setParentTable("board");
        file.setParentNo(no);
        List<Files> fileList = fileService.listByParent(file);

        // 모델 등록
        model.addAttribute("board", board);
        model.addAttribute("fileList", fileList);
        
        // 뷰페이지 지정
        return "/board/read";
    }

    /**
     * 게시글 등록 화면
     * @return
     */
    @GetMapping("/insert")
    public String insert() {
        return "/board/insert";
    }

    /**
     * 게시글 등록 처리
     * @param board
     * @return
     * @throws Exception 
     */
    @PostMapping("/insert")
    public String insertPro(Board board) throws Exception {

        log.info(board.toString());

        // 데이터 요청
        int result = boardService.insert(board);

        // 리다이렉트
        // O 데이터 처리 성공
        if(result > 0) {
            return "redirect:/board/list";
        }
        // X 데이터 처리 실패
        return "redirect:/board/insert?error";
    }
    
    /**
     * 게시글 수정 화면
     * @param no
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/update")
    public String update(@RequestParam("no") int no, 
                                            Model model, 
                                            Files file) throws Exception {
       
         // 데이터 요청
         Board board = boardService.select(no);

         // 파일 목록 요청
         file.setParentTable("board");
         file.setParentNo(no);
         List<Files> fileList = fileService.listByParent(file);
 
         // 모델 등록
         model.addAttribute("board", board);
         model.addAttribute("fileList", fileList);

        return "/board/update";
    }

    /**
     * 게시글 수정 처리
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public String updatePro(Board board) throws Exception {
        int result = boardService.update(board);

        if(result > 0) {
            return "redirect:/board/list";
        }
        int no = board.getNo();
        return "redirect:/board/update?no=" + no + "&error";
    }
    
    /**
     * 게시글 삭제 처리
     * @param no
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("no") int no) throws Exception {
        int result = boardService.delete(no);
        if (result > 0) {

            // 첨부 파일 삭제
            Files file = new Files();
            file.setParentTable("board");
            file.setParentNo(no);
            fileService.deleteByParent(file);
            
            return "redirect:/board/list";
        }
        return "redirect:/board/update?no=" + no + "&error";
    }
}
