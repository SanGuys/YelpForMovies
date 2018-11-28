package uci.cs297p.service;


import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uci.cs297p.common.*;
import uci.cs297p.model.*;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UMRelationMapper umRelationMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        //User user in session doesn't have the password.
        if (userMapper.checkUsername(username) == 0) {
            return new ServerResponse<>(ResponseCode.ERROR.getCode(), "username not exists", null);
        }
        User user = userMapper.selectLogin(username, MD5Util.getMD5(password));
        if (user == null) {
            return new ServerResponse<>(ResponseCode.ERROR.getCode(), "wrong password", null);
        }
        user.setPassword("");
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), "login success", user);
    }

    @Override
    public ServerResponse<String> signup(User user) {
        if (StringUtils.isNullOrEmpty(user.getUsername()) ||
                StringUtils.isNullOrEmpty(user.getEmail()) ||
                StringUtils.isNullOrEmpty(user.getPassword()) ||
                StringUtils.isNullOrEmpty(user.getQuestion()) ||
                StringUtils.isNullOrEmpty(user.getAnswer())) {
            return ServerResponse.failWithMsg("info incomplete!");
        }
        if (userMapper.checkUsername(user.getUsername()) > 0) {
            return ServerResponse.failWithMsg("username already exists!");
        }
//        if (userMapper.checkEmail(user.getEmail()) > 0) {
//            return ServerResponse.failWithMsg("email already exists!");
//        }
        user.setRole(Cnst.Role.ROLE_CUSTOMER);
        //MD5 encryption
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        if (userMapper.insertSelective(user) <= 0) {
            return ServerResponse.failWithMsg("register failed!");
        } else {
            return ServerResponse.succWithMsg("register success!");
        }
    }

    @Override
    public ServerResponse<String> getQuestion(String username) {
        if (userMapper.checkUsername(username) == 0) {
            return ServerResponse.failWithMsg("user doesn't exist!");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (!question.isEmpty()) {
            return ServerResponse.succWithMsgData("question found", question);
        } else {
            return ServerResponse.failWithMsg("question not found");
        }
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String answer) {
        //If answer is wrong
        if (userMapper.checkAnswer(username, answer) <= 0) {
            return ServerResponse.failWithMsg("wrong answer");
        }
        //If correct, Generate token and put into local cache
        String forgetToken = UUID.randomUUID().toString();
        TokenCache.setKey("token_" + username, forgetToken);
        return ServerResponse.succWithMsgData("forgetToken generated", forgetToken);
    }

    @Override
    public ServerResponse<String> resetPassword(String username, String newPassword, String token) {
        //Check token
        String tokenCached = TokenCache.getKey("token_" + username);
        if (tokenCached == null || !tokenCached.equals(token)) {
            return ServerResponse.failWithMsg("wrong token!");
        }
        //Token checked
        if (StringUtils.isNullOrEmpty(newPassword)) {
            return ServerResponse.failWithMsg("password is empty!");
        }
        if (userMapper.updatePasswordByUsername(username, MD5Util.getMD5(newPassword)) > 0) {
            return ServerResponse.succWithMsg("update password success");
        } else {
            return ServerResponse.failWithMsg("update password failed");
        }
    }

    @Override
    public ServerResponse<String> resetPasswordLogin(User user, String oldPassword,
            String newPassword) {
        if (StringUtils.isNullOrEmpty(newPassword)) {
            return ServerResponse.failWithMsg("password is empty!");
        }
        if (userMapper.checkUseridPassword(user.getId(), MD5Util.getMD5(oldPassword)) <= 0) {
            return ServerResponse.failWithMsg("userid and password not match!");
        }
        user.setPassword(MD5Util.getMD5(newPassword));
        if (userMapper.updateByPrimaryKey(user) <= 0) {
            return ServerResponse.failWithMsg("reset password failed!");
        } else {
            return ServerResponse.succWithMsg("reset password success!");
        }
    }

    @Override
    //return full-userInfo in DB according to userInfo in Session
    public ServerResponse<User> getUserInfo(User user) {
        User userInDB = userMapper.selectByPrimaryKey(user.getId());
        if (userInDB == null) {
            return ServerResponse.failWithMsg("user not found in DB!");
        } else {
            return ServerResponse.succWithMsgData("getUserInfo success", userInDB);
        }
    }

    @Override
    // just return username with user id
    public String getUserName(Integer userId) {
        User user = userMapper.selectByPrimaryKey(32);
        String name = "null";
        if(user != null) {
            name = user.getUsername();
        }
        return name;
    }

    @Override
    public ServerResponse<User> updateUserInfo(User user) {
        //We ONLY update user.email, phone, question, answer according to id. NOT update user.id, name, password.
        //id, name got from session.
        if (userMapper.checkIdEmail(user.getId(), user.getEmail()) > 0) {
            return ServerResponse.failWithMsg("duplicate email! update userInfo failed!");
        }
        User updateUser = new User();
        updateUser.setUpdateField(user);
        if (userMapper.updateByPrimaryKeySelective(updateUser) > 0) {
            return ServerResponse.succWithMsgData("update userInfo success!", updateUser);
        } else {
            return ServerResponse.failWithMsg("update userInfo failed!");
        }
    }

    @Override
    public ServerResponse<User> updateUserInfo(User user, UserProfileForm userProfileForm) {
        //We ONLY update user.email, phone, question, answer according to id. NOT update user.id, username.
        if (userProfileForm.getId() != user.getId()) {
            return ServerResponse.failWithMsg("duplicate user_id! update userInfo failed!");
        }
        user.setPassword(null);
        if(userProfileForm.getPassword() != null && !"".equals(userProfileForm.getPassword())) user.setPassword(MD5Util.getMD5(userProfileForm.getPassword()));
        if(userProfileForm.getPhone() != null) user.setPhone(userProfileForm.getPhone());
        if(userProfileForm.getEmail() != null) user.setEmail(userProfileForm.getEmail());
        if(userProfileForm.getQuestion() != null) user.setQuestion(userProfileForm.getQuestion());
        if(userProfileForm.getAnswer() != null) user.setAnswer(userProfileForm.getAnswer());

        if (userMapper.updateByPrimaryKeySelective(user) > 0) {
            return ServerResponse.succWithMsgData("update userInfo success!", user);
        } else {
            return ServerResponse.failWithMsg("update userInfo failed!");
        }
    }

    @Override
    public ServerResponse<UMRelation> getCollection(UMRelationKey key) {
        UMRelation recordInDB = umRelationMapper.selectByPrimaryKey(key);
        if (recordInDB == null) {
            return ServerResponse.failWithMsg("record not found in DB!");
        } else {
            return ServerResponse.succWithMsgData("getCollection success", recordInDB);
        }
    }

    @Override
    public ServerResponse<List<UMRelation>> getCollections(Integer userId) {
        List<UMRelation> movieListInDB = umRelationMapper.getCollectionsByUserId(userId);
        if (movieListInDB == null) {
            return ServerResponse.failWithMsg("user not found in DB!");
        } else {
            return ServerResponse.succWithMsgData("getCollections success", movieListInDB);
        }
    }

    @Override
    public ServerResponse<UMRelation> updateCollection(UMRelationKey key) {
        UMRelation recordInDB = umRelationMapper.selectByPrimaryKey(key);
        if (recordInDB != null) {
            if (recordInDB.getCollected() == Cnst.CollectionStatus.COLLECTION_TRUE && recordInDB.getRating() == null) {
                if(umRelationMapper.deleteByPrimaryKey(key) > 0) {
                    return ServerResponse.succWithMsgData("update success!", null);
                } else {
                    return ServerResponse.failWithMsg("update failed!");
                }
            } else {
                recordInDB.setCollected(1 - recordInDB.getCollected());
                if(umRelationMapper.updateByPrimaryKeySelective(recordInDB) > 0){
                    return ServerResponse.succWithMsgData("update success!", recordInDB);
                } else {
                    return ServerResponse.failWithMsg("update failed!");
                }
            }
        }
        return ServerResponse.failWithMsg("record not found in DB!");
    }
}

