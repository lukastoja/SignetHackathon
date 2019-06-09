package Engine;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import Engine.EngineCore.StisakTipke;

public abstract class EngineEpisode {
	ArrayList<ViewComponent> viewComponents = new ArrayList<>();
	ArrayList<EngineThread> threads = new ArrayList<>();
	ArrayList<CollisionGroup> collisionGroups = new ArrayList<>();
	
	ArrayList<ViewComponent> viewComponentsToAdd = new ArrayList<>();
	ArrayList<EngineThread> threadsToAdd = new ArrayList<>();
	ArrayList<CollisionGroup> collisionGroupsToAdd = new ArrayList<>();
	
	InputMap im = new InputMap();
    ActionMap am = new ActionMap();
	EngineCore engine;
	CameraController cameraController=null;
	
	public abstract void mainLoop(long renderDeltaTime);
	public abstract void keyPressed(int key, int state);
	
	public void setEngine(EngineCore core) {
		this.engine = core;
	}
	
	public EngineCore getEngine() {
		return engine;
	}
	
	public void addViewComponent(ViewComponent v) {
		viewComponentsToAdd.add(v);
	}
	
	public boolean isKeyPressed(int key) {
		return engine.isKeyPressed(key);
	}
	
	public void bindKey(int key, String name) {
        im.put(KeyStroke.getKeyStroke(key, 0, false), name + "-pressed");
        im.put(KeyStroke.getKeyStroke(key, 0, true), name + "-released");
        
        am.put(name + "-pressed", engine.createStisakTipke(key, true));
        am.put(name + "-released", engine.createStisakTipke(key, false));
	}
	
	public void setCameraController(CameraController camera) {
		this.cameraController = camera;
	}
	
	public CameraController getCameraController() {
		return this.cameraController;
	}
	
	public void addCollisionGroup(CollisionGroup colGrp) {
		this.collisionGroupsToAdd.add(colGrp);
	}
	
	public abstract void init(EngineCore engineCore);
}
