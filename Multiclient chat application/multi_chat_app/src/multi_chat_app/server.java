/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multi_chat_app;

import java.io.*;
import java.net.*;
import java.util.*;

public class server extends javax.swing.JFrame {

    static ArrayList c_os;
    static ArrayList<String> users;
    static Socket cs;
    public class server_listening implements Runnable
    {
        @Override
        public void run()
        {
            c_os=new ArrayList();
            users=new ArrayList();
            
            try
            {
                ServerSocket ss=new ServerSocket(8000);
                while(true)
                {
                    cs=ss.accept();
                    PrintWriter w=new PrintWriter(cs.getOutputStream());
                    c_os.add(w);
                    Thread l=new Thread(new client_handler(cs,w));
                    l.start();
                    area.append("\nGOT A CONNECTION.\n");
                }
            }
            catch(IOException e)
            {
                area.append("\nAN ERROR OCCURS WHILE ESTABLISHING A CONNECTION.\n");
            }
        }
    }
    
    public class client_handler implements Runnable
        {
            BufferedReader r;
            PrintWriter client;
            
            public client_handler(Socket cs,PrintWriter w)
            {
                client=w;
                try
                {
                    InputStreamReader in=new InputStreamReader(cs.getInputStream());
                    r= new BufferedReader(in);
                }
                catch(Exception e)
                {
                     area.append("\nAN ERROR OCCURS WHILE READING FROM INPUTSTREAM.\n");
                }
            }
            
            @Override
            public void run()
            {
                int c=0;
                String msg;
                String[] i;
                
                try
                {
                       while((msg=r.readLine())!=null)
                       {
                       i=msg.split(":");
                       area.append("\nRECEIVED:"+msg+"\n");
                       
                        switch (i[2]) {
                               case "connect":
                                  for(String u:users)
                                  {
                                    if(i[0].equals(u))
                                        c=1;
                                    else
                                        c=0;
                                   }
                                  if(c==0)
                                  {
                                   tell_everyone(msg);
                                   users.add(i[0]);
                                   area.append("SERVER HAS ADDED "+i[0]+" TO THE CHAT USERS LIST.\n");
                                  }
                                  else
                                  tell_everyone("CU");
                                   break;
                               case "disconnect":
                                   tell_everyone(msg);
                                   users.remove(i[0]);
                                   c_os.remove(client);
                                   area.append("SERVER HAS REMOVED "+i[0]+" FROM THE CHAT USERS LIST.\n");
                                   break;
                               case "chat":
                                   tell_everyone(msg);
                                   break;
                               default:
                                   break;
                        }
                   }
                }
                catch(Exception e)
                {
                     area.append("LOST CONNECTION.\n");
                     c_os.remove(client);
                }
            }
        }
    
        public void tell_everyone(String msg)
        {
            Iterator it=c_os.iterator();
            area.append("SENDING: "+msg+"\n");
            
            while(it.hasNext())
            {
                try
                {
                    PrintWriter w=(PrintWriter) it.next();
                    w.println(msg);
                    w.flush();
                }
                catch(Exception e)
                {
                 area.append("\nAN ERROR OCCURS WHILE TELLING EVERYONE.\n");   
                }
            }
        }
    
    public server() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        start = new javax.swing.JButton();
        o_user = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SERVER");

        start.setBackground(new java.awt.Color(204, 255, 255));
        start.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        start.setText("START");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        o_user.setBackground(new java.awt.Color(204, 255, 255));
        o_user.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        o_user.setText("ONLINE USERS");
        o_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                o_userActionPerformed(evt);
            }
        });

        clear.setBackground(new java.awt.Color(204, 255, 255));
        clear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        clear.setText("CLEAR");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        area.setEditable(false);
        area.setBackground(new java.awt.Color(204, 255, 204));
        area.setColumns(20);
        area.setFont(new java.awt.Font("Monospaced", 3, 12)); // NOI18N
        area.setForeground(new java.awt.Color(0, 0, 102));
        area.setRows(5);
        jScrollPane1.setViewportView(area);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(o_user)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                        .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(start)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o_user)
                    .addComponent(clear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        Thread starter=new Thread(new server_listening());
        starter.start();
        area.append("SERVER STARTED LISTENING...\n");
    }//GEN-LAST:event_startActionPerformed

    private void o_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_o_userActionPerformed
        area.append("\nONLINE USERS:\n");
        int i=1;
        for(String ou:users)
        {
            area.append(i+". "+ou+"\n");
            i++;
        }
        if(i==1)
            area.append("CURRENTLY NO ONE IS ONLINE.\n");
    }//GEN-LAST:event_o_userActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        area.setText("");
    }//GEN-LAST:event_clearActionPerformed

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
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextArea area;
    private javax.swing.JButton clear;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton o_user;
    private javax.swing.JButton start;
    // End of variables declaration//GEN-END:variables
}
