import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
 
public class TravelManager extends JFrame {
    private DefaultListModel<String> bookingListModel;
    private JList<String> bookingList;
    private List<Booking> bookings;
 
    public TravelManager() {
        setTitle("StaySmart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
 
        bookings = loadBookings();
 
        bookingListModel = new DefaultListModel<>();
        for (Booking booking : bookings) {
            bookingListModel.addElement(booking.getType() + ": " + booking.getName() + " - Customer: " + booking.getCustomerName() + " - Date: " + booking.getDate());
        }
 
        bookingList = new JList<>(bookingListModel);
        JScrollPane scrollPane = new JScrollPane(bookingList);
 
        JButton addButton = new JButton("Add Booking");
        JButton removeButton = new JButton("Remove Booking");
        JButton updateButton = new JButton("Update Booking");
 
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
 
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
 
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBooking();
            }
        });
 
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeBooking();
            }
        });
 
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateBooking();
            }
        });
    }
 
    private void addBooking() {
        JTextField typeField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField customerNameField = new JTextField();
        JTextField dateField = new JTextField();
 
        Object[] message = {
            "Type (Hotel/Flight):", typeField,
            "Name:", nameField,
            "Customer Name:", customerNameField,
            "Date:", dateField
        };
 
        int option = JOptionPane.showConfirmDialog(this, message, "Add Booking", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String type = typeField.getText();
            String name = nameField.getText();
            String customerName = customerNameField.getText();
            String date = dateField.getText();
            Booking booking = new Booking(type, name, customerName, date);
            bookings.add(booking);
            bookingListModel.addElement(type + ": " + name + " - Customer: " + customerName + " - Date: " + date);
            saveBookings();
        }
    }
 
    private void removeBooking() {
        int selectedIndex = bookingList.getSelectedIndex();
        if (selectedIndex != -1) {
            bookings.remove(selectedIndex);
            bookingListModel.remove(selectedIndex);
            saveBookings();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a booking to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void updateBooking() {
        int selectedIndex = bookingList.getSelectedIndex();
        if (selectedIndex != -1) {
            Booking booking = bookings.get(selectedIndex);
            JTextField typeField = new JTextField(booking.getType());
            JTextField nameField = new JTextField(booking.getName());
            JTextField customerNameField = new JTextField(booking.getCustomerName());
            JTextField dateField = new JTextField(booking.getDate());
 
            Object[] message = {
                "Type (Hotel/Flight):", typeField,
                "Name:", nameField,
                "Customer Name:", customerNameField,
                "Date:", dateField
            };
 
            int option = JOptionPane.showConfirmDialog(this, message, "Update Booking", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                booking.setType(typeField.getText());
                booking.setName(nameField.getText());
                booking.setCustomerName(customerNameField.getText());
                booking.setDate(dateField.getText());
                bookingListModel.set(selectedIndex, booking.getType() + ": " + booking.getName() + " - Customer: " + booking.getCustomerName() + " - Date: " + booking.getDate());
                saveBookings();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a booking to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                bookings.add(Booking.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }
 
    private void saveBookings() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt"))) {
            for (Booking booking : bookings) {
                bw.write(booking.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TravelManager manager = new TravelManager();
            manager.setVisible(true);
        });
    }
}
