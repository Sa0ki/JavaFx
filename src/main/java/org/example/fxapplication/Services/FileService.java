package org.example.fxapplication.Services;

import org.example.fxapplication.Models.Client;
import com.google.gson.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class FileService {

    //-------------------FUNCTIONS TXT---------------------------------
    public void writeIntoTxtFile(String nameFile){
        try(FileOutputStream file = new FileOutputStream("src/main/resources/Files/" + nameFile)){
            file.write(("1, Kinan Saad, 2000-08-02, e.saad.kinan@gmail.com\n".getBytes()));
            file.write(("2, Mohamed Ali, 1998-11-25, alioukinan@gmail.com\n".getBytes()));
            file.write(("3, Darik Sarah, 2003-03-14, sardar@gmail.com\n".getBytes()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public List<Client> readFromTxtFile(String nameFile){
        List<Client> list = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Files/Sources/" + nameFile))){

            String line = "";

            //Récupération des informations du fichier inputData.txt

            while((line = reader.readLine()) != null){
                String attributes[] = line.split(", ");
                String dob[] = attributes[3].split("-");

                list.add(new Client(
                        Long.parseLong(attributes[0].trim()),
                        attributes[1].trim(),
                        attributes[2].trim(),
                        LocalDate.of(
                                Integer.parseInt(dob[0].trim()),
                                Integer.parseInt(dob[1].trim()),
                                Integer.parseInt(dob[2].trim())
                        ),
                        attributes[4].trim(),
                        attributes[5].trim(),
                        attributes[6].trim()
                ));
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public Boolean writeListIntoTxtFile(List<Client> list, String nameFile){
        //Insertion des informations dans le fichier outputData.txt
        try(FileOutputStream fileOutPut = new FileOutputStream("src/main/resources/Files/Sauvegardes/" + nameFile);){
            for(Client c: list)
                fileOutPut.write(c.toString().concat("\n").getBytes());
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //-------------------FUNCTIONS XLS----------------------------------
    public void writeIntoExcelFile(String nameFile){
        //Création du classeur
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Création d'une feuille
        XSSFSheet sheet = workbook.createSheet("Clients");

        //Création de lignes et colonnes
        Row rows[] = new Row[2];
        Cell cells[] = new Cell[14];

        for(int i = 0; i < rows.length; i ++)
            rows[i] = sheet.createRow(i);
        for(int j = 0; j < cells.length; j ++)
            cells[j] = rows[j / 7].createCell(j % 7);

        //Insertion de données dans le tableau de cellules
        cells[0].setCellValue("4");
        cells[1].setCellValue("Laki");
        cells[2].setCellValue("Omar");
        cells[3].setCellValue("2001-12-08");
        cells[4].setCellValue("BK763498");
        cells[5].setCellValue("omar2@gmail.com");
        cells[7].setCellValue("qsdqdqd");

        cells[0].setCellValue("5");
        cells[1].setCellValue("Ahmed");
        cells[2].setCellValue("Ali");
        cells[3].setCellValue("1999-07-23");
        cells[4].setCellValue("HF763498");
        cells[5].setCellValue("omar2@gmail.com");
        cells[7].setCellValue("qsdqdqd");

        try(FileOutputStream file = new FileOutputStream("src/main/resources/Files/" + nameFile)){
            workbook.write(file);
            workbook.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public List<Client> readFromExcelFile(String nameFile){
        List<Client> list = new ArrayList<>();
        try(FileInputStream file = new FileInputStream("src/main/resources/Files/" + nameFile)){

            //Création du classeur et récupération de la feuille
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("clients");

            //Création d'un itérateur sur les lignes
            Iterator<Row> rowIterator = sheet.rowIterator();

            //Parcours de lignes
            while(rowIterator.hasNext()){
                Row currentRow = rowIterator.next();

                try{
                    Double id = Double.parseDouble(currentRow.getCell(0).toString());
                    String firstName = currentRow.getCell(1).toString();
                    String lastName = currentRow.getCell(2).toString();
                    String dobString = currentRow.getCell(3).toString();
                    LocalDate dob = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.FRENCH));
                    String cin = currentRow.getCell(4).toString();
                    String email = currentRow.getCell(5).toString();
                    String password = currentRow.getCell(6).toString();


                    list.add(new Client(id.longValue(), firstName, lastName, dob, cin, email, password));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public Boolean writeListIntoExcelFile(List<Client> list, String nameFile){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("clients");

        for(int i = 0; i < list.size(); i ++){
            Row row = sheet.createRow(i);
            Cell cells[] = {row.createCell(0), row.createCell(1), row.createCell(2), row.createCell(3), row.createCell(4), row.createCell(5), row.createCell(6)};
            cells[0].setCellValue(list.get(i).getId());
            cells[1].setCellValue(list.get(i).getFirstName());
            cells[2].setCellValue(list.get(i).getLastName());
            cells[3].setCellValue(list.get(i).getDob().toString());
            cells[4].setCellValue(list.get(i).getCin());
            cells[5].setCellValue(list.get(i).getEmail());
            cells[6].setCellValue(list.get(i).getPassword());
        }
        try(FileOutputStream file = new FileOutputStream("src/main/resources/Files/" + nameFile)){
            workbook.write(file);
            workbook.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //-------------------FUNCTIONS JSON----------------------------------
    public void writeIntoJsonFile(String nameFile){
        try(FileOutputStream file = new FileOutputStream("src/main/resources/Files/" + nameFile)){

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonObject jsonObject1 = new JsonObject();
            JsonObject jsonObject2 = new JsonObject();

            List<JsonObject> jsonList = new ArrayList<>();

            jsonObject1.addProperty("id", 6L);
            jsonObject1.addProperty("fullName", "Karam Ismail");
            jsonObject1.addProperty("dob", "2015-05-30");
            jsonObject1.addProperty("email", "ismail123@gmail.com");

            jsonObject2.addProperty("id", 7L);
            jsonObject2.addProperty("fullName", "Fail Assia");
            jsonObject2.addProperty("dob", "2000-09-02");
            jsonObject2.addProperty("email", "assi23@gmail.com");

            jsonList.add(jsonObject1);
            jsonList.add(jsonObject2);

            String json = gson.toJson(jsonList);

            file.write(json.getBytes());

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public List<Client> readFromJsonFile(String nameFile){
        List<Client> list = new ArrayList<>();
        try{
            JsonParser jsonParser = new JsonParser();
            Object obj = jsonParser.parse(new FileReader("src/main/resources/Files/Sources/" + nameFile));
            JsonArray jsonArray = (JsonArray) obj;
            for(JsonElement client: jsonArray){
                JsonObject jsonObject = client.getAsJsonObject();
                list.add(new Client(
                        jsonObject.get("id").getAsLong(),
                        jsonObject.get("firstName").getAsString(),
                        jsonObject.get("lastName").getAsString(),
                        LocalDate.parse(jsonObject.get("dob").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        jsonObject.get("cin").getAsString(),
                        jsonObject.get("email").getAsString(),
                        jsonObject.get("password").getAsString()
                ));
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public Boolean writeListToJsonFile(List <Client> list, String nameFile){
        try(FileOutputStream file = new FileOutputStream("src/main/resources/Files/Sauvegardes/" + nameFile)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<JsonObject> listJson = new ArrayList<>();

            for(Client c: list){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", c.getId());
                jsonObject.addProperty("firstName", c.getFirstName());
                jsonObject.addProperty("lastName", c.getFirstName());
                jsonObject.addProperty("dob", c.getDob().toString());
                jsonObject.addProperty("cin", c.getCin());
                jsonObject.addProperty("email", c.getEmail());
                jsonObject.addProperty("password", c.getPassword());
                listJson.add(jsonObject);
            }

            String json = gson.toJson(listJson);
            file.write(json.getBytes());
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
