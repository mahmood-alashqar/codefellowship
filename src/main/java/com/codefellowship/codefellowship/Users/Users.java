package com.codefellowship.codefellowship.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstname;
    String lastname;
    String dateOfBirth;
    String location;
    String bio;
    @OneToMany(mappedBy = "users")
    private List<Post> posts;
    public List<Post> getPosts() {
        return posts;
    }

    @ManyToMany
    @JoinTable(
            name = "useraccount_useraccount",
            joinColumns = {@JoinColumn(name = "from_id")},
            inverseJoinColumns = {@JoinColumn(name = "to_id")}
    )
    public Set<Users> following= new HashSet<>();

    public Set<Users> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Users> following) {
        this.following = following;
    }

    public List<Users> getFollowers() {
        return followers;
    }



    @ManyToMany(mappedBy = "following", fetch = FetchType.EAGER)
    public List<Users> followers;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public Users(String firstname, String lastname,String username, String password  , String location,String dateOfBirth, String bio) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.location = location;
        this.bio = bio;
        this.username = username;
        this.password = password;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    @Column(unique = true)
    String username;
    String password;
    public void addPost(Post body){
        this.posts.add(body);
    }
    public Long getId() {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastName) {
        this.lastname = lastName;
    }
    public Users() {
    }
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    public Set<Users> addFollowing(Users users){
        this.following.add(users);
        return this.following ;
    }
    public Set<Users> deleteFollowing(Users userAccount){
        this.following.remove(userAccount);
        return this.following ;
    }
}