package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.FearPower;

public class CowardsBrand extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(CowardsBrand.class.getSimpleName());

    public CowardsBrand() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 12;
        upgradeDamageBy = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        if (mo != null && mo.hasPower(FearPower.POWER_ID)) {
            return 2f * tmp;
        }
        return tmp;
    }
}