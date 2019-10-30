import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

class ParseMolecule {

    public static void main(String[] args) {
        Map<String, Integer> test = new HashMap<String, Integer>();
        //molecule("Mg(OH)2");
        //System.out.println();
        //molecule("K4[ON(SO3)2]2");
        //test = getAtoms("As2{Be4C5[BCo3(CO2)3]2}4Cu5");
        test = getAtoms("((H)2)[O]");
        for (String k : test.keySet()) {
            System.out.print(k + " : " + test.get(k) + "  , ");
        }
        //System.out.println("\nShould be :");
        //System.out.println("As : 2  , Be : 16  , C : 44  , B : 8  , Co : 24  , O : 48  , Cu : 5");

    }
    public static Map<String,Integer> getAtoms(String formula) {
        Map<String, Integer> atoms = new HashMap<String, Integer>();
        Map<String, Integer> roundMap = new HashMap<String, Integer>();
        Map<String, Integer> squareMap = new HashMap<String, Integer>();
        Map<String, Integer> curlyMap = new HashMap<String, Integer>();
        LinkedList<Map<String, Integer>> order = new LinkedList<>();

        atoms.clear();
        order.clear();
        order.add(atoms);

        for (int i = 0; i < formula.length(); i++) {
            char element = formula.charAt(i);
            String key = "";
            int value = 1;

            if (element > 96 && element < 123) {
                key += formula.charAt(i - 1) + "" + element;
            }
            else {
                key += element;
            }

            // Check if first element is not last
            if (i < formula.length() - 1) {
                char nextElement = formula.charAt(i + 1);

                // Check if first element is a letter
                if ((element > 64 && element < 91) || (element > 96 && element < 123)) {

                     // Check if second element is a number
                    if (nextElement > 47 && nextElement < 58) {
                        value = checkNumber(nextElement, formula, i);

                        writeValue(order.getLast(), key, value);

                    }
                    // If next element is a bracket or an uppercase letter
                    else if (nextElement == 40 || nextElement == 41 || nextElement == 91 || nextElement == 93
                    || nextElement == 123 || nextElement == 125 || (nextElement > 64 && nextElement < 91)) {

                        writeValue(order.getLast(), key, value);
                    }
                }

                // Check if first element is an opening round bracket
                if (element == 40) {
                    order.add(roundMap);
                }
                // Check if first element is an opening square bracket
                else if (element == 91) {
                    order.add(squareMap);
                }
                // Check if first element is an opening curly bracket
                else if (element == 123) {
                    order.add(curlyMap);
                }

                // Check if first element is a closing round bracket
                if (element == 41) {
                    if (nextElement > 47 && nextElement < 58) {
                        value = checkNumber(nextElement, formula, i);
                    }
                    if (order.getLast() == roundMap) {
                        closeBracket(roundMap, order.get(order.size() - 2), value);
                        order.removeLast();
                    }
                }
                // Check if first element is a closing square bracket
                else if (element == 93) {
                    if (nextElement > 47 && nextElement < 58) {
                        value = checkNumber(nextElement, formula, i);
                    }
                    if (order.getLast() == squareMap) {
                        closeBracket(squareMap, order.get(order.size() - 2), value);
                        order.removeLast();
                    }
                }
                // Check if first element is a closing curly bracket
                else if (element == 125) {
                    if (nextElement > 47 && nextElement < 58) {
                        value = checkNumber(nextElement, formula, i);
                    }
                    if (order.getLast() == curlyMap) {
                        closeBracket(curlyMap, order.get(order.size() - 2), value);
                        order.removeLast();
                    }
                }

            }
            // If first element is last
            else  {
                // If first element is a letter
                if (element > 64 && element < 91) {
                    writeValue(order.getLast(), key, value);
                }
                else {
                    // Check if first element is a closing round bracket
                    if (element == 41) {
                        if (order.getLast() == roundMap) {
                            closeBracket(roundMap, order.get(order.size() - 2), value);
                            order.removeLast();
                        }
                    }
                    // Check if first element is a closing square bracket
                    else if (element == 93) {
                        if (order.getLast() == squareMap) {
                            closeBracket(squareMap, order.get(order.size() - 2), value);
                            order.removeLast();
                        }
                    }
                    // Check if first element is a closing curly bracket
                    else if (element == 125) {
                        if (order.getLast() == curlyMap) {
                            closeBracket(curlyMap, order.get(order.size() - 2), value);
                            order.removeLast();
                        }
                    }
                }
            }
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
}