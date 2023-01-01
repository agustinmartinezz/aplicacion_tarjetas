package com.tarjetas.tarjetas.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.tarjetas.tarjetas.domain.Compra;
import javax.swing.JList;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PantallaCompras extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaCompras frame = new PantallaCompras();
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
	public PantallaCompras() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 200);
		contentPane = new JPanel();
		setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/appImage.png")));
		setTitle("Compras");
		contentPane.setBackground(new Color(175, 119, 234));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[5%][60%][10%][10%][10%][5%]", "[20%][20%][20%][20%][20%]"));
		
		JLabel lblVolver = new JLabel("");
		lblVolver.setIcon(new ImageIcon(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/arrow.png")));
		lblVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dispose();
					MenuPrincipal frame = new MenuPrincipal();
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
		contentPane.add(lblVolver, "cell 0 0,alignx center,aligny center");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		contentPane.add(comboBox, "cell 1 2,growx");
		
		JLabel lblModificar = new JLabel("");
		lblModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dispose();
					Compra compra = new Compra();
					IngModCompra frame = new IngModCompra(compra);
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
		lblModificar.setIcon(new ImageIcon(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/edit.png")));
		contentPane.add(lblModificar, "cell 2 2,alignx center");
		
		JLabel lblEliminar = new JLabel("");
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*LOGICA PARA ELIMINAR COMPRA SELECCIONADA*/
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
		lblEliminar.setIcon(new ImageIcon(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/delete.png")));
		contentPane.add(lblEliminar, "cell 3 2,alignx center");
		
		JLabel lblAgregar = new JLabel("");
		lblAgregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dispose();
					Compra compra = new Compra();
					IngModCompra frame = new IngModCompra(compra);
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
		lblAgregar.setIcon(new ImageIcon(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/add.png")));
		contentPane.add(lblAgregar, "cell 4 2,alignx center");
	}

}
