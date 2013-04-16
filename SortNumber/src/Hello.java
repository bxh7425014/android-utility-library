
public class Hello {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] values ={
                3,1,6,2,9,100,7,4,5,21,56,52
        };
		sort(values);
		for (int i = 0; i < values.length; ++i) {
			System.out.println("Index: " + i + ", Value: " + values[i]);
		}
		;
		System.out.println(Math.round(11.5));
		System.out.println(Math.round(-0.6));
	}

	/**
	 * 由大到小排列数组的数据
	 * @author bianxh
	 * @param values
	 */
	public static void sort(int[] values) {
		int temp;
		for (int i = 0; i < values.length; ++i) {
			for (int j = 0; j < values.length - i - 1; ++j) {
				if (values[j] > values[j + 1]) {
					temp = values[j];
					values[j] = values[j + 1];
					values[j + 1] = temp;
				}
			}
		}
	}
	
	/**
	 * 由小到大排列数组的数据
	 * @author bianxh
	 * @param values
	 */
	public static void sort1(int[] values) {
		int temp;
		for (int i = 0; i < values.length; ++i) {
			for (int j = 0; j < values.length - i - 1; ++j) {
				if (values[j] < values[j + 1]) {
					temp = values[j];
					values[j] = values[j + 1];
					values[j + 1] = temp;
				}
			}
		}
	}
}
