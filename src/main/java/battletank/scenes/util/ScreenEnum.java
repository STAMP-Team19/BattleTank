package battletank.scenes.util;

import battletank.scenes.screen.*;


public enum ScreenEnum {

	LEVEL_SELECT {
		public AbstractScreen getScreen(Object... params) {
			return new JoinMenu();
		}
	};
	
	public abstract AbstractScreen getScreen(Object... params);
}
