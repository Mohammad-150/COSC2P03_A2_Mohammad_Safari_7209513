import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DrugBank {

    private Drug[] drugs;       //array of drugs
    private BinaryNode root;    //root of the tree
    private FileWriter out;     //fileWriter to output a text file

    public DrugBank() {

        drugs = new Drug[1932]; //array of drugs

    }   //constructor

    /** This method recursively finds the depth of the deepest node in the tree.
     * @param node  passed node is root
     * @returns a number which is the height of the tree                        */
    public int depth2(BinaryNode node){
        //basically find the height of the tree
        if (node == null) {
            return -1;
        } else {
            int left = depth2(node.left);   //depth of left subtree
            int right = depth2(node.right); //depth of right subtree
            return 1 + Math.max(left, right); //max of left and right node + 1
        }
    }   //depth2

    /** This method recursively finds the depth of a node with a given key.
     * @param key   given key
     * @param node  passed node is root
     * @return depth of the node                                        */
    public int depth1(String key, BinaryNode node){
        if (node == null){
            return -1;
        }
        int d = Math.max(depth1(key, node.left), depth1(key, node.right));
        if (d >= 0)     //found in a subtree
            return d +1;
        else if(node.data.getDrugBankID() == Integer.parseInt(key.substring(2))) //not in subtree, but in root
            return 0;
        else            //not in tree
            return -1;
    }   //depth1

    /**This method traverses the BST in order meaning Traverse left
     * subtree, visit root, traverse right subtree.
     * @param node  root node.                                      */
    public void inOrderTraverse(BinaryNode node){
            if(node == null)
                return;
            else
            {
                inOrderTraverse(node.left);
                //node.displayNode();
                writeToFile(node);
                inOrderTraverse(node.right);
            }
    }   //inOrderTraverse

    /** This method outputs a text file in the order of the traversal.
     * Method overloading for inOrderTraverse          */
    public void inOrderTraverse(){
        try {
            out = new FileWriter("dockedApprovedSorted.tab");
            out.write("Generic Name\t"+"SMILES\t"+"Drug Bank ID\t"+"URL\t"+"Drug Groups\t"+"Score\n");

            inOrderTraverse(root);

            out.close();
        } catch (IOException ex) {
            System.out.println("An error occurred.");
        }
    }

    public void writeToFile(BinaryNode node){
        try {
            out.write( node.data.displayDrug() + "\n");
        } catch (IOException ex) {
            System.out.println("An error occurred.");
        }
    }   //writeToFile

    /** This method searches for a drug entry based on a given drugBankID.
     * @param key   DrugBankID which is String then convert it to int.
     * @param node  root node.                                          */
    public void search(String key, BinaryNode node){
        int ID = Integer.parseInt(key.substring(2)); //get only the integer part of the key

        if (root == null){
            System.out.println("Drug "+key+" can not be found!");
        }
        if (ID < node.data.getDrugBankID()) {       //left subtree
            search(key, node.left);
        } else if (ID > node.data.getDrugBankID()) {//right subtree
            search(key, node.right);
        } else {    //key == node.data.getDrugBankID() found it
            node.displayNode();
        }
    }   //search

    /** This method finds the smallest element - this must
     *  be the leftmost node in the entire tree.
     * @param T root node
     * @return  the leftmost node in the tree.           */
    public BinaryNode findMin(BinaryNode T){
        if (T == null)          //base case
            return null;
        else if(T.left == null) //base case
            return T;
        return findMin(T.left); //makes progress
    }   //findMin

    /** This method find the largest element - this must
     *  be the rightmost node in the entire tree.
     * @param T root node
     * @return  the rightmost node in the tree          */
    public BinaryNode findMax(BinaryNode T){
        //
        if (T == null)          //base case
            return null;
        else if(T.right == null) //base case
            return T;
        return findMax(T.right); //makes progress
    }   //findMax

    /** This method removes a node with a drug entry of the
     * given drugBankID from the binary search tree.
     * @param key   node to be deleted
     * @param node  root node
     * @return  node                                     */
    public BinaryNode delete(String key, BinaryNode node) {
        int ID = Integer.parseInt(key.substring(2)); //get only the int part of the key

        if (node == null) { //empty tree
            return null;
        }
        if (ID < node.data.getDrugBankID()) {           //it's in left subtree
            node.left = delete(key, node.left);         //del from left, update
        } else if (ID > node.data.getDrugBankID()) {    //it's in right subtree
            node.right = delete(key, node.right);       //del from right,update
        } else { // node.data.getDrugBankID == key
            //One way of doing it
            // One Child or Leaf Node (no children)
            System.out.println("Deleted drug: DB"+node.data.getDrugBankID());
            if (node.left == null){
                return node.right;
            } else if (node.right == null){
                return node.left;
            }
            // Two Children
            node.data = findMax(node.left).data;
            node.left = delete("DB"+String.valueOf(node.data.getDrugBankID()), node.left);


            /*
              //Second way of doing it
            if (node.left != null && node.right != null) {
                node.data = findMin(node.right).data;
                node.right = delete("DB"+String.valueOf(node.data.getDrugBankID()), node.right);
            } else { //T only has left or right subtree, or T is a leaf, update T
                node = (node.left != null) ? node.left : node.right;
            }
             */
        }
        return node;
    }   //delete

    /**This method inserts all drugs from the array into a
     * binary search tree using drugBankID as the key.       */
    public void create(){
        //to load the drugs into a BST
        for (int i=0; i<drugs.length; i++){
            insert(drugs[i], root);
        }
    }   //create

    /** This is a helper method to the create() method.
     * @param dr    drug to be inserted
     * @param node  root node                       */
    public void insert(Drug dr, BinaryNode node){
        if (root == null){  //empty tree
            root = new BinaryNode(null, dr, null);
        } else {
            if (dr.getDrugBankID() < node.data.getDrugBankID()) {   //if the new drugID is less than rootID
                if (node.left == null) {        //if the is no leftChild make newNode the leftChild
                    BinaryNode newNOde = new BinaryNode(null, dr, null);
                    node.left = newNOde;
                } else {
                    insert(dr, node.left);
                }
            } else if (dr.getDrugBankID() > node.data.getDrugBankID()) { //if the new drugID is greater than rootID
                if (node.right == null) {       //if the is no rightChild make newNode the rightChild
                    BinaryNode newNode = new BinaryNode(null, dr, null);
                    node.right = newNode;
                } else {
                    insert(dr, node.right);
                }
            }
        }
    }   //insert

    /**This method reads the data from the file
     * then sends each line to Drug class to be saved    */
    public void readData(){
        Drug med = null;

        try {
            File doc = new File("src/dockedApproved.tab");
            Scanner scan = new Scanner(doc);
            scan.nextLine();

            int counter = 0;
            while (scan.hasNextLine()) {
                String lineStr = scan.nextLine();
                med = new Drug(lineStr);
                drugs[counter] = med;
                counter++;
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }

    }   //readData

    public static void main(String[] args) {
        DrugBank db = new DrugBank();
        db.readData();
        db.create();
        db.inOrderTraverse();
        System.out.println("Depth of drug is: " + db.depth1("DB01050", db.root));
        System.out.println("Depth of deepest node is: " + db.depth2(db.root));
        db.search("DB01050",db.root);
        db.search("DB00316",db.root);
        db.delete("DB01065",db.root);
    }
}
