import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The main class for the Chopped programming language interpreter.
 * This class handles user input and initiates the lexing and parsing process.
 */
public class chopped {

    /**
     * The main entry point of the program.
     * If a file is provided, executes it; otherwise, enters REPL.
     * @param args Command-line arguments: optional file name.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                String fileName = args[0];
                String content = new String(Files.readAllBytes(Paths.get(fileName)));
                Lexer.lexar(content);
            } catch (Exception e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("Welcome to the chopped parser!");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Chopped > ");
                String programText = scanner.nextLine();
                try {
                    if (programText.startsWith("chopped ")) {
                        String fileName = programText.substring(8).trim();
                        String content = new String(Files.readAllBytes(Paths.get(fileName)));
                        Lexer.lexar(content);
                    } else {
                        Lexer.lexar(programText);
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            // scanner.close(); // not reached
        }
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
                    String w = word.toString();
                    if (w.toUpperCase().equals("YAP") && i < text.length() /*&& text.charAt(i) == ':'*/) {
                        // skip comment line
                        while (i < text.length() && text.charAt(i) != '\n') {
                            i++;
                        }
                        if (i < text.length()) {
                            tokenArray.add(new Token("\n"));
                            i++;
                        }
                    } else {
                        tokenArray.add(new Token(w));
                    }
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
                } else if (c == '\n') {
                  //  tokenArray.add(new Token("\n"));
                    i++;
                } else {
                    // Skip other whitespace or invalid characters
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
                switch (text.toLowerCase()) {
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
                    case "times":
                        this.TokenType = "KEYWORD:TIMES";
                        break;
                    case "chopped":
                        this.TokenType = "KEYWORD:CHOPPED";
                        break;
                    case "repeat":
                        this.TokenType = "KEYWORD:REPEAT";
                        break;
                    case "ask":
                        this.TokenType = "KEYWORD:ASK";
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
        private static Map<String, Object> variables = new HashMap<>();
        private static Scanner inputScanner = new Scanner(System.in);

        /**
         * Parses the list of tokens, handling multiple statements.
         * @param tokenArray The list of tokens to parse.
         */
        private static void parse(List<Token> tokenArray) {
            tokens = tokenArray;
            pos = 0;
            while (pos < tokens.size()) {
                if (tokens.get(pos).TokenType.equals("\n")) {
                    pos++;
                    continue;
                }
                try {
                    if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:SAY")) {
                        parseSay(true);
                    } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:IF")) {
                        parseIf();
                    } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:SET")) {
                        parseSet();
                    } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:CHOPPED")) {
                        parseChopped();
                    } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:REPEAT")) {
                        parseRepeat();
                    } else if (pos < tokens.size()) {
                        parseExpr(true);
                    }
                } catch (Exception e) {
                    System.out.println("Parse error: " + e.getMessage());
                    break; // stop on error
                }
            }
        }

        /**
         * Parses a say statement: say "message".
         */
        private static void parseSay() {
            parseSay(true);
        }

        /**
         * Parses a say statement: say message [if condition [otherwise [say] message]] [count times].
         * @param execute Whether to print the message.
         */
        private static void parseSay(boolean execute) {
            pos++; // consume say
            Object result = parseExpr(false);
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:IF")) {
                pos++; // consume if
                boolean condition = parseCondition();
                if (condition) {
                    Object countObj = null;
                    if (pos < tokens.size() && (tokens.get(pos).TokenType.equals("NUMBER") || tokens.get(pos).TokenType.equals("IDENTIFIER") || tokens.get(pos).TokenType.equals("LPAREN"))) {
                        countObj = parseExpr(false);
                        if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:TIMES")) throw new RuntimeException("Expected 'times' after count");
                        pos++; // consume times
                    }
                    int loopCount = countObj == null ? 1 : ((Double) countObj).intValue();
                    if (execute) {
                        for (int i = 0; i < loopCount; i++) {
                            printMessage(result);
                        }
                    }
                } else {
                    if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:OTHERWISE")) {
                        pos++; // consume otherwise
                        if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:SAY")) {
                            parseSay(execute);
                        } else {
                            parseExpr(execute);
                        }
                    }
                }
            } else {
                if (execute) {
                    printMessage(result);
                }
            }
        }

        /**
         * Prints the message with punctuation handling.
         * @param result The object to print.
         */
        private static void printMessage(Object result) {
            String msg = result.toString();
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
         * Parses a set statement: set var to value (string, identifier, expression, or ask string).
         */
        private static void parseSet() {
            pos++; // consume set
            if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("IDENTIFIER")) throw new RuntimeException("Expected variable name after set");
            String varName = tokens.get(pos).TokenValue;
            pos++;
            if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:TO")) throw new RuntimeException("Expected 'to' after variable name");
            pos++; // consume to
            if (pos < tokens.size()) {
                Token next = tokens.get(pos);
                if (next.TokenType.equals("KEYWORD:ASK")) {
                    pos++; // consume ask
                    if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("STRING")) throw new RuntimeException("Expected string after ask");
                    String prompt = tokens.get(pos).TokenValue;
                    pos++;
                    System.out.print(prompt + " ");
                    String input = inputScanner.nextLine();
                    variables.put(varName, input);
                } else if (next.TokenType.equals("STRING")) {
                    variables.put(varName, next.TokenValue);
                    pos++;
                } else if (next.TokenType.equals("IDENTIFIER")) {
                    if (!variables.containsKey(next.TokenValue)) {
                        throw new RuntimeException("Undefined variable: " + next.TokenValue);
                    }
                    variables.put(varName, variables.get(next.TokenValue));
                    pos++;
                } else {
                    Object value = parseExpr(false);
                    variables.put(varName, value);
                }
            }
        }



        /**
         * Parses a repeat loop: repeat [code] [count] times or repeat [count] times [code].
         */
        private static void parseRepeat() {
            pos++; // consume repeat
            Object countObj;
            int startPos;
            int endPos;
            if (pos < tokens.size() && (tokens.get(pos).TokenType.equals("NUMBER") || tokens.get(pos).TokenType.equals("IDENTIFIER"))) {
                // Form 2: repeat [count] times [code]
                countObj = parseExpr(false);
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:TIMES")) throw new RuntimeException("Expected 'times' after count");
                pos++; // consume times
                startPos = pos;
                parseStatement(false); // parse the code without executing
                endPos = pos;
            } else {
                // Form 1: repeat [code] [count] times
                startPos = pos;
                parseStatement(false); // parse the code without executing
                countObj = parseExpr(false);
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:TIMES")) throw new RuntimeException("Expected 'times' after count");
                pos++; // consume times
                endPos = pos;
            }
            if (!(countObj instanceof Double)) throw new RuntimeException("Loop count must be numeric");
            int count = ((Double) countObj).intValue();
            for (int i = 0; i < count; i++) {
                pos = startPos;
                parseStatement(true);
            }
            pos = endPos;
        }

        /**
         * Parses a chopped statement: chopped "filename" or chopped filename.
         */
        private static void parseChopped() {
            pos++; // consume chopped
            String fileName;
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("STRING")) {
                fileName = tokens.get(pos).TokenValue;
                pos++;
            } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("IDENTIFIER")) {
                StringBuilder sb = new StringBuilder(tokens.get(pos).TokenValue);
                pos++;
                if (pos < tokens.size() && tokens.get(pos).TokenType.equals("PUNCTUATION:PERIOD")) {
                    sb.append(".");
                    pos++;
                    if (pos < tokens.size() && tokens.get(pos).TokenType.equals("IDENTIFIER")) {
                        sb.append(tokens.get(pos).TokenValue);
                        pos++;
                    }
                }
                fileName = sb.toString();
            } else {
                throw new RuntimeException("Expected file name after chopped");
            }
            try {
                String content = new String(Files.readAllBytes(Paths.get(fileName)));
                Lexer.lexar(content);
            } catch (Exception e) {
                throw new RuntimeException("Error reading file: " + e.getMessage());
            }
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
                Object result = parseExpr(execute);
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
            Object leftObj = parseExpr(false);
            if (!(leftObj instanceof Double)) throw new RuntimeException("Condition must be numeric");
            double left = (Double) leftObj;
            if (pos >= tokens.size()) throw new RuntimeException("Expected comparison operator");
            String op = tokens.get(pos).TokenType;
            pos++;
            Object rightObj = parseExpr(false);
            if (!(rightObj instanceof Double)) throw new RuntimeException("Condition must be numeric");
            double right = (Double) rightObj;
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
        private static Object parseExpr() {
            return parseExpr(true);
        }

        /**
         * Parses an expression, handling addition and subtraction.
         * @param execute Whether to print the result.
         * @return The evaluated result of the expression.
         */
        private static Object parseExpr(boolean execute) {
            Object left = parseTerm();
            while (pos < tokens.size() && (tokens.get(pos).TokenType.equals("OPERATOR:PLUS") || tokens.get(pos).TokenType.equals("OPERATOR:MINUS"))) {
                String op = tokens.get(pos).TokenType;
                pos++;
                Object right = parseTerm();
                if (op.equals("OPERATOR:PLUS")) {
                    if (left instanceof Double && right instanceof Double) {
                        left = (Double) left + (Double) right;
                    } else if (left instanceof String || right instanceof String) {
                        left = left.toString() + right.toString();
                    } else {
                        throw new RuntimeException("Invalid operands for +");
                    }
                } else if (op.equals("OPERATOR:MINUS")) {
                    if (left instanceof Double && right instanceof Double) {
                        left = (Double) left - (Double) right;
                    } else {
                        throw new RuntimeException("Invalid operands for -");
                    }
                }
            }
            if (execute) {
                if (left instanceof Double) {
                    System.out.println("Result: " + left);
                } else if (left instanceof String) {
                    System.out.println(left);
                } else if (left == null) {
                    // do nothing
                }
            }
            return left;
        }

        /**
         * Parses a term, handling multiplication and division.
         * @return The evaluated result of the term.
         */
        private static Object parseTerm() {
            Object left = parseFactor();
            while (pos < tokens.size() && (tokens.get(pos).TokenType.equals("OPERATOR:MULTIPLY") || tokens.get(pos).TokenType.equals("OPERATOR:DIVIDE"))) {
                String op = tokens.get(pos).TokenType;
                pos++;
                Object right = parseFactor();
                if (!(left instanceof Double) || !(right instanceof Double)) throw new RuntimeException("Operands for * / must be numbers");
                double l = (Double) left;
                double r = (Double) right;
                if (op.equals("OPERATOR:MULTIPLY")) left = l * r;
                else left = l / r;
            }
            return left;
        }

        /**
         * Parses a factor, which can be a number, string, identifier, or a parenthesized expression.
         * @return The evaluated result of the factor.
         */
        private static Object parseFactor() {
            if (pos >= tokens.size()) throw new RuntimeException("Unexpected end of input");
            Token t = tokens.get(pos);
            pos++;
            if (t.TokenType.equals("NUMBER")) {
                return Double.parseDouble(t.TokenValue);
            } else if (t.TokenType.equals("STRING")) {
                return t.TokenValue;
            } else if (t.TokenType.equals("IDENTIFIER")) {
                if (variables.containsKey(t.TokenValue)) {
                    return variables.get(t.TokenValue);
                } else {
                    return t.TokenValue;
                }
            } else if (t.TokenType.equals("LPAREN")) {
                Object val = parseExpr();
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("RPAREN")) throw new RuntimeException("Missing )");
                pos++;
                return val;
            } else {
                throw new RuntimeException("Unexpected token: " + t);
            }
        }
    }
}
