# TODO List for Chopped Language Enhancements

## Task 1: Make repeat keyword modular
- [x] Add "repeat" as KEYWORD:REPEAT in Token switch
- [x] Add parseRepeat() method in Parser class
- [x] Modify parse() method to call parseRepeat() when encountering KEYWORD:REPEAT
- [x] Implement logic in parseRepeat() to handle both forms:
  - repeat [code] [NUM/VAR/EXPR] times
  - repeat [NUM/VAR/EXPR] times [code]

## Task 2: Implement ask keyword
- [x] Add "ask" as KEYWORD:ASK in Token switch
- [x] Add static Scanner in Parser class for user input
- [x] Modify parseSet() to handle "to ask string" syntax

## Testing
- [x] Test repeat functionality with both syntaxes
- [x] Test ask functionality in set statements
