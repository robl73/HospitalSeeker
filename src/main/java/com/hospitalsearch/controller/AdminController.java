package com.hospitalsearch.controller;

//import static com.hospitalsearch.config.security.SecurityConfiguration.REMEMBER_ME_TOKEN_EXPIRATION;
//import static com.hospitalsearch.entity.PasswordResetToken.RESET_PASSWORD_TOKEN_EXPIRATION;
//import static com.hospitalsearch.entity.VerificationToken.VERIFICATION_TOKEN_EXPIRATION;

import java.net.ConnectException;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hospitalsearch.dto.UserFilterDTO;
import com.hospitalsearch.dto.UserRegisterDTO;
import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.Token;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.AdminTokenConfigService;
import com.hospitalsearch.service.MailService;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;

/**
 * @author Andrew Jasinskiy on 10.05.16
 */
@Controller
public class AdminController {
	
	@Autowired
	private AdminTokenConfigService configService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    MailService mailService;

    @Autowired
    private MessageSource messageSource;

    private Integer usersPerPage = 10;

    private static String emailTemplate = "emailTemplate.vm";

    @ModelAttribute("roles")
    public List<Role> initializeRoles() {
        return roleService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/**/changeStatus/{userId}", method = RequestMethod.GET)
    public String  changeUserStatus(@PathVariable long userId) throws ConnectException {
        Locale locale = LocaleContextHolder.getLocale();
        userService.changeStatus(userId);
        sendBannedMessageToUserById(userId, locale);
        return "redirect:/";
    }

    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/**/viewUser/{id}", method = RequestMethod.POST)
    public User viewUser(@PathVariable("id") String id) {
        return userService.getById(Long.parseLong(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/delete/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable long userId, Locale locale,
                             @RequestParam(value = "status", required = false) String status) throws ConnectException {
        userService.changeStatus(userId);
        sendBannedMessageToUserById(userId, locale);
        return "redirect:/admin/users?status=" + status;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/**/admin/users/edit", method = RequestMethod.GET)
    public ModelAndView editUser(@RequestParam("id") Long userId,
                                 @RequestParam("status") String status) {
        ModelAndView modelAndView = new ModelAndView("admin/editUser");
        modelAndView.addObject("editUser", userService.getById(userId));
        modelAndView.addObject("status", status);
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/**/admin/users/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("editUser") User editUser, BindingResult result, ModelMap model,
                           @RequestParam("status") String status, Locale locale,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageError", "messageError");
            return "redirect:/admin/users?status=" + status;
        }
        try {
            User user = userService.getByEmail(editUser.getEmail());
            user.setUserRoles(editUser.getUserRoles());
            user.setEnabled(editUser.getEnabled());
            userService.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("messageError", messageSource.getMessage("admin.dashboard.message.error.edit", null, locale));
            return "redirect:/admin/users?status=" + status;
        }
        redirectAttributes.addFlashAttribute("messageSuccess", editUser.getEmail() + " " +
                messageSource.getMessage("admin.dashboard.message.success.edit", null, locale));
        return "redirect:/admin/users?status=" + status;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public String allUsers(ModelMap model, @RequestParam(value = "status", required = false) String status,
                           @ModelAttribute UserFilterDTO dto,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "sort", defaultValue = "email") String sort,
                           @RequestParam(value = "asc", defaultValue = "true") Boolean asc) {

        dto.setCurrentPage(page);
        dto.setAsc(asc);
        dto.setSort(sort);
        dto.setPageSize(usersPerPage);
        dto.setStatus(status);
        List users = userService.getUsers(dto);
        if (dto.getTotalPage() > 1) model.addAttribute("pagination", "pagination");
        model.addAttribute("userFilterDTO", dto);
        model.addAttribute("pageSize", dto.getPageSize());
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/search", method = RequestMethod.GET)
    public String searchUser(@RequestParam("status") String status,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @ModelAttribute("userFilterDTO") UserFilterDTO dto,
                             ModelMap model) throws Exception {
        dto.setPageSize(usersPerPage);
        List users = userService.searchUser(dto);
        if (dto.getTotalPage() > 1) model.addAttribute("pagination", "pagination");
        model.addAttribute("search", "search");
        model.addAttribute("userFilterDTO", dto);
        model.addAttribute("status", status);
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/configureToken", method = RequestMethod.GET)
    public String configureToken(ModelMap model) throws Exception {
/*    	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + configService.getAll());
    	AdminTokenConfig adminTokenConfig = new AdminTokenConfig();
    	adminTokenConfig.setToken(Token.REMEMBER_ME_TOKEN_EXPIRATION);
    	adminTokenConfig.setValue((Integer) 130);
    	configService.update(adminTokenConfig);
    	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + configService.getAll());*/
    	List<AdminTokenConfig> configs = configService.getAll();
    	model.addAttribute("configs", configs);
        return "admin/configureToken";
    }

 /*   @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/configureToken", method = RequestMethod.POST)
    public String configureTokens(ModelMap model, 
                                  @Valid @ModelAttribute("config") AdminTokenConfig config, BindingResult result) throws Exception {
    	System.out.println("!!!!!!! POST" + config);
    	configService.update(config);
    	System.out.println("!!!!!!! config = " + config);
    	*/
/*       if (result.hasErrors()) {
        	System.out.println("!!!!!!! POST Errors");
            return "admin/configureToken";
        }*/
    	
    	
        //TODO insert into DB 
       // AdminTokenConfig adminTokenConfig = configService.getById(1);
        
       // configService.updateTokenConfig(config.getResetPasswordToken(), config.getVerificationToken(), config.getRememberMeToken());
     /*  
        RESET_PASSWORD_TOKEN_EXPIRATION = config.getResetPasswordToken();
        VERIFICATION_TOKEN_EXPIRATION = config.getVerificationToken();
        REMEMBER_ME_TOKEN_EXPIRATION = config.getRememberMeToken();
     */ 
/*    	List<AdminTokenConfig> configs = configService.getAll();
    	model.addAttribute("configs", configs);
        return "admin/configureToken";
    }
*/
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/configureToken", method = RequestMethod.POST)
    public String configureTokens(ModelMap model, 
                            @Valid @ModelAttribute("config") AdminTokenConfig config, BindingResult result) throws Exception {
    	//System.out.println("!!!!!!! POST" + config);
    	if (result.hasErrors()) {
        	//System.out.println("!!!!!!! POST Errors");
        	List<AdminTokenConfig> configs = configService.getAll();
        	model.addAttribute("configs", configs);
            return "admin/configureToken";
        }
    	//configService.update(config);
    	//System.out.println("!!!!!!! config = " + config);
    	List<AdminTokenConfig> configs = configService.getAll();
    	model.addAttribute("configs", configs);
        return "admin/configureToken";
    }
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/newUser", method = RequestMethod.GET)
    public String newRegistration(@ModelAttribute("userDto") UserRegisterDTO userDto, ModelMap model) {
        model.addAttribute("userRegisterDto", userDto);
        return "admin/newUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/newUser", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("userDto") UserRegisterDTO userDto, BindingResult result,
                           ModelMap model, RedirectAttributes redirectAttributes, Locale locale) {
        if (result.hasErrors()) {
            return "admin/newUser";
        }
        try {
            userService.register(userDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messageError", messageSource.getMessage("admin.dashboard.message.error.new.user", null, locale));
            return "redirect:/admin/users?status=true";
        }
        redirectAttributes.addFlashAttribute("messageSuccess", userDto.getEmail() + " " +
                messageSource.getMessage("admin.dashboard.message.success.new.user", null, locale));

        return "redirect:/admin/users?status=true";
    }

    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/setItemsPerPage/{value}", method = RequestMethod.GET)
    public String setItemsPerPage(@PathVariable int value) {
        usersPerPage = value;
        return "done";
    }

    //utility methods
    private void sendBannedMessageToUserById(Long id, Locale locale) throws ConnectException {
        User user = userService.getById(id);
        if (!user.getEnabled()) {
            String bannedMessage = mailService.createBannedMessage(user, locale);
            mailService.sendMessage(user, messageSource.getMessage("mail.message.banned.title", null, locale), bannedMessage, emailTemplate);
        }
    }
}
