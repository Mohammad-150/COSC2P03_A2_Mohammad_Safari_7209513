public class BinaryNode {

    Drug data; //data stored in node
    BinaryNode left; //left node
    BinaryNode right; //right node

    public BinaryNode(BinaryNode l, Drug med, BinaryNode r){
        left = l;
        data = med;
        right = r;
    }

    //This method displays the node's data attributes
    public void displayNode(){
        System.out.println(data.displayDrug());
    }

}
