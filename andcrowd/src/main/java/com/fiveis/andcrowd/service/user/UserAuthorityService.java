package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorityService implements UserDetailsService {
    private final UserJPARepository userJPARepository;

    @Autowired
    public UserAuthorityService(UserJPARepository userJPARepository){
        this.userJPARepository = userJPARepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException{
        return userJPARepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));
    }
}