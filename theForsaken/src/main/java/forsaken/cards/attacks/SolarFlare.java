package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.SunlightPower;

@SuppressWarnings("unused")
public class SolarFlare extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(SolarFlare.class.getSimpleName());

    public SolarFlare() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        damage = baseDamage = 5;
        upgradeDamageBy = 3;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealAoeDamage(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (p.hasPower(SunlightPower.POWER_ID)) {
            int sunlightAmount = p.getPower(SunlightPower.POWER_ID).amount;
            for (int i = 0; i < sunlightAmount; i++) {
                dealAoeDamage(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            }
        }
    }
}