package forsaken.oldCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.WrathOfGodAction;
import forsaken.characters.TheForsaken;

public class WrathOfGod extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(WrathOfGod.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = -1;

    private static final int DAMAGE = 3;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int UPGRADE_PLUS_BLK = 2;

    public WrathOfGod() {
        this(0);
    }

    private WrathOfGod(int upgrades) {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        timesUpgraded = upgrades;
        baseDamage = DAMAGE + timesUpgraded;
        baseBlock = BLOCK + timesUpgraded;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new WrathOfGodAction(p, m, damage, block, damageTypeForTurn, freeToPlayOnce, energyOnUse));
    }

    @Override
    public void upgrade() {
        this.upgradeDamage(UPGRADE_PLUS_DMG);
        this.upgradeBlock(UPGRADE_PLUS_BLK);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    // Can upgrade any number of times
    @Override
    public boolean canUpgrade() {
        return true;
    }
}