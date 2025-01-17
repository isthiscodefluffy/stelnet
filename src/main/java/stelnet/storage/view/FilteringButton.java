package stelnet.storage.view;

import com.fs.starfarer.api.util.Misc;

import stelnet.storage.FilterManager;
import stelnet.storage.StorageBoard;
import stelnet.ui.Size;
import stelnet.ui.ToggleButton;

public abstract class FilteringButton extends ToggleButton {

    public FilteringButton(String name) {
        super(new Size(180, 24), name + ": On", name + ": Off", true, Misc.getHighlightColor(), Misc.getGrayColor(),
                true);
    }

    protected FilterManager getFilterManager() {
        StorageBoard board = StorageBoard.getInstance();
        return board.getFilterManager();
    }
}
