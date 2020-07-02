public enum BeadState {
  in_pool, spent, exhausted, wounded;

  public BeadState becomeDamaged() {
    switch (this) {
      case in_pool:
        return spent;
      case spent:
        return exhausted;
      case exhausted:
        return wounded;
      default:
        throw new IllegalStateException("Cannot make state '" + this + "' worse");
    }
  }
}
