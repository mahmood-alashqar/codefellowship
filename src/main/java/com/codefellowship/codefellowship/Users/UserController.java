package com.codefellowship.codefellowship.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public String homePage(){
        return "home";
    }
    @GetMapping("/signup")
    public String getSignupPage(){
        return "signup";
    }
    @GetMapping("/login")
    public String getLoginPge(){
//        model.addAttribute("userInfo",userRepository.findAll());
        return "logIn";
    }
    @GetMapping("/myprofile")
    public String getProfilePage(Model model){
        UserDetails userDetails= (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Users users = userRepository.findUsersByUsername(userDetails.getUsername());
        model.addAttribute("posts",users.getPosts());
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("userInfo",users);
         userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users userAccountLoggedIn = userRepository.findUsersByUsername(userDetails.getUsername());
        model.addAttribute("noOfFollowers",userAccountLoggedIn.getFollowers().size());
        model.addAttribute("noOfFollowing",userAccountLoggedIn.getFollowing().size());
        return "profile";
    }
    @PostMapping("/myprofile")
    public RedirectView createPost(@RequestParam String body){
        Date date = new Date();
        UserDetails userDetails= (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Users users = userRepository.findUsersByUsername(userDetails.getUsername());
        Post post= new Post(body);
        post.setUsers(users);
        post.setCreatedAt(date);
        post= postRepository.save(post);
        return new RedirectView("/myprofile");
    }
    @GetMapping("/test")
    public String testTem(){
        return "test";
    }
    @PostMapping("/signup")
    public RedirectView trySignUp(@RequestParam String firstname,
                                  @RequestParam String lastname,
                                  @RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String location,
                                  @RequestParam String dateOfBirth,
                                  @RequestParam String bio){
        Users newUser = new Users(firstname,lastname,username,bCryptPasswordEncoder.encode(password),location,dateOfBirth,bio);
        newUser = userRepository.save(newUser);
        Authentication authentication= new UsernamePasswordAuthenticationToken(newUser,null,new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }
    @RequestMapping(value="/users/{id}",method= RequestMethod.GET)
    public String getUsers(Model model, @PathVariable (value ="id") Long id){
        Optional<Users> userById=userRepository.findById(id);
        model.addAttribute("bios", userById);
        return "profile";
    }
    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "error";
    }




    @GetMapping("/feeds")
    public String getFeeds (Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users userAccountLoggedIn = userRepository.findUsersByUsername(userDetails.getUsername());
        model.addAttribute("followinguser",userAccountLoggedIn.getFollowing());
        model.addAttribute("userName",userDetails.getUsername());
return "feeds";
    }

    @PostMapping("/following/{id}")
    public RedirectView addFollowing(@PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users userAccount = userRepository.findById(id).orElseThrow();
        Users userAccountLoggedIn = userRepository.findUsersByUsername(userDetails.getUsername());
        userAccountLoggedIn.addFollowing(userAccount);
        userRepository.save(userAccountLoggedIn);
        return new RedirectView("/user/{id}");
    }
    @GetMapping("/user/{id}")
            public String getUsr(Model model , @PathVariable Long id)
    {
        model.addAttribute("users",userRepository.findById(id).orElseThrow());
        return "userId";
    }
    @PostMapping("/unFollowing/{id}")
    public RedirectView removeFollowing(@PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users userAccount = userRepository.findById(id).orElseThrow();
        Users userAccountLoggedIn = userRepository.findUsersByUsername(userDetails.getUsername());
        userAccountLoggedIn.deleteFollowing(userAccount);
        userRepository.save(userAccountLoggedIn);
        return new RedirectView("/user/{id}");
    }

//@GetMapping("/follow")
//public String getFollowing(Model model){
//    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    Users userAccountLoggedIn = userRepository.findUsersByUsername(userDetails.getUsername());
//    model.addAttribute("noOfFollowers",userAccountLoggedIn.getFollowers().size());
//    model.addAttribute("noOfFollowing",userAccountLoggedIn.getFollowing().size());
//    return "follow";
//
//}
@GetMapping("/users")
    public String getUsers(Model model)
{
    model.addAttribute("users",userRepository.findAll());
    return "userProfile";
}
//    @GetMapping("/users/{id}")
//    public String getUsersByPostss(Model model,@PathVariable Long id)
//    {
//        model.addAttribute("users",userRepository.findById(id).orElseThrow());
//        return "userId";
//    }
    }

