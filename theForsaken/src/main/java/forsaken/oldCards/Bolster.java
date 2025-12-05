package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.OldBolsterPower;

@SuppressWarnings("unused")
public class Bolster extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(Bolster.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int BLOCK_AMT = 3;

    public Bolster() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseMagicNumber = BLOCK_AMT;
        magicNumber = BLOCK_AMT;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new OldBolsterPower(p, this.magicNumber)));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            isInnate = true;
            initializeDescription();
        }
    }
}