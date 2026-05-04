package co.edu.uptc.view;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame implements ViewInterface {

    private static GameFrame instance;

    private PresenterInterface presenter;
    private GamePanel gamePanel;

    private JLabel labelBounces;
    private JLabel labelStartTime;
    private JLabel labelElapsed;
    private JButton btnAddBall;

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

        JMenu menuGame = new JMenu("Playing");
        JMenuItem itemStart   = new JMenuItem("Start");
        JMenuItem itemPause   = new JMenuItem("Pause");
        JMenuItem itemResume  = new JMenuItem("Resume");
        JMenuItem itemRestart = new JMenuItem("Restart");
        JMenuItem itemExit    = new JMenuItem("Exit");

        itemStart.addActionListener(e   -> { if (presenter != null) presenter.onStartGame();  });
        itemPause.addActionListener(e   -> { if (presenter != null) presenter.onPauseGame();  });
        itemResume.addActionListener(e  -> { if (presenter != null) presenter.onResumeGame(); });
        itemRestart.addActionListener(e -> { if (presenter != null) presenter.onResetGame();  });
        itemExit.addActionListener(e    -> System.exit(0));

        menuGame.add(itemStart);
        menuGame.add(itemPause);
        menuGame.add(itemResume);
        menuGame.add(itemRestart);
        menuGame.addSeparator();
        menuGame.add(itemExit);

        JMenu menuInfo = new JMenu("About");
        JMenuItem itemInfo = new JMenuItem("Project Info");
        itemInfo.addActionListener(e -> showProjectInfo());
        menuInfo.add(itemInfo);

        menuBar.add(menuGame);
        menuBar.add(menuInfo);
        setJMenuBar(menuBar);
    }

    private void addGamePanel() {
        gamePanel = new GamePanel();
        add(gamePanel);
    }

    private void addInfoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        infoPanel.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH, AppConfig.FRAME_HEIGHT));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Game Info"));

        buildDataPanel(infoPanel);
        buildButtonPanel(infoPanel);

        add(infoPanel);
    }

    private void buildDataPanel(JPanel parent) {
        JLabel lblBouncesTitle = new JLabel("Rebotes:");
        labelBounces = new JLabel("0");

        JLabel lblStartTitle = new JLabel("Hora de inicio:");
        labelStartTime = new JLabel("--:--:--");

        JLabel lblElapsedTitle = new JLabel("Tiempo transcurrido:");
        labelElapsed = new JLabel("00:00:00");

        parent.add(lblBouncesTitle);
        parent.add(labelBounces);
        parent.add(lblStartTitle);
        parent.add(labelStartTime);
        parent.add(lblElapsedTitle);
        parent.add(labelElapsed);
    }

    private void buildButtonPanel(JPanel parent) {
        btnAddBall = new JButton("Add Ball");
        btnAddBall.setPreferredSize(new Dimension(150, 30));
        btnAddBall.addActionListener(e -> { if (presenter != null) presenter.onAddBall(); });
        parent.add(btnAddBall);
    }

    private void addKeyListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (presenter == null) return;
                if (e.getKeyCode() == KeyEvent.VK_UP)   presenter.onMoveRacket(-20);
                if (e.getKeyCode() == KeyEvent.VK_DOWN) presenter.onMoveRacket(20);
                if (e.getKeyChar() == '+')               presenter.onIncreaseSpeed();
                if (e.getKeyChar() == '-')               presenter.onDecreaseSpeed();
            }
        });
        setFocusable(true);
    }

    private void showProjectInfo() {
        JOptionPane.showMessageDialog(
                this,
                "Ping Pong Game\nDesarrollado con Java + Swing\nPatrón MVP\nConcurrencia con Threads",
                "Project Info",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void refresh() {
        if (gamePanel != null) gamePanel.repaint();
    }

    @Override
    public void showGameOver() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Game Over. ¿Deseas reiniciar?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );
        if (option == JOptionPane.YES_OPTION) presenter.onResetGame();
        else System.exit(0);
    }

    public void updateBounces(String value)   { labelBounces.setText(value);   }
    public void updateStartTime(String value) { labelStartTime.setText(value); }
    public void updateElapsed(String value)   { labelElapsed.setText(value);   }
}
