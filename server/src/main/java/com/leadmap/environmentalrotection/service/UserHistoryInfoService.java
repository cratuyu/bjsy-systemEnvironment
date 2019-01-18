package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.common.config.ConnectionConfig;
import com.leadmap.environmentalrotection.dao.DocumentInfoDao;
import com.leadmap.environmentalrotection.dao.UserInfoDao;
import com.leadmap.environmentalrotection.dao.UserTokenMappedInfoDao;
import com.leadmap.environmentalrotection.dto.UserCollectInfoDTO;
import com.leadmap.environmentalrotection.dto.UserCommentInfoDTO;
import com.leadmap.environmentalrotection.dto.UserLikeInfoDTO;
import com.leadmap.environmentalrotection.dto.UserOperateHistoryInfo;
import com.leadmap.environmentalrotection.entity.document.DocumentInfo;
import com.leadmap.environmentalrotection.entity.user.UserCollectDocInfo;
import com.leadmap.environmentalrotection.entity.user.UserInfo;
import com.leadmap.environmentalrotection.entity.user.UserLikeDocInfo;
import com.leadmap.environmentalrotection.entity.user.UserTokenMappedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/13 9:27
 */
@Component
public class UserHistoryInfoService {
    @Autowired
    ConnectionConfig connectionConfig;

    @Autowired
    UserTokenMappedInfoDao userTokenMappedInfoDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    DocumentInfoDao documentInfoDao;

