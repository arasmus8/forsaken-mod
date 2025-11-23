package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.BonusDamagePower;

public class Penitence extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(Penitence.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    private static final int DMG_AMT = 3;
    private static final int UPGRADE_DMG_AMT = 2;

    public Penitence() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseBlock = BLOCK;
        baseMagicNumber = DMG_AMT;
        magicNumber = DMG_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BonusDamagePower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_DMG_AMT);
            initializeDescription();
        }
    }
}