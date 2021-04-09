package forwardmodel.bin.sprites;

//TODO: there is a bullet bill spawner - needs attention

import forwardmodel.bin.core.MarioBinData;

public class BulletBillBin {

    public static float getX(MarioBinData data, int entityIndex) {
        return data.floats[data.spriteStorageInfo[MarioBinData.FLOATS_BULLET_BILL_START]
                + entityIndex * MarioBinData.BULLET_BILL_FLOATS + MarioBinData.BULLET_BILL_X];
    }

    public static void setX(MarioBinData data, int entityIndex, float value) {
        data.floats[data.spriteStorageInfo[MarioBinData.FLOATS_BULLET_BILL_START]
                + entityIndex * MarioBinData.BULLET_BILL_FLOATS + MarioBinData.BULLET_BILL_X]
                = value;
    }


	/*public static IBinUpdate update = new IBinUpdate() {

		@Override
		public void Update(MarioBinData data, int entityIndex) {
			update(data, entityIndex);
		}
		
	};
	
	public static int getFacing(MarioBinData data, int entityIndex) {
    	return data[entityIndex * 4];
    }
    
    public static int setFacing(MarioBinData data, int entityIndex, int value) {
    	data.floats[entityIndex * 4] = value;
    }
    
    public static int incFacing(MarioBinData data, int entityIndex, int delta) {
    	data.floats[entityIndex * 4] += delta;
    }
    
    public static void update(MarioBinData data, int entityIndex) {
    	//...
    }*/
	
}
