package skill.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skill.project.enums.ModeType;

@RestController
@RequestMapping("/post")
public class GeneralController {

  @GetMapping
  public ResponseEntity<?> getPost(@RequestParam ModeType mode,
                                   @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
                                   @RequestParam(name = "offset", defaultValue = "10", required = false) Integer limit) {


    return ResponseEntity.ok("");
  }
}
