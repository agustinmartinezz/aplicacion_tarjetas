package com.tarjetas.tarjetas.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tarjetas.tarjetas.domain.Compra;
import com.tarjetas.tarjetas.domain.Tienda;
import com.tarjetas.tarjetas.infrastructure.RestRepository;
import net.miginfocom.swing.MigLayout;
import org.springframework.web.client.RestTemplate;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tarjetas.tarjetas.gui.MenuPrincipal.*;
import static com.tarjetas.tarjetas.infrastructure.DependencyRestTemplate.newRestTemplate;

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
		RestTemplate restTemplate = newRestTemplate();
		RestRepository restRepository = new RestRepository(restTemplate);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		List<Tienda> tiendas = new ArrayList<Tienda>();
		List<Compra> comprasIngresadas = new ArrayList<Compra>();

		//Cargo las tiendas ingresadas
		try {
			tiendas = objectMapper.readValue(restRepository.getTiendas(), new TypeReference<>(){});
			comprasIngresadas = objectMapper.readValue(restRepository.getCompras(), new TypeReference<>(){});
		} catch (Exception e) {
			e.printStackTrace();
			//TODO: Desarrollar un popup de error
		}

		/*Variable auxiliar para poder usar el filter*/
		List<Compra> finalCompras = comprasIngresadas;
		/*-------------------------------------------*/

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
		lblVolver.setIcon(new ImageIcon(Objects.requireNonNull(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/arrow.png"))));
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

		JComboBox<String> cboCompras = new JComboBox<String>();
		cboCompras.setEditable(true);
		cboCompras.setMaximumSize(new Dimension(370,30));
		contentPane.add(cboCompras, "cell 1 2,growx");
		cargarComboBox(cboCompras, comprasIngresadas, tiendas);

		JLabel lblModificar = new JLabel("");
		lblModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//Obtengo el texto del item seleccionado del combo
					String compraTxt = (String) cboCompras.getSelectedItem();

					//Creo el frame de modificar con la compra seleccionada
					IngModCompra frame = new IngModCompra(getCompraSeleccionada(compraTxt, finalCompras));

					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					dispose();
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
		lblModificar.setIcon(new ImageIcon(Objects.requireNonNull(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/edit.png"))));
		contentPane.add(lblModificar, "cell 2 2,alignx center");

		JLabel lblEliminar = new JLabel("");
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Obtengo el texto del item seleccionado del combo
				String compraTxt = (String) cboCompras.getSelectedItem();

				int input = JOptionPane.showConfirmDialog(null,
						"Seguro que desea eliminar esta compra?", "Eliminar Compra",JOptionPane.OK_CANCEL_OPTION);

				if (input == 0) {
					restRepository.eliminarCompra(getCompraSeleccionada(compraTxt, finalCompras));

					JOptionPane.showMessageDialog(null,"Compra Eliminada Correctamente.","Atencion",JOptionPane.INFORMATION_MESSAGE);

					//Recargo el combo box con las compras
					try {
						List<Tienda> tiendasAux = objectMapper.readValue(restRepository.getTiendas(), new TypeReference<>(){});
						List<Compra> comprasIngresadasAux = objectMapper.readValue(restRepository.getCompras(), new TypeReference<>(){});

						cboCompras.removeAllItems();
						cargarComboBox(cboCompras, comprasIngresadasAux, tiendasAux);
					} catch (Exception e1) {
						e1.printStackTrace();
						//TODO: Desarrollar un popup de error
					}
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
		lblEliminar.setIcon(new ImageIcon(Objects.requireNonNull(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/delete.png"))));
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
		lblAgregar.setIcon(new ImageIcon(Objects.requireNonNull(PantallaCompras.class.getResource("/com/tarjetas/tarjetas/img/add.png"))));
		contentPane.add(lblAgregar, "cell 4 2,alignx center");
	}



	private void cargarComboBox(JComboBox comboBox, List<Compra> comprasIngresadas, List<Tienda> tiendas){
		//Recorro las compras ingresadas para cargarlas en el combo
		for (Compra compra : comprasIngresadas) {
			String item = compra.toString();

			String tiendaDescripcion = "";
			boolean encontre = false;
			int i = 0;

			//Busco el nombre de la tienda de la compra en la lista cargada al principio
			while (!encontre && i < tiendas.size()) {
				if (tiendas.get(i).getTiendaId() == compra.getTiendaId()) {
					encontre = true;
					tiendaDescripcion = tiendas.get(i).getTiendaNombre();
				} else {
					i++;
				}
			}

			item += " - " + tiendaDescripcion;
			comboBox.addItem(item);
		}
	}
}
