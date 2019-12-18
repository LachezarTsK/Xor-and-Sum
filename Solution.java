import java.util.Scanner;

public class Solution {
  private static int mod = (int) Math.pow(10, 9) + 7;
  private static long totalShifsToLeft = 314159;
  private static long exponent = 1;
  private static long ones = 0;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String a_binary = scanner.next();
    String b_binary = scanner.next();
    scanner.close();

    int result = xorSum_total(a_binary, b_binary);
    System.out.println(result);
  }

/**
  * Calculates the result for xor sum.
  */
  private static int xorSum_total(String a_binary, String b_binary) {
    long total_xorSum = xorSum_bits_within_totalShifsToLeft(a_binary, b_binary)
                      + xorSum_bits_higher_than_totalShifsToLeft(a_binary, b_binary);
    return (int) total_xorSum % mod;
  }
  
/**  
  * Calculates 'a' XOR 'b' for bits from index 0 to 314159.
  * Adds the value of each XOR to the total xor sum for these bits.
  *
  * |-------->  (314159+1) <---------|   =>  bits covered in current method.
  * xxxxxxxxxxxxxxxxxxxxxxxxxxxxx....x   =>  total bit shifts to left = 314159					      
  *           xxxxxxxxxxxxxxxxxxx....x   =>  number 'a', max bits = Math.pow(10,5)	  
  *           xxxxxxxxxxxxxxxxxxx....x   =>  number 'b', max bits = Math.pow(10,5)
  *
  *@return A long integer, representing the sum of 'a' XOR 'b' for the described bits.
  */ 
  private static long xorSum_bits_within_totalShifsToLeft(String a_binary, String b_binary) {
    long xorSum = 0;
    for (int i = 0; i <= totalShifsToLeft; i++) {
      int a_bit = (a_binary.length() > i) ? (a_binary.charAt(a_binary.length() - 1 - i) - '0') : 0;
      int b_bit = (b_binary.length() > i) ? (b_binary.charAt(b_binary.length() - 1 - i) - '0') : 0;

      if (b_bit == 1) {
        ones++;
      }
      
      long multiplicant = (a_bit == 0) ? ones : ((totalShifsToLeft + 1) - ones);
      xorSum = (xorSum + (exponent * multiplicant) % mod) % mod;
      exponent = (exponent * 2) % mod;
    }

    return xorSum;
  }

/**
 * Calculates 'a' XOR 'b' for bits from index 314160 to index (314160+b_binary.length()-1).
 * Adds the value of each XOR to the total xor sum for these bits.
 *
 * Position of the bits after 314159 shifts to the left of b_binary, as follows:
 *                           |-------->  (314159+1) <---------|
 *                           xxxxxxxxxxxxxxxxxxxxxxxxxxxxx....x   =>  total shifts to left                       
 *                                     xxxxxxxxxxxxxxxxxxx....x   =>  number 'a' 
 * xxxxxxxxxxxxxxxxxxxxxx....x                                    =>  number 'b'
 * |--> b_binary.length() <--| => bits covered in current method.
 *
 * @return A long integer, representing the sum of 'a' XOR 'b' for the described bits.
 */ 
  private static long xorSum_bits_higher_than_totalShifsToLeft(String a_binary, String b_binary) {
    long xorSum = 0;
    for (int i = 0; i < b_binary.length(); i++) {
      if (b_binary.charAt(b_binary.length() - 1 - i) == '1') {
        ones--;
      }
      xorSum = (xorSum + (exponent * ones) % mod) % mod;
      exponent = (exponent * 2) % mod;
    }

    return xorSum;
  }
}
