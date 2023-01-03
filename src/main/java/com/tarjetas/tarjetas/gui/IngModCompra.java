package com.tarjetas.tarjetas.gui;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import com.tarjetas.tarjetas.domain.Compra;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class IngModCompra extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JDateChooser dateChooser;
	private JLabel lblDescripcion;
	private JLabel lblMonto;
	private JLabel lblFecha;
	private JLabel lblCuotas;
	private JLabel lblTienda;
	private JLabel lblTarjeta;
	private JLabel lblTitulo;
	private JButton btnAccion;
	private JLabel lblVolver;

	/**
	 * Launch the application.
	*/
	
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IngModCompra frame = new IngModCompra(new Compra());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	*/
	public IngModCompra(Compra compra) {
		if (compra.getCompraId() != 0)
			setTitle("Modificar Compra");
		else
			setTitle("Ingresar Compra");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 360);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(IngModCompra.class.getResource("/com/tarjetas/tarjetas/img/appImage.png")));
		contentPane.setBackground(new Color(175, 119, 234));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[20%][20%][20%][20%][20%]", "[8.3%][8.3%][8.3%][8.3%][8.3%][8.3%][8.3%][8.3%][8.3%][8.3%][8.3%][8.3%]"));
		
		lblVolver = new JLabel("");
		lblVolver.setIcon(new ImageIcon(Objects.requireNonNull(IngModCompra.class.getResource("/com/tarjetas/tarjetas/img/arrow.png"))));
		lblVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dispose();
					PantallaCompras frame = new PantallaCompras();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
			    setCursor(cursor);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR); 
			    setCursor(cursor);
			}
		});
		contentPane.add(lblVolver, "cell 0 0");
		
		lblTitulo = new JLabel(this.getTitle());
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblTitulo, "cell 1 1 3 1,alignx center,aligny center");
		
		lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblDescripcion, "cell 1 3");
		
		textField = new JTextField();
		contentPane.add(textField, "cell 2 3,growx");
		textField.setColumns(10);
		
		lblMonto = new JLabel("Monto");
		lblMonto.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblMonto, "cell 1 4");
		
		textField_1 = new JTextField();
		contentPane.add(textField_1, "cell 2 4,growx");
		textField_1.setColumns(10);
		
		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblFecha, "cell 1 5");
		
		dateChooser = new JDateChooser();
		contentPane.add(dateChooser, "cell 2 5,growx");
		
		lblCuotas = new JLabel("Cuotas");
		lblCuotas.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblCuotas, "cell 1 6");
		
		textField_2 = new JTextField();
		contentPane.add(textField_2, "cell 2 6,growx");
		textField_2.setColumns(10);
		
		lblTienda = new JLabel("Tienda");
		lblTienda.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblTienda, "cell 1 7");
		
		JComboBox comboBox = new JComboBox();
		contentPane.add(comboBox, "cell 2 7 2 1,growx");
		
		lblTarjeta = new JLabel("Tarjeta");
		lblTarjeta.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblTarjeta, "cell 1 8");
		
		JComboBox comboBox_1 = new JComboBox();
		contentPane.add(comboBox_1, "cell 2 8 2 1,growx");
		
		btnAccion = new JButton("Ingresar");
		btnAccion.setFocusable(false);
		contentPane.add(btnAccion, "cell 2 10,alignx center,aligny center");
	}

}
