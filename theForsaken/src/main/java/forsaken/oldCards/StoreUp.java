package forsaken.oldCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.StoreUpAction;
import forsaken.characters.TheForsaken;

@SuppressWarnings("unused")
public class StoreUp extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(StoreUp.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 0;

    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC_AMT = 1;

    public StoreUp() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new StoreUpAction(magicNumber));
    }

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