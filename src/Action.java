
public class Action {
	int dx,dy;
	boolean shot;
	Action(int x,int y,boolean s){
		dx=x;
		dy=y;
		shot=s;
	}
	
	double getDx(int speed){
		if(dx==1)return speed;
		if(dx==-1)return -1*speed;
		return 0;
	}
	double getDy(int speed){
		if(dy==1)return speed;
		if(dy==-1)return -1*speed;
		return 0;
	}
	
	boolean getShot(){
		return shot;
	}
}
