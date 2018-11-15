//package ddw;
import java.io.File;

//Audio
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//Graphics
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Component;

//Actions
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Others
import javax.swing.Timer;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.List;
import java.util.LinkedList;
import java.lang.Boolean;
import java.util.Collections;
import java.util.Random;
import java.net.URL;

public class GameFrame extends JPanel implements ActionListener{

	//Variables
	private	JFrame frame = new JFrame();
	private	Timer mainTimer;
        private Clip clip; 
	private int btlBackTimer,indexAttack;
	private JButton btnExit = new JButton();

	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	private String background, music, screen;
	private String difficulty = "Medium";
	private boolean musicMute, inSettings;

	//Set Characters
	private String[] characters = new String[]{"Bandit","Esqueleto","Knight","Archer","Mage","Warrior"};

	//Set Characters Objects
	private Object[] characObjects = new Object[characters.length];

	//SetPlayers
	private int numPlayers = 2;
	private List<Player> player = new LinkedList<Player>();

	//Set Aux for Animation
	private List<String> typeAnimation = new LinkedList<String>();
	private List<String> whoAnimation = new LinkedList<String>();
	private List<Integer> indexOf= new LinkedList<Integer>();
	private List<Integer> conditionMove = new LinkedList<Integer>();
	private List<Boolean> startAnimation = new LinkedList<Boolean>();
	private List<Boolean> finishAnimation = new LinkedList<Boolean>();

	//Set Aux for Battle Round
	private boolean inRound,inAnimationAttack,inPositionAttack; 
	private int auxWhoInAttack,  auxPositionWhoAttack, timeForAttackAnimation ;
	private	boolean finishGame = false;
	private String finishGameS;
	private int auxConditionMove;

	//Buttons for Choise
	private	JButton[] btnPlyAttack;
	private	JButton[] btnPlyTarget;


