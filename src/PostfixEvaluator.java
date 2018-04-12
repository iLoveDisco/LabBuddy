
import java.util.Scanner;
import java.util.Stack;

/**
 * TODO Put here a description of what this class does.
 *
 * @author tuey. Created Mar 18, 2018.
 */
public class PostfixEvaluator {
	public Stack<Integer> stack;

	public PostfixEvaluator() {
		this.stack = new Stack<>();
	}

	public int evaluate(String expression) throws ArithmeticException {
		String op = "";
		Scanner scanner = new Scanner(expression);
		int opCount = 0;
		int intCount = 0;
		while (scanner.hasNext()) {
			if (scanner.hasNextInt()) {
				stack.push(scanner.nextInt());
				intCount++;
				continue;
			}
			op = scanner.next();
			opCount++;

			if (stack.size() < 2 || op.equals("(") || op.equals(")"))
				throw new ArithmeticException();// Accounts for () errors.
			else if (op.equals("+")) {
				int rightNum = stack.pop();
				int leftNum = stack.pop();
				stack.push(leftNum + rightNum);
			} else if (op.equals("-")) {
				int rightNum = stack.pop();
				int leftNum = stack.pop();
				stack.push(leftNum - rightNum);
			} else if (op.equals("*")) {
				int rightNum = stack.pop();
				int leftNum = stack.pop();
				stack.push(leftNum * rightNum);
			} else if (op.equals("/")) {
				int divisor = stack.pop();
				int dividend = stack.pop();
				stack.push(dividend / divisor);
			} else if (op.equals("^")) {
				int exp = stack.pop();
				int base = stack.pop();
				stack.push((int) Math.pow(base, exp));
			}
		}
		if (intCount - opCount != 1)
			throw new ArithmeticException();
		return stack.peek();
	}

}
