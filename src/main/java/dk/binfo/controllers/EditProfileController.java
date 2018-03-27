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
public class EditProfileController {

    @Autowired
    private UserService userService;

    // Den gemmer p.t. ikke en redigeret bruger
    // Der skal udvides med alle de felter som er relevante for en bruger.


    @RequestMapping(value="/editprofile/{email:.+}", method=RequestMethod.GET)
    public ModelAndView editProfile(@PathVariable String email) {
        ModelAndView modelAndView = new ModelAndView("/editprofile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        User editedUser = userService.findUserByEmail(email);
        if (user.isActive()) {
            modelAndView.addObject("activeMessage", "Administrator har markeret dig som AKTIV på ventelisten.\n " +
                    "Kontakt ejendommens bestyrelse hvis du vil ændres til IKKE AKTIV.");
        } else {
            modelAndView.addObject("activeMessage", "Administrator har markeret dig som IKKE AKTIV på ventelisten" +
                    ".\n " +
                    "Kontakt ejendommens bestyrelse hvis du vil ændres til AKTIV.");
        }
        modelAndView.addObject("user", user);
        modelAndView.addObject("editeduser", editedUser);
        return modelAndView;
    }

    @RequestMapping(value="/editprofile/{email:.+}", method=RequestMethod.POST)
    public ModelAndView editProfile(@ModelAttribute @Valid User editedUser, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        if (bindingResult.hasErrors())
        {
            modelAndView.setViewName("/editprofile/{email}");
        }
        modelAndView.setViewName("redirect:/editprofile/{email}");
        userService.update(editedUser);
        return modelAndView;
    }
}
