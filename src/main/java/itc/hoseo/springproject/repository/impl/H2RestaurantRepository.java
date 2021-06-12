package itc.hoseo.springproject.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.repository.RestaurantRepository;

@Repository
public class H2RestaurantRepository implements RestaurantRepository{


	@Autowired
	private JdbcTemplate template;
	
	@Override
	public Restaurant save(Restaurant restaurant) {
		String sql = "insert into restaurant(shop_name, shop_address, category, phone, thumbnail_photo, opening_hour, x, y, shop_desc) values(?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, restaurant.getShop_name());
                ps.setString(2, restaurant.getShop_address());
                ps.setString(3, restaurant.getCategory());
                ps.setString(4, restaurant.getPhone());
                ps.setString(5, restaurant.getThumbnail_photo());
                ps.setString(6, restaurant.getOpening_hour());
                ps.setString(7, restaurant.getX());
                ps.setString(8, restaurant.getY());
                ps.setString(9, restaurant.getShop_desc());
                return ps;
            }
        }, keyHolder);

        int no = (int)keyHolder.getKeys().get("no");
        restaurant.setNo(no);
        return restaurant;
	}

	@Override
	public List<Restaurant> findAll() {
		return template.query("select * from restaurant", 
				new BeanPropertyRowMapper<Restaurant>(Restaurant.class));
	}

	@Override
	public List<Restaurant> findByShopName(String shopName) {
		return template.query("select * from restaurant where shop_name like '%' || ? || '%'",
				new BeanPropertyRowMapper<Restaurant>(Restaurant.class), shopName);
	}

	@Override
	public Restaurant findByShopNo(int shopNo) {
		try{
			return template.queryForObject("select * from restaurant where no = ?",
					new BeanPropertyRowMapper<Restaurant>(Restaurant.class), shopNo);
		}catch (IncorrectResultSizeDataAccessException dataAccessException){
			return null;
		}
	}

	@Override
	public List<Restaurant> findByCategory(String category) {
		return template.query("select * from restaurant where category like '%' || ? || '%'",
				new BeanPropertyRowMapper<Restaurant>(Restaurant.class), category);
	}

	@Override
	public Restaurant findByMoreLike(Integer cnt_like) {
		return template.queryForObject("select * from restaurant order by ? desc",
				new BeanPropertyRowMapper<Restaurant>(Restaurant.class), cnt_like);
	}

	@Override
	public Restaurant findByLessLike(Integer cnt_like) {
		return template.queryForObject("select * from restaurant order by ? asc",
				new BeanPropertyRowMapper<Restaurant>(Restaurant.class), cnt_like);
	}
	


}
