package battletank.scene_management.util;

import battletank.scene_management.screen.AbstractScreen;
import battletank.scene_management.screen.JoinMenu;
import battletank.scene_management.screen.MainMenuScreen;


public enum ScreenEnum {
	
	MAIN_MENU {
		public AbstractScreen getScreen(Object... params) {
			return new MainMenuScreen();
		}
	},

	LEVEL_SELECT {
		public AbstractScreen getScreen(Object... params) {
			return new JoinMenu();
		}
	},

	GAME {
		public AbstractScreen getScreen(Object... params) {
			return new JoinMenu();
		}
	};
	
	public abstract AbstractScreen getScreen(Object... params);
}
