package com.tarjetas.tarjetas.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JYearChooser;
import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JDayChooser;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;

public class Totales extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Totales frame = new Totales();
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
	public Totales() {
		setTitle("Totales");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(175, 119, 234));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[18%][20%,grow][20%,grow][20%][18%]", "[16%][16%][10%][16%][16%][16%]"));
		
		JLabel lblVolver = new JLabel("");
		lblVolver.setIcon(new ImageIcon(Totales.class.getResource("/com/tarjetas/tarjetas/img/arrow.png")));
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
		contentPane.add(lblVolver, "cell 0 0,alignx left,aligny top");
		
		JComboBox comboBox = new JComboBox();
		contentPane.add(comboBox, "cell 1 1 3 1,growx");
		
		JMonthChooser monthChooser = new JMonthChooser();
		contentPane.add(monthChooser, "cell 1 2,grow");
		
		JYearChooser yearChooser = new JYearChooser();
		contentPane.add(yearChooser, "cell 2 2,grow");
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setFocusable(false);
		contentPane.add(btnConsultar, "cell 3 2");
		
		JLabel lblTituloTotal = new JLabel("Total");
		lblTituloTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(lblTituloTotal, "cell 1 4");
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		contentPane.add(textField, "cell 2 4,growx");
		textField.setColumns(10);
	}

}
