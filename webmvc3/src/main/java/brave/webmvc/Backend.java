package brave.webmvc;

import java.util.Date;

import brave.webmvc.hessian.SpeakerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class Backend {

  @Resource private SpeakerService clientSayService;

  @RequestMapping("/api")
  public ResponseEntity<String> printDate(
      @RequestHeader(value = "user-name", required = false) String username
  ) {
    String result;
    if (username != null) {
      result = new Date().toString() + " " + username;
    } else {
      result = new Date().toString();
    }

    result += clientSayService.say("pengfeis");

    return new ResponseEntity<String>(result, HttpStatus.OK);
  }
}
