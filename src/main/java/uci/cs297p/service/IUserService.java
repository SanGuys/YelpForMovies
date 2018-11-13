package uci.cs297p.service;

import uci.cs297p.common.ServerResponse;
import uci.cs297p.model.User;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> signup(User user);

    ServerResponse<String> getQuestion(String username);

    ServerResponse<String> checkAnswer(String user, String answer);

    ServerResponse<String> resetPassword(String username, String newPassword, String token);

    ServerResponse<String> resetPasswordLogin(User user, String oldPassword, String newPassword);

    ServerResponse<User> getUserInfo(User user);

    ServerResponse<User> updateUserInfo(User user);
}
