package dk.binfo.controllers;

import dk.binfo.models.User;
import dk.binfo.repositories.ApartmentRepository;
import dk.binfo.services.ApartmentService;
import dk.binfo.services.PreferenceService;
import dk.binfo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class UserPreferencesController {

    /**
     *  @author Patrick Klæbel
     *  @author Steen Petersen
     *  @author Morten Olsen
     *  @author jensbackvall
     */

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private PreferenceService preferenceService;

    // Den viser nu lejligheder. De skal sorteres efter kvm størrelse og evt bundles i størrelser.
    // Brugeren skal kunne vælge en enkelt, eller flere lejlighder hver for sig, som gemmes til deres preferences.
    // Der skal evt være gruppeboxe som man kan udfylde hvis man gerne vil vælge alle lejligheder i størrelse mellem
    // x m2 og y m2, for at gøre det lettere at vælge.



    // Maybe there is no need to use two different list for apartment ids. I can use the same list for adging ids and
    // for fetching all ids and any new ones there might be.
    @RequestMapping(name = "/preferences")
    public ModelAndView showUserPreferencesPage() {
        ModelAndView modelAndView = new ModelAndView("/preferences", "apartment", repository.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        ArrayList<Integer> validApartmentIds = preferenceService.getPreferences(user.getUserId());
        System.out.println(validApartmentIds);
        modelAndView.addObject("apartmentIds", validApartmentIds);
        modelAndView.addObject("user", user);
        modelAndView.addObject("userMessage","Du er logget ind med email: " + user.getEmail());
        return modelAndView;
    }

    @RequestMapping(name = "/preferencessaved", method= RequestMethod.POST)
    public ModelAndView showUserPreferences(@RequestParam("apartmentIds") ArrayList<Integer> validApartmentIds)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("/preferencessaved", "apartment", repository.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("userMessage","Du er logget ind med email: " + user.getEmail());
        modelAndView.setViewName("redirect: /preferences");
        System.out.println(validApartmentIds);
        return modelAndView;
    }

}
