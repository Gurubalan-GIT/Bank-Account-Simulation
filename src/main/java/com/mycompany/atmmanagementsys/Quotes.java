
package com.mycompany.atmmanagementsys;

import java.util.Random;

public class Quotes {
    String quote ;
    public String returnQuotes(){
    Random ran = new Random();
        int num = ran.nextInt(7);
        switch (num) {
            case 0:
               quote  = "To accomplish great things, we\n"
                        + "must not only act, but also dream,\n"
                        + "not only plan, but also believe.\n\n"
                        + "- Anatole France";
                break;
            case 1:
                quote  = "Victory is always possible for the\n"
                        + "person who refuses to\n"
                        + "stop fighting.\n\n"
                        + "- Napoleon Hill";
                break;
            case 2:
                quote  = "Great works are performed\n"
                        + "not by strength,\n"
                        + "but perseverance.\n\n"
                        + "- Dr. Samuel Johnson";
                break;
            case 3:
                quote  = "Society may predict, but only you\n"
                        + "can determine your destiny.\n\n"
                        + "- Clair Oliver";
                break;
            case 4:
                quote  = "Success comes from having dreams\n"
                        + "that are bigger than your fears.\n\n"
                        + "- Terry Litwiller";
                break;
            case 5:
                quote  = "Imagination is more important\n"
                        + "than knowledge.\n\n"
                        + "- Albert Einstein";
                break;

            case 6:
                quote  = "Without a goal, discipline is\n"
                        + "nothing but self-punishment.\n\n"
                        + "- Elanor Roosevelt";
    }
    return quote;
    }
}
