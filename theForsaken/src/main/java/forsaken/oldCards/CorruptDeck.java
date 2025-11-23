package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.CorruptDeckPower;

public class CorruptDeck extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(CorruptDeck.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int MAX_CARDS = 3;
    private static final int UPGRADE_MAX_CARDS = 1;

    public CorruptDeck() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseMagicNumber = MAX_CARDS;
        magicNumber = MAX_CARDS;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CorruptDeckPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAX_CARDS);
            initializeDescription();
        }
    }
}