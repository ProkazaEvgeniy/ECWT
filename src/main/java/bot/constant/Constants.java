package bot.constant;

public class Constants {

	public static final String[] EMPTY_ARRAY = {};
	
	public static enum UIImageType {
		
		PRODUCT(350, 350);
		
		private final int largeWidth;
		private final int largeHeight;
		
		private UIImageType(int largeWidth, int largeHeight) {
			this.largeWidth = largeWidth;
			this.largeHeight = largeHeight;
		}
		public String getFolderName() {
			return name().toLowerCase();
		}
		public int getLargeWidth() {
			return largeWidth;
		}
		public int getLargeHeight() {
			return largeHeight;
		}
	}
	
	public static class UI {
		public static final int MAX_PRODUCTS_PER_PAGE = 4;
		public static final int ORDERS_PER_PAGE = 5;
	}
}
