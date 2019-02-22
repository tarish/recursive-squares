import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarish Rhees on 2/6/2019.
 */
public abstract class Box {
    protected List<Box> childrenBoxes = new ArrayList<>(); //Initialize for null safety and faster reading
    protected int numberOfVerticalEdges = 1;
    protected int numberOfHorizontalEdges = 1;

    public void add(Box innerBox) {
        childrenBoxes.add(innerBox);
    }

    public void draw() {
        //Initialize all boxes with their required number of side walls (i.e. '|') and top/bottom walls (i.e. '-')
        findAndSetNumberOfHorizontalEdgesForDrawing();
        findAndSetNumberOfVerticalEdgesNeededForDrawing();

        //Construct and print all collected lines
        //  IN RETROSPECT: boxPieces should be a 2D array and passed into the construct method as a parameter
        //  Both adjustments would likely remove some of the code duplication and allow for more Polymorphism
        String[] boxPieces = constructAndCollectAllBoxAndChildrenLines();
        StringBuilder box = new StringBuilder();
        for (String piece : boxPieces) {
            box.append(piece);
            box.append("\n");
        }
        System.out.println(box);
    }

    public String buildTopAndBottomBoxLine() {
        StringBuilder builder = new StringBuilder("+");
        for (int i = 0; i < getNumberOfHorizontalEdges(); i++) {
            builder.append("-");
        }
        builder.append("+");
        return builder.toString();
    }

    //Also debated naming this 'isLeaf' but isEmpty seemed more clear to the language used in the spec (ie readme.txt)
    public boolean isEmpty() {
        return childrenBoxes.isEmpty();
    }

    public List<Box> getChildrenBoxes() {
        return childrenBoxes;
    }

    public int getNumberOfVerticalEdges() {
        return numberOfVerticalEdges;
    }

    public int getNumberOfHorizontalEdges() {
        return numberOfHorizontalEdges;
    }

    public abstract String[] constructAndCollectAllBoxAndChildrenLines();

    public abstract int findAndSetNumberOfVerticalEdgesNeededForDrawing();

    public abstract int findAndSetNumberOfHorizontalEdgesForDrawing();
}
