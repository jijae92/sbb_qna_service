package com.exam.sbb.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    private int increaseNo = 0;

    @RequestMapping("/sbb")
    @ResponseBody
    public String index(){
        return "안녕하세요.adddd";
    }
    @GetMapping("/page1")
    @ResponseBody
    public String showGet() {
        return """
                <form method="POST" action="/page2"/>
                    <input type="number" name=age placeholder="나이 입력"/>
                    <input type="submit" value="page2로 POST 방식으로 이동"/>
                </form>
                """;
    }
    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d </h1>
                <h1>안녕하세요.  POST방식으로 오신걸 환영합니다.</h1>
                
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d </h1>
                <h1>안녕하세요.  GET방식으로 오신걸 환영합니다.</h1>
                
                """.formatted(age);
    }

    @GetMapping("/plus")
    @ResponseBody
    public int showPlus(int a, int b) {
        return a + b;
    }
    @GetMapping("/plus2")
    @ResponseBody
    public void showPlus2(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));
        resp.getWriter().append(a + b +"" );
    }
//15강 들을 차례
    @GetMapping("/minus")
    @ResponseBody
    public int showMinus(int a, int b) {
        return a - b;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease() {
        increaseNo++;
        return increaseNo;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showGugudan(int dan, int limit) {
        String rs = "";

        for(int i=1; i<= limit; i++){
            rs += "%d * %d = %d<br>".formatted(dan, i , dan*i);
        }
        return rs;
    }

    @GetMapping("/gugudan1")
    @ResponseBody
    public String showGugudan(Integer dan, Integer limit) {
        if(dan == null){
            dan = 9;
        }
        if(limit == null){
            limit=9;
        }

        final Integer finalDan = dan;

        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showMbti(@PathVariable String name) {
        return switch (name){
            case "홍길동" -> {
                char j = 'j';
                yield "INF" + j;
            }
            case "임꺽정" -> "ESFJ";
            case "장익재","홍길순" -> "ESFP";
            default -> "모름";

        };
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name, HttpSession session) {
        String value = (String) session.getAttribute(name);

        return "세션변수 %s의 값은 %s 입니다.".formatted(name, value);
    }

    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(String title, String body) {
        int id = 1;
        Article article = new Article(id, title, body);
        return "%d번 게시물이 생성되었습니다.".formatted(id);
    }
    @AllArgsConstructor
    class Article {
        private int id;
        private String title;
        private String body;
    }
}
