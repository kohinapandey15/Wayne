import java.util.Random;

public class Order {
    private static final Random random = new Random();
    private final int cargoWeight;
    private final String destination;

    public Order() {
        this.cargoWeight = random.nextInt(41) + 10;
        this.destination = random.nextBoolean() ? "Gotham" : "Atlanta";
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public String getDestination() {
        return destination;
    }
}
