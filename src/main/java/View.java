import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

public class View extends JFrame implements Observer {

    private Model model;
    private JPanel masterPanel;
    private ShapeCanvas canvas;
    private ScrubPanel sP;

    public static boolean RIGHT_TO_LEFT = false;

    /**
     * Create a new View.
     */
    public View(Model model) {
        // Set up the window.
        Model.setIsSaved(true);
        this.setTitle("Paint");
        this.setMinimumSize(new Dimension(480, 360));
        this.setSize(800, 600);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        this.addWindowListener(new WindowAdapter() {
            //I skipped unused callbacks for readability

            @Override
            public void windowClosing(WindowEvent e) {
//                System.out.println("is everything saved?: " + Model.isIsSaved());
                if (!Model.isIsSaved()) {
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Would you like to save the current file?",
                            "Unsaved Changes Detected", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm != 0) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    } else {
                        JTextField filename = new JTextField();
                        JTextField dir = new JTextField();
                        JFileChooser iJChooser = new JFileChooser();

                        int iRval = iJChooser.showSaveDialog(View.this);
                        if (iRval == JFileChooser.APPROVE_OPTION) {
                            filename.setText(iJChooser.getSelectedFile().getName());
                            dir.setText(iJChooser.getCurrentDirectory().toString());
                            try {
                                FileOutputStream fileOut =
                                        new FileOutputStream(dir.getText() + "/" + filename.getText());
                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                out.writeObject(Model.getArrShape());
                                out.close();
                                fileOut.close();
                                Model.setIsSaved(true);
                            } catch (IOException i) {
                                Model.setIsSaved(false);
                                i.printStackTrace();
                            }
                        }
                        if (iRval == JFileChooser.CANCEL_OPTION) {
                            filename.setText("You pressed cancel");
                            dir.setText("");
                        }
                    }
                } else {
                    setVisible(false);
                    dispose();
                    System.exit(0);
                }
            }
        });


        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        model.addObserver(this);
//        pack();
        setVisible(true);


        // set layout on content pane
        Container content = this.getContentPane();
        masterPanel = new JPanel(new BorderLayout());
        content.add(masterPanel);
