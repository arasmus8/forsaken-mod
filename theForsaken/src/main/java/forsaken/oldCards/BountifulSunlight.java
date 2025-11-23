package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;

public class BountifulSunlight extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(BountifulSunlight.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int MAGIC = 4;
    private static final int UPGRADED_MAGIC = 2;

    public BountifulSunlight() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, CardTags.HEALING);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}