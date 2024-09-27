package com.jonasdrechsel.kilterboardleaderboard.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "climbs")
public class Climb {
    // The Id Field ist uuidKilter + climbUuidKilter
    @Id
    private String id;
    @JsonProperty("_type")
    private String type;
    @JsonProperty("uuid")
    private String uuidKilter;
    @JsonProperty("climb_uuid")
    private String climbUuidKilter;
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty
    private int angle;
    @JsonProperty("is_mirror")
    private boolean isMirror;
    @JsonProperty("attempt_id")
    private int attemptId;
    @JsonProperty("bid_count")
    private int bidCount;
    @JsonProperty
    private int difficulty;
    @JsonProperty
    private boolean is_benchmark;
    @JsonProperty
    private String comment;
    @JsonProperty("climbed_at")
    private String date;
    private int ppUnweighted;
    private int ppWeighted;
    private int ppUnweightedSeason1;
    private int ppWeightedSeason1;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMirror(boolean mirror) {
        isMirror = mirror;
    }

    public String getName() {
        return name;
    }

    public void setPpUnweightedSeason1(int ppUnweightedSeason1) {
        this.ppUnweightedSeason1 = ppUnweightedSeason1;
    }

    public void setPpWeightedSeason1(int ppWeightedSeason1) {
        this.ppWeightedSeason1 = ppWeightedSeason1;
    }

    public int getPpUnweightedSeason1() {
        return ppUnweightedSeason1;
    }

    public int getPpWeightedSeason1() {
        return ppWeightedSeason1;
    }

    public void setPpWeighted(int ppWeighted) {
        this.ppWeighted = ppWeighted;
    }

    public int getPpWeighted() {
        return ppWeighted;
    }

    public void setClimbUuidKilter(String climbUuid) {
        this.climbUuidKilter = climbUuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPpUnweighted() {
        return ppUnweighted;
    }

    public void setPpUnweighted(int pp) {
        this.ppUnweighted = pp;
    }

    public int getAngle() {
        return angle;
    }

    public String getClimbUuidKilter() {
        return climbUuidKilter;
    }

    public String getUuidKilter() {
        return uuidKilter;
    }

    public void setUuidKilter(String uuid) {
        this.uuidKilter = uuid;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public int getBidCount() {
        return bidCount;
    }

    public int getDifficulty() {
        return difficulty;
    }


    public long getUserId() {
        return userId;
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

    public void setAttemptId(int attempt_id) {
        this.attemptId = attempt_id;
    }

    public void setBidCount(int bid_count) {
        this.bidCount = bid_count;
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

    public void setIsMirror(boolean is_mirror) {
        this.isMirror = is_mirror;
    }

    public void setIs_benchmark(boolean is_benchmark) {
        this.is_benchmark = is_benchmark;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
