package processor;

import com.jme3.app.state.AppState;

public interface JmeProcessor extends AppState {
	
	enum TYPE {
		VISUAL, AUDIO, COMMAND
	}
	
	public TYPE getType();
	
}
