import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The main class for the Chopped programming language interpreter.
 * This class handles user input and initiates the lexing and parsing process.
 */
public class chopped {

    /**
     * The main entry point of the program.
     * Continuously reads user input from the console, lexes it into tokens, and parses the tokens.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // get text from user and send to parser
        System.out.println("Welcome to the chopped parser!");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Chopped > ");
            String programText = scanner.nextLine();
            try {
                Lexer.lexar(programText);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        // scanner.close(); // not reached
    }

    /**
     * The Lexer class is responsible for converting input text into a list of tokens.
     * It processes the input character by character to create tokens, not relying on spaces.
     */
    public static class Lexer {
        /**
         * Lexes the input text into tokens and passes them to the Parser.
         * Processes the input character by character, not relying on spaces.
         * @param text The input string to be lexed.
         */
        private static void lexar(String text) {
            List<Token> tokenArray = new ArrayList<>();
            int i = 0;
            while (i < text.length()) {
                char c = text.charAt(i);
                if (Character.isDigit(c)) {
                    // Parse number
                    StringBuilder num = new StringBuilder();
                    while (i < text.length() && Character.isDigit(text.charAt(i))) {
                        num.append(text.charAt(i));
                        i++;
                    }
                    tokenArray.add(new Token(num.toString()));
                } else if (Character.isLetter(c)) {
                    // Parse keyword or identifier
                    StringBuilder word = new StringBuilder();
                    while (i < text.length() && Character.isLetter(text.charAt(i))) {
                        word.append(text.charAt(i));
                        i++;
                    }
                    tokenArray.add(new Token(word.toString()));
                } else if (c == '"') {
                    // Parse string
                    StringBuilder str = new StringBuilder();
                    i++; // skip opening "
                    while (i < text.length() && text.charAt(i) != '"') {
                        str.append(text.charAt(i));
                        i++;
                    }
                    if (i < text.length()) i++; // skip closing "
                    tokenArray.add(new Token('"' + str.toString() + '"'));
                } else if (c == '=' && i + 1 < text.length() && text.charAt(i + 1) == '=') {
                    tokenArray.add(new Token("=="));
                    i += 2;
                } else if (c == '!' && i + 1 < text.length() && text.charAt(i + 1) == '=') {
                    tokenArray.add(new Token("!="));
                    i += 2;
                } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>' || c == '(' || c == ')' || c == '!' || c == '?' || c == '.') {
                    tokenArray.add(new Token(String.valueOf(c)));
                    i++;
                } else {
                    // Skip whitespace or invalid characters
                    i++;
                }
            }
            Parser.parse(tokenArray);
        }

        // This class represents a token, which is a piece of text that has a specific
        // meaning in the language. For example, a token could be a keyword, an
        // identifier, or a literal value.

    }

/**
 * Represents a token in the Chopped language, which can be a keyword, operator, number, identifier, etc.
 */
public static class Token {

            private String TokenValue;
            private String TokenType;

            /**
             * Constructs a Token from the given text, determining its type.
             * @param text The string representation of the token.
             */
            public Token(String text) {
                if (isNumeric(text)) {
                    this.TokenType = "NUMBER";
                    this.TokenValue = text;
                    return;
                }
                if (text.startsWith("\"") && text.endsWith("\"")) {
                    this.TokenType = "STRING";
                    this.TokenValue = text.substring(1, text.length() - 1);
                    return;
                }
                switch (text) {
                    case "if":
                        this.TokenType = "KEYWORD:IF";
                        break;
                    case "then":
                        this.TokenType = "KEYWORD:THEN";
                        break;
                    case "do":
                        this.TokenType = "KEYWORD:DO";
                        break;
                    case "say":
                        this.TokenType = "KEYWORD:SAY";
                        break;
                    case "otherwise":
                        this.TokenType = "KEYWORD:OTHERWISE";
                        break;
                    case "or": // first part in an ELSE statement (or if)
                        this.TokenType = "KEYWORD:OR";
                        break;
                    case "set":
                        this.TokenType = "KEYWORD:SET";
                        break;
                    case "to":
                        this.TokenType = "KEYWORD:TO";
                        break;
                    case "+":
                        this.TokenType = "OPERATOR:PLUS";
                        break;
                    case "-":
                        this.TokenType = "OPERATOR:MINUS";
                        break;
                    case "*":
                        this.TokenType = "OPERATOR:MULTIPLY";
                        break;
                    case "/":
                        this.TokenType = "OPERATOR:DIVIDE";
                        break;
                    case "==":
                        this.TokenType = "OPERATOR:EQUAL";
                        break;
                    case "!=":
                        this.TokenType = "OPERATOR:NOT_EQUAL";
                        break;
                    case "<":
                        this.TokenType = "OPERATOR:LESS";
                        break;
                    case ">":
                        this.TokenType = "OPERATOR:GREATER";
                        break;
                    case "(":
                        this.TokenType = "LPAREN";
                        break;
                    case ")":
                        this.TokenType = "RPAREN";
                        break;
                    case "!":
                        this.TokenType = "PUNCTUATION:EXCLAMATION";
                        break;
                    case "?":
                        this.TokenType = "PUNCTUATION:QUESTION";
                        break;
                    case ".":
                        this.TokenType = "PUNCTUATION:PERIOD";
                        break;

                    default:
                        this.TokenType = "IDENTIFIER";
                        this.TokenValue = text;
                        break;
                }
            }

