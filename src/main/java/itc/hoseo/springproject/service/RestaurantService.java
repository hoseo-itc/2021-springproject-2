package itc.hoseo.springproject.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import itc.hoseo.springproject.domain.Menu;
import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.repository.MenuRepository;
import itc.hoseo.springproject.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    public void save(Restaurant rst) {
        Restaurant savedRst = restaurantRepository.save(rst);
        for (Menu mnu : rst.getMenus()) {
            mnu.setShopNo(savedRst.getNo());
            menuRepository.save(mnu);
        }
    }

    public Restaurant findByShopNo(int shopNo){
        Restaurant restaurant = restaurantRepository.findByShopNo(shopNo);
        if(restaurant == null) return null;
        restaurant.setMenus(menuRepository.findByShopNo(shopNo));
        return restaurant;
    }

    public List<Restaurant> findByMenuOrName(String keyword){
        return Stream.concat(findByMenuName(keyword).stream() ,
                findByShopName(keyword).stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Restaurant> findByMenuName(String name) {
        return menuRepository.findByMenuName(name)
                .stream()
                .map(m -> {
                    Restaurant restaurant = restaurantRepository.findByShopNo(m.getShopNo());
                    if (restaurant != null) {
                        restaurant.setMenus(menuRepository.findByShopNo(m.getShopNo()));
                        return restaurant;
                    } else {
                        return null;
                    }
                })
                .filter(r -> {
                    return r != null;
                })
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Restaurant> findByShopName(String shopName) {
        List<Restaurant> rslt = restaurantRepository.findByShopName(shopName);
        rslt.stream()
                .forEach(r -> {
                    r.setMenus(menuRepository.findByShopNo(r.getNo()));
                });
        return rslt;
    }

    public List<Restaurant> findByCategory(String category) {
        List<Restaurant> rslt = restaurantRepository.findByCategory(category);
        for (Restaurant item : rslt) {
            item.setMenus(menuRepository.findByShopNo(item.getNo()));
        }
        return rslt;
    }


    public List<Restaurant> findAll() {
        List<Restaurant> rslt = restaurantRepository.findAll();
        for (Restaurant rst : rslt) {
            rst.setMenus(menuRepository.findByShopNo(rst.getNo()));
        }
        return rslt;
    }

    public List<Menu> findAllMenu() {
        List<Menu> rslt = menuRepository.findAllMenu();
        return rslt;
    }

    public int countRestaurant() {
        return restaurantRepository.findAll().size();
    }

    public int countMenu() {
        return menuRepository.findAllMenu().size();
    }


}
