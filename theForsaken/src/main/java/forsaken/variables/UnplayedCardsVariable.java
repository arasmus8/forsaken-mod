package forsaken.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.TheForsakenMod;

import java.util.UUID;

import static forsaken.TheForsakenMod.makeID;


public class UnplayedCardsVariable extends DynamicVariable
{   // Custom Dynamic Variables are what you do if you need your card text to display a cool, changing number that the base game doesn't provide.
    // If the !D! and !B! (for Damage and Block) etc. are not enough for you, this is how you make your own one. It Changes In Real Time!

    public static int unplayedCardCount() {
        int count = 0;
        if(AbstractDungeon.player != null && AbstractDungeon.player.discardPile != null) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                UUID uuid = c.uuid;
                if (!TheForsakenMod.usedCards.contains(uuid)) {
                    count += 1;
                }
            }
        }
        return count;
    }
    
    // This is what you type in your card string to make the variable show up. Remember to encase it in "!"'s in the json!
    @Override
    public String key()
    {
        return makeID("UNPLAYED_CARDS");
    }

    // Checks whether the current value is different than the base one. 
    // For example, this will check whether your damage is modified (i.e. by strength) and color the variable appropriately (Green/Red).
    @Override
    public boolean isModified(AbstractCard card)
    {
        return unplayedCardCount() > 0;
    }

    // The value the variable should display.
    @Override
    public int value(AbstractCard card)
    {
        return unplayedCardCount();
    }
    
    // The baseValue the variable should display.
    // just like baseBlock or baseDamage, this is what the variable should reset to by default. (the base value before any modifications)
    @Override
    public int baseValue(AbstractCard card)
    {   
        return unplayedCardCount();
    }
    
    // If the card has its damage upgraded, this variable will glow green on the upgrade selection screen as well.
    @Override
    public boolean upgraded(AbstractCard card)
    {               
       return unplayedCardCount() > 0;
    }
}