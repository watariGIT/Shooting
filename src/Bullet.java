import java.awt.Color;
import java.awt.Graphics;


public class Bullet {
	int x=0,y=0;
	int atk=1;
	static int speed=8;
	static int size=8;
	
	Bullet(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	void move(){
		y+=speed;
	}
	
	void paint(Graphics g){
		g.setColor(new Color(255,155,155));
		g.fillOval(ShootingPanel.startx+x-size/2, 
				ShootingPanel.starty+ShootingPanel.lengthy-(y+size), size, size);
	}
	

}
