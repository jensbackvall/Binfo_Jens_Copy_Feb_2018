package dk.binfo.controllers;

import dk.binfo.models.User;
import dk.binfo.repositories.UserRepository;
import dk.binfo.services.EmailService;
import dk.binfo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *  @author Steen Petersen
 *
 */
@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value={"/forgotpassword"}, method = RequestMethod.GET)
    public ModelAndView forgotPassword(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forgotpassword");
        return modelAndView;
    }

    @RequestMapping(value={"/forgotpassword/{password}/{email}/newpassword"}, method = RequestMethod.GET)
    public ModelAndView forgotPassword(@PathVariable("email") String email, @PathVariable("password") String password){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forgotpasswordlink");
        User user = userService.findUserByEmail(email);
        if (user == null) {
            modelAndView.setViewName("redirect:/login");
        }else{
            if (user.getPassword().replace("/","").equals(password)){

            }else{
                modelAndView.setViewName("redirect:/login");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value={"/forgotpassword/{password}/{email}/newpassword"}, method = RequestMethod.POST)
    public ModelAndView forgotPassword(@RequestParam String password1,@RequestParam String password2,@PathVariable
            ("email") String email, @PathVariable("password") String password){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forgotpasswordlink");
        User user = userService.findUserByEmail(email);
        if (user == null) {
            modelAndView.addObject("Message", "Linket virker ikke, prøv igen.");
        }else{
            if (user.getPassword().replace("/","").equals(password)){
                if (password1.equals(password2)){
                    modelAndView.addObject("Message", "Din kode er blevet ændret");
                    modelAndView.setViewName("redirect:/login");
                    user.setPassword(bCryptPasswordEncoder.encode(password1));
                    userRepository.save(user);
                }else{
                    modelAndView.addObject("Message", "Du har ik indtastet den samme kode");
                }
            }else{
                modelAndView.addObject("Message", "Linket virker ikke, prøv igen.");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    public ModelAndView forgotPassword(@RequestParam String email) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(email);
        if (user == null) {
            modelAndView.addObject("Message", "Emailen existere ikke, prøv igen.");
        }else{
            modelAndView.addObject("Message", "SUCCES!: Der er sent et link til din email!");
            sendResetEmail(user);
        }
        return modelAndView;
    }

    public void sendResetEmail(User user){
        String body = "<B>Reset dit password</B><br><br>";
        body += "Hvis du har bedt om at få dit password resat,<br> beder vi dig ";
        body += "beder vi dig klikke på nedenstående link.<br><br>";
        body += "<a href=\"http://localhost:8080/forgotpassword/"+user.getPassword().replace("/","")+"/"+user.getEmail()+"/newpassword\">Klik her for at reset dit password</a><br><br>";
        body += "Har du ikke bedt om et reset skal du bare ignore denne email.<br><br>";
        body += "Mvh<br>";
        body += "Jens Boligforening<br>";
        body += "nørrebrogade 123<br>";
        body += "2312 nørrebro";
        emailService.generateAndSendEmail(user.getEmail(),"BoligInfo password reset",body);
    }
}
