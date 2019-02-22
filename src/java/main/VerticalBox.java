/**
 * Created by Tarish Rhees on 2/10/2019.
 */
public class VerticalBox extends Box {

    @Override
    public String[] constructAndCollectAllBoxAndChildrenLines() {
        int totalNumberOfLines = numberOfVerticalEdges + 2;
        String[] boxPieces = new String[totalNumberOfLines];

        if (isEmpty()) {
            boxPieces[0] = "+-+";
            boxPieces[1] = "| |";
            boxPieces[2] = "+-+";
            return boxPieces;
        }

        //Not a leaf, so set this box's top and bottom line, which are always the same for this box
        String topAndBottomLine = buildTopAndBottomBoxLine();
        boxPieces[0] = topAndBottomLine;
        boxPieces[totalNumberOfLines - 1] = topAndBottomLine;

        int childCount = 0;
        int childTopPosition = 1;
        int childBottomPosition = 1;
        int childLineCount = 1;
        String[] nestedChild = null;
        StringBuilder line;
        String childLine = "";
        Box child = null;

        //Contstruct the intermediate lines (aka walls) for this box and its children
        for (int lineCount = 1; lineCount < totalNumberOfLines - 1; lineCount++) {
            line = new StringBuilder("| ");

            //Construct the child's portion of the line
            if (child == null || lineCount > childBottomPosition) {
                child = childrenBoxes.get(childCount);
            }

            int childHeight = child.getNumberOfVerticalEdges() + 2;
            childBottomPosition = childTopPosition + childHeight - 1;
            if (lineCount == 1 || lineCount == childTopPosition || lineCount == childBottomPosition) {
                childLine = child.buildTopAndBottomBoxLine();
            } else {
                if (child.isEmpty()) {
                    childLine = "| |";
                } else {
                    if (nestedChild == null) {
                        nestedChild = child.constructAndCollectAllBoxAndChildrenLines();
                    }
                    if (childLineCount < nestedChild.length) {
                        childLine = nestedChild[childLineCount - 1];
                    }
                }
            }
            childLine = addPaddingForThinnerBoxesBelowWiderBoxes(childLine, child);
            line.append(childLine);
            line.append(" |");
            boxPieces[lineCount] = line.toString();

            childLineCount++;

            //Go to next child and reset associated variables if all lines for this child are finished
            if (lineCount == childBottomPosition) {
                childCount++;
                childTopPosition = childBottomPosition + 1;
                childLineCount = 1;
                nestedChild = null;
            }
        }

        return boxPieces;
    }

    private String addPaddingForThinnerBoxesBelowWiderBoxes(String childLine, Box child) {
        int extraWidth = numberOfHorizontalEdges - child.getNumberOfHorizontalEdges();
        if (extraWidth > 4) {
            for (int i = 0; i < extraWidth - 4; i++) {
                childLine += " ";
            }
        }
        return childLine;
    }

    @Override
    public int findAndSetNumberOfVerticalEdgesNeededForDrawing() {
        int height = 0;

        if (isEmpty()) {
            numberOfVerticalEdges = 1;
            return 1;
        }

        for (Box child : childrenBoxes) {
            if (child.isEmpty()) {
                height += 3;
                numberOfVerticalEdges = height;
                continue;
            }
            int childHeight = child.findAndSetNumberOfVerticalEdgesNeededForDrawing();
            if (child instanceof VerticalBox) {
                height += childHeight + 2;
                numberOfVerticalEdges = height;
            }
            if (child instanceof HorizontalBox) {
                if (childHeight > height) {
                    height = childHeight + 2;
                } else {
                    height += childHeight + 2;
                }
                numberOfVerticalEdges = height;
            }
        }
        numberOfVerticalEdges = height;
        return height;
    }

    @Override
    public int findAndSetNumberOfHorizontalEdgesForDrawing() {
        int width = 0;

        if (isEmpty()) {
            numberOfHorizontalEdges = 1;
            return 1;
        }

        for (Box child : childrenBoxes) {
            if (child.isEmpty()) {
                if (width < 5) {
                    width += 5;
                    numberOfHorizontalEdges = width;
                }
                continue;
            }
            int childWidth = child.findAndSetNumberOfHorizontalEdgesForDrawing();
            if (childWidth > width) {
                width = childWidth + 4;
                numberOfHorizontalEdges = width;
            }
        }
        numberOfHorizontalEdges = width;
        return width;
    }
}
