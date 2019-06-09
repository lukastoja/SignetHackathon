package Engine;

public abstract class EngineThread {
	EngineCore core;
	boolean stopFlag = false;
	public EngineThread() {
	}
	
	public void setEngine(EngineCore core) {
		this.core = core;
	}
	
	public void stop() {
		stopFlag = true;
	}

	public abstract void loop(long deltaTime);
	
}
