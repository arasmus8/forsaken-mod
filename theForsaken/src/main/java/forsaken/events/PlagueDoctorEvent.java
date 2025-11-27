package forsaken.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import forsaken.TheForsakenMod;
import forsaken.oldCards.PlagueCurse;
import forsaken.relics.PlagueMask;

import static forsaken.TheForsakenMod.eventResourcePath;

public class PlagueDoctorEvent extends AbstractImageEvent {


    public static final String ID = TheForsakenMod.makeID("PlagueDoctorEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = eventResourcePath("PlagueDoctorEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public PlagueDoctorEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Take - gain plague mask and plague curse
        imageEventText.setDialogOption(OPTIONS[1]); // Refuse
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        // we'll still continue the switch (screenNum) statement. It'll find screen 1 and do it's actions
                        // (in our case, that's the final screen, but you can chain as many as you want like that)

                        if (!AbstractDungeon.player.hasRelic(PlagueMask.ID)) {
                            AbstractRelic relicToAdd = new PlagueMask();
                            // Get a random starting relic

                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relicToAdd);

                        }

                        AbstractCard plagueCurse = new PlagueCurse();
                        logMetricObtainCard("Plague Doctor", "Obtain Plague Curse", plagueCurse);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(plagueCurse, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));

                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Deinal
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        break; // Onto screen 1 we go.
                }
                break;
            case 1: // Welcome to screenNum = 1;
                if (i == 0) { // If you press the first (and this should be the only) button,
                    openMap(); // You'll open the map and end the event.
                }
                break;
        }
    }

}
