package com.exam.sbb.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@RequestMapping(value = "/question")
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/list")
    //@ResponseBody
    public String list(Model model){
        List<Question> questionList =  questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        Question question = questionService.getQuestion(id);

        model.addAttribute("question", question);
        return "question_detail";
    }
}
