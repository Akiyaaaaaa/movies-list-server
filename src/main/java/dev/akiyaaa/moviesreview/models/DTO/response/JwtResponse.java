package dev.akiyaaa.moviesreview.models.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type="Bearer";
    @Getter
    private String id;
    @Getter
    private String username;
    @Getter
    private String email;
    @Getter
    private List<String> roles;
    
    public JwtResponse(String accessToken, String id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
    
   public String getAccessToken(){
        return token;
   }
   public void setAccessToken(String accessToken){
        this.token = accessToken;
   }
   public String getTokenType(){
        return type;
   }
   public void setTokenType(String tokenType){
        this.type = tokenType;
   }
    public void setId(String id) {
        this.id = id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
