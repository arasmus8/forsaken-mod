package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

public class ShieldBash extends AbstractForsakenCard {
    private static final Logger logger = LogManager.getLogger(ShieldBash.class.getName());
    public static final String ID = TheForsakenMod.makeID(ShieldBash.class.getSimpleName());

    public ShieldBash () {
        super(ID, -2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 3;
        upgradeDamageBy = 1;
        unplayedEffect = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int unplayableCardsInHand = unplayableCards(p.hand).size();
        logger.info("ShieldBash::dealing damage {} times", unplayableCardsInHand);
        IntStream.range(0, unplayableCardsInHand)
                .forEach(this::damageAction);
    }

    private void damageAction(int i) {
        Optional<AbstractMonster> target = target();
        target.ifPresent(m -> {
            dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        });
    }

    private Optional<AbstractMonster> target() {
        if (upgraded) {
            return monsterList().stream()
                    .min(Comparator.comparingInt(m -> m.currentHealth));
        }
        return Optional.ofNullable(AbstractDungeon.getRandomMonster());
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return !card.equals(this);
    }
}