    public List<UserCommentInfoDTO> queryUserCommentHistorys(String token, long pageNumber, long pageSize) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserCommentInfoDTO> userCommentInfoDTOList = new ArrayList<>();
                String strSql = "select *,t2.id as docId, t2.document_url as docUrl,t2.title as docTitle from user_comment_info t1 left join document_info t2 on t1.doc_id=t2.id where t1.user_id=? limit ?,?";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, userInfo.getId().toString());
                preparedStatement.setLong(2, (pageNumber - 1) * pageSize);
                preparedStatement.setLong(3, pageSize);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    UserCommentInfoDTO userCommentInfoDTO = new UserCommentInfoDTO();
                    userCommentInfoDTO.setUserName(userInfo.getUserName());
                    userCommentInfoDTO.setCommentContent(resultSet.getString("content"));
                    Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userCommentInfoDTO.setCommentTime(f.format(resultSet.getTimestamp("comment_time")));
                    userCommentInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
                    userCommentInfoDTO.setDocId(resultSet.getString("docId"));
                    userCommentInfoDTO.setDocUrl(resultSet.getString("docUrl"));
                    userCommentInfoDTO.setDocTitle(resultSet.getString("docTitle"));
                    userCommentInfoDTO.setPublishDate(resultSet.getString("publish_date"));
                    userCommentInfoDTO.setPublisher(resultSet.getString("publisher"));
                    userCommentInfoDTO.setImageUrl(resultSet.getString("image_url"));
                    userCommentInfoDTOList.add(userCommentInfoDTO);
                }
                return userCommentInfoDTOList;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<UserCommentInfoDTO> queryUserCommentHistorys(String token) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserCommentInfoDTO> userCommentInfoDTOList = new ArrayList<>();
                String strSql = "select *,t2.id as docId, t2.document_url as docUrl,t2.title as docTitle  from user_comment_info t1 left join document_info t2 on t1.doc_id=t2.id where t1.user_id=?";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, userInfo.getId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    UserCommentInfoDTO userCommentInfoDTO = new UserCommentInfoDTO();
                    userCommentInfoDTO.setUserName(userInfo.getUserName());
                    userCommentInfoDTO.setCommentContent(resultSet.getString("content"));
                    Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userCommentInfoDTO.setCommentTime(f.format(resultSet.getTimestamp("comment_time")));
                    userCommentInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
                    userCommentInfoDTO.setDocId(resultSet.getString("docId"));
                    userCommentInfoDTO.setDocUrl(resultSet.getString("docUrl"));
                    userCommentInfoDTO.setDocTitle(resultSet.getString("docTitle"));
                    userCommentInfoDTO.setPublishDate(resultSet.getString("publish_date"));
                    userCommentInfoDTO.setPublisher(resultSet.getString("publisher"));
                    userCommentInfoDTO.setImageUrl(resultSet.getString("image_url"));
                    userCommentInfoDTOList.add(userCommentInfoDTO);
                }
                return userCommentInfoDTOList;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<UserCommentInfoDTO> queryUserCommentHistorysByDocId(String docId) {
        try {
//            UserInfo userInfo = getUserInfoByToken(token);
            List<UserCommentInfoDTO> userCommentInfoDTOList = new ArrayList<>();
            String strSql = "select *,t2.id as docId, t2.document_url as docUrl ,t3.user_name as username,t3.avatar_url as avatarurl,t2.title as docTitle from user_comment_info t1 left join document_info t2 on t1.doc_id=t2.id left join user_info t3 on t1.user_id=t3.id where t1.doc_id=?";
            Connection connection = getDatabaseSchemaConnection(connectionConfig);
            PreparedStatement preparedStatement = connection.prepareStatement(strSql);
            preparedStatement.setString(1, docId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserCommentInfoDTO userCommentInfoDTO = new UserCommentInfoDTO();
                userCommentInfoDTO.setUserName(resultSet.getString("username"));
                userCommentInfoDTO.setCommentContent(resultSet.getString("content"));
                Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userCommentInfoDTO.setCommentTime(f.format(resultSet.getTimestamp("comment_time")));
                userCommentInfoDTO.setAvatarUrl(resultSet.getString("avatarurl"));
                userCommentInfoDTO.setDocId(resultSet.getString("docId"));
                userCommentInfoDTO.setDocUrl(resultSet.getString("docUrl"));
                userCommentInfoDTO.setDocTitle(resultSet.getString("docTitle"));
                userCommentInfoDTO.setPublishDate(resultSet.getString("publish_date"));
                userCommentInfoDTO.setPublisher(resultSet.getString("publisher"));
                userCommentInfoDTO.setImageUrl(resultSet.getString("image_url"));
                userCommentInfoDTOList.add(userCommentInfoDTO);
            }
            return userCommentInfoDTOList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<UserCommentInfoDTO> queryUserCommentHistorysByDocId(String docId, String token) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserCommentInfoDTO> userCommentInfoDTOList = new ArrayList<>();
                String strSql = "select *,t2.id as docId, t2.document_url as docUrl ,t3.user_name as username,t3.avatar_url as avatarurl,t2.title as docTitle from user_comment_info t1 left join document_info t2 on t1.doc_id=t2.id left join user_info t3 on t1.user_id=t3.id where t1.doc_id=? and t1.user_id=?";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, docId);
                preparedStatement.setString(2, userInfo.getId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    UserCommentInfoDTO userCommentInfoDTO = new UserCommentInfoDTO();
                    userCommentInfoDTO.setUserName(resultSet.getString("username"));
                    userCommentInfoDTO.setCommentContent(resultSet.getString("content"));
                    Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userCommentInfoDTO.setCommentTime(f.format(resultSet.getTimestamp("comment_time")));
                    userCommentInfoDTO.setAvatarUrl(resultSet.getString("avatarurl"));
                    userCommentInfoDTO.setDocId(resultSet.getString("docId"));
                    userCommentInfoDTO.setDocUrl(resultSet.getString("docUrl"));
                    userCommentInfoDTO.setDocTitle(resultSet.getString("docTitle"));
                    userCommentInfoDTO.setPublishDate(resultSet.getString("publish_date"));
                    userCommentInfoDTO.setPublisher(resultSet.getString("publisher"));
                    userCommentInfoDTO.setImageUrl(resultSet.getString("image_url"));
                    userCommentInfoDTOList.add(userCommentInfoDTO);
                }
                return userCommentInfoDTOList;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public long queryUserCommentHistoryCount(String token) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserCommentInfoDTO> userCommentInfoDTOList = new ArrayList<>();
                String strSql = "select count(t1.id) as num from user_comment_info t1 left join document_info t2 on t1.doc_id=t2.id where t1.user_id=?";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, userInfo.getId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    return resultSet.getLong("num");
                }
                return 0;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public List<UserLikeInfoDTO> queryUserLikeHistorys(String token) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserLikeInfoDTO> userOperateHistoryInfoList = new ArrayList<>();
                String strSql = "select * from user_like_doc_info t1 left join document_info t2 on t1.doc_id=t2.id where t1.user_id=?";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, userInfo.getId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    UserLikeInfoDTO userLikeInfoDTO = new UserLikeInfoDTO();
                    userLikeInfoDTO.setDocId(resultSet.getString("doc_id"));
                    userLikeInfoDTO.setDocTitle(resultSet.getString("title"));
                    userLikeInfoDTO.setPublisher(resultSet.getString("publisher"));
                    userLikeInfoDTO.setPublishDate(resultSet.getString("publish_date"));
                    userLikeInfoDTO.setDocUrl(resultSet.getString("document_url"));
                    userLikeInfoDTO.setUserName(userInfo.getUserName());
                    Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userLikeInfoDTO.setLikeTime(f.format(resultSet.getTimestamp("like_time")));
                    userLikeInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
                    userLikeInfoDTO.setImageUrl(resultSet.getString("image_url"));
                    userOperateHistoryInfoList.add(userLikeInfoDTO);
                }
                return userOperateHistoryInfoList;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public long queryUserLikeHistoryCount(String token) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserOperateHistoryInfo> userOperateHistoryInfoList = new ArrayList<>();
                String strSql = "select count(t1.id) as num from user_operate_doc_info t1 left join document_info t2 on t1.doc_id=t2.id where t1.user_id=? and t1.is_like=?";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, userInfo.getId().toString());
                preparedStatement.setBoolean(2, true);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    return resultSet.getLong("num");
                }
                return 0;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public List<UserLikeInfoDTO> queryUserLikeHistorys(String token, long pageNumber, long pageSize) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserLikeInfoDTO> userOperateHistoryInfoList = new ArrayList<>();
                String strSql = "select * from user_like_doc_info t1 left join document_info t2 on t1.doc_id=t2.id where t1.user_id=? limit ?,?";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, userInfo.getId().toString());
                preparedStatement.setLong(2, (pageNumber - 1) * pageSize);
                preparedStatement.setLong(3, pageSize);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    UserLikeInfoDTO userLikeInfoDTO = new UserLikeInfoDTO();
                    userLikeInfoDTO.setDocId(resultSet.getString("doc_id"));
                    userLikeInfoDTO.setDocTitle(resultSet.getString("title"));

                    userLikeInfoDTO.setDocUrl(resultSet.getString("document_url"));
                    userLikeInfoDTO.setUserName(userInfo.getUserName());
                    Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userLikeInfoDTO.setLikeTime(f.format(resultSet.getTimestamp("like_time")));
                    userLikeInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
                    userLikeInfoDTO.setPublisher(resultSet.getString("publisher"));
                    userLikeInfoDTO.setPublishDate(resultSet.getString("publish_date"));
                    userLikeInfoDTO.setImageUrl(resultSet.getString("image_url"));
                    userOperateHistoryInfoList.add(userLikeInfoDTO);
                }
                return userOperateHistoryInfoList;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    private UserInfo getUserInfoByToken(String token) {
        UserTokenMappedInfo userTokenMappedInfo = userTokenMappedInfoDao.findByToken(token);
        if (userTokenMappedInfo != null) {
            return userInfoDao.findById(userTokenMappedInfo.getUserId());
        }

        return null;
    }

    public List<UserCollectInfoDTO> queryUserCollectHistorys(String token, long pageNumber, long pageSize) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserCollectInfoDTO> userOperateHistoryInfoList = new ArrayList<>();
                String strSql = "select t2.*,t1.collect_time as collect_time from user_collect_doc_info t1 left join document_info t2 on t1.doc_id=t2.id where t1.user_id=? limit ?,?";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, userInfo.getId().toString());
                preparedStatement.setLong(2, (pageNumber - 1) * pageSize);
                preparedStatement.setLong(3, pageSize);
                ResultSet resultSet = preparedStatement.executeQuery();
