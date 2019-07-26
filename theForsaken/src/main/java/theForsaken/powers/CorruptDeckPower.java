package theForsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theForsaken.TheForsakenMod;
import theForsaken.util.TextureLoader;

import static theForsaken.TheForsakenMod.makePowerPath;


//Reduce damage dealt to 1

public class CorruptDeckPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(CorruptDeckPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("corrupt_deck84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("corrupt_deck32.png"));

    private static final int DRAW_AMOUNT = 2;
    public int maxCardsEachTurn;

    public CorruptDeckPower(final AbstractCreature owner, final int maxCardsEachTurn) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.maxCardsEachTurn = maxCardsEachTurn;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, DRAW_AMOUNT));
        updateDescription();
    }

    @Override
    public void updateDescription() {
        int i = maxCardsEachTurn - AbstractDungeon.player.cardsPlayedThisTurn;
        if (i == 0) {
            description = DESCRIPTIONS[3];
        } else if (i == 1) {
            description = DESCRIPTIONS[0] + i + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + i + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new CorruptDeckPower(owner, maxCardsEachTurn);
    }
}
