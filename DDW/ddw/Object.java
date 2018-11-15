//package ddw;

import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Object extends Entity{

	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();	

	public Object(String type, int x, int y){
		super(type,x,y,0,0);
		this.setWidth((int)(dimension.width*0.066));
		this.setHeight((int)(dimension.height*0.12));
	}

	public void setImage(String img){
		super.setImage("objects/"+this.getType()+"_"+img);
        }

}
