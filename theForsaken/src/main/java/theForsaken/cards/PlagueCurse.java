package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theForsaken.TheForsakenMod;
import theForsaken.relics.PlagueMask;

public class PlagueCurse extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(PlagueCurse.class.getSimpleName());

    private static final int COST = -2;
    private static final int POISON_AMT = 2;

    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    public PlagueCurse() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PoisonPower(p, p, POISON_AMT), POISON_AMT));
            if(p.hasRelic(PlagueMask.ID)) {
                p.getRelic(PlagueMask.ID).flash();
                AbstractMonster mo = AbstractDungeon.getRandomMonster();
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, POISON_AMT), POISON_AMT));
            }
        }

    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public AbstractCard makeCopy() {
        return new PlagueCurse();
    }

    public void upgrade() {
    }
}
