import java.util.*;

// subclass of moves which counter attacks
class CounterMove extends Move{
    private CounterElement counterelem;

    // constructor method
    public CounterMove(int counter, String type, EnumMap<ChargeType, Integer> charges, String effect) {
        this.charges.putAll(charges);
        counterelem = new CounterElement(counter, type);
        this.effect = effect;
    }

    // getters and setters
    public CounterElement getCounterElement(){
        return counterelem;
    }

    // move execution to add a counter to counter list
    @Override
    public void executeMove(Player player) {
        ArrayList<CounterElement> counters = player.getCounters();
        counters.add(counterelem);
        player.setCounters(counters);
    }
    
}
