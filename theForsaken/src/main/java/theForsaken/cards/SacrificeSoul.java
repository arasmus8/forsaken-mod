package theForsaken.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.actions.SacrificeSoulAction;
import theForsaken.characters.TheForsaken;

public class SacrificeSoul extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(SacrificeSoul.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 5;

    private static final int DAMAGE_PER_BUFF = 3;
    private static final int UPGRADE_DAMAGE_PER_BUFF = 2;

    public SacrificeSoul() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        isMultiDamage = true;
        exhaust = true;
        baseDamage = DAMAGE;
        baseMagicNumber = DAMAGE_PER_BUFF;
        magicNumber = DAMAGE_PER_BUFF;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SacrificeSoulAction(p, multiDamage, this.magicNumber, this.damageTypeForTurn));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADE_DAMAGE_PER_BUFF);
            initializeDescription();
        }
    }
}