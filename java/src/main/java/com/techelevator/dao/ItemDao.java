package com.techelevator.dao;
import com.techelevator.model.*;

import java.util.List;

public interface ItemDao {

    Item getItemById(int itemId);

    List<Item> getItemsByIdsFromWatchlist(List<Watchlist> watchlistEntries);
}
