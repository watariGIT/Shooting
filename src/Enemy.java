import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;


public class Enemy {
	int x,y;
	double dx,dy;
	boolean shot;
	boolean live;
	ArrayList<Action> actList;
	int time;//��������
	int shotTime;//�e��ł��Ă���̎���
	int speed;//����
	int hp;//hp
	int fitness;//�K���l
	int atk_count=0;//�^�����e�̐�
	static int generation=0;
	static Image img;
	final static int shotTimelag=20;
	static int size=25;
	static int maxHp=1;
	ArrayList<EnemyBullet> ball;
	
	Enemy(int x,int y){
		this.x=x;
		this.y=y;
		dx=0;
		dy=0;
		shot=false;
		actList=new ArrayList<Action>();
		ball=new ArrayList<EnemyBullet>();
		shotTime=20;
		time=0;
		fitness=0;
		atk_count=0;
		speed=5;
		hp=maxHp;
		live=true;
	}
	
	public void setAction(int x,int y,ArrayList<Action> act){
		this.x=x;
		this.y=y;
		dx=0;
		dy=0;
		shot=false;
		actList=act;
		shotTime=20;
		time=0;
		atk_count=0;
		speed=5;
		hp=maxHp;
		live=true;
	}
	
	void hitBullet(Player p){
		Iterator<EnemyBullet> bul=ball.iterator();
		while(bul.hasNext()){
			Bullet b=bul.next();
			if(Math.pow(((p.x+p.size/2)-b.x),2)+Math.pow((p.y-p.size/2)-b.y,2)<Math.pow((p.size/2),2)){
				p.hp-=b.atk;
				atk_count++;
				fitness+=100000/time;
				bul.remove();
				}else{
					if(b.y-EnemyBullet.size<0)
						bul.remove();
			}
		}
	}
	void move(){
		time++;
		Action act;
		if(actList.size()<=time/60){
			act=makeAct();
			actList.add(act);
			}else{
				act=actList.get(time/60);
			}
		if(shotTime>shotTimelag)
			speed=5;
		else
			speed=2;
		dx=act.getDx(speed);
		dy=act.getDy(speed);
		shot=act.getShot();
		if(dy*dy+dx*dx>speed*speed){
			dy=dy/Math.sqrt(2);
			dx=dx/Math.sqrt(2);
			}
		x+=dx;
		y+=dy;	
		if(y>=ShootingPanel.lengthy)y=ShootingPanel.lengthy;
		if(y<=size+150)y=size+150;
		if(x<=0)x=0;
		if(x>=ShootingPanel.lengthx-size)x=ShootingPanel.lengthx-size;
		if(shot)
			shot();
		shotTime++;
	}
	void ballMove(){
		for(EnemyBullet b:ball)
			b.move();
	}
	
	private void shot(){
		if(shotTime>shotTimelag){
			ball.add(new EnemyBullet(x+size/2,y-EnemyBullet.size,Math.PI*3/2));
			shotTime=0;
			}
	}

	static Action makeAct(){
		int dxf,dyf;
		boolean sf;

		dxf=1-(int)(Math.random()*3);
		dyf=1-(int)(Math.random()*3);

		if((int)(Math.random()*2)==0)
			sf=true;
		else
			sf=false;
		return new Action(dxf,dyf,sf);
	}

	void setDead(){
		if(hp<=0 && live){
			live=false;
			fitness+=time;
		}
	}
	static void setImage(String imagePath){
		Toolkit kit = Toolkit.getDefaultToolkit();
		img = kit.createImage(imagePath);
	}
	
	void paint(Graphics g){		
		g.drawImage(img,x+ShootingPanel.startx,ShootingPanel.lengthy-y+ShootingPanel.starty, null);
	}
	void ballPaint(Graphics g){
		for(EnemyBullet b:ball)
			b.paint(g);
	}
}
