import java.util.List;

public class Order {
   // private String[] ingredients;
    private List<String> ingredients;

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    //    public Order() {
//    }
//
//    public Order(String[] ingredients) {
//        this.ingredients = ingredients;
//    }
//
//    public String[] getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(String[] ingredients) {
//        this.ingredients = ingredients;
//    }
}
