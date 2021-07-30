package skill.project.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*Для запросов не через API*/
@RestController
@RequestMapping("")
public class DefaultController {

  @GetMapping
  public String start(){
    return "index";
  }
}
