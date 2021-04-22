package stelnet.market.view;

import com.fs.starfarer.api.util.Misc;

import stelnet.ui.Button;
import stelnet.ui.Size;

public class DeleteOneButton extends Button {

    public DeleteOneButton() {
        super(new Size(120, 24), "Delete", true, Misc.getNegativeHighlightColor());
    }
}