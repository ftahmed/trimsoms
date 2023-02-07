package me.ftahmed.bootify.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import me.ftahmed.bootify.domain.User;
import me.ftahmed.bootify.repos.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .map(DaoUserDetails::new)
            .orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", username)
                ));
    }

    public static class DaoUserDetails implements UserDetails {
        private User user;
         
        public DaoUserDetails(User user) {
            this.user = user;
        }
     
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_"+r.getRoleName())).collect(Collectors.toList());
            // SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
            // return Collections.singletonList(authority);
        }
     
        @Override
        public String getPassword() {
            return user.getPassword();
        }
     
        @Override
        public String getUsername() {
            return user.getUsername();
        }
     
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }
     
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }
     
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
     
        @Override
        public boolean isEnabled() {
            return true;
        }
     
    }
}
