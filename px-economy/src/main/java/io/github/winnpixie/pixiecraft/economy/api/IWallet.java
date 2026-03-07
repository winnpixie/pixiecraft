package io.github.winnpixie.pixiecraft.economy.api;

public interface IWallet {
    long getBalance();

    void setBalance(long amount);

    boolean earn(long amount);

    boolean spend(long amount);
}
