package com.codefellowship.codefellowship.Users;
import org.springframework.data.annotation.Id;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    String body;
    Date createdAt;
    @ManyToOne
    @JoinColumn(name = "userid")
    private Users users;



    public Post() {
    }
    public Users getUsers(){
        return users;
    }
    public void setUsers(Users users) {
        this.users = users;
    }
    public Post(String body) {
        this.body=body;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Post(String body, Date createdAt,Users users) {
        this.users=users;
        this.body = body;
        this.createdAt = createdAt;
    }
    public Long getId() {
        return id;
    }
}