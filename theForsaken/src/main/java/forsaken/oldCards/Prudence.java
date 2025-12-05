package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.OldPrudencePower;

@SuppressWarnings("unused")
public class Prudence extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(Prudence.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int BLOCK = 2;
    private static final int UPGRADE_BLOCK = 1;

    public Prudence() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseMagicNumber = BLOCK;
        magicNumber = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new OldPrudencePower(this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}