package me.laudoak.oakpark.widget.calendar.bizs.themes;

/**
 * Created by LaudOak on 2015-10-13 at 22:12.
 */
public class OakParkTheme extends DPTheme{

    /**
     * 月视图背景色
     *
     * Color of MonthView's background
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorBG() {
        return 0xFFFFFFFF;
    }


    /**
     * 背景圆颜色
     *
     * Color of MonthView's selected circle
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorBGCircle() {
        return 0x44000000;
    }


    /**
     * 标题栏背景色
     *
     * Color of TitleBar's background
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorTitleBG() {
        return 0xEEFFFFFF;
    }


    /**
     * 标题栏文本颜色
     *
     * Color of TitleBar text
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorTitle() {
        return 0x88F37B7A;
    }


    /**
     * 今天的背景色
     *
     * Color of Today's background
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorToday() {
        return 0x88F37B7A;
    }

    /**
     * 公历文本颜色
     *
     * Color of Gregorian text
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorG() {
        return 0xEE333333;
    }


    /**
     * 节日文本颜色
     *
     * Color of Festival text
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorF() {
        return 0xEEC08AA4;
    }

    /**
     * 周末文本颜色
     *
     * Color of Weekend text
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorWeekend() {
        return 0xEEF78082;
    }


    /**
     * 假期文本颜色
     *
     * Color of Holiday text
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorHoliday() {
        return 0x80FED6D6;
    }
}
