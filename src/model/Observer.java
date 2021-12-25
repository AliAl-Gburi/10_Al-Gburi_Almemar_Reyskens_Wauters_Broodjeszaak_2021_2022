package model;

import model.database.BelegDatabase;
import model.database.BroodjesDatabase;

public interface Observer {
    void update(BroodjesDatabase broodjeDatabase, BelegDatabase belegDatabase);
}
