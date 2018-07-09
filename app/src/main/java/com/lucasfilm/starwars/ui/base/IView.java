package com.lucasfilm.starwars.ui.base;

public interface IView {
    void showLoading();
    void dissmisLoading();
    void showMessageToast(String text);
    void showMessageDialog(String text);
    void killActivity();
}