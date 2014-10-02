/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.ide;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.PlainTextChange;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;
import org.reactfx.EventStream;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class CodeEditorFactory {

    private static final String[] KEYWORDS = new String[]{
        "as", " abstract", "assert", "boolean", "break", "byte",
        "case", "catch", "char", "class", "const",
        "continue", "default", "do", "double", "else",
        "enum", "extends", "final", "finally", "float",
        "for", "goto", "if", "implements", "import",
        "instanceof", "int", "interface", "long", "native",
        "new", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super",
        "switch", "synchronized", "this", "throw", "throws",
        "transient", "try", "void", "volatile", "while"
    };

    private static final Pattern KEYWORD_PATTERN = Pattern.compile("\\b(" + String.join("|", KEYWORDS) + ")\\b");

//    public static VCodeEditor prepareEditor(VCodeEditor codeArea) {
////        VCodeEditor codeArea = new VCodeEditor();
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
//        EventStream<PlainTextChange> textChanges = codeArea.plainTextChanges();
//        textChanges
//                .successionEnds(Duration.ofMillis(500))
//                .supplyTask(() -> {
//                    return computeHighlightingAsync(codeArea, executor);
//                })
//                .awaitLatest(textChanges)
//                .subscribe((highlighting) -> {
//                    applyHighlighting(highlighting, codeArea);
//                });
//
//        // codeArea.replaceText(0, 0, sampleCode);
//        codeArea.getStylesheets().add(CodeEditorFactory.class.getResource("groovy-keywords.css").toExternalForm());
//        
//        return codeArea;
//    }
//
//    private static Task<StyleSpans<Collection<String>>> computeHighlightingAsync(VCodeEditor codeArea, ExecutorService executor) {
//        String text = codeArea.getText();
//        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
//            @Override
//            protected StyleSpans<Collection<String>> call() throws Exception {
//                return computeHighlighting(text);
//            }
//        };
//        executor.execute(task);
//        return task;
//    }
//
//    private static void applyHighlighting(StyleSpans<Collection<String>> highlighting, VCodeEditor codeArea) {
//        codeArea.setStyleSpans(0, highlighting);
//    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = KEYWORD_PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton("keyword"), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
