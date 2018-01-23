package uk.co.samsaam.javafx;

public class Quadtree {

    private Body body;     // body or aggregate body stored in this node
    private Quadrant quadrant;     // square region that the tree represents
    private Quadtree NW;     // tree representing northwest quadrant
    private Quadtree NE;     // tree representing northeast quadrant
    private Quadtree SW;     // tree representing southwest quadrant
    private Quadtree SE;     // tree representing southeast quadrant
  
    public Quadtree(Quadrant quadrant) { //constructor for the quadtree
        this.quadrant = quadrant;
        this.body = null;
        this.NW = null;
        this.NE = null;
        this.SW = null;
        this.SE = null;
    }
 
    public String toString() { //method to return a string of the quadtree's attributes
        return (" Body:" + body + ", NW:"+  NW+ ", NE:"+ NE+ ", SW:"+ SW+ ", SE:"+ SE);
      }

    public void insert(Body currentBody) {

        // if this node does not contain a body, put the new body b here
        if (body == null) {
            body = currentBody;
            return;
        }
  
        // internal node
        if (! isExternal()) {
            // update the center-of-mass and total mass
            body = body.addBody(currentBody);
        
            // recursively insert Body b into the appropriate quadrant
            placeBody(currentBody);
        }

        // external node
        else {
            // subdivide the region further by creating four children
            NW = new Quadtree(quadrant.NW());
            NE = new Quadtree(quadrant.NE());
            SE = new Quadtree(quadrant.SE());
            SW = new Quadtree(quadrant.SW());

            // recursively insert both this body and Body b into the appropriate quadrant
            placeBody(this.body);
            placeBody(currentBody);

            // update the center-of-mass and total mass
            body = body.addBody(currentBody);
        }
    }

    private void placeBody(Body b) {
        if (b.in(quadrant.NW()))
            NW.insert(b);
        else if (b.in(quadrant.NE()))
            NE.insert(b);
        else if (b.in(quadrant.SE()))
            SE.insert(b);
        else if (b.in(quadrant.SW()))
            SW.insert(b);
    }

    private boolean isExternal() {
        // a node is external iff all four children are null
        return (NW == null && NE == null && SW == null && SE == null);
    }

    public void updateForce(Body currentBody) {
    
        if (body == null || currentBody.equals(body)) {
            return;
        }
        // if the current node is external, update net force acting on b
        if (isExternal()) {
            currentBody.calculateForce(body);
        }
        // for internal nodes
        else {
    
            // width of region represented by internal node
            double s = quadrant.getQuadLength();

            // distance between Body b and this node's center-of-mass
            double d = body.distanceFrom(currentBody);
            //System.out.println("s/d: " + s/d);
            // compare ratio (s / d) to threshold value Theta
            if ((s / d) < Simulation.theta) {
                currentBody.calculateForce(body);   // b is far away
            }
            // recurse on each of current node's children
            else {
                NW.updateForce(currentBody);
                NE.updateForce(currentBody);
                SW.updateForce(currentBody);
                SE.updateForce(currentBody);
            }
        }
    }
}