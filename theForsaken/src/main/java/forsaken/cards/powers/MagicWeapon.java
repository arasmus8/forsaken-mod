package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.MagicWeaponPower;

@SuppressWarnings("unused")
public class MagicWeapon extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(MagicWeapon.class.getSimpleName());

    public MagicWeapon() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        magicNumber = baseMagicNumber = 2;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MagicWeaponPower(p, magicNumber));
    }
}