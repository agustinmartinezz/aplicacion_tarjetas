package com.tarjetas.tarjetas.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.tarjetas.tarjetas.domain.Tienda;
import com.tarjetas.tarjetas.infrastructure.RestRepository;
import net.miginfocom.swing.MigLayout;
import org.springframework.web.client.RestTemplate;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import static com.tarjetas.tarjetas.infrastructure.DependencyRestTemplate.newRestTemplate;

public class IngModTienda extends JFrame {

	private JPanel contentPane;
	private JTextField txtTiendaNombre;

	/**
	 * Launch the application.
	 */
	
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IngModTienda frame = new IngModTienda(new Tienda());
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
	public IngModTienda(Tienda tienda) {
		if (tienda.getTiendaId() != 0)
			setTitle("Modificar Tienda");
		else
			setTitle("Ingresar Tienda");

		RestTemplate restTemplate = newRestTemplate();
		RestRepository restRepository = new RestRepository(restTemplate);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setIconImage(Toolkit.getDefaultToolkit().getImage(IngModTienda.class.getResource("/com/tarjetas/tarjetas/img/appImage.png")));
		contentPane.setBackground(new Color(175, 119, 234));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[20%][20%][20%][20%][20%]", "[25%][25%][25%][25%]"));
		
		JLabel lblVolver = new JLabel("");
		lblVolver.setIcon(new ImageIcon(Objects.requireNonNull(IngModTienda.class.getResource("/com/tarjetas/tarjetas/img/arrow.png"))));
		lblVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dispose();
					PantallaTiendas frame = new PantallaTiendas();
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
		
		JLabel lblTitulo = new JLabel(this.getTitle());
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblTitulo, "cell 1 0 3 1,alignx center,aligny center");
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNombre, "cell 1 1");

		txtTiendaNombre = new JTextField();
		contentPane.add(txtTiendaNombre, "cell 2 1 2 1,growx");
		txtTiendaNombre.setColumns(10);
		
		JButton btnAccion = new JButton("Ingresar");
		btnAccion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tienda.setTiendaNombre(txtTiendaNombre.getText());

				int input = JOptionPane.showConfirmDialog(null,
						"Nombre: " + txtTiendaNombre.getText(), "Confirmar Tienda",JOptionPane.OK_CANCEL_OPTION);

				if (input == 0) {
					if (tienda.getTiendaId() != 0) {
						restRepository.modificarTienda(tienda);
						JOptionPane.showMessageDialog(null,"Tienda Modificada Correctamente.","Atencion",JOptionPane.INFORMATION_MESSAGE);
					} else {
						restRepository.ingresarTienda(tienda);
						JOptionPane.showMessageDialog(null,"Tienda Ingresada Correctamente.","Atencion",JOptionPane.INFORMATION_MESSAGE);
					}

					dispose();
					PantallaTiendas frame = new PantallaTiendas();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			}
		});
		btnAccion.setFocusable(false);
		contentPane.add(btnAccion, "cell 2 2,alignx center,aligny center");

		if (tienda.getTiendaId() != 0) {
			txtTiendaNombre.setText(tienda.getTiendaNombre());
		}
	}
}
