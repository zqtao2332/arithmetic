package cn.zqtao.learn.nowcode_advance;

import java.util.Arrays;

/**
 * @auther: zqtao
 * @description: KMP：解决两个字符串是否包含的问题
 * KMP算法
 *
 * 给定两个字符串str和match，长度分别是N和M。实现一个算法，如果字符串str中含有子串match，则返回match在str中的开始位置，不含有则返回-1。
 *
 * 例如
 *
 *     str = "acbc", match = "bc" , 返回2
 *     str = "acbc", match = "bcc", 返回-1
 *
 * 要求，如果match的长度大于str的长度(M > N) ，str一定不会含有match, 可直接返回-1。如果N>=M , 要求算法复杂度为O(N)。
 *
 * 1、理解最长前缀和最长后缀匹配长度
 *
 *     最长前缀不包括最后一个字符
 *
 *     最长后缀不包括第一个字符
 *
 *
 */
public class Code_01_KMP {

    /**
     * str 是否包含子串 sub ，包含返回开始位置。
     * @param str 母串
     * @param sub 待匹配子串
     * @return 开始匹配位置
     */
    public static int kmp(String str, String sub) {
        if (str == null || sub == null || sub.length() < 1 || str.length() < sub.length()) {
            return -1;
        }

        char[] strArr = str.toCharArray();
        char[] subArr = sub.toCharArray();

        int strIndex = 0; // 指向 str 位置指针
        int subIndex = 0; // 指向 sub 字符位置指针

        int[] next = getNextArr(subArr);
        while (strIndex < strArr.length && subIndex < subArr.length) {
            if (strArr[strIndex] == subArr[subIndex]) {
                // 如果两个指针指向的位置的字符相等
                strIndex++;
                subIndex++;
            } else {
                // 不相等，1、sub 已经到达第一个字符 2、未到第一个字符
                if (next[subIndex] == -1) { // 已经到达第一个字符
                    // 在两指针指向的字符不相等的情况下，并且待匹配子串也处于第一个字符
                    // 说明就连第一个字符都不相等，那么母串下标+1，进行下一个字符匹配
                    strIndex++;
                } else { // 不是第一个字符，说明子串依然可以前移
                    subIndex = next[subIndex];
                }
            }
        }
        // subIndex 超过了长度，证明已经找到了
        return subIndex == subArr.length ? strIndex - subIndex : -1;
    }

    /**
     * 获取next[] , next[i] 表示 以 i 为观察点，0~i-1 范围内
     * 最长前缀和最长后缀长度
     * 最长前缀不包含最后一个字符
     * 最长后缀不包含第一个字符
     *
     * 0 1 2 3 4 5 6 7 8 9 10
     * a b a b c a b a b t k
     *
     * next[]
     * 0    1   2   3   4   5   6   7   8   9   10
     * -1   0
     * cur=2  pre=0
     * 当前 arr[cur-1]=arr[1]=b, arr[pre]=arr[0]=a, a!=b, 不相等，pre>0 不成立，所以cur++, 并将当前位置置为0
     * next[]
     * -1 0
     *
     *
     *
     *
     * next[]
     * 0    1   2   3   4   5   6   7   8   9   10
     * -1   0   0
     * cur=3  pre=0
     * 当前 arr[cur-1]=arr[2]=a, arr[pre]=arr[0]=a, a=a, 相等，cur++, pre++
     *
     *
     *
     *
     * next[]
     * 0    1   2   3   4   5   6   7   8   9   10
     * -1   0   0   1
     * cur=4  pre=1
     * 当前 arr[cur-1]=arr[3]=b, arr[pre]=arr[1]=b, b=b，相等，cur++, pre++
     *
     *
     *
     *
     * next[]
     * 0    1   2   3   4   5   6   7   8   9   10
     * -1   0   0   1   2
     * cur=5  pre=2
     * 当前 arr[cur-1]=arr[4]=c, arr[pre]=arr[2]=a, c!=a，不相等，pre=2>0 成立，可以继续往前跳，pre=next[pre]=next[2]=0
     *
     *
     *
     *
     * next[]
     * 0    1   2   3   4   5   6   7   8   9   10
     * -1   0   0   1   2
     * cur=5  pre=0
     * 当前 arr[cur-1]=arr[4]=c, arr[pre]=arr[0]=a, c!=a，不相等，pre=0>0 不成立，所以cur++, 并将当前位置置为0
     *
     *
     *
     *
     * next[]
     * 0    1   2   3   4   5   6   7   8   9   10
     * -1   0   0   1   2   0
     *
     * 求 k 位置最长匹配
     */
    public static int[] getNextArr(char[] sub) {
        if (sub.length == 1) return new int[]{-1};

        int[] next = new int[sub.length];
        next[0] = -1;
        next[1] = 0;
        int cur = 2; //  i 表示当前所求的位置
        int pre = 0; // pre 表示当前跳到的位置
        while (cur < next.length){
            if (sub[cur - 1] == sub[pre]) {
                // 如果当前位置 i 的前一个位置 i-1 和 跳到的位置相等
                // 长度就是跳到的位置 pre ，它对应的最长长度+1
                next[cur++] = ++pre;
            } else if (pre > 0) { // 不相等，但是还可以继续往前跳
                pre = next[pre];
            } else {
                next[cur++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String str = "ababcababtk";
        String sub = "bab";
        System.out.println(Arrays.toString(getNextArr(str.toCharArray())));
        System.out.println(kmp(str, sub));
    }
}
