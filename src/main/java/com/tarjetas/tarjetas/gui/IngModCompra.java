package com.tarjetas.tarjetas.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarjetas.tarjetas.domain.*;
import com.tarjetas.tarjetas.infrastructure.RestRepository;
import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.JDateChooser;
import org.springframework.web.client.RestTemplate;
import javax.swing.text.PlainDocument;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.tarjetas.tarjetas.MenuPrincipal.*;
import static com.tarjetas.tarjetas.infrastructure.DependencyRestTemplate.newRestTemplate;

public class IngModCompra extends JFrame {

	private JPanel contentPane;
	private JTextField txtDescripcion;
	private JTextField txtMonto;
	private JTextField txtCuotas;
	private JDateChooser dteFecha;
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

		RestTemplate restTemplate = newRestTemplate();
		RestRepository restRepository = new RestRepository(restTemplate);

		ObjectMapper objectMapper = new ObjectMapper();

		List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
		List<Banco> bancos = new ArrayList<Banco>();
		List<Persona> personas = new ArrayList<Persona>();
		List<Tienda> tiendas = new ArrayList<>();

		try {
			tiendas = objectMapper.readValue(restRepository.getTiendas(), new TypeReference<>(){});
			tarjetas = objectMapper.readValue(restRepository.getTarjetas(), new TypeReference<>(){});
			bancos = objectMapper.readValue(restRepository.getBancos(), new TypeReference<>(){});
			personas = objectMapper.readValue(restRepository.getPersonas(), new TypeReference<>(){});
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado", "Error", JOptionPane.ERROR_MESSAGE);
		}

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
		
		txtDescripcion = new JTextField();
		contentPane.add(txtDescripcion, "cell 2 3,growx");
		txtDescripcion.setColumns(10);
		
		lblMonto = new JLabel("Monto");
		lblMonto.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblMonto, "cell 1 4");
		
		txtMonto = new JTextField();
		contentPane.add(txtMonto, "cell 2 4,growx");
		txtMonto.setColumns(10);
		
		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblFecha, "cell 1 5");
		
		dteFecha = new JDateChooser();
		contentPane.add(dteFecha, "cell 2 5,growx");
		
		lblCuotas = new JLabel("Cuotas");
		lblCuotas.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblCuotas, "cell 1 6");
		
		txtCuotas = new JTextField();
		PlainDocument doc = (PlainDocument) txtCuotas.getDocument();
		contentPane.add(txtCuotas, "cell 2 6,growx");
		txtCuotas.setColumns(10);

		lblTienda = new JLabel("Tienda");
		lblTienda.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblTienda, "cell 1 7");
		
