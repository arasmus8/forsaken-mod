package theForsaken.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theForsaken.TheForsakenMod;
import theForsaken.util.TextureLoader;

import static theForsaken.TheForsakenMod.makeRelicOutlinePath;
import static theForsaken.TheForsakenMod.makeRelicPath;


public class ScalesOfTruth extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 str for every 3 unplayed attacks, 1 dex for every 3 unplayed skills.
     */

    // ID, images, text.
    public static final String ID = TheForsakenMod.makeID(ScalesOfTruth.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ScalesOfTruth.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ScalesOfTruth.png"));

    private int skillCount = 0;
    private int attackCount = 0;

    public ScalesOfTruth() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        skillCount = 0;
        attackCount = 0;
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.hand.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                attackCount += 1;
            } else if (c.type == AbstractCard.CardType.SKILL) {
                skillCount += 1;
            }
        }
        while (skillCount >= 3) {
            skillCount -= 3;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        }
        while (attackCount >= 3) {
            attackCount -= 3;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            this.flash();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(JudgementScales.ID);
    }
}