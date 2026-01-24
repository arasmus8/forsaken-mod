package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.AncientWordsPower;

@SuppressWarnings("unused")
public class AncientWords extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(AncientWords.class.getSimpleName());

    public AncientWords() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = 1;
        if (upgraded) {
            amount = 2;
        }
        applyToSelf(new AncientWordsPower(p, amount));
    }
}