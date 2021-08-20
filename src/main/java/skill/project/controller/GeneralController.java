package skill.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skill.project.dto.response.CalendarResponse;
import skill.project.dto.response.InitResponse;
import skill.project.service.GeneralService;

import java.time.LocalDate;

/*Для запросов которые некуда пристроить :))) */
@RestController
@RequestMapping("/api")
public class GeneralController {
  @Autowired
  private GeneralService generalService;
  @Autowired
  private InitResponse initResponse;

  @GetMapping("/init")
  public ResponseEntity<?> getInit() {
    return ResponseEntity.ok().body(initResponse);
  }

  @GetMapping("/settings")
  public ResponseEntity<?> getSettings() {
    return ResponseEntity.ok().body(generalService.settingsMap());
  }

  @GetMapping("/tag")
  public ResponseEntity<?> getTags(@RequestParam(name = "query", required = false) String query) {
    return new ResponseEntity<>(generalService.getTags(query), HttpStatus.OK);
  }

  @GetMapping("/calendar")
  public ResponseEntity<?> calendar(@RequestParam(name = "year", required = false) String year ) {
    //TODO Посты - только  за переданный год. Нет года - выводим за текущий год. Года отдаются все.
    CalendarResponse calendarPosts = generalService.getCalendarPosts(year);
    return new ResponseEntity<>(calendarPosts, HttpStatus.OK);
  }
}
