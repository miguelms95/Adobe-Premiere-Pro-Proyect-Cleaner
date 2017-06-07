package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JViewport;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelCentro;
	private JPanel panelTop;
	private JLabel lblAdobePremierePro;
	private JPanel panelCentroTop;
	private JButton btSeleccionar;
	private JLabel lblSeleccionarDirectorioPara;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField txPathSeleccionado;
	private JButton btEjecutar;
	private final String DEFAULT_PATH = "J:\\";
	
	VentanaPrincipal vp;
	private ArrayList<File> listaArchivos = new ArrayList<File>();
	private ArrayList<File> listaNOEliminados = new ArrayList<File>();
	
	private JScrollPane scrollPane;
	private JTextArea txAreaLog;
	private int contadorCFA = 0;
	private int contadorPEK = 0;
	private int contadorAVI = 0;
	private int contadorNoEliminados = 0;
	private int tamCFA = 0;	// contador para calcular tama�os totales
	private int tamPEK = 0;
	private int tamAVI = 0;
	
	private JMenuBar menuBar;
	private JMenu mnAplicacin;
	private JMenu mnAyuda;
	private JMenuItem mntmSeleccionarDirectorio;
	private JMenuItem mntmEjecutar;
	private JSeparator separator;
	private JMenuItem mntmSalir;
	private JMenuItem mntmAcercaDe;
	private JMenu mnOpciones;
	private JCheckBoxMenuItem chckbxmntmMostrarRutaCompleta;
	private JButton btnSoloEscanear;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		vp = this;
		setTitle("Premiere Pro Cleaner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 783, 525);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelTop(), BorderLayout.NORTH);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
		
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getPanelCentroTop(), BorderLayout.NORTH);
			panelCentro.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panelCentro;
	}
	private JPanel getPanelTop() {
		if (panelTop == null) {
			panelTop = new JPanel();
			panelTop.add(getLblAdobePremierePro());
		}
		return panelTop;
	}
	private JLabel getLblAdobePremierePro() {
		if (lblAdobePremierePro == null) {
			lblAdobePremierePro = new JLabel("Premiere Pro Cleaner");
			lblAdobePremierePro.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblAdobePremierePro;
	}
	private JPanel getPanelCentroTop() {
		if (panelCentroTop == null) {
			panelCentroTop = new JPanel();
			panelCentroTop.setLayout(new BorderLayout(0, 0));
			panelCentroTop.add(getPanel_1());
			panelCentroTop.add(getPanel_2(), BorderLayout.SOUTH);
		}
		return panelCentroTop;
	}
	private JButton getBtSeleccionar() {
		if (btSeleccionar == null) {
			btSeleccionar = new JButton("Seleccionar directorio ");
			btSeleccionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					seleccionarDirectorio();
				}
			});
		}
		return btSeleccionar;
	}
	private JLabel getLblSeleccionarDirectorioPara() {
		if (lblSeleccionarDirectorioPara == null) {
			lblSeleccionarDirectorioPara = new JLabel("Seleccionar directorio para escanear y limpiar:");
		}
		return lblSeleccionarDirectorioPara;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.add(getLblSeleccionarDirectorioPara());
			panel_1.add(getBtSeleccionar());
		}
		return panel_1;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
			panel_2.add(getTxPathSeleccionado());
			panel_2.add(getBtEjecutar());
			panel_2.add(getBtnSoloEscanear());
		}
		return panel_2;
	}
	private JTextField getTxPathSeleccionado() {
		if (txPathSeleccionado == null) {
			txPathSeleccionado = new JTextField();
			txPathSeleccionado.setText(DEFAULT_PATH);
			txPathSeleccionado.setColumns(30);
		}
		return txPathSeleccionado;
	}
	private JButton getBtEjecutar() {
		if (btEjecutar == null) {
			btEjecutar = new JButton("Escanear y limpiar");
			btEjecutar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ejecutarPrograma();
					//System.err.println(txPathSeleccionado.getText());
					//rellenaListaArchivos();
				}
			});
			btEjecutar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return btEjecutar;
	}

	public void escanearArchivos(String directoryName) {
	    File directorio = new File(directoryName);
//    	System.out.println(directorio.getName());
	    if((directoryName.toString().equals(txPathSeleccionado.getText().toString())) || 
	    		(!directorio.isHidden() && !directorio.getName().startsWith("$"))){
	    	
	    	System.out.println("-> "+txPathSeleccionado.getText().toString() + " -- " + directoryName);
//	    	System.out.println(directoryName.toString().equals(txPathSeleccionado.getText().toString()));
//	    	System.out.println(directorio.isHidden());
//	    	System.out.println(directorio.getName().startsWith("$"));
	    	
	    	if(directorio.getName() == "RECYCLER")
	    		System.err.println("ENTRO EN CAMPO PELIGROSO. DEBUG.");
		    File[] fList = directorio.listFiles();
		    for (File file : fList) {
	    		String printName;
	        	if(chckbxmntmMostrarRutaCompleta.isSelected())
	        		printName = file.getAbsolutePath();
	        	else
	        		printName = file.getName();
	        	
		        if (file.isFile()) {
		        	if(printName.endsWith(".cfa")){
		        		contadorCFA += 1;
		        		tamCFA += file.length();
		        	}else if(printName.endsWith(".pek")){
		        		contadorPEK += 1;
		        		tamPEK += file.length();
		        	}else if(printName.startsWith("Rendered - ") && printName.endsWith(".AVI")){
		        		contadorAVI += 1;
		        		tamAVI += file.length();
		        	}
		        	if(printName.endsWith(".cfa") || printName.endsWith(".pek") || (printName.startsWith("Rendered - ") && printName.endsWith(".AVI"))){
		        		listaArchivos.add(file);
		        		txAreaLog.append("   "+printName+" - "+file.length()/1000000.0+" MB\n");
		        	}
		        	
		        }else if (file.isDirectory()) {
		        	if(printName.endsWith(".PRV")){
		        		if(txAreaLog.getText().equals(""))
		        			txAreaLog.append(""+printName+"\n");
		        		else
		        			txAreaLog.append("\n"+printName+"\n");
		        	}
		        	if(file.getName() == "RECYCLER")
			    		System.err.println("######## ENTRO EN CAMPO PELIGROSO. DEBUG. ########");
		        	escanearArchivos(file.getAbsolutePath());
		        }
		    } // fin for
	    }	// fin if recycler
	}
	
	private void eliminarArchivos(){
		for (File file : listaArchivos) {
			if(!file.delete()){
				listaNOEliminados.add(file);
				contadorNoEliminados += 1;
			}
		}
		if(contadorNoEliminados > 0){
			JOptionPane.showMessageDialog(vp, "No se han eliminado " + contadorNoEliminados + " archivos", "�Atenci�n!", JOptionPane.WARNING_MESSAGE);
			txAreaLog.append("\n *** �Atenci�n! No se han eliminado " + contadorNoEliminados + " archivos ***");
			for (File file : listaNOEliminados) {
				if(chckbxmntmMostrarRutaCompleta.isSelected())
					txAreaLog.append(file.getAbsolutePath() + "\n");
				else
					txAreaLog.append(file.getName() + "\n");
			}
		}
	}
	
	private void seleccionarDirectorio(){
		JFileChooser jf = new JFileChooser();
		jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		if(jf.showOpenDialog(vp) == jf.APPROVE_OPTION){
			txPathSeleccionado.setText(jf.getSelectedFile().getAbsolutePath());
			btEjecutar.setFont(new Font("Tahoma", Font.BOLD, 11));
			btEjecutar.setEnabled(true);
			btEjecutar.setForeground(Color.BLACK);
			btEjecutar.grabFocus();
		}
	}
	
	
	private void ejecutarPrograma(){
		File f = new File(txPathSeleccionado.getText().toString());
		if(f.exists()){
			txAreaLog.setText("");
			escanearArchivos(txPathSeleccionado.getText());
			printStats("Encontrado");
		}else{
			JOptionPane.showMessageDialog(vp, "�El directorio/archivo seleccionado: < "+txPathSeleccionado.getText()+" >  no existe!", "Error, no te inventes la ruta ;)", JOptionPane.ERROR_MESSAGE);
			btSeleccionar.grabFocus();
		}
	}
	
	private void printStats(String accion){
		int tamTotal = tamAVI + tamPEK +tamCFA;
		
		System.out.println("#### RESUMEN ARCHIVOS "+accion+" ###");
		System.out.println(contadorAVI + " archivos AVI - " + tamAVI + " Bytes = " + tamAVI/1000000000.0 + " GB");
		System.out.println(contadorPEK + " archivos PEK - " + tamPEK + " Bytes = " + tamPEK/1000000000.0 + " GB");
		System.out.println(contadorCFA + " archivos CFA - " + tamCFA + " Bytes = " + tamCFA/1000000000.0 + " GB");
		System.out.println("Espacio total: " + tamTotal + " Bytes = " + tamTotal/1000000000.0 + " GB");
		
		txAreaLog.append("\n###### RESUMEN ARCHIVOS "+accion+" ######\n");
		txAreaLog.append(contadorAVI + " archivos AVI - " + tamAVI + " Bytes = " + tamAVI/1000000000.0 + " GB\n");
		txAreaLog.append(contadorPEK + " archivos PEK - " + tamPEK + " Bytes = " + tamPEK/1000000000.0 + " GB\n");
		txAreaLog.append(contadorCFA + " archivos CFA - " + tamCFA + " Bytes = " + tamCFA/1000000000.0 + " GB\n\n");
		txAreaLog.append("Espacio total: " + tamTotal + " Bytes = " + tamTotal/1000000000.0 + " GB\n");
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTxAreaLog());
			scrollPane.add(txAreaLog);
			scrollPane.setViewportView(txAreaLog);
			
			
		}
		return scrollPane;
	}
	private JTextArea getTxAreaLog() {
		if (txAreaLog == null) {
			txAreaLog = new JTextArea();
			txAreaLog.setEditable(false);
		}
		return txAreaLog;
	}
	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnAplicacin());
			menuBar.add(getMnOpciones());
			menuBar.add(getMnAyuda());
		}
		return menuBar;
	}
	private JMenu getMnAplicacin() {
		if (mnAplicacin == null) {
			mnAplicacin = new JMenu("Aplicaci\u00F3n");
			mnAplicacin.add(getMntmSeleccionarDirectorio());
			mnAplicacin.add(getMntmEjecutar());
			mnAplicacin.add(getSeparator());
			mnAplicacin.add(getMntmSalir());
		}
		return mnAplicacin;
	}
	private JMenu getMnAyuda() {
		if (mnAyuda == null) {
			mnAyuda = new JMenu("Ayuda");
			mnAyuda.add(getMntmAcercaDe());
		}
		return mnAyuda;
	}
	private JMenuItem getMntmSeleccionarDirectorio() {
		if (mntmSeleccionarDirectorio == null) {
			mntmSeleccionarDirectorio = new JMenuItem("Seleccionar directorio...");
			mntmSeleccionarDirectorio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					seleccionarDirectorio();
				}
			});
		}
		return mntmSeleccionarDirectorio;
	}
	private JMenuItem getMntmEjecutar() {
		if (mntmEjecutar == null) {
			mntmEjecutar = new JMenuItem("Ejecutar");
			mntmEjecutar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ejecutarPrograma();
				}
			});
		}
		return mntmEjecutar;
	}
	
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}
	private JMenuItem getMntmSalir() {
		if (mntmSalir == null) {
			mntmSalir = new JMenuItem("Salir");
			mntmSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return mntmSalir;
	}
	private JMenuItem getMntmAcercaDe() {
		if (mntmAcercaDe == null) {
			mntmAcercaDe = new JMenuItem("Acerca de");
			mntmAcercaDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(vp, "Premiere Pro Cleaner v1.0\nSoftware desarrollado por Miguel Mart�nez Serrano 2017\nwww.miguelms.es","Informaci�n del programa",JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return mntmAcercaDe;
	}
	private JMenu getMnOpciones() {
		if (mnOpciones == null) {
			mnOpciones = new JMenu("Opciones");
			mnOpciones.add(getChckbxmntmMostrarRutaCompleta());
		}
		return mnOpciones;
	}
	private JCheckBoxMenuItem getChckbxmntmMostrarRutaCompleta() {
		if (chckbxmntmMostrarRutaCompleta == null) {
			chckbxmntmMostrarRutaCompleta = new JCheckBoxMenuItem("Mostrar ruta completa");
			chckbxmntmMostrarRutaCompleta.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {

					txAreaLog.setText("");
					ejecutarPrograma();
						
				}
			});
			chckbxmntmMostrarRutaCompleta.setToolTipText("Mostrar la ruta completa de archivos");
		}
		return chckbxmntmMostrarRutaCompleta;
	}
	private JButton getBtnSoloEscanear() {
		if (btnSoloEscanear == null) {
			btnSoloEscanear = new JButton("Solo escanear");
			btnSoloEscanear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ejecutarPrograma();
				}
			});
		}
		return btnSoloEscanear;
	}
}
