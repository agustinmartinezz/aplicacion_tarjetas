package com.tarjetas.tarjetas.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.tarjetas.tarjetas.domain.Compra;

import net.miginfocom.swing.MigLayout;

public class PantallaTiendas extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaTiendas frame = new PantallaTiendas();
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
	public PantallaTiendas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 200);
		contentPane = new JPanel();
		setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/appImage.png")));
		setTitle("Tiendas");
		contentPane.setBackground(new Color(175, 119, 234));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[10%][60%][10%][10%][10%]", "[20%][20%][20%][20%][20%]"));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		contentPane.add(comboBox, "cell 1 2,growx");
		
		JLabel lblModificar = new JLabel("");
		lblModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Compra compra = new Compra();
					IngModCompra frame = new IngModCompra(compra);
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
		
		JLabel lblAgregar = new JLabel("");
		lblAgregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Compra compra = new Compra();
					IngModCompra frame = new IngModCompra(compra);
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
		contentPane.add(lblAgregar, "cell 3 2,alignx center");
	}

}
