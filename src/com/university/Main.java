package com.university;

import com.university.bl.Util;
import com.university.entity.Lector;
import com.university.service.impl.LectorServiceImpl;
import com.university.service.impl.DepartamentServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        DepartamentServiceImpl universityService = new DepartamentServiceImpl();
        LectorServiceImpl lectorService = new LectorServiceImpl();
        Util util = new Util();

        DatabaseMetaData metaData = util.getConnection().getMetaData();
        ResultSet resultSet = metaData.getTables("DEPARTAMENT", "PUBLIC", "%", null);
        System.out.println(resultSet.next());
        if (!resultSet.next()) {
            Statement stmt = util.getConnection().createStatement();

            stmt.execute("create table if not exists Departaments" +
                    "(id int AUTO_INCREMENT primary key," +
                    "name varchar(50))");

            stmt.execute("create table if not exists Lectors"
                    + "(id int AUTO_INCREMENT primary key, " +
                    "fullName varchar(40)," +
                    "age int," +
                    "degree varchar(30)," +
                    "departament int," +
                    "salary double)");
            stmt.execute("alter table Lectors add foreign key (departament) references Departaments(id)");
            stmt.execute("insert into Departaments(name) values ('Math'), ('Economy'), ('Computer'), ('Security');");
            stmt.execute("insert into Lectors(fullName, age, degree, salary, departament) " +
                    "values ('Damir Idiatulin', 33, 'Head', 9920.23, 3), ('Maksym Kozak', 56, 'Professor', 7698.47, 3)," +
                    "('Vladimir Pesekov', 21, 'Assistant', 6789.21, 3), ('Roman Pasichnyk', 30, 'Associate professor', 8433.99, 3)," +
                    "('Roman Kos', 25, 'Assistant', 6719.21, 3), ('Paul Gaus', 26, 'Assistant', 6489.21, 3)," +
                    "('Volodymyr Kolosov', 56, 'Head', 10202.21, 1),('Vladimir Anisimov', 18, 'Assistant', 4588.21, 1)," +
                    "('Vova Ivaniv', 44, 'Associate professor', 8666.29, 1),('Igor Sorokivskyi', 24, 'Professor', 8712.55, 1)," +
                    "('Andre Galas', 66, 'Head', 10902.21, 2),('Artem Lysiak', 19, 'Assistant', 5500.21, 2)," +
                    "('Yana Farina', 19, 'Assistant', 3500.86, 2),('Stas Palich', 55, 'Associate professor', 7888.21, 2)," +
                    "('Taras Vovk', 19, 'Assistant', 4999.86, 4),('Stas Derevyanchenko', 47, 'Associate professor', 7918.44, 4)," +
                    "('Pasha Baluh', 48, 'Head', 11002.55, 4),('Ivan Gorev', 44, 'Associate professor', 8477.12, 4)," +
                    "('Vladimir Pesekov', 21, 'Assistant', 5555.21, 1), ('Ivan Gorev', 44, 'Professor', 9599.12, 3);");
            stmt.close();
        }
            boolean check = true;
            int i = 0;
            String str = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            showMenu();
            while (check) {
                str = br.readLine();
                try {
                    i = Integer.parseInt(str);
                } catch (Exception e) {
                    System.out.println("Enter number!");
                }

                switch (i) {
                    case 1: {
                        System.out.println("Enter name of departament (Economy, Computer, Security, Math) : ");
                        str = br.readLine();
                        Lector lector = lectorService.getHeadOfDepartament(str);
                        System.out.println("Head of departament = " + lector.getFullName());
                    }
                    break;

                    case 2: {
                        System.out.println("Enter name of departament (Economy, Computer, Security, Math) : ");
                        str = br.readLine();
                        System.out.println(str);
                        System.out.println(lectorService.getDepartamentStatistic(str));
                    }
                    break;

                    case 3: {
                        System.out.println("Enter name of departament (Economy, Computer, Security, Math) : ");
                        str = br.readLine();
                        System.out.println(String.format("%.2f", lectorService.getAverageSalary(str)));
                    }
                    break;

                    case 4: {
                        System.out.println("Enter name of departament (Economy, Computer, Security, Math) : ");
                        str = br.readLine();
                        System.out.println(lectorService.getCount(str));;
                    }
                    break;

                    case 5: {
                        System.out.println("Enter regex : ");
                        str = br.readLine();
                        List<Lector> lectors = lectorService.getByTemplate(str);
                        for (Lector l : lectors) {
                            System.out.println(l.getFullName());
                        }
                    }
                    break;

                    case 6 : {
                        showMenu();
                    }
                    break;

                    case 7: {
                        check = false;
                        System.out.println("Good Bye!");
                    }
                    break;

                    default: {
                        System.out.println("Choose correct number of menu!");
                    }break;
                }
            }
    }

    public static void showMenu() {
        System.out.println("----Menu---- \n" +
                "1 - Who is head of departament \n" +
                "2 - Show departament statistic \n" +
                "3 - Show the average salary of departament \n" +
                "4 - Show count of lectors \n" +
                "5 - Global search by tempalte \n" +
                "6 - menu \n" +
                "7 - exit \n" +
                "Just pick number from the list: \n");
    }
}
