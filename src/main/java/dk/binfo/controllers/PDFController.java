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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 *  @author Jens Bäckvall
 *  @author Steen Petersen
 */
@Controller
public class PDFController {

    @Autowired
    private UserService userService;

    @Autowired
    private ListService listService;

    @RequestMapping(value={"/lists/connect/pdf"})
    public ModelAndView generateConnectListPDF() {
        ModelAndView modelAndView = new ModelAndView("/lists/connect", "list", listService
                .generateList(Integer.MAX_VALUE, 1));
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Sammenlaegningsliste.pdf"; // filePath
        listService.generateCompleteListPDF(Integer.MAX_VALUE, 1, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/internal/pdf"})
    public ModelAndView generateInternListPDF() {
        ModelAndView modelAndView = new ModelAndView("/lists/internal", "list", listService
                .generateList(Integer.MAX_VALUE, 2));
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Intern_liste.pdf"; // filePath
        listService.generateCompleteListPDF(Integer.MAX_VALUE, 2, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/family/pdf"})
    public ModelAndView generateFamilyListPDF() {
        ModelAndView modelAndView = new ModelAndView("/lists/family", "list", listService
                .generateList(Integer.MAX_VALUE, 3));
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Familieliste.pdf"; // filePath
        listService.generateCompleteListPDF(Integer.MAX_VALUE, 3, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/external/pdf"})
    public ModelAndView generateExternalListPDF() {
        ModelAndView modelAndView = new ModelAndView("/lists/external", "list", listService.generateList(Integer.MAX_VALUE, 4));
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Ekstern_liste.pdf"; // filePath
        listService.generateCompleteListPDF(Integer.MAX_VALUE, 4, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/connect/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentListConnect(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("/lists/connect", "list", listService
                .generateSingleApartmentList(Integer.MAX_VALUE, id, 1));
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Liste_for_lejlighed_" + id + ".pdf"; // filePath
        listService.generateSingleApartmentPDF(Integer.MAX_VALUE, id, 1, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject("HeaderMessage","Viser Liste for Lejlighed nr. " + id);
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/internal/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentListInternal(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("/lists/internal", "list", listService
                .generateSingleApartmentList(Integer.MAX_VALUE, id, 2));
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Liste_for_lejlighed_" + id + ".pdf"; // filePath
        listService.generateSingleApartmentPDF(Integer.MAX_VALUE, id, 2, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject("HeaderMessage","Viser Liste for Lejlighed nr. " + id);
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/family/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentListFamily(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("/lists/family", "list", listService
                .generateSingleApartmentList(Integer.MAX_VALUE, id, 3));
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Liste_for_lejlighed_" + id + ".pdf"; // filePath
        listService.generateSingleApartmentPDF(Integer.MAX_VALUE, id, 3, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject("HeaderMessage","Viser Liste for Lejlighed nr. " + id);
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/external/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentListExternal(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("/lists/external", "list", listService
                .generateSingleApartmentList(Integer.MAX_VALUE, id, 4));
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Liste_for_lejlighed_" + id + ".pdf"; // filePath
        listService.generateSingleApartmentPDF(Integer.MAX_VALUE, id, 4, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject("HeaderMessage","Viser Liste for Lejlighed nr. " + id);
        modelAndView.addObject(user);
        return modelAndView;
    }
}
