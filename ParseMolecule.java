import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

class ParseMolecule {

    public static void main(String[] args) {
        Map<String, Integer> test = new HashMap<String, Integer>();
        test = getAtoms("As2{Be4C5[BCo3(CO2)3]2}4Cu5");
        for (String k : test.keySet()) {
            System.out.print(k + " : " + test.get(k) + "  , ");
        }
        System.out.println("\nShould be :");
        System.out.println("As : 2  , Be : 16  , C : 44  , B : 8  , Co : 24  , O : 48  , Cu : 5");
    }

    public static Map<String,Integer> getAtoms(String formula) {
        Map<String, Integer> atoms = new HashMap<String, Integer>();
        Map<String, Integer> roundMap = new HashMap<String, Integer>();
        Map<String, Integer> squareMap = new HashMap<String, Integer>();
        Map<String, Integer> curlyMap = new HashMap<String, Integer>();
        LinkedList<Map<String, Integer>> order = new LinkedList<>();
        order.add(atoms);
        
        for (int i = 0; i < formula.length(); i++) {
            char element = formula.charAt(i);
            char nextElement;
            String key = "";
            int value = 1;
            
            // Check first element
            if (element > 96 && element < 123) {
                if (i == 0 || !(formula.charAt(i - 1) > 64 && formula.charAt(i - 1) < 91)) {
                    throw new IllegalArgumentException();
                }
                else {
                    key += formula.charAt(i - 1) + "" + element;
                }
            }
            else {
                key += element;
            }

            if (i < formula.length() - 1) {
                nextElement = formula.charAt(i + 1);

                // Check if next element is a number
                if (nextElement > 47 && nextElement < 58) {
                    value = checkNumber(nextElement, formula, i);  
                }
            }
            else {
                nextElement = ' ';
            }

            // Check if first element is an opening round bracket
            if (element == 40) {
                order.add(roundMap);
            }
            // or an opening square bracket
            else if (element == 91) {
                order.add(squareMap);
            }
            // or an opening curly bracket
            else if (element == 123) {
                order.add(curlyMap);
            }
    
            // Check if first element is a closing round bracket
            if (element == 41) {
                checkClosedBracket(roundMap, order, value, formula, i, nextElement);
            }
            // or a closing square bracket
            else if (element == 93) {
                checkClosedBracket(squareMap, order, value, formula, i, nextElement);
            }
            // or a closing curly bracket
            else if (element == 125) {
                checkClosedBracket(curlyMap, order, value, formula, i, nextElement);
            }
            // If first element is a letter and next is a lowercase letter
            if (((element > 64 && element < 91) || (element > 96 && element < 123)) && !(nextElement > 96 && nextElement < 123)) {
                writeValue(order.getLast(), key, value);
            }
        }
        if (order.getLast() != atoms) {
            throw new IllegalArgumentException();
        }
        return atoms;
    }

    private static void writeValue(Map<String, Integer> map, String key, int value) {

        if (map.containsKey(key)) {
            map.put(key, map.get(key) + value);
        }
        else {
            map.put(key, value);
        }
    }

    private static int checkNumber(char element, String formula, int i) {
        int value = 1;
        
        if (i < formula.length() - 2 && formula.charAt(i + 2) > 47 && formula.charAt(i + 2) < 58) {
            value = Integer.parseInt(element + "" + formula.charAt(i + 2));
        }
        else {
            value = Integer.parseInt(element + "");
        }
        return value;
    }

    private static void closeBracket(Map<String, Integer> closedMap, Map<String, Integer> previousMap, int value) {

        Map<String, Integer> tempMap = new HashMap<String, Integer>();

        for (String k : closedMap.keySet()) {
            tempMap.put(k, closedMap.get(k) * value);
        }
        closedMap.clear();

        for (String k : tempMap.keySet()) {
            
            if (previousMap.containsKey(k)) {
                previousMap.put(k, previousMap.get(k) + tempMap.get(k));
            }
            else {
                previousMap.put(k, tempMap.get(k));
            }
        }
    }

    private static void checkClosedBracket(Map map, LinkedList<Map<String, Integer>> order, int value, String formula, int i, char nextElement) {

        if (order.getLast() != map) {
            throw new IllegalArgumentException();
        }
        if (nextElement > 47 && nextElement < 58) {
            value = checkNumber(nextElement, formula, i);
        }
        closeBracket(map, order.get(order.size() - 2), value);
        order.removeLast();
    }
}