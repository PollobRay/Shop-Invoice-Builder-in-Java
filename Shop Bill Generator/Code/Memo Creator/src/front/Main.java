/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package front;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Roy
 */
public class Main extends JFrame {

    private Container c;
    private Font font, font1;
    private Cursor cursor;
    private JLabel name, date, dateD, product, price, given_money, back_money, back_moneyD, shop;
    private JTextField nameT, productT, priceT, given_moneyT;
    private JButton save;

    public Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(Color.WHITE);

        font = new Font("Bahnschrift SemiLight", Font.BOLD, 25);
        font1 = new Font("Arial", Font.BOLD, 40);

        cursor = new Cursor(Cursor.HAND_CURSOR);
        this.setTitle("Digital Shop");

        //make full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(screenSize);

        Border border = BorderFactory.createLineBorder(Color.CYAN, 1);

        // digital shop
        shop = new JLabel("Digital Shop ");
        shop.setFont(font1);
        shop.setBounds(510, 30, 400, 50);
        shop.setForeground(Color.ORANGE);
        c.add(shop);

        // name of customer
        name = new JLabel("Name of Customer :");
        name.setFont(font);
        name.setBounds(200, 100, 250, 50);
        name.setForeground(Color.BLUE);
        c.add(name);

        nameT = new JTextField();
        nameT.setFont(font);
        nameT.setBounds(500, 100, 300, 50);
        // nameT.setBackground(Color.LIGHT_GRAY);
        nameT.setBorder(border);
        c.add(nameT);

        //Date
        date = new JLabel("Date :");
        date.setFont(font);
        date.setBounds(200, 160, 100, 50);
        date.setForeground(Color.BLUE);
        c.add(date);

        LocalDate date = LocalDate.now();
        dateD = new JLabel(date.toString());
        dateD.setFont(font);
        dateD.setBounds(380, 160, 150, 50);
        c.add(dateD);

        // product
        product = new JLabel("Name of Product");
        product.setFont(font);
        product.setBounds(200, 200, 350, 50);
        product.setForeground(Color.BLUE);
        c.add(product);

        productT = new JTextField();
        productT.setFont(font);
        productT.setBounds(200, 250, 350, 400);
        //productT.setBackground(Color.LIGHT_GRAY);
        productT.setBorder(border);
        c.add(productT);

        // given money
        given_money = new JLabel("Received Money");
        given_money.setFont(font);
        given_money.setBounds(530, 200, 200, 50);
        given_money.setForeground(Color.BLUE);
        c.add(given_money);

        given_moneyT = new JTextField();
        given_moneyT.setFont(font);
        //given_moneyT.setBackground(Color.LIGHT_GRAY);
        given_moneyT.setBorder(border);
        given_moneyT.setBounds(550, 250, 170, 400);
        c.add(given_moneyT);

        // price
        price = new JLabel("Price");
        price.setFont(font);
        price.setBounds(750, 200, 170, 50);
        price.setForeground(Color.BLUE);
        c.add(price);

        priceT = new JTextField();
        priceT.setFont(font);
        priceT.setBounds(720, 250, 170, 400);
        priceT.setBorder(border);
        c.add(priceT);

        // back money
        back_money = new JLabel("Back Money");
        back_money.setFont(font);
        back_money.setBounds(890, 200, 170, 50);
        back_money.setForeground(Color.BLUE);
        c.add(back_money);

        back_moneyD = new JLabel("00");
        back_moneyD.setFont(font);
        back_moneyD.setBounds(890, 250, 170, 400);
        back_moneyD.setBorder(border);
        c.add(back_moneyD);

        // Button
        save = new JButton("Save As PDF");
        save.setFont(font);
        save.setBounds(850, 120, 200, 50);
        save.setForeground(Color.BLUE);
        c.add(save);

        // priceT text box handler
        priceT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gm = given_moneyT.getText();
                String pr = priceT.getText();
                if (!gm.equals("") && pr.equals("")) {
                } else {
                    int giveM = Integer.parseInt(gm);
                    int price = Integer.parseInt(pr);
                    if (giveM > price) {
                        int result = giveM - price;
                        back_moneyD.setText(" " + result);
                    }
                }
            }
        });

// save button Handler
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cwd = System.getProperty("user.dir");
                LocalDate date = LocalDate.now();

                String dd = date.toString();

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

                File dir = new File(cwd + "\\pdf");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                String file = cwd + "\\pdf\\bil " + dd +"   "+nameT.getText()+".pdf";

                //make pdf
                float left = 30;
                float right = 30;
                float top = 40;
                float bottom = 40;

                try {
                    Document document = new Document(PageSize.A4, left, right, top, bottom);
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                    document.open();

                    document.addCreator("Roy.Inc");
                    document.addAuthor("Pollob Ray");
                    document.addTitle("Report");
                    document.addSubject("Bil Report");

                    document.setMargins(left, right, top, bottom);

                    document.add(new Paragraph("                                                     Digital Shop", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, com.itextpdf.text.Font.BOLD, BaseColor.RED)));
                    document.add(new Paragraph("-----------------------------------------------------------------------------------------", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, com.itextpdf.text.Font.BOLD, BaseColor.BLUE)));

                    document.add(new Paragraph("                                         "));
                    document.add(new Paragraph("                                         "));

                    document.add(new Paragraph("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                Date :" + dd, FontFactory.getFont(FontFactory.TIMES_BOLD, 10, com.itextpdf.text.Font.BOLD, BaseColor.BLACK)));

                    document.add(new Paragraph("                                         "));
                    document.add(new Paragraph("                                         "));
                    
                    document.add(new Paragraph("                                       Name of Customer : "+nameT.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD, 18, com.itextpdf.text.Font.BOLD, BaseColor.BLACK)));
                    
                    document.add(new Paragraph("                                         "));
                    document.add(new Paragraph("                                         "));

                    PdfPTable tb = new PdfPTable(4);// 4 is column

                    PdfPCell cell = new PdfPCell(new Paragraph("Shop Memo"));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.CYAN);
                    tb.addCell(cell);

                    PdfPCell product = new PdfPCell(new Paragraph("Name of Product"));
                    product.setHorizontalAlignment(Element.ALIGN_CENTER);
                    product.setBackgroundColor(BaseColor.LIGHT_GRAY);

                    PdfPCell rmoney = new PdfPCell(new Paragraph("Receive Money"));
                    rmoney.setHorizontalAlignment(Element.ALIGN_CENTER);
                    rmoney.setBackgroundColor(BaseColor.LIGHT_GRAY);

                    PdfPCell price = new PdfPCell(new Paragraph("Price"));
                    price.setHorizontalAlignment(Element.ALIGN_CENTER);
                    price.setBackgroundColor(BaseColor.LIGHT_GRAY);

                    PdfPCell bmoney = new PdfPCell(new Paragraph("Back Money"));
                    bmoney.setHorizontalAlignment(Element.ALIGN_CENTER);
                    bmoney.setBackgroundColor(BaseColor.LIGHT_GRAY);

                    tb.addCell(product);
                    tb.addCell(rmoney);
                    tb.addCell(price);
                    tb.addCell(bmoney);

                    String Name = productT.getText();
                    String RM = given_moneyT.getText();
                    String pr = priceT.getText();
                    String backM = back_moneyD.getText();
                    tb.addCell(Name);
                    tb.addCell(RM);
                    tb.addCell(pr);
                    tb.addCell(backM);

                    document.add(tb);

                    
                    // Pollob Ray
                    document.add(new Paragraph("                                  "));
                    document.add(new Paragraph("                                  "));
                    document.add(new Paragraph("                                  "));
                    document.add(new Paragraph("                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          Pollob Ray" , FontFactory.getFont(FontFactory.TIMES_BOLD, 8, com.itextpdf.text.Font.BOLD, BaseColor.BLUE)));

                    
                    document.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Failed" + ex);
                }
            }

        });

    }
//    public static void main(String[] args) {
//        new Main().setVisible(true);
//    }

}