//                UserInfo userInfo = userInfoDao.findByAccessToken(token);
                while (resultSet.next()) {
                    UserCollectInfoDTO userCollectInfoDTO = new UserCollectInfoDTO();
                    userCollectInfoDTO.setDocId(resultSet.getString("id"));
                    userCollectInfoDTO.setDocTitle(resultSet.getString("title"));
                    userCollectInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
                    Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userCollectInfoDTO.setCollectTime(f.format(resultSet.getTimestamp("collect_time")));
                    userCollectInfoDTO.setDocUrl(resultSet.getString("document_url"));
                    userCollectInfoDTO.setUserName(userInfo.getUserName());
                    userCollectInfoDTO.setPublisher(resultSet.getString("publisher"));
                    userCollectInfoDTO.setPublishDate(resultSet.getString("publish_date"));
                    userCollectInfoDTO.setImageUrl(resultSet.getString("image_url"));
                    userOperateHistoryInfoList.add(userCollectInfoDTO);
                }
                return userOperateHistoryInfoList;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public long queryUserCollectHistoryCount(String token) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserOperateHistoryInfo> userOperateHistoryInfoList = new ArrayList<>();
                String strSql = "select count(t1.id) as num from user_operate_doc_info t1 left join document_info t2 on t1.doc_id=t2.id where t1.user_id=? and t1.is_collect=?";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, userInfo.getId().toString());
                preparedStatement.setBoolean(2, true);
                ResultSet resultSet = preparedStatement.executeQuery();
