package com.tarjetas.tarjetas.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tarjetas.tarjetas.MenuPrincipal;
import com.tarjetas.tarjetas.domain.*;
import com.tarjetas.tarjetas.infrastructure.RestRepository;
import net.miginfocom.swing.MigLayout;
import org.springframework.web.client.RestTemplate;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tarjetas.tarjetas.gui.IngModCompra.armarTarjetaDescripcion;
import static com.tarjetas.tarjetas.MenuPrincipal.*;
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
		List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
		List<Banco> bancos = new ArrayList<Banco>();
		List<Persona> personas = new ArrayList<Persona>();

		//Cargo las tiendas ingresadas
		try {
			tiendas = objectMapper.readValue(restRepository.getTiendas(), new TypeReference<>(){});
			comprasIngresadas = objectMapper.readValue(restRepository.getCompras(), new TypeReference<>(){});
			tarjetas = objectMapper.readValue(restRepository.getTarjetas(), new TypeReference<>(){});
			bancos = objectMapper.readValue(restRepository.getBancos(), new TypeReference<>(){});
			personas = objectMapper.readValue(restRepository.getPersonas(), new TypeReference<>(){});
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado", "Error", JOptionPane.ERROR_MESSAGE);
		}

		/*Variable auxiliar para poder usar el filter*/
		List<Compra> finalCompras = comprasIngresadas;
		List<Tienda> finalTiendas = tiendas;
		List<Tarjeta> finalTarjetas = tarjetas;
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
		contentPane.setLayout(new MigLayout("", "[5%][60%][10%][10%][10%][5%]", "[16%][16%][16%][16%][16%][16%]"));

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
		contentPane.add(lblVolver, "cell 0 0,alignx left,aligny center");

		JLabel lblTarjeta = new JLabel("Tarjeta");
		lblTarjeta.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblTarjeta, "cell 0 2,alignx trailing");

		FilterComboBox cboCompras = new FilterComboBox(comprasAsList(comprasIngresadas, tiendas, 0));
		cboCompras.setEditable(true);
		cboCompras.setMaximumSize(new Dimension(370,30));
		contentPane.add(cboCompras, "cell 1 3,growx");

		JComboBox cboTarjetas = new JComboBox();
		cboTarjetas.addItem("Todas");
		for (Tarjeta tarjeta : tarjetas) {
			cboTarjetas.addItem(armarTarjetaDescripcion(tarjeta,bancos,personas));
		}
		cboTarjetas.setEditable(false);
		cboTarjetas.setMaximumSize(new Dimension(370,30));
		cboTarjetas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//Con el itemStateChanged se producen dos eventos, deselect y select, uso el if para filtrar unicamente el select
				if (e.getStateChange() == 1) {
					String tarjetaSeleccionada = cboTarjetas.getSelectedItem().toString();

					int tarjetaId = 0;
					if (!tarjetaSeleccionada.equals("Todas")) {
						tarjetaId = getTarjetaSeleccionada(tarjetaSeleccionada, finalTarjetas).getTarjetaId();
					}

					cboCompras.removeAllItems();
					for (String compra : comprasAsList(finalCompras, finalTiendas, tarjetaId)) {
						cboCompras.addItem(compra);
					}
				}
			}
		});
		contentPane.add(cboTarjetas, "cell 1 2,growx");

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
		contentPane.add(lblModificar, "cell 2 3,alignx center");

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

					dispose();
					PantallaCompras frame = new PantallaCompras();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
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
		contentPane.add(lblEliminar, "cell 3 3,alignx center");

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
		contentPane.add(lblAgregar, "cell 4 3,alignx center");
	}

	private List<String> comprasAsList (List<Compra> comprasIngresadas, List<Tienda> tiendas, int tarjetaId) {
		List<String> compras = new ArrayList<>();
		compras.add("");

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

			if (tarjetaId != 0) {
				//Si vino filtro por tarjeta solo agrego aquellas compras que pertenecen a esa tarjeta
				if (tarjetaId == compra.getTarjetaId())
					compras.add(item);
			} else {
				//Si no vino filtro por tarjeta agrego todas
				compras.add(item);
			}
		}

		return compras;
	}
}
