package itc.hoseo.springproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import itc.hoseo.springproject.domain.*;
import itc.hoseo.springproject.domain.dto.CartDTO;
import itc.hoseo.springproject.domain.dto.OrderDTO;
import itc.hoseo.springproject.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itc.hoseo.springproject.service.OrderService;
import itc.hoseo.springproject.service.RestaurantService;

@Controller
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private OrderService orderService;

	@GetMapping("/res")
	public String list(RestaurantCategory category, ModelMap mm) {
		mm.put("list", restaurantService.findByCategory(category.getDesc()));
		return "rest/list";
	}

	@PostMapping("/kakaopay")
	public String kakapay(OrderDTO order) {
		orderService.save(order);
		return "redirect:/";
	}

	@GetMapping("/search")
	public String list(String keyword, ModelMap mm) {
		mm.put("list", restaurantService.findByMenuOrName(keyword));
		return "rest/list";
	}

	@GetMapping("/detail")
	public String detail(@RequestParam int shopNo, ModelMap mm,HttpSession session) {
		Restaurant restaurant = restaurantService.findByShopNo(shopNo);
		session.setAttribute("shopId", shopNo);
		if (restaurant == null) {
			return "redirect:https://http.cat/404";
		}
		mm.put("res", restaurant);
		return "rest/rest";
	}

	@GetMapping("/pay")
	public String detail(HttpSession session) {
		if (session.getAttribute("carts") == null) {
			return "redirect:https://http.cat/404";
		}
		return "rest/pay";
	}

	@PostMapping("/addCart")
	public String addCart(CartDTO dto, HttpSession session, ModelMap mm) {
		if (dto.getCount() == 0) {
			return "redirect:https://http.cat/404";
		} else {
			if (session.getAttribute("carts") == null) {
				session.setAttribute("carts", new HashMap<Integer, OrderMenu>());
			}

			Map<Integer, OrderMenu> menuMap = (Map<Integer, OrderMenu>) session.getAttribute("carts");
			Menu menu = menuRepository.findByMenuNo(dto.getMenuNo());
			OrderMenu orderMenu = new OrderMenu(menu, dto.getCount(), (dto.getCount() * menu.getCost()));

			if (menuMap.isEmpty()) {
				menuMap.put(dto.getMenuNo(), orderMenu);
				// ????????? ?????? ???
			} else if (menuMap.containsKey(orderMenu.getMenu().getNo())) {
				// ????????? ????????? ?????????
				int oldPoint = 0;
				oldPoint = menuMap.get(orderMenu.getMenu().getNo()).getCount();

				OrderMenu ord = new OrderMenu(menu, oldPoint + dto.getCount(),
						(oldPoint + dto.getCount()) * menu.getCost());
				menuMap.put(orderMenu.getMenu().getNo(), ord);
			} else {
				menuMap.put(dto.getMenuNo(), orderMenu);
				// ????????? ????????? ??????
			}
			return "redirect:/detail?shopNo=" + menu.getShopNo();
		}
	}

	@GetMapping("/shopList")
	public String list(ModelMap mm) {
		mm.put("restList", restaurantService.findAll());
		return "rest/shopList.html";
	}

	@GetMapping("/menuList")
	public String listM(ModelMap mm) {
		mm.put("menuList", restaurantService.findAllMenu());
		return "rest/menuList.html";
	}

//	@GetMapping("/selectResult")
//	public String listSM(ModelMap mm) {
//		mm.put("menuList", restaurantService.findAllMenu());
//		mm.put("restList", restaurantService.findAll());
//		return "rest/selectResult.html";
//	}

	@GetMapping("/select")
	public String select() {
		return "rest/select.html";
	}

//    @PostMapping("/search")
//    public String search(
//            ModelMap mm,
//            @RequestParam("input") String str,
//            @RequestParam("option") int opt,
//            HttpSession session) {
//        if (opt == 1) {
//            session.setAttribute("restList", restaurantService.findByShopName(str));
//            mm.put("restList", restaurantService.findByShopName(str));
//        } else {
//            session.setAttribute("menuList", restaurantService.findByMenuName(str));
//            mm.put("menuList", restaurantService.findByMenuName(str));
//        }
//        return "redirect:/selectResult";
//    }

}
