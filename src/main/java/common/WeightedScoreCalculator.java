package common;

public class WeightedScoreCalculator {
    private static final double MAX_PACKAGE_NUM = 100;
    private static final double MAX_DISTANCE = 1000;
    private static final double WEIGHT_PACKAGE_NUM = 0.5;
    private static final double WEIGHT_DISTANCE = 0.5;

    public static double calculateWeightedScore(double packageNum, double distance) {
        // 如果 packageNum 超过100，返回一个极小的分数值
        if (packageNum > MAX_PACKAGE_NUM) {
            return Double.NEGATIVE_INFINITY;
        }

        double normalizedPackageNum = packageNum / MAX_PACKAGE_NUM;
        double normalizedDistance = distance / MAX_DISTANCE;

        double score = (1 - normalizedPackageNum) * WEIGHT_PACKAGE_NUM + (1 - normalizedDistance) * WEIGHT_DISTANCE;

        return score;
    }
}
