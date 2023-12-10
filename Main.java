import java.util.*;
import java.util.concurrent.*;


public class Main {
    private static final int orderCost = 1000;
    private static final int cancelOrder = 250;
    private static final int target = 1000000;
    private final BlockingQueue<Order> orderQueue ; // study
    private final Ship[] ships;
    private final ExecutorService executor;
    private int revenue;
    public Main(int numberOfShips) {
        orderQueue = new LinkedBlockingQueue<>();
        this.ships = new Ship[numberOfShips];
        this.executor = Executors.newFixedThreadPool(numberOfShips);

        for(int i=0;i<numberOfShips;i++){
            ships[i] = new Ship();
        }
    }

    public  void placeOrder(Order order) throws InterruptedException {
        orderQueue.put(order);
    }

    public  void startSimulation() throws InterruptedException {
        while (revenue < target) {
            Order order;
            try {
                order = orderQueue.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }

            if (order != null) {
                Ship ship = getAvailableShip();
                if (ship != null) {
                    executor.submit(() -> processOrder(ship, order));
                } else {

                    System.out.println("No available ships, order canceled.");
                    revenue -= cancelOrder;
                }
            }
        }

        executor.shutdown();

        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    private void processOrder(Ship ship, Order order) {
        ship.setProcessingOrder(true);
        ship.loadCargo(order.getCargoWeight());


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ship.unloadCargo();
        ship.setProcessingOrder(false);


        if (ship.getTrips() % 5 == 0) {
            ship.maintenance();
        }

        // Calculate revenue
        revenue += orderCost;
        System.out.println("Order delivered. Revenue: $" + revenue);
    }

    private Ship getAvailableShip() {
        for (Ship ship : ships) {
            if (ship.getCargo() >= 50 && ship.getCargo() <= 300 && ship.isProcessingOrder() == true) {
                return ship;
            }
        }
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        Main wayneEnterprise = new Main(5);


        for (int i = 0; i < 7; i++) {
            new Thread(() -> {

                    Order order = new Order();

                    try {
                        wayneEnterprise.placeOrder(order);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }).start();
        }


        wayneEnterprise.startSimulation();

    }
}