package com.zs;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

/**
 * Created by cloud on 2019/4/26.
 */
@Component("userDetailsService")
public class MyInMemoryUserDetailsManager extends InMemoryUserDetailsManager {

}
