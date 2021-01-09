package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController
{
    @Autowired
    UserService userService;

    @GetMapping
    public String getPage()
    {
        return "signup";
    }

    @PostMapping
    public String postPage(User user, Model model, RedirectAttributes redirectAttributes)
    {
        String signupError = null;
        //Check if username available
        if(!userService.isUsernameAvailable(user.getUsername()))
        {
            signupError = "The username already exists.";
        }

        if(signupError == null)
        {
            int rowsAdded = userService.createUser(user);
            if(rowsAdded < 0)
            {
                signupError = "There was and error signing you up. Please try again.";
            }
        }
        if(signupError == null)
        {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/login";
        }
        else
        {
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }


}
