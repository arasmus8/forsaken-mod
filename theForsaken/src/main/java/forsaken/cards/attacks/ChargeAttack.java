package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

public class ChargeAttack extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(ChargeAttack.class.getSimpleName());

    public ChargeAttack() {
        super(ID, 4, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 3;
        upgradeDamageBy = 1;
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 1;
    }

    private void recalculateCost() {
        if (AbstractDungeon.player == null ) return;
        AbstractPlayer p = AbstractDungeon.player;
        int unplayableCount = unplayableCards(p.hand).size();
        int tempCost = this.cost - unplayableCount;
        if (tempCost < this.costForTurn) {
            this.setCostForTurn(tempCost);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        recalculateCost();
    }

    @Override
    public void didDiscard() {
        super.didDiscard();
        recalculateCost();
    }

    @Override
    public void triggerWhenCardAddedInCombat(AbstractCard c) {
        super.triggerWhenCardAddedInCombat(c);
        recalculateCost();
    }
}
