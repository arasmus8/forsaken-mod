package theForsaken.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theForsaken.TheForsakenMod;
import theForsaken.util.TextureLoader;

import static theForsaken.TheForsakenMod.relicOutlineResourcePath;
import static theForsaken.TheForsakenMod.relicResourcePath;


public class PlagueMask extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * If you play no attacks, gain 1 strength, If you play no skills, gain 1 dexterity.
     */

    // ID, images, text.
    public static final String ID = TheForsakenMod.makeID(PlagueMask.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(relicResourcePath("PlagueMask.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicOutlineResourcePath("PlagueMask.png"));

    public PlagueMask() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}