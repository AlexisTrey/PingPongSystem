package co.edu.uptc.view;

import co.edu.uptc.components.button.CustomButton;
import co.edu.uptc.components.dialog.AboutDialog;
import co.edu.uptc.components.fonts.AppFonts;
import co.edu.uptc.config.AppConfig;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.pojo.Ball;
import co.edu.uptc.pojo.Racket;
import co.edu.uptc.util.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameFrame extends JFrame implements ViewInterface {

    private static GameFrame instance;

    private PresenterInterface presenter;
    private GamePanel gamePanel;

    private JLabel labelStartTime;
    private JLabel labelElapsed;
    private JTextArea textAreaBounces;

    private GameFrame() {
        initFrame();
        addComponents();
    }

    public static GameFrame getInstance() {
        if (instance == null) {
            instance = new GameFrame();
        }
        return instance;
    }

    private void initFrame() {
        setTitle("Ping Pong");
        setSize(AppConfig.FRAME_WIDTH, AppConfig.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setResizable(false);
    }

    private void addComponents() {
        addMenuBar();
        addGamePanel();
        addInfoPanel();
        addKeyListeners();
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuJuego = new JMenu("Juego");
        JMenu menuTema  = new JMenu("Cambiar tema");

        JMenuItem itemClaro  = new JMenuItem("Claro");
        JMenuItem itemOscuro = new JMenuItem("Oscuro");

        itemClaro.addActionListener(e  -> ThemeManager.applyByKey(ThemeManager.LIGHT));
        itemOscuro.addActionListener(e -> ThemeManager.applyByKey(ThemeManager.DARK));

        menuTema.add(itemClaro);
        menuTema.add(itemOscuro);

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));

        menuJuego.add(menuTema);
        menuJuego.addSeparator();
        menuJuego.add(itemSalir);

        JMenu menuInfo     = new JMenu("Acerca de");
        JMenuItem itemInfo = new JMenuItem("Info del proyecto");
        itemInfo.addActionListener(e -> showProjectInfo());
        menuInfo.add(itemInfo);

        menuBar.add(menuJuego);
        menuBar.add(menuInfo);
        setJMenuBar(menuBar);
    }

    private void addGamePanel() {
        gamePanel = new GamePanel();
        gamePanel.setPresenter(presenter);
        add(gamePanel);
    }

    private void addInfoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        infoPanel.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH, AppConfig.PANEL_HEIGHT));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Game Info"));

        buildDataPanel(infoPanel);
        buildButtonPanel(infoPanel);

        add(infoPanel);
    }

    private void buildDataPanel(JPanel parent) {

        JLabel lblBouncesTitle = new JLabel("Rebotes:");
        lblBouncesTitle.setFont(AppFonts.BODY_BOLD);
        lblBouncesTitle.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 20));
        textAreaBounces = new JTextArea();
        textAreaBounces.setEditable(false);
        textAreaBounces.setFont(AppFonts.BODY);
        textAreaBounces.setBackground(parent.getBackground());
        textAreaBounces.setText("--");
        JScrollPane scrollBounces = new JScrollPane(textAreaBounces);
        scrollBounces.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 150));
        scrollBounces.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblStartTitle = new JLabel("Hora de inicio:");
        lblStartTitle.setFont(AppFonts.BODY_BOLD);
        lblStartTitle.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 20));
        labelStartTime = new JLabel("--:--:--");
        labelStartTime.setFont(AppFonts.BODY);
        labelStartTime.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 20));

        JLabel lblElapsedTitle = new JLabel("Tiempo transcurrido:");
        lblElapsedTitle.setFont(AppFonts.BODY_BOLD);
        lblElapsedTitle.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 20));
        labelElapsed = new JLabel("00:00:00");
        labelElapsed.setFont(AppFonts.BODY);
        labelElapsed.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 20));
        parent.add(lblBouncesTitle);
        parent.add(scrollBounces);
        parent.add(lblStartTitle);
        parent.add(labelStartTime);
        parent.add(lblElapsedTitle);
        parent.add(labelElapsed);

        UIManager.addPropertyChangeListener(evt -> {
            if ("lookAndFeel".equals(evt.getPropertyName())) {
                textAreaBounces.setBackground(parent.getBackground());
            }
        });
    }

    private void buildButtonPanel(JPanel parent) {
        CustomButton btnIniciar = new CustomButton("Iniciar");
        btnIniciar.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 35));
        btnIniciar.onClick(e -> {
            if (presenter != null)
                presenter.onStartGame();
        });
        CustomButton btnPausar = new CustomButton("Pausar");
        btnPausar.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 35));
        btnPausar.onClick(e -> {
            if (presenter != null)
                presenter.onPauseGame();
        });
        CustomButton btnReanudar = new CustomButton("Reanudar");
        btnReanudar.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 35));
        btnReanudar.onClick(e -> {
            if (presenter != null)
                presenter.onResumeGame();
        });
        CustomButton btnReiniciar = new CustomButton("Reiniciar");
        btnReiniciar.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 35));
        btnReiniciar.onClick(e -> {
            if (presenter != null)
                presenter.onResetGame();
        });
        CustomButton btnAgregarPelota = new CustomButton("Añadir pelota");
        btnAgregarPelota.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH - 20, 35));
        btnAgregarPelota.onClick(e -> {
            if (presenter != null)
                presenter.onAddBall();
        });
        parent.add(btnIniciar);
        parent.add(btnPausar);
        parent.add(btnReanudar);
        parent.add(btnReiniciar);
        parent.add(btnAgregarPelota);
    }

    private void addKeyListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (presenter == null)
                    return;
                if (e.getKeyCode() == KeyEvent.VK_UP)
                    presenter.onMoveRacketBy(-20);
                if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    presenter.onMoveRacketBy(20);
                if (e.getKeyChar() == '+')
                    presenter.onIncreaseSpeed();
                if (e.getKeyChar() == '-')
                    presenter.onDecreaseSpeed();
            }
        });
        setFocusable(true);
    }

    private void showProjectInfo() {
        Map<String, String> info = new LinkedHashMap<>();
        info.put("Proyecto:", "Ping Pong Game");
        info.put("Autor:", "Yulian Alexis Tobar Rios");
        info.put("Versión:", "1.0.0");
        info.put("Materia:", "Programación III");
        info.put("Patrón:", "MVP");
        info.put("Tecnología:", "Java + Swing + Hilos");
        new AboutDialog(this, "Ping Pong Game", info).setVisible(true);
    }

    @Override
    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter;
        if (gamePanel != null)
            gamePanel.setPresenter(presenter);
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void refresh() {
        if (gamePanel != null)
            gamePanel.repaint();
    }

    @Override
    public void showGameOver() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Juego terminado. ¿Deseas reiniciar?",
                "Fin del juego",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION)
            presenter.onResetGame();
        else
            System.exit(0);
    }

    public void updateBounces(String value) {
        textAreaBounces.setText(value);
    }

    public void updateStartTime(String value) {
        labelStartTime.setText(value);
    }

    public void updateElapsed(String value) {
        labelElapsed.setText(value);
    }

    public void updateGameState(java.util.List<Ball> balls, Racket racket) {
        if (gamePanel != null)
            gamePanel.updateState(balls, racket);
    }
}
