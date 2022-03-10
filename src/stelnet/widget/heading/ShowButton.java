package stelnet.widget.heading;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.CutStyle;
import com.fs.starfarer.api.ui.IntelUIAPI;
import java.awt.event.KeyEvent;
import stelnet.BaseIntel;
import stelnet.CommonL10n;
import stelnet.util.AwtUtils;
import stelnet.util.L10n;
import stelnet.util.SectorUtils;
import uilib.Button;
import uilib.EventHandler;
import uilib.UiConstants;
import uilib.property.Size;

public class ShowButton extends Button {

    public ShowButton(final BaseIntel intel, final MarketAPI market) {
        super(new Size(UiConstants.AUTO_WIDTH, UiConstants.DEFAULT_ROW_HEIGHT), L10n.get(CommonL10n.SHOW), true);
        setPadding(0);
        setCutStyle(CutStyle.C2_MENU);
        setHandler(
            new EventHandler() {
                @Override
                public void onConfirm(IntelUIAPI ui) {
                    intel.setSectorEntityToken(market.getPrimaryEntity());
                    AwtUtils.send(KeyEvent.VK_S);
                    SectorUtils.addTransientScript(intel);
                }
            }
        );
    }
}