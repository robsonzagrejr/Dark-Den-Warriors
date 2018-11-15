//package ddw;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Dimension;

import java.net.URL;

public class Entity{
	private String type;
	private Image image;
	private int x,y,width,height;
	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	public Entity(String type,int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
	}
	
	//Setters and Getters
	public String getType(){
                return this.type;
        }

	public void setX(int x){
		this.x = x;
	}
	public int getX(){
		return this.x;
	}

	public void setY(int y){
		this.y = y;
	}
	public int getY(){
		return this.y;
	}

        public void setWidth(int width){
        	this.width = width;
        }
	public int getWidth(){
		return this.width;
	}
 
        public void setHeight(int height){
        	this.height = height;
        }
	public int getHeight(){
		return this.height;
	}

	public void setImage(String img){
		this.image = new ImageIcon("../resources/images/"+img).getImage();
	}

	//Drawing
	public void draw(Graphics2D g2d){
		g2d.drawImage(this.image, x, y, width, height, null);
	}

	//Animation
        public void update(String type){
                switch(type){
                        case "MoveR":
                               this.setX(this.getX() + (int)(dimension.width * 0.0022)); //Set new place to Drawing
                                break;
                        case "MoveL":
                                this.setX(this.getX() - (int)(dimension.width * 0.0022)); //Set new place to Drawing
				break;
                }
        }


}
