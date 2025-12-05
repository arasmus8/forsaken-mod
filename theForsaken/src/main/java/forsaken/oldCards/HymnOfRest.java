package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.HymnOfRestPower;

@SuppressWarnings("unused")
public class HymnOfRest extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(HymnOfRest.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = -2;

    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC_AMT = 1;

    private int otherCardsPlayed;
    private static final int BONUS_THRESHOLD = 4;

    public HymnOfRest() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (dontTriggerOnUseCard) {
            AbstractPower pwr = new HymnOfRestPower(p, magicNumber, otherCardsPlayed < BONUS_THRESHOLD);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, pwr, magicNumber));
        }
        if (p.hasPower(HymnOfRestPower.POWER_ID) && otherCardsPlayed < BONUS_THRESHOLD) {
            HymnOfRestPower pow = (HymnOfRestPower) p.getPower(HymnOfRestPower.POWER_ID);
            pow.retainAll();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.otherCardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_AMT);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}