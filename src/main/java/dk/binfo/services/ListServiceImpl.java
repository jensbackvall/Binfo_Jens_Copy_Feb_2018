package dk.binfo.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dk.binfo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import static java.lang.Integer.MAX_VALUE;


/**
 * A service used for generating waitinglists for displaying on screen
 * in a table or for generating a PDF of a list for a specific
 * apartment.
 *
 * The Waitinglist service is used, and just as we can do it in that
 * service, we use length, i.e. number of users in list and priority,
 * i.e. which list we want to display, as parameters.
 *
 * The priorities are:
 * 1. Connect list, for connecting apartments into larger apartments
 * 2. Internal list, for users who already live in the building
 * 3. Family list, for users who are related to Internal users
 * 4. External list, for users who have no relation to the building.
 *
 * @author jensbackvall
 */

@Service("listService")
public class ListServiceImpl implements ListService {

    @Autowired
    private UserService userService;

    @Autowired
    private Waitinglist waitinglist;

    private Font theFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font theSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ListServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * The generatePDF method generates a PDF using the itext API
     * for creating a pdf. It also uses getWaitinglist from the
     * waitinglist service, and therefore takes in the same parameters:
     *
     * @param listLength
     * @param apartmentNumber
     *
     */

    @Override
    public void generateSingleApartmentPDF(int listLength, int apartmentNumber, int list_priority, String filePath) {
        Document theList = new Document();
        List<User> userList = generateSingleApartmentList(listLength, apartmentNumber, list_priority);
        ArrayList<Integer> idList =new ArrayList<>();
        for (User listUser: userList) {
            idList.add(listUser.getUserId());
        }
        loopThroughIdList(idList, theList, filePath);
    }

    @Override
    public void generateCompleteListPDF(int listLength, int priority, String filePath) {

        Document theList = new Document();

        ArrayList<Integer> idList = waitinglist.getSingleWaitinglist(listLength, priority);

        loopThroughIdList(idList, theList, filePath);
    }


    /**
     * The generateList method uses the getSingleWaitinglist method
     * from the waitinglist service to generate a waiting list for a
     * certain priority, as described above. It takes in the
     * parameters needed, being the length, or number of users, in
     * the list as well as the priority or type of waiting list.
     *
     * It returns a list of the desired type, for displaying in a
     * table on screen.
     *
     * @param length
     * @param priority
     * @return
     */

    public List<User> generateList(int length, int priority) {
        List<User> generatedList = new ArrayList<>();
        ArrayList<Integer> idList = waitinglist.getSingleWaitinglist(Integer.MAX_VALUE, priority);
        for (int user_id: idList) {
            User listUser = userService.findUserById(user_id);
            generatedList.add(listUser);
        }
        return generatedList;
    }

    /**
     * The generateSingleApartmentList method uses the getWaitinglist method
     * from the waitinglist service to generate a waiting list for a
     * certain apartment. It takes in the
     * parameters needed, being the length, or number of users, in
     * the list as well as the id of the apartment that is being sold.
     *
     * It returns a list of the desired type, for displaying in a
     * table on screen.
     *
     * @param length
     * @param ApartmentId
     * @return
     */

