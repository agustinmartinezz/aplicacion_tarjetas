package com.tarjetas.tarjetas.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tarjetas.tarjetas.MenuPrincipal;
import com.tarjetas.tarjetas.domain.Banco;
import com.tarjetas.tarjetas.domain.Compra;
import com.tarjetas.tarjetas.domain.Persona;
import com.tarjetas.tarjetas.domain.Tarjeta;
import com.tarjetas.tarjetas.infrastructure.RestRepository;
import com.toedter.calendar.JYearChooser;
import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.JMonthChooser;
import org.springframework.web.client.RestTemplate;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import static com.tarjetas.tarjetas.MenuPrincipal.getTarjetaSeleccionada;
import static com.tarjetas.tarjetas.infrastructure.DependencyRestTemplate.newRestTemplate;

public class Totales extends JFrame {

	private JPanel contentPane;
	private JTextField txtTotal;
	private JTable tblCompras;

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
		RestTemplate restTemplate = newRestTemplate();
		RestRepository restRepository = new RestRepository(restTemplate);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
		List<Banco> bancos = new ArrayList<Banco>();
		List<Persona> personas = new ArrayList<Persona>();

		try {
			tarjetas = objectMapper.readValue(restRepository.getTarjetas(), new TypeReference<>(){});
			bancos = objectMapper.readValue(restRepository.getBancos(), new TypeReference<>(){});
			personas = objectMapper.readValue(restRepository.getPersonas(), new TypeReference<>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("Totales");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 500);
		contentPane = new JPanel();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Totales.class.getResource("/com/tarjetas/tarjetas/img/appImage.png")));
		contentPane.setBackground(new Color(175, 119, 234));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[18%][20%,grow][20%,grow][20%][18%]", "[10%][7%][7%][10%][7%][9%][10%][10%][10%][10%][10%]"));

		JLabel lblVolver = new JLabel("");
		lblVolver.setIcon(new ImageIcon(Objects.requireNonNull(Totales.class.getResource("/com/tarjetas/tarjetas/img/arrow.png"))));
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

		JComboBox<String> cboTarjetas = new JComboBox<String>();
		//Recorro las tarjetas  para cargarlas en el combo
		for (Tarjeta tarjeta : tarjetas) {
			String tarjetaDescripcion = String.valueOf(tarjeta.getTarjetaId());

			boolean encontre = false;
			int i = 0;

			while (!encontre && i < bancos.size()) {
				if (bancos.get(i).getBancoId() == tarjeta.getBanco().getBancoId()) {
					encontre = true;
					tarjetaDescripcion += " - " + bancos.get(i).getBancoNombre();
				} else {
					i++;
				}
			}

			encontre = false;
			i = 0;

			while (!encontre && i < personas.size()) {
				if (personas.get(i).getPersonaId() == tarjeta.getPersona().getPersonaId()) {
					encontre = true;
					tarjetaDescripcion += " - " + personas.get(i).getPersonaNombre() + " " + personas.get(i).getPersonaApellido();
				} else {
					i++;
				}
			}

			cboTarjetas.addItem(tarjetaDescripcion);
		}
		contentPane.add(cboTarjetas, "cell 1 1 3 1,growx");

		JMonthChooser monthChooser = new JMonthChooser();
		contentPane.add(monthChooser, "cell 1 2,grow");

		JYearChooser yearChooser = new JYearChooser();
		contentPane.add(yearChooser, "cell 2 2,grow");

		DefaultTableModel tblModel = new DefaultTableModel();
		tblModel.addColumn("Id");
		tblModel.addColumn("Descripcion");
		tblModel.addColumn("Fecha");
		tblModel.addColumn("Total");
		tblModel.addColumn("Cuotas");
		tblModel.addColumn("Monto Cuota");
		tblCompras = new JTable(tblModel);
		tblCompras.setEnabled(false);
		JScrollPane pane = new JScrollPane(tblCompras);
		contentPane.add(pane, "cell 0 6 5 4,grow");

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setFocusable(false);
		List<Tarjeta> finalTarjetas = tarjetas;
		btnConsultar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String year = String.valueOf(yearChooser.getYear());
					String month = (monthChooser.getMonth() + 1) < 10 ? "0"+(monthChooser.getMonth() + 1) : String.valueOf(monthChooser.getMonth() + 1);
					LocalDate fechaConsulta = LocalDate.parse(year+"-"+month+"-01");

					//Cargo todas las compras que no estan eliminadas
					List<Compra> compras = objectMapper.readValue(restRepository.getCompras(), new TypeReference<>(){});

					//Cargo la tarjeta seleccionada del combo
					Tarjeta tarjetaSeleccionada = getTarjetaSeleccionada(cboTarjetas.getSelectedItem().toString(), finalTarjetas);

					List<Compra> comprasTarjeta = new ArrayList<>();
					compras.forEach(compra -> {
						if (compra.getTarjeta().getTarjetaId() == tarjetaSeleccionada.getTarjetaId()) {
							LocalDate primeraCuota;
							LocalDate ultimaCuota;

							if (compra.getCompraFecha().getDayOfMonth() > tarjetaSeleccionada.getTarjetaDiaCierre()) {
								primeraCuota = compra.getCompraFecha().plusMonths(1);
								primeraCuota = primeraCuota.withDayOfMonth(1);

								ultimaCuota = compra.getCompraFecha().plusMonths(compra.getCompraCuotas());
								ultimaCuota = ultimaCuota.withDayOfMonth(1);
							} else {
								primeraCuota = compra.getCompraFecha();
								primeraCuota = primeraCuota.withDayOfMonth(1);

								ultimaCuota = compra.getCompraFecha().plusMonths(compra.getCompraCuotas() - 1);
								ultimaCuota = ultimaCuota.withDayOfMonth(1);
							}

							if ((primeraCuota.isBefore(fechaConsulta) || primeraCuota.isEqual(fechaConsulta)) && (ultimaCuota.isAfter(fechaConsulta) || ultimaCuota.isEqual(fechaConsulta)))
								comprasTarjeta.add(compra);
						}
					});

					double totalFechaSeleccionada = 0.00;

					for (Compra compra : comprasTarjeta) {
						totalFechaSeleccionada += compra.getCompraMonto() / compra.getCompraCuotas();
					}

					txtTotal.setText(String.format("%.2f", totalFechaSeleccionada));

					cargarTabla(tblModel, comprasTarjeta);
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
		contentPane.add(btnConsultar, "cell 3 2");

		JLabel lblTituloTotal = new JLabel("Total");
		lblTituloTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(lblTituloTotal, "cell 1 4");

		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(txtTotal, "cell 2 4 2 1,growx");
		txtTotal.setColumns(10);
	}

	private void cargarTabla(DefaultTableModel tblModel, List<Compra> compras) {
		tblModel.setRowCount(0); //Limpio la tabla

		int x = 0;
		for (Compra compra : compras) {
			tblModel.addRow(new Vector<>());
			tblModel.setValueAt(compra.getCompraId(),x,0);
			tblModel.setValueAt(compra.getCompraDescripcion(),x,1);
			tblModel.setValueAt(compra.getCompraFecha(),x,2);
			tblModel.setValueAt(compra.getCompraMonto(),x,3);
			tblModel.setValueAt(compra.getCompraCuotas(),x,4);
			tblModel.setValueAt((compra.getCompraMonto() / compra.getCompraCuotas()),x,5);
			x++;
		}
	}
}
