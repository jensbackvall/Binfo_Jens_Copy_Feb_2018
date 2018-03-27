package dk.binfo.services;

import java.util.ArrayList;

public interface PreferenceService {
    ArrayList<Integer> getPreferences(int userId);
    ArrayList<Integer> setPreferences(int userId);
}
