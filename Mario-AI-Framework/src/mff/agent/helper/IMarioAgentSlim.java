package mff.agent.helper;

import engine.core.MarioTimer;
import mff.forwardmodel.slim.core.MarioForwardModelSlim;

public interface IMarioAgentSlim {

    void initialize(MarioForwardModelSlim model, MarioTimer timer);

    boolean[] getActions(MarioForwardModelSlim model, MarioTimer timer);

    String getAgentName();
    
}
