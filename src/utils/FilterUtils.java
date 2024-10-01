package utils;

public class FilterUtils {
    public static boolean isYouTubeVideoLink(String url) {
        // Используем регулярное выражение для проверки формата URL-адреса видео на YouTube
        return url.matches("^https://www\\.youtube\\.com/watch\\?v=[a-zA-Z0-9]+$");
    }
}
