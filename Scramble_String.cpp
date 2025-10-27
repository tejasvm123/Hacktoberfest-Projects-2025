class Solution {
    bool isScrambleHelper(unordered_map<string, bool> &memo, string s1, string s2) {
        int i, len = s1.size();
        bool result = false;

        if (len == 0) {
            return true;
        } else if (len == 1) {
            return s1 == s2;
        } else {
            if (memo.count(s1 + s2)) {
                return memo[s1 + s2];
            }
            if (s1 == s2) {
                result = true;
            } else {
                for (i = 1; i < len && !result; ++i) {
                    result = result || (isScrambleHelper(memo, s1.substr(0, i), s2.substr(0, i)) && isScrambleHelper(memo, s1.substr(i, len - i), s2.substr(i, len - i)));

                    result = result || (isScrambleHelper(memo, s1.substr(0, i), s2.substr(len - i, i)) && isScrambleHelper(memo, s1.substr(i, len - i), s2.substr(0, len - i)));
                }
            }
            return memo[s1 + s2] = result;
        }
    }
public:
    bool isScramble(string s1, string s2) {
        unordered_map<string, bool> memo;
        return isScrambleHelper(memo, s1, s2);
    }
};
