package com.jinan.entities.Voter;

import java.security.Timestamp;
import java.util.List;

public class Vote<T> {
    private Integer voteId;
    private String title;
    private String description;
    private Integer createdBy;
    private T createdAt;
    private Boolean isActive;
    private Long expiryTime;
    private String createName;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(T createdAt) {
        this.createdAt = createdAt;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public T getCreatedAt() {
        return createdAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    private List<Options> options;
    // 查找时 保存选项信息
    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }
    @Override
    public String toString() {
        return "Vote{" +
                "voteId=" + voteId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", isActive=" + isActive +
                ", expiryTime=" + expiryTime +
                ", options=" + options +
                '}';
    }
}
