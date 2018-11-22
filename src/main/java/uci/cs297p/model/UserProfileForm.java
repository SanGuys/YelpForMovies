package uci.cs297p.model;

import lombok.Data;

@Data
public class UserProfileForm {
    Integer id;
    String username;
    String password;
    String email;
    String phone;
    String question;
    String answer;

    @Override
    public String toString() {
        return "UserProfileForm{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
