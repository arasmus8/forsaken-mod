package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

@SuppressWarnings("unused")
public class WardingHymn extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(WardingHymn.class.getSimpleName());

    private int cardsPlayed = 0;

    public WardingHymn() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        block = baseBlock = 20;
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 1;
        unplayedEffect = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c == this || !AbstractDungeon.player.hand.contains(this)) {
            return;
        }
        cardsPlayed += 1;
        if (cardsPlayed >= magicNumber) {
            qAction(new DiscardSpecificCardAction(this));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        cardsPlayed = 0;
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return !card.equals(this);
    }
}