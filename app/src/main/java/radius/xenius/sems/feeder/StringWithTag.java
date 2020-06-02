package radius.xenius.sems.feeder;

/**
 * Created by Pratima Singh on 05-02-2018.
 */

public class StringWithTag {
    public String string;
    public String tag;

    public StringWithTag(String string, String tag) {
        this.string = string;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return string;
    }
}
