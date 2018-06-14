package com.ratestart.integrator.model

enum ActionType {

    APPLY_NOW("Apply Now"),
    BOOK_TRAVEL("Book Now"),
    CALL_NOW("Call Now"),
    CONTACT_US("Contact Us"),
    DONATE_NOW("Donate Now"),
    DOWNLOAD("Download"),
    GET_DIRECTIONS("Get Directions"),
    GET_SHOWTIMES("Get Showtimes"),
    GET_QUOTE("Get Quote"),
    LEARN_MORE("Learn More"),
    LISTEN_NOW("Listen Now"),
    NO_BUTTON("No Button"),
    SEE_MENU("See Menu"),
    MESSAGE_PAGE("Send Message"),
    SHOP_NOW("Shop Now"),
    SIGN_UP("Sign Up"),
    SUBSCRIBE("Subscribe"),
    REQUEST_TIME("Request Time"),
    WATCH_MORE("Watch More")

    String action

    ActionType(String action) {
        this.action = action
    }

    static ActionType getAction(String action) {
        values().find {it.action.equalsIgnoreCase(action) }
    }
}