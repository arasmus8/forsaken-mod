package theForsaken.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theForsaken.TheForsakenMod;
import theForsaken.util.TextureLoader;

import static theForsaken.TheForsakenMod.relicOutlineResourcePath;
import static theForsaken.TheForsakenMod.relicResourcePath;


public class JudgementScales extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * If you play no attacks, gain 1 strength, If you play no skills, gain 1 dexterity.
     */

    // ID, images, text.
    public static final String ID = TheForsakenMod.makeID(JudgementScales.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(relicResourcePath("JudgementScales.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicOutlineResourcePath("JudgementScales.png"));

    private boolean firstTurn = false;
    private boolean gainStr = false;
    private boolean gainDex = false;

    public JudgementScales() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        this.firstTurn = true;
        this.gainStr = true;
        this.gainDex = true;
        if (!this.pulse) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    // Reset vars at turn start
    @Override
    public void atTurnStart() {
        if (!this.firstTurn && (this.gainDex || this.gainStr)) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            // Gain str/dex based on last turn's actions
            if (this.gainStr) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
            }

            if (this.gainDex) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
            }
        }

        this.beginPulse();
        this.pulse = true;
        this.firstTurn = false;
        this.gainDex = true;
        this.gainStr = true;
    }

    // Disable str gain when an attack is played.
    // Disable dex gain when a skill is played.
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.gainStr = false;
        }
        if (card.type == AbstractCard.CardType.SKILL) {
            this.gainDex = false;
        }
        if (!this.gainDex && !this.gainStr) {
            this.pulse = false;
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

}
