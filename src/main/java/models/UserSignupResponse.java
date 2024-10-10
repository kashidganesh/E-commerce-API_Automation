package models;


import lombok.Data;

@Data
public class UserSignupResponse {

    private String id;
    private String email;
    private String createdAt;

}
