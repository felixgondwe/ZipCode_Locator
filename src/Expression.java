import java.util.*;


public class Expression {

	String expr;
	List<String> toks;
	
	public Expression(String expr) {
		this.expr = expr.replaceAll(" ", "");;
		this.toks = this.tokenize();
	}
	
	private boolean isOp(char ch){
		return ch == '+' || ch == '*' || ch == '-' || ch == '/';
	}
	
	private boolean isParen (char ch) {
		return ch == ')' || ch == '(';
	}
	
	public List<String> tokenize() {
		LinkedList<String> toks = new LinkedList<String>();
		int i = 0;
		String tok = "";
		
		while (i < this.expr.length()){
			char ch = expr.charAt(i++);
			if(isOp(ch) || isParen(ch)) {
				// if we have any number saved up, add it to toks
				if(tok.length() > 0){
					toks.add(tok);
					tok = "";
				}
				
				// add the operator or paren
				toks.add(Character.toString(ch));
			} 
			else if(Character.isDigit(ch) || ch== '.') {
				tok = tok + ch;
			} 
			else {
				System.out.println("Syntax error: character" + ch + " not recognized.");
				return null;
			}
		} //while
		return toks;
	}
	
	/**
	 * return true when op1 is higher precedence than op2
	 * @param op1
	 * @param op2
	 * @return
	 */
	private boolean higher_precedence (Character op1, Character op2){
		return((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'));
	}
	
	private Double eval(Character op, Double x, Double y){
		if (op == '+')
			return x+y;
		else if (op == '-')
			return x-y;
		else if (op == '*')
			return x*y;
		else if (op == '/')
			return x/y;
		else {			
			//something really bad happened
			return null;
		}
	}
	
	/**
	 * eval will evaluate the tokenized string
	 */
	public Double eval(){
		Stack<Character> char_stack = new Stack<Character>();
		Stack<Double> val_stack = new Stack<Double>();
		Iterator<String> tok_iter = toks.iterator();
		while(tok_iter.hasNext()){
			String tok = tok_iter.next();
			if(tok.equals("(")){
				char_stack.push('(');
				
			}
			else if(Character.isDigit(tok.charAt(0)) || tok.charAt(0) == '.'){
				Double val = Double.parseDouble(tok);
				val_stack.push(val);
			}
			else if(isOp(tok.charAt(0)) && !char_stack.isEmpty() && isOp(char_stack.peek())) {
				if(!higher_precedence(tok.charAt(0), char_stack.peek())){
					Double val1 = val_stack.pop();
					Double val2 = val_stack.pop();
					Character op = char_stack.pop();//bug
					Double result = eval(op, val2, val1);
					val_stack.push(result);
				}
				char_stack.push(tok.charAt(0));
			}
			else if(isOp(tok.charAt(0))){
				char_stack.push(tok.charAt(0));
			}
			else if(tok.equals(")")) {
				Character top = char_stack.pop();
				while(top != '(') {
					Double val1 = val_stack.pop();
					Double val2 = val_stack.pop();
					Double result = eval(top, val2, val1);
					val_stack.push(result);
					top = char_stack.pop();
				}
			} // else if
			else {
				// something really bad happened
				return null;
			} // else
		} //while
		
		// finish calculations left on the stack
		while(!char_stack.isEmpty()){
			Double val1 = val_stack.pop();
			Double val2 = val_stack.pop();
			Character op = char_stack.pop();
			Double result = eval(op, val2, val1);
			val_stack.push(result);
		}
		return val_stack.pop();
	} // eval
}