            /**
             * Helper method to check if a string represents a numeric value.
             * @param str The string to check.
             * @return true if the string is numeric, false otherwise.
             */
            private static boolean isNumeric(String str) {
                if (str == null || str.isEmpty())
                    return false;
                for (char c : str.toCharArray()) {
                    if (!Character.isDigit(c))
                        return false;
                }
                return true;
            }

            /**
             * Returns a string representation of the token.
             * @return A formatted string showing the token type and value.
             */
            public String toString() {
                return "(" + this.TokenType + ": " + this.TokenValue + ")";
            }
        }
    /**
     * The Parser class is responsible for parsing the list of tokens into executable code.
     * It supports arithmetic expressions and if statements with conditions.
     */
    private static class Parser {
        private static List<Token> tokens;
        private static int pos;
        private static Map<String, Double> variables = new HashMap<>();

        /**
         * Parses the list of tokens, either as a say statement, if statement, or an expression.
         * @param tokenArray The list of tokens to parse.
         */
        private static void parse(List<Token> tokenArray) {
            tokens = tokenArray;
            pos = 0;
            try {
                if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:SAY")) {
                    parseSay(true);
                } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:IF")) {
                    parseIf();
                } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:SET")) {
                    parseSet();
                } else {
                    parseExpr(true);
                }
            } catch (Exception e) {
                System.out.println("Parse error: " + e.getMessage());
            }
        }

        /**
         * Parses a say statement: say "message".
         */
        private static void parseSay() {
            parseSay(true);
        }

        /**
         * Parses a say statement: say message.
         * @param execute Whether to print the message.
         */
        private static void parseSay(boolean execute) {
            pos++; // consume say
            StringBuilder message = new StringBuilder();
            while (pos < tokens.size()) {
                Token t = tokens.get(pos);
                if (t.TokenType.startsWith("KEYWORD")) break;
                if (t.TokenType.startsWith("PUNCTUATION")) {
                    message.append(t.TokenValue);
                } else if (t.TokenType.equals("IDENTIFIER")) {
                    if (variables.containsKey(t.TokenValue)) {
                        message.append(variables.get(t.TokenValue));
                    } else {
                        message.append(t.TokenValue);
                    }
                } else {
                    message.append(t.TokenValue);
                }
                pos++;
                if (pos < tokens.size() && !tokens.get(pos).TokenType.startsWith("KEYWORD")) message.append(" ");
            }
            String msg = message.toString().trim();
            if (execute) {
                if (msg.length() >= 1 && (msg.charAt(msg.length() - 1) == '.' || msg.charAt(msg.length() - 1) == '!' || msg.charAt(msg.length() - 1) == '?')) {
                    if (msg.length() >= 2 && msg.charAt(msg.length() - 1) == msg.charAt(msg.length() - 2)) {
                        System.out.println(msg.substring(0, msg.length() - 1));
                    } else {
                        System.out.println(msg);
                    }
                } else {
                    System.out.println(msg);
                }
            }
        }

        /**
         * Parses an if statement: if condition [then] [do] statement [otherwise [do] statement].
         */
        private static void parseIf() {
            pos++; // consume if
            boolean condition = parseCondition();
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:THEN")) {
                pos++; // consume then
            }
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:DO")) pos++; // optional do
            parseStatement(condition);
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:OTHERWISE")) {
                pos++; // consume otherwise
                if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:DO")) pos++; // optional do
                parseStatement(!condition);
            }
        }

        /**
         * Parses a set statement: set var to expression.
         */
        private static void parseSet() {
            pos++; // consume set
            if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("IDENTIFIER")) throw new RuntimeException("Expected variable name after set");
            String varName = tokens.get(pos).TokenValue;
            pos++;
            if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:TO")) throw new RuntimeException("Expected 'to' after variable name");
            pos++; // consume to
            double value = parseExpr(false);
            variables.put(varName, value);
        }

        /**
         * Parses a statement: either say, if, set, or expression.
         * @param execute Whether to execute the statement (print output).
         */
        private static void parseStatement(boolean execute) {
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:SAY")) {
                parseSay(execute);
            } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:IF")) {
                parseIf();
            } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:SET")) {
                parseSet();
            } else {
                double result = parseExpr(execute);
            }
        }

        /**
         * Parses a condition for comparison in if statements.
         * Supports optional parentheses around the condition.
         * @return true if the condition is met, false otherwise.
         */
        private static boolean parseCondition() {
            boolean hasParen = false;
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("LPAREN")) {
                hasParen = true;
                pos++;
            }
            double left = parseExpr(false);
            if (pos >= tokens.size()) throw new RuntimeException("Expected comparison operator");
            String op = tokens.get(pos).TokenType;
            pos++;
            double right = parseExpr(false);
            if (hasParen) {
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("RPAREN")) throw new RuntimeException("Expected )");
                pos++;
            }
            switch (op) {
                case "OPERATOR:EQUAL": return left == right;
                case "OPERATOR:NOT_EQUAL": return left != right;
                case "OPERATOR:LESS": return left < right;
                case "OPERATOR:GREATER": return left > right;
                default: throw new RuntimeException("Invalid comparison operator: " + op);
            }
        }

        /**
         * Parses an expression, handling addition and subtraction.
         * @return The evaluated result of the expression.
         */
        private static double parseExpr() {
            return parseExpr(true);
        }

        /**
         * Parses an expression, handling addition and subtraction.
         * @param execute Whether to print the result.
         * @return The evaluated result of the expression.
         */
        private static double parseExpr(boolean execute) {
            double left = parseTerm();
            while (pos < tokens.size() && (tokens.get(pos).TokenType.equals("OPERATOR:PLUS") || tokens.get(pos).TokenType.equals("OPERATOR:MINUS"))) {
                String op = tokens.get(pos).TokenType;
                pos++;
                double right = parseTerm();
                if (op.equals("OPERATOR:PLUS")) left += right;
                else left -= right;
            }
            if (execute) System.out.println("Result: " + left);
            return left;
        }

        /**
         * Parses a term, handling multiplication and division.
         * @return The evaluated result of the term.
         */
        private static double parseTerm() {
            double left = parseFactor();
            while (pos < tokens.size() && (tokens.get(pos).TokenType.equals("OPERATOR:MULTIPLY") || tokens.get(pos).TokenType.equals("OPERATOR:DIVIDE"))) {
                String op = tokens.get(pos).TokenType;
                pos++;
                double right = parseFactor();
                if (op.equals("OPERATOR:MULTIPLY")) left *= right;
                else left /= right;
            }
            return left;
        }

        /**
         * Parses a factor, which can be a number, identifier, or a parenthesized expression.
         * @return The evaluated result of the factor.
         */
        private static double parseFactor() {
            if (pos >= tokens.size()) throw new RuntimeException("Unexpected end of input");
            Token t = tokens.get(pos);
            pos++;
            if (t.TokenType.equals("NUMBER")) {
                return Double.parseDouble(t.TokenValue);
            } else if (t.TokenType.equals("IDENTIFIER")) {
                if (!variables.containsKey(t.TokenValue)) throw new RuntimeException("Undefined variable: " + t.TokenValue);
                return variables.get(t.TokenValue);
            } else if (t.TokenType.equals("LPAREN")) {
                double val = parseExpr();
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("RPAREN")) throw new RuntimeException("Missing )");
                pos++;
                return val;
            } else {
                throw new RuntimeException("Unexpected token: " + t);
            }
        }
    }
}
