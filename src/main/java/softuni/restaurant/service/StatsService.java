package softuni.restaurant.service;


import softuni.restaurant.model.view.StatsView;

public interface StatsService {
  void onRequest();
  StatsView getStats();
  void addOrder();
}
