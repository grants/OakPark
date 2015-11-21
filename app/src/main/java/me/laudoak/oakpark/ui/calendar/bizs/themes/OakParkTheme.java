package me.laudoak.oakpark.ui.calendar.bizs.themes;

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
        return 0xFFEFEF;
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
        return 0xFFEFEF ;
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
        return 0xFF000000;
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
        return 0xB4FF8800;
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
        return 0xFF3F3F3F;
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
        return 0xFFFF4081;
    }


    /**
     * 假期文本背景颜色
     *
     * Color of Holiday text
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorHoliday() {
        return 0xFFD3D3D3;
    }
}
