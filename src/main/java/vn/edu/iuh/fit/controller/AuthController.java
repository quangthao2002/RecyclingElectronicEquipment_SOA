package vn.edu.iuh.fit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.exceptions.UserAlreadyExistsException;
import vn.edu.iuh.fit.models.User;
import vn.edu.iuh.fit.request.LoginRequest;
import vn.edu.iuh.fit.response.JwtResponse;
import vn.edu.iuh.fit.security.jwt.JwtUtils;
import vn.edu.iuh.fit.security.user.QuoteUserDetails;
import vn.edu.iuh.fit.services.IUserService;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful!");
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody User user){
        try{
            userService.registerAdmin(user);
            return ResponseEntity.ok("Registration successful!");
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request){
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);// luu thong tin xac thuc cua user
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        QuoteUserDetails quoteUserDetails = (QuoteUserDetails) authentication.getPrincipal(); // lay thong tin user tu doi tuong xac thuc
        List<String> roles = quoteUserDetails.getAuthorities() // lay danh sach cac quyen cua user tu userDetails
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(new JwtResponse(
                quoteUserDetails.getId(),
                quoteUserDetails.getEmail(),
                jwt,
                roles));
    }
}
