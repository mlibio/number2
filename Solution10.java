/**
 * @description:
 * 给定一个表示分数加减运算的字符串 expression ，你需要返回一个字符串形式的计算结果。
 *
 * 这个结果应该是不可约分的分数，即最简分数。 如果最终结果是一个整数，例如 2，你需要将它转换成分数形式，其分母为 1。所以在上述例子中, 2 应该被转换为 2/1。
 *
 *
 *
 * 示例 1:
 *
 * 输入: expression = "-1/2+1/2"
 * 输出: "0/1"
 *  示例 2:
 *
 * 输入: expression = "-1/2+1/2+1/3"
 * 输出: "1/3"
 * 示例 3:
 *
 * 输入: expression = "1/3-1/2"
 * 输出: "-1/6"
 *
 *
 * 提示:
 *
 * 输入和输出字符串只包含 '0' 到 '9' 的数字，以及 '/', '+' 和 '-'。
 * 输入和输出分数格式均为 ±分子/分母。如果输入的第一个分数或者输出的分数是正数，则 '+' 会被省略掉。
 * 输入只包含合法的最简分数，每个分数的分子与分母的范围是  [1,10]。 如果分母是1，意味着这个分数实际上是一个整数。
 * 输入的分数个数范围是 [1,10]。
 * 最终结果的分子与分母保证是 32 位整数范围内的有效整数。
 */
public class Solution10 {
    public String fractionAddition(String expression) {
        long numerator = 0, denominator = 1; // 分子，分母
        int index = 0, n = expression.length();

        while (index < n) {
            // 读取分子
            long x1 = 0, sign = 1;
            if (expression.charAt(index) == '-' || expression.charAt(index) == '+') {
                sign = expression.charAt(index) == '+' ? 1 : -1;
                index++;
            }
            while (index < n && Character.isDigit(expression.charAt(index))) {
                x1 = x1 * 10 + expression.charAt(index) - '0';
                index++;
            }
            x1 *= sign; // 设置分子符号
            index++; // 跳过 '/'

            // 读取分母
            long y1 = 0;
            while (index < n && Character.isDigit(expression.charAt(index))) {
                y1 = y1 * 10 + expression.charAt(index) - '0'; // 正确读取分母
                index++;
            }

            // 累加分数
            numerator = numerator * y1 + x1 * denominator;
            denominator *= y1; // 更新分母
        }

        if (numerator == 0) {
            return "0/1"; // 结果为0时
        }

        long gcdValue = gcd(Math.abs(numerator), denominator); // 获取最大公约数
        return (numerator / gcdValue) + "/" + (denominator / gcdValue); // 返回最简分数
    }

    public long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a); // 返回绝对值
    }

}

//这段代码总体思路是正确的，已经可以解决分数加减运算的问题，并且使用了最大公约数（gcd）来简化分数。但还有几个可以优化或需要注意的地方：

//分母为负数的处理： 当前代码中，当 numerator 为负时，分母始终为正，但有可能分母最后被简化成负数。为了保证结果形式统一（如 -1/6 而不是 1/-6），可以在返回结果前检查分母，如果分母是负数，则将符号移动到分子上。

//溢出问题： 由于 numerator 和 denominator 的计算涉及到累乘和累加，有可能导致溢出。虽然你用的是 long 类型，它有更大的范围，但如果表达式中的分子或分母较大，依旧有可能发生溢出。需要确保输入范围和内部计算符合32位整数的限制。

//代码结构优化： 可以将分数的累加部分拆分为一个方法，提高代码的可读性和可维护性。
