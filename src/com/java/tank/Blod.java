package com.java.tank;

import java.awt.Rectangle;

class Blod {
	public static final int R = 2;
	private int x;
	private int y;
	private int life = 9;
	private Direction dir ;
	private int speed = 4;
	private boolean use = true;
	
	public Blod(int x, int y, Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void subLife(){
		if(life > 0){
			life--;
		}
	}
	
	public void move(){
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
		if(x < 0 || y < 0 || x > TankWar.WIDTH || y > TankWar.HEIGHT){
				this.use = false;
		}
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
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public boolean isUse() {
		return use;
	}
	
	public void setUse(boolean use) {
		this.use = use;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x , y, this.R, this.R);
	}
	public boolean hitTank(Tank t){
		if(this.getRectangle().intersects(t.getRectangle())){
			this.use = false;
			t.setLife(false);
			return true;
		}
		return false;
	}
}
