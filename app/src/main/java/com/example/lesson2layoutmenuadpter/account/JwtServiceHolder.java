package com.example.lesson2layoutmenuadpter.account;

public interface JwtServiceHolder {
    void saveJWTToken(String token);
    String getToken();
    void removeToken();




}
