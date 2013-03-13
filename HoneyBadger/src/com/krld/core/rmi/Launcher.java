package com.krld.core.rmi;

public class Launcher {
    public static void main(String[] args) {
        if (args[0].equals("client")) {
          Client.main(args);
        } else if (args[0].equals("server")) {
            ServiceImpl.main(null);
        }
    }
}
