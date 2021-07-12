package agents.robinBaumgarten2009;

import engine.core.MarioForwardModel;

public class EnvironmentAdapter {

	private MarioForwardModel model;
	
	public EnvironmentAdapter(MarioForwardModel model) {
		this.model = model;
	}

	public byte[][] getLevelSceneObservationZ(int zLevel) {
		assert(zLevel == 0);
		// TODO Auto-generated method stub
		return null;
	}

	public float[] getEnemiesFloatPos() {
		// TODO Auto-generated method stub
		return null;
	}

	public float[] getMarioFloatPos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
