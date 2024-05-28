package Tasks.Leaves;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;

import static Settings.ScriptSettings.getSettings;

public class DoBankLogs extends Leaf {
    @Override
    public boolean isValid() {
        return !getSettings().shouldFletch && !getSettings().shouldFiremake && !getSettings().shouldDrop && Inventory.isFull() && Inventory.contains(t -> t.getName().toLowerCase().contains("logs"));
    }

    @Override
    public int onLoop() {

        BankLocation closestBank = Bank.getClosestBankLocation(false);

        if(closestBank.getArea(3).contains(Players.getLocal())) {
            if(!Bank.isOpen()) {
                Bank.open();
                Sleep.sleepUntil(() -> Bank.isOpen(), 10000);
            }

            Bank.depositAll(item -> !item.getName().toLowerCase().contains("axe"));
            return 1000;
        }
        if(!closestBank.getArea(3).contains(Players.getLocal())) {
            if(Walking.shouldWalk(Calculations.random(1,7))) {
                Walking.walk(closestBank.getArea(3).getNearestTile(Players.getLocal()));
            }
            return Calculations.random(50,500);
        }

        return 0;
    }
}
