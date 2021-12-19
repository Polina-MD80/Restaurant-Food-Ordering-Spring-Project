package softuni.restaurant.model.view;

public class StatsView {

  private final int authRequests;
  private final int anonRequests;
  private final int orders;

  public StatsView(int authRequests, int anonRequests, int orders) {
    this.authRequests = authRequests;
    this.anonRequests = anonRequests;
    this.orders = orders;
  }

  public int getTotalRequests() {
    return anonRequests + authRequests;
  }

  public int getAuthRequests() {
    return authRequests;
  }


  public int getAnonRequests() {
    return anonRequests;
  }

  public int getOrders() {
    return orders;
  }
}
