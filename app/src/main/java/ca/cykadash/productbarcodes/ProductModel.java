package ca.cykadash.productbarcodes;

public class ProductModel {

    private int id;
    private String name;
    private String sku;
    private String console;

    // constructors

    public ProductModel(int id, String name, String sku, String console) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.console = console;
    }

    public ProductModel() {
    }

    @Override
    public String toString() {
        return name + " (" + console + ")";
    }

    // getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }
}
