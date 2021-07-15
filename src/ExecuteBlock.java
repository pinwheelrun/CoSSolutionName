import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;

public class ExecuteBlock extends JPanel {
	private final int PROB = 0;
	private final int SOL = 1;
	private int flag = SOL;

	private JRadioButton problem = new JRadioButton("문제 텍스트 파일");
	private JRadioButton solution = new JRadioButton("초기 Solution class 파일", true);
	private JButton btn = new JButton("실행");

	public ExecuteBlock(FileBlock fileBlock) {
		add(problem);
		add(solution);

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(problem);
		btnGroup.add(solution);

		problem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					flag = PROB;
			}
		});
		solution.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					flag = SOL;
			}
		});

		add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File dir = fileBlock.getDir();
				if (dir == null)
					return;

				if (flag == SOL) {
					solutionConverter(dir);
				}
				else {
					problemConverter(dir);
				}
			}
		});
	}

	private void solutionConverter(File dir) {
		File[] list = dir.listFiles();
		String dirPath = dir.getPath();
		File tmp = null;

		FileReader fin = null;
		FileWriter fout = null;
		Scanner scan = null;

		try {
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory())
					continue;

				String newName = nameMaker(list[i]);
				if (newName == null)
					continue;
				else
					tmp = new File(dirPath + "\\" + newName + ".java");

				fin = new FileReader(list[i]);
				fout = new FileWriter(tmp);
				scan = new Scanner(fin);

				while (scan.hasNext()) {
					String line = scan.nextLine();
					if (line.contains("Solution")) {
						line = line.replaceAll("Solution", newName);
					}
					fout.write(line + "\r\n");
				} // end of while

				scan.close();
				fout.close();
				fin.close();

				list[i].delete();
			} // end of for-loop
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(this, "오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private String nameMaker(File src) {
		String origName = src.getName();
		if (!origName.contains("initial_code"))
			return null;

		StringBuilder sb = new StringBuilder("Solution");
		int idx = origName.indexOf("차");
		sb.append(origName.substring(0, idx));
		sb.append("x");
		idx = origName.lastIndexOf(" ");
		sb.append(origName.substring(idx + 1, origName.indexOf("_")));
		return sb.toString();
	}

	private void problemConverter(File dir) {
		File[] list = dir.listFiles();
		String dirPath = dir.getPath();
		File tmp = null;

		FileReader fin = null;
		FileWriter fout = null;
		Scanner scan = null;
		try {
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory())
					continue;

				String name = list[i].getName();
				if (!name.contains("java언어description"))
					continue;
				name = name.substring(0, name.indexOf(".txt"));
				tmp = new File(dirPath + "\\" + name + ".html");
				fin = new FileReader(list[i]);
				fout = new FileWriter(tmp);
				scan = new Scanner(fin);

				fout.write("<html>\n");
				fout.write("\t<head>\n");
				fout.write("\t\t<title>" + name + "</title>\n");
				fout.write("\t</head>\n");
				fout.write("\n");
				fout.write("\t<body>\n");

				while (scan.hasNext()) {
					String line = scan.nextLine();
					if (line.contains("#문제"))
						fout.write("\t\t\t<hr>\n");

					fout.write("\t\t\t<p>");
					int idx = line.indexOf("https");
					if (idx != -1)
						line = "<img src=\"" + line.substring(idx, line.lastIndexOf(")")) + "\">";
					fout.write(line);
					fout.write("</p>\n");
				} // end of while

				fout.write("\t</body>\n");
				fout.write("</html>");

				scan.close();
				fout.close();
				fin.close();

				list[i].delete();
			} // end of for-loop
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(this, "오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
