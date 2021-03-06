package itc.hoseo.springproject.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import itc.hoseo.springproject.domain.Menu;
import itc.hoseo.springproject.domain.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InitService {

    @Autowired
    RestaurantService service;

    @Transactional
    public void initRestaurantsByNaver() {
        String url = "https://map.naver.com/v5/api/search";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("caller", "pcweb");
        map.add("query", "음식점");
        map.add("type", "all");
        map.add("searchCoord", "126.8639991000001;37.554732100000265");
        map.add("page", "1");
        map.add("displayCount", "100");
        map.add("isPlaceRecommendationReplace", "true");
        map.add("lang", "ko");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ArrayNode list = (ArrayNode) restTemplate.postForObject(url, request, JsonNode.class).at("/result/place/list");
        // System.out.println(list.toString());

        // x,y 경도 위도 속성
        // tel 전화번호
        // 영업시간 자체로 보여주기
        // categoryName
        list.forEach(c -> {
            try {
                Restaurant rst = new Restaurant();
                rst.setShop_name(c.at("/name").textValue()); // om.treeToValue(c, Restaurant.class); //알아서 관련 속성값을 맞춰주는 친구
                rst.setShop_address(c.at("/roadAddress").textValue());

                List<String> category = new ArrayList<>();
                ArrayNode categoryNode = (ArrayNode) c.at("/category");
                for (JsonNode node : categoryNode) {
                    String txt = node.textValue();
                    if (txt.contains(",")) {
                        category.addAll(Arrays.asList(txt.split(",")));
                    } else {
                        category.add(txt);
                    }
                }
                rst.setCategory(category.stream().collect(Collectors.joining(",")));
                rst.setPhone(c.at("/tel").textValue());
                rst.setOpening_hour(c.at("/bizhourInfo").textValue());
                rst.setShop_desc(c.at("/microReview/0").textValue());
                rst.setThumbnail_photo(c.at("/thumUrl").textValue());
                rst.setX(c.at("/x").textValue());
                rst.setY(c.at("/y").textValue());
                // System.out.println(rst);

                List<Menu> menus = new ArrayList<>();

                for (String menuStr : c.at("/menuInfo").textValue().split("\\|")) {
                    Menu mnu = new Menu();
                    menuStr = menuStr.trim();
                    String tt = "";
                    for (String menuSt : menuStr.split(" ")) {

                        if (menuSt.contains(",")) {
                            mnu.setMenuName(tt.trim());
                            mnu.setCost(Integer.parseInt(menuSt.replaceAll(",", "")));
                            // System.out.println(mnu);
                        } else {
                            tt = tt + menuSt;
                            tt = tt + " ";
                        }
                    }
                    if (StringUtils.isEmpty(mnu.getMenuName()) == false) {
                        menus.add(mnu);
                    }

                }
                rst.setMenus(menus);
                service.save(rst);
            } catch (Exception ex) {
                log.error("식당 추가중 오류 발생 {} ", ex);
            }
        });
    }

    @Transactional
    public void initRestaurantsByYogiyo()  throws JsonParseException, IOException {
        RestTemplate restTemplate = new RestTemplate();
//        HttpComponentsClientHttpRequestFactory factory =
//                new HttpComponentsClientHttpRequestFactory();
//
//        HttpClient httpClient =
//                HttpClientBuilder.create()
//                        .setRedirectStrategy(new LaxRedirectStrategy())
//                        .build();
//
//        factory.setHttpClient(httpClient);
//
//        restTemplate.setRequestFactory(factory);

        String url = "https://www.yogiyo.co.kr/api/v1/restaurants-geo/";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("x-apikey", "iphoneap");
        headers.set("x-apisecret", "fe5183cc3dea12bd0ce299cf110a75a2");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Safari/537.36");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("items", "10")
                .queryParam("lat", "37.564845394375")
                .queryParam("lng", "126.838912104684")
                .queryParam("order", "rank");

       ResponseEntity<String> exchange = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(exchange.getBody());

        System.out.println(node.textValue());
//        ArrayNode list = (ArrayNode) restTemplate.postForObject(url, request, JsonNode.class).at("/restaurants");
//        System.out.println(list.toString());

        // x,y 경도 위도 속성
        // tel 전화번호
        // 영업시간 자체로 보여주기
        // categoryName
//        list.forEach(c -> {
//            try {
//                Restaurant rst = new Restaurant();
//                rst.setShop_name(c.at("/name").textValue()); // om.treeToValue(c, Restaurant.class); //알아서 관련 속성값을 맞춰주는 친구
//                rst.setShop_address(c.at("/roadAddress").textValue());
//
//                List<String> category = new ArrayList<>();
//                ArrayNode categoryNode = (ArrayNode) c.at("/category");
//                for (JsonNode node : categoryNode) {
//                    String txt = node.textValue();
//                    if (txt.contains(",")) {
//                        category.addAll(Arrays.asList(txt.split(",")));
//                    } else {
//                        category.add(txt);
//                    }
//                }
//                rst.setCategory(category.stream().collect(Collectors.joining(",")));
//                rst.setPhone(c.at("/tel").textValue());
//                rst.setOpening_hour(c.at("/bizhourInfo").textValue());
//                rst.setShop_desc(c.at("/microReview/0").textValue());
//                rst.setThumbnail_photo(c.at("/thumUrl").textValue());
//                rst.setX(c.at("/x").textValue());
//                rst.setY(c.at("/y").textValue());
//                // System.out.println(rst);
//
//                List<Menu> menus = new ArrayList<>();
//
//                for (String menuStr : c.at("/menuInfo").textValue().split("\\|")) {
//                    Menu mnu = new Menu();
//                    menuStr = menuStr.trim();
//                    String tt = "";
//                    for (String menuSt : menuStr.split(" ")) {
//
//                        if (menuSt.contains(",")) {
//                            mnu.setMenuName(tt.trim());
//                            mnu.setCost(Integer.parseInt(menuSt.replaceAll(",", "")));
//                            // System.out.println(mnu);
//                        } else {
//                            tt = tt + menuSt;
//                            tt = tt + " ";
//                        }
//                    }
//                    if (StringUtils.isEmpty(mnu.getMenuName()) == false) {
//                        menus.add(mnu);
//                    }
//
//                }
//                rst.setMenus(menus);
//                service.save(rst);
//            } catch (Exception ex) {
//                log.error("식당 추가중 오류 발생 {} ", ex);
//            }
//        });
    }
}
