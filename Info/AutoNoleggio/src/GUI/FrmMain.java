/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import veicoli.Auto;
import veicoli.FileException;
import veicoli.Furgone;
import veicoli.TextFile;
import veicoli.TargaEsistente;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;

import noleggio.ListaNoleggiati;
import noleggio.ListaVeicoli;

/**
 *
 * @author Michele
 */
public class FrmMain extends javax.swing.JFrame {

    /**
     * Creates new form FrmMain
     */
    DefaultComboBoxModel<String> cbmAnno;
    ListaVeicoli veicoli = new ListaVeicoli();
    ListaNoleggiati veicoliNoleggiati = new ListaNoleggiati();
    DefaultListModel<String> dlmTarghe;

    final String autoCost = "Il costo per le autovetture è:\n- 50 € al giorno\n- 1 € ogni 25 km percorsi\n- 2 € ogni litro di carburante mancante";
    final String furgoneCost = "Il costo per i furgoni è:\n- 70 € al giorno\n- 1 € ogni 30 km percorsi dopo i primi 100 km\n- 2 € ogni litro di carburante mancante";

    DefaultComboBoxModel<String> cbmAnnoInizio;
    DefaultComboBoxModel<String> cbmMeseInizio;
    DefaultComboBoxModel<String> cbmGiornoInizio;

    DefaultComboBoxModel<String> cbmTargheNoleggiate;

    Calendar dataAttuale = new GregorianCalendar();
    String data = "" + dataAttuale.get(Calendar.YEAR) + "/" + (dataAttuale.get(Calendar.MONTH) + 1) + "/" + dataAttuale.get(Calendar.DAY_OF_MONTH);
    String[] mesi;
    String[] giorni;

    public FrmMain() throws IOException, FileException, NumberFormatException {
        this.mesi = new String[12];
        mesi[0] = "Gennaio";
        mesi[1] = "Febbraio";
        mesi[2] = "Marzo";
        mesi[3] = "Aprile";
        mesi[4] = "Maggio";
        mesi[5] = "Giugno";
        mesi[6] = "Luglio";
        mesi[7] = "Agosto";
        mesi[8] = "Settembre";
        mesi[9] = "Ottobre";
        mesi[10] = "Novembre";
        mesi[11] = "Dicembre";

        this.giorni = new String[12];
        giorni[0] = "31";
        giorni[1] = "28";
        giorni[2] = "31";
        giorni[3] = "30";
        giorni[4] = "31";
        giorni[5] = "30";
        giorni[6] = "31";
        giorni[7] = "31";
        giorni[8] = "30";
        giorni[9] = "31";
        giorni[10] = "30";
        giorni[11] = "31";

        this.cbmAnno = new DefaultComboBoxModel<>();

        for (int i = dataAttuale.get(1); i >= 1990; i--) {
            cbmAnno.addElement("" + i);
        }

        dlmTarghe = new DefaultListModel();
        cbmTargheNoleggiate = new DefaultComboBoxModel<>();

        initComponents();
        setResizable(false);

        txtData.setText(data);

        comboTargheNoleggiate.setModel(cbmTargheNoleggiate);

        this.cbmAnnoInizio = new DefaultComboBoxModel<>();
        cbmAnnoInizio.addElement("" + dataAttuale.get(Calendar.YEAR));
        /*
        for(int i = 0; i<=5; i++){
            cbmAnnoInizio.addElement("" + (dataAttuale.get(1)+i));
        }
         */
        annoinizio.setModel(cbmAnnoInizio);

        this.cbmMeseInizio = new DefaultComboBoxModel<>();
        for (int i = dataAttuale.get(Calendar.MONTH); i < 12; i++) {
            cbmMeseInizio.addElement("" + mesi[i]);
        }
        meseInizio.setModel(cbmMeseInizio);
        meseInizio.setSelectedIndex(0);

        this.cbmGiornoInizio = new DefaultComboBoxModel<>();
        int max = Integer.parseInt(giorni[meseInizio.getSelectedIndex()]);
        for (int i = dataAttuale.get(Calendar.DAY_OF_MONTH); i <= max; i++) {
            cbmGiornoInizio.addElement("" + i);
        }
        giornoInizio.setModel(cbmGiornoInizio);

        txtAreaCost.setLineWrap(true);
        txtAreaCost.setWrapStyleWord(true);

        txtCapCarico.setEnabled(false);

        comboAnno.setModel(cbmAnno);
        annoinizio.setModel(cbmAnnoInizio);

        listaTarghe.setModel(dlmTarghe);
        listaTarghe.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        listaTarghe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    if (listaTarghe.getSelectedValuesList().isEmpty()) {
                        return;
                    }

                    String s = "";
                    s = veicoli.getVeicolo(listaTarghe.getSelectedValue()).toString();
                    /*
                    for (String i : listaTarghe.getSelectedValuesList()) {
                        s += veicoliDisponibili.getVeicolo(i).toString() + "\n";
                    }
                    for (int i = 0; i < listaTarghe.getSelectedValuesList().size(); i++) {
                        s += veicoliDisponibili.getVeicolo(listaTarghe.getSelectedValuesList().get(i)).toString() + "\n";
                    }*/
                    txtAreaDescription.setText(s);

                    if (veicoli.getVeicolo(listaTarghe.getSelectedValue()).type().equals("Auto")) {
                        txtAreaCost.setText(autoCost);
                    } else {
                        txtAreaCost.setText(furgoneCost);
                    }
                }
            }
        }
        );

        TextFile read = new TextFile("./src/autonoleggio.txt", 'R');

        while (read.empty()) {
            String[] values = read.fromFile().split(", ");
            System.out.println(Arrays.toString(values));
            try {
                if (values[0].equals("Auto")) {
                    veicoli.addVeicolo(new Auto(values[1], Integer.parseInt(values[2]),
                            values[3], values[4], Integer.parseInt(values[5]),
                            Integer.parseInt(values[6]), Integer.parseInt(values[7]),
                            Integer.parseInt(values[8])));

                } else {
                    veicoli.addVeicolo(new Furgone(values[1], Integer.parseInt(values[2]),
                            values[3], values[4], Integer.parseInt(values[5]),
                            Integer.parseInt(values[6]), Integer.parseInt(values[7]),
                            Integer.parseInt(values[8])));
                }
                dlmTarghe.addElement(values[1]);
            } catch (TargaEsistente te) {
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, te);
            }
        }

        read.closeFile();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tipoVeicolo = new javax.swing.ButtonGroup();
        jTextField1 = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtTarga = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtModello = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboNPass = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtCilindrata = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdbAuto = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        rdbFurgone = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        comboAnno = new javax.swing.JComboBox<>();
        txtMatricola = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCapCarico = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCapSerbatoio = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaTarghe = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaDescription = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaCost = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtTargaNoleggio = new javax.swing.JTextField();
        buttonScelta = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        meseInizio = new javax.swing.JComboBox<>();
        giornoInizio = new javax.swing.JComboBox<>();
        annoinizio = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        buttonDataAttuale = new javax.swing.JButton();
        buttonNoleggia = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        comboTargheNoleggiate = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtDataInizio = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtKmPercorsi = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCarbAttuale = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtCarbTotale = new javax.swing.JTextField();
        buttonRestituisci = new javax.swing.JButton();
        day = new javax.swing.JButton();
        monthUp = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Autonoleggio");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        txtTarga.setToolTipText("");

        jLabel1.setText("Targa");

        txtMarca.setToolTipText("");

        jLabel2.setText("Marca");

        txtModello.setToolTipText("");

        jLabel3.setText("Modello");

        jLabel4.setText("Anno");

        comboNPass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        comboNPass.setToolTipText("");

        jLabel5.setText("Num passeggeri");

        txtCilindrata.setToolTipText("");

        jLabel6.setText("Cilindrata");

        tipoVeicolo.add(rdbAuto);
        rdbAuto.setSelected(true);
        rdbAuto.setText("Auto");
        rdbAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAutoActionPerformed(evt);
            }
        });

        jLabel7.setText("Veicolo");

        tipoVeicolo.add(rdbFurgone);
        rdbFurgone.setText("Furgone");
        rdbFurgone.setToolTipText("");
        rdbFurgone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbFurgoneActionPerformed(evt);
            }
        });

        jButton1.setText("Aggiungi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        comboAnno.setToolTipText("");

        jLabel9.setText("Num matricola");

        jLabel8.setText("Cap di carico");

        jLabel10.setText("Cap serbatoio");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel1))
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCapCarico)
                                .addComponent(txtCilindrata, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtModello)
                                .addComponent(txtMarca, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtMatricola)
                                .addComponent(comboNPass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTarga, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel10)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(rdbAuto)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCapSerbatoio)
                                    .addComponent(comboAnno, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(rdbFurgone))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jButton1)))
                .addContainerGap(209, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMatricola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModello, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCilindrata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCapSerbatoio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbAuto)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbFurgone)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboNPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCapCarico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Aggiungi veicolo", jPanel1);

        jScrollPane1.setViewportView(listaTarghe);

        txtAreaDescription.setEditable(false);
        txtAreaDescription.setColumns(20);
        txtAreaDescription.setRows(5);
        jScrollPane3.setViewportView(txtAreaDescription);

        jButton2.setText("Noleggia");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtAreaCost.setEditable(false);
        txtAreaCost.setColumns(20);
        txtAreaCost.setRows(5);
        jScrollPane2.setViewportView(txtAreaCost);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Lista veicoli", jPanel2);

        txtTargaNoleggio.setEditable(false);
        txtTargaNoleggio.setToolTipText("Inserisci targa del veicolo");

        buttonScelta.setText("Scegli veicolo");
        buttonScelta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSceltaActionPerformed(evt);
            }
        });

        jLabel11.setText("Targa");

        meseInizio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meseInizioActionPerformed(evt);
            }
        });

        jLabel12.setText("Data inizio");

        buttonDataAttuale.setText("Data attuale");
        buttonDataAttuale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDataAttualeActionPerformed(evt);
            }
        });

        buttonNoleggia.setText("Noleggia");
        buttonNoleggia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNoleggiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(annoinizio, 0, 117, Short.MAX_VALUE)
                                .addGap(140, 140, 140))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtTargaNoleggio, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(meseInizio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(giornoInizio, 0, 80, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonDataAttuale, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buttonScelta, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonNoleggia, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTargaNoleggio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonScelta)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(meseInizio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(giornoInizio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(annoinizio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(buttonDataAttuale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonNoleggia)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        comboTargheNoleggiate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTargheNoleggiateActionPerformed(evt);
            }
        });

        jLabel13.setText("Targa");

        txtDataInizio.setEditable(false);

        jLabel14.setText("Data inizio");

        txtData.setEditable(false);

        jLabel15.setText("Data attuale");
        jLabel15.setToolTipText("");

        jLabel16.setText("Km percorsi");

        jLabel17.setText("Carburante");

        jLabel18.setText("/");

        txtCarbTotale.setEditable(false);

        buttonRestituisci.setText("Restituisci");
        buttonRestituisci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRestituisciActionPerformed(evt);
            }
        });

        day.setText("D+");
        day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayActionPerformed(evt);
            }
        });

        monthUp.setText("30D+");
        monthUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthUpActionPerformed(evt);
            }
        });

        jButton3.setText("D-");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKmPercorsi)
                            .addComponent(txtData)
                            .addComponent(comboTargheNoleggiate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDataInizio)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtCarbAttuale, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCarbTotale, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(day)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monthUp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonRestituisci)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(comboTargheNoleggiate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataInizio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(day)
                    .addComponent(monthUp)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKmPercorsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCarbAttuale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(txtCarbTotale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(buttonRestituisci)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Noleggio", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdbFurgoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbFurgoneActionPerformed
        comboNPass.setSelectedIndex(0);
        comboNPass.setEnabled(false);
        txtCapCarico.setEnabled(true);
    }//GEN-LAST:event_rdbFurgoneActionPerformed

    private void rdbAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAutoActionPerformed
        txtCapCarico.setText("");
        comboNPass.setEnabled(true);
        txtCapCarico.setEnabled(false);
    }//GEN-LAST:event_rdbAutoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String targa;
        int numMatricola;
        String marca;
        String modello;
        int cilindrata;
        int anno;
        int capSerbatoio;
        int numPasseggeri;
        int capCarico;
        try {
            targa = txtTarga.getText();
            numMatricola = Integer.parseInt(txtMatricola.getText());
            marca = txtMarca.getText();
            modello = txtModello.getText();
            cilindrata = Integer.parseInt(txtCilindrata.getText());
            anno = Integer.parseInt("" + comboAnno.getSelectedItem());
            capSerbatoio = Integer.parseInt(txtCapSerbatoio.getText());

            if (rdbFurgone.isSelected()) {
                capCarico = Integer.parseInt(txtCapCarico.getText());
                veicoli.addVeicolo(new Furgone(targa, numMatricola, marca, modello, cilindrata, anno, capSerbatoio, capCarico));
            } else {
                numPasseggeri = Integer.parseInt("" + comboNPass.getSelectedItem());
                veicoli.addVeicolo(new Auto(targa, numMatricola, marca, modello, cilindrata, anno, capSerbatoio, numPasseggeri));
            }

            dlmTarghe.addElement(targa);

        } catch (NumberFormatException nfe) {
            System.out.println(nfe);
        } catch (TargaEsistente te) {
            System.out.println(te);
            txtTarga.setText("");
        }

        jTabbedPane1.setSelectedIndex(1);

        txtTarga.setText("");
        txtMatricola.setText("");
        txtMarca.setText("");
        txtModello.setText("");
        txtCilindrata.setText("");
        comboAnno.setSelectedIndex(0);
        txtCapSerbatoio.setText("");
        comboNPass.setSelectedItem(0);
        txtCapCarico.setText("");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            TextFile write = new TextFile("./src/autonoleggio.txt", 'W');
            for (String key : veicoli.getVetture().keySet()) {
                if (veicoli.getVeicolo(key) != null) {
                    System.out.println(write.toFile(veicoli.getVeicolo(key).stringForFile()));
                }
            }
            write.closeFile();
        } catch (IOException | FileException ex) {
            Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (listaTarghe.getSelectedValue() == null) {
            System.out.println("Nessun veicolo è stato selezionato");
        } else {

            if (veicoliNoleggiati.isNoleggiato(listaTarghe.getSelectedValue())) {
                System.out.println("Il veicolo selezionato è già stato noleggiato");
            } else {
                txtTargaNoleggio.setText(listaTarghe.getSelectedValue());
                jTabbedPane1.setSelectedIndex(2);
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void buttonSceltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSceltaActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_buttonSceltaActionPerformed

    private void meseInizioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meseInizioActionPerformed
        int max = 0;
        int x = 1;
        try {
            int i = 0;
            while (meseInizio.getSelectedItem().equals(mesi[i])) {
                i++;
            }
            max = Integer.parseInt(giorni[i]);
        } catch (NumberFormatException nfe) {

        }
        try {
            giornoInizio.removeAllItems();
            cbmGiornoInizio.removeAllElements();
            if (meseInizio.getSelectedItem().equals(mesi[dataAttuale.get(Calendar.MONTH) - 1])) {
                x = dataAttuale.get(Calendar.DAY_OF_MONTH);
            }
            for (int i = x; i <= max; i++) {
                cbmGiornoInizio.addElement("" + i);
            }

            giornoInizio.setModel(cbmGiornoInizio);
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_meseInizioActionPerformed

    private void buttonDataAttualeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDataAttualeActionPerformed
        cbmAnnoInizio.removeAllElements();
        cbmAnnoInizio.addElement("" + dataAttuale.get(Calendar.YEAR));
        try {
            meseInizio.removeAllItems();
            cbmMeseInizio.removeAllElements();
        } catch (NullPointerException npe) {
        }
            for (int i = dataAttuale.get(Calendar.MONTH); i < 12; i++) {
                cbmMeseInizio.addElement("" + mesi[i]);
            }
        
        int max = 0;
        try {
            max = Integer.parseInt(giorni[dataAttuale.get(Calendar.MONTH)]);
        } catch (NumberFormatException e) {
        }

        //giornoInizio.removeAllItems();
        cbmGiornoInizio.removeAllElements();
        for (int i = dataAttuale.get(Calendar.DAY_OF_MONTH); i <= max; i++) {
            cbmGiornoInizio.addElement("" + i);
        }

    }//GEN-LAST:event_buttonDataAttualeActionPerformed

    private void buttonNoleggiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNoleggiaActionPerformed
        if (txtTargaNoleggio.equals("")) {
            return;
        }
        int anno = 0, mese = 0, giorno = 0;
        try {
            anno = Integer.parseInt(annoinizio.getSelectedItem().toString());
            mese = meseInizio.getSelectedIndex();
            giorno = Integer.parseInt(giornoInizio.getSelectedItem().toString());
        } catch (NumberFormatException nfe) {

        }
        veicoliNoleggiati.addData(new GregorianCalendar(anno, dataAttuale.get(Calendar.MONTH) + mese, giorno), txtTargaNoleggio.getText());
        cbmTargheNoleggiate.addElement(txtTargaNoleggio.getText());
        comboTargheNoleggiate.setSelectedItem(0);
        dlmTarghe.removeElement(txtTargaNoleggio.getText());
        txtTargaNoleggio.setText("");
    }//GEN-LAST:event_buttonNoleggiaActionPerformed

    private void comboTargheNoleggiateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTargheNoleggiateActionPerformed
        if (comboTargheNoleggiate.getSelectedIndex() == -1) {
            txtDataInizio.setText("");
            return;
        }
        Calendar dataInizio = veicoliNoleggiati.getData((String) comboTargheNoleggiate.getSelectedItem());
        if (dataInizio == null) {
            return;
        }
        String temp = dataInizio.get(Calendar.YEAR) + "/" + (dataInizio.get(Calendar.MONTH) + 1) + "/" + dataInizio.get(Calendar.DAY_OF_MONTH);
        txtDataInizio.setText(temp);
        txtCarbTotale.setText("" + veicoli.getVeicolo((String) comboTargheNoleggiate.getSelectedItem()).getCapSerbatoio());
    }//GEN-LAST:event_comboTargheNoleggiateActionPerformed

    private void buttonRestituisciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRestituisciActionPerformed
        if (comboTargheNoleggiate.getSelectedIndex() == -1) {
            return;
        }
        int kmPercorsi = 0;
        int carbMancante = 0;
        long giorniNoleggio = 0;

        try {
            kmPercorsi = Integer.parseInt(txtKmPercorsi.getText());
            carbMancante = Integer.parseInt(txtCarbTotale.getText()) - Integer.parseInt(txtCarbAttuale.getText());
            giorniNoleggio = veicoliNoleggiati.durataNoleggio((GregorianCalendar) dataAttuale, (String) comboTargheNoleggiate.getSelectedItem());
        } catch (NumberFormatException nfe) {

        }
        txtCarbAttuale.setText("");
        txtKmPercorsi.setText("");
        String targa = (String) comboTargheNoleggiate.getSelectedItem();
        System.out.println(kmPercorsi);
        System.out.println(carbMancante);
        System.out.println(giorniNoleggio);
        veicoliNoleggiati.restituisci(targa);
        dlmTarghe.addElement(targa);
        System.out.println("restituito, costo noleggio: " + veicoli.getVeicolo(targa).calcCosto(giorniNoleggio, kmPercorsi, carbMancante));
        cbmTargheNoleggiate.removeElementAt(comboTargheNoleggiate.getSelectedIndex());
    }//GEN-LAST:event_buttonRestituisciActionPerformed

    private void monthUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthUpActionPerformed
        dataAttuale.add(Calendar.DAY_OF_MONTH, 30);
        data = "" + dataAttuale.get(Calendar.YEAR) + "/" + (dataAttuale.get(Calendar.MONTH) + 1) + "/" + dataAttuale.get(Calendar.DAY_OF_MONTH);
        txtData.setText(data);
    }//GEN-LAST:event_monthUpActionPerformed

    private void dayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayActionPerformed
        dataAttuale.add(Calendar.DAY_OF_MONTH, 1);
        data = "" + dataAttuale.get(Calendar.YEAR) + "/" + (dataAttuale.get(Calendar.MONTH) + 1) + "/" + dataAttuale.get(Calendar.DAY_OF_MONTH);
        txtData.setText(data);
    }//GEN-LAST:event_dayActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dataAttuale.set(Calendar.DAY_OF_MONTH, dataAttuale.get(Calendar.DAY_OF_MONTH) - 1);
        data = "" + dataAttuale.get(Calendar.YEAR) + "/" + (dataAttuale.get(Calendar.MONTH) + 1) + "/" + dataAttuale.get(Calendar.DAY_OF_MONTH);
        txtData.setText(data);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmMain().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileException ex) {
                    Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> annoinizio;
    private javax.swing.JButton buttonDataAttuale;
    private javax.swing.JButton buttonNoleggia;
    private javax.swing.JButton buttonRestituisci;
    private javax.swing.JButton buttonScelta;
    private javax.swing.JComboBox<String> comboAnno;
    private javax.swing.JComboBox<String> comboNPass;
    private javax.swing.JComboBox<String> comboTargheNoleggiate;
    private javax.swing.JButton day;
    private javax.swing.JComboBox<String> giornoInizio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList<String> listaTarghe;
    private javax.swing.JComboBox<String> meseInizio;
    private javax.swing.JButton monthUp;
    private javax.swing.JRadioButton rdbAuto;
    private javax.swing.JRadioButton rdbFurgone;
    private javax.swing.ButtonGroup tipoVeicolo;
    private javax.swing.JTextArea txtAreaCost;
    private javax.swing.JTextArea txtAreaDescription;
    private javax.swing.JTextField txtCapCarico;
    private javax.swing.JTextField txtCapSerbatoio;
    private javax.swing.JTextField txtCarbAttuale;
    private javax.swing.JTextField txtCarbTotale;
    private javax.swing.JTextField txtCilindrata;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDataInizio;
    private javax.swing.JTextField txtKmPercorsi;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtMatricola;
    private javax.swing.JTextField txtModello;
    private javax.swing.JTextField txtTarga;
    private javax.swing.JTextField txtTargaNoleggio;
    // End of variables declaration//GEN-END:variables
}
