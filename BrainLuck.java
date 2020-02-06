import java.util.*;

public class BrainLuck {

    
    public BrainLuck(String code) {
        
    }
    
    public String process(String input) {
        return "";
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