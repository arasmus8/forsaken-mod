package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.potions.BottledPlaguePotion;

public class BottledPlague extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(BottledPlague.class.getSimpleName());

    public BottledPlague() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        upgradeCostTo = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower power = p.getPower(PoisonPower.POWER_ID);
        if (power != null) {
            int poisonAmt = power.amount;
            qAction(new RemoveSpecificPowerAction(p, p, power));
            qAction(new ObtainPotionAction(new BottledPlaguePotion(poisonAmt)));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (canUse) {
            boolean hasSlot = false;
            for (AbstractPotion potion : p.potions) {
                if (potion.ID.equals(PotionSlot.POTION_ID)) {
                    hasSlot = true;
                    break;
                }
            }
            if (!hasSlot) {
                canUse = false;
                this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            }
        }
        return canUse;
    }
}