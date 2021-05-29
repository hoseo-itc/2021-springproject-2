package itc.hoseo.springproject.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import itc.hoseo.springproject.domain.dto.UserJoinFormDTO;
import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
public class KakaoLoginService {

	private String client_id = "7720e2e195b7f09bf3215d501c9ffb09";
	private String redirect_url = "http://localhost:8080/auth";
	
	
	private UserJoinFormDTO userJoinDTO;

	public UserJoinFormDTO kakaoLogin(String accessToken) throws JsonMappingException, JsonProcessingException {
		if (accessToken == null) {
			throw new RuntimeException("사용자 정보가 존재하지 않습니다.");
		}

		RestTemplate rt = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + accessToken);
		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(header);

		ResponseEntity<String> response = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, restRequest,
				String.class);

		ObjectMapper om = new ObjectMapper();
		JsonNode node = om.readTree(response.getBody());
		String email = node.at("/kakao_account/email").textValue();
		String imgUrl = node.at("/kakao_account/profile/thumbnail_image_url").textValue();
		String nickName = node.at("/kakao_account/profile/nickname").textValue();
		
		UserJoinFormDTO rslt = new UserJoinFormDTO();
		rslt.setEmail(email);
		rslt.setImgUrl(imgUrl);
		rslt.setNickName(nickName);
		
		return rslt;
		
	}
	
	public String getKakaoAccessToken(String code) {

		final String RequestUrl = "https://kauth.kakao.com/oauth/token"; // Host
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();

		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id", client_id)); // REST API KEY
		postParams.add(new BasicNameValuePair("redirect_uri", redirect_url)); // 리다이렉트 URI
		postParams.add(new BasicNameValuePair("code", code)); // 로그인 과정중 얻은 code 값

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);

		JsonNode returnNode = null;

		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));

			final HttpResponse response = client.execute(post);
			final int responseCode = response.getStatusLine().getStatusCode();

			System.out.println("\nSending 'POST' request to URL : " + RequestUrl);
			System.out.println("Post parameters : " + postParams);
			System.out.println("Response Code : " + responseCode);

			// JSON 형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();

			returnNode = mapper.readTree(response.getEntity().getContent());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return returnNode.get("access_token").asText();
	}
}
