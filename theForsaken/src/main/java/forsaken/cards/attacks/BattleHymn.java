package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class BattleHymn extends AbstractForsakenCard {
    private static final Logger logger = LogManager.getLogger(BattleHymn.class);
    public static final String ID = TheForsakenMod.makeID(BattleHymn.class.getSimpleName());

    private int cardsPlayed = 0;

    public BattleHymn() {
        super(ID, -2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.NONE);
        damage = baseDamage = 18;
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 1;
        unplayedEffect = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qEffect(new ViolentAttackEffect(m.drawX, m.drawY, TheForsakenMod.FORSAKEN_GOLD));
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c == this || !AbstractDungeon.player.hand.contains(this)) {
            return;
        }
        logger.info("BattleHymn: Other card played: {}", c);
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