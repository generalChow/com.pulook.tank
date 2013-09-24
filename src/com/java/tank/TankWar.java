package com.java.tank;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class TankWar extends JFrame{

	MyPanel myPanel = null;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 300;
	
	public static void main(String[] args) {
		new TankWar().launchFrame();
	}
	
	public TankWar(){
		myPanel = new MyPanel();
	}
	
	public void launchFrame(){
		//添加容器
		this.add(myPanel);
	    
		this.addKeyListener(myPanel.new MyKey());
		new Thread(myPanel).start();
		//设置窗口
		this.setSize(WIDTH,HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
}

//我的面板
class MyPanel extends JPanel implements Runnable{
	
	Hero hero = null;
	Vector<EmentTank> ement = new Vector<EmentTank>();
	Vector<Blod> blod = new Vector<Blod>();
	
	public MyPanel(){
		hero = new Hero(10, 10, Direction.up);
		Thread t = new Thread(hero);
		t.start();
		for(int i = 0; i < 3; i++){
			EmentTank t1 = new EmentTank(50*i+100, 50*i+100, Direction.down);
			ement.add(t1);
			new Thread(t1).start();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fill3DRect(0, 0, this.getWidth(), this.getHeight(), false);
		drawTank(hero.getX(), hero.getY(), g, hero.getDir(), 0);
		for(int i = 0; i < ement.size(); i++){
			EmentTank e = ement.get(i);
			if(!e.isLife()){
				ement.remove(e);
			}
			else{
			   drawTank(e.getX(), e.getY(), g, e.getDir(), 1);
			}
		}
		for(int i = 0; i < blod.size(); i++){
			g.setColor(Color.WHITE);
			g.drawString("还剩"+blod.size(), 50, 50);
			Blod b = blod.get(i);
			if(b.isUse()){
				this.drawBlod(b.getX(), b.getY(), g, b.R);
				b.move();
				for(int j = 0; j < ement.size(); j++)
				     b.hitTank(ement.get(j));
			}
			else{
				blod.remove(b);
			}
		}
		g.setColor(c);
	}
	
	public void drawBlod(int x, int y, Graphics g,int r){
		g.setColor(Color.RED);
		g.fillOval(x, y, r, r);
	}
	
	public void drawTank(int x, int y, Graphics g, Direction dir, int type){
		//0我方坦克，1敌方坦克
		switch(type){
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}
		//0方向向上，1方向向右，2方向向下，3方向向左
		switch(dir){
		case up:
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x+15, y, 5, 30, false);
			g.fill3DRect(x+5, y+5, 10, 20, false);
			g.fillOval(x+6, y+11, 8, 8);
			g.drawLine(x+10, y+15, x+10, y);
			break;
		case right:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+11, y+6, 8, 8);
			g.drawLine(x+15, y+10, x+30, y+10);
			break;
		case down:
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x+15, y, 5, 30, false);
			g.fill3DRect(x+5, y+5, 10, 20, false);
			g.fillOval(x+6, y+11, 8, 8);
			g.drawLine(x+10, y+15, x+10, y+30);
			break;
		case left:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+11, y+6, 8, 8);
			g.drawLine(x+15, y+10, x, y+10);
			break;
			default:
				break;
		}
	}
	
	class MyKey extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			//hero.keyPressed(e);
			int code = e.getKeyCode();
			Direction dir = hero.getDir();
			if(code == KeyEvent.VK_UP){
				dir = Direction.up;
			}
			else if(code == KeyEvent.VK_RIGHT){
				dir = Direction.right;
			}
			else if(code == KeyEvent.VK_DOWN){
				dir = Direction.down;
			}
			else if(code == KeyEvent.VK_LEFT){
				dir = Direction.left;
			}
			else if(code == KeyEvent.VK_B){
				if(blod.size() < 5){
					Blod b = new Blod(hero.getX(), hero.getY(), hero.getDir());
					if(hero.getDir() == Direction.up || hero.getDir() == Direction.down)
						 b = new Blod(hero.getX() + 10, hero.getY(), hero.getDir());
					else{
						b = new Blod(hero.getX(), hero.getY() + 10, hero.getDir());
					}
					blod.add(b);
				}
				
			}
			hero.setDir(dir);
			hero.move();
		}
		@Override
		public void keyReleased(KeyEvent e) {
			hero.keyReleased(e);
		}
		
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}