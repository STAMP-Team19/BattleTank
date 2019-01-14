package battletank.scene_management.util;

import battletank.scene_management.screen.*;


public enum ScreenEnum {

	LEVEL_SELECT {
		public AbstractScreen getScreen(Object... params) {
			return new JoinMenu();
		}
	};
	
	public abstract AbstractScreen getScreen(Object... params);
}
