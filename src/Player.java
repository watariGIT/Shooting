import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;


public class Player {
	int x;
	int y;
	int life;
	Image image;
	int size=25;
	int speed=7;
	int shotTime=20;
	int hp;
	static int maxHp=20;
	ArrayList<Bullet> ball;
	
	Player(String imagePath){
		this.x=ShootingPanel.lengthx/2;
		this.y=size;
		life=10;
		hp=maxHp;
		Toolkit kit = Toolkit.getDefaultToolkit();
		image = kit.createImage(imagePath);
		ball=new ArrayList<Bullet>();

	}
	void shot(){
		if(shotTime>10){
			ball.add(new Bullet(x+size/2,y));
			shotTime=0;
			}
	}
	
	void hitBullet(Enemy e){
		Iterator<Bullet> bul=ball.iterator();
		while(bul.hasNext()){
			Bullet b=bul.next();
			if(e.live && Math.pow(((e.x+Enemy.size/2)-b.x),2)+Math.pow((e.y-Enemy.size/2)-b.y,2)
				<Math.pow((Enemy.size)/2,2)){
				e.hp-=b.atk;
				bul.remove();
				}else{
					if(b.y>ShootingPanel.lengthy-Bullet.size)
						bul.remove();
			}
		}
	}
	void move(boolean u,boolean d,boolean l,boolean r){
		double dy=0,dx=0;
		if(shotTime<10)
			speed=3;
		else
			speed=7;
		if(u)dy+=speed;
		if(d)dy-=speed;
		if(l)dx-=speed;
		if(r)dx+=speed;
		if(dy*dy+dx*dx>speed*speed){
			dy=dy/Math.sqrt(2);
			dx=dx/Math.sqrt(2);
		}
		x+=dx;
		y+=dy;
		
		if(y>=ShootingPanel.lengthy)y=ShootingPanel.lengthy;
		if(y<=size)y=size;
		if(x<=0)x=0;
		if(x>=ShootingPanel.lengthx-size)x=ShootingPanel.lengthx-size;
		for(Bullet b:ball)
			b.move();
		shotTime++;
	}
	
	void paint(Graphics g){
		g.drawImage(image,x+ShootingPanel.startx,ShootingPanel.lengthy-y+ShootingPanel.starty, null);
		for(Bullet b:ball)
			b.paint(g);
	}

}
