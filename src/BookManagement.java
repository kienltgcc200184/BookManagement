import lib.XFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class BookManagement extends JFrame{

    private JPanel mainPanel;
    private JTextField txId;
    private JTextField txtName;
    private JTextField txtYear;
    private JTextArea txtDes;
    private JComboBox cbCategory;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton sortByYearButton;
    private JTable tbBook;
    private JTextField txtAuthor;
    private JTextField txtId;

    DefaultTableModel tbModel;
    DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
    ArrayList<Book> bookList;
    String filePath = "book.dat";

    int currentRow = -1;

    public BookManagement(String title){
        //1. Initialize Setup
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setSize(800,400);
        //2. First load ComboBox, table
        initTable();
        loadCb();
        tbBook.setDefaultEditor(Object.class, null);
        //load data file(in your project)
        bookList = new ArrayList<>();
        boolean file = loadFile();
        if(file){
            fillToTable();
        }else{
            showMess("Nothing to show");
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addBook();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList();
            }
        });
        tbBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentRow = tbBook.getSelectedRow();
                showDetail(currentRow);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteList();
            }
        });
        sortByYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortByName();
                fillToTable();
            }
        });
    }


    private void sortByName() {
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    private void deleteList() {
        //1.delete a book to arraylist with currentRow
        deleteBook();
        //2. fill to table
        fillToTable();
        //3. save arraylist
        saveFile();
    }
    private void resetForm() {
        txtId.setText("");
        txtName.setText("");
        cbCategory.setSelectedIndex(0);
        txtDes.setText("");
        txtAuthor.setText("");
        txtYear.setText("");
    }

    private void deleteBook() {
        int re = JOptionPane.showConfirmDialog(this,""+"Do you want to delete this one?","Delete Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if(re==JOptionPane.YES_OPTION){
            bookList.remove(currentRow);
            resetForm();
        }
    }

    private void showDetail(int currentRow) {
        Book b = bookList.get(currentRow);

        String id = b.getID();
        txtId.setText(id);
        String name = b.getName();
        txtName.setText(name);
        String category = b.getCategory();
        cbCategory.setSelectedItem(category);
        String des = b.getDes();
        txtDes.setText(des);
        String author = b.getAuthor();
        txtAuthor.setText(author);
        String year = b.getYear();
        txtYear.setText(year);
    }

    private void updateList() {
        //1. update a book to arraylist with currentRow
        updateBook();
        //2. fill to table
        fillToTable();
        //3. save arraylist
        saveFile();
    }

    private void updateBook() {
        Book b = bookList.get(currentRow);

        String id = txtId.getText();
        b.setID(id);
        String name = txtName.getText();
        b.setName(name);
        String category = cbCategory.getSelectedItem().toString();
        b.setCategory(category);
        String des = txtDes.getText();
        b.setDes(des);
        String author = txtAuthor.getText();
        b.setAuthor(author);
        String year = txtYear.getText();
        b.setYear(year);

    }

    private void addBook() {
        //1. add new book to arraylist
        addTolist();
        //2. fill to table
        fillToTable();
        //3. save arraylist
        saveFile();
    }
    private HashSet<String> idSet = new HashSet<String>();
    private void addTolist() {

        String id = txtId.getText();
        if (idSet.contains(id)) {
            showMess("This ID already exists! Please enter a different ID.");
            return;
        }
        String name = txtName.getText();
        if (name.length() < 1 || name.length() > 30) {
            showMess("Name must be between 1 and 30 characters long!");// Name length
            return;
        }
//        String category = cbCategory.getSelectedItem().toString();
        String category = "";
        if (cbCategory.getSelectedIndex() == 0) {
            showMess("You need to select a category!");
            return;
        } else {
            category = cbCategory.getSelectedItem().toString();
        }

        String author = txtAuthor.getText();
        if (author.length() < 1 || author.length() > 30) {
            showMess("Author name must be between 1 and 30 characters long!");//Author name length
            return;
        }
        String des = txtDes.getText();
        String year = txtYear.getText();

        if (year.isEmpty()) {
            showMess("You need to fill in the year!!!");
            return;
        }

        try {
            int yearNum = Integer.parseInt(year);
            if (yearNum < 1900 || yearNum > Calendar.getInstance().get(Calendar.YEAR)) {
                showMess("Invalid year! The year must be between 1900 and " + Calendar.getInstance().get(Calendar.YEAR));
                return;
            }
        } catch (NumberFormatException e) {
            showMess("Year must be a number!");
            return;
        }

        if (id.isEmpty() || name.isEmpty() || category.isEmpty() || author.isEmpty() || des.isEmpty() || year.isEmpty()) {
            showMess("You need to fill in the information!!!");
            return;
        }
        Book b = new Book(id,name,category,des,author,year);
        bookList.add(b);
        idSet.add(id);

    }

    private void saveFile() {
        lib.XFile.writeObject(filePath, bookList);
    }

    private boolean loadFile() {
        if(XFile.readObject(filePath)==null){
            return false;
        }
        bookList = (ArrayList<Book>) XFile.readObject(filePath);
        for(Book b : bookList){
            idSet.add(b.getID());
        }
        return true;
    }

    private void fillToTable() {
        //clear old data in the table
        tbModel.setRowCount(0);
        for(Book b : bookList){
            Object[] row = new Object[]{
                    b.getID(),
                    b.getName(),
                    b.getCategory(),
                    b.getDes(), b.getAuthor(), b.getYear()
            };
            tbModel.addRow(row);
        }
    }

    private void loadCb() {
        String[] cateLst = {"Choose Category","Manga","Novel","TextBook"};
        for(String s:cateLst){
            cbModel.addElement(s);
        }
        cbCategory.setModel(cbModel);
    }

    private void initTable() {
        String[] columnNames ={"ID","Name","Category","Description","Author","Public Year"};
        tbModel = new DefaultTableModel(columnNames,0);
        tbBook.setModel(tbModel);
    }
    private void showMess(String mess) {
        JOptionPane.showMessageDialog(this,mess);
    }
    public static void main(String[] args) {
        BookManagement b = new BookManagement("Book Management");
        b.setVisible(true);
        b.setLocationRelativeTo(null);
    }
}
