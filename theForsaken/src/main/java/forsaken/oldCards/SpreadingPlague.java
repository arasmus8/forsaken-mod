package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.OldSpreadingPlaguePower;

@SuppressWarnings("unused")
public class SpreadingPlague extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(SpreadingPlague.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int POISON_AMT = 2;
    private static final int UPGRADE_POISON_AMT = 1;

    public SpreadingPlague() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseMagicNumber = POISON_AMT;
        magicNumber = POISON_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new OldSpreadingPlaguePower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_POISON_AMT);
            initializeDescription();
        }
    }
}