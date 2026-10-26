import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + "}";
    }
}

public class ProductInventory {

    public static void runOperations(List<Product> list, String listType) {
        System.out.println("--- Running operations on " + listType + " ---");
        list.add(new Product("Laptop", 999.99));
        list.add(new Product("Phone", 499.99));
        list.add(new Product("Headphones", 79.99));

        for (Product p : list) {
            System.out.println(p);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> userList = new ArrayList<>();

        System.out.println("--- Product Inventory Input ---");
        System.out.print("How many products do you want to add? ");
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.print("Enter name for product " + (i + 1) + ": ");
            String name = scanner.nextLine();

            System.out.print("Enter price for product " + (i + 1) + ": ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            userList.add(new Product(name, price));
        }

        System.out.println("\nYour Entered Products:");
        for (Product p : userList) {
            System.out.println(p);
        }
        System.out.println("--------------------------------\n");

        try {
            List<Product> arrayList = new ArrayList<>();
            List<Product> linkedList = new LinkedList<>();

            runOperations(arrayList, "ArrayList");
            runOperations(linkedList, "LinkedList");

            System.out.println("NOTE: Both lists gave identical results because both implement List.");
            System.out.println("ArrayList is faster for get(i) (direct index); LinkedList is better for");
            System.out.println("adding/removing at the ends. For this small data the difference is not visible.");
        } catch (RuntimeException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}