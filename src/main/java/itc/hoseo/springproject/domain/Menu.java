package itc.hoseo.springproject.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
	private int no; 
	private int shopNo; 
	private String menuName;
	private String foodPhoto;
	private int cost;
}
