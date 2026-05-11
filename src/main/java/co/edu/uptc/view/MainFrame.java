package co.edu.uptc.view;

import co.edu.uptc.components.button.CustomButton;
import co.edu.uptc.components.dialog.AboutDialog;
import co.edu.uptc.components.fonts.AppFonts;
import co.edu.uptc.util.Utilities;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.pojo.Ball;
import co.edu.uptc.pojo.Racket;
import co.edu.uptc.util.ThemeManager;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainFrame extends JFrame implements ViewInterface {

    private static MainFrame instance;

    private PresenterInterface presenter;
    private GamePanel gamePanel;
    private CustomButton btnStart;

    private JLabel labelStartTime;
    private JLabel labelElapsed;
    private JTextArea textAreaBounces;

    private MainFrame() {
        initFrame();
        addComponents();
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    private void initFrame() {
        setTitle("Ping Pong");
        setSize(Utilities.FRAME_WIDTH, Utilities.FRAME_HEIGHT);
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
        JMenu menuTema = new JMenu("Cambiar tema");

        JMenuItem itemClaro = new JMenuItem("Claro");
        JMenuItem itemOscuro = new JMenuItem("Oscuro");

        itemClaro.addActionListener(e -> ThemeManager.applyByKey(ThemeManager.LIGHT));
        itemOscuro.addActionListener(e -> ThemeManager.applyByKey(ThemeManager.DARK));

        menuTema.add(itemClaro);
        menuTema.add(itemOscuro);

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));

        menuJuego.add(menuTema);
        menuJuego.addSeparator();
        menuJuego.add(itemSalir);

        JMenu menuInfo = new JMenu("Acerca de");
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
        infoPanel.setPreferredSize(new Dimension(Utilities.INFO_PANEL_WIDTH, Utilities.PANEL_HEIGHT));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Game Info"));

        buildDataPanel(infoPanel);
        buildButtonPanel(infoPanel);

        add(infoPanel);
    }

    private void buildDataPanel(JPanel parent) {
        parent.add(buildBouncesSection(parent));
        parent.add(buildStartTimeSection());
        parent.add(buildElapsedSection());
        registerThemeListener(parent);
    }

    private JScrollPane buildBouncesSection(JPanel parent) {
        JLabel title = createLabel("Rebotes:", AppFonts.BODY_BOLD);
        parent.add(title);
        textAreaBounces = new JTextArea("--");
        textAreaBounces.setEditable(false);
        textAreaBounces.setFont(AppFonts.BODY);
        textAreaBounces.setBackground(parent.getBackground());
        ((DefaultCaret) textAreaBounces.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        JScrollPane scroll = new JScrollPane(textAreaBounces);
        scroll.setPreferredSize(new Dimension(Utilities.INFO_PANEL_WIDTH - 20, 150));
        scroll.setBorder(BorderFactory.createEtchedBorder());
        return scroll;
    }

    private JPanel buildStartTimeSection() {
        JPanel section = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        section.setOpaque(false);
        section.setPreferredSize(new Dimension(Utilities.INFO_PANEL_WIDTH - 20, 45));
        section.add(createLabel("Hora de inicio:", AppFonts.BODY_BOLD));
        labelStartTime = new JLabel("--:--:--");
        labelStartTime.setFont(AppFonts.BODY);
        labelStartTime.setPreferredSize(new Dimension(Utilities.INFO_PANEL_WIDTH - 20, 20));
        section.add(labelStartTime);
        return section;
    }

    private JPanel buildElapsedSection() {
        JPanel section = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        section.setOpaque(false);
        section.setPreferredSize(new Dimension(Utilities.INFO_PANEL_WIDTH - 20, 45));
        section.add(createLabel("Tiempo transcurrido:", AppFonts.BODY_BOLD));
        labelElapsed = new JLabel("00:00:00");
        labelElapsed.setFont(AppFonts.BODY);
        labelElapsed.setPreferredSize(new Dimension(Utilities.INFO_PANEL_WIDTH - 20, 20));
        section.add(labelElapsed);
        return section;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setPreferredSize(new Dimension(Utilities.INFO_PANEL_WIDTH - 20, 20));
        return label;
    }

    private void registerThemeListener(JPanel parent) {
        UIManager.addPropertyChangeListener(evt -> {
            if ("lookAndFeel".equals(evt.getPropertyName()))
                textAreaBounces.setBackground(parent.getBackground());
        });
    }

    private void buildButtonPanel(JPanel parent) {
        btnStart = createButton("Iniciar",       this::handleStart);
        parent.add(btnStart);
        parent.add(createButton("Pausar",        e -> fire(presenter::onPauseGame)));
        parent.add(createButton("Reanudar",      e -> fire(presenter::onResumeGame)));
        parent.add(createButton("Reiniciar",     e -> fire(presenter::onResetGame)));
        parent.add(createButton("Añadir pelota", e -> fire(presenter::onAddBall)));
    }

    private CustomButton createButton(String label, java.awt.event.ActionListener action) {
        CustomButton btn = new CustomButton(label);
        btn.setPreferredSize(new Dimension(Utilities.INFO_PANEL_WIDTH - 20, 35));
        btn.onClick(action);
        return btn;
    }

    private void handleStart(java.awt.event.ActionEvent e) {
        if (presenter == null) return;
        presenter.onStartGame();
        btnStart.setEnabled(false);
    }

    private void fire(Runnable action) {
        if (presenter != null) action.run();
    }

    private void addKeyListeners() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(e -> {
                    if (e.getID() != KeyEvent.KEY_PRESSED || presenter == null)
                        return false;
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> presenter.onMoveRacketBy(-20);
                        case KeyEvent.VK_DOWN -> presenter.onMoveRacketBy(20);
                        case KeyEvent.VK_ADD -> presenter.onIncreaseSpeed(); // + del teclado numérico
                        case KeyEvent.VK_MINUS -> presenter.onDecreaseSpeed(); // - del teclado numérico
                    }
                    if (e.getKeyChar() == '+')
                        presenter.onIncreaseSpeed(); // + normal
                    if (e.getKeyChar() == '-')
                        presenter.onDecreaseSpeed(); // - normal
                    return false;
                });
        setFocusable(true);
        requestFocusInWindow();
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
                "Juego terminado. \n¿Deseas reiniciar?",
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
