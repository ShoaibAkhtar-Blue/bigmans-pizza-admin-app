package blue.project.bigmanspizzaadminapp.Model;

public class BakeryItem {
    private String itemName;
    private String itemImage = "NA";

    public BakeryItem(String itemName) {
        this.itemName = itemName;
    }

    public BakeryItem(String itemName, String itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
