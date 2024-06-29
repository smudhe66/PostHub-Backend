package com.sm.blog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="posts")
public class Post {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    
    @Column(name="title", length = 100, nullable = false)
    private String postTitle;
    
    @Column(name="content", length = 10000)
    private String postContent;
    
    @Column(name="image")
    private String postImage;
    
    private Date addedDate;
    
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", postImage='" + postImage + '\'' +
                ", addedDate=" + addedDate +
                ", category=" + (category != null ? category.getCategoryId() : null) +
                '}';
    }
}
