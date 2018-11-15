//package ddw;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Player extends Entity implements Comparable<Player>{
	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	//Moves
	private int[][] movesStatus = new int[4][4];
	private int[] movesPP = new int[4];
	private String[] movesNames = new String[4];
	private String[] movesStatusBuff = new String[4];
	private int speed, hp, hpMax;
	private float dano, armadura;
	private int targetAttack, moveAttack;

	public Player(String type, int x, int y){
		super(type,x,y,0,0);
		this.setWidth((int)(dimension.width*0.094));
		this.setHeight((int)(dimension.height*0.17));
		try{
			loadMoves();
		} catch(IOException error){
			System.err.println(error.getMessage());
		}
		this.hpMax = this.hp;
	}

	//Getters and Setters
	public void setTargetAttack(int targetAttack){
		this.targetAttack = targetAttack;
	}
	public int getTargetAttack(){
		return this.targetAttack;
	}

	public void setMoveAttack(int moveAttack){
		this.moveAttack = moveAttack;
	}
	public int getMoveAttack(){
		return this.moveAttack;
	}

	public void setSpeed(int speed){
		this.speed = speed;
	}
	public int getSpeed(){
		return this.speed;
	}

	public void setHp(int hp){
		this.hp = hp;
	}
	public int getHp(){
		return this.hp;
	}
	public int getHpMax(){
		return this.hpMax;
	}

	public void setDano(float dano){
		this.dano = dano;
	}
	public float getDano(){
		return this.dano;
	}

	public void setArmadura(float armadura){
		this.armadura = armadura;
	}
	public float getArmadura(){
		return this.armadura;
	}


	//Getters Moves
	public int[][] getMovesStatus(){
		return this.movesStatus;
	}
	public void setMovesPP(int i){
		this.movesPP[i] --;
	}
	public int[] getMovesPP(){
		return this.movesPP;
	}
	public String[] getMovesNames(){
		return this.movesNames;
	}
	public String[] getMovesStatusBuff(){
		return this.movesStatusBuff;
	}

	public void setImage(String img){
		super.setImage("characters/"+this.getType()+"/"+this.getType()+"_"+img);
        }

	//Compare Speed
	public int compareTo(Player p){

		if(this.getSpeed() > p.getSpeed()){
			return -1;
		}
		if(this.getSpeed() < p.getSpeed()){
			return 1;
		}
		return 0;
	}

	//Load from .ini
	private void loadMoves() throws FileNotFoundException, IOException{
		try(BufferedReader reader = new BufferedReader(
					new FileReader("../resources/characters.ini"))){

			boolean reading = true;
			boolean isCharacter = false;
			int aux = 0;
			while(reading){
				String line = reader.readLine();
				//System.out.println(line);
				if(line == null){
					reading = false;
					isCharacter = false;
				}else if(line.matches("\\[\\w+\\]")){//Verify if is section
					if(line.equals("["+this.getType()+"]")){
						isCharacter = true;
					}else{
						isCharacter = false;
					}
				}else if(isCharacter){
					String[] move = line.split("\\=");
					if(move[0].equals("speed")){
						this.speed = Integer.parseInt(move[1]);
					}else if(move[0].equals("hp")){
						this.hp = Integer.parseInt(move[1]);

					}else if(move[0].equals("dano")){
						this.dano = Float.parseFloat(move[1]);
					}else if(move[0].equals("armadura")){
						this.armadura = Float.parseFloat(move[1]);
					}
					else{
						String[] status = move[1].split("\\|");
						//System.out.println(move[1]);
						//System.out.println(status[4]);
						this.movesNames[aux] = status[0];
						this.movesStatus[aux][0] = Integer.parseInt(status[1]);
						this.movesPP[aux] = Integer.parseInt(status[1]);
						this.movesStatus[aux][1] = Integer.parseInt(status[2]);
						this.movesStatus[aux][2] = Integer.parseInt(status[3]);
						this.movesStatus[aux][3] = Integer.parseInt(status[4]);
						this.movesStatusBuff[aux] = status[5];
						aux++;
					}
				}
			}
		}
	}

}
