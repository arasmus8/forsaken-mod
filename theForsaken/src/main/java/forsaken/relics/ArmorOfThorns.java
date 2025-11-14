package forsaken.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import forsaken.TheForsakenMod;
import forsaken.util.TextureLoader;

import static forsaken.TheForsakenMod.relicOutlineResourcePath;
import static forsaken.TheForsakenMod.relicResourcePath;


public class ArmorOfThorns extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * If you play no attacks, gain 1 strength, If you play no skills, gain 1 dexterity.
     */

    // ID, images, text.
    public static final String ID = TheForsakenMod.makeID(ArmorOfThorns.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(relicResourcePath("ArmorOfThorns.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicOutlineResourcePath("ArmorOfThorns.png"));

    public ArmorOfThorns() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        if (blockAmount >= 1.0F) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) null, DamageInfo.createDamageMatrix(3, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        return super.onPlayerGainedBlock(blockAmount);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public boolean canSpawn() {
        return !UnlockTracker.isRelicLocked(this.relicId);
    }
}