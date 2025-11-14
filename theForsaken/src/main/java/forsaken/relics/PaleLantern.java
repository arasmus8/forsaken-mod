package forsaken.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.TheForsakenMod;
import forsaken.util.TextureLoader;

import static forsaken.TheForsakenMod.relicOutlineResourcePath;
import static forsaken.TheForsakenMod.relicResourcePath;


public class PaleLantern extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = TheForsakenMod.makeID("PaleLantern");

    private static final Texture IMG = TextureLoader.getTexture(relicResourcePath("PaleLantern.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicOutlineResourcePath("PaleLantern.png"));

    public PaleLantern() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
        AbstractDungeon.player.gameHandSize = AbstractDungeon.player.masterHandSize - 1;
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
