import java.io.*;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String[] products;
    private final int[] prices;
    private final int[] numb;
    private int sum = 0;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.numb = new int[products.length];
    }

    public Basket(String[] products, int[] prices, int[] numb) {
        this.products = products;
        this.prices = prices;
        this.numb = numb;
    }

    public void addToCart(int productNum, int numb) {
        this.numb[productNum] += numb;
    }

    public void printCart() {
        System.out.println("В вашей корзине:");
        for (int i = 0; i < products.length; i++) {
            if (numb[i] > 0) {
                System.out.println(products[i] + " " + numb[i] + " шт, " + prices[i] + " руб/шт. Итого: " + (numb[i] * prices[i]) + " руб");
                sum += (numb[i] * prices[i]);
            }
        }
        System.out.println("Сумма всей покупки: " + sum + " руб");
    }

    public void saveTxt(File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file);) {
            for (int i = 0; i < products.length; i++) {
                if (numb[i] > 0) {
                    out.write(products[i] + " " + prices[i] + " " + numb[i] + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBin(File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File file) {
        Basket basket = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int size = 5;
            String[] products = new String[size];
            int[] prices = new int[size];
            int[] numb = new int[size];
            String s;
            int i = 0;
            while ((s = br.readLine()) != null) {
                String[] parts = s.split(" ");
                products[i] = parts[0];
                prices[i] = Integer.parseInt(parts[1]);
                numb[i] = Integer.parseInt(parts[2]);
                i++;
            }
            basket = new Basket(products, prices, numb);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        Basket basket = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }
}