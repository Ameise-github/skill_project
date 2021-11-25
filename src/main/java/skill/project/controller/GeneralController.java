package skill.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import skill.project.dto.request.ModeratorRequest;
import skill.project.dto.request.ProfileRequest;
import skill.project.dto.response.CalendarResponse;
import skill.project.dto.response.InitResponse;
import skill.project.security.CustomUser;
import skill.project.security.UserPrincipal;
import skill.project.service.GeneralService;
import skill.project.service.ProfileService;
import skill.project.service.UploadService;

import java.time.LocalDate;

/*Для запросов которые некуда пристроить :))) */
@RestController
@RequestMapping("/api")
public class GeneralController {
  @Autowired
  private GeneralService generalService;
  @Autowired
  private InitResponse initResponse;
  @Autowired
  private UploadService uploadService;
  @Autowired
  private ProfileService profileService;

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
    //Посты - только  за переданный год. Нет года - выводим за текущий год. Года отдаются все.
    CalendarResponse calendarPosts = generalService.getCalendarPosts(year);
    return new ResponseEntity<>(calendarPosts, HttpStatus.OK);
  }

  @PostMapping("/image")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<?> saveImage(@RequestBody MultipartFile image) {
    return ResponseEntity.ok(uploadService.uploadImage(image));
  }

  @PostMapping("/moderation")
  @PreAuthorize("hasAuthority('user:moderarot')")
  public ResponseEntity<?> moderatorPost(@UserPrincipal CustomUser principal, @RequestBody ModeratorRequest moderatorPost) {
    return new ResponseEntity<>(generalService.moderationPost(principal, moderatorPost), HttpStatus.OK);
  }

  @PostMapping("/profile/my")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<?> editedProfile(@UserPrincipal CustomUser principal, @RequestBody ProfileRequest profile) {
    return ResponseEntity.ok(profileService.editedProfile(principal, profile));
  }

  @GetMapping("/statistics/my")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<?> getMyStatistics(@UserPrincipal CustomUser principal) {
    return new ResponseEntity<>(generalService.myStatistics(principal.getId()), HttpStatus.OK);
  }

  @GetMapping("/statistics/all")
  public ResponseEntity<?> getAllStatistics(@UserPrincipal CustomUser principal) {
    return new ResponseEntity<>(generalService.allStatistics(principal), HttpStatus.OK);
  }
}
