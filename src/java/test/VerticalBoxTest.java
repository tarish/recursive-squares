import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Tarish Rhees on 2/11/2019.
 */
public class VerticalBoxTest {
    private final ByteArrayOutputStream capturedOutStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    //Object Under Test
    private Box outerBox = new VerticalBox();

    @Before
    public void setUpPrintOutStreamForContentCaptureForTest() {
        System.setOut(new PrintStream(capturedOutStream));
    }

    @Test
    public void findAndSetNumberOfVerticalAndHorizontalEdgesNeededForDrawingOnEachBox_VerticalOuter() {
        Box innerBox01 = new HorizontalBox();

        //Add two empty boxes to the second inner box
        Box innerBox02 = new VerticalBox();
        innerBox02.add(new HorizontalBox());
        innerBox02.add(new VerticalBox());

        //Add second inner box and two empty boxes to first inner box
        innerBox01.add(innerBox02);
        innerBox01.add(new VerticalBox());
        innerBox01.add(new VerticalBox());

        //Add two empty boxes to the third inner box
        Box innerBox03 = new HorizontalBox();
        innerBox03.add(new VerticalBox());
        innerBox03.add(new HorizontalBox());

        //Add the first inner box, two empty boxes, and the last inner box to the outer box
        outerBox.add(innerBox01);
        outerBox.add(new HorizontalBox());
        outerBox.add(new HorizontalBox());
        outerBox.add(innerBox03);

        //Find and set number of edges for each box
        outerBox.findAndSetNumberOfVerticalEdgesNeededForDrawing();
        outerBox.findAndSetNumberOfHorizontalEdgesForDrawing();

        assertThat("Vertical edges wrong", outerBox.getNumberOfVerticalEdges(), is(21));
        assertThat("Horizontal edges wrong", outerBox.getNumberOfHorizontalEdges(), is(21));

        assertThat("Vertical edges wrong", innerBox01.getNumberOfVerticalEdges(), is(8));
        assertThat("Horizontal edges wrong", innerBox01.getNumberOfHorizontalEdges(), is(17));

        assertThat("Vertical edges wrong", innerBox02.getNumberOfVerticalEdges(), is(6));
        assertThat("Horizontal edges wrong", innerBox02.getNumberOfHorizontalEdges(), is(5));

        assertThat("Vertical edges wrong", innerBox03.getNumberOfVerticalEdges(), is(3));
        assertThat("Horizontal edges wrong", innerBox03.getNumberOfHorizontalEdges(), is(9));
    }

    @Test
    public void drawsVerticalEmptyBox_akaLeaf() {
        //Given a leaf
        outerBox = new VerticalBox();

        //When we draw the box
        outerBox.draw();

        //Then we get the following output
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                        "+-+\n" +
                        "| |\n" +
                        "+-+"
                )
        );
    }

    @Test
    public void drawsouterBoxAndOneVerticalChildLeafBox() {
        outerBox = new VerticalBox();
        outerBox.add(new VerticalBox());

        outerBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+-----+\n" +
                    "| +-+ |\n" +
                    "| | | |\n" +
                    "| +-+ |\n" +
                    "+-----+"
                )
        );
    }

    @Test
    public void drawsouterBoxAndMultipleVerticalChildrenBoxes() {
        outerBox.add(new VerticalBox());
        outerBox.add(new VerticalBox());
        outerBox.add(new VerticalBox());
        assertThat("Vertical edges wrong", outerBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(9));
        assertThat("Horizontal edges wrong", outerBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(5));

        outerBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+-----+\n" +
                    "| +-+ |\n" +
                    "| | | |\n" +
                    "| +-+ |\n" +
                    "| +-+ |\n" +
                    "| | | |\n" +
                    "| +-+ |\n" +
                    "| +-+ |\n" +
                    "| | | |\n" +
                    "| +-+ |\n" +
                    "+-----+"
                )
        );
    }

    @Test
    public void drawsoOuterBoxAndNestedChildBoxes_Two() {
        outerBox = new VerticalBox();
        Box innerBox = new VerticalBox();
        innerBox.add(new VerticalBox());
        outerBox.add(innerBox);
        assertThat("Vertical edges wrong", outerBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(5));
        assertThat("Horizontal edges wrong", outerBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(9));

        outerBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+---------+\n" +
                    "| +-----+ |\n" +
                    "| | +-+ | |\n" +
                    "| | | | | |\n" +
                    "| | +-+ | |\n" +
                    "| +-----+ |\n" +
                    "+---------+"
                )
        );
    }

    @Test
    public void drawsouterBoxAndMultipleVerticalComplexChildrenBoxes() {
        outerBox = new VerticalBox();

        Box innerBox = new VerticalBox();
        innerBox.add(new VerticalBox());
        innerBox.add(new VerticalBox());

        outerBox.add(innerBox);
        outerBox.add(new VerticalBox());
        outerBox.add(new VerticalBox());

        outerBox.draw();
        assertThat("Vertical edges wrong", outerBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(14));
        assertThat("Horizontal edges wrong", outerBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(9));
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+---------+\n" +
                    "| +-----+ |\n" +
                    "| | +-+ | |\n" +
                    "| | | | | |\n" +
                    "| | +-+ | |\n" +
                    "| | +-+ | |\n" +
                    "| | | | | |\n" +
                    "| | +-+ | |\n" +
                    "| +-----+ |\n" +
                    "| +-+     |\n" +
                    "| | |     |\n" +
                    "| +-+     |\n" +
                    "| +-+     |\n" +
                    "| | |     |\n" +
                    "| +-+     |\n" +
                    "+---------+"
                )
        );
    }

    @After
    public void resetPrintOutStream() {
        System.setOut(originalOut);
    }
}