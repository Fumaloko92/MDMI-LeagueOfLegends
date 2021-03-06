/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.sun.xml.internal.ws.util.StringUtils;
import csv.FileManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.jdmp.complete.JDMP;
/**
 *
 * @author lucas
 */
public class MainForm extends javax.swing.JFrame {

    private HashMap<String,String> championCodes;
    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
         ArrayList<String> championNames=FileManager.readColumnFromFile("champions.csv", 9, true);
         ArrayList<String> codes=FileManager.readColumnFromFile("champions.csv", 0, true);
         championCodes=new HashMap<String,String>();
         for(int i=0;i<championNames.size();i++)
         {
             championCodes.put(championNames.get(i), codes.get(i));
         }
        String[] dataModel=new String[championNames.size()];
        for(int i=0;i<championNames.size();i++)
            dataModel[i]=championNames.get(i);
        cboChampionNames.setModel(new javax.swing.DefaultComboBoxModel(dataModel));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cboChampionNames = new javax.swing.JComboBox<>();
        chkConsiderLost = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        txtFileName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cboChampionNames.setToolTipText("");

        chkConsiderLost.setText("Consider lost matches");
        chkConsiderLost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkConsiderLostActionPerformed(evt);
            }
        });

        jButton1.setText("Filter CSV Matches file");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtFileName.setText("filteredMatches.csv");
        txtFileName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFileNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboChampionNames, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkConsiderLost)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(cboChampionNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkConsiderLost)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(185, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkConsiderLostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkConsiderLostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkConsiderLostActionPerformed

    private void txtFileNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFileNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFileNameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean considerLost=chkConsiderLost.isSelected();
        String text=txtFileName.getText();
        String champName=(String)cboChampionNames.getSelectedItem();
        ArrayList<ArrayList<String>> matches=FileManager.readFile("fetchedMatches.csv", true);
        ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
        for(int i=matches.size()-1;i>=0;i--){
            boolean found=false;
            for(int k=0;k<matches.get(i).size();k+=9){
                if(matches.get(i).get(k).equals(championCodes.get(champName)))
                {
                    ArrayList<String> row;
                    if(!considerLost){
                        if(matches.get(i).get(k+8).equals("true")){
                    found=true;
                    row=new ArrayList<String>(matches.get(i).subList(k+1, k+8));
                    result.add(row);
                    break;
                        }
                    }else{
                        row=new ArrayList<String>(matches.get(i).subList(k+1, k+8));
                        result.add(row);
                    break; 
                    }
                }
            }
            if(!found)
                matches.remove(i);
        }
        ArrayList<String> header=new ArrayList<String>();
            for(int i=0;i<1;i++)
            {
                //header.add("champID_"+(i+1)+"_player");
                header.add("item1_"+(i+1)+"_player");
                header.add("item2_"+(i+1)+"_player");
                header.add("item3_"+(i+1)+"_player");
                header.add("item4_"+(i+1)+"_player");
                header.add("item5_"+(i+1)+"_player");
                header.add("item6_"+(i+1)+"_player");
                header.add("item7_"+(i+1)+"_player");
               // header.add("won_"+(i+1)+"_player");
            }
            result.add(0,header);
        ArrayList<String> itemIDS=FileManager.readColumnFromFile("items.csv", 20, true);
        ArrayList<String> itemNames=FileManager.readColumnFromFile("items.csv", 36, true);
        HashMap<String,String> itemDictionary=new HashMap<String,String>();
        for(int i=0;i<itemIDS.size();i++)
            itemDictionary.put(itemIDS.get(i),itemNames.get(i));
        for(int i=0;i<result.size();i++)
            for(int k=0;k<result.get(i).size();k++)
                if(itemDictionary.containsKey(result.get(i).get(k))){
                    result.get(i).set(k, itemDictionary.get(result.get(i).get(k)));
                }else
                    if(result.get(i).get(k).equals("0")){
                        result.get(i).set(k,"void");
                    }else
                    if(isNumeric(result.get(i).get(k)))
                        result.get(i).set(k,"deletedItem");
        for(ArrayList<String> row : result)
            Collections.sort(row);
        FileManager.writeItems(text, result);
    }//GEN-LAST:event_jButton1ActionPerformed
public static boolean isNumeric(String str)  
{  
  try  
  {  
    double d = Double.parseDouble(str);  
  }  
  catch(NumberFormatException nfe)  
  {  
    return false;  
  }  
  return true;  
}
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboChampionNames;
    private javax.swing.JCheckBox chkConsiderLost;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField txtFileName;
    // End of variables declaration//GEN-END:variables
}
