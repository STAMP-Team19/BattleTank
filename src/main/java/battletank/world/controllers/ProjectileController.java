package battletank.world.controllers;

import battletank.controls.ActionInfo;
import battletank.world.IGame;
import battletank.world.events.go.CreateProjectile;
import battletank.world.events.transitions.StartTransition;
import battletank.world.gameobjects.Player;
import battletank.world.gameobjects.PlayerColor;
import battletank.world.gameobjects.Projectile;

public class ProjectileController extends ActionController {

    //TODO: Add functionality to remove project if projectile healthpoints reaches 0.
    public ProjectileController(IGame game, ActionInfo action) {
        Player player = game.getPlayer(action.getUserIdentifier());

        game.addPlayerEvent(player,new CreateProjectile(player.getName(),0));

    }
}
