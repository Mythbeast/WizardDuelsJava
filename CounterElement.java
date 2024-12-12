public class CounterElement implements Comparable<CounterElement>{
    private int counter = 0;
    // private String type = null;
    
    // constructor method
    public CounterElement(int counter, String type) {
        this.counter = counter;
        // this.type  = type;
    }

    // getters and setters
    public int getCounter() {
        return counter;
    }

    // check whether an attack can be countered
    public boolean counterCheck(AttackElement attackElement) {
        // unblockable attacks can never be countered
        if (attackElement.getType() == "unblockable") {
            return false;
        }
        else {
            if (this.counter >= attackElement.getDamage()){
                return true;
            } 
            else {
                return false;
            }
        }
    }

    // mechanic to sort in descending counter order
    @Override
    public int compareTo(CounterElement other) {
        int x = 0;
        if (other.counter > this.counter) {
            x = 1;
        }
        if (other.counter == this.counter) {
            x = 0;
        }
        if (other.counter < this.counter) { 
            x = -1;
        }
        return x;
    }


}
