import java.awt.Color;
import java.awt.Graphics;


public class EnemyBullet extends Bullet{
	double angle;
	static int speed=2;
	EnemyBullet(int x,int y,double angle){
		super(x,y);
		this.angle=angle;
	}
	void move(){
		double dx=Math.cos(angle);
		double dy=Math.sin(angle);
		x+=dx;
		y+=dy;
	}
	void paint(Graphics g){
		g.setColor(new Color(55,55,155));
		g.fillOval(ShootingPanel.startx+x-size/2, 
				ShootingPanel.starty+ShootingPanel.lengthy-(y+size), size, size);
	}
}
