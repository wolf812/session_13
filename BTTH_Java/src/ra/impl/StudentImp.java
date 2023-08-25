package ra.impl;

import ra.entity.Student;

import javax.imageio.IIOException;
import java.io.*;
import java.util.*;

public class StudentImp {
    static List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("*********************MENU*******************************");
            System.out.println("1. Nhập thông tin các sinh viên");
            System.out.println("2. Tính tuổi các sinh viên");
            System.out.println("3. Tính điểm trung bình và xếp loại sinh viên");
            System.out.println("4. Sắp xếp sinh viên theo tuổi tăng dần");
            System.out.println("5. Thống kê sinh viên theo xếp loại sinh viên");
            System.out.println("6. Cập nhật thông tin sinh viên theo mã sinh viên");
            System.out.println("7. Tìm kiếm sinh viên theo tên sinh viên");
            System.out.println("8. Thoát");
            System.out.println("-----------------------------------------------------");

            System.out.print("Lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Hãy nhập thông tin sinh viên.");
                        StudentImp.inputDataStd(sc);
                        break;
                    case 2:
                        break;
                    case 3:
                        StudentImp.calAvg_rank();
                        break;
                    case 4:
                        StudentImp.sortStudent();
                        break;
                    case 5:
                        StudentImp.thongKe();
                        break;
                    case 6:
                        StudentImp.updateStudent(sc);
                        break;
                    case 7:
                        StudentImp.searchStudentByName(sc);
                        break;
                    case 8:
                        writeDataToFile();
                        System.exit(0);
                    default:
                        System.err.println("Vui lòng chọn từ 1 - 8");
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Vui lòng chọn số");
            } catch (Exception ex) {
                System.err.println("Có lỗi không xác định, vui lòng liên hệ.");
            }
        } while (true);
    }

    public static void displayStdData() {
        for (Student std :
                studentList) {
            std.displayData();
        }
    }

    public static void inputDataStd(Scanner sc) {
        System.out.println("số lượng sinh viên cần nhập thông tin là: ");
        do {
            try {
                int number = Integer.parseInt(sc.nextLine());
                for (int i = 0; i < number; i++) {
                    System.out.println("Nhập thông tin sinh viên thứ " + (i + 1));
                    Student student = new Student();
                    student.inputData(sc, studentList);
                    studentList.add(student);
                }
                break;
            } catch (NumberFormatException nfe) {
                System.err.println();
            }
        } while (true);
    }

    public static void writeDataToFile() {
        //1. Khởi tạo đối tượng file để làm việc với file
        File file = new File("listStudent.txt");

        FileOutputStream fos = null;
        ObjectOutput oos = null;

        try {
            //2. Khởi tạo đối tượng FileOutputStream từ file - checked Excetion
            fos = new FileOutputStream(file);

            //3. khởi tạo đối tượng ObjectOutputStream để ghi ra file
            oos = new ObjectOutputStream(fos);

            //4. sử dụng writeObject để ghi object ra file
            oos.writeObject(studentList);

            //5. đẩy dữ liệu từ stream xuống file
            oos.flush();
        } catch (FileNotFoundException ex1) {
            System.err.println("File không tồn tại.");
        } catch (Exception ex2) {
            System.err.println("Lỗi khi ghi đè dữ liệu ra file.");
        } finally {
            //6. Đóng cá Stream
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IIOException ex1) {
                System.err.println("Xảy ra lỗi khi đóng các stream");
            } catch (Exception ex) {
                System.err.println("Xảy ra lỗi trong quá trình đóng các stream");
            }
        }
    }

    public static List<Student> readDataFromFile() {
        //1. Khởi tạo đối tượng File
        File filelistStudent = new File("filelistStudent.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<Student> studentListRead = null;
        try {
            //2. Khởi tạo đối tượng FileInputStream
            fis = new FileInputStream(filelistStudent);

            //3. Khởi tạo đối tượng ObjectInputStream
            ois = new ObjectInputStream(fis);
        } catch (FileNotFoundException ex1) {
            System.err.println("Không tồn tại file");
        } catch (IOException ex2) {
            System.err.println("Lỗi khi đọc file");
        } catch (Exception ex) {
            System.err.println("Có lỗi trong quá trình đọc dữ liệu từ file");
        } finally {
            //6. Đóng các stream
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex1) {
                System.err.println("Có lỗi khi đóng stream");
            } catch (Exception ex) {
                System.err.println("Có lỗi trong quá trình đóng các stream");
            }
        }


        return studentListRead;
    }

    //    public static void calListAge() {
//        for (Student student : studentList) {
//            student.calAge();
//        }
//        System.out.println("Đã tính xong tuổi cho tất cả sinh viên");
//    }
    public static void calAvg_rank() {
        for (Student student : studentList) {
            student.calAvgMark_Rank();
        }
        System.out.println("Đã tính xong điểm trung bình và xếp loại cho tất cả các sinh viên");
    }

    public static void sortStudent() {
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getAge() - o2.getAge();
            }
        });
    }

    public static void updateStudent(Scanner sc) {
        System.out.println("Nhập vào mã sinh viên cần cập nhật:");
        String studentId = sc.nextLine();
        int index = -1;
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId().equals(studentId)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            //Cập nhật
            studentList.get(index).setStudentName(Student.validateStudentName(sc));
            studentList.get(index).setSex(Student.validateSex(sc));
            studentList.get(index).setBirthday(Student.valiDate(sc));
            studentList.get(index).setMark_html(Student.validateMarkHTML(sc));
            studentList.get(index).setMark_css(Student.validateMarkCSS(sc));
            studentList.get(index).setMark_js(Student.validateMarkJS(sc));
            studentList.get(index).calAge();
            studentList.get(index).calAvgMark_Rank();
        } else {
            System.out.println("Không tồn tại mã sinh viên như vậy");
        }
    }

    public static void searchStudentByName(Scanner sc) {
        System.out.println("Nhập vào tên sinh viên cần tìm: ");
        String studentName = sc.nextLine();
        for (Student student : studentList) {
            if (student.getStudentName().toLowerCase().contains(studentName.toLowerCase())) {
                student.displayData();
            }
        }
    }

    public static void thongKe() {
        int cntYeu = 0;
        int cntTb = 0;
        int cntKha = 0;
        int cntGioi = 0;
        int cntXS = 0;
        for (Student student : studentList) {
            if (student.getStudentRank().equals("Yếu")) {
                cntYeu++;
            } else if (student.getStudentRank().equals("Trung bình")) {
                cntTb++;
            } else if (student.getStudentRank().equals("Khá")) {
                cntKha++;
            } else if (student.getStudentRank().equals("Giỏi")) {
                cntGioi++;
            } else {
                cntXS++;
            }
        }
        System.out.printf("Thống kê: Xuất sắc: %d - Giỏi: %d - Khá: %d - Trung bình: %d - Yếu: %d\n", cntXS, cntGioi, cntKha, cntTb, cntYeu);

    }
}

