public class Main {
    static void main() {
        GameLogic game1 = new GameLogic();
        game1.game(game1);
    }
    
// ToDo List
//  possible - currently all attacks will always hit the oldest minion, even if they could kill other ones?
//  possible - add minion sort so the most damaging minion dies first (if the attack will kill it [similar to combination block code]), not the oldest one maybe?
// CONTENT - change getActions code to allow for use of maxActions
// CHECK - readability, comments, check to remove _ on  _localVariables, consistency
//  CONTENT - computer players
//  CONTENT - campaign with different themed players


    public static void main(String[] args) {
        main();
    }
}

/*
 * My notes for Java from reading a website, please ignore all below this line
 */

// multi assigning:
// x = y = z = 50;

// float x = 3.45f; <- 6-7 dp
// char x = 'a'; <- single characters
// double x = 12.33; <- 15 decimal places

// camelCase for variables
// byte x for values between -128 and 127
// short x for numbers between -32k and 32k
// Cap for Strings, Arrays, Classes, Interfaces


// x ^= 3 = x^3

// String text = "hello";
// String TEXT = text.toUpperCase();
// text.indexOf("el")    would = 1 (as 0 1st)

// String txt = "It\'s alright.";
// for when your String needs a quote or '



// String result = (time < 18) ? "Good day." : "Good evening.";

// is the same as 

// int time = 20;
// if (time < 18) {
//     System.out.println("Good day.");
// } else {
//     System.out.println("Good evening.");



// executes once BEFORE checking validity!
// do{    }
// while ()


// continue skips rest for statement but    does next one


// multidimensional arrays
// int[][] myNumbers = { {1, 2, 3, 4}, {5, 6, 7} };

// static means belongs to entire class not element of class

// recursion: f calls f to execute



