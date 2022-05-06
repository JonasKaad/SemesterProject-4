package dk.sdu.mmmi.modulemon.BundleController;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class BundleControllerUI extends JFrame {
    private BundleControllerUI _self;
    private BundleControllerService service;
    private BundleContext context;
    private boolean yoloMode = false;
    private JPanel contentPane;
    private JPanel bundlePanel;
    private JFileChooser fileChooser;

    private java.util.List<String> dynamicallyLoadUnloadBundles = Arrays.asList(
            "Battle",
            "Map",
            "Player",
            "NPC",
            "Interaction",
            "Collision"
    );

    public BundleControllerUI(BundleControllerService service) {
        _self = this;
        context = FrameworkUtil.getBundle(BundleControllerService.class).getBundleContext();
        this.service = service;
        setTitle("OSGi BundleController");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 750);
        setIconImage(Toolkit.getDefaultToolkit().createImage(new ResourceUtil("/icons/cat-icon.png").readBytes()));

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        contentPane.add(topPanel);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        JLabel lblBundles = new JLabel("Bundles:");
        topPanel.add(lblBundles);
        lblBundles.setFont(new Font("SansSerif", Font.BOLD, 20));

        Component horizontalGlue = Box.createHorizontalGlue();
        topPanel.add(horizontalGlue);

        JCheckBox chckbxAlwaysOnTop = new JCheckBox("Always on top");
        chckbxAlwaysOnTop.addActionListener(e -> setAlwaysOnTop(chckbxAlwaysOnTop.isSelected()));
        chckbxAlwaysOnTop.setSelected(true);
        topPanel.add(chckbxAlwaysOnTop);

        JCheckBox yoloCheckbox = new JCheckBox("YOLO MODE");
        topPanel.add(yoloCheckbox);
        yoloCheckbox.setSelected(yoloMode);
        yoloCheckbox.addActionListener(e -> {
            yoloMode = yoloCheckbox.isSelected();
            updateBundleList();
        });
        yoloCheckbox.setToolTipText("You only live once...");

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane);

        bundlePanel = new JPanel();
        scrollPane.setViewportView(bundlePanel);
        bundlePanel.setLayout(new BoxLayout(bundlePanel, BoxLayout.Y_AXIS));

        JPanel bottomPanel = new JPanel();
        contentPane.add(bottomPanel);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        JButton btnInstallNewBundle = new JButton("Install new bundle");
        btnInstallNewBundle.addActionListener(handleInstallBtnAction());

        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a bundle");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("OSGi bundles", "jar"));

        bottomPanel.add(btnInstallNewBundle);

        JButton btnRefreshBundles = new JButton("Refresh Bundles");
        btnRefreshBundles.addActionListener(e -> updateBundleList());
        bottomPanel.add(btnRefreshBundles);

        updateBundleList();
    }

    public void updateBundleList() {
        bundlePanel.removeAll();
        for (Bundle b : context.getBundles()) {
            bundlePanel.add(getBundleEntry(b));
        }
        bundlePanel.updateUI();
    }

    public ActionListener handleInstallBtnAction() {
        return e -> {
            int returnVal = fileChooser.showOpenDialog(_self);


            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File f = fileChooser.getSelectedFile();
            String filePath = f.getAbsolutePath();
            try {
                context.installBundle("file:///" + filePath);
            } catch (BundleException ex) {
                JOptionPane.showMessageDialog(_self, "Could not install this bundle!\n" + ex.getMessage());
            } finally {
                updateBundleList();
            }
        };
    }

    public JPanel getBundleEntry(Bundle bundle) {
        JPanel bundlePanel = new JPanel();
        bundlePanel.setLayout(new BoxLayout(bundlePanel, BoxLayout.X_AXIS));

        JCheckBox chckbxEnableBundle = new JCheckBox(String.format("%d - %s (%s)", bundle.getBundleId(), bundle.getSymbolicName(), getState(bundle.getState())));
        chckbxEnableBundle.setSelected(bundle.getState() == Bundle.ACTIVE);

        boolean shouldBeToggleable = this.dynamicallyLoadUnloadBundles.stream().anyMatch(x -> x.equalsIgnoreCase(bundle.getSymbolicName()))
                || yoloMode;

        Component horizontalGlue_1 = Box.createHorizontalGlue();
        JButton btnUninstall = new JButton("Uninstall");
        JButton btnLocation = new JButton("Location");
        if (!shouldBeToggleable) {
            chckbxEnableBundle.setEnabled(false);
            chckbxEnableBundle.setToolTipText("This bundle can not be dynamically loaded/unloaded.");
            btnUninstall.setEnabled(false);
            btnUninstall.setToolTipText("This bundle cannot be uninstalled.");
        }

        //Registering actions

        //Dynamically loading/unloading
        chckbxEnableBundle.addActionListener(e -> {
            try {
                if (chckbxEnableBundle.isSelected()) {
                    bundle.start();
                } else {
                    bundle.stop();
                }
            } catch (BundleException ex) {
                JOptionPane.showMessageDialog(_self, "A BundleException has occurred!\nIt's probably all broken now..");
                ex.printStackTrace();
            } finally {
                updateBundleList();
            }
        });

        //Uninstalling
        btnUninstall.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog(_self, "Are you sure you want to uninstall: " + bundle.getSymbolicName(), "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                try {
                    bundle.uninstall();
                } catch (BundleException ex) {
                    JOptionPane.showMessageDialog(_self, "A BundleException has occurred!\nIt's probably all broken now..");
                    ex.printStackTrace();
                } finally {
                    updateBundleList();
                }
            }
        });

        //Get location
        btnLocation.addActionListener(e -> {
            JOptionPane.showInputDialog(_self, String.format("Bundle Location for %s", bundle.getSymbolicName()), bundle.getLocation());
        });

        bundlePanel.add(chckbxEnableBundle);
        bundlePanel.add(horizontalGlue_1);
        bundlePanel.add(btnLocation);
        bundlePanel.add(btnUninstall);
        return bundlePanel;
    }

    private String getState(int state) {
        switch (state) {
            case Bundle.ACTIVE:
                return "Active";
            case Bundle.INSTALLED:
                return "Installed";
            case Bundle.STARTING:
                return "Starting";
            case Bundle.STOPPING:
                return "Stopping";
            case Bundle.UNINSTALLED:
                return "Uninstalled";
            case Bundle.RESOLVED:
                return "Resolved";
            default:
                return "Unknown";
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        service.setClosed();
    }
}