	public GameFrame(){
	
		//Setting Frame;
		frame.setUndecorated(true);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);//Fullscreen
		frame.setSize(dimension.width,dimension.height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(this);
		//
		//Set Cursor Image
		Toolkit toolkit = Toolkit.getDefaultToolkit();		
		Image cursorImg = toolkit.getImage("../resources/images/objects/cursor.png");
		this.setCursor(toolkit.createCustomCursor(cursorImg , new Point(0,0), "img"));

		this.setLayout(null);//Can organize the thinks in panel
		this.setFocusable(true);

		//Set Default Variables
		clearVariables();

		startScreen();

		//System.out.println(dimension.width);
		//System.out.println(dimension.height);

		//Frame, Update in 15 miliseconds, aprox 60 FPS
		mainTimer = new Timer(15, this);
		mainTimer.start();
		
		//Define Exit Button
		this.btnExit.setBounds((int)(dimension.width - dimension.width*0.0327),
					(int)(dimension.height*0.0065),
					(int)(dimension.width*0.029),(int)(dimension.height*0.052));
		//Define Button Transparant
              	this.btnExit.setOpaque(false);
              	this.btnExit.setContentAreaFilled(false);
               	this.btnExit.setBorderPainted(false);
		
		//Define Action 
		this.btnExit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
				System.exit(0);
                        }
                });

	}

	//Start Screen
	public void startScreen(){
		//Clear Screen
		this.removeAll();

		//Define Screen
		screen = "start";

		//Define BackGround
		background = "startBackground.gif";

		//Define Music
		if(music != "open_music1.wav"){
			music = "open_music1.wav";
			if(!musicMute){
				soundMusic("open_music1.wav");
			}
		}

		JButton btnStart = new JButton();
		btnStart.setBounds(0,0,dimension.width,dimension.height);
		//Define Button Transparant
               	btnStart.setOpaque(false);
		btnStart.setContentAreaFilled(false);
                btnStart.setBorderPainted(false);
		
		//Define Actions
                btnStart.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                menuScreen();
                        }
                });

		//Add in Panel
		this.add(btnStart);
		//Update Frame
		frame.setVisible(true);
	}

	//Menu Screen
	public void menuScreen(){
		//Clear Screen
		this.removeAll();

		//Define Screen
		screen = "menu";

		//Define Background
		background = "menuBackground.gif";
		
		//Define Music
		if(music != "open_music1.wav"){
			music = "open_music1.wav";
			if(!musicMute){
				soundMusic("open_music1.wav");
			}
		}

		//#############Menu_Settings##############

		//Define Settings Panel
		JPanel panelSettings = new JPanel();
		panelSettings.setLayout(null);
		//panelSettings.setBackground(new Color(0,0,0,142));
		panelSettings.setOpaque(false);
		panelSettings.setBounds(0,0,dimension.width,(int)(dimension.height*0.75));

		//Define Settings Buttons

		JButton btnMute = new JButton();
		btnMute.setBounds((int)(dimension.width*0.335),(int)(dimension.height*0.48),
					(int)(dimension.width * 0.059),
					(int)(dimension.height * 0.078));//80,60
		//Define Button Transparant
                btnMute.setOpaque(false);
		btnMute.setContentAreaFilled(false);
                btnMute.setBorderPainted(false);
	
		JButton btnNumPlayers = new JButton();
		btnNumPlayers.setBounds((int)(dimension.width*0.58),(int)(dimension.height*0.48),
					(int)(dimension.width * 0.059),
					(int)(dimension.height * 0.078));
		//Define Button Transparant
                btnNumPlayers.setOpaque(false);
		btnNumPlayers.setContentAreaFilled(false);
                btnNumPlayers.setBorderPainted(false);


		//Define Actions
		btnMute.addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
				musicMute = !musicMute;
				if(!musicMute){
					soundMusic(music);
				}else{
					clip.stop();
				}
                       	}
               	});
		btnNumPlayers.addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
				numPlayers += 2;
				if(numPlayers == ((characters.length *2)+2)){
					numPlayers = 2;
				}
				clearVariables();
                       	}
               	});

		panelSettings.add(btnMute);
		panelSettings.add(btnNumPlayers);

		this.add(panelSettings);

		//############Characters Choise############

		//Define Characters Buttons
		JButton[] btnCharacter = new JButton[characters.length];
		
		int location = dimension.width / characters.length;
		int locationHeight = (int)(dimension.height * 0.827);

		//System.out.println(location);
		for(int i=0; i<characters.length; i++){
			
			//Define Objects
			characObjects[i] = new Object(characters[i],0,locationHeight);
			int locationWidth = location*(i+1)-location/2 - characObjects[i].getWidth()/2;//Middle Equal,
			characObjects[i].setX(locationWidth);
			characObjects[i].setImage("Front.png");
			
			//Define Buttons
			btnCharacter[i] = new JButton();
			btnCharacter[i].setBounds(locationWidth,locationHeight,
						characObjects[i].getWidth(),characObjects[i].getHeight());
			//Define Button Transparant
              		btnCharacter[i].setOpaque(true);
              		btnCharacter[i].setContentAreaFilled(false);
               		btnCharacter[i].setBorderPainted(false);
			
			//Aux
			int index = i;
			String character = characters[i];
			JPanel panel = this;
			//Define Actions
               		btnCharacter[i].addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent e) {
					//System.out.println();
					panelSettings.removeAll();
					//Remove this Button;
					remove(btnCharacter[index]);

					player.add(new Player(character,0,(int)(dimension.height*0.56)));
					player.get(player.size()-1).setTargetAttack(numPlayers/2);
					animation(player.size()-1,"MoveR","Object",index,dimension.width);

					if(player.size() == numPlayers/2){
						removeAll();
					}
                        	}
               		});
			//Add in Panel 
			this.add(btnCharacter[i]);

		}		

	
		//Update Frame
		frame.setVisible(true);
	}

	//Battle Screen
	public void battleScreen(){
		//Clear Screen
		this.removeAll();

		//Define Screen
		screen = "battle";

		
		//Define BackGround
		background = "battleBackground_1.png";

		//Define Music
		if(music != "battle_music.wav"){
			music = "battle_music.wav";
			if(!musicMute){
				soundMusic(music);
			}
		}
		
		//Define Player 2
		setPlayer2();

		//Define Animation
		for(int i=0; i<numPlayers;i++){
			if(i<numPlayers/2){
				//Set Player 1
				player.get(i).setX(player.get(i).getX() - player.get(i).getWidth()/2 *(i));
				animation(i,"MoveR","Player",i,(int)(dimension.width*0.40)-player.get(i).getWidth()/2 *(i+1));	
			}else{
				//Set Player 2
				player.get(i).setX(player.get(i).getX() + player.get(i).getWidth()/2 *(i-numPlayers));
				animation(i,"MoveL","Player",i,
						(int)(dimension.width*0.55)+player.get(i).getWidth()/2 *(i-numPlayers/2));	
			}
		}

		//#############Battle Settings#############
	
		//Define Settings Panel
		JPanel panelSettings = new JPanel();
		panelSettings.setLayout(null);
		panelSettings.setBounds(0,0,dimension.width,dimension.height);
		panelSettings.setOpaque(false);

		//Define Settings Components

		//Back to Menu Button
		JButton btnBackMenu = new JButton();
		btnBackMenu.setBounds(dimension.width/2 -(int)(dimension.width*0.029*1.55), dimension.height/2,
					(int)(dimension.width*0.029),(int)(dimension.height*0.052));
		//Define Button Transparant
              	btnBackMenu.setOpaque(false);
              	btnBackMenu.setContentAreaFilled(false);
               	btnBackMenu.setBorderPainted(false);

		//Mute Button
		JButton btnMute = new JButton();
		btnMute.setBounds(dimension.width/2 -(int)(dimension.width*0.029 /2),dimension.height/2,
					(int)(dimension.width*0.029),(int)(dimension.height*0.052));
		//Define Button Transparant
              	btnMute.setOpaque(false);
              	btnMute.setContentAreaFilled(false);
               	btnMute.setBorderPainted(false);

		//Back Button
		JButton btnBack = new JButton();
		btnBack.setBounds(dimension.width/2 + (int)(dimension.width*0.029*0.60),dimension.height/2,
					(int)(dimension.width*0.029),(int)(dimension.height*0.052));
		//Define Button Transparant
              	btnBack.setOpaque(false);
              	btnBack.setContentAreaFilled(false);
               	btnBack.setBorderPainted(false);

		//Define Actions
		btnMute.addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
				musicMute = !musicMute;
				if(!musicMute){
					soundMusic(music);
				}else{
					clip.stop();
				}
                       	}
               	});

		btnBackMenu.addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
				//Clear Players
				player.clear();	
				clearVariables();
				inSettings = false;
				inRound = false;
				btnExit.setEnabled(true);
				menuScreen();
                       	}
               	});

		//Aux
		JPanel panel = this;

		btnBack.addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
				enablePanel(panel,true);
				remove(panelSettings);
				inSettings = false;
                       	}
               	});
		
		//Add in Settings Panel
		panelSettings.add(btnMute);
		panelSettings.add(btnBackMenu);
		panelSettings.add(btnBack);
		
		//Define SettingsButton
		JButton btnSettings = new JButton();
		btnSettings.setBounds((int)(dimension.width*0.0037),(int)(dimension.height*0.0065),
					(int)(dimension.width*0.029),(int)(dimension.height*0.052));
		//Define Button Transparant
              	btnSettings.setOpaque(false);
              	btnSettings.setContentAreaFilled(false);
               	btnSettings.setBorderPainted(false);
		
		//Define BtnSettings Action
               	btnSettings.addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
				enablePanel(panel,false);
				add(panelSettings,0);
				inSettings = true;
				//System.out.println("BTN");
                       	}
               	});
		

		//#############Moves############

		//Define Moves Buttons
		JButton[] btnMove = new JButton[4];
		
		for(int i=0; i<4; i++){
			btnMove[i] = new JButton();
			//Define Button Transparant
              		btnMove[i].setOpaque(false);
           	   	btnMove[i].setContentAreaFilled(false);
               		btnMove[i].setBorderPainted(false);

			//Set Button Positions
			int btnX;
			if(i%2 ==0){
				btnX = (int)(dimension.width * 0.305);						
			}else{
				btnX = (int)(dimension.width * 0.437);
			}
			if(i < 2){
				btnMove[i].setBounds(btnX,(int)(dimension.height *0.809),
						(int)(dimension.width*0.092),(int)(dimension.height*0.045));
			}else{
				btnMove[i].setBounds(btnX,(int)(dimension.height *0.88),
						(int)(dimension.width*0.092),(int)(dimension.height*0.045));
			}

			//Define Actions
			int aux = i;
			btnMove[i].addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
                	       		player.get(indexAttack).setMoveAttack(aux);
				}
               		});
			this.add(btnMove[i]);
		}

		//#############Character Choise##########
 		btnPlyTarget = new JButton[numPlayers/2];
 		btnPlyAttack = new JButton[numPlayers/2];
		//Define Player Attack Button
		for(int i=0; i<numPlayers/2; i++){
			btnPlyTarget[i] = new JButton();
			int aux = i+ numPlayers/2;
 			btnPlyTarget[i].setBounds((int)(dimension.width*0.55)+player.get(aux).getWidth()/2*(i)+
				       			player.get(aux).getWidth()*1/3,
							player.get(aux).getY(),
							player.get(aux).getWidth()-player.get(aux).getWidth()*2/3,
							player.get(aux).getHeight());
			//Define Button Transparant
              		btnPlyTarget[i].setOpaque(false);
           	   	btnPlyTarget[i].setContentAreaFilled(false);
               		btnPlyTarget[i].setBorderPainted(false);

			btnPlyTarget[i].addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
                	       		player.get(indexAttack).setTargetAttack(aux);
				}
               		});
			
			btnPlyAttack[i] = new JButton();
 			btnPlyAttack[i].setBounds((int)(dimension.width*0.40)-player.get(i).getWidth()/2*(i)-					
				       			player.get(aux).getWidth()*1/6,
							player.get(i).getY(),
							player.get(i).getWidth()-player.get(i).getWidth()*2/3,
							player.get(i).getHeight());
			//Define Button Transparant
              		btnPlyAttack[i].setOpaque(false);
           	   	btnPlyAttack[i].setContentAreaFilled(false);
               		btnPlyAttack[i].setBorderPainted(false);
			int aux2 = i;
			btnPlyAttack[i].addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
                	       		indexAttack = aux2;
					//System.out.println(indexAttack);
				}
               		});

			this.add(btnPlyTarget[i]);
			this.add(btnPlyAttack[i]);

		}

		//*************Define Battle*****************
		
		//Define Attack Button
		JButton btnAttack = new JButton();
		btnAttack.setBounds((int)(dimension.width * 0.63),(int)(dimension.height *0.88),
						(int)(dimension.width*0.055),(int)(dimension.height*0.031));
		//Define Button Transparant
              	btnAttack.setOpaque(false);
              	btnAttack.setContentAreaFilled(false);
               	btnAttack.setBorderPainted(false);

		JPanel thisPanel = this;
		
		//Define BtnAttack Action
               	btnAttack.addActionListener(new ActionListener() {
                       	public void actionPerformed(ActionEvent e) {
				inRound = true;
				enablePanel(thisPanel, false);
				btnSettings.setEnabled(true);
				setPlayer2MovesTarget();
			}	
               	});

		//Add in Panel
		this.add(btnAttack);
		this.add(btnSettings);

		//Update Frame
		frame.setVisible(true);
	}

	//Player 2
	public void setPlayer2(){
		int[] auxChoise = new int[characters.length];
		Random rand = new Random();
		int n = rand.nextInt(characters.length);
		boolean aux = true;

		for(int i = 0; i<numPlayers/2;i++){
			while(aux){
				if(auxChoise[n] == 0){
					auxChoise[n] = 1;
					aux = false;
				}else{
					n = rand.nextInt(characters.length);
				}
					
			}
			this.player.add(new Player(characters[n],dimension.width,(int)(dimension.height*0.56)));
			this.player.get(i+numPlayers/2).setTargetAttack(0);
			aux = true;
		}
	}
	public void setPlayer2MovesTarget(){
		for(int i=0; i<numPlayers/2; i++){
			Random rand = new Random();
			if(this.player.get(numPlayers/2 + i) != null){
				int n = rand.nextInt(4);
				this.player.get(numPlayers/2+i).setMoveAttack(n);	
				n = rand.nextInt(numPlayers/2);
				while(this.player.get(n) == null){
					n = rand.nextInt(numPlayers/2);
				}
				this.player.get(numPlayers/2+i).setTargetAttack(n);	
			}
		}
	}

	//Functions Extra
	public void enablePanel(JPanel panel,boolean enable){
		 Component[] components = panel.getComponents();
       		 for (Component component : components) {
           		 component.setEnabled(enable);
		 }
	}

	public void animation(int i,String type, String who, int index, int condition){
		this.whoAnimation.remove(i);
		this.whoAnimation.add(i,who);
		this.typeAnimation.remove(i);
		this.typeAnimation.add(i,type);
		this.indexOf.remove(i);
		this.indexOf.add(i,index);
		this.conditionMove.remove(i);
		this.conditionMove.add(i,condition);
		this.startAnimation.remove(i);
		this.startAnimation.add(i,Boolean.TRUE);
	}

	public void clearVariables(){
	
		//Clear everithing
		whoAnimation.clear();
		typeAnimation.clear();
		indexOf.clear();
		conditionMove.clear();
		startAnimation.clear();
		finishAnimation.clear();

		for(int i=0; i<numPlayers; i++){
			whoAnimation.add("");
			typeAnimation.add("");
			indexOf.add(0);
			conditionMove.add(0);
			startAnimation.add(Boolean.FALSE);
			finishAnimation.add(Boolean.FALSE);
		}
	}

	//Functions_Battle
	public List<Player> ordemPlayers() {
		List<Player> ordPlayers = new LinkedList<Player>();
		for(Player p : player){
			if(p != null){
				ordPlayers.add(p);
			}
		}
                Collections.sort(ordPlayers);
                return ordPlayers;
        }

	public void executar(Player p) {
		int target = p.getTargetAttack();
		int move = p.getMoveAttack();	
		this.player.get(this.player.indexOf(p)).setMovesPP(move);

		if(p.getMovesStatus()[move][2] == 1) {
              		buff(p, move);
               	}else{
			ataque(p, this.player.get(target), p.getMovesStatus()[move]);
        	}
		//Remove Dead Player
		if(player.get(target).getHp() <= 0){
				//Change Select/Target Choise
				if(target >= numPlayers/2){
					for(int i = numPlayers/2; i<numPlayers; i++){
						if((this.player.get(i) != null) && (i != target)){
							for(Player p1 : this.player){
								if((p1 != null)&&(p1.getTargetAttack() == target)){
									p1.setTargetAttack(i);
								}
							}
							i = numPlayers;
						}
					}	
					//Remove BTN
					this.remove(btnPlyTarget[target - numPlayers/2]);
				}else{
					for(int i = 0; i<numPlayers/2; i++){
						if((this.player.get(i) != null) && (i != target)){
							indexAttack = i;
							for(Player p1 : this.player){
								if((p1 != null)&&(p1.getTargetAttack() == target)){
									p1.setTargetAttack(i);
								}
							}
							i = numPlayers;
						}
					}	
					//Remove BTN
					this.remove(btnPlyAttack[target]);
				}
				this.player.remove(target);
				this.player.add(target,null);
		}
        }
	
	//a ataca, b leva dano
        public void ataque(Player a, Player b, int[] g ) {
		//System.out.println(a.getHp()+" | "+b.getHp());
                b.setHp(b.getHp() - (int)((a.getDano()*g[1])/b.getArmadura()));
        }

        //a da um buff pro b,
        public void buff(Player b, int g ) {
                if(b.getMovesStatusBuff()[g].equals("armadura")) {
                        b.setArmadura(b.getArmadura() + b.getMovesStatus()[g][1]);
                } else if (b.getMovesStatusBuff()[g].equals("hp")) {
                                b.setHp(b.getHp() + b.getMovesStatus()[g][1]);
                        } else if (b.getMovesStatusBuff()[g].equals("speed")) {
                                        b.setSpeed(b.getSpeed() + b.getMovesStatus()[g][1]);

                        } else if (b.getMovesStatusBuff()[g].equals("dano")){
					b.setDano(b.getSpeed() + b.getMovesStatus()[g][1]);
				}
        }

	//Sound Definition
        public void soundMusic(String music){
                try{
                        File sound = new File("../resources/sounds/"+music);
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(sound);
			if(this.clip != null){
				this.clip.stop();
			}
			this.clip = AudioSystem.getClip();
                        this.clip.open(inputStream);
                        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
                }catch(Exception e){
                        System.out.println(e);
                }
	}

	//Drawing in Screen
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		//URL =../ Main.class.getResource();

		//Background
		Image imgBackground = new ImageIcon("../resources/images/backgrounds/"+background).getImage();
		g.drawImage(imgBackground,0,0,dimension.width,dimension.height,this);

		//Drawing Exit Button
		Image imgBtnExit = new ImageIcon("../resources/images/objects/btn_red_close.png").getImage();
		g2d.drawImage(imgBtnExit,(int)(dimension.width - dimension.width*0.0327),
					(int)(dimension.height*0.0065),
					(int)(dimension.width*0.029),(int)(dimension.height*0.052),null);
		
		//Define Screens
		switch(screen){
			case "menu":

				//Draw Characters Objects
				for(int i=0; i<characters.length; i++){
					characObjects[i].draw(g2d);
				}
					
				//Verify Animation
				for(int i=0; i<numPlayers/2; i++){
					if(startAnimation.get(i) && !finishAnimation.get(i)){
						//Drawing select Character upside others
						characObjects[indexOf.get(i)].draw(g2d);
						i = numPlayers;
					}else if(startAnimation.get(i) && finishAnimation.get(i)){
						indexOf.remove(i);
						indexOf.add(i,0);
						startAnimation.remove(i);
						startAnimation.add(i,Boolean.FALSE);
						finishAnimation.remove(i);
						finishAnimation.add(i,Boolean.FALSE);
						if(i == numPlayers/2 -1){
							clearVariables();
							battleScreen();
						}
					}
				}

				//Draw Buttons
				Image imgBtnBoard = new ImageIcon("../resources/images/objects/board0.png").getImage();

				//Button Num Players 
				g2d.drawImage(imgBtnBoard,(int)(dimension.width*0.58),
						(int)(dimension.height*0.48),
						(int)(dimension.width * 0.059),
						(int)(dimension.height * 0.078),null);

				//Button Play/Mute 
				g2d.drawImage(imgBtnBoard,(int)(dimension.width*0.335),
						(int)(dimension.height*0.48),
						(int)(dimension.width * 0.059),
						(int)(dimension.height * 0.078),null);//80,60
				g2d.setColor(Color.WHITE);
				g2d.setFont( new Font("Courier", Font.BOLD, (int)(dimension.height*0.03)));
				g2d.drawString(""+numPlayers/2,(int)(dimension.width * 0.605),(int)(dimension.height *0.516));
					
				String auxMenu = "On.png";
				if(musicMute){
					auxMenu = "Off.png";
				}
					Image imgBtnMuteMenu = new ImageIcon("../resources/images/objects/btn_png_volum"+auxMenu).getImage();
				g2d.drawImage(imgBtnMuteMenu,(int)(dimension.width*0.353),
							(int)(dimension.height*0.48666666),
							(int)(dimension.width*0.025),(int)(dimension.height*0.044),null);
					
				break;
			case "battle":
				btlBackTimer ++;
				this.add(btnExit);

				if(btlBackTimer == 666){//Aprox 10 seg;
					String[] aux = background.split("\\.")[0].split("\\_");
					switch(aux[1]){
						case "1":
							background = "battleBackground_2.png";
							btlBackTimer = 0;
							break;
						case "2":
							background = "battleBackground_3.png";
							btlBackTimer = 0;
							break;
						case "3":
							background = "battleBackground_1.png";
							btlBackTimer = 0;
							break;
					}
				}

				String url = "../resources/images/objects/";
			
				//Draw Settings Button
				Image imgBtnSettings = new ImageIcon(url+"btn_red_settings.png").getImage();
				g2d.drawImage(imgBtnSettings,(int)(dimension.width*0.0037),(int)(dimension.width*0.0037),
					(int)(dimension.width*0.029),(int)(dimension.height*0.052),null);

				for(int i=0; i<numPlayers;i++){
					if(player.get(i) != null){
						player.get(i).draw(g2d);
						Image imgPlayerFace = new ImageIcon("../resources/images/characters/"+
								player.get(i).getType()+"/"+player.get(i).getType()+"_Face.png").getImage();
						//Draw Player Hp
						if(i < numPlayers/2){
							g2d.drawImage(imgPlayerFace,(int)(dimension.width * 0.055),
									(int)(dimension.height * 0.047 + 
									((int)(dimension.height*0.053) * i) ),
							    	    	(int)(dimension.width*0.029),
								    	(int)(dimension.height*0.052),null );
							int auxhp = player.get(i).getHp();
							int auxhpMax = player.get(i).getHpMax();
							double porcentHp = (double)(auxhp) /(double)(auxhpMax + 0.01);

							g2d.setColor(new Color((int)(255 *(1- porcentHp)),
										(int)(255 * porcentHp),0));
							g2d.fillRect((int)(dimension.width * 0.087),
									(int)(dimension.height * 0.034 +
									((int)(dimension.height*0.053) * (i+1))),
									(int)(dimension.width*0.07 * porcentHp),
								    	(int)(dimension.height*0.012));
						}else{
							g2d.drawImage(imgPlayerFace,(int)(dimension.width * 0.926),
									(int)(dimension.height * 0.047 + 
									((int)(dimension.height*0.053) * (i-numPlayers/2)) ),
							    	    	(int)(dimension.width*0.029),
								    	(int)(dimension.height*0.052),null );
							int auxhp = player.get(i).getHp();
							int auxhpMax = player.get(i).getHpMax();
							double porcentHp = (double)(auxhp) /(double)(auxhpMax + 0.01);

							g2d.setColor(new Color((int)(255 *(1- porcentHp)),
										(int)(255 * porcentHp),0));
							g2d.fillRect((int)(dimension.width * 0.854)+(int)(dimension.width*0.07 *(1- porcentHp)),
									(int)(dimension.height * 0.034 +
									((int)(dimension.height*0.053) * ((i-numPlayers/2)+1))),
									(int)(dimension.width*0.07 * porcentHp),
								    	(int)(dimension.height*0.012));

						}
					}
				}


				//Verify Animation
				for(int i=0; i<numPlayers; i++){
					if(startAnimation.get(i) && finishAnimation.get(i)){
						if(player.indexOf(player.get(indexOf.get(i))) < numPlayers/2){
							player.get(indexOf.get(i)).setImage("Right.png");
						}else{
							player.get(indexOf.get(i)).setImage("Left.png");
						}
						indexOf.remove(i);
						indexOf.add(i,0);
						startAnimation.remove(i);
						startAnimation.add(i,Boolean.FALSE);
						finishAnimation.remove(i);
						finishAnimation.add(i,Boolean.FALSE);
					}
				}

				//Verify Round
				if(inRound && !finishGame){

					Player p = ordemPlayers().get(auxWhoInAttack);	
					int move = p.getMoveAttack();
					int target = p.getTargetAttack();
					int index = this.player.indexOf(p);
					String auxDirection = "";
					//Aux for correct Imperfection
					boolean auxImperGo, auxImperBack;

					if(index < numPlayers/2){
						if(p.getMovesStatus()[move][3] == 1 && !startAnimation.get(0)){
							auxConditionMove = 	
					 			(this.player.get(target).getX()- 
							 	this.player.get(target).getWidth()/2);
							auxImperGo = (p.getX() >= auxConditionMove);
							auxImperBack = (p.getX() <= auxPositionWhoAttack);
						}else{
							auxImperGo = true;
							auxImperBack = true;

						}
						auxDirection = "Right";
					}else{
						if(p.getMovesStatus()[move][3] == 1 && !startAnimation.get(0)){
							auxConditionMove = 	
					 			(this.player.get(target).getX()+
							 	this.player.get(target).getWidth()/2);
							auxImperGo = (p.getX() <= auxConditionMove);
							auxImperBack = (p.getX() >= auxPositionWhoAttack);
							auxDirection = "Left";
						}else{
							auxImperGo = true;
							auxImperBack = true;
						}
						auxDirection = "Left";
					}

					
			
					//Make Movimantation before ataque
					if(!startAnimation.get(0) && !auxImperGo
							&& !inPositionAttack && !inAnimationAttack){
						if(p.getMovesStatus()[move][3] == 1){
							auxPositionWhoAttack = p.getX(); 
							if(index< numPlayers/2){
								animation(0,"MoveR","Player",index,auxConditionMove);
							}else{
								animation(0,"MoveL","Player",index,auxConditionMove);
							}
						}
						
					}
					if(!startAnimation.get(0) && auxImperGo){
						inPositionAttack = true;
						inAnimationAttack = true;
					}

					if(inAnimationAttack){
						if(p.getMovesPP()[move] > 0){
							timeForAttackAnimation ++;
						
							this.player.get(index).setImage("Attack_"+(move+1)+"_"+auxDirection+".gif");
							if(timeForAttackAnimation == 162){
								timeForAttackAnimation = 0;
								inAnimationAttack = false;
								this.player.get(index).setImage(auxDirection+".png");

								executar(p);
							}
							
						}else{
							inAnimationAttack = false;
						}
					}

					//Make Movimantation after ataque
					if(!startAnimation.get(0) && !auxImperBack
						       	&& inPositionAttack && !inAnimationAttack){
						if(p.getMovesStatus()[move][3] == 1){
							if(index < numPlayers/2){
								animation(0,"MoveL","Player",index,auxPositionWhoAttack);
							}else{
								animation(0,"MoveR","Player",index,auxPositionWhoAttack);
							}
						}
					}
					
					if(!startAnimation.get(0) && !inAnimationAttack){
						auxWhoInAttack ++;
						
						inPositionAttack = false; 
						if(auxWhoInAttack > ordemPlayers().size() - 1){
							inRound = false;
							enablePanel(this,true);
						}
					}
					
					//Draw Select Player
					player.get(index).draw(g2d);

					//Verify if Game Over
					boolean lose = false;
					for(int i=0; i<numPlayers/2; i++){
						if(player.get(i) != null){
							lose = true;
						}
					}
			
					boolean win = false;
					for(int i=numPlayers/2; i<numPlayers; i++){
						if(player.get(i) != null){
							win = true;
						}
					}
			
					if(!win){
						inRound = false;
						finishGame = true;
						clearVariables();
						finishGameS = "You Win";
						 
					}
					if(!lose){
						inRound = false;
						finishGame = true;
						clearVariables();
						finishGameS = "Game Over";
					}
				}
			
				//Draw Start After Animation
				if(!(startAnimation.contains(Boolean.TRUE)) && !inRound && !finishGame){
					//Define Aux Battle
					auxWhoInAttack = 0;

					//Draw Target and Select
					Image imgTarget = new ImageIcon(url+"target.png").getImage();
					Image imgSelect = new ImageIcon(url+player.get(indexAttack).getType()+
										"_SelectIcon.png").getImage();
					int auxIndex = player.get(indexAttack).getTargetAttack();
						
					g2d.drawImage(imgTarget,player.get(auxIndex).getX()+ (int)(player.get(auxIndex).getWidth()*0.37) ,
								(int)(player.get(auxIndex).getY()*0.98),(int)(dimension.width*0.024),
								(int)(dimension.height*0.043),null);
					g2d.drawImage(imgSelect,player.get(indexAttack).getX()+ (int)(player.get(indexAttack).getWidth()*0.37) ,
								(int)(player.get(indexAttack).getY()*0.98),(int)(dimension.width*0.024),
								(int)(dimension.height*0.043),null);

					//Draw Select Player Moves
					g2d.setColor(Color.WHITE);
					for(int i=0; i<4; i++){
						int ltSize = player.get(indexAttack).getMovesNames()[i].length();
						int ltY,auxX;
						if(i%2 == 0){
							auxX = 311;
						}else{
							auxX = 443;
						}
						if(i<2){
							ltY = (int)(dimension.height*0.839);
						}else{
							ltY = (int)(dimension.height*0.91);
						}
						double letterSizeW = 0, letterSizeH = 0;
						if(ltSize <= 10){
							String aux = "0.025";
							letterSizeH = Double.parseDouble(aux);
							aux = "0."+(auxX+4*(10-ltSize));
							letterSizeW = Double.parseDouble(aux);
						}else{
							String aux = "0.0" + (25 - 2*(ltSize - 10)); 
							letterSizeH = Double.parseDouble(aux);
							aux = "0."+auxX;
							letterSizeW = Double.parseDouble(aux);
						}
						g2d.setFont( new Font("Courier", Font.BOLD, (int)(dimension.height*letterSizeH)));
						g2d.drawString(player.get(indexAttack).getMovesNames()[i],
									(int)(dimension.width*letterSizeW),ltY);
						//Drawing Line in Select Move
						if(i == player.get(indexAttack).getMoveAttack()){
							g2d.fillRect((int)(dimension.width*letterSizeW),
									ltY+(int)(dimension.height*0.0065),
									(int)(letterSizeH * 1000 * 0.44 * ltSize),
									(int)(dimension.height*0.0035));
						}
					}
					//Attack Button
					g2d.setFont( new Font("Courier", Font.BOLD, (int)(dimension.height*0.023)));
					g2d.drawString("Attack!",(int)(dimension.width * 0.633),(int)(dimension.height *0.9));
					
					//Drawing PP Attack
					int move = player.get(indexAttack).getMoveAttack();
					g2d.setColor(Color.LIGHT_GRAY);
					g2d.setFont( new Font("Courier", Font.BOLD, (int)(dimension.height*0.025)));
					g2d.drawString(player.get(indexAttack).getMovesPP()[move]+"/"+
							player.get(indexAttack).getMovesStatus()[move][0],
							(int)(dimension.width * 0.6),(int)(dimension.height * 0.85));
					String auxTypeMove;
					if(player.get(indexAttack).getMovesStatus()[move][2] == 1){
						auxTypeMove = "Buff/"+player.get(indexAttack).getMovesStatusBuff()[move];
					}else{
						auxTypeMove = "  Attack";
					}
					g2d.setFont( new Font("Courier", Font.BOLD, (int)(dimension.height*0.018)));
					g2d.drawString(auxTypeMove,(int)(dimension.width * 0.655),(int)(dimension.height * 0.85));

				}
				if(inSettings){
					//Background
					g2d.setColor(new Color(0,0,0,145));
					g2d.fillRect(0,0,dimension.width,dimension.height);
					
					//Image Buttons
					Image imgBtnBackMenu = new ImageIcon(url+"btn_red_close.png").getImage();
					g2d.drawImage(imgBtnBackMenu,dimension.width/2 -(int)(dimension.width*0.029*1.55), dimension.height/2,
								(int)(dimension.width*0.029),(int)(dimension.height*0.052),null);
				
					String aux = "On.png";
					if(musicMute){
						aux = "Off.png";
					}
					Image imgBtnMute = new ImageIcon(url+"btn_red_volum"+aux).getImage();
					g2d.drawImage(imgBtnMute,dimension.width/2 -(int)(dimension.width*0.029 /2), dimension.height/2,
								(int)(dimension.width*0.029),(int)(dimension.height*0.052),null);
					
					Image imgBtnBack = new ImageIcon(url+"btn_red_back.png").getImage();
					g2d.drawImage(imgBtnBack,dimension.width/2 +(int)(dimension.width*0.029*0.6), dimension.height/2,
								(int)(dimension.width*0.029),(int)(dimension.height*0.052),null);
				}

				//Finish Game Screen
				if(finishGame){
					timeForAttackAnimation ++;
					inRound = false;
					//Background
					g2d.setColor(new Color(0,0,0,145));
					g2d.fillRect(0,0,dimension.width,dimension.height);

					if(finishGameS.equals("You Win")){
						g2d.setColor(new Color(0, 220, 124));
						g2d.setFont( new Font("Courier", Font.BOLD, (int)(dimension.height*0.05)));
						g2d.drawString("You Win!! :)",(int)(dimension.width*0.4),dimension.height/2);
					}else{
						g2d.setColor(new Color(200, 40, 5));
						g2d.setFont( new Font("Courier", Font.BOLD, (int)(dimension.height*0.05)));
						g2d.drawString("Game Over!! :(",(int)(dimension.width*0.39),dimension.height/2);
					}

					if(timeForAttackAnimation == 200  ){
						timeForAttackAnimation = 0;
						//Define Game Finish Button
						finishGame = false;
						finishGameS = "";
						player.clear();
						enablePanel(this,true);
        	                       		menuScreen();
					}
				}

				//Drawing select Character upside others
				break;
			default:
				break;
		}

	}

	//GameLoop in 15 miliseconds
	@Override
	public void actionPerformed(ActionEvent arg0){
		for(int i = 0;i<numPlayers;i++){
			
		switch(whoAnimation.get(i)){
			case "Object":
				if((typeAnimation.get(i) == "MoveR") &&
					(characObjects[indexOf.get(i)].getX()<conditionMove.get(i))){
					characObjects[indexOf.get(i)].setImage("Walk_Right.gif");
					characObjects[indexOf.get(i)].update(typeAnimation.get(i));
				}else if((typeAnimation.get(i) == "MoveL") &&
					(characObjects[indexOf.get(i)].getX()>conditionMove.get(i))){
					characObjects[indexOf.get(i)].setImage("Walk_Left.gif");
					characObjects[indexOf.get(i)].update(typeAnimation.get(i));
				}else{
					this.whoAnimation.remove(i);
					this.whoAnimation.add(i,"");
					this.typeAnimation.remove(i);
					this.typeAnimation.add(i,"");
					this.conditionMove.remove(i);
					this.conditionMove.add(i,0);
					this.finishAnimation.remove(i);
					this.finishAnimation.add(i,Boolean.TRUE);
				}
				break;
			case "Player":
				if((typeAnimation.get(i) == "MoveR") &&
					(player.get(indexOf.get(i)).getX()<conditionMove.get(i))){
					player.get(indexOf.get(i)).setImage("Walk_Right.gif");
					player.get(indexOf.get(i)).update(typeAnimation.get(i));
				}else if((typeAnimation.get(i) == "MoveL") &&
					(player.get(indexOf.get(i)).getX()>conditionMove.get(i))){
					player.get(indexOf.get(i)).setImage("Walk_Left.gif");
					player.get(indexOf.get(i)).update(typeAnimation.get(i));
				}else{
					this.whoAnimation.remove(i);
					this.whoAnimation.add(i,"");
					this.typeAnimation.remove(i);
					this.typeAnimation.add(i,"");
					this.conditionMove.remove(i);
					this.conditionMove.add(i,0);
					this.finishAnimation.remove(i);
					this.finishAnimation.add(i,Boolean.TRUE);				}
				break;
			default:
				break;
		}}

		repaint();
		//Add Exit Button
		btnExit.setEnabled(true);
		this.add(btnExit);
	}

}
