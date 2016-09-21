import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShootingPanel extends JPanel {
	 public static int startx = 20;
	 public static int starty = 10;
	 public final static int lengthx =300;
	 public final static int lengthy =500;
	 final static int enemyNum=10;
	 Player play;
	 Enemy[] target;
	 JLabel label;
	 boolean keyLeft=false;	
	 boolean keyRight=false;
	 boolean keyUp=false;	
	 boolean keyDown=false;
	 boolean shot=false;
	 
	 ShootingPanel(){
		 play=new Player("play.gif");
		 Enemy.setImage("enemy.gif");
		 target=new Enemy[enemyNum];
		 label=new JLabel();
		 //this.add(label);
		 for(int i=0;i<enemyNum;i++){
			 target[i]=new Enemy((int)(lengthx*Math.random()),lengthy/10*8);
		 }
		
	 }
	
	 public void step(){
			play.move(keyUp,keyDown,keyLeft,keyRight);
			for(Enemy e:target){
				if(e.live)e.move();
				e.ballMove();
				}
			
			if(shot)play.shot();
			
			//????????
			for(Enemy e:target){
				e.hitBullet(play);
				play.hitBullet(e);
				e.setDead();
			}
			makeEnemy();
			String s="<html>generation"+Enemy.generation+"<br>";
			for(int i=0;i<enemyNum;i++){
				s+="["+i+"]"+target[i].fitness+"<br>";
			}
			s+="</html>";
			label.setText(s);
	 }
	 public Boolean makeEnemy(){
		
		 for(Enemy e:target){
			 if(e.live)
				 return false;
		 }	
		 int[] num=new int[enemyNum];
		 chose(num);
		 cross(num);
		 Enemy.generation++;
		return true;			 
	 }
	 private void chose(int[] n){
		 int sum=0;
		 for(Enemy e:target)
			 sum+=e.fitness;
		 System.out.print("<chose>\n");

		 for(int i=0;i<enemyNum;i++){
			 int t=(int)(sum*Math.random());
			 for(int j=0;j<enemyNum;j++){
				 t-=target[j].fitness;
				
				 if(t<=0){
					 n[i]=j;
					 System.out.print("["+i+"]=>"+j+"\n");
					 break;
					 }
			 }		 
		 }
	 }
	 private void cross(int[] n){
		 ArrayList<ArrayList<Action>> act=new ArrayList<ArrayList<Action>>();
		 System.out.print("<cross>\n");
		 for(int i=0;i<enemyNum;i++){
			 int a1=(int)(enemyNum*Math.random());
			 int a2=(int)(enemyNum*Math.random());
			  act.add(crossAction(target[n[a1]].actList,target[n[a2]].actList));
			 System.out.print("("+n[a1]+")"+"-("+n[a2]+")\n");
		 }

		 for(int i=0;i<enemyNum;i++){
			 if(i<enemyNum/10*6)
				 target[i].setAction((int)(lengthx*Math.random()),lengthy/10*8,act.get(i));
			 else
				 target[i]=new Enemy((int)(lengthx*Math.random()),lengthy/10*8);
			 }
	 }
	 private  ArrayList<Action> crossAction(ArrayList<Action> a1,ArrayList<Action> a2){
		 int p1=0,p2=0;
		 int max;
		 if(a1.size()<a2.size())
			 max=a1.size();
		 else
			 max=a2.size();
		 p1=(int)(max*Math.random());
		 p2=(int)(max*Math.random());
		 if(p1>p2){
			 int j=p1;
			 p1=p2;
			 p2=j;
		 }
		 Action[] s1=(Action[]) a1.toArray(new Action[0]);
		 Action[] s2=(Action[]) a2.toArray(new Action[0]);
		 for(int i=p1;i<p2;i++){
			 Action j=s1[i];
			 s1[i]=s2[i];
			 s2[i]=j;
		 }
		 return new ArrayList<Action>(Arrays.asList(s1));
	 }
	 
	 public void keyPresse(KeyEvent e){
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			}
	 }
	public void keyRelease(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			}
			if(e.getKeyChar()=='z')
				shot=false;
	}
	 public void typed(char c){
		 if(c=='z')
			 shot=true;
	 }
	 public void paintComponent(Graphics g){
		 //?g?g???\??
	    	g.setColor(new Color(255,255,255));
	    	g.fillRect(startx,starty,lengthx,lengthy);
	    	g.setColor(Color.BLACK);
	    	g.drawRect(startx,starty,lengthx,lengthy);
	    	//???@??HP?\??
	    	for(int i=0;i<Player.maxHp;i++){
	    		if(i<=play.hp)
	    			g.setColor(new Color(225,55,55));
	    		else
	    			g.setColor(Color.WHITE);
	    		g.fillRect(startx+10*i,starty+lengthy+3,8,15);
	    		g.setColor(Color.BLACK);	
	    		g.drawRect(startx+10*i,starty+lengthy+3,8,15);
	    	}
	    	//?L?????N?^?[??\??
	    	play.paint(g);
	    	for(Enemy e:target){
	    		if(e.live)
					e.paint(g);
	    		e.ballPaint(g);
	    	}
	    	
	    }
	 

}
