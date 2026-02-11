
# 🪓 Chopped Programming Language

**Chopped** is an expressive, natural-language-inspired interpreter built in Java. It is designed to be readable, almost like writing instructions in a kitchen, while maintaining the logic of a structured programming language.

## 🚀 Getting Started

### Prerequisites
* **Java Development Kit (JDK) 8** or higher.

### Installation & Execution
1. **Clone and Compile:**
   ```bash
   git clone https://github.com/matthewl580/Chopped.git
   cd chopped
   javac chopped.java 
   ```

2. **Run a Script:**
```bash
chopped > say "You're cooked lowkey"
```



---

## 📝 Language Features & Syntax

### 1. Variables and Assignment

Chopped uses `set [var] to [value]` syntax. It supports numbers, strings, and expressions.

* **Syntax:** `set identifier to expression`
* **Example:** ```chopped
set apples to 5
set total to apples * 2
say total
```


```



### 2. Output (`say`)

The `say` command is used for printing. It features "smart punctuation" handling—if you end a string with a period, exclamation, or question mark, Chopped ensures the formatting remains clean.

* **Example:** ```chopped
say "Hello world!"
say 100 + (50 / 2)
```


```



### 3. Conditional Logic

Chopped supports robust `if` statements with optional `otherwise` (else) blocks.

* **Keywords:** `if`, `then`, `do`, `otherwise`
* **Operators:** `==`, `!=`, `<`, `>`
* **Example:**
```chopped
set heat to 200
if (heat > 180) then do say "Too hot!" otherwise say "Just right."

```



### 4. Loops

Repeating actions is straightforward with the `for` loop.

* **Syntax:** `for [number] times [statement]`
* **Example:**
```chopped
set count to 3
for count times say "Chopping..."

```



---

## 🧩 Advanced Examples

### Dynamic Messaging

You can combine strings and logic in a single line using the inline `if` syntax within a `say` command:

```chopped
set ingredients to 10
say "Inventory full" if ingredients == 10 otherwise say "Keep gathering."

```

### Mathematical Precedence

Chopped supports standard order of operations and parenthetical grouping:

```chopped
set x to (10 + 5) * 2
set y to 100 / (x - 20)
say y

```

### File Inclusion

Organize your code by splitting logic into multiple files:

```chopped
// main.chopped
set version to 1.2
chopped "header.chopped"
say "Loading version..."
say version

```

---

## ⚙️ Technical Architecture

The interpreter is built as a single-pass processing system contained within the `chopped` class.

### 1. The Lexer

The Lexer processes input character-by-character. It does **not** rely on whitespace to separate tokens, allowing for compact expressions like `x=10+y`. It categorizes input into `NUMBER`, `STRING`, `KEYWORD`, `OPERATOR`, or `IDENTIFIER`.

### 2. The Parser

The Parser uses a recursive descent approach to evaluate logic:

* **Expressions:** Handles addition and subtraction.
* **Terms:** Handles multiplication and division.
* **Factors:** Handles the lowest level units (numbers, variables, or parenthesized expressions).

### 3. Memory Management

Variables are stored in a `HashMap<String, Object>`, allowing for dynamic typing where a variable can hold a `Double` or a `String`.

---

## 🛠 Contribution

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
