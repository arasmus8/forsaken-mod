package forsaken.oldCards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.OldFearPower;

public class CowardsBrand extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(CowardsBrand.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int DAMAGE = 18;
    private static final int UPGRADE_PLUS_DMG = 6;

    private static final float DAMAGE_ADJUST = 2.0F;

    public CowardsBrand() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int modifiedDamage = m.hasPower(OldFearPower.POWER_ID) ? MathUtils.floor(damage * DAMAGE_ADJUST) : damage;
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, modifiedDamage, damageTypeForTurn), AttackEffect.SMASH)
        );
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