public class Main {
    public static void main(String[] args) {
        try {
            GUI g = GUI.getSharedApplication();
            g.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}