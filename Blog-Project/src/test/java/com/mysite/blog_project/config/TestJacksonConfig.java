/** 테스트 환경에서 Jackson ObjectMapper를 명시적으로 등록하기 위한 클래스
 * 
 * - Spring Boot 테스트 실행 시 특정 테스트(TokenApiControllerTest 등)에서는
 *   Jackson 자동 설정이 로드되지 않아 ObjectMapper Bean이 주입되지 않는 문제가 발생할 수 있다.
 *   
 * - 이를 해결하기 위해 test 전용 @TestConfiguration을 사용해 ObjectMapper를 Bean으로 직접 등록한다.
 * 
 * - 운영 환경(@Configuration)에는 영향을 주지 않으며, 테스트 컨텍스트에만 적용된다.
*/
package com.mysite.blog_project.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@TestConfiguration
public class TestJacksonConfig {
	
	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
