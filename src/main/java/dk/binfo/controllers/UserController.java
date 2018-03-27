package dk.binfo.controllers;


import dk.binfo.models.User;
import dk.binfo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
/**
 *  @author Jens Bäckvall
 *  @author Morten Olsen
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/settings/{email:.+}"}, method = RequestMethod.GET)
    public ModelAndView settings(@PathVariable String email){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("userForm",user);
        modelAndView.setViewName("/settings");
        return modelAndView;
    }

    @RequestMapping(value="/settings/{email:.+}", method=RequestMethod.POST)
    public ModelAndView editUserSettings(@ModelAttribute @Valid User userinfo, BindingResult bindingResult,
                                      @PathVariable String email){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);

        if (bindingResult.hasErrors())
        {
            modelAndView.setViewName("/settings/{email}");
        }
        modelAndView.setViewName("redirect:/settings/{email}");
        userService.updateUserSettings(userinfo);
        return modelAndView;
    }
}