//                UserInfo userInfo = userInfoDao.findByAccessToken(token);
                while (resultSet.next()) {
                    return resultSet.getLong("num");
                }
                return 0;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public List<UserCollectInfoDTO> queryUserCollectHistorys(String token) {
        try {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                List<UserCollectInfoDTO> userOperateHistoryInfoList = new ArrayList<>();
                String strSql = "select t2.*,t1.collect_time as collect_time from user_operate_doc_info t1 left join document_info t2 on t1.doc_id=t2.id where t1.user_id=? ";
                Connection connection = getDatabaseSchemaConnection(connectionConfig);
                PreparedStatement preparedStatement = connection.prepareStatement(strSql);
                preparedStatement.setString(1, userInfo.getId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
//                UserInfo userInfo = userInfoDao.findByAccessToken(token);
                while (resultSet.next()) {
                    UserCollectInfoDTO userCollectInfoDTO = new UserCollectInfoDTO();
                    userCollectInfoDTO.setDocId(resultSet.getString("id"));
                    userCollectInfoDTO.setDocTitle(resultSet.getString("title"));
                    userCollectInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
                    Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userCollectInfoDTO.setCollectTime(f.format(resultSet.getTimestamp("collect_time")));
                    userCollectInfoDTO.setDocUrl(resultSet.getString("document_url"));
                    userCollectInfoDTO.setUserName(userInfo.getUserName());
                    userCollectInfoDTO.setPublisher(resultSet.getString("publisher"));
                    userCollectInfoDTO.setPublishDate(resultSet.getString("publish_date"));
                    userCollectInfoDTO.setImageUrl(resultSet.getString("image_url"));
                    userOperateHistoryInfoList.add(userCollectInfoDTO);
                }
                return userOperateHistoryInfoList;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public Connection getDatabaseSchemaConnection(ConnectionConfig connectionConfig) {
        Connection connection = null;
        try {
            Class.forName(connectionConfig.getDriverClassName());
            connection = DriverManager.getConnection(connectionConfig.getUrl(), connectionConfig.getUserName(), connectionConfig.getPassword());
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserLikeInfoDTO userLikeInfo2Dto(UserLikeDocInfo userLikeDocInfo) {
        if (userLikeDocInfo != null) {
            UserInfo userInfo = userInfoDao.findById(Long.parseLong(userLikeDocInfo.getUserId()));
            UserLikeInfoDTO userLikeInfoDTO = new UserLikeInfoDTO();
            DocumentInfo documentInfo = documentInfoDao.findById(Long.parseLong(userLikeDocInfo.getDocId()));
            userLikeInfoDTO.setDocUrl(documentInfo.getDocumentUrl());
            Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userLikeInfoDTO.setLikeTime(f.format(userLikeDocInfo.getLikeTime()));
            userLikeInfoDTO.setDocId(userLikeDocInfo.getDocId());
            if(userInfo != null){
                userLikeInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
                userLikeInfoDTO.setUserName(userInfo.getUserName());
            }
            userLikeInfoDTO.setDocTitle(documentInfo.getTitle());
            userLikeInfoDTO.setPublishDate(documentInfo.getPublishDate());
            userLikeInfoDTO.setPublisher(documentInfo.getPublisher());
            userLikeInfoDTO.setImageUrl(documentInfo.getImageUrl());
            return userLikeInfoDTO;
        } else {
            return null;
        }
    }

    public UserCollectInfoDTO userCollectInfo2Dto(UserCollectDocInfo userCollectDocInfo) {
        if (userCollectDocInfo != null) {
            UserInfo userInfo = userInfoDao.findById(Long.parseLong(userCollectDocInfo.getUserId()));
            if (userInfo != null) {
                UserCollectInfoDTO userCollectInfoDTO = new UserCollectInfoDTO();
                DocumentInfo documentInfo = documentInfoDao.findById(Long.parseLong(userCollectDocInfo.getDocId()));
                userCollectInfoDTO.setDocUrl(documentInfo.getDocumentUrl());
                Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userCollectInfoDTO.setCollectTime(f.format(userCollectDocInfo.getCollectTime()));
                userCollectInfoDTO.setDocId(userCollectDocInfo.getDocId());
                userCollectInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
                userCollectInfoDTO.setUserName(userInfo.getUserName());
                userCollectInfoDTO.setDocTitle(documentInfo.getTitle());
                userCollectInfoDTO.setPublishDate(documentInfo.getPublishDate());
                userCollectInfoDTO.setPublisher(documentInfo.getPublisher());
                userCollectInfoDTO.setImageUrl(documentInfo.getImageUrl());
                return userCollectInfoDTO;
            }
            return null;
        } else {
            return null;
        }
    }
}
