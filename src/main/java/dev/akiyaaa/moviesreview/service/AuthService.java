package dev.akiyaaa.moviesreview.service;

import dev.akiyaaa.moviesreview.models.DTO.request.LoginRequest;
import dev.akiyaaa.moviesreview.models.DTO.request.SignupRequest;
import dev.akiyaaa.moviesreview.models.DTO.response.JwtResponse;
import dev.akiyaaa.moviesreview.models.DTO.response.MessageResponse;
import dev.akiyaaa.moviesreview.models.ERole;
import dev.akiyaaa.moviesreview.models.Role;
import dev.akiyaaa.moviesreview.models.User;
import dev.akiyaaa.moviesreview.repositories.RoleRepository;
import dev.akiyaaa.moviesreview.repositories.UserRepository;
import dev.akiyaaa.moviesreview.utils.UserDetailImpl;
import dev.akiyaaa.moviesreview.utils.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        List<String> roles = userDetail.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetail.getId(),
                userDetail.getUsername(),
                userDetail.getEmail(),
                roles));
    }
    
    public ResponseEntity<?> registerUser(SignupRequest signupRequest){
        if (userRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error : Username is already taken!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error : Email is already taken!"));
        }
        
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));
        Set<String> listRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        
        if(listRoles == null){
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }else {
            listRoles.forEach(role -> {
                switch (role){
                    case "admin" :
                        Role admin = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(admin);
                        break;
                    case "mod" :
                        Role mod = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(mod);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    
}
