package stelnet.commodity.view;

import com.fs.starfarer.api.ui.IntelUIAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import stelnet.commodity.CommodityBoard;
import stelnet.commodity.CommodityIntel;
import stelnet.ui.Button;
import stelnet.ui.EventHandler;
import stelnet.ui.Size;

public class DeleteIntel extends Button {

    public DeleteIntel(float width, final CommodityIntel intel) {
        super(new Size(width, 24), "Delete", true, Misc.getButtonTextColor());
        setHandler(new EventHandler() {

            @Override
            public boolean hasPrompt() {
                return true;
            }

            @Override
            public void onConfirm(IntelUIAPI ui) {
                CommodityBoard.getInstance().deleteIntel(intel);
            }

            @Override
            public void onPrompt(TooltipMakerAPI tooltipMaker) {
                tooltipMaker.addPara("Are you sure you want to delete intel for this entry?", Misc.getTextColor(), 0f);
            }
        });
    }
}