import java.io.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class FileBlock extends JPanel {
	private JFileChooser chooser = new JFileChooser();
	private JTextField tf = new JTextField(24);
	private JButton btn = new JButton("찾기");
	private File src = null;

	public FileBlock() {
		tf.setEditable(false);
		tf.setBackground(Color.WHITE);
		add(tf);
		
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ret = chooser.showOpenDialog(FileBlock.this);
				if (ret != JFileChooser.APPROVE_OPTION)
					return;

				src = chooser.getSelectedFile();
				tf.setText(chooser.getSelectedFile().getPath());
			}
		});
	}

	public File getDir() { return src; }
}
