import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TesteJogo extends JFrame {

	private JPanel contentPane;
	private JLabel sacola;
	private JButton pah;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TesteJogo frame = new TesteJogo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TesteJogo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 668, 466);
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Image img = Toolkit.getDefaultToolkit().getImage(TesteJogo.class.getResource("wallpaper.png"));
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}

		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING).addGap(0, 644, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING).addGap(0, 419, Short.MAX_VALUE));
		contentPane.setLayout(gl_contentPane);
		threadJogo();
	}

	public void threadJogo() {
		try {
			criarSacola();
			moverSacola();
			criarPah();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		new Thread(() -> {
			while (true) {
				try {
//					System.out.println("ThreadJogo funcionando...");
					refreshPanel();
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	private void moverSacola() throws InterruptedException {
		new Thread(() -> {
			try {
				while (true) {

//					System.out.println("FUNCIONA TRHEAD FILHO DUMA PUTA");
//					System.out.println("Qtd sacolas " + contentPane.getComponentCount());
//					System.out.println("Executando thread mover sacola");
//					System.out.println("Sacola é null? " + sacola == null ? "Sim" : "Não");
					// System.out.println("sacola.y "+sacola.getY());
					int x = 0;

					for (int i = 0; i < contentPane.getComponentCount(); i++) {
						int randomNum = ThreadLocalRandom.current().nextInt(-100, 100);
						// sacola = (JLabel) contentPane.getComponent(i);
						// while (contentPane.getComponent(i).getY() < contentPane.getHeight()) {
//						System.out.println("Numero random: " + randomNum);
						// System.out.println("Quantidades de sacolas: " +
						// (contentPane.getComponentCount())
						// + " verificando sacola sacola.y " + sacola.getY());
						if (contentPane.getComponent(i) != null && contentPane.getComponent(i) instanceof JLabel)
							if (randomNum < 0) {
								contentPane.getComponent(i).setBounds(
										contentPane.getComponent(i).getX() <= 64
												? (contentPane.getComponent(i).getX() + (randomNum * (-1)))
												: contentPane.getComponent(i).getX() - randomNum,
										(contentPane.getComponent(i).getY() + 45), 64, 64);
							} else {
								contentPane.getComponent(i).setBounds(
										(contentPane.getComponent(i)
												.getX() > (contentPane.getComponent(i).getWidth() - 80)
														? contentPane.getComponent(i).getX() - randomNum
														: contentPane.getComponent(i).getX() + randomNum),
										(contentPane.getComponent(i).getY() + 45), 64, 64);
							}
						refreshPanel();
						Thread.sleep(250);
//							}
					}

					for (int i = 0; i < contentPane.getComponentCount(); i++) {
						if (contentPane.getComponent(i) != null) {
							if (contentPane.getComponent(i).getY() > contentPane.getHeight()) {
								contentPane.remove(contentPane.getComponent(i));
							}
						}
						refreshPanel();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}).start();
	}

	private void refreshPanel() {
		contentPane.revalidate();
		contentPane.repaint();
	}

	private void criarSacola() throws InterruptedException {

		Thread criaSacola = new Thread(() -> {
			while (true) {
				try {
					sacola = new JLabel("sacola");
					sacola.setIcon(new ImageIcon("src/saco-de-lixo.png"));
					sacola.setBounds(contentPane.getWidth() / 2, 10, 64, 65);
					contentPane.add(sacola);
					refreshPanel();
					System.out.println("Sacola criada");
					Thread.sleep((1000 * 3));
				} catch (Exception e) {
				}

			}
		});
		criaSacola.start();

	}

	public void criarPah() {
		this.pah = new JButton("PÁ", new ImageIcon("src/pa.png"));
		pah.setOpaque(false);
		pah.setContentAreaFilled(false);
		pah.setBorderPainted(false);
		System.out.println("Pane.W" + contentPane.getWidth() + " Pane.Y" + contentPane.getY());
		this.pah.setBounds(450, 300, 65, 65);
		contentPane.add(pah);
		refreshPanel();

	}
}
