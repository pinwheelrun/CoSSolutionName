import java.awt.*;
import javax.swing.*;

public class ConverterFrame extends JFrame {
	private FileBlock top = new FileBlock();
	private JPanel bottom = new ExecuteBlock(top);

	public ConverterFrame() {
		super("CoS 기출문제 텍스트 일괄 편집기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.add(top, BorderLayout.NORTH);
		c.add(bottom, BorderLayout.SOUTH);

		ImageIcon icon = new ImageIcon("images\\description.png");
		c.add(new JLabel(icon));

		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ConverterFrame();
	}
}
