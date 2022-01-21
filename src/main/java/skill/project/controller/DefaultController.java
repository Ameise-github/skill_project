package skill.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*Для запросов не через API*/
@Controller
@RequestMapping("/")
public class DefaultController {

  @GetMapping
  public String start(){
    return "index";
  }

  @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = "/**/{path:[^\\.]*}")
  public String redirectToIndex() {
    return "index";
  }
}
