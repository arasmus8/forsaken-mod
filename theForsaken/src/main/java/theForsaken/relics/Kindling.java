package theForsaken.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theForsaken.TheForsakenMod;
import theForsaken.util.TextureLoader;

import static theForsaken.TheForsakenMod.makeRelicOutlinePath;
import static theForsaken.TheForsakenMod.makeRelicPath;


public class Kindling extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * If you play no attacks, gain 1 strength, If you play no skills, gain 1 dexterity.
     */

    // ID, images, text.
    public static final String ID = TheForsakenMod.makeID(Kindling.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Kindling.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Kindling.png"));

    private static final int MAX_HP_AMOUNT = 5;

    public Kindling() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MAX_HP_AMOUNT + LocalizedStrings.PERIOD;
    }

    @Override
    public void onRest() {
        this.flash();
        AbstractDungeon.player.increaseMaxHp(MAX_HP_AMOUNT, true);
    }

    @Override
    public boolean canSpawn() {
        return !UnlockTracker.isRelicLocked(this.relicId);
    }
}