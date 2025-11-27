package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.SunlightPower;

public class ScorchingSunlight extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(ScorchingSunlight.class.getSimpleName());

    public ScorchingSunlight() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        isMultiDamage = true;
        damage = baseDamage = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealAoeDamage(AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        float mult = 1.0f;
        if (player.hasPower(SunlightPower.POWER_ID)) {
            if (upgraded) {
                mult = 2f;
            } else {
                mult = 1.5f;
            }
        }
        return tmp * mult;
    }
}
