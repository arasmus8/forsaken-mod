package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.cards.AbstractQuickdrawCard;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class WordsOfPestilence extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(WordsOfPestilence.class.getSimpleName());

    public WordsOfPestilence() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        qAction(new FunctionalAction(Settings.ACTION_DUR_MED, f -> {
            if (f) {
                List<AbstractCard> discardedQuickdraws = p.discardPile.group.stream()
                        .filter(AbstractQuickdrawCard::isQuickdraw)
                        .collect(Collectors.toList());
                for (AbstractCard card : discardedQuickdraws) {
                    p.discardPile.moveToDeck(card, true);
                }
            }
            return false;
        }));
        if (upgraded) {
            qAction(new DrawCardAction(1));
        }
    }
}