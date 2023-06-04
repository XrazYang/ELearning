package org.example.commendation;

import java.util.Arrays;
import java.util.List;

public class Utils {
    /**
     * 数据需要核对的位置：
     * 重点航线：
     * 杨艺迅3 无
     * 郑奇2  郑奇1
     * 普通航线：
     * 高雯铉1 <高雯炫名字打错>
     * 孙昊楠3 <孙浩楠名字打错>
     *
     * 目前需要人工核对数据：表扬信数量是否超过一百
     */

    public static void isSame() {
        //普通航线
        //String myStrs = "彭静2、贾欣悦3、李丽铭2、杨柳F1、陈晓芳2、陈盛跃2、冷晓悦2、舒瑞2、胡月琳2、李洋F2、吴海梅1、冯文怡2、李珊1、高惺惺2、陈晓娜5、高靖媛1、李萧琪1、杨钟化2、赵晓敏1、杜潇1、杨梅5、高杨1、段胜娟1、杨芙榕1、高雯铉1、邓思源1、毛芃森2、孙昊楠3";
        //String youStrs = "陈晓娜5、杨梅5、陈晓芳2、彭静2、冷晓悦2、冯文怡2、毛芃森2、高靖媛1、李萧琪1、段胜娟1、赵晓敏1、吴海梅1、杨钟化2、陈盛跃2、胡月琳2、李洋F2、舒瑞2、孙浩楠3、贾欣悦3、李丽铭2、李珊1、杨芙榕1、杨柳F1、高雯炫1、杜潇1、高杨1、王晔1、施睿琳1、令狐小娟1、李双慧1、高惺惺2、邓思源1";

        //重点航线
        String myStr = "刘立洋15、李灼丹15、庄明慧15、蒋竺芸11、彭静5、陈晓芳5、王帆C4、杨宏4、阎加玲4、赵茜B4、杨艺迅3、王一婷3、许晨3、余雪诗2、高凌雯2、郑奇2、张潘婷1、李明月1、杨舒涵1、刘梓璇1、石明鑫1、刘颖i1、吴俊谕1、陈倩倩1、刘璐1、杨杨1、普馨1、官源1、洪蕴娟1";
        String youStr = "蒋竺芸11、庄明慧15、刘立洋15、李灼丹15、陈晓芳5、官源1、普馨1、赵茜B4、杨杨1、王帆C4、王一婷3、彭静5、阎加玲4、张潘婷1、吴俊谕1、陈倩倩1、石明鑫1、杨舒涵1、刘颖i1、许晨3、杨宏4、杨艺讯3、余雪诗2、刘璐1、刘梓璇1、李明月1、洪蕴娟1、高凌雯2、郑奇1";

        List<String> myStrs = Arrays.asList(myStr.split("、"));
        List<String> youStrs = Arrays.asList(youStr.split("、"));

        youStrs.forEach(str -> {
            if (!myStrs.contains(str)) {
                System.out.println(str);
            }
        });
    }

    public static void main(String[] args) {
        isSame();
    }
}
