package com.enroll.security.service.impl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enroll.common.DateUtils;
import com.enroll.core.dao.EnrollmentDao;
import com.enroll.core.entity.User;
import com.enroll.security.service.SecurityService;

@Service("securityService")
@Transactional("txManager")
public class SecurityServiceImpl implements SecurityService, UserDetailsService {

	private static final Logger LOGGER = LogManager.getLogger(SecurityService.class);
    
	@Autowired
	private EnrollmentDao enrollmentDao;
    
    @Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	LOGGER.info("User {} log onto application at {}.", username, LocalDateTime.now().format(DateUtils.YYYY_MM_DD));
		User user = enrollmentDao.readUserByName(username);
        user.addAuthority(new SimpleGrantedAuthority(""));
		return user;
	}
}
