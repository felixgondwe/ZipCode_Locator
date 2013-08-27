/*
 * Felix Gondwe
 * Expression Evaluator project
 * main class
 * jvm entry point
 */

import java.util.List;

public class Main {

	public static void main(String[] args) {
		Expression expr = new Expression("(3.14159 * 2 - 7) + (33 /12)");
		List<String> toks = expr.tokenize();
		Double val = expr.eval();
		
		//test Expression functions
		System.out.println(toks);
		System.out.println(val);

	}

}