//        content.setLayout();


        // add componenets
        addMenuBar();
        addComponentsToPane(masterPanel);
    }

    public void addComponentsToPane(Container pane) {

        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(
                    java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }

        canvas = new ShapeCanvas();
        pane.add(canvas, BorderLayout.CENTER);

        pane.add(new LeftPallet(), BorderLayout.LINE_START);
        sP = new ScrubPanel(canvas);
        pane.add(sP, BorderLayout.PAGE_END);


//        button = new JButton("5 (LINE_END)");
//        pane.add(button, BorderLayout.LINE_END);
    }

    public void addMenuBar() {
        JMenuBar menuBar;
        JMenu menuFile, menuView;
        JMenuItem menuItemNew;
        JMenuItem menuItemOpen;
        JMenuItem menuItemClose;

        menuBar = new JMenuBar();

        //Build the first menu.
        menuFile = new JMenu("File");
//        menuView = new JMenu("View");
        menuBar.add(menuFile);
//        menuBar.add(menuView);

        //a group of JMenuItems
        menuItemNew = new JMenuItem("New drawing");
        menuItemOpen = new JMenuItem("Open from saved file");
        menuItemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!Model.isIsSaved()) {
                        int confirm = JOptionPane.showOptionDialog(
                                null, "Would you like to save the current file?",
                                "Unsaved Changes Detected", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, null, null);
                        if (confirm == 0) {
                            setVisible(false);
                            dispose();
                            System.exit(0);
                        } else {
                            JTextField filename = new JTextField();
                            JTextField dir = new JTextField();
                            JFileChooser iJChooser = new JFileChooser();

                            int iRval = iJChooser.showSaveDialog(View.this);
                            if (iRval == JFileChooser.APPROVE_OPTION) {
                                filename.setText(iJChooser.getSelectedFile().getName());
                                dir.setText(iJChooser.getCurrentDirectory().toString());
                                try {
                                    FileOutputStream fileOut =
                                            new FileOutputStream(dir.getText() + "/" + filename.getText());
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(Model.getArrShape());
                                    out.close();
                                    fileOut.close();
                                    Model.setIsSaved(true);
                                } catch (IOException i) {
                                    Model.setIsSaved(false);
                                    i.printStackTrace();
                                }
                            }
                            if (iRval == JFileChooser.CANCEL_OPTION) {
                                filename.setText("You pressed cancel");
                                dir.setText("");
                            }
                        }
                    }
                } catch (Exception exp) {
                    Model.setIsSaved(false);
                    exp.printStackTrace();
                }
                JFileChooser jChooser = new JFileChooser();
                int rVal = jChooser.showOpenDialog(View.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    try{
                        Model.getArrShape().clear();
                        FileInputStream fin = new FileInputStream(jChooser.getSelectedFile().getPath());
                        System.out.println("save file path" + jChooser.getSelectedFile().getPath());
                        ObjectInputStream ois = new ObjectInputStream(fin);
                        Model.setArrShape((ArrayList) ois.readObject());
                        sP.getScrollP().setMaximum(Model.getArrShape().size());
                        sP.getScrollP().setValue(Model.getArrShape().size());
                        Model.setMaxRenderAmt(Model.getArrShape().size());
                        ois.close();
                        fin.close();
//                        Model.getArrShape().add(readCase);
                    } catch (Exception i) {
                        i.printStackTrace();
                    }
                } else if (rVal == JFileChooser.CANCEL_OPTION) {
                }
            }
        });
        menuItemClose = new JMenuItem("Close current drawing");
        menuItemClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("is everything saved?: " + Model.isIsSaved());
                if (!Model.isIsSaved()) {
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Would you like to save the current file?",
                            "Unsaved Changes Detected", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm != 0) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    } else {
                        JTextField filename = new JTextField();
                        JTextField dir = new JTextField();
                        JFileChooser iJChooser = new JFileChooser();

                        int iRval = iJChooser.showSaveDialog(View.this);
                        if (iRval == JFileChooser.APPROVE_OPTION) {
                            filename.setText(iJChooser.getSelectedFile().getName());
                            dir.setText(iJChooser.getCurrentDirectory().toString());
                            try {
                                FileOutputStream fileOut =
                                        new FileOutputStream(dir.getText() + "/" + filename.getText());
                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                out.writeObject(Model.getArrShape());
                                out.close();
                                fileOut.close();
                                Model.setIsSaved(true);
                            } catch (IOException i) {
                                Model.setIsSaved(false);
                                i.printStackTrace();
                            }
                        }
                        if (iRval == JFileChooser.CANCEL_OPTION) {
                            filename.setText("You pressed cancel");
                            dir.setText("");
                        }
                    }
                } else {
                    setVisible(false);
                    dispose();
                    System.exit(0);
                }

            }
        });
        menuItemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("is everything saved?: " + Model.isIsSaved());
                if (!Model.isIsSaved()) {
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Would you like to save the current file?",
                            "Unsaved Changes Detected", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm != 0) {
                        Model.getArrShape().clear();
                        sP.getScrollP().setMaximum(1);
                        sP.getScrollP().setValue(1);
                        Model.setMaxRenderAmt(Model.getArrShape().size());
                    } else {
                        JTextField filename = new JTextField();
                        JTextField dir = new JTextField();
                        JFileChooser iJChooser = new JFileChooser();

                        int iRval = iJChooser.showSaveDialog(View.this);
                        if (iRval == JFileChooser.APPROVE_OPTION) {
                            filename.setText(iJChooser.getSelectedFile().getName());
                            dir.setText(iJChooser.getCurrentDirectory().toString());
                            try {
                                FileOutputStream fileOut =
                                        new FileOutputStream(dir.getText() + "/" + filename.getText());
                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                out.writeObject(Model.getArrShape());
                                out.close();
                                fileOut.close();
                                Model.setIsSaved(true);
                            } catch (IOException i) {
                                Model.setIsSaved(false);
                                i.printStackTrace();
                            }
                        }
                        if (iRval == JFileChooser.CANCEL_OPTION) {
                            filename.setText("You pressed cancel");
                            dir.setText("");
                        }
                    }
                } else {
                    Model.getArrShape().clear();
                    Model.setCurrentColor(Color.BLACK);
                    Model.setCurrentThickness(2.0f);
                    sP.getScrollP().setMaximum(1);
                    sP.getScrollP().setValue(1);
                    Model.setMaxRenderAmt(Model.getArrShape().size());
                    sP.getCanvas().repaint();
                }

            }
        });
        menuFile.add(menuItemNew);
        menuFile.add(menuItemOpen);
        menuFile.add(menuItemClose);
        setJMenuBar(menuBar);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes
        System.out.println("Model changed!");
    }

}
