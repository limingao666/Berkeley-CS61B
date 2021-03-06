import list.EquationList;

public class Calculator {
    // YOU MAY WISH TO ADD SOME FIELDS
    EquationList history;
    EquationList tail;
    int size = 0;
    /**
     * TASK 2: ADDING WITH BIT OPERATIONS
     * add() is a method which computes the sum of two integers x and y using 
     * only bitwise operators.
     * @param x is an integer which is one of two addends
     * @param y is an integer which is the other of the two addends
     * @return the sum of x and y
     **/
    public int add(int x, int y) {
        // YOUR CODE HERE
        int part = x ^ y;
        int carry = x & y;
        if (carry == 0){
            return part;
        } else {
            carry <<= 1;
            return add(part, carry);
        }        
    }

    /**
     * TASK 3: MULTIPLYING WITH BIT OPERATIONS
     * multiply() is a method which computes the product of two integers x and 
     * y using only bitwise operators.
     * @param x is an integer which is one of the two numbers to multiply
     * @param y is an integer which is the other of the two numbers to multiply
     * @return the product of x and y
     **/
    public int multiply(int x, int y) {
        // YOUR CODE HERE
        if (y == 1){
            return x;
        } else if (y == 0){
            return 0;
        } else{
            int result = 0;
            boolean yNegated = false;

            if (y < 0){
                y = add(~y, 1);
                yNegated = true; 
            }

            while (y > 0){
                result = add(result, x);
                y -= 1;
            }

            if (!yNegated){
                return result;    
            } else {
                return add(~result, 1);
            }            
        }
    }

    /**
     * TASK 5A: CALCULATOR HISTORY - IMPLEMENTING THE HISTORY DATA STRUCTURE
     * saveEquation() updates calculator history by storing the equation and 
     * the corresponding result.
     * Note: You only need to save equations, not other commands.  See spec for 
     * details.
     * @param equation is a String representation of the equation, ex. "1 + 2"
     * @param result is an integer corresponding to the result of the equation
     **/
    public void saveEquation(String equation, int result) {
        // YOUR CODE HERE
        if (history == null){
            history = new EquationList(equation, result, null);
            tail = history;
        } else {
            tail.next = new EquationList(equation, result, null);
            tail = tail.next;
        }

        size += 1;
    }

    /**
     * TASK 5B: CALCULATOR HISTORY - PRINT HISTORY HELPER METHODS
     * printAllHistory() prints each equation (and its corresponding result), 
     * most recent equation first with one equation per line.  Please print in 
     * the following format:
     * Ex   "1 + 2 = 3"
     **/
    public void printAllHistory() {
        // YOUR CODE HERE
        printHistory(size);
    }

    /**
     * TASK 5B: CALCULATOR HISTORY - PRINT HISTORY HELPER METHODS
     * printHistory() prints each equation (and its corresponding result), 
     * most recent equation first with one equation per line.  A maximum of n 
     * equations should be printed out.  Please print in the following format:
     * Ex   "1 + 2 = 3"
     **/
    public void printHistory(int n) {
        // YOUR CODE HERE
        if (n <= 0){
            return;
        } else if (n > size){
            System.out.println("History doesn't contain " + n + " records.");
        } else {
            EquationList ptr = history;
            for (int i = 0; i < (size - n); i++){
                ptr = ptr.next;
            }

            printHistoryHelper(ptr);
        }

    }    

    private void printHistoryHelper(EquationList list){
        if (list == null){
            return;
        } else if (list.next == null){
            System.out.println(list.equation + " = " + list.result);
        } else {
            printHistoryHelper(list.next);
            System.out.println(list.equation + " = " + list.result);
        }
    }
    /**
     * TASK 6: CLEAR AND UNDO
     * undoEquation() removes the most recent equation we saved to our history.
    **/
    public void undoEquation() {
        // YOUR CODE HERE
        if (size <= 0){
            return;
        } else if (size == 1){
            history = null;
        } else {
            EquationList ptr = history;
            while (ptr.next.next != null){
                ptr = ptr.next;
            }
            ptr.next = null;
        }
        size -= 1;
    }

    /**
     * TASK 6: CLEAR AND UNDO
     * clearHistory() removes all entries in our history.
     **/
    public void clearHistory() {
        // YOUR CODE HERE
        history = null;
        size = 0;
    }

    /**
     * TASK 7: ADVANCED CALCULATOR HISTORY COMMANDS
     * cumulativeSum() computes the sum over the result of each equation in our 
     * history.
     * @return the sum of all of the results in history
     **/
    public int cumulativeSum() {
        // YOUR CODE HERE
        int sum = 0;
        EquationList ptr = history;
        while (ptr != null){
            sum += ptr.result;
            ptr = ptr.next;
        }
        return sum;
    }

    /**
     * TASK 7: ADVANCED CALCULATOR HISTORY COMMANDS
     * cumulativeProduct() computes the product over the result of each equation 
     * in history.
     * @return the product of all of the results in history
     **/
    public int cumulativeProduct() {
        // YOUR CODE HERE
        int product = 1;
        EquationList ptr = history;
        while (ptr != null){
            product *= ptr.result;
            ptr = ptr.next;
        }
        return product;
    }
}