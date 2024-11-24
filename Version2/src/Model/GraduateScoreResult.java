package Version2.src.Model;

public class GraduateScoreResult {
    public static double calculateGraduationScore(double mathScore, double literatureScore, double englishScore,
                                                  double avgCombinationScore, double tb12, double khuyenKhich, double doiTuong) {
        double totalScore = mathScore + literatureScore + englishScore + avgCombinationScore;
        double avgScore = (totalScore + khuyenKhich) / 4;
        return ((avgScore * 7) + (tb12 * 3)) / 10 + doiTuong;
    }
}
