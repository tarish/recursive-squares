import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Tarish Rhees on 2/8/2019.
 */
public class BoxTest {
    private final ByteArrayOutputStream capturedOutStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    //ObjectUnderTest
    private Box outerVerticalBox = new VerticalBox();
    private Box outerHorizontalBox = new HorizontalBox();

    @Before
    public void setUpPrintOutStreamForContentCaptureForTest() {
        System.setOut(new PrintStream(capturedOutStream));
    }

    @Test
    public void addsChildBox() {
        Box innerBox = new HorizontalBox();
        outerVerticalBox.add(innerBox);
        assertThat(outerVerticalBox.getChildrenBoxes(), hasItem(innerBox));

        innerBox = new VerticalBox();
        outerHorizontalBox.add(innerBox);
        assertThat(outerHorizontalBox.getChildrenBoxes(), hasItem(innerBox));
    }

    @Test
    public void findsNumberOfEdgesOfOuterBoxAndSingleChildLeafBox_RegardlessOfDirectionOfLeaf() {
        //Outer Vertical / Inner Vertical
        outerVerticalBox.add(new VerticalBox());
        Box childBox = outerVerticalBox.getChildrenBoxes().get(0);
        assertThat("Vertical edges wrong", outerVerticalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(3));
        assertThat("Horizontal edges wrong", outerVerticalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(5));
        assertThat("Child Vertical edges wrong", childBox.getNumberOfVerticalEdges(), is(1));
        assertThat("Child Horizontal edges wrong", childBox.getNumberOfHorizontalEdges(), is(1));
        outerVerticalBox.getChildrenBoxes().clear();

        //Outer Vertical / Inner Horizontal
        outerVerticalBox.add(new HorizontalBox());
        childBox = outerVerticalBox.getChildrenBoxes().get(0);
        assertThat("Vertical edges wrong", outerVerticalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(3));
        assertThat("Horizontal edges wrong", outerVerticalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(5));
        assertThat("Child Vertical edges wrong", childBox.getNumberOfVerticalEdges(), is(1));
        assertThat("Child Horizontal edges wrong", childBox.getNumberOfHorizontalEdges(), is(1));
        outerVerticalBox.getChildrenBoxes().clear();

        //Outer Horizontal / Inner Horizontal
        outerHorizontalBox = new HorizontalBox();
        outerHorizontalBox.add(new HorizontalBox());
        childBox = outerHorizontalBox.getChildrenBoxes().get(0);
        assertThat("Vertical edges wrong", outerHorizontalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(3));
        assertThat("Horizontal edges wrong", outerHorizontalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(5));
        assertThat("Child Vertical edges wrong", childBox.getNumberOfVerticalEdges(), is(1));
        assertThat("Child Horizontal edges wrong", childBox.getNumberOfHorizontalEdges(), is(1));
        outerHorizontalBox.getChildrenBoxes().clear();

        //Outer Horizontal / Inner Vertical
        outerHorizontalBox = new HorizontalBox();
        outerHorizontalBox.add(new VerticalBox());
        childBox = outerHorizontalBox.getChildrenBoxes().get(0);
        assertThat("Vertical edges wrong", outerHorizontalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(3));
        assertThat("Horizontal edges wrong", outerHorizontalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(5));
        assertThat("Child Vertical edges wrong", childBox.getNumberOfVerticalEdges(), is(1));
        assertThat("Child Horizontal edges wrong", childBox.getNumberOfHorizontalEdges(), is(1));
    }

