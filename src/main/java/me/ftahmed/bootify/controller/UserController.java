package me.ftahmed.bootify.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import me.ftahmed.bootify.domain.Role;
import me.ftahmed.bootify.domain.Vendor;
import me.ftahmed.bootify.model.UserDto;
import me.ftahmed.bootify.repos.RoleRepository;
import me.ftahmed.bootify.repos.VendorRepository;
import me.ftahmed.bootify.service.UserService;
import me.ftahmed.bootify.util.WebUtils;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private VendorRepository vendorRepository;

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("rolesValues", roleRepository.findAll(Sort.by("id"))
                .stream()
                .collect(Collectors.toMap(Role::getId, Role::getRoleName)));
        model.addAttribute("vendorValues", vendorRepository.findAll(Sort.by("vendorName"))
                .stream()
                .collect(Collectors.toMap(Vendor::getVendorCode, Vendor::getVendorName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") final UserDto userDto) {
        return "user/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid final UserDto userDto,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("username") && userService.usernameExists(userDto.getUsername())) {
            bindingResult.rejectValue("username", "Exists.user.username");
        }
        if (!bindingResult.hasFieldErrors("email") && userService.emailExists(userDto.getEmail())) {
            bindingResult.rejectValue("email", "Exists.user.email");
        }
        if (bindingResult.hasErrors()) {
            return "user/add";
        }
        userService.create(userDto);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.create.success"));
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("user", userService.get(id));
        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("user") @Valid final UserDto userDto, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("email") &&
                !userService.get(id).getEmail().equalsIgnoreCase(userDto.getEmail()) &&
                userService.emailExists(userDto.getEmail())) {
            bindingResult.rejectValue("email", "Exists.user.email");
        }
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(id, userDto);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.update.success"));
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.delete.success"));
        return "redirect:/users";
    }

}
