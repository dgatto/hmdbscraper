public class Main {
    public static void main(String[] args) {
        try {
            GUI g = new GUI();
            g.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}