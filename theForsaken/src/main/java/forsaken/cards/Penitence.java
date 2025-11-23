package forsaken.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cardmods.BonusDamageMod;

public class Penitence extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Penitence.class.getSimpleName());

    public Penitence() {
        super(ID, 2, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        block = baseBlock = 10;
        magicNumber = baseMagicNumber = 2;

        upgradeBlockBy = 4;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
        AbstractDungeon.player.hand.group.stream()
                .filter(c -> c.type.equals(CardType.ATTACK))
                .forEach(c -> {
                    BonusDamageMod.applyToCard(c, magicNumber);
                });
    }
}
