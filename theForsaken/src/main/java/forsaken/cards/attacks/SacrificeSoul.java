package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.SunlightPower;

import java.util.Optional;

@SuppressWarnings("unused")
public class SacrificeSoul extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(SacrificeSoul.class.getSimpleName());

    public SacrificeSoul() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        isMultiDamage = true;
        damage = baseDamage = 4;
        upgradeDamageBy = 1;
        magicNumber = baseMagicNumber = 4;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealAoeDamage(AbstractGameAction.AttackEffect.SLASH_HEAVY);
        qAction(new RemoveSpecificPowerAction(p, p, SunlightPower.POWER_ID));
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        Optional<AbstractPower> sunlight = Optional.ofNullable(player.getPower(SunlightPower.POWER_ID));
        if (sunlight.isPresent()) {
            int sunlightStacks = sunlight.get().amount;
            return tmp * sunlightStacks;
        }
        return 0;
    }
}