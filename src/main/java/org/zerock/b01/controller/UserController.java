package org.zerock.b01.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.UserDTO;
import org.zerock.b01.service.UserService;

@Controller
@RequestMapping("/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public void signUpGET() {
        // html 호출을 위해 기본 get 사용
    }

    @PostMapping("/signup")
    public String signUpPOST(@Valid UserDTO userDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/user/signup";
        }
        // BindingResult (인터페이스) : 폼 데이터를 바인딩할 때 발생하는 오류를 저장하는 객체
        // - @Valid, @Validated 를 사용하여 폼 데이터 유효성 검사
        // getAllError() 메서드 : 폼 데이터 바인딩 및 유효성 검사 과정에서 발생한 오류를 List<ObjectError> 형태로 반환
        // - ObjectError (클래스) : 바인딩 오류나 유효성 검사 오류를 나타내는 객체 (오류 객체 이름, 메시지 등 정보 포함)

        // Binding : Form Data를 객체에 Mapping하는 과정
        // Form Data : HTML Form에 사용자가 입력한 값
        // Mapping : 한 형태의 데이터를 다른 형태의 데이터로 변환하는 과정.
        // - Parsing은 데이터 해석 및 분석/ Mapping은 데이터 변환 및 할당

        try {
            userService.signUp(userDTO);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/signup";
        }
        // IllegalArgumentException : 잘못된 argument가 메서드에 전달될 때 발생하는 예외 (UserController -> UserDTO)
        // RedirectAttributes (인터페이스) : 주로 리디렉션 후 일회성으로 사용할 데이터를 저장할 때 사용
        // - 주요 메서드 : addAttribute(String name, Object value), addFlashAttribute(String name, Object value)
        // addFlashAttribute : 리디렉션 후 일회성으로 사용할 속성 추가/ HTTP 세션에 저장되며, 리디렌션된 페이지에서 한 번만 사용 가능
        // addAttribute : 리디렉션 URL에 쿼리 매개변수로 추가할 속성 설정
        // redirection : 웹 서버가 클라이언트에게 다른 URL로 이동하라고 지시하는 것

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/user/signin";
    }

    @GetMapping("/signin")
    public void logInGET() {
    }

    @PostMapping("/signin")
    public String logInPOST(@Valid UserDTO userDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/user/signin";
        }

        if (userService.signIn(userDTO)) {
            session.setAttribute("username", userDTO.getId());
            return "redirect:/board/list";// 로그인 성공 시 홈 페이지로 이동
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid ID or Password");
            return "redirect:/user/signin"; // 로그인 실패 시 다시 로그인 페이지로 리디렉션
        }

        /*
        아래 코드는 Optional<User> 로 대체되었다!
        try {
            if (userService.signIn(userDTO)) {
                return "redirect:/board/list"; // 로그인 성공 시 홈 페이지로 이동
            }
        } catch (NullPointerException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/signin"; // 로그인 실패 시 다시 로그인 페이지로 리디렉션
        }
        return "redirect:/user/signin";
         */
    }

}
