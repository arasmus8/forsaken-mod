package theForsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.variables.UnplayedCardsVariable;

import static theForsaken.TheForsakenMod.makeCardPath;

public class ChargeAttack extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(ChargeAttack.class.getSimpleName());
    public static final String IMG = makeCardPath("ChargeAttack.png");
    // Must have an image with the same NAME as the card in your image folder!.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 6;
    private static final int UPGRADED_COST = 5;

    private static final int DAMAGE = 9;

    private static final int MAGIC = 2;
    // /STAT DECLARATION/

    public ChargeAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        isMultiDamage = true;
    }

    private void recalculateCost() {
        int tempCost = Math.max(this.cost - UnplayedCardsVariable.unplayedCardCount(), 0);
        if (!this.freeToPlayOnce) {
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

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}