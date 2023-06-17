package projectpaa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;


public class Map extends JFrame {
    private final int[][] map =
            {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
             {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
             {1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
             {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
             {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1}, // 0 = putih
             {1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1}, // 1 = hitam
             {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
             {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
             {1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1},
             {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
             {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1},
             {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
             {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    private boolean gameStarted;
    private boolean isPaused;
    private Timer timer;
    private int droidRedX = 1;
    private int droidRedY = 1;
    private int droidGreenX = 11;
    private int droidGreenY = 9;
    private JButton startButton;
    private JButton pauseButton;
    private boolean isGameStarted = false;
    private Color[][] colors;
    private Color[][] droidColors; // Deklarasikan variabel droidColors
    int CELL_SIZE = 30;
    private boolean droidMerahDitambahkan = false;
    private int droidMerahY = -1;
    private int droidMerahX = -1;
    private List<int[]> droidMerahPosisi = new ArrayList<>();
    private int currentDroidMerahIndex = 0;
    private int droidAddX;
    private int droidAddY;
    private boolean povDroidMerahAktif = false;
    private List<int[]> droidHijauPosisi = new ArrayList<>();
    private boolean isPOVDroidMerah = false;
    private List<int[]> originalColors = new ArrayList<>();

    public Map() {
    setTitle("M.SYAFIQ HAIQAL");
    setSize(640, 480);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel = new JPanel(); // Panel utama
    mainPanel.setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel(); // Panel untuk tombol-tombol
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    // Deklarasi variabel verticalGap
    int verticalGap = 20; // Jarak vertikal antara tombol dan panel atas (dapat disesuaikan)
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.LINE_END; // Set anchor ke kanan
    
     droidColors = new Color[map.length][map[0].length];
    
    // Tombol Mulai
    JButton startButton = new JButton("Mulai");
    startButton.setPreferredSize(new Dimension(130, 25));
    // ActionListener untuk tombol "Mulai"
    startButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        gameStarted = true;
        startGame();
    }
});
  
    //Tombol Acak Peta
    JButton acakButton = new JButton("Acak Peta");
    acakButton.setPreferredSize(new Dimension(130, 35));
    acakButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        acakPeta();
    }
});
    
    // Tombol Pause
    JButton pauseButton = new JButton("Pause");
    pauseButton.setPreferredSize(new Dimension(130, 25));
    pauseButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameStarted) {
            return; // Tidak melakukan apa-apa jika permainan belum dimulai
        }        
        isPaused = !isPaused; // Toggle isPaused
        
        if (isPaused) {
            timer.stop(); // Jika di-pause, hentikan timer
        } else {
            timer.start(); // Jika dilanjutkan, jalankan timer kembali
        }
    }
});
    
    //Tombol Acak Droid Merah
    JButton acakmerahButton = new JButton("Acak Droid Merah");
    acakmerahButton.setPreferredSize(new Dimension(130, 25));
    acakmerahButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        acakDroidMerah();
    }
});
    
    //Tombol Acak Droid Hijau
    JButton acakhijauButton = new JButton("Acak Droid Hijau");
    acakhijauButton.setPreferredSize(new Dimension(130, 25));
    acakhijauButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        acakDroidHijau();
    }
});
    
    //Tombol Tambah Droid Merah
    JButton tambahMerahButton = new JButton("Tambah Droid Merah");
    tambahMerahButton.setPreferredSize(new Dimension(150, 25));
    tambahMerahButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        tambahDroidMerah();
    }
});

    // Tombol Remove Droid Merah
    JButton removeMerahButton = new JButton("Remove Droid Merah");
    removeMerahButton.setPreferredSize(new Dimension(150, 25));
    removeMerahButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        removeDroidMerah();
    }
});


    
    //Tombol POV Droid Merah
    JButton povDroidMerahButton = new JButton("POV Droid Merah");
povDroidMerahButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        isPOVDroidMerah = !isPOVDroidMerah;
        togglePOVDroidMerah();
    }
});

    JButton povDroidHijauButton = new JButton("POV Droid Hijau");


    buttonPanel.add(Box.createRigidArea(new Dimension(0, verticalGap))); // Bagian kosong di atas tombol
    buttonPanel.add(startButton);
    buttonPanel.add(Box.createVerticalStrut(10)); // Spasi antara tombol
    buttonPanel.add(acakButton);
    buttonPanel.add(Box.createVerticalStrut(10));
    buttonPanel.add(pauseButton);
    buttonPanel.add(Box.createVerticalStrut(10));
    buttonPanel.add(acakmerahButton);
    buttonPanel.add(Box.createVerticalStrut(10));
    buttonPanel.add(acakhijauButton);
    buttonPanel.add(Box.createVerticalStrut(10));
    buttonPanel.add(tambahMerahButton);
    buttonPanel.add(Box.createVerticalStrut(10));
    buttonPanel.add(removeMerahButton);
    buttonPanel.add(Box.createVerticalStrut(10));
    buttonPanel.add(povDroidMerahButton);

    mainPanel.add(buttonPanel, BorderLayout.EAST);
    getContentPane().add(mainPanel);
}
    
