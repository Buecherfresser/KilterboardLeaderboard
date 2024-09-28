package com.jonasdrechsel.kilterboardleaderboard.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

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
    private int ppWeighted;
    private int ppUnweighted;
    private int ppWeightedSeason1;
    private int ppUnweightedSeason1;
    private int ascents;
    private int ascentsSeason1;
    private int flashes;
    private int flashesSeason1;
    private int highestDifficulty;
    private LocalDateTime lastUpdate;

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getAscentsSeason1() {
        return ascentsSeason1;
    }

    public void setAscentsSeason1(int ascentsSeason1) {
        this.ascentsSeason1 = ascentsSeason1;
    }

    public void setFlashesSeason1(int flashesSeason1) {
        this.flashesSeason1 = flashesSeason1;
    }

    public int getFlashesSeason1() {
        return flashesSeason1;
    }

    public int getPpUnweightedSeason1() {
        return ppUnweightedSeason1;
    }

    public int getPpWeightedSeason1() {
        return ppWeightedSeason1;
    }

    public void setPpUnweightedSeason1(int unweightedPPSeason1) {
        this.ppUnweightedSeason1 = unweightedPPSeason1;
    }

    public void setPpWeightedSeason1(int weightedPPSeason1) {
        this.ppWeightedSeason1 = weightedPPSeason1;
    }

    public String getType() {
        return type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPpWeighted() {
        return ppWeighted;
    }

    public void setPpUnweighted(int unweightedPP) {
        this.ppUnweighted = unweightedPP;
    }

    public int getPpUnweighted() {
        return ppUnweighted;
    }

    public void setPpWeighted(int pp) {
        this.ppWeighted = pp;
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
