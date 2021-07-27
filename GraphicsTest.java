import javax.swing.JFrame;

public class GraphicsTest {
    public static void main(String args[]) {
        JFrame frame = new JFrame("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        Card card = new Card(1, 1);
        frame.add(card);
        frame.setVisible(true);
    }
}
