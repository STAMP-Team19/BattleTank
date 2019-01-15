package battletank.controllers;

import battletank.world.gameobjects.Projectile;

public class ProjectileController extends ActionController {

    //TODO: Add functionality to remove project if projectile healthpoints reaches 0.
    public ProjectileController(Projectile projectile) {
        //Assuming here that the plane hit is perfectly level (180 degrees).
        double initialRotation = projectile.getRotation();
        int totRotation = 180;

        //Calculate sigma, since (sigma - projectileRotation = 90 degrees)
        double sigma = (totRotation / 2) - initialRotation;

        //Using the formula: initialRotation + 2*sigma + resultRotation = 180 degrees, we get:
        double resRotation = initialRotation + (2 * sigma);

        projectile.setRotation(resRotation);

    }
}
