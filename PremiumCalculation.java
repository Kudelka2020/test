package by.iba.myPackage;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class PremiumCalculation { //исходный класс
    public static void myFunc(Functional functional){
        functional.func();
    }
    public static void main(String[] args) throws java.io.IOException{
        Staff.counter = 0;
        Employee<Integer> employees[] = new Employee[1000]; //создание массива класса Сотрудник
        employees[Staff.counter++] = new Employee("Иванов", "Иван", "Иванович", 23432, Subdivision.salesDepartment, 5.3f, 850.0f, 5538.75f);
        employees[Staff.counter++] = new Employee ("Петров", "Петр", "Петрович", 52675, Subdivision.apparatusControl, 1.4f, 1500f);
        employees[Staff.counter++] = new Employee ("Федоров", "Федор", "Федорович", 45784, Subdivision.salesDepartment, 10.4f, 1000f, 3047.4f);
        employees[Staff.counter++] = new Employee ("Андреев", "Андрей", "Андреевич", 11245, Subdivision.salesDepartment, 0.5f, 900f, 1632.7f);
        employees[Staff.counter++] = new Employee ("Александрова", "Александра", "Александровна", 34643, Subdivision.apparatusControl, 12.7f, 950f);
        employees[Staff.counter++] = new Employee ("Семенов", "Семен", "Семенович", 87425, Subdivision.salesDepartment, 2.4f, 600f, 823.3f);
        employees[Staff.counter++] = new Employee ("Романов", "Роман", "Романович", 56784, Subdivision.salesDepartment, 6.3f, 760f, 1232f);
        employees[Staff.counter++] = new Employee ("Валентинова", "Валентина", "Валентиновна", 88532, Subdivision.salesDepartment, 5.9f, 890f, 4032.3f);
        employees[Staff.counter++] = new Employee ("Артемов", "Артем", "Артемович", 25675, Subdivision.salesDepartment, 1.7f, 380f);
        employees[Staff.counter++] = new Employee ("Владимиров", "Владимир", "Владимирович", 35673, Subdivision.salesDepartment, 3.6f, 900f, 9111.9f);
        employees[Staff.counter++] = new Employee ("Вадимов", "Вадим", "Вадимович", 36732, Subdivision.salesDepartment, 8.8f, 540f, 483.1f);
        Source source = new Source(5000f, 2000, 1f, 2f, 3f, 5f, 3f);
        Staff outOb = new Staff(employees);
        outOb.SerializableStaff();
        char choice, ignore;
        for (;;) {
            myFunc(new Functional() {
                @Override
                public void func() {
                    System.out.println("1 - Просмотреть исходные данные");
                    System.out.println("2 - Просмотреть список сотрудников");
                    System.out.println("3 - Проанализировать данные");
                    System.out.println("4 - Рассчет премирования сотрудников");
                    System.out.println("5 - Выход из программы");
                }
            });
            choice = (char) System.in.read();
            do {
                ignore = (char) System.in.read();
            } while (ignore != '\n');
            switch (choice) {
                case '1':
                    source.display();
                    break;
                case '2':
                    minorMenu :
                    {
                        try (ObjectInputStream objIStrm = new ObjectInputStream(new
                                FileInputStream("outOb"))) {
                            Staff outOb2 = (Staff) objIStrm.readObject();
                            for (int i = 0; i < Staff.counter; i++) {
                                System.out.println("Сотрудник № " + (i + 1) + "\n" + outOb2.staff2[i] + "\n");
                            }

                        } catch (Exception е) {
                            System.out.println("Исключение при десериализации:"+ е);
                        }



                        char choice2;
                        for (; ; ) {
                            myFunc(new Functional() {
                                @Override
                                public void func() {
                                    System.out.println("\n1 - Добавить сотрудника \n2 - Удалить сотрудника \n3 - Возврат в главное меню");
                                }
                            });
                            choice2 = (char) System.in.read();
                            do {
                                ignore = (char) System.in.read();
                            } while (ignore != '\n');
                            switch (choice2) {
                                case '1':
                                    Scanner scan = new Scanner(System.in);
                                    System.out.print("Введите код ID: ");
                                    int ID = scan.nextInt();
                                    outOb.newEmployee(ID);
                                    outOb.SerializableStaff();
                                    break;
                                case '2':
                                    outOb.deleteEmployee();
                                    outOb.SerializableStaff();
                                    break;
                                case '3':
                                    break minorMenu;
                                default:
                                    System.out.print("Ошибка. Сделайте свой выбор еще раз.");
                                    break;
                            }
                        }
                    } break;
                case '3':
                    outOb.analyze();
                    break;
                case '4':
                    BigDecimal total = new BigDecimal("0.00");
                    System.out.println("Сумма премии к выплате (бел.руб.):");
                    for(int i = 0; i < Staff.counter; i++) {
                        System.out.printf("%10.2f " + outOb.staff2[i].surname + " " + outOb.staff2[i].name + " " + outOb.staff2[i].patronymic + "\n", outOb.staff2[i].calculationPremium(source));
                        total = total.add(new BigDecimal(String.valueOf(outOb.staff2[i].calculationPremium(source))));

                    }
                    System.out.printf("Итого: ");
                    System.out.println(total.setScale(3, RoundingMode.HALF_EVEN).toString());
                    break;
                case '5':
                    return;
                default:
                    System.out.print("Ошибка. Сделайте свой выбор еще раз.\n");
                    break;
            }
        }
    }
}

