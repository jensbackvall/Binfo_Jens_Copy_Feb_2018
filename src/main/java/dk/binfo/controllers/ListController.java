package dk.binfo.controllers;

import dk.binfo.models.User;
import dk.binfo.services.ListService;
import dk.binfo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 *  @author Patrick Klæbel
 *  @author Jens Bäckvall
 *  @author Steen Petersen
 *  @author Morten Olsen
 *
 */
@Controller
public class ListController {

    @Autowired
    private UserService userService;

    @Autowired
    private ListService listService;


    @RequestMapping(value={"/lists/connect"})
    public ModelAndView showConnectList() {
        ModelAndView modelAndView = new ModelAndView("/lists/connect", "list", listService
                .generateList(Integer.MAX_VALUE, 1));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        return modelAndView;
    }


    @RequestMapping(value={"/lists/internal"})
    public ModelAndView showInternList() {
        ModelAndView modelAndView = new ModelAndView("/lists/internal", "list", listService
                .generateList(20, 2));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        return modelAndView;
    }

    @RequestMapping(value={"/lists/family"})
    public ModelAndView showFamilyList() {
        ModelAndView modelAndView = new ModelAndView("/lists/family", "list", listService
                .generateList(Integer.MAX_VALUE, 3));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som admin");
        return modelAndView;
    }

    @RequestMapping(value={"/lists/external"})
    public ModelAndView showExternalList() {
        ModelAndView modelAndView = new ModelAndView("/lists/external", "list", listService.generateList(Integer.MAX_VALUE, 4));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som admin");
        return modelAndView;
    }

    @RequestMapping(value={"/lists/listapartmentconnect/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentListConnect(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("/lists/listapartment");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject( "listconnect", listService.generateSingleApartmentList(Integer.MAX_VALUE, id, 1));
        modelAndView.addObject("adminMessage","Du er logget ind som admin");
        modelAndView.addObject("HeaderMessage","Viser Sammenlægningsliste for Lejlighed nr. " + id);
        modelAndView.setViewName("/lists/listapartmentconnect");
        return modelAndView;
    }

    @RequestMapping(value={"/lists/listapartmentinternal/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentListInternal(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("/lists/listapartment");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject( "listinternal", listService.generateSingleApartmentList(Integer.MAX_VALUE, id, 2));
        modelAndView.addObject("adminMessage","Du er logget ind som admin");
        modelAndView.addObject("HeaderMessage","Viser Intern Liste for Lejlighed nr. " + id);
        modelAndView.setViewName("/lists/listapartmentinternal");
        return modelAndView;
    }

    @RequestMapping(value={"/lists/listapartmentfamily/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentListFamily(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("/lists/listapartment");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject( "listfamily", listService.generateSingleApartmentList(Integer.MAX_VALUE, id, 3));
        modelAndView.addObject("adminMessage","Du er logget ind som admin");
        modelAndView.addObject("HeaderMessage","Viser Familieliste for Lejlighed nr. " + id);
        modelAndView.setViewName("/lists/listapartmentfamily");
        return modelAndView;
    }

    @RequestMapping(value={"/lists/listapartmentexternal/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentListExternal(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("/lists/listapartment");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject( "listexternal", listService.generateSingleApartmentList(Integer.MAX_VALUE, id, 4));
        modelAndView.addObject("adminMessage","Du er logget ind som admin");
        modelAndView.addObject("HeaderMessage","Viser Ekstern Liste for Lejlighed nr. " + id);
        modelAndView.setViewName("/lists/listapartmentexternal");
        return modelAndView;
    }

}
