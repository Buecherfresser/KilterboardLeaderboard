package com.jonasdrechsel.kilterboardleaderboard.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
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
    //    @ElementCollection
//    private List<Integer> climbs;
    private int pp;
    private int ascents;
    private int flashes;
    private int highestDifficulty;

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

    public int getAscents() {
        return ascents;
    }

    public int getFlashes() {
        return flashes;
    }

    public void setAscents(int ascents) {
        this.ascents = ascents;
    }

    public void setFlashes(int flashes) {
        this.flashes = flashes;
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

    public int getHighestDifficulty() {
        return highestDifficulty;
    }

    public void setHighestDifficulty(int highestDifficulty) {
        this.highestDifficulty = highestDifficulty;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
