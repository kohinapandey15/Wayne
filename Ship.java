class Ship {
    private int cargo;
    private int trips;

    private boolean processingOrder;
    public Ship() {
        this.cargo = 0;
        this.trips = 0;
    }

    public int getCargo() {
        return cargo;
    }

    public int getTrips() {
        return trips;
    }

    public void loadCargo(int weight) {
        cargo += weight;
    }

    public void unloadCargo() {
        cargo = 0;
        trips++;
    }

    public void maintenance() {

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setProcessingOrder(boolean processingOrder) {
        this.processingOrder= processingOrder;
    }

    public boolean isProcessingOrder() {
        return processingOrder;
    }
}

