package com.schary.ssoserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import com.schary.SsoServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SsoServerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringContextTest
{
	@Test
    public void whenLoadApplication_thenSuccess() {
		
	}
}
