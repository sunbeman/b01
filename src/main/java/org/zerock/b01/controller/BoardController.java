package org.zerock.b01.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.*;
import org.zerock.b01.service.BoardService;
import org.zerock.b01.service.UserService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /*
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }
    */

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, UserDTO userDTO, Model model) {
        //PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        PageResponseDTO<BoardListReplyCountDTO> responseDTO=
                boardService.listWithReplyCount(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("username", userDTO.getId());
        // 로그인 후 리스트 화면에서 "'id'님 환영합니다." 상단 텍스트메시지 (아직 구현안함)
    }

    @GetMapping("/register")
    public String registerGET(HttpSession session, Model model) {//html 호출을 위해 기본 get 사용
        String writer = (String) session.getAttribute("username");
        model.addAttribute("writer", writer);
        return "board/register";
    }

    @PostMapping("/register")//@Valid BoardDTO 데이터 바인딩
    public String registerPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes
    , HttpSession session) {
        // BindingResult는 오류가 나면 어떤 오류 인지 담기
        // RedirectAttributes 리다이랙트에 값을 담아서 보네기
        if (bindingResult.hasErrors()) {
            //에러가 있으면
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            //에러가 나면 erreors와 함께 에러 메시지 받기
            return "redirect:/board/register";
        }

        // 로그인 후 작성시 writer = username(id)
        String writer = (String) session.getAttribute("username");
        boardDTO.setWriter(writer);


        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);

        // 연결 성공시 정보전달
        return "redirect:/board/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long bno,
                     PageRequestDTO pageRequestDTO,
                     Model model){
        BoardDTO boardDTO = boardService.readOne(bno);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){

            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/list";
    }

}
























