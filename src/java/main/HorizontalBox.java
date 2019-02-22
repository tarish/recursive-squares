/**
 * Created by Tarish Rhees on 2/10/2019.
 */
public class HorizontalBox extends Box {
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

        StringBuilder line;
        Box child = null;

        String[] nestedChild = null;
        //Contstruct the intermediate lines for this box and its children
        for (int lineCount = 1; lineCount < totalNumberOfLines - 1; lineCount++) {
            line = new StringBuilder("| ");

            //Construct the child's portion of the line
            int numberOfChildren = childrenBoxes.size();
            String childLine = "";
            for (int childCount = 0; childCount < numberOfChildren; childCount++) {
                child = childrenBoxes.get(childCount);

                int childLineCount = child.getNumberOfVerticalEdges() + 2;
                if (lineCount == 1 || lineCount == childLineCount) {
                    childLine = child.buildTopAndBottomBoxLine();
                } else {
                    if (child.isEmpty()) {
                        childLine = "| |";
                    } else {
                        nestedChild = child.constructAndCollectAllBoxAndChildrenLines();
                        if (lineCount - 1 < nestedChild.length) {
                            childLine = nestedChild[lineCount - 1];
                        }
                    }
                }

                childLine = addPaddingForShorterBoxesBesideTallerBoxes(child, lineCount, childLine, childLineCount);
                line.append(childLine);
                addPaddingBetweenBoxWalls(line, numberOfChildren, childCount);
            }
            line.append(" |");
            boxPieces[lineCount] = line.toString();
        }

        return boxPieces;
    }

    private String addPaddingForShorterBoxesBesideTallerBoxes(Box child, int lineCount, String childLine, int childLineCount) {
        if (lineCount > childLineCount) {
            childLine = " "; //Replace old junk part of line with space
            for (int i = 0; i < child.getNumberOfHorizontalEdges() + 1; i++) {
                childLine += " ";
            }
        }
        return childLine;
    }

    private void addPaddingBetweenBoxWalls(StringBuilder line, int numberOfBoxes, int currentBoxCount) {
        boolean isFirstChild = currentBoxCount == 0 && numberOfBoxes > 1;
        boolean isLastChild = currentBoxCount == numberOfBoxes - 1;
        boolean isMiddleChild = !isFirstChild && !isLastChild;
        if (isFirstChild || isMiddleChild) {
            line.append(" ");
        }
    }

    @Override
    public int findAndSetNumberOfVerticalEdgesNeededForDrawing() {
        int height = 0;

        //Base case - if this Box is empty (i.e. a Leaf, if you think about the nested boxes as a tree)
        if (isEmpty()) {
            numberOfVerticalEdges = 1;
            return 1;
        }

        for (Box child : childrenBoxes) {
            if (child.isEmpty()) {
                if (height < 3) {
                    height += 3;
                    numberOfVerticalEdges = height;
                }
                continue;
            }
            int childHeight = child.findAndSetNumberOfVerticalEdgesNeededForDrawing();
            if (childHeight > height) {
                height = childHeight + 2;
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
                width += (width < 5) ? 5 : 4;
                numberOfHorizontalEdges = width;
            } else {
                int childWidth = child.findAndSetNumberOfHorizontalEdgesForDrawing();
                //TODO: More often than not, using instanceof is a code smell. As mentioned in the Box class
                // comments, I'd design differently in the future, and this design difference would likely
                // allow more Polymorphism to take care of things rather than using instanceof checks
                if (child instanceof VerticalBox) {
                    if (childWidth > width) {
                        width = childWidth + 4;
                    } else {
                        width += childWidth + 3;
                    }
                    numberOfHorizontalEdges = width;
                }
                if (child instanceof HorizontalBox) {
                    if (childWidth > width) {
                        width += childWidth + 4;
                    } else {
                        width += childWidth + 3;
                    }
                    numberOfHorizontalEdges = width;
                }
            }
        }
        numberOfHorizontalEdges = width;
        return width;
    }
}
