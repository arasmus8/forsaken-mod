package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;

public class Confusion extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Confusion.class.getSimpleName());

    public Confusion() {
        super(ID, -2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        damage = baseDamage = 0;
        selfRetain = true;
    }

    @Override
    public int triggerOnPlayerDamaged(int damage, DamageInfo info) {
        if ((info.owner instanceof AbstractMonster) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.type != DamageInfo.DamageType.THORNS)) {
            AbstractPlayer p = AbstractDungeon.player;
            int unblockedDamage = damage - p.currentBlock;
            if (unblockedDamage <= 0) {
                return damage;
            }
            if (upgraded) {
                unblockedDamage *= 2;
            }
            final int damageToDeal = unblockedDamage;
            addToTop(new FunctionalAction(firstUpdate -> {
                qEffect(new ShowCardBrieflyEffect(this.makeStatEquivalentCopy()));
                addToTop(new ExhaustSpecificCardAction(this, p.hand));
                addToTop(new DamageAction(info.owner, makeDamageInfo(damageToDeal, DamageInfo.DamageType.THORNS)));
                return true;
            }));
            return 0;
        }
        return damage;
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return !card.equals(this);
    }
}