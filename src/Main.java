import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("basket.bin");
        String[] products = {"Молоко", "Яблоки", "Сыр", "картофель", "Хлеб"};
        int[] prices = {80, 50, 200, 35, 45};
        Basket basket;

        if (file.exists()) {
            // System.out.println("Загружаю корзину: ");
            basket = Basket.loadFromBinFile(file);
            basket.printCart();
            System.out.println();
        } else {
            basket = new Basket(products, prices);
        }

        System.out.println("Список товаров:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб");
        }

        while (true) {
            System.out.println("Выберите товар и количество.");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                basket.printCart();
                basket.saveBin(file);
                break;
            }

            String[] parts = input.split(" ");
            int numProduct = Integer.parseInt(parts[0]) - 1;
            int amount = Integer.parseInt(parts[1]);
            basket.addToCart(numProduct, amount);
        }
    }
}