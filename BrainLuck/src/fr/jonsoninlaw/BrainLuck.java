package fr.jonsoninlaw;

import java.util.*;

public class BrainLuck {

    private String code;


    public BrainLuck(String code) {
        this.code = code;
    }

    public String process(String input) {
        int pointer = 0;
        char data = 0;
        String result = "";
        LinkedList<Boolean> brackets = new LinkedList<>();
        LinkedList<Integer> bracketIndex = new LinkedList<>();
        for (int i = 0; i < this.code.length(); i++) {
            switch (this.code.charAt(i)) {
                case '>':
                    if (brackets.isEmpty() || brackets.getLast() != false) {
                        pointer++;
                    }
                    break;
                case '<':
                    if (brackets.isEmpty() || brackets.getLast() != false) {
                        pointer--;
                    }
                    break;
                case '+':
                    if (brackets.isEmpty() || brackets.getLast() != false) {
                        data = (char) (data == 255 ? 0 : data + 1);
                    }
                    break;
                case '-':
                    if (brackets.isEmpty() || brackets.getLast() != false) {
                        data = (char) (data == 0 ? 255 : data - 1);
                    }
                    break;
                case '.':
                    if (brackets.isEmpty() || brackets.getLast() != false) {
                        result += data;
                    }
                    break;
                case ',':
                    if (brackets.isEmpty() || brackets.getLast() != false) {
                        data = input.charAt(pointer);
                        input = input.substring(0, pointer) + input.substring(pointer + 1, input.length());
                    }
                    break;
                case '[':
                    bracketIndex.add(i);
                    if (input.charAt(pointer) == 0) {
                        brackets.add(false);
                    }
                    else {
                        brackets.add(true);
                    }
                    break;
                case ']':
                    if (brackets.getLast() && input.charAt(pointer) != 0) {
                        pointer = bracketIndex.getLast() + 1;
                    } else {
                        brackets.removeLast();
                        bracketIndex.removeLast();
                    }
                    break;
            }
        }
        return result;
    }
}


/* Instructions

    > increment the data pointer (to point to the next cell to the right).
    < decrement the data pointer (to point to the next cell to the left).
    + increment (increase by one, truncate overflow: 255 + 1 = 0) the byte at the data pointer.
    - decrement (decrease by one, treat as unsigned byte: 0 - 1 = 255 ) the byte at the data pointer.
    . output the byte at the data pointer.
    , accept one byte of input, storing its value in the byte at the data pointer.
    [ if the byte at the data pointer is zero, then instead of moving the instruction pointer forward to the next command, jump it forward to the command after the matching ] command.
    ] if the byte at the data pointer is nonzero, then instead of moving the instruction pointer forward to the next command, jump it back to the command after the matching [ command.

    */