private void acakPeta() {
    // Reset peta menjadi semuanya berwarna hitam
    for (int row = 0; row < map.length; row++) {
        for (int col = 0; col < map[0].length; col++) {
            map[row][col] = 1;
        }
    }
    
    generateMaze(1, 1);

    repaint();
}

private void generateMaze(int row, int col) {
    // Tandai posisi saat ini sebagai putih (jalan)
    map[row][col] = 0;

    List<int[]> directions = new ArrayList<>();
    directions.add(new int[]{0, 2});
    directions.add(new int[]{2, 0});
    directions.add(new int[]{0, -2});
    directions.add(new int[]{-2, 0});

    // Acak urutan arah
    Collections.shuffle(directions);

    for (int[] direction : directions) {
        int newRow = row + direction[0];
        int newCol = col + direction[1];
        
          
        if (newRow >= 0 && newRow < map.length && newCol >= 0 && newCol < map[0].length && map[newRow][newCol] == 1) {
    // Tandai posisi baru sebagai putih (jalan)
    map[newRow][newCol] = 0;

    // Hapus dinding antara posisi saat ini dan posisi baru
    int wallRow = row + direction[0] / 2;
    int wallCol = col + direction[1] / 2;
    map[wallRow][wallCol] = 0;

    // Rekursi ke posisi baru
    generateMaze(newRow, newCol);
}

    }
}

private void acakDroidMerah() {
    List<int[]> possibleLocations = new ArrayList<>();
    
    for (int row = 0; row < map.length; row++) {
        for (int col = 0; col < map[0].length; col++) {
            if (map[row][col] == 0) {
                possibleLocations.add(new int[]{col, row});
            }
        }
    }
    
    // Jika ada setidaknya satu posisi yang valid
    if (!possibleLocations.isEmpty()) {
        // Acak indeks posisi baru secara acak
        int randomIndex = (int) (Math.random() * possibleLocations.size());
        int[] newLocation = possibleLocations.get(randomIndex);
        
        // Update koordinat Droid Merah
        droidRedX = newLocation[0];
        droidRedY = newLocation[1];
        
        repaint(); // Gambar ulang peta
    }
}

private void acakDroidHijau() {
    List<int[]> possibleLocations = new ArrayList<>();
    
    for (int row = 0; row < map.length; row++) {
        for (int col = 0; col < map[0].length; col++) {
            if (map[row][col] == 0) {
                possibleLocations.add(new int[]{col, row});
            }
        }
    }
    
    // Jika ada setidaknya satu posisi yang valid
    if (!possibleLocations.isEmpty()) {
        // Acak indeks posisi baru secara acak
        int randomIndex = (int) (Math.random() * possibleLocations.size());
        int[] newLocation = possibleLocations.get(randomIndex);
        
        // Update koordinat Droid Hijau
        droidGreenX = newLocation[0];
        droidGreenY = newLocation[1];
        
        repaint(); // Gambar ulang peta
    }
}

private void togglePOVDroidMerah() {
    povDroidMerahAktif = !povDroidMerahAktif; // Toggle status
    
    if (povDroidMerahAktif) {
        // Dalam POV Droid Merah: Sembunyikan droid hijau
        hideDroidHijau();
    } else {
        // Keluar dari POV Droid Merah: Tampilkan kembali droid hijau
        showDroidHijau();
    }
    
    repaint(); // Gambar ulang peta
}

private void hideDroidHijau() {
    for (int[] posisi : droidHijauPosisi) {
        int col = posisi[0];
        int row = posisi[1];
        droidColors[row][col] = Color.WHITE; // Setel warna droid hijau menjadi putih
    }
}

private void showDroidHijau() {
    for (int[] posisi : droidHijauPosisi) {
        int col = posisi[0];
        int row = posisi[1];
        droidColors[row][col] = Color.GREEN; // Setel warna droid hijau menjadi hijau
    }
}

private void tambahDroidMerah() {
    List<int[]> possibleLocations = new ArrayList<>();
    
    for (int row = 0; row < map.length; row++) {
        for (int col = 0; col < map[0].length; col++) {
            if (map[row][col] == 0) {
                possibleLocations.add(new int[]{col, row});
            }
        }
    }
    
    // Jika ada setidaknya satu posisi yang valid
    if (!possibleLocations.isEmpty()) {
        // Acak indeks posisi baru secara acak
        int randomIndex = (int) (Math.random() * possibleLocations.size());
        int[] newLocation = possibleLocations.get(randomIndex);
        
        // Update koordinat Droid Merah
        droidMerahPosisi.add(newLocation);
        
        repaint(); // Gambar ulang peta
    }
}

