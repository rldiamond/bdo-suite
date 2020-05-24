package common.jfx;

public enum Theme {
    DARK("theme/dark.css");

    private final String css;

    private Theme(String css) {
        this.css = css;
    }

    public String getCss() {
        return css;
    }

}
