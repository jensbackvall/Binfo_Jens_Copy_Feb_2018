package dk.binfo.services;

import dk.binfo.models.User;

import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.*;

/**
 * The Interface for creating waitinglists for displaying on screen
 * or in a PDF
 *
 * @author jensbackvall
 */

public interface ListService {
    void generateSingleApartmentPDF(int listLength, int apartmentNumber, int list_priority, String filePath);
    void generateCompleteListPDF(int listLength, int priority, String filePath);
    List<User> generateList(int length, int priority);
    List<User> generateSingleApartmentList(int length, int ApartmentId, int list_priority);
    void loopThroughEmailList(ArrayList<String> emailList, Document theList, String filePath);
    void loopThroughIdList(ArrayList<Integer> idList, Document theList, String filePath);

}