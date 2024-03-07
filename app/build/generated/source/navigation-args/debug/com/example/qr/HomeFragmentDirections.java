package com.example.qr;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class HomeFragmentDirections {
  private HomeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionHomeFragmentToAdminMenuFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeFragment_to_adminMenuFragment);
  }
}