    @Test
    public void findsNumberOfEdgesOf_OuterHorizontalBox_AndChildBoxWithChildren() {
        //Child is Horizontal
        Box child = new HorizontalBox();
        Box firstGrandchild = new VerticalBox();
        Box secondGrandchild = new VerticalBox();

        child.add(firstGrandchild);
        child.add(secondGrandchild);
        outerHorizontalBox.add(child);

        assertThat("Vertical edges wrong", outerHorizontalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(5));
        assertThat("Horizontal edges wrong", outerHorizontalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(13));
        assertThat("Vertical edges wrong", child.getNumberOfVerticalEdges(), is(3));
        assertThat("Horizontal edges wrong", child.getNumberOfHorizontalEdges(), is(9));
        assertThat("Vertical edges wrong", firstGrandchild.getNumberOfVerticalEdges(), is(1));
        assertThat("Horizontal edges wrong", firstGrandchild.getNumberOfHorizontalEdges(), is(1));
        assertThat("Vertical edges wrong", secondGrandchild.getNumberOfVerticalEdges(), is(1));
        assertThat("Horizontal edges wrong", secondGrandchild.getNumberOfHorizontalEdges(), is(1));

        //Child is Vertical
        outerHorizontalBox.childrenBoxes.clear();
        child = new VerticalBox();
        firstGrandchild = new HorizontalBox();
        secondGrandchild = new HorizontalBox();

        child.add(firstGrandchild);
        child.add(secondGrandchild);
        outerHorizontalBox.add(child);

        assertThat("Vertical edges wrong", outerHorizontalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(8));
        assertThat("Horizontal edges wrong", outerHorizontalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(9));
        assertThat("Vertical edges wrong", child.getNumberOfVerticalEdges(), is(6));
        assertThat("Horizontal edges wrong", child.getNumberOfHorizontalEdges(), is(5));
        assertThat("Vertical edges wrong", firstGrandchild.getNumberOfVerticalEdges(), is(1));
        assertThat("Horizontal edges wrong", firstGrandchild.getNumberOfHorizontalEdges(), is(1));
        assertThat("Vertical edges wrong", secondGrandchild.getNumberOfVerticalEdges(), is(1));
        assertThat("Horizontal edges wrong", secondGrandchild.getNumberOfHorizontalEdges(), is(1));
    }

    @Test
    public void findsNumberOfEdgesOf_OuterVerticalBox_AndChildBoxWithChildren() {
        //Child is Horizontal
        Box child = new HorizontalBox();
        Box firstGrandchild = new VerticalBox();
        Box secondGrandchild = new VerticalBox();

        child.add(firstGrandchild);
        child.add(secondGrandchild);
        outerVerticalBox.add(child);

        assertThat("Vertical edges wrong", outerVerticalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(5));
        assertThat("Horizontal edges wrong", outerVerticalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(13));
        assertThat("Vertical edges wrong", child.getNumberOfVerticalEdges(), is(3));
        assertThat("Horizontal edges wrong", child.getNumberOfHorizontalEdges(), is(9));
        assertThat("Vertical edges wrong", firstGrandchild.getNumberOfVerticalEdges(), is(1));
        assertThat("Horizontal edges wrong", firstGrandchild.getNumberOfHorizontalEdges(), is(1));

        //Child is Vertical
        outerVerticalBox.childrenBoxes.clear();
        child = new VerticalBox();
        firstGrandchild = new HorizontalBox();
        secondGrandchild = new HorizontalBox();

        child.add(firstGrandchild);
        child.add(secondGrandchild);
        outerVerticalBox.add(child);

        assertThat("Vertical edges wrong", outerVerticalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(8));
        assertThat("Horizontal edges wrong", outerVerticalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(9));
        assertThat("Vertical edges wrong", child.getNumberOfVerticalEdges(), is(6));
        assertThat("Horizontal edges wrong", child.getNumberOfHorizontalEdges(), is(5));
        assertThat("Vertical edges wrong", firstGrandchild.getNumberOfVerticalEdges(), is(1));
        assertThat("Horizontal edges wrong", firstGrandchild.getNumberOfHorizontalEdges(), is(1));
    }

