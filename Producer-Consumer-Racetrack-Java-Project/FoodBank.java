public class FoodBank {
	private int food;
		
	public FoodBank() {
		food = 0;
	}
	public void giveFood(int moreFood) {
		food += moreFood;
	}
	public void takeFood(int lessFood) {
		food -= lessFood;
	}
	public int getFood() {
		return food;
	}
}
