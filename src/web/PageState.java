package web;

import input.Action;
import lombok.Getter;
import lombok.Setter;

public abstract class PageState {
    @Getter @Setter
    private WebPage webPage;

    public PageState(WebPage updatedPage) {
        setWebPage(updatedPage);
    }

    /**
     * executes on page action
     * @param action on page action
     */
    public abstract void onPage(Action action);

    /**
     * executes change page action
     * @param action change page action
     */
    public abstract void changePage(Action action);

    /**
     * makes output
     * @param out output message
     */
    public void message(final String out) {
        getWebPage().consructOutput(out);
    }


}
