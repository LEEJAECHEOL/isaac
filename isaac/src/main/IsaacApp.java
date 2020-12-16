package main;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JFrame;

import SpriteSheet.SpriteSheet;
import character.Isaac;
import item.Bomb;
import item.Heart;
import item.Item;
import item.Key;
import map.Background;
import monster.Monster;
import monster.Stone;
import monster.Worm;
import objectSize.BombSize;
import objectSize.DoorSize;
import objectSize.Gap;
import objectSize.KeySize;
import objectSize.RecoveryLifeSize;
import objectSize.StoneSize;
import objectSize.WormSize;
import structure.Door;
import structure.Rock;
import structure.Spike;
import structure.Structure;

public class IsaacApp extends JFrame {
	private JFrame app;
	
	private Background bg;	// background
	private Isaac isaac;	// player
	private Vector<Monster> monster;
	private Vector<Structure> structures;	// rock, spike
	private Vector<Door> door;
	private Vector<Item> items;
	private boolean noMonster = false;
	
	public IsaacApp() {
		init();
		setting();
		batch();
		listener();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!noMonster) {
					notMonsterIsDoorOpen();
				}
			}
		}).start();
		setVisible(true);
	}
	
	public void init() {
		app = this;
		bg = new Background(app);
		structures = new Vector<Structure>();
		monster = new Vector<Monster>();
		door = new Vector<Door>();
		items = new Vector<Item>();
		
		// 구석자리 바위
		structures.add(new Rock(app, 125, 115));
		structures.add(new Rock(app, 640, 115));
		structures.add(new Rock(app, 740, 115));
		structures.add(new Rock(app, 785, 160));
		
		// center 바위
		structures.add(new Rock(app, 425, 225));
		structures.add(new Rock(app, 475, 225));
		structures.add(new Rock(app, 425, 275));
		structures.add(new Rock(app, 475, 275));
		structures.add(new Rock(app, 425, 325));
		structures.add(new Rock(app, 475, 325));
		

		structures.add(new Spike(app, 125, 165));
		structures.add(new Spike(app, 130, 445));
		structures.add(new Spike(app, 180, 445));
		structures.add(new Spike(app, 675, 275));
		structures.add(new Spike(app, 675, 325));
		
		
		items.add(new Key(app, "item/key.png", "key", 790, 450, KeySize.WIDTH, KeySize.HEIGHT ));
		items.add(new Bomb(app, "item/bomb.png", "bomb", 140, 400, BombSize.PICKWIDTH, BombSize.PICKHEIGHT ));
		items.add(new Heart(app, "item/RecoveryLife.png", "life", 400, 400, RecoveryLifeSize.WIDTH, RecoveryLifeSize.HEIGHT ));
		
		isaac = new Isaac(app, structures, monster, items, door);
		monster.add(new Worm(app, isaac, structures, "monster/worm.png", WormSize.WIDTH, WormSize.HEIGHT));
		monster.add(new Stone(app, isaac, structures, "monster/stone.png", StoneSize.WIDTH, StoneSize.HEIGHT));
		door.add(new Door(app,new SpriteSheet("structure/Door.png", "door", 0, 0, DoorSize.WIDTH, DoorSize.HEIGHT),"North", 420, 40));
		door.add(new Door(app,new SpriteSheet("structure/Door.png", "door", 279, 0, DoorSize.WIDTH, DoorSize.HEIGHT),"West", 50, 260));

	}
	
	public void setting() {
		app.setTitle("IsaacApp");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setLocationRelativeTo(null);
		app.setSize(960, 640);
		app.setLayout(null);
	}
	
	public void batch() {
		
	}
	public void notMonsterIsDoorOpen() {
		int deadMonsterCount = 0;
		
		for(int i = 0; i < monster.size(); i++) {
			if(monster.get(i).isDead()) {
				deadMonsterCount++;
			}
		}
		if(deadMonsterCount == monster.size()) {
			noMonster = true;
			for(int i = 0; i < door.size(); i++) {
				System.out.println(door.get(i).getGubun());
				if(door.get(i).getSsDoor().getGubun() == "door") {
					door.get(i).getSsDoor().setYPos(DoorSize.HEIGHT + Gap.ROWGAP);
					door.get(i).getSsDoor().drawObject(door.get(i).getXDoor(), door.get(i).getYDoor());
				}
			}
		}
	}
	public void listener() {
		keyboardEvent();
	}
	
	
	public static void main(String[] args) {
		new IsaacApp();
	}

	public void keyboardEvent() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					isaac.moveRight();
				} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					isaac.moveLeft();
				} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					isaac.moveDown();
				} else if(e.getKeyCode() == KeyEvent.VK_UP) {
					isaac.moveUp();
				} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					isaac.attack();
				} else if(e.getKeyCode() == 66) {
					isaac.useBomb();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					isaac.setRight(false);
					isaac.refreshDirect();
				} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					isaac.setLeft(false);
					isaac.refreshDirect();
				} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					isaac.setDown(false);
					isaac.refreshDirect();
				} else if(e.getKeyCode() == KeyEvent.VK_UP) {
					isaac.setUp(false);
					isaac.refreshDirect();
				} 
			}
		});
	}
	

}
