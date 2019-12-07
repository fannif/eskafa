package recommendations.domain;

public enum Color {
    RED("\u001B[91m"),
    GREEN("\u001b[32;1m"),
    CYAN("\u001b[36;1m"),
    BLACK("\u001B[30;1m"),
    WHITE("\u001B[97m"),
    ORIGINAL("\u001B[0m"),
    BOLD("\u001b[37;1m"),
    GREENBG("\u001B[102m"),
    RESET("\u001B[0m"),
    BLUEBG("\u001B[104m");

    private String code;

    private Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
