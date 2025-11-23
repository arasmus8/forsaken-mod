package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;


public class CorruptDeckPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(CorruptDeckPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int DRAW_AMOUNT = 2;
    public int maxCardsEachTurn;

    public CorruptDeckPower(final AbstractCreature owner, final int maxCardsEachTurn) {
        super(POWER_ID, owner, maxCardsEachTurn);

        this.maxCardsEachTurn = maxCardsEachTurn;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.loadRegion("corrupt_deck");
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
