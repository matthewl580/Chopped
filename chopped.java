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
            System.out.println("Welcome to the chopped parser! \n");
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
            this.TokenValue = text;
            if (isNumeric(text)) {
                this.TokenType = "NUMBER";
                return;
            }
            if (text.startsWith("\"") && text.endsWith("\"")) {
                this.TokenType = "STRING";
                this.TokenValue = text.substring(1, text.length() - 1);
                return;
            }
            if (text.equals("\n")) {
                this.TokenType = "NEWLINE";
                return;
            }
            switch (text.toLowerCase()) {
                case "if":
                    this.TokenType = "KEYWORD:IF";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "then":
                    this.TokenType = "KEYWORD:THEN";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "do":
                    this.TokenType = "KEYWORD:DO";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "say":
                    this.TokenType = "KEYWORD:SAY";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "otherwise":
                    this.TokenType = "KEYWORD:OTHERWISE";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "or":
                    this.TokenType = "KEYWORD:OR";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "set":
                    this.TokenType = "KEYWORD:SET";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "to":
                    this.TokenType = "KEYWORD:TO";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "times":
                    this.TokenType = "KEYWORD:TIMES";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "chopped":
                    this.TokenType = "KEYWORD:CHOPPED";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "repeat":
                    this.TokenType = "KEYWORD:REPEAT";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "ask":
                    this.TokenType = "KEYWORD:ASK";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "cook":
                    this.TokenType = "KEYWORD:COOK";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "using":
                    this.TokenType = "KEYWORD:USING";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "by":
                    this.TokenType = "KEYWORD:BY";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "serve":
                    this.TokenType = "KEYWORD:SERVE";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "with":
                    this.TokenType = "KEYWORD:WITH";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "as":
                    this.TokenType = "KEYWORD:AS";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "nothing":
                    this.TokenType = "KEYWORD:NOTHING";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "and":
                    this.TokenType = "KEYWORD:AND";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "only":
                    this.TokenType = "KEYWORD:ONLY";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "list":
                    this.TokenType = "KEYWORD:LIST";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "empty":
                    this.TokenType = "KEYWORD:EMPTY";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "return":
                    this.TokenType = "KEYWORD:RETURN";
                    this.TokenValue = text.toLowerCase();
                    break;
                case "+":
                    this.TokenType = "OPERATOR:PLUS";
                    this.TokenValue = text;
                    break;
                case "-":
                    this.TokenType = "OPERATOR:MINUS";
                    this.TokenValue = text;
                    break;
                case "*":
                    this.TokenType = "OPERATOR:MULTIPLY";
                    this.TokenValue = text;
                    break;
                case "/":
                    this.TokenType = "OPERATOR:DIVIDE";
                    this.TokenValue = text;
                    break;
                case "==":
                    this.TokenType = "OPERATOR:EQUAL";
                    this.TokenValue = text;
                    break;
                case "!=":
                    this.TokenType = "OPERATOR:NOT_EQUAL";
                    this.TokenValue = text;
                    break;
                case "=":
                    this.TokenType = "OPERATOR:ASSIGN";
                    this.TokenValue = text;
                    break;
                case "<":
                    this.TokenType = "OPERATOR:LESS";
                    this.TokenValue = text;
                    break;
                case ">":
                    this.TokenType = "OPERATOR:GREATER";
                    this.TokenValue = text;
                    break;
                case "(":
                    this.TokenType = "LPAREN";
                    this.TokenValue = text;
                    break;
                case ")":
                    this.TokenType = "RPAREN";
                    this.TokenValue = text;
                    break;
                case "!":
                    this.TokenType = "PUNCTUATION:EXCLAMATION";
                    this.TokenValue = text;
                    break;
                case "?":
                    this.TokenType = "PUNCTUATION:QUESTION";
                    this.TokenValue = text;
                    break;
                case ".":
                    this.TokenType = "PUNCTUATION:PERIOD";
                    this.TokenValue = text;
                    break;
                case ",":
                    this.TokenType = ",";
                    this.TokenValue = text;
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
                } else if (c == '=') {
                    tokenArray.add(new Token("="));
                    i++;
                } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>' || c == '(' || c == ')' || c == '!' || c == '?' || c == '.' || c == ',' || c == ':') {
                    tokenArray.add(new Token(String.valueOf(c)));
                    i++;
                } else if (c == '\n') {
                    tokenArray.add(new Token("\n"));
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
     * The Parser class is responsible for parsing the list of tokens into executable code.
     * It supports arithmetic expressions and if statements with conditions.
     */
    private static class Parser {
        private static List<Token> tokens;
        private static int pos;
        private static Map<String, Object> variables = new HashMap<>();
        private static Map<String, Function> functions = new HashMap<>();
        private static Scanner inputScanner = new Scanner(System.in);

        private static class Function {
            String name;
            List<String> params;
            Map<String, Object> defaults;
            List<Token> body;
            List<Token> returnTokens;

            Function(String name, List<String> params, Map<String, Object> defaults, List<Token> body, List<Token> returnTokens) {
                this.name = name;
                this.params = params;
                this.defaults = defaults;
                this.body = body;
                this.returnTokens = returnTokens;
            }
        }

        private static boolean isStatementStart(String tokenType) {
            return tokenType.equals("KEYWORD:SAY") || tokenType.equals("KEYWORD:IF") || tokenType.equals("KEYWORD:SET") || tokenType.equals("KEYWORD:REPEAT") || tokenType.equals("KEYWORD:COOK") || tokenType.equals("KEYWORD:USING") || tokenType.equals("KEYWORD:CHOPPED");
        }

        /**
         * Checks if the current position has a function call pattern.
         * A function call is: identifier with param as value, ...
         * @return true if it's a function call pattern
         */
        private static boolean isFunctionCall() {
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("IDENTIFIER")) {
                String funcName = tokens.get(pos).TokenValue;
                if (functions.containsKey(funcName)) {
                    if (pos + 1 < tokens.size() && tokens.get(pos + 1).TokenType.equals("KEYWORD:WITH")) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Parses the list of tokens, handling multiple statements.
         * @param tokenArray The list of tokens to parse.
         */
        private static void parse(List<Token> tokenArray) {
            tokens = tokenArray;
            pos = 0;
            while (pos < tokens.size()) {
                if (tokens.get(pos).TokenType.equals("NEWLINE")) {
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
                    } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:COOK")) {
                        parseCook();
                    } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:USING")) {
                        parseCook();
                    } else if (isFunctionCall()) {
                        // Handle function call as a statement
                        String funcName = tokens.get(pos).TokenValue;
                        pos++;
                        parseCall(funcName);
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
         * Also supports: set var to empty list
         * and set var to list with expr1, expr2, and exprN
         */
        private static void parseSet() {
            pos++; // consume set
            if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("IDENTIFIER")) throw new RuntimeException("Expected variable name after set");
            String varName = tokens.get(pos).TokenValue;
            pos++;
            if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:TO")) throw new RuntimeException("Expected 'to' after variable name");
            pos++; // consume to

            Object value;
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:ASK")) {
                // Handle: set var to ask "prompt"
                pos++; // consume ask
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("STRING")) throw new RuntimeException("Expected prompt string after 'ask'");
                String prompt = tokens.get(pos).TokenValue;
                pos++; // consume prompt string
                System.out.print(prompt);
                String userInput = inputScanner.nextLine();
                // Try to parse as number if possible, otherwise keep as string
                try {
                    value = Double.parseDouble(userInput);
                } catch (NumberFormatException e) {
                    value = userInput;
                }
            } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:EMPTY")) {
                pos++; // consume empty
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:LIST")) throw new RuntimeException("Expected 'list' after 'empty'");
                pos++; // consume list
                value = new ArrayList<>();
            } else if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:LIST")) {
                pos++; // consume list
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:WITH")) throw new RuntimeException("Expected 'with' after 'list'");
                pos++; // consume with
                List<Object> list = new ArrayList<>();
                Object item = parseExpr(false);
                list.add(item);
                boolean lastWasAnd = false;
                while (pos < tokens.size()) {
                    if (tokens.get(pos).TokenType.equals(",")) {
                        pos++; // consume ,
                        if (pos < tokens.size() && tokens.get(pos).TokenValue.equals("and")) {
                            pos++; // consume and
                            item = parseExpr(false);
                            list.add(item);
                            lastWasAnd = true;
                            break;
                        } else {
                            item = parseExpr(false);
                            list.add(item);
                            lastWasAnd = false;
                        }
                    } else if (tokens.get(pos).TokenValue.equals("and")) {
                        pos++; // consume and
                        item = parseExpr(false);
                        list.add(item);
                        lastWasAnd = true;
                        break;
                    } else {
                        break;
                    }
                }

                value = list;
            } else {
                value = parseExpr(false);
            }
            variables.put(varName, value);
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
         * Parses parameters: comma-separated params or params separated by 'and', e.g., param1, param2, param3 or param1 and param2 and param3,
         * Supports defaults: param or default
         */
        private static void parseParams(List<String> params, Map<String, Object> defaults) {
            while (pos < tokens.size()) {
                if (tokens.get(pos).TokenType.equals("IDENTIFIER")) {
                    String param = tokens.get(pos).TokenValue;
                    params.add(param);
                    pos++;
                    if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:OR")) {
                        pos++; // consume or
                        Object defaultVal = parseExpr(false);
                        defaults.put(param, defaultVal);
                    }
                    if (pos < tokens.size() && (tokens.get(pos).TokenType.equals(",") || tokens.get(pos).TokenValue.equalsIgnoreCase("and"))) {
                        pos++; // consume , or and
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        /**
         * Parses a cook statement: cook [func name] using [params] by [code] serve [return]
         * or using [params] cook [func name] by [code] serve [return]
         */
        private static void parseCook() {
            String funcName;
            List<String> params = new ArrayList<>();
            Map<String, Object> defaults = new HashMap<>();
            List<Token> body = new ArrayList<>();
            Object returnValue = null;

            boolean usingFirst = tokens.get(pos).TokenType.equals("KEYWORD:USING");
            if (usingFirst) {
                pos++; // consume using
                parseParams(params, defaults);
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:COOK")) throw new RuntimeException("Expected 'cook' after params");
                pos++; // consume cook
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("IDENTIFIER")) throw new RuntimeException("Expected function name after cook");
                funcName = tokens.get(pos).TokenValue;
                pos++;
            } else {
                pos++; // consume cook
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("IDENTIFIER")) throw new RuntimeException("Expected function name after cook");
                funcName = tokens.get(pos).TokenValue;
                pos++;
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:USING")) throw new RuntimeException("Expected 'using' after function name");
                pos++; // consume using
                // Handle optional "only" keyword
                if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:ONLY")) {
                    pos++; // consume only
                }
                parseParams(params, defaults);
            }

            if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:BY")) throw new RuntimeException("Expected 'by' after params");
            pos++; // consume by

            // Parse body until serve
            int startPos = pos;
            while (pos < tokens.size() && !tokens.get(pos).TokenType.equals("KEYWORD:SERVE")) {
                body.add(tokens.get(pos));
                pos++;
            }
            if (pos >= tokens.size()) throw new RuntimeException("Expected 'serve' at end of function");
            pos++; // consume serve

            // Parse return value
            List<Token> returnTokens = new ArrayList<>();
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:NOTHING")) {
                pos++;
            } else {
                while (pos < tokens.size() && !tokens.get(pos).TokenType.equals("NEWLINE") && !isStatementStart(tokens.get(pos).TokenType)) {
                    returnTokens.add(tokens.get(pos));
                    pos++;
                }
                if (pos < tokens.size() && tokens.get(pos).TokenType.equals("NEWLINE")) {
                    pos++; // consume NEWLINE
                }
            }

            functions.put(funcName, new Function(funcName, params, defaults, body, returnTokens));
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
            } else if (t.TokenType.equals("KEYWORD:ASK")) {
                // Handle ask "prompt" - get user input
                if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("STRING")) throw new RuntimeException("Expected prompt string after 'ask'");
                String prompt = tokens.get(pos).TokenValue;
                pos++; // consume prompt string
                System.out.print(prompt);
                String userInput = inputScanner.nextLine();
                // Try to parse as number if possible, otherwise keep as string
                try {
                    return Double.parseDouble(userInput);
                } catch (NumberFormatException e) {
                    return userInput;
                }
            } else if (t.TokenType.equals("IDENTIFIER")) {
                if (functions.containsKey(t.TokenValue)) {
                    return parseCall(t.TokenValue);
                } else if (variables.containsKey(t.TokenValue)) {
                    Object varValue = variables.get(t.TokenValue);
                    // Check for list indexing: var:index
                    if (pos < tokens.size() && tokens.get(pos).TokenValue.equals(":")) {
                        pos++; // consume :
                        Object indexObj = parseExpr(false);
                        if (!(indexObj instanceof Double)) throw new RuntimeException("Index must be numeric");
                        int index = ((Double) indexObj).intValue() - 1; // 1-based to 0-based
                        if (!(varValue instanceof List)) throw new RuntimeException("Cannot index non-list variable");
                        List<Object> list = (List<Object>) varValue;
                        if (index < 0 || index >= list.size()) throw new RuntimeException("Index out of bounds");
                        return list.get(index);
                    } else {
                        return varValue;
                    }
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

        /**
         * Parses a function call: funcname with param as value, param2 as value2
         * @param funcName The name of the function.
         * @return The return value of the function.
         */
        private static Object parseCall(String funcName) {
            Function func = functions.get(funcName);
            if (func == null) throw new RuntimeException("Undefined function: " + funcName);

            Map<String, Object> args = new HashMap<>();
            if (pos < tokens.size() && tokens.get(pos).TokenType.equals("KEYWORD:WITH")) {
                pos++; // consume with
                while (pos < tokens.size() && tokens.get(pos).TokenType.equals("IDENTIFIER")) {
                    String param = tokens.get(pos).TokenValue;
                    pos++;
                    if (pos >= tokens.size() || !tokens.get(pos).TokenType.equals("KEYWORD:AS")) throw new RuntimeException("Expected 'as' after param");
                    pos++; // consume as
                    Object value = parseExpr(false);
                    args.put(param, value);
                    if (pos < tokens.size() && tokens.get(pos).TokenType.equals(",")) {
                        pos++; // consume ,
                    } else {
                        break;
                    }
                }
            }

            // Set params
            Map<String, Object> oldVars = new HashMap<>(variables);
            for (int i = 0; i < func.params.size(); i++) {
                String param = func.params.get(i);
                if (args.containsKey(param)) {
                    variables.put(param, args.get(param));
                } else if (func.defaults.containsKey(param)) {
                    variables.put(param, func.defaults.get(param));
                } else {
                    throw new RuntimeException("Missing argument for param: " + param);
                }
            }

            // Execute body
            int oldPos = pos;
            List<Token> oldTokens = tokens;
            tokens = func.body;
            pos = 0;
            while (pos < tokens.size()) {
                if (tokens.get(pos).TokenType.equals("NEWLINE")) {
                    pos++;
                    continue;
                }
                parseStatement(true);
            }

            // Evaluate return value
            Object returnValue;
            if (func.returnTokens.isEmpty()) {
                returnValue = null;
            } else {
                List<Token> oldTokens2 = tokens;
                int oldPos2 = pos;
                tokens = func.returnTokens;
                pos = 0;
                returnValue = parseExpr(false);
                tokens = oldTokens2;
                pos = oldPos2;
            }

            // Restore
            tokens = oldTokens;
            pos = oldPos;
            variables = oldVars;

            return returnValue;
        }
    }
}
