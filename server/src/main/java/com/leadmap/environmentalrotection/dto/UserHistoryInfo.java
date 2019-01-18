package com.leadmap.environmentalrotection.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 * 足迹
 * @author: ttq
 * @Date: 2018/7/13 8:51
 */
public class UserHistoryInfo implements Serializable {
    public List<UserCollectInfoDTO> getUserCollectHistoryList() {
        return userCollectHistoryList;
    }

    public void setUserCollectHistoryList(List<UserCollectInfoDTO> userCollectHistoryList) {
        this.userCollectHistoryList = userCollectHistoryList;
    }

    public List<UserLikeInfoDTO> getUserLikeHistoryList() {
        return userLikeHistoryList;
    }

    public void setUserLikeHistoryList(List<UserLikeInfoDTO> userLikeHistoryList) {
        this.userLikeHistoryList = userLikeHistoryList;
    }

    public List<UserCommentInfoDTO> getUserCommentHistoryList() {
        return userCommentHistoryList;
    }

    public void setUserCommentHistoryList(List<UserCommentInfoDTO> userCommentHistoryList) {
        this.userCommentHistoryList = userCommentHistoryList;
    }

    private List<UserCollectInfoDTO> userCollectHistoryList;
    private List<UserLikeInfoDTO> userLikeHistoryList;
    private List<UserCommentInfoDTO> userCommentHistoryList;
}
