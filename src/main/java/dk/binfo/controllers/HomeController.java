package dk.binfo.controllers;

import dk.binfo.models.User;
import dk.binfo.services.SeniorityService;
import dk.binfo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 *  @author Morten Olsen
 *  @author jensbackvall
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private SeniorityService seniorityService;

    @RequestMapping(value= "/home", method = RequestMethod.GET)
    public ModelAndView userHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        int id = user.getUserId();
       /* Date connectSeniority = seniorityService.getSeniority(id, 1);
        Date internalSeniority = seniorityService.getSeniority(id, 2);
        Date familySeniority = seniorityService.getSeniority(id, 3);
        Date externalSeniority = seniorityService.getSeniority(id, 4);*/
        int connectSeniority = seniorityService.getSeniority(id, 1);
        int internalSeniority = seniorityService.getSeniority(id, 2);
        int familySeniority = seniorityService.getSeniority(id, 3);
        int externalSeniority = seniorityService.getSeniority(id, 4);
        modelAndView.addObject("user", user);
        modelAndView.addObject("connectSeniority", connectSeniority);
        modelAndView.addObject("internalSeniority", internalSeniority);
        modelAndView.addObject("familySeniority", familySeniority);
        modelAndView.addObject("externalSeniority", externalSeniority);
        modelAndView.addObject("userMessage","Du er logget ind med email: " + user.getEmail());
        modelAndView.setViewName("/home");
        return modelAndView;
    }
}