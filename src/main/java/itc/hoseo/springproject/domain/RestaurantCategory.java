package itc.hoseo.springproject.domain;

public enum RestaurantCategory {
    chicken("치킨"),
    dessert("디저트"),
    feet("족발"),
    japan("일식"),
    korean("한식"),
    pizza("피자"),
    snackbar("분식");

    final String[] desc;

    private RestaurantCategory(String... desc){
        this.desc = desc;
    }

    public String getDesc(){
        return this.desc[0];
    }
}
