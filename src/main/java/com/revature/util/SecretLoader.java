package com.revature.util;

import org.springframework.security.core.parameters.P;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SecretLoader {
  private static final String PROP_FILE = "secret.properties";

  public static boolean loadPropertyValues() {
    if (System.getProperty("secretKey") != null && System.getProperty("expirationTimeMs") != null) {
      System.out.println("Already Loaded");
      return true;
    }

    Properties props = new Properties();

    try (InputStream in = SecretLoader.class.getClassLoader().getResourceAsStream(PROP_FILE)) {
      if (in != null) {
        props.load(in);
      } else {
        throw new FileNotFoundException("Could not load " + PROP_FILE);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    System.setProperty("secretKey", props.getProperty("secretKey"));
    System.setProperty("expirationTimeMs", props.getProperty("expirationTimeMs"));

    if (System.getProperty("secretKey") != null && System.getProperty("expirationTimeMs") != null) {
      return true;
    }

    return false;
  }
}
