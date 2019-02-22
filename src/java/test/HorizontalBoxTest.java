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
public class HorizontalBoxTest {
    private final ByteArrayOutputStream capturedOutStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    //Object Under Test
    private HorizontalBox outerBox = new HorizontalBox();

    @Before
    public void setUpPrintOutStreamForContentCaptureForTest() {
        System.setOut(new PrintStream(capturedOutStream));
    }

    @Test
    public void findAndSetNumberVerticalAndHorizontalEdgesNeededForDrawingOnEachBox_HorizontalOuter() {
        Box innerBox01 = new VerticalBox();

        //Add two empty boxes to the second inner box
        Box innerBox02 = new HorizontalBox();
        innerBox02.add(new VerticalBox());
        innerBox02.add(new HorizontalBox());

        //Add second inner box and two empty boxes to first inner box
        innerBox01.add(innerBox02);
        innerBox01.add(new VerticalBox());
        innerBox01.add(new VerticalBox());

        //Add two empty boxes to the third inner box
        Box innerBox03 = new VerticalBox();
        innerBox03.add(new HorizontalBox());
        innerBox03.add(new HorizontalBox());

        //Add the first inner box, two empty boxes, and the last inner box to the outer box
        outerBox.add(innerBox01);
        outerBox.add(new HorizontalBox());
        outerBox.add(new VerticalBox());
        outerBox.add(innerBox03);

        //Find and set number of edges for each box
        outerBox.findAndSetNumberOfVerticalEdgesNeededForDrawing();
        outerBox.findAndSetNumberOfHorizontalEdgesForDrawing();

        assertThat("Vertical edges wrong", outerBox.getNumberOfVerticalEdges(), is(13));
        assertThat("Horizontal edges wrong", outerBox.getNumberOfHorizontalEdges(), is(33));

        assertThat("Vertical edges wrong", innerBox01.getNumberOfVerticalEdges(), is(11));
        assertThat("Horizontal edges wrong", innerBox01.getNumberOfHorizontalEdges(), is(13));

        assertThat("Vertical edges wrong", innerBox02.getNumberOfVerticalEdges(), is(3));
        assertThat("Horizontal edges wrong", innerBox02.getNumberOfHorizontalEdges(), is(9));

        assertThat("Vertical edges wrong", innerBox03.getNumberOfVerticalEdges(), is(6));
        assertThat("Horizontal edges wrong", innerBox03.getNumberOfHorizontalEdges(), is(5));

    }

    @Test
    public void drawsHorizontalEmptyBox_akaLeaf() {
        //Given a horizontal leaf
        outerBox = new HorizontalBox();

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
    public void drawsouterBoxAndOneHorizontalChildLeafBox() {
        outerBox = new HorizontalBox();
        outerBox.add(new HorizontalBox());

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
    public void drawsouterBoxAndMultipleChildrenLeafBoxes_Two() {
        outerBox = new HorizontalBox();
        outerBox.add(new VerticalBox());
        outerBox.add(new HorizontalBox());

        outerBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+---------+\n" +
                    "| +-+ +-+ |\n" +
                    "| | | | | |\n" +
                    "| +-+ +-+ |\n" +
                    "+---------+"
                )
        );
    }

    @Test
    public void drawsouterBoxAndMultipleHorizontalChildrenLeafBoxes_Three() {
        //Given a simple collection of nested horizontal boxes
        outerBox = new HorizontalBox();
        outerBox.add(new HorizontalBox());
        outerBox.add(new HorizontalBox());
        outerBox.add(new HorizontalBox());

        //When we draw the outer box and all its children
        outerBox.draw();

        //Then we get the following output
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+-------------+\n" +
                    "| +-+ +-+ +-+ |\n" +
                    "| | | | | | | |\n" +
                    "| +-+ +-+ +-+ |\n" +
                    "+-------------+"
                )
        );
    }

    @Test
    public void drawsoOuterBoxAndNestedChildBoxes_Two() {
        outerBox = new HorizontalBox();
        Box innerBox = new HorizontalBox();
        innerBox.add(new HorizontalBox());
        outerBox.add(innerBox);

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
    public void drawsouterBoxAndMultipleHorizontalComplexChildrenBoxes() {
        //Given a simple collection of nested horizontal boxes
        outerBox = new HorizontalBox();

        Box innerBox = new HorizontalBox();
        innerBox.add(new HorizontalBox());
        innerBox.add(new HorizontalBox());

        outerBox.add(innerBox);
        outerBox.add(new HorizontalBox());
        outerBox.add(new HorizontalBox());

        //When we draw the outer box and all its children
        outerBox.draw();

        //Then we get the following output
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+---------------------+\n" +
                    "| +---------+ +-+ +-+ |\n" +
                    "| | +-+ +-+ | | | | | |\n" +
                    "| | | | | | | +-+ +-+ |\n" +
                    "| | +-+ +-+ |         |\n" +
                    "| +---------+         |\n" +
                    "+---------------------+"
                )
        );
    }

    @After
    public void resetPrintOutStream() {
        System.setOut(originalOut);
    }
}