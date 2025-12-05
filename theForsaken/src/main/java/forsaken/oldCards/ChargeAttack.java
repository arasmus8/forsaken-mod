package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.variables.UnplayedCardsVariable;

@SuppressWarnings("unused")
public class ChargeAttack extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(ChargeAttack.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 6;
    private static final int UPGRADED_COST = 5;

    private static final int DAMAGE = 9;

    private static final int MAGIC = 2;

    public ChargeAttack() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        isMultiDamage = true;
    }

    private void recalculateCost() {
        int unplayedCount = UnplayedCardsVariable.unplayedCardCount();
        int tempCost = Math.max(this.cost - unplayedCount, 0);
        if (tempCost < this.costForTurn) {
            this.setCostForTurn(tempCost);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        this.recalculateCost();
    }

    @Override
    public void didDiscard() {
        this.recalculateCost();
    }

    @Override
    public void triggerWhenCardAddedInCombat() {
        this.recalculateCost();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}