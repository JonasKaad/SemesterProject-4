package dk.sdu.mmmi.modulemon.BundleController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BundleControllerUI extends JFrame {
    private BundleControllerService service;
    private JScrollPane bundleCheckboxPane;

    public BundleControllerUI(BundleControllerService service){
        this.service = service;
        setTitle("OSGi BundleController");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 600);

        JPanel contentpane = new JPanel();
        contentpane.setBorder(new EmptyBorder(5,5,5,5));
        contentpane.setLayout(new BoxLayout(contentpane, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Bundles:");
        title.setFont(new Font("Serif", Font.PLAIN, 16));
        contentpane.add(title);

        JButton button = new JButton("Update Bundles");
        button.addActionListener(e -> updateBundleList());
        contentpane.add(button);

        bundleCheckboxPane = new JScrollPane();

        updateBundleList();
        contentpane.add(bundleCheckboxPane);
        setContentPane(contentpane);
    }

    public void updateBundleList(){
        System.out.println("Should add new checkbox");
    }

    @Override
    public void dispose() {
        super.dispose();
        service.setClosed();
    }
}
