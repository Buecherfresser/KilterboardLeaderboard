package com.jonasdrechsel.kilterboardleaderboard.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "climbs")
public class Climb {
    @JsonProperty("_type")
    private String type;
    @JsonProperty
    private String uuid;
    @Id
    @JsonProperty
    private String climb_uuid;
    @JsonProperty
    private long user_id;
    @JsonProperty
    private int angle;
    @JsonProperty
    private boolean is_mirror;
    @JsonProperty
    private int attempt_id;
    @JsonProperty
    private int bid_count;
    @JsonProperty
    private int difficulty;
    @JsonProperty
    private boolean is_benchmark;
    @JsonProperty
    private String comment;
    @JsonProperty("climbed_at")
    private String date;
    private int pp;

    public int getPp() {
        return pp;
    }
    public void setPp(int pp) {
        this.pp = pp;
    }
    public int getAngle() {
        return angle;
    }

    public String getClimb_uuid() {
        return climb_uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setClimb_uuid(String climb_uuid) {
        this.climb_uuid = climb_uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getAttempt_id() {
        return attempt_id;
    }

    public int getBid_count() {
        return bid_count;
    }

    public int getDifficulty() {
        return difficulty;
    }


    public long getUser_id() {
        return user_id;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setAttempt_id(int attempt_id) {
        this.attempt_id = attempt_id;
    }

    public void setBid_count(int bid_count) {
        this.bid_count = bid_count;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setIs_mirror(boolean is_mirror) {
        this.is_mirror = is_mirror;
    }

    public void setIs_benchmark(boolean is_benchmark) {
        this.is_benchmark = is_benchmark;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}