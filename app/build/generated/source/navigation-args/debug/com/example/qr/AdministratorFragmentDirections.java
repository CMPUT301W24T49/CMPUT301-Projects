package com.example.qr;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class AdministratorFragmentDirections {
  private AdministratorFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAdminMenuFragmentToEventsListFragment() {
    return new ActionOnlyNavDirections(R.id.action_adminMenuFragment_to_eventsListFragment);
  }
}
