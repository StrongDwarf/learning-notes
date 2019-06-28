import java.util.*;

import com.sun.glass.ui.Size;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Test {
    public static void main(String[] args) {
        ActionListener listener = event -> {
            System.out.println(this.toString());
        };
        listener();
    }

    enum Size {
        SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

        private String abbreviation;

        private Size(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return abbreviation;
        }
    }
}