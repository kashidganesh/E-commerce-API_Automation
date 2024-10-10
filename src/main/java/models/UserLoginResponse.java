package models;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String token;
    private String userId;

}
