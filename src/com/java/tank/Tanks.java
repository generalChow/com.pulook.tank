package com.java.tank;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

class Tank{
	//tank�ĺ�������
	private int x;
	private int y;
	private int x1,y1;
	
	private Direction dir ;
	
	private int speed = 3;
	
	private boolean life = true;
	
	public Tank(int x, int y){
		this.x = x;
		this.y = y;
		x1 = x;
		y1 = y;
	}

	public Tank(int x, int y, Direction dir) {
		this(x, y);
		this.dir = dir;
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public boolean isLife() {
		return life;
	}

	public void setLife(boolean life) {
		this.life = life;
	}

	public void move(){
		x1 = x;
		y1 = y;
		switch(dir){
		case up:
			y -= speed;
			break;
		case right:
			x += speed;
			break;
		case down:
			y += speed;
			break;
		case left:
			x -= speed;
			break;
		default:
			break;
		}
		if(x < 0){
			x = x1;
		}
		if(y < 0){
			y = y1;
		}
		if(x > TankWar.WIDTH){
			x = x1;
		}
		if(y > TankWar.HEIGHT - 45){
			y = y1;
		}
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x, y, 30, 30);
	}
}

class Hero extends Tank implements Runnable{

	public Hero(int x, int y, Direction dir) {
		super(x, y, dir);
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public Blod getBlod(){
		return new Blod(this.getX()+10, this.getY(), this.getDir());
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}
}

class EmentTank extends Tank implements Runnable{
	private Direction[] dir = Direction.values();

	public EmentTank(int x, int y) {
		super(x, y);
		super.setSpeed(2);
	}

	public EmentTank(int i, int j, Direction down) {
		this(i,j);
		setDir(down);
	}

	@Override
	public void run() {
		while(true){
			int ran = (int)(Math.random()*4);
			setDir(dir[ran]);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int i = 0; i < 30; i++){
			    move();
			    try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