/*		JComboBox cboTiendas = new JComboBox();*/
		FilterComboBox cboTiendas = new FilterComboBox(tiendasAsString(tiendas));
		cboTiendas.setEditable(true);
		contentPane.add(cboTiendas, "cell 2 7 2 1,growx");
		
		lblTarjeta = new JLabel("Tarjeta");
		lblTarjeta.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblTarjeta, "cell 1 8");
		
		JComboBox cboTarjetas = new JComboBox();
		//Recorro las tarjetas  para cargarlas en el combo
		for (Tarjeta tarjeta : tarjetas) {
			cboTarjetas.addItem(armarTarjetaDescripcion(tarjeta,bancos,personas));
		}
		contentPane.add(cboTarjetas, "cell 2 8 2 1,growx");

		List<Tienda> finalTiendas = tiendas;
		List<Tarjeta> finalTarjetas = tarjetas;

		btnAccion = new JButton("Ingresar");
		btnAccion.setFocusable(false);
		btnAccion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					compra.setCompraDescripcion(txtDescripcion.getText());
					compra.setCompraMonto(Double.parseDouble(txtMonto.getText()));
					compra.setCompraFecha(dteFecha.getDate().toInstant()
							.atZone(ZoneId.systemDefault())
							.toLocalDate());
					compra.setCompraCuotas(Integer.parseInt(txtCuotas.getText()));

					Tienda tiendaSeleccionada = getTiendaSeleccionada((String) cboTiendas.getSelectedItem(), finalTiendas);
					compra.setTiendaId(tiendaSeleccionada.getTiendaId());

					Tarjeta tarjetaSeleccionada = getTarjetaSeleccionada((String) cboTarjetas.getSelectedItem(), finalTarjetas);
					compra.setTarjetaId(tarjetaSeleccionada.getTarjetaId());

					String msgConfirm = compra.getCompraDescripcion() + " \n$" + compra.getCompraMonto() + " \n" + compra.getCompraFecha() + " \nCuotas " + compra.getCompraCuotas() + " \n" + cboTiendas.getSelectedItem() + " \n" + cboTarjetas.getSelectedItem();

					int input = JOptionPane.showConfirmDialog(null,
							msgConfirm, "Confirmar Compra",JOptionPane.OK_CANCEL_OPTION);

					if (input == 0) {
						if (compra.getCompraId() != 0) {
							restRepository.modificarCompra(compra);
							JOptionPane.showMessageDialog(null,"Compra Modificada Correctamente.","Atencion",JOptionPane.INFORMATION_MESSAGE);
						} else {
							restRepository.ingresarCompra(compra);
							JOptionPane.showMessageDialog(null,"Compra Ingresada Correctamente.","Atencion",JOptionPane.INFORMATION_MESSAGE);
						}

						dispose();
						PantallaCompras frame = new PantallaCompras();
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnAccion, "cell 2 10,alignx center,aligny center");

		if (compra.getCompraId() != 0 ) {
			txtDescripcion.setText(compra.getCompraDescripcion());
			txtMonto.setText(String.valueOf(compra.getCompraMonto()));

			//CODIGO COPIADO PARA PASAR DE LOCALDATE A DATE
			ZoneId defaultZoneId = ZoneId.systemDefault();
			Date date = Date.from(compra.getCompraFecha().atStartOfDay(defaultZoneId).toInstant());
			dteFecha.setDate(date);

			txtCuotas.setText(String.valueOf(compra.getCompraCuotas()));

			List<Tienda> tienda = tiendas.stream()
					.filter(t -> {
						assert compra.getTiendaId() != 0;
						return t.getTiendaId() == compra.getTiendaId();
					})
					.toList();

			List<Tarjeta> tarjeta = tarjetas.stream()
					.filter(t -> {
						assert compra.getTarjetaId() != 0;
						return t.getTarjetaId() == compra.getTarjetaId();
					})
					.toList();

			cboTiendas.setSelectedItem(tienda.get(0).toString());
			cboTarjetas.setSelectedItem(armarTarjetaDescripcion(tarjeta.get(0),bancos,personas));
		}
	}

	static String armarTarjetaDescripcion(Tarjeta tarjeta, List<Banco> bancos, List<Persona> personas) {
		String tarjetaDescripcion = String.valueOf(tarjeta.getTarjetaId());

		boolean encontre = false;
		int i = 0;

		while (!encontre && i < bancos.size()) {
			if (bancos.get(i).getBancoId() == tarjeta.getBancoId()) {
				encontre = true;
				tarjetaDescripcion += " - " + bancos.get(i).getBancoNombre();
			} else {
				i++;
			}
		}

		encontre = false;
		i = 0;

		while (!encontre && i < personas.size()) {
			if (personas.get(i).getPersonaId() == tarjeta.getPersonaId()) {
				encontre = true;
				tarjetaDescripcion += " - " + personas.get(i).getPersonaNombre() + " " + personas.get(i).getPersonaApellido();
			} else {
				i++;
			}
		}

		return tarjetaDescripcion;
	}

	private List<String> tiendasAsString(List<Tienda> tiendasIngresadas) {
		List<String> tiendas = new ArrayList<>();
		tiendas.add("");

		//Recorro las tiendas ingresadas para cargarlas en el combo
		for (Tienda tienda : tiendasIngresadas) {
			tiendas.add(tienda.toString());
		}

		return tiendas;
	}
}
