package forsaken.oldCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.PurificationAction;
import forsaken.characters.TheForsaken;

@SuppressWarnings("unused")
public class Purification extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(Purification.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 3;

    private static final int DAMAGE = 30;
    private static final int UPGRADE_PLUS_DMG = 9;

    public Purification() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new PurificationAction(m, damage, damageTypeForTurn));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}