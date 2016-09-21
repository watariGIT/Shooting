import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame implements Runnable, KeyListener{
	Thread thread = null;
	ShootingPanel panel=new ShootingPanel();
	JLabel log = new JLabel("test");
	Thread gameThread;

    public static void main(String args[]) {
		Main game = new Main();
        game.setVisible(true);
	}

	Main(){
		setTitle("Shooting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(20, 20, 450, 600);
		
		JPanel mainP= (JPanel)getContentPane();		
		mainP.add(panel);
		addKeyListener(this);
		requestFocus();
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	public void run() {
		while (gameThread == Thread.currentThread()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				break;
			}
			panel.step();
			repaint();
		}
	}


	public void keyPressed(KeyEvent e) {
		panel.keyPresse(e);
	}

	public void keyReleased(KeyEvent e) {
		panel.keyRelease(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		 char key = e.getKeyChar();
		 panel.typed(key);
		
	}
}
