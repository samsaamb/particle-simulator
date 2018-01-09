package uk.co.samsaam.javafx;

public class Quadtree {

    // threshold value
    private static final double Theta = 1;

    private Body body;     // body or aggregate body stored in this node
    private Quadrant quad;     // square region that the tree represents
    private Quadtree NW;     // tree representing northwest quadrant
    private Quadtree NE;     // tree representing northeast quadrant
    private Quadtree SW;     // tree representing southwest quadrant
    private Quadtree SE;     // tree representing southeast quadrant
  
    /**
     * Constructor: creates a new Barnes-Hut tree with no bodies. 
     * Each BHTree represents a quadrant and an aggregate body 
     * that represents all bodies inside the quadrant.
     *
     * @param q the quadrant this node is contained within
     */
    public Quadtree(Quadrant q) {
        this.quad = q;
        this.body = null;
        this.NW = null;
        this.NE = null;
        this.SW = null;
        this.SE = null;
    }
 

    /**
     * Adds the Body b to the invoking Barnes-Hut tree.
     */
    public void insert(Body b) {

        // if this node does not contain a body, put the new body b here
        if (body == null) {
            body = b;
            return;
        }
  
        // internal node
        if (! isExternal()) {
            // update the center-of-mass and total mass
            body = body.addBody(b);
        
            // recursively insert Body b into the appropriate quadrant
            putBody(b);
        }

        // external node
        else {
            // subdivide the region further by creating four children
            NW = new Quadtree(quad.NW());
            NE = new Quadtree(quad.NE());
            SE = new Quadtree(quad.SE());
            SW = new Quadtree(quad.SW());

            // recursively insert both this body and Body b into the appropriate quadrant
            putBody(this.body);
            putBody(b);

            // update the center-of-mass and total mass
            body = body.addBody(b);
        }
    }


    /**
     * Inserts a body into the appropriate quadrant.
     */ 
    private void putBody(Body b) {
        if (b.in(quad.NW()))
            NW.insert(b);
        else if (b.in(quad.NE()))
            NE.insert(b);
        else if (b.in(quad.SE()))
            SE.insert(b);
        else if (b.in(quad.SW()))
            SW.insert(b);
    }


    /**
     * Returns true iff this tree node is external.
     */
    private boolean isExternal() {
        // a node is external iff all four children are null
        return (NW == null && NE == null && SW == null && SE == null);
    }


    /**
     * Approximates the net force acting on Body b from all bodies
     * in the invoking Barnes-Hut tree, and updates b's force accordingly.
     */
    public void updateForce(Body b) {
    
        if (body == null || b.equals(body))
            return;

        // if the current node is external, update net force acting on b
        if (isExternal()) 
            b.calculateForce(body);
 
        // for internal nodes
        else {
    
            // width of region represented by internal node
            double s = quad.getQuadLength();

            // distance between Body b and this node's center-of-mass
            double d = body.distanceFrom(b);

            // compare ratio (s / d) to threshold value Theta
            if ((s / d) < Theta)
                b.calculateForce(body);   // b is far away
            
            // recurse on each of current node's children
            else {
                NW.updateForce(b);
                NE.updateForce(b);
                SW.updateForce(b);
                SE.updateForce(b);
            }
        }
    }
}
