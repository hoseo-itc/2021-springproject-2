package itc.hoseo.springproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenu {
    private Menu menu;
    private int count;
    private int cost; //해당 메뉴에 수량에 따른 가격을 저장.
}
