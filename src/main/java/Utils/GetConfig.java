package Utils;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public class GetConfig {
        public static Ini ini;

        static {
            try {
                ini = new Ini(new File("config.ini"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
