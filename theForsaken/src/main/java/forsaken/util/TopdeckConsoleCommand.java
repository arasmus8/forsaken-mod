package forsaken.util;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class TopdeckConsoleCommand extends ConsoleCommand {
    public TopdeckConsoleCommand() {
        maxExtraTokens = 0;
        minExtraTokens = 0;
        requiresPlayer = true;
    }
    @Override
    protected void execute(String[] strings, int i) {
        Iterator<AbstractCard> it = AbstractDungeon.player.hand.group.iterator();
        while (it.hasNext()) {
            AbstractCard card = it.next();
            it.remove();
            AbstractDungeon.player.hand.moveToDeck(card, false);
        }
    }
}
