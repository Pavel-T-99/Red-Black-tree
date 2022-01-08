package project;

import project.trees.RedBlackTree.RBNode;
import project.trees.RedBlackTree.RBTree;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.JFileChooser;
import java.io.File;


public class Viewer extends JPanel implements Serializable {


    private static final long serialVersionUID = 1250L;

    private RBTree<Integer> tree = new RBTree<Integer>();
    private panel treePanel = new panel(tree);
    JFileChooser j = new JFileChooser();
    public Viewer(){
        treePanel.setBackground(new Color(235, 225, 240));
        initViews();
    }

    private void setMidPoint(JScrollPane scrollPane){
        scrollPane.getViewport().setViewPosition(new Point(4100, 0));
    }

    private void setTopPanel(){
        JLabel info = new JLabel("RED-BLACK Tree app");
        info.setForeground(new Color(230,220,250));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70,70,70));
        panel.add(info);
        panel.setBorder(new EmptyBorder(10,10,10,10));
        add(panel, BorderLayout.NORTH);
    }/**set the top panel*/

    private void setBottomPanel(){
        final JTextField txtField = new JTextField(15);
        final JButton ins = new JButton();
        setupButton(ins, "add");
        final JButton del = new JButton();
        setupButton(del, "del");
        final JButton clr = new JButton();
        setupButton(clr, "clr");
        final JButton info = new JButton();
        setupButton(info, "search");
        final JButton saveFile = new JButton();
        setupButton(saveFile, "saveFile");
        final JButton open = new JButton();
        setupButton(open, "open");

        JPanel panel = new JPanel();
        panel.add(info);
        panel.add(ins);
        panel.add(txtField);
        panel.add(del);
        panel.add(clr);
        panel.add(saveFile);
        panel.add(open);
        panel.setBackground(Color.WHITE);
        add(panel, BorderLayout.SOUTH);
        /**add the buttons to the panel*/

        ins.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent actionEvent) {
                if (txtField.getText().equals(""))
                    return;

                Integer toInsert =Integer.parseInt (txtField.getText());
                if (tree.contains(toInsert)) {
                    JOptionPane.showMessageDialog(null, "Element is present");
                } else {
                    tree.insert(toInsert);
                    treePanel.repaint();
                    txtField.requestFocus();
                    txtField.selectAll();
                }

            }


        });/**insertion*/

        del.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent actionEvent){
                if(txtField.getText().equals(""))
                    return;

                Integer toDelete = Integer.parseInt (txtField.getText());
                if(!tree.contains(toDelete)){
                    JOptionPane.showMessageDialog(null, "Element is not in the tree");
                }else{
                    tree.remove(toDelete);
                    treePanel.repaint();
                    txtField.requestFocus();
                    txtField.selectAll();
                }
            }
        });/**deletion*/

        clr.addActionListener(new ActionListener(){


            public void actionPerformed(ActionEvent actionEvent) {

                if(tree.isEmpty())
                    JOptionPane.showMessageDialog(null, "Tree is empty");

                else
                    tree.clear();

                treePanel.setSearch(null);
                treePanel.repaint();
            }
        });/**clear the tree*/

        info.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent actionEvent){
                if(txtField.getText().equals(""))
                    return;

                Integer toSearch = Integer.parseInt (txtField.getText());
                if(!tree.contains(toSearch)){
                    JOptionPane.showMessageDialog(null, "No such element");
                }else{

                    treePanel.setSearch(toSearch);
                    treePanel.repaint();
                    txtField.requestFocus();
                    txtField.selectAll();
                }
            }
        });/**search for an element*/

        saveFile.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent){

                if(tree.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nothing to save!");
                }
                else{
                        try{
                        int result = j.showSaveDialog(null);
                         if(result == JFileChooser.APPROVE_OPTION){
                             File file = j.getSelectedFile();
                             FileOutputStream fos = new FileOutputStream(file);

                         ObjectOutputStream outputStream = new ObjectOutputStream(fos); {
                        outputStream.writeObject(tree);}

                         }
                        } catch (IOException ex) {
                        System.err.println(ex);
                    }


            }
        }});/**save a serialized file*/

        open.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               if(tree.isEmpty()) {
                   try {

                       int result = j.showOpenDialog(null);
                       if(result == JFileChooser.APPROVE_OPTION){
                           File file = j.getSelectedFile();
                           FileInputStream fis = new FileInputStream(file);
                           ObjectInputStream inputStream = new ObjectInputStream(fis);
                           {

                               RBTree toOpen = (RBTree) inputStream.readObject();

                               for (int i = 1; i <= toOpen.getSz(); i++) {
                                   tree.insert(Integer.valueOf(i));
                                   treePanel.repaint();
                                   txtField.requestFocus();
                                   txtField.selectAll();
                               }

                           }
                       } } catch (ClassNotFoundException ex) {
                       System.err.println("Class not found: " + ex);
                   } catch (IOException ex) {
                       System.err.println("IO error: " + ex);
                   }
               } else {JOptionPane.showMessageDialog(null,"You need to clear the tree");}

           }
        });/**open a serialized file*/


        txtField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent){
                ins.doClick();
            }
        });
    }/**text that is fetched by the user*/

    private void setScrollPane(){
        treePanel.setPreferredSize(new Dimension(9000,4096));

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(treePanel);
        scroll.setPreferredSize(new Dimension(750,900));
        setMidPoint(scroll);
        add(scroll, BorderLayout.CENTER);
    }/**setting the scroll pane*/

    private void initViews(){
        super.setLayout(new BorderLayout());
        setScrollPane();
        setTopPanel();
        setBottomPanel();
    }

    private void setupButton(JButton button, String imgSource){
        try{
            Image icon = ImageIO.read(getClass().getResource("/images/" + imgSource + ".png"));
            button.setIcon(new ImageIcon(icon));
            button.setBorderPainted(false);
            button.setFocusPainted(true);
            button.setContentAreaFilled(false);
        }catch(IOException e){
            e.printStackTrace();
        }
    }/**method to link the images to the logical buttons*/

    public static void main(String... args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
        }

        JFrame j = new JFrame();
        j.setTitle("Tree Viewer");/**title for the app*/

        try{
            j.setIconImage(ImageIO.read(Viewer.class.getResource("/images/ic_binary.png")));/**icon for the app*/
        }catch (IOException e){
            e.printStackTrace();
        }

        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.add(new Viewer());
        j.pack();
        j.setVisible(true);
    }
}/**main*/
