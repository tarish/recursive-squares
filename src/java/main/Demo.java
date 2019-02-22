/**
 * Created by Tarish Rhees on 2/6/2019.
 */
public class Demo {
    public static void main(String[] args) {
        //Currently builds example #2 from readme.txt ("A more complicated example")
        //However, as requested in the readme, it can be easily edited to build other configurations
        Box outerBox00 = new HorizontalBox();
        Box innerBox01 = new VerticalBox();

        //Add two empty boxes to the third inner box
        Box innerBox03 = new HorizontalBox();
        innerBox03.add(new VerticalBox());
        innerBox03.add(new VerticalBox());

        //Add the third box and two empty boxes to the first inner box
        innerBox01.add(innerBox03);
        innerBox01.add(new HorizontalBox());
        innerBox01.add(new HorizontalBox());

        //Add two empty boxes to the second inner box
        Box innerBox02 = new VerticalBox();
        innerBox02.add(new HorizontalBox());
        innerBox02.add(new HorizontalBox());

        //Add the first inner box, two empty boxes, and the second inner box to the outermost box
        outerBox00.add(innerBox01);
        outerBox00.add(new VerticalBox());
        outerBox00.add(new VerticalBox());
        outerBox00.add(innerBox02);

        //Draw outer box and ALL children boxes contained within all the boxes
        outerBox00.draw();
    }
}
