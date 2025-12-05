package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.OldFearPower;

@SuppressWarnings("unused")
public class Terrorize extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(Terrorize.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int FEAR_AMOUNT = 1;
    private static final int UPGRADE_FEAR_AMOUNT = 1;

    public Terrorize() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        this.baseMagicNumber = FEAR_AMOUNT;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new OldFearPower(m, this.magicNumber, false), this.magicNumber, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_FEAR_AMOUNT);
            initializeDescription();
        }
    }
}
