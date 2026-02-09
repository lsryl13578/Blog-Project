package com.mysite.blog_project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.blog_project.config.TestJacksonConfig;
import com.mysite.blog_project.config.jwt.JwtFactory;
import com.mysite.blog_project.config.jwt.JwtProperties;
import com.mysite.blog_project.domain.RefreshToken;
import com.mysite.blog_project.domain.User;
import com.mysite.blog_project.dto.CreateAccessTokenRequest;
import com.mysite.blog_project.repository.RefreshTokenRepository;
import com.mysite.blog_project.repository.UserRepository;

@SpringBootTest
@Import(TestJacksonConfig.class)
class TokenApiControllerTest {

	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	JwtProperties jwtProperties;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RefreshTokenRepository refreshTokenRepository;
	
	@BeforeEach
	public void mockMvcSetup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
		userRepository.deleteAll();
	}
	
	@DisplayName("createNewAccessToken: 새로운 액세스 토큰을 발급한다.")
	@Test
	public void createNewAccessToken() throws Exception {
		// given
		final String url = "/api/token";
		
		User testUser = userRepository.save(User.builder()
				.email("user@gmail.com")
				.password("test")
				.build());
		
		String refreshToken = JwtFactory.builder()
				.claims(Map.of("id", testUser.getId()))
				.build()
				.createToken(jwtProperties);
		
		refreshTokenRepository.save(new RefreshToken(testUser.getId(), refreshToken));
		
		CreateAccessTokenRequest request = new CreateAccessTokenRequest();
		request.setRefreshToken(refreshToken);
		final String requestBody = objectMapper.writeValueAsString(request);
		
		// when
		ResultActions resultActions = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(requestBody));
		
		// then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.accessToken").isNotEmpty());
	}
}
