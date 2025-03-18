package org.example;

import org.example.model.StandingResponse;
import org.example.service.OfflineDataService;

import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class FootballApplication {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
/*        System.out.println("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }*/

        OfflineDataService service = new OfflineDataService();
        Optional<StandingResponse> response = service.getOfflineData("England", "Premier League", "Arsenal");

        response.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No data found!")
        );
    }
}