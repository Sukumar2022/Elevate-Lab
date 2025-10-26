package collegeadmissionsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SUKUMAR
 */

import java.sql.Connection;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Student {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps;
    public void crud_student(String application_no,String stu_name, String gurdian_name,
            String dob, String email, String phone, String gender,String address,
            int sub1, int sub2, int sub3, int sub4,int percent,String stream,int status){
        //----------------for inserting new record--------------
        try {
            ps=conn.prepareStatement("INSERT INTO application(application_id,stu_name,gurdian_name,dob,email,phone,gender,address,sub1,sub2,sub3,sub4,percent,stream,status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, application_no);
            ps.setString(2, stu_name);
            ps.setString(3, gurdian_name);
            ps.setString(4, dob);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, gender);
            ps.setString(8, address);
            ps.setInt(9, sub1);
            ps.setInt(10, sub2);
            ps.setInt(11, sub3);
            ps.setInt(12, sub4);
            ps.setInt(13, percent);
            ps.setString(14, stream);
            ps.setInt(15, status);
            if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Application Resgister Successfully");
            }
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
        
    }
    
    public int getPendingStudent(){
        int count=0;
        try {
            ps=conn.prepareStatement("SELECT COUNT(*) AS total FROM application WHERE status=0");
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                count=rs.getInt("total"); 
            }
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
        return count;
    }
    public void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // clear all rows
    }
    public void fillTable(JTable table,String valueToSearch){
        try {
            ps=conn.prepareStatement("SELECT * FROM application");
            //ps.setString(0, valueToSearch);
            ResultSet rs=ps.executeQuery();
            clearTable(table);
            DefaultTableModel model=(DefaultTableModel)table.getModel();
            Object[] row;
            while(rs.next()){
                row=new Object[11];
                row[0]=rs.getInt(1);
                row[1]=rs.getString(2);
                row[2]=rs.getString(3);
                row[3]=rs.getString(4);
                row[4]=rs.getString(5);
                row[5]=rs.getString(6);
                row[6]=rs.getString(7);
                row[7]=rs.getString(8);
                row[8]=rs.getString(9);
                row[9]=rs.getInt(14);
                row[10]=rs.getInt(16);
            
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
        }
    
    public void fillTablebyStream(JTable table,String stream){
        try {
            String query = "SELECT * FROM meritlist WHERE stream = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, stream); // index starts at 1
            //ps=conn.prepareStatement("SELECT * FROM meritlist WHERE stream="+stream);
            //ps.setString(0, valueToSearch);
            ResultSet rs=ps.executeQuery();
            
            DefaultTableModel model=(DefaultTableModel)table.getModel();
            model.setRowCount(0);
            Object[] row;
            while(rs.next()){
                row=new Object[11];
                row[0]=rs.getInt(1);
                row[1]=rs.getString(2);
                row[2]=rs.getString(3);
                row[3]=rs.getString(4);
                row[4]=rs.getFloat(5);
                row[5]=rs.getInt(6);
                row[6]=rs.getString(7);
                
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
        }
    
    public void generateMeritList() {
    try {
        // 1️⃣ Clear old meritlist
        PreparedStatement clear = conn.prepareStatement("DELETE FROM meritlist");
        clear.executeUpdate();

        // 2️⃣ Stream list with cutoff values
        String[][] streams = {
            {"Science", "75"},
            {"Commerce", "60"},
            {"Arts", "55"},
            {"Bio-Science", "65"},
            {"Computer Science", "75"},
            {"Vocational", "60"}
        };

        // 3️⃣ Prepare INSERT statement
        String insertSQL = "INSERT INTO meritlist(application_id, stu_name, stream, percent, merit_rank, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement insert = conn.prepareStatement(insertSQL);

        // 4️⃣ Loop for each stream
        for (String[] streamData : streams) {
            String streamName = streamData[0];
            float cutoff = Float.parseFloat(streamData[1]);
            
            // Select eligible students for this stream
            String sql = "SELECT application_id, stu_name, percent FROM application WHERE percent >= ? ORDER BY percent DESC";
            ps = conn.prepareStatement(sql);
            ps.setFloat(1, cutoff);
            ResultSet rs = ps.executeQuery();

            int rank = 1; // rank resets for each stream

            while (rs.next()) {
                insert.setString(1, rs.getString("application_id"));
                insert.setString(2, rs.getString("stu_name"));
                insert.setString(3, streamName);
                insert.setFloat(4, rs.getFloat("percent"));
                insert.setInt(5, rank++);
                insert.setString(6, "Eligible");
                insert.addBatch();
            }

            ps.close();
        }

        insert.executeBatch(); // insert all results at once
        insert.close();

        JOptionPane.showMessageDialog(null, "✅ Merit list generated and ranked per stream!");

    } catch (Exception e) {
   
        JOptionPane.showMessageDialog(null, "❌ Error generating merit list: " + e.getMessage());
    }
}
    
    
    public void generateStreamWisePDFs() {
    try {
        // 1️⃣ Fetch distinct streams
        String getStreamsSQL = "SELECT DISTINCT stream FROM meritlist";
        PreparedStatement psStreams = conn.prepareStatement(getStreamsSQL);
        ResultSet rsStreams = psStreams.executeQuery();

        while (rsStreams.next()) {
            String streamName = rsStreams.getString("stream");
            String fileName = streamName.replace(" ", "_") + "_MeritList.pdf";

            // 2️⃣ Prepare PDF document
//            Document document = new Document();
            Document document=new Document() {
               
                public int getLength() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                public void addDocumentListener(DocumentListener listener) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

               
                public void removeDocumentListener(DocumentListener listener) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

         
                public void addUndoableEditListener(UndoableEditListener listener) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

           
                public void removeUndoableEditListener(UndoableEditListener listener) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                 
                public Object getProperty(Object key) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

             
                public void putProperty(Object key, Object value) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

           
                public void remove(int offs, int len) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

             
                public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

          
                public String getText(int offset, int length) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

     
                public void getText(int offset, int length, Segment txt) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

               
                public Position getStartPosition() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

      
                public Position getEndPosition() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

            
                public Position createPosition(int offs) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

         
                public javax.swing.text.Element[] getRootElements() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

     
                public javax.swing.text.Element getDefaultRootElement() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }


                public void render(Runnable r) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            };
            PdfWriter.getInstance(document, new FileOutputStream("D:/Elevate_Lab/CollegeAdmissionSystem/MeritLists/" + fileName)); // change folder path as needed
            document.open();

            // 3️⃣ Add title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph(streamName + " Merit List", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // 4️⃣ Create table
            PdfPTable table = new PdfPTable(5); // 5 columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            // Table headers
            table.addCell("Application No");
            table.addCell("Student Name");
            table.addCell("Stream");
            table.addCell("Percent");
            table.addCell("Merit Rank");

            // 5️⃣ Fetch data for this stream
            String sql = "SELECT application_id, stu_name, stream, percent, merit_rank FROM meritlist WHERE stream = ? ORDER BY merit_rank ASC";
            PreparedStatement psData = conn.prepareStatement(sql);
            psData.setString(1, streamName);
            ResultSet rsData = psData.executeQuery();

            while (rsData.next()) {
                table.addCell(rsData.getString("application_id"));
                table.addCell(rsData.getString("stu_name"));
                table.addCell(rsData.getString("stream"));
                table.addCell(String.valueOf(rsData.getFloat("percent")));
                table.addCell(String.valueOf(rsData.getInt("merit_rank")));
            }

            document.add(table);
            document.close();

            System.out.println("✅ PDF generated for stream: " + streamName);
        }

        JOptionPane.showMessageDialog(null, "All stream-wise PDF files generated successfully!");

    } catch (Exception e) {
        System.out.println(""+e);
        JOptionPane.showMessageDialog(null, "❌ Error generating PDF: " + e.getMessage());
    }
}
    
    
    public void finalRegister(String stu_name,String stream,String percent,String merit_rank,String application_id){
        try {
            ps=conn.prepareStatement("INSERT INTO students(stu_name,stream,percent,merit_rank,application_id) VALUES (?,?,?,?,?)");
            ps.setString(1, stu_name);
            ps.setString(2, stream);
            ps.setString(3, percent);
            ps.setString(4, merit_rank);
            ps.setString(5, application_id);
            if(ps.executeUpdate()>0){
                String sql = "DELETE FROM meritlist WHERE application_id = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, application_id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "✅ Student Registration successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "⚠️ No record found with this Application ID!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}

