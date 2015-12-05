package me.laudoak.oakpark.ctrl.xv;

import java.util.ArrayList;

import me.laudoak.oakpark.entity.core.XVerse;
import me.laudoak.oakpark.fragment.XBaseFragment;

/**
 * Created by LaudOak on 2015-11-21 at 22:12.
 */
public abstract class AbXVSubject extends XBaseFragment
{
    private static final String TAG = "AbXVSubject";

    protected ArrayList<AbXVOberver> obervers = new ArrayList<AbXVOberver>();

    public abstract void notifyAllXVUpdated(XVerse xVerse);

    public void attach(AbXVOberver newObserver)
    {
        obervers.add(newObserver);
    }

    public void detach(AbXVOberver oberver)
    {
        obervers.remove(oberver);
    }

}
