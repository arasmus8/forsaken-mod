package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.RetributionPower;

public class Retribution extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Retribution.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int THORNS_AMT = 5;
    private static final int UPGRADE_THORNS_AMT = 2;

    public Retribution() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        this.isEthereal = true;
        this.baseMagicNumber = THORNS_AMT;
        this.magicNumber = THORNS_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            this.exhaust = true;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RetributionPower(p, 1), 1));
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        this.freeToPlayOnce = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_THORNS_AMT);
            initializeDescription();
        }
    }
}