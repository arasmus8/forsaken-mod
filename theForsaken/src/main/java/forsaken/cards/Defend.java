package forsaken.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;

@SuppressWarnings("unused")
public class Defend extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Defend.class.getSimpleName());

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF, TheForsaken.Enums.COLOR_GOLD, "TheForsaken_Defend", CardTags.STARTER_DEFEND);
        baseBlock = block = 5;
        upgradeBlockBy = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
    }
}
