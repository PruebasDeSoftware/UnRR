package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import main.App;
import main.Device;
import main.UnRR;

import org.apache.commons.lang3.StringUtils;

import utils.ShellUtils;

public class UnRRGUI extends JFrame {

	private static String adbPath;
	private JPanel contentPane;
	private JTextField txtadb;
	private JTextField txtPaquete;
	private JButton btnIniciarLaGrabacin;
	private JButton btnDetenerGrabacin;
	private JButton btnReproducir;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblListaDispositivos;
	private JList<String> list;
	private DefaultListModel<String> deviceListModel;

	private UnRR unrr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the frame.
	 */
	public UnRRGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));

		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);

		lblListaDispositivos = new JLabel("Lista dispositivos");
		lblListaDispositivos.setBounds(57, 5, 124, 15);
		panel.add(lblListaDispositivos);

		deviceListModel = new DefaultListModel<String>();

		list = new JList<String>(new AbstractListModel() {
			String[] values = new String[] {};

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(67, 36, 114, 97);
		panel.add(list);

		panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblAdbPath = new JLabel("Ruta del Android ADB");
		panel_1.add(lblAdbPath);

		txtadb = new JTextField();
		panel_1.add(txtadb);
		txtadb.setText("adb");
		txtadb.setColumns(20);

		panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblApp = new JLabel("App");
		panel_2.add(lblApp);

		txtPaquete = new JTextField();
		panel_2.add(txtPaquete);
		txtPaquete.setText("com.rj.pixelesque/.PixelArtEditor");
		txtPaquete.setColumns(20);

		panel_3 = new JPanel();
		contentPane.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnIniciarLaGrabacin = new JButton("Iniciar la grabación");
		btnIniciarLaGrabacin.addMouseListener(new MouseAdapter() {



			@Override
			public void mouseClicked(MouseEvent e) {
				UnRRGUI.this.updateADBPath();
				unrr = new UnRR(UnRRGUI.this.getApp(), UnRRGUI.this);
				unrr.startRecording(UnRRGUI.this.getSelectedDevice());
			}
		});
		panel_3.add(btnIniciarLaGrabacin);

		btnDetenerGrabacin = new JButton("Detener grabación");
		btnDetenerGrabacin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				unrr.stopRecording();
			}
		});
		panel_3.add(btnDetenerGrabacin);

		btnReproducir = new JButton("Reproducir");
		btnReproducir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				unrr.replayOn(getSelectedDevice());
			}
		});
		panel_3.add(btnReproducir);

		updateADBPath();
		loadDeviceList();
	}

	protected void updateADBPath() {
		adbPath = txtadb.getText();

	}

	protected Device getSelectedDevice() {
		int index = list.getSelectedIndex();
		Device selectedDevice = Device.getDevice(deviceListModel.elementAt(index));
		return selectedDevice;
	}

	protected App getApp() {
		String[] tokens = StringUtils.split(txtPaquete.getText(), "/");
		return new App(tokens[0], tokens[1]);
	}

	private void loadDeviceList() {
		String response = ShellUtils.executeCommand(getPathToADB() + " devices");

		String[] lines = StringUtils.split(response, System.lineSeparator());

		boolean readMode = false;
		for (String line : lines) {

			if (line.contains("List of devices")) {
				readMode = true;
			} else if (readMode) {
				String serialNumberToken = StringUtils.split(line)[0];
				deviceListModel.addElement(serialNumberToken);
			}
		}
		list.setModel(deviceListModel);

	}

	public static String getPathToADB() {
		return adbPath;
	}
}