    @Test
    public void drawsSimpleMixedNestedBoxes_VerticalOuter() {
        Box firstChild = new HorizontalBox();
        firstChild.add(new HorizontalBox());
        firstChild.add(new VerticalBox());
        outerVerticalBox.add(firstChild);
        outerVerticalBox.add(new VerticalBox());
        outerVerticalBox.add(new HorizontalBox());
        assertThat("Vertical edges wrong", outerVerticalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(11));
        assertThat("Horizontal edges wrong", outerVerticalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(13));

        outerVerticalBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+-------------+\n" +
                    "| +---------+ |\n" +
                    "| | +-+ +-+ | |\n" +
                    "| | | | | | | |\n" +
                    "| | +-+ +-+ | |\n" +
                    "| +---------+ |\n" +
                    "| +-+         |\n" +
                    "| | |         |\n" +
                    "| +-+         |\n" +
                    "| +-+         |\n" +
                    "| | |         |\n" +
                    "| +-+         |\n" +
                    "+-------------+"
                )

        );
    }

    @Test
    public void drawsModerateMixedNestedBoxes_HorizontalOuter_MixedInner() {
        Box innerVertical = new VerticalBox();
        innerVertical.add(new VerticalBox());
        innerVertical.add(new VerticalBox());
        assertThat("InnerVertical Vertical edges wrong", innerVertical.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(6));
        assertThat("InnerVertical Horizontal edges wrong", innerVertical.findAndSetNumberOfHorizontalEdgesForDrawing(), is(5));

        Box innerHorizontal = new HorizontalBox();
        innerHorizontal.add(new HorizontalBox());
        innerHorizontal.add(new HorizontalBox());
        assertThat("InnerHorizontal Vertical edges wrong", innerHorizontal.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(3));
        assertThat("InnerHorizontal Horizontal edges wrong", innerHorizontal.findAndSetNumberOfHorizontalEdgesForDrawing(), is(9));

        outerHorizontalBox.add(innerVertical);
        outerHorizontalBox.add(innerHorizontal);

        assertThat("OuterHorizontal Vertical edges wrong", outerHorizontalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(8));
        assertThat("OuterHorizontal Horizontal edges wrong", outerHorizontalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(21));

        outerHorizontalBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+---------------------+\n" +
                    "| +-----+ +---------+ |\n" +
                    "| | +-+ | | +-+ +-+ | |\n" +
                    "| | | | | | | | | | | |\n" +
                    "| | +-+ | | +-+ +-+ | |\n" +
                    "| | +-+ | +---------+ |\n" +
                    "| | | | |             |\n" +
                    "| | +-+ |             |\n" +
                    "| +-----+             |\n" +
                    "+---------------------+"
                )
        );
    }

    @Test
    public void drawsModerateMixedNestedBoxes_VerticalOuter_MixedInner() {
        Box innerHorizontal = new HorizontalBox();
        innerHorizontal.add(new HorizontalBox());
        innerHorizontal.add(new HorizontalBox());
        assertThat("InnerHorizontal Vertical edges wrong", innerHorizontal.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(3));
        assertThat("InnerHorizontal Horizontal edges wrong", innerHorizontal.findAndSetNumberOfHorizontalEdgesForDrawing(), is(9));

        Box innerVertical = new VerticalBox();
        innerVertical.add(new VerticalBox());
        innerVertical.add(new VerticalBox());
        assertThat("InnerVertical Vertical edges wrong", innerVertical.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(6));
        assertThat("InnerVertical Horizontal edges wrong", innerVertical.findAndSetNumberOfHorizontalEdgesForDrawing(), is(5));

        outerVerticalBox.add(innerHorizontal);
        outerVerticalBox.add(innerVertical);

        assertThat("OuterHorizontal Vertical edges wrong", outerVerticalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(13));
        assertThat("OuterHorizontal Horizontal edges wrong", outerVerticalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(13));

        outerVerticalBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+-------------+\n" +
                    "| +---------+ |\n" +
                    "| | +-+ +-+ | |\n" +
                    "| | | | | | | |\n" +
                    "| | +-+ +-+ | |\n" +
                    "| +---------+ |\n" +
                    "| +-----+     |\n" +
                    "| | +-+ |     |\n" +
                    "| | | | |     |\n" +
                    "| | +-+ |     |\n" +
                    "| | +-+ |     |\n" +
                    "| | | | |     |\n" +
                    "| | +-+ |     |\n" +
                    "| +-----+     |\n" +
                    "+-------------+"
                )
        );
    }

    @Test
    public void drawsModerateHorizontalAndVerticalMixedOuterAndChildrenBoxes() {
        outerHorizontalBox = new HorizontalBox();
        Box innerVerticalBox01 = new VerticalBox();
        Box innerVerticalBox02 = new VerticalBox();
        Box innerVerticalBox03 = new VerticalBox();

        //Nest the boxes
        innerVerticalBox01.add(new VerticalBox());
        innerVerticalBox01.add(new VerticalBox());
        innerVerticalBox01.add(new VerticalBox());

        innerVerticalBox02.add(new HorizontalBox());
        innerVerticalBox02.add(new HorizontalBox());
        innerVerticalBox02.add(new HorizontalBox());

        innerVerticalBox03.add(new VerticalBox());
        innerVerticalBox03.add(new VerticalBox());
        innerVerticalBox03.add(new VerticalBox());

        outerHorizontalBox.add(innerVerticalBox01);
        outerHorizontalBox.add(innerVerticalBox02);
        outerHorizontalBox.add(innerVerticalBox03);

        //Draw the configuration
        outerHorizontalBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+-------------------------+\n" +
                    "| +-----+ +-----+ +-----+ |\n" +
                    "| | +-+ | | +-+ | | +-+ | |\n" +
                    "| | | | | | | | | | | | | |\n" +
                    "| | +-+ | | +-+ | | +-+ | |\n" +
                    "| | +-+ | | +-+ | | +-+ | |\n" +
                    "| | | | | | | | | | | | | |\n" +
                    "| | +-+ | | +-+ | | +-+ | |\n" +
                    "| | +-+ | | +-+ | | +-+ | |\n" +
                    "| | | | | | | | | | | | | |\n" +
                    "| | +-+ | | +-+ | | +-+ | |\n" +
                    "| +-----+ +-----+ +-----+ |\n" +
                    "+-------------------------+"
                )
        );
    }

    @Test
    public void drawsComplicatedMixedNestedBoxes_HorizontalOuter() {
        Box innerBox01 = new VerticalBox();

        //Add two empty boxes to the second inner box
        Box innerBox02 = new HorizontalBox();
        innerBox02.add(new VerticalBox());
        innerBox02.add(new VerticalBox());

        //Add second inner box and two empty boxes to first inner box
        innerBox01.add(innerBox02);
        innerBox01.add(new VerticalBox());
        innerBox01.add(new VerticalBox());

        //Add two empty boxes to the third inner box
        Box innerBox03 = new VerticalBox();
        innerBox03.add(new VerticalBox());
        innerBox03.add(new VerticalBox());

        //Add the first inner box, two empty boxes, and the last inner box to the outer box
        outerHorizontalBox.add(innerBox01);
        outerHorizontalBox.add(new VerticalBox());
        outerHorizontalBox.add(new VerticalBox());
        outerHorizontalBox.add(innerBox03);

        //Sanity check on outer box
        assertThat("Vertical edges wrong", outerHorizontalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(13));
        assertThat("Horizontal edges wrong", outerHorizontalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(33));

        outerHorizontalBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is (
                    "+---------------------------------+\n" +
                    "| +-------------+ +-+ +-+ +-----+ |\n" +
                    "| | +---------+ | | | | | | +-+ | |\n" +
                    "| | | +-+ +-+ | | +-+ +-+ | | | | |\n" +
                    "| | | | | | | | |         | +-+ | |\n" +
                    "| | | +-+ +-+ | |         | +-+ | |\n" +
                    "| | +---------+ |         | | | | |\n" +
                    "| | +-+         |         | +-+ | |\n" +
                    "| | | |         |         +-----+ |\n" +
                    "| | +-+         |                 |\n" +
                    "| | +-+         |                 |\n" +
                    "| | | |         |                 |\n" +
                    "| | +-+         |                 |\n" +
                    "| +-------------+                 |\n" +
                    "+---------------------------------+"
                )
        );
    }

    @Test
    public void drawsComplicatedNestedBoxes_VerticalOuter() {
        Box innerBox01 = new HorizontalBox();

        //Add two empty boxes to the second inner box
        Box innerBox02 = new VerticalBox();
        innerBox02.add(new VerticalBox());
        innerBox02.add(new VerticalBox());

        //Add second inner box and two empty boxes to first inner box
        innerBox01.add(innerBox02);
        innerBox01.add(new VerticalBox());
        innerBox01.add(new VerticalBox());

        //Add two empty boxes to the third inner box
        Box innerBox03 = new HorizontalBox();
        innerBox03.add(new VerticalBox());
        innerBox03.add(new VerticalBox());

        //Add the first inner box, two empty boxes, and the last inner box to the outer box
        outerVerticalBox.add(innerBox01);
        outerVerticalBox.add(new VerticalBox());
        outerVerticalBox.add(new VerticalBox());
        outerVerticalBox.add(innerBox03);

        //Sanity check on outer box
        assertThat("Vertical edges wrong", outerVerticalBox.findAndSetNumberOfVerticalEdgesNeededForDrawing(), is(21));
        assertThat("Horizontal edges wrong", outerVerticalBox.findAndSetNumberOfHorizontalEdgesForDrawing(), is(21));

        outerVerticalBox.draw();
        String capturedOutput = capturedOutStream.toString();
        assertThat(capturedOutput.trim(),
                is(
                    "+---------------------+\n" +
                    "| +-----------------+ |\n" +
                    "| | +-----+ +-+ +-+ | |\n" +
                    "| | | +-+ | | | | | | |\n" +
                    "| | | | | | +-+ +-+ | |\n" +
                    "| | | +-+ |         | |\n" +
                    "| | | +-+ |         | |\n" +
                    "| | | | | |         | |\n" +
                    "| | | +-+ |         | |\n" +
                    "| | +-----+         | |\n" +
                    "| +-----------------+ |\n" +
                    "| +-+                 |\n" +
                    "| | |                 |\n" +
                    "| +-+                 |\n" +
                    "| +-+                 |\n" +
                    "| | |                 |\n" +
                    "| +-+                 |\n" +
                    "| +---------+         |\n" +
                    "| | +-+ +-+ |         |\n" +
                    "| | | | | | |         |\n" +
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