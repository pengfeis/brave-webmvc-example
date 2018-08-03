package brave.webmvc.hessian.impl;

import brave.webmvc.hessian.SpeakerService;
import org.springframework.stereotype.Service;

/**
 * @author pengfeisu
 */
@Service("speakerService")
public class HessianSpeakerServiceImpl implements SpeakerService {
    @Override
    public String say(String name) {
        return String.valueOf(name) + " greeting! ";
    }
}
