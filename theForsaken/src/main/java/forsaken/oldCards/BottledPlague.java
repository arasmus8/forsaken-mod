package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PoisonPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.PoisonPower;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;

public class BottledPlague extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(BottledPlague.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public BottledPlague() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, PoisonPower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(new PoisonPotion()));
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