package com.tarjetas.tarjetas.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarjetas.tarjetas.domain.Tienda;
import com.tarjetas.tarjetas.infrastructure.RestRepository;
import net.miginfocom.swing.MigLayout;
import org.springframework.web.client.RestTemplate;
import static com.tarjetas.tarjetas.infrastructure.DependencyRestTemplate.newRestTemplate;

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
		RestTemplate restTemplate = newRestTemplate();
		RestRepository restRepository = new RestRepository(restTemplate);

		ObjectMapper objectMapper = new ObjectMapper();

		List<Tienda> tiendasIngresadas = new ArrayList<>();
		try {
			//Busco las compras ingresadas
			tiendasIngresadas = objectMapper.readValue(restRepository.getTiendas(), new TypeReference<>(){});
		} catch (Exception e) {
			e.printStackTrace();
			//TODO: Desarrollar un popup de error
		}

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
		
		JLabel lblVolver = new JLabel("");
		lblVolver.setIcon(new ImageIcon(Objects.requireNonNull(PantallaTiendas.class.getResource("/com/tarjetas/tarjetas/img/arrow.png"))));
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
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setEditable(true);
		//Recorro las tiendas ingresadas para cargarlas en el combo
		for (Tienda tienda : tiendasIngresadas) {
			comboBox.addItem(tienda.toString());
		}
		contentPane.add(comboBox, "cell 1 2,growx");
		
		JLabel lblModificar = new JLabel("");
		/*Variable auxiliar para poder usar el filter*/
		List<Tienda> finalTiendas = tiendasIngresadas;
		/*-------------------------------------------*/
		lblModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//Obtengo el texto del item seleccionado del combo
					String text = (String) comboBox.getSelectedItem();

					//Busco en las compras ingresadas la que tiene el id de la que seleccione
					List<Tienda> tienda = finalTiendas.stream()
							.filter(t -> {
								assert text != null;
								return t.getTiendaId() == Integer.parseInt(text.substring(0,text.indexOf(' ')));
							})
							.toList();

					//Creo el frame de modificar con el objeto de la compra que seleccione
					IngModTienda frame = new IngModTienda(tienda.get(0));

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
		
		JLabel lblAgregar = new JLabel("");
		lblAgregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dispose();
					Tienda tienda = new Tienda();
					IngModTienda frame = new IngModTienda(tienda);
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
		contentPane.add(lblAgregar, "cell 3 2,alignx center");
	}
}
