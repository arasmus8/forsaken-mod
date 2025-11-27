package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.cards.AbstractQuickdrawCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.IntStream;

public class Parry extends AbstractQuickdrawCard {
    private static final Logger logger = LogManager.getLogger(Parry.class);
    public static final String ID = TheForsakenMod.makeID(Parry.class.getSimpleName());

    public Parry() {
        super(ID, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        block = baseBlock = 2;
        upgradeBlockBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qAction(new FunctionalAction(firstUpdate -> {
            IntStream.range(0, AbstractForsakenCard.unplayableCards(p.hand).size())
                    .forEach(i -> {
                        logger.info("Parry {} gains block", uuid);
                        gainBlock();
                    });
            return true;
        }));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        use(AbstractDungeon.player, null);
    }
}