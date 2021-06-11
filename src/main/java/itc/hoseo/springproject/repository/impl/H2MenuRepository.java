package itc.hoseo.springproject.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import itc.hoseo.springproject.domain.Menu;
import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.repository.MenuRepository;

@Repository
public class H2MenuRepository implements MenuRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public Menu save(Menu menu) {
		String sql = "insert into menu(shop_no, menu_name, food_photo, cost) values(?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, menu.getShopNo());
				ps.setString(2, menu.getMenuName());
				ps.setString(3, menu.getFoodPhoto());
				ps.setInt(4, menu.getCost());

				return ps;
			}
		}, keyHolder);

		int no = (int) keyHolder.getKeys().get("no");
		menu.setNo(no);
		return menu;
	}

	@Override
	public List<Menu> findByShopNo(int shopNo) {
		return template.query("select * from menu where shop_no = ?", new BeanPropertyRowMapper<Menu>(Menu.class),
				shopNo);
	}

	@Override
	public List<Menu> findAllMenu() {
		return template.query("select * from menu", new BeanPropertyRowMapper<Menu>(Menu.class));
	}

	@Override
	public List<Menu> findByMenuName(String menuName) {
		return template.query("select * from menu where menu_name = ?", new BeanPropertyRowMapper<Menu>(Menu.class), menuName);
	}

}
