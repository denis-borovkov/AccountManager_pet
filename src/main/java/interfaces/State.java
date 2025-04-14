package main.java.interfaces;

public interface State {
    enum userState {
        GUEST,
        ADMIN,
        USER
    }
    void showMenu();

}
