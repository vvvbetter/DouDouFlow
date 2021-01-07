package org.doudou.doudouflow.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityProperties securityProperties;

//	@Autowired
//	private ConfigurableApplicationContext applicationContext;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Autowired
//	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.debug("loadUserByUsername:{}", username);

		List<SimpleGrantedAuthority> list = new ArrayList<>();

		org.springframework.boot.autoconfigure.security.SecurityProperties.User user = securityProperties.getUser();

		if (user.getName().equals(username)) {
			return new org.springframework.security.core.userdetails.User(user.getName(),
					passwordEncoder.encode(user.getPassword()), list);
		} else {
			throw new UsernameNotFoundException("用户名或密码错误");
//			if (applicationContext.containsBean("dataSource")) {
//				User user2 = userRepository.findById(username)
//						.orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));
//				return new org.springframework.security.core.userdetails.User(user2.getName(), user2.getPassword(),
//						list);
//
//			} else {
//				throw new UsernameNotFoundException("用户名或密码错误");
//			}

		}

	}

}
