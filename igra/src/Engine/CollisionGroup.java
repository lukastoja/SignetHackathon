package Engine;

import java.util.ArrayList;

public class CollisionGroup {
	ArrayList<BasicElement> subgroup1 = new ArrayList<>();
	ArrayList<BasicElement> subgroup2 = new ArrayList<>();
	boolean removeFlag = false;
	CollisionListener listener;
	
	public void addToSubgroup1(BasicElement obj) {
		if(obj == null)System.out.println("[Error] Dodao si null");
		if(obj.getBoundingRadius() == 0.0f)System.out.println("[Warning] Bounding radius nije postavljen");
		subgroup1.add(obj);
	}
	
	public void addToSubgroup2(BasicElement obj) {
		if(obj == null)System.out.println("[Error] Dodao si null");
		if(obj.getBoundingRadius() == 0.0f)System.out.println("[Warning] Bounding radius nije postavljen");
		subgroup2.add(obj);
	}
	
	public void setCollisionListener(CollisionListener l) {
		this.listener = l;
	}
	
	public void checkCollision() {
		if(listener != null)return;
		for(int i=0; i < subgroup1.size(); i++) {
			BasicElement el1 = subgroup1.get(i);
			if(el1.removeFlag == true) {
				subgroup1.remove(i);
				i--;
				continue;
			}
			
			for(int j=0; j < subgroup2.size(); j++) {
				BasicElement el2 = subgroup2.get(j);
				if(el2.removeFlag == true) {
					subgroup2.remove(j);
					j--;
					continue;
				}
				
				Position pos1 = el1.getDrawingPosition();
				Position pos2 = el2.getDrawingPosition();
				
				int dx = pos1.getX() - pos2.getX();
				int dy = pos1.getY() - pos2.getY();
				
				float dist = el1.getBoundingRadius() + el2.getBoundingRadius();
				dist = dist * dist;
				
				if(dx*dx + dy*dy < dist) {
					System.out.println("Kolizija");
					try {
						listener.onCollision(el1, el2);
					}catch(java.lang.NullPointerException np) {
						np.printStackTrace();
					}
				}
			}
		}
	}
	
	public void remove() {
		this.removeFlag = true;
	}
}
