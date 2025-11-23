package forsaken.oldCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import forsaken.TheForsakenMod;
import forsaken.actions.ConfusionAction;
import forsaken.characters.TheForsaken;

public class Confusion extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(Confusion.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public Confusion() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ConfusionAction(m));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (canUse && m != null) {
            if (m.intent == Intent.ATTACK || m.intent == Intent.ATTACK_BUFF || m.intent == Intent.ATTACK_DEBUFF || m.intent == Intent.ATTACK_DEFEND) {
                return true;
            } else {
                this.cantUseMessage = EXTENDED_DESCRIPTION[0];
                return false;
            }
        }
        return canUse;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}