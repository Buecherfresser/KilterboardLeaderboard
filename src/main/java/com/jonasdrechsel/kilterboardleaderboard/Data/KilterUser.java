package com.jonasdrechsel.kilterboardleaderboard.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
public class KilterUser {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long userKey;
    @JsonProperty("_type")
    private String type;
    @Id
    @JsonProperty
    private long id;
    @JsonProperty
    private String username;
    @JsonProperty
    private String name;
    @JsonProperty("avatar_image")
    private String avatarImage;
    @JsonProperty("is_verified")
    private boolean isVerified;
    @JsonProperty("created_at")
    private String createdAt;
    @ElementCollection
    private List<Integer> difficulties;
    private int pp;

//    public Long getKey() {
//        return userKey;
//    }
//
//    public void setKey(Long key) {
//        this.userKey = userKey;
//    }

    public String getType() {
        return type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public void setDifficulties(List<Integer> difficulties) {
        this.difficulties = difficulties;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
