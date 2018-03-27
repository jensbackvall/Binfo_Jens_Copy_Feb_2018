package dk.binfo.controllers;

import dk.binfo.models.Apartment;
import dk.binfo.repositories.ApartmentRepository;
import dk.binfo.services.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import dk.binfo.models.User;
import dk.binfo.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;

@Controller
public class ApartmentController {

    /**
     *  @author Patrick Klæbel
     *  @author Steen Petersen
     *  @author Morten Olsen
     */

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping("/apartment")
    public ModelAndView showApartment() {
        ModelAndView modelAndView = new ModelAndView("/apartment", "apartment", repository.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.setViewName("/apartment");

        return modelAndView;
    }

    @RequestMapping(value = "/apartment/add", method = RequestMethod.POST)
    public ModelAndView createNewApartment(@Valid Apartment apartment, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/apartment/add");
        } else {
            apartmentService.saveApartment(apartment);
            modelAndView.addObject("successMessage", "Fantastisk arbejde! Du har nu tilføjet en ny lejlighed. Du ROCKER!!!");
            modelAndView.addObject("user", user);
            modelAndView.addObject("apartment", new Apartment());
            modelAndView.setViewName("/apartment/add");

        }
        return modelAndView;
    }

    @RequestMapping("/apartment/add")
    public ModelAndView add(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("adminMessage","Fedt man spa du er admin");
        modelAndView.addObject("apartment", new Apartment());
        modelAndView.setViewName("/apartment/add");
        return modelAndView;
    }

    @RequestMapping(value="/apartment/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editApartmentPage(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("/apartment/add-edit");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        Apartment apartment = apartmentService.findById(id);
        modelAndView.addObject("apartment", apartment);
        return modelAndView;
    }

    @RequestMapping(value="/apartment/edit/{id}", method=RequestMethod.POST)
    public ModelAndView editApartment(@ModelAttribute @Valid Apartment apartment, BindingResult bindingResult, @PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        if (bindingResult.hasErrors())
        {
            modelAndView.setViewName("/apartment/edit");
        }
        modelAndView.setViewName("redirect:/apartment");
        apartmentService.update(apartment);
        return modelAndView;
    }

    @RequestMapping(value="/apartment/delete/{id}", method=RequestMethod.GET)
    public ModelAndView deleteApartment(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/apartment");
        apartmentService.delete(id);
        return modelAndView;
    }

}