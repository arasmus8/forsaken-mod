package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;

public class DefensiveStance extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(DefensiveStance.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int PLATED_ARMOR_AMT = 5;
    private static final int UPGRADE_PLATED_ARMOR_AMT = 2;

    public DefensiveStance() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseMagicNumber = PLATED_ARMOR_AMT;
        magicNumber = PLATED_ARMOR_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLATED_ARMOR_AMT);
            initializeDescription();
        }
    }
}