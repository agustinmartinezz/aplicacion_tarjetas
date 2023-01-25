package com.tarjetas.tarjetas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import com.tarjetas.tarjetas.domain.Compra;
import com.tarjetas.tarjetas.domain.Tarjeta;
import com.tarjetas.tarjetas.domain.Tienda;
import com.tarjetas.tarjetas.gui.PantallaCompras;
import com.tarjetas.tarjetas.gui.PantallaTiendas;
import com.tarjetas.tarjetas.gui.Totales;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MenuPrincipal extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuPrincipal frame = new MenuPrincipal();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MenuPrincipal() {
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/com/tarjetas/tarjetas/img/appImage.png")));
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 410, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(175, 119, 234));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[20%][20%][20%][20%][20%]", "[16%][16%][16%][16%][16%][16%]"));

        JLabel lblTitulo = new JLabel("Menu Principal");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        contentPane.add(lblTitulo, "cell 2 0,alignx center,aligny center");

        JButton btnCompras = new JButton("Compras");
        btnCompras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                PantallaCompras frame = new PantallaCompras();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        btnCompras.setForeground(new Color(0, 0, 0));
        btnCompras.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCompras.setFocusable(false);
        contentPane.add(btnCompras, "cell 2 2,alignx center,aligny center");

        JButton btnTotales = new JButton("Totales");
        btnTotales.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                Totales frame = new Totales();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        btnTotales.setForeground(new Color(0, 0, 0));
        btnTotales.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTotales.setFocusable(false);
        contentPane.add(btnTotales, "cell 2 3,alignx center,aligny center");

        JButton btnTiendas = new JButton("Tiendas");
        btnTiendas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                PantallaTiendas frame = new PantallaTiendas();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        btnTiendas.setForeground(new Color(0, 0, 0));
        btnTiendas.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTiendas.setFocusable(false);
        contentPane.add(btnTiendas, "cell 2 4,alignx center,aligny center");
    }

    public static Compra getCompraSeleccionada(String compraTxt, List<Compra> compras) {
        //Busco en las compras ingresadas la que tiene el id de la que seleccione
        List<Compra> compra = compras.stream()
                .filter(t -> {
                    assert compraTxt != null;
                    return t.getCompraId() == Integer.parseInt(compraTxt.substring(0,compraTxt.indexOf(' ')));
                })
                .toList();

        return  compra.get(0);
    }

    public static Tienda getTiendaSeleccionada(String tiendaTxt, List<Tienda> tiendas) {
        //Busco en las tiendas ingresadas la que tiene el id de la que seleccione
        List<Tienda> tienda = tiendas.stream()
                .filter(t -> {
                    assert tiendaTxt != null;
                    return t.getTiendaId() == Integer.parseInt(tiendaTxt.substring(0,tiendaTxt.indexOf(' ')));
                })
                .toList();

        return tienda.get(0);
    }

    public static Tarjeta getTarjetaSeleccionada(String tarjetaTxt, List<Tarjeta> tarjetas) {
        //Busco en las tarjetas ingresadas la que tiene el id de la que seleccione
        List<Tarjeta> tarjeta = tarjetas.stream()
                .filter(t -> {
                    assert tarjetaTxt != null;
                    return t.getTarjetaId() == Integer.parseInt(tarjetaTxt.substring(0,tarjetaTxt.indexOf(' ')));
                })
                .toList();

        return tarjeta.get(0);
    }
}
