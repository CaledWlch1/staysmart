public class Booking {
private String type;
private String name; 
private String customerName;
private String date; 

public Booking(String type, String name, String customerName, String date) {
this.type = type;
this.name = name;
this.customerName = customerName;
this.date = date;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCustomerName() {
return customerName;
}

public void setCustomerName(String customerName) {
this.customerName = customerName;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String toString() {
return type + "," + name + "," + customerName + "," + date;
}

public static Booking fromString(String line) {
String[] parts = line.split(",");
return new Booking(parts[0], parts[1], parts[2], parts[3]);
}
}
