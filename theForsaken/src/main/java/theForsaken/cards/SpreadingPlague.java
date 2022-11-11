package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.powers.SpreadingPlaguePower;

public class SpreadingPlague extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(SpreadingPlague.class.getSimpleName());

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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpreadingPlaguePower(p, this.magicNumber)));
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