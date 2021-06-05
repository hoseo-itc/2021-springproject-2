package itc.hoseo.springproject.repository;

<<<<<<< HEAD
<<<<<<< HEAD
import java.util.List;

import itc.hoseo.springproject.domain.Menu;
=======
import itc.hoseo.springproject.domain.menu;
>>>>>>> parent of d3246b3 (domain 구현)

public interface MenuRepository {
	public Menu save(Menu menu);
	public List<Menu> findByShopNo (int shopNo);
=======
import itc.hoseo.springproject.domain.menu;

public interface menuRepository {
	public void save(menu menu);
<<<<<<< HEAD
>>>>>>> parent of d3246b3 (domain 구현)
=======
>>>>>>> parent of d3246b3 (domain 구현)
}
