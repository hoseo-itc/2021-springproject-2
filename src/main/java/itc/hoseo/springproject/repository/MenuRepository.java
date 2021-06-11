package itc.hoseo.springproject.repository;

import java.util.List;

import itc.hoseo.springproject.domain.Menu;

public interface MenuRepository {
	public Menu save(Menu menu);
	public List<Menu> findByShopNo (int shopNo);
	public List<Menu> findByMenuName (String menuName);
	public List<Menu> findAllMenu();
}
