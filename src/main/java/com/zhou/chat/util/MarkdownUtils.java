package com.zhou.chat.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownUtils {
    public static String markdownToHtml(String htmlString){
        // 使用 CommonMark 解析器解析 Markdown
        Parser parser = Parser.builder().build();
        Node document = parser.parse(htmlString);
        // 使用 CommonMark HTML 渲染器将 Markdown 转换为 HTML
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String htmlContent = renderer.render(document);
        return htmlContent;
    }
}
