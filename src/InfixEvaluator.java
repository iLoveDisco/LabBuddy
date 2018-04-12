
import java.util.Scanner;
import java.util.Stack;

public class InfixEvaluator {
	public int evaluate(String expression) throws ArithmeticException {
		//Converts the expression to postfix then sends it to the postfix evaluator.
		String input = convertToPostfix(expression);
		PostfixEvaluator eval = new PostfixEvaluator();
		return eval.evaluate(input);
	}
	public String convertToPostfix(String expression) {
		//Sets everything up for the conversion.
		Scanner scnr = new Scanner(expression);
		Stack<String> operands = new Stack<>();
		Stack<String> operators = new Stack<>();
		Stack<Integer> opsInParenStack = new Stack<>();
		int numLeftParem = 0;
		int numRightParem = 0;
		int intCount = 0;
		int opCount = 0;
		StringBuilder sb = new StringBuilder();
		
		//Scans through the expression to convert
		while(scnr.hasNext()) {
			
			//If the next item is an integer it adds it to the operands stack
			if(scnr.hasNextInt()) {
				operands.push(scnr.next());
				intCount++;
				continue;
			}
			
			//When the item is an opperator or a parenthesis it will search this code
			String op = scnr.next();
			
			//Count of the operators, in case not enough operators
			if(op.equals("*") || op.equals("/") || op.equals("+") 
					|| op.equals("-") || op.equals("^"))
				opCount++;
			
			if(!isOperator(op))
				throw new ArithmeticException();
			
			//Sets up a stack to keep track of parentheses
			if(op.equals("(")) {
				numLeftParem++;
				opsInParenStack.push(0);
				continue;
			}
			
			//Errors if there is an extra right parentheses
			if(op.equals(")") && opsInParenStack.isEmpty())
				throw new ArithmeticException();
				
			//Finishes off everything in a set of parentheses
			if(op.equals(")")) {
				numRightParem++;
				if(!operators.isEmpty() && !operands.isEmpty()) {
					for(int i = 0; i < opsInParenStack.peek(); i++) {
						String rightNum = operands.pop() + " ";
						String leftNum = operands.pop() + " ";
						sb.append(leftNum);
						sb.append(rightNum);
						sb.append(operators.pop());
						operands.push(sb.toString());
						sb.setLength(0);
					}
				}
				opsInParenStack.pop();
				continue;
			}
			
			//Runs for the first operator
			if(operators.isEmpty()) {
				operators.push(op);
				if(!opsInParenStack.isEmpty() && opsInParenStack.peek() == 0)
					opsInParenStack.push(opsInParenStack.pop() + 1);
				continue;
			}
			
			//Compares operators to see what needs to be added
			if(getRank(op) <= getRank(operators.peek())) {
				if(operands.isEmpty())
					throw new ArithmeticException();
				
				//Keeps track of how much was done in parentheses
				if(!opsInParenStack.isEmpty() && opsInParenStack.peek() == 0) {
					opsInParenStack.push(opsInParenStack.pop() + 1);
					operators.push(op);
					continue;
				}
				
				//Keeps doing operations until the scanned operator has a greater rank than the current
				while(!operators.isEmpty() && getRank(op) <= getRank(operators.peek())) {
					String rightNum = operands.pop() + " ";
					String leftNum = operands.pop() + " ";
					sb.append(leftNum);
					sb.append(rightNum);
					sb.append(operators.pop());
					operands.push(sb.toString());
					sb.setLength(0);
				}
				operators.push(op);
				continue;
			} else {
				if(!opsInParenStack.isEmpty())
					opsInParenStack.push(opsInParenStack.pop() + 1);
				operators.push(op);
			}
		}
		//Checks for problems with the number of items passed in
		if(numLeftParem != numRightParem || intCount == 0 || intCount - opCount != 1)
			throw new ArithmeticException();
		
		//Empties the stacks by performing operations for all remaining operators
		while(!operators.isEmpty()) {
			String rightNum = operands.pop() + " ";
			String leftNum = operands.pop() + " ";
			sb.append(leftNum);
			sb.append(rightNum);
			sb.append(operators.pop());
			operands.push(sb.toString());
			sb.setLength(0);
		}
		
		return operands.pop();
	}
	public int getRank(String token) {
		//Ranking system for the different operators
		if(token.equals("+") || token.equals("-"))
			return 1;
		if(token.equals("*") || token.equals("/"))
			return 2;
		if(token.equals("^"))
			return 3;
		return 1;
	}

	private boolean isOperator(String token) {
		return "*/+^-()".contains(token) && token.length() == 1;
	}

}
