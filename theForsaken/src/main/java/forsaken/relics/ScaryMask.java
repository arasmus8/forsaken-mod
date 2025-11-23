package forsaken.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import forsaken.TheForsakenMod;
import forsaken.powers.OldFearPower;
import forsaken.util.TextureLoader;

import static forsaken.TheForsakenMod.relicOutlineResourcePath;
import static forsaken.TheForsakenMod.relicResourcePath;


public class ScaryMask extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * If you play no attacks, gain 1 strength, If you play no skills, gain 1 dexterity.
     */

    // ID, images, text.
    public static final String ID = TheForsakenMod.makeID(ScaryMask.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(relicResourcePath("ScaryMask.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicOutlineResourcePath("ScaryMask.png"));

    private boolean active;

    public ScaryMask() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
        this.active = false;
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        this.active = true;
        if (!this.pulse) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    // Apply 1 fear on first attack of battle
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (this.active && card.type == AbstractCard.CardType.ATTACK) {
            this.active = false;
            this.pulse = false;

            if (card.target.equals(AbstractCard.CardTarget.ALL) || card.target.equals(AbstractCard.CardTarget.ALL_ENEMY)) {
                for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new OldFearPower(m, 1, false), 1));
                }
            } else {
                AbstractCreature m = action.target;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new OldFearPower(m, 1, false), 1));
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        this.pulse = false;
    }

    @Override
    public boolean canSpawn() {
        return !UnlockTracker.isRelicLocked(this.relicId);
    }
}