private void removeDroidMerah() {
    if (!droidMerahPosisi.isEmpty()) {
        // Menghapus droid merah terakhir dari daftar posisi
        droidMerahPosisi.remove(droidMerahPosisi.size() - 1);
        repaint(); // Gambar ulang peta
    }
}


        private void startGame() {
       
    timer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // Gerakan Droid Merah (Menggunakan BFS)
            if (!gameStarted) {
                return;
            }
            if (droidRedX == droidGreenX && droidRedY == droidGreenY) {
                endGame();
                return;
            }
            
            Queue<int[]> queue = new LinkedList<>();
            boolean[][] visited = new boolean[map.length][map[0].length];
            int[][] parent = new int[map.length][map[0].length];
            
            queue.offer(new int[]{droidRedX, droidRedY});//menambah titik saat ini kedalam antrian queue
            visited[droidRedY][droidRedX] = true;// menandai titik saat ini sudah dikunjungi
            
            boolean foundTarget = false;
            
            while (!queue.isEmpty()) {
                int[] current = queue.poll();
                int currentX = current[0];
                int currentY = current[1];
                
                if (currentX == droidGreenX && currentY == droidGreenY) {
                    foundTarget = true;
                    break;
                }
                
                // Pilihan gerakan dalam urutan: kiri, atas, kanan, bawah
                int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
               
                for (int[] dir : directions) {
                    int nextX = currentX + dir[0];
                    int nextY = currentY + dir[1];
                    
                    if (nextX >= 0 && nextX <= map[0].length && nextY >= 0 && nextY < map.length
                        && map[nextY][nextX] == 0 && !visited[nextY][nextX]) {
                        queue.offer(new int[]{nextX, nextY});
                        visited[nextY][nextX] = true;
                        parent[nextY][nextX] = currentY * map[0].length + currentX;
                    }
                }
            }
            
            if (foundTarget) {
                // Memulihkan jalur terpendek dari BFS
                List<int[]> shortestPath = new ArrayList<>();
                int targetX = droidGreenX;
                int targetY = droidGreenY;
                
                while (targetX != droidRedX || targetY != droidRedY) {
                    shortestPath.add(0, new int[]{targetX, targetY});
                    int parentX = parent[targetY][targetX] % map[0].length;
                    int parentY = parent[targetY][targetX] / map[0].length;
                    targetX = parentX;
                    targetY = parentY;
                }
               
                // Pilih langkah pertama dalam jalur terpendek
                int[] nextMove = shortestPath.get(0);
                droidRedX = nextMove[0];
                droidRedY = nextMove[1];
            }

                // Gerakan Droid Hijau (Menghindar)
                int newX = droidGreenX;
                int newY = droidGreenY;
                int randomDirection = (int) (Math.random() * 4);
                switch (randomDirection) {
                    case 0: // Gerak ke atas
                        newY--;
                        break;
                    case 1: // Gerak ke bawah
                        newY++;
                        break;
                    case 2: // Gerak ke kiri
                        newX--;
                        break;
                    case 3: // Gerak ke kanan
                        newX++;
                        break;
                }

                // Periksa batas-batas peta
                if (newX >= 0 && newX < map[0].length && newY >= 0 && newY < map.length && map[newY][newX] == 0) {
                    droidGreenX = newX;
                    droidGreenY = newY;
                }

                repaint();

                // Periksa apakah Droid Merah berhasil menangkap Droid Hijau
                if (droidRedX == droidGreenX && droidRedY == droidGreenY) {
                    endGame();
                }
            
        }
    });
    timer.start();
}

private void endGame() {
    timer.stop();
    gameStarted = false;
    JOptionPane.showMessageDialog(this, "Droid Merah berhasil menangkap Droid Hijau!");
}

    @Override
public void paint(Graphics g) {
    super.paint(g);

    g.translate(50, 50);

    for (int row = 0; row < map.length; row++) {
        for (int col = 0; col < map[0].length; col++) {
                       
            Color color;
            switch (map[row][col]) {
                case 1:
                    color = Color.BLACK;
                    break;
                default:
                    color = Color.WHITE;
            }
            g.setColor(color);
            g.fillRect(30 * col, 30 * row, 30, 30);

            // Gambar Droid Merah
            if (row == droidRedY && col == droidRedX) {
                g.setColor(Color.RED);
                g.fillRect(30 * col, 30 * row, 30, 30);
            }

            // Gambar Droid Hijau
            if (!isPOVDroidMerah && row == droidGreenY && col == droidGreenX) {
                g.setColor(Color.GREEN);
                g.fillRect(30 * col, 30 * row, 30, 30);
            }
            
            // Gambar Droid dengan warna sesuai droidColors
            g.setColor(droidColors[row][col]);
            g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            
            // Gambar Droid Merah
            for (int[] posisi : droidMerahPosisi) {
                if (row == posisi[1] && col == posisi[0]) {
                    g.setColor(Color.RED);
                    g.fillRect(30 * col, 30 * row, 30, 30);
                }
            }

        }
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Map map = new Map();
                map.setVisible(true);
            }
        });
    }


    
}