    public List<User> generateSingleApartmentList(int length, int ApartmentId, int list_priority) {
        List<User> generatedApartmentList = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        try {
            PreparedStatement sql;
            // In the below statement, we are using a view, that does the sorting for us
            if (list_priority == 1) {
                sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("select email " +
                        "from " +
                        "waiting_for_apartment where apartment_id = ?;");
            } else if (list_priority == 2) {
                sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("select email " +
                        "from " +
                        "waiting_for_apartment_2 where apartment_id = ?;");
            } else if (list_priority == 3) {
                sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("select email " +
                        "from " +
                        "waiting_for_apartment_3 where apartment_id = ?;");
            } else {
                sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("select email " +
                        "from " +
                        "waiting_for_apartment_4 where apartment_id = ?;");
            }
            sql.setInt(1, ApartmentId);
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                emailList.add(result.getString("email"));
                while (result.next()) {
                    emailList.add(result.getString("email"));
                }
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        for (String email: emailList) {
            User listUser = userService.findUserByEmail(email);
            generatedApartmentList.add(listUser);
        }
        ArrayList<Integer> neighbourList = new ArrayList<>();
        List<User> newApartmentList = new ArrayList<>();
        if (list_priority == 1) {
            try {
                PreparedStatement sql2 = jdbcTemplate.getDataSource().getConnection().prepareStatement("select " +
                        "neighbour_id from apartment_neighbours where id = ?;");
                sql2.setInt(1, ApartmentId);
                ResultSet result = sql2.executeQuery();
                if (result.next()) {
                    neighbourList.add(result.getInt("neighbour_id"));
                    while (result.next()) {
                        neighbourList.add(result.getInt("neighbour_id"));
                    }
                    System.out.println(neighbourList);
                    System.out.println(generatedApartmentList);
                } else {
                    return null;
                }
                for (int nabo: neighbourList) {
                    for (User user: generatedApartmentList) {
                        if (user.getMyApartment() == nabo) {
                            newApartmentList.add(user);
                        }
                    }
                }
                return newApartmentList;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return generatedApartmentList;
    }

    @Override
    public void loopThroughIdList(ArrayList <Integer> idList, Document theList, String filePath) {
        try {
            PdfWriter.getInstance(theList, new FileOutputStream(new File(filePath))); // filePath ?!?!?!?!?!?!?!

            theList.open();

            Header h = new Header("listHeader","New Apartment List"); // evt dags dato + listetype + lejlighed, eks. "010217_intern_ for lejlighed_23"

            theList.add(h);

            for (int user_id: idList) {
                User listUser = userService.findUserById(user_id);
                Paragraph p = new Paragraph();
                Chunk seniority = new Chunk("\nAncienittet: " + (idList.indexOf(user_id) + 1) + "\n", theFont);
                Chunk name = new Chunk("Navn: " + listUser.getName() + " " + listUser.getLastName() + "\n", theSmallFont);
                Chunk address = new Chunk("Adresse: " + listUser.getAddress() + "\n", theSmallFont);
                Chunk postCode = new Chunk("Postnummer: " + listUser.getPostCode() + "\n", theSmallFont);
                Chunk phoneNumber = new Chunk("Telefonnummer: " + listUser.getPhoneNumber() + "\n", theSmallFont);
                Chunk user_email = new Chunk("E-mail: " + listUser.getEmail() + "\n", theSmallFont);
                p.add(seniority);
                p.add(name);
                p.add(address);
                p.add(postCode);
                p.add(phoneNumber);
                p.add(user_email);
                p.setAlignment(Element.ALIGN_CENTER);
                theList.add(p);
                Paragraph p2 = new Paragraph();
                p2.add("___________________________________________________");
                p2.setAlignment(Element.ALIGN_CENTER);
                theList.add(p2);
            }

            theList.close();

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loopThroughEmailList(ArrayList <String> emailList, Document theList, String filePath) {
        try {
            PdfWriter.getInstance(theList, new FileOutputStream(new File(filePath))); // filePath ?!?!?!?!?!?!?!

            theList.open();

            Header h = new Header("listHeader","New Apartment List"); // evt dags dato + listetype + lejlighed, eks. "010217_intern_ for lejlighed_23"

            theList.add(h);

            for (String email: emailList) {
                User listUser = userService.findUserByEmail(email);
                Paragraph p = new Paragraph();
                Chunk seniority = new Chunk("\nAncienittet: " + (emailList.indexOf(email) + 1) + "\n", theFont);
                Chunk name = new Chunk("Navn: " + listUser.getName() + " " + listUser.getLastName() + "\n", theSmallFont);
                Chunk address = new Chunk("Adresse: " + listUser.getAddress() + "\n", theSmallFont);
                Chunk postCode = new Chunk("Postnummer: " + listUser.getPostCode() + "\n", theSmallFont);
                Chunk phoneNumber = new Chunk("Telefonnummer: " + listUser.getPhoneNumber() + "\n", theSmallFont);
                Chunk user_email = new Chunk("E-mail: " + listUser.getEmail() + "\n", theSmallFont);
                p.add(seniority);
                p.add(name);
                p.add(address);
                p.add(postCode);
                p.add(phoneNumber);
                p.add(user_email);
                p.setAlignment(Element.ALIGN_CENTER);
                theList.add(p);
                Paragraph p2 = new Paragraph();
                p2.add("___________________________________________________");
                p2.setAlignment(Element.ALIGN_CENTER);
                theList.add(p2);
            }

            theList.close();

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

}


