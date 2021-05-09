import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import jaco.mp3.player.MP3Player;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class TesteJogo extends JFrame {

	private JPanel contentPane;
	private JLabel sacola;
	private JButton pah;
	private Retangulo rectangleSacola;
	private Retangulo rectanglePah;
	private List<Retangulo> sacolas = new ArrayList<Retangulo>();
	private JPanel panelVidas;
	private JMenuBar menuBar;
	private JMenu menuJogo;
	private JMenuItem menuItemJogar;
	private int pts = 0;
	private JLabel labelPts;
	private JLabel labelVidas;
	private final static int SACOLA_WIDTH = 65;
	private final static int SACOLA_HEIGHT = 65;
	private final static int PAH_WIDTH = 65;
	private final static int PAH_HEIGHT = 65;
	static TesteJogo frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new TesteJogo();
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
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				System.out.println("ComponentMoved");
				moverPah();
			}

			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("ComponentResized");
				moverPah();
			}

			@Override
			public void componentShown(ComponentEvent e) {
				System.out.println("ComponentShown");
				moverPah();
			}
		});
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == JFrame.MAXIMIZED_BOTH) {
					moverPah();
				}

				if (e.getNewState() == JFrame.NORMAL) {
					moverPah();
				}
			}
		});

		setTitle("Meu primeiro jogo ;)");
		int velocidadeX = 50;
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (pah.getX() >= pah.getWidth()) {
						System.out.println("você andou pra esquerda ;) pa.x " + pah.getX() + " pa.y" + pah.getY());
						pah.setBounds(pah.getX() - velocidadeX, contentPane.getHeight() - pah.getHeight(),
								pah.getWidth(), pah.getHeight());
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (pah.getX() <= (contentPane.getWidth() - (pah.getWidth() * 1.5))) {
						System.out.println("você andou pra esquerda ;) pa.x " + pah.getX() + " pa.y" + pah.getY());
						pah.setBounds(pah.getX() + velocidadeX, contentPane.getHeight() - pah.getHeight(),
								pah.getWidth(), pah.getHeight());
					}
				}
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 660);
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuJogo = new JMenu("Jogar");
		menuBar.add(menuJogo);

		menuItemJogar = new JMenuItem("Nogo jogo");
		menuJogo.add(menuItemJogar);
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Image img = Toolkit.getDefaultToolkit().getImage(TesteJogo.class.getResource("jogo3.png"));
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}

		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel panelPts = new JPanel();
		panelVidas = new JPanel();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(670, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panelVidas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panelPts, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(panelPts, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelVidas, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(512, Short.MAX_VALUE)));

		labelVidas = new JLabel("Vidas: 3");
		labelVidas.setFont(new Font("Tahoma", Font.BOLD, 14));
		GroupLayout gl_panelVidas = new GroupLayout(panelVidas);
		gl_panelVidas.setHorizontalGroup(gl_panelVidas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelVidas.createSequentialGroup().addContainerGap()
						.addComponent(labelVidas, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE).addContainerGap()));
		gl_panelVidas.setVerticalGroup(gl_panelVidas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelVidas.createSequentialGroup().addContainerGap().addComponent(labelVidas)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panelVidas.setLayout(gl_panelVidas);

		labelPts = new JLabel("Pontos: 0");
		labelPts.setFont(new Font("Tahoma", Font.BOLD, 14));
		GroupLayout gl_panelPts = new GroupLayout(panelPts);
		gl_panelPts.setHorizontalGroup(gl_panelPts.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPts.createSequentialGroup().addContainerGap()
						.addComponent(labelPts, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE).addContainerGap()));
		gl_panelPts.setVerticalGroup(gl_panelPts.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPts.createSequentialGroup().addContainerGap().addComponent(labelPts)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panelPts.setLayout(gl_panelPts);
		contentPane.setLayout(gl_contentPane);
		setContentPane(contentPane);

		threadJogo();
		playSongControlVol("songs/song.mp3", -10f);
	}

	protected void moverPah() {
		System.out.println("moverPah invocado!");
		System.out.println("Pane = " + contentPane.getWidth());
		this.pah.setBounds(contentPane.getWidth() / 2, (contentPane.getHeight() - pah.getHeight()), PAH_WIDTH,
				PAH_HEIGHT);
		refreshPanel();
	}

	public void threadJogo() {
		try {
			criarSacola();
			moverSacola();
			criarPah();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
//		new Thread(() -> {
//			while (true) {
//				try {
//					// refreshPanel();
//					// Thread.sleep(5);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();

	}

	private void moverSacola() throws InterruptedException {
		new Thread(() -> {
			try {
				while (true) {
					// refreshPanel();
					definirPosicaoSacola();
					verificarImpactoSacola();
					removerSacolaPerdida();
					Thread.sleep(20);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}).start();
	}

	public void verificarImpactoSacola() throws InterruptedException {

		for (int i = 0; i < contentPane.getComponentCount(); i++) {
			if (contentPane.getComponent(i) instanceof JLabel) {
				sacola = (JLabel) contentPane.getComponent(i);
				definirPosicaoRetanguloImpacto();
				if (rectanglePah.intersects(rectangleSacola)) {
					removerSacolaImpacto();
					criarExplosaoImpacto();
					definirPontuacao();
				}
			}
		}
	}

	private void definirPontuacao() throws InterruptedException {
		pts += 10;
		labelPts.setText("Pontos: " + pts);
		//adicionarInfoPts();
	}

	private void criarExplosaoImpacto() throws InterruptedException {
		JLabel explosao = new JLabel();
		explosao.setIcon(new ImageIcon("src/explosao.png"));
		explosao.setBounds(pah.getX(), pah.getY(), 64, 64);
		contentPane.add(explosao);
		refreshPanel();
		Thread.currentThread().sleep(20);
		contentPane.remove(explosao);
		playSong("songs/saco-estouro.mpeg");
		refreshPanel();
	}

	private void adicionarInfoPts() throws InterruptedException {
		JLabel pontos = new JLabel();
		pontos.setIcon(new ImageIcon("src/10pts.png"));
		pontos.setBounds(pah.getX() + 20, pah.getY() - 30, 100, 100);
		contentPane.add(pontos);
		// Thread.currentThread().sleep(50);
		contentPane.remove(pontos);
		contentPane.repaint();
	}

	private void removerSacolaImpacto() {
		contentPane.remove(sacola);
		sacola.setBounds(-100, contentPane.getHeight() + 100, 0, 0);
		contentPane.validate();
		contentPane.repaint();
	}

	private void removerSacolaImpacto(Retangulo sacola) {

		for (int i = 0; i < contentPane.getComponentCount(); i++) {
			if (contentPane.getComponent(i) instanceof JLabel) {
				JLabel sacolaRemover = (JLabel) contentPane.getComponent(i);
				if (sacolaRemover.getText().equals(sacola.getToken())) {
					contentPane.remove(sacolaRemover);
					sacolaRemover.setBounds(-6000, contentPane.getHeight() + 6000, 0, 0);
					sacolaRemover = null;
					// refreshPanel();
				}
			}
		}

	}

	private void removerSacolaPerdida() {

		// remover componentes da tela
		for (int i = 0; i < contentPane.getComponentCount(); i++) {
			if (contentPane.getComponent(i) != null && contentPane.getComponent(i) instanceof JLabel) {
				sacola = (JLabel) contentPane.getComponent(i);
				if (sacola.getY() > contentPane.getHeight()) {
					contentPane.remove(sacola);
					sacola.setBounds(-6000, contentPane.getHeight() + 6000, 0, 0);
					sacola = null;
					// refreshPanel();
				}
			}

		}
		// remover sacolas perdidas
		for (Iterator<Retangulo> i = sacolas.iterator(); i.hasNext();) {
			Retangulo sacolaRemover = i.next();
			if (sacolaRemover.getHeight() > contentPane.getHeight()) {
				sacolaRemover.setBounds(-5000, -1000, 0, 0);
				sacolaRemover = null;
				i.remove();
			}
		}
		// System.out.println("Qtd de sacolas lista: " + sacolas.size());

	}

	private void definirPosicaoSacola() throws InterruptedException {
		int sacolaX = 0;
		int velocidadeY = 1;
		int posicaoInicialX = 10;
		int posicaoFinalX = contentPane.getWidth() - SACOLA_WIDTH;
		for (int i = 0; i < contentPane.getComponentCount(); i++) {
			// gera numero posicao X da sacola
			int randomNum = ThreadLocalRandom.current().nextInt(-1, 2);

			// cast cada sacola
			if (contentPane.getComponent(i) != null && contentPane.getComponent(i) instanceof JLabel) {
				sacola = (JLabel) contentPane.getComponent(i);
				if (randomNum < 0) {
					sacolaX = sacola.getX() <= posicaoInicialX ? sacola.getX() + (randomNum * (-1))
							: sacola.getX() + randomNum;
					// seta aposicao da sacola
					System.out.println("Posicao sacola X < 0 " + sacola.getX() + " Número random: " + randomNum);
					sacola.setBounds(sacolaX, (sacola.getY() + velocidadeY), sacola.getWidth(), sacola.getHeight());
				} else {
					if (randomNum == 0) {
						randomNum = 1;
					}
					sacolaX = (sacola.getX() > posicaoFinalX ? sacola.getX() - randomNum : sacola.getX() + randomNum);
					System.out.println("Posicao sacola X > 0 " + sacola.getX() + " Número random: " + randomNum);
					// seta a posicao da sacola
					sacola.setBounds(sacolaX, (sacola.getY() + velocidadeY), sacola.getWidth(), sacola.getHeight());
				}
				// System.out.println("Sacola " + (i + 1) + " token: " + sacola.getText());
			}
			// definirPosicaoRetanguloImpacto();
		}
	}

	private void definirPosicaoRetanguloImpacto() {
		rectangleSacola = new Retangulo();
		rectanglePah = new Retangulo();
		rectangleSacola.setBounds(sacola.getX(), sacola.getY(), sacola.getWidth(), sacola.getHeight());
		rectanglePah.setBounds(pah.getX(), pah.getY(), pah.getWidth(), pah.getHeight());
	}

	private void refreshPanel() {
		contentPane.revalidate();
		contentPane.repaint();
	}

	private void criarSacola() throws InterruptedException {
		int posicaoStartSacolaY = 10;

		Thread criaSacola = new Thread(() -> {
			while (true) {
				try {
					int posicaoStartSacolaX = ThreadLocalRandom.current().nextInt(SACOLA_WIDTH,
							(this.getWidth() - SACOLA_WIDTH));
					System.out.println("Posicao Sacola X " + posicaoStartSacolaX);
					sacola = new JLabel(UUID.randomUUID().toString());
					sacola.setIcon(new ImageIcon("src/saco-de-lixo.png"));
					sacola.setBounds(posicaoStartSacolaX, posicaoStartSacolaY, SACOLA_WIDTH, SACOLA_HEIGHT);
					contentPane.add(sacola);
					// refreshPanel();
					Thread.sleep((1000 * 3));
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}

			}
		});
		criaSacola.start();

	}

	public void criarPah() {
		contentPane.setSize(this.getWidth(), this.getHeight());
		this.pah = new JButton(new ImageIcon("src/pa.png"));
		this.pah.setSize(PAH_WIDTH, PAH_HEIGHT);
		pah.setBounds(contentPane.getWidth() / 2, (contentPane.getHeight() - pah.getHeight()), pah.getWidth(),
				pah.getHeight());
		buttonTransparent();
		System.out.println("Pane.W" + contentPane.getWidth() + " Pane.Y" + contentPane.getHeight() + " pah.w "
				+ pah.getWidth() + " pah.h " + pah.getHeight());
		contentPane.add(pah);
		// refreshPanel();

	}

	private void playSong(String pathSong) {
		new Thread(() -> {
			try {
				MP3Player mp3Player = new MP3Player(new File(pathSong));
				mp3Player.play();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}).start();
	}

	private void buttonTransparent() {
		pah.setOpaque(false);
		pah.setContentAreaFilled(false);
		pah.setBorderPainted(false);
		Border emptyBorder = BorderFactory.createEmptyBorder();
		pah.setBorder(emptyBorder);
	}

	public void playSongControlVol(String pathSong, float volume) {

		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(pathSong));
			AudioFormat baseFormat = audioStream.getFormat();
			AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream2);
			clip.start();
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
