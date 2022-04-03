package com.example.Preproj223.controllers;

import com.example.Preproj223.dao.UserDAO;
import com.example.Preproj223.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public String index(Model model) {
        model.addAttribute("users", userDAO.getAllUsers());
        return "users/index";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String indexAdmin(Model model) {
        model.addAttribute("users", userDAO.getAllUsers());
        return "users/indexadmin";
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDAO.show(id));
        return "users/show";
    }

    @GetMapping("admin/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String newPerson(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "users/new";

        userDAO.save(user);
        return "redirect:/users";
    }

    @GetMapping("/admin/{id}/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDAO.show(id));
        return "users/edit";
    }

    @PostMapping("/admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "users/edit";

        userDAO.update(id, user);
        return "redirect:/users";
    }

    @PostMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") int id) {
        System.out.println(id);
        userDAO.delete(id);
        return "redirect:/users";
    }
}