package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    public static int checkLastDataIsNull(String[] datas) {

        int res = 0;
        for (int i = 0; i < datas.length; i++) {
            if (datas[i] == null) {
                res = i;
                break;
            }
        }
        return res;

    }

    public static void show(String[] datas) {

        System.out.println("------------------------------------------------------------------------------------");

        try {
            for (int i = 0; i<datas.length; i++) {
                if (datas[i] != null) {
                    StringTokenizer stringToken = new StringTokenizer(datas[i], "|");
                    System.out.printf("|\t%-30.30s", (i+1) + ".\t" + stringToken.nextToken());
                    System.out.printf("|\t%10s", "(pada jam : " + stringToken.nextToken() + "\b)\t");
                    System.out.printf("|\t%10s", "(pada hari : " + stringToken.nextToken() + "\b)\n");
                }
            }
            System.out.println("------------------------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void create(String[] datas) throws Exception {

        FileWriter writer = new FileWriter("databases.txt");
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));

        int j = -1;
        boolean retry;
        do {
            try {
                System.out.print("\nBerapa list yang ingin anda masukkan ? ");
                j = Integer.parseInt(scan.readLine());
                retry = false;
            } catch (Exception e) {
                System.err.println("Hanya boleh angka\n");
                retry = true;
            }
        } while (retry);

        if (j == -1) {
            return;
        }

        System.out.println("Masukkan judul list : ");
        show(datas);
        int check = checkLastDataIsNull(datas);
        for (int i=check; i < (j+check); i++) {

            System.out.print("\t" + (i+1) + ".\t");
            String list = scan.readLine();
            System.out.print("Masukkan deskripsi           : "); String descr = scan.readLine();
            descr = descr + "\t\t\t(dibuat pada : " + getDate() + ")";

            int time = -1;
            do {
                try {
                    System.out.print("\nMasukkan jam (0-23)          : ");
                    time = Integer.parseInt(scan.readLine());
                    retry = false;
                    if (time < 0 || time > 23) {
                        throw new Exception("Error");
                    }
                } catch (Exception e) {
                    System.err.println("Hanya boleh angka dan waktu jam (0-23)\n");
                    retry = true;
                }
            }while (retry);

            System.out.print("Masukkan hari (senin-minggu) : "); String day = scan.readLine();
            datas[i] = list + " |" + time + " |" + day + " |" + descr + " ";

        }
        for (String list : datas) {
            if (list != null) {
                writer.write(list + "\n");
            }
        }
        writer.close();

    }

    public static void update(String[] datas) throws Exception {

        FileWriter writer = new FileWriter("databases.txt");
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        String yesOrNo;

        int choise = -1;
        boolean retry;
        do {
            try {
                System.out.print("\nPilih nomor berapa yang ingin anda ubah : ");
                choise  = Integer.parseInt(scan.readLine());
                retry = false;
            } catch (Exception e) {
                System.err.println("\nHanya boleh angka\n");
                retry = true;
            }
        }while (retry);

        if (choise == -1) {
            return;
        }

        do {

            StringTokenizer stringToken = new StringTokenizer(datas[choise - 1], "|");
            String data = stringToken.nextToken();
            data += "\b";
            String tmpT = stringToken.nextToken();
            tmpT += "\b";
            String tmpD = stringToken.nextToken();
            tmpD += "\b";
            String tmpDscr = stringToken.nextToken();
            tmpDscr += "\b";
            System.out.println("-------------------------------------------------");
            System.out.println("|1. Masukkan judul list yang baru               |");
            System.out.println("|2. Masukkan waktu yang baru                    |");
            System.out.println("|3. Masukkan hari yang baru                     |");
            System.out.println("|4. Masukkan deskripsi yang baru                |");
            System.out.println("|5. Keluar                                      |");
            System.out.println("-------------------------------------------------");
            System.out.print("\tPilihan anda : ");

            int input = Integer.parseInt(scan.readLine());

            switch (input) {

                case 1:
                    System.out.print("Masukkan judul list yang baru : ");
                    data = scan.readLine();

                    break;

                case 2:
                    System.out.print("Masukkan waktu yang baru  : ");
                    tmpT = scan.readLine();

                    break;

                case 3:
                    System.out.print("Masukkan hari yang baru : ");
                    tmpD = scan.readLine();

                    break;

                case 4:
                    System.out.print("Masukkan deskripsi yang baru : ");
                    tmpDscr = scan.readLine();

                    break;

                case 5:

                    break;

                default:
                    System.out.print("\nInputan tidak diketahui");
            }

            datas[choise - 1] = data + " |" + tmpT + " |" + tmpD + " |" + tmpDscr + " ";
            yesOrNo = "n";
            if (input != 5) {
                System.out.print("\n\tLagi (halaman update) ? (y) ");
                yesOrNo = scan.readLine();
            }
        } while (yesOrNo.equalsIgnoreCase("y"));

        for (String list : datas) {
            if (list != null) {
                writer.write(list + "\n");
            }
        }

        writer.close();

    }

    public static void showDescr(String[] datas) {

        for (int i = 0; i < datas.length; i++) {
            if (datas[i] != null) {
                StringTokenizer stringToken = new StringTokenizer(datas[i], "|");
                System.out.print((i + 1) + ".\t" + stringToken.nextToken());
                stringToken.nextToken();
                stringToken.nextToken();
                System.out.println("=> " + stringToken.nextToken());
            }
        }

    }

    public static void delete(String[] datas) throws Exception {

        FileWriter writer = new FileWriter("databases.txt");
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));

        boolean retry;
        int choise = -1;
        do {
            try {
                System.out.print("\nPilih nomor berapa yang ingin anda hapus : ");
                choise = Integer.parseInt(scan.readLine());
                retry = false;
            } catch (Exception e) {
                System.err.println("Harus angka\n");
                retry = true;
            }
        } while (retry);

        if (choise == -1) {
            return;
        }

        datas[choise-1] = null;
        Arrays.sort(datas, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
        for (String list : datas) {
            if (list != null) {
                writer.write(list + "\n");
            }
        }
        writer.close();

    }

    public static void main(String[] args) throws Exception {

        FileReader fileInput;
        BufferedReader bufferInput;
        String[] datas = new String[20];
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        String yesOrNo;

        do {

            try {
                fileInput = new FileReader("databases.txt");
                bufferInput = new BufferedReader(fileInput);
            } catch (Exception e) {
                System.err.println("Database tidak ditemukan");
                return;
            }

            //Render data databases.txt
            String data = bufferInput.readLine();
            int i = 0;
            while (data != null) {
                datas[i] = data;
                data = bufferInput.readLine();
                i++;
            }
            Arrays.sort(datas, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));

            System.out.println("\n\t\t\t**********************************************");
            System.out.println("\t\t\t*      Selamat Datang di ToDo List           *");
            System.out.println("\t\t\t*       1. Tampilkan list                    *");
            System.out.println("\t\t\t*       2. Menambah list                     *");
            System.out.println("\t\t\t*       3. Update list                       *");
            System.out.println("\t\t\t*       4. Hapus list                        *");
            System.out.println("\t\t\t*       5. Lihat deskripsi list              *");
            System.out.println("\t\t\t*       6. Keluar                            *");
            System.out.println("\t\t\t**********************************************");
            System.out.print("\n\tPilihan anda : ");
            int choise = Integer.parseInt(scan.readLine());

            switch (choise) {

                case 1:
                    System.out.println("\nTodo list : ");
                    show(datas);

                    break;

                case 2:
                    System.out.println("\nTambah list : ");
                    show(datas);
                    create(datas);

                    break;

                case 3:
                    System.out.println("\nUbah list : ");
                    show(datas);
                    update(datas);

                    break;

                case 4:
                    System.out.println("\nHapus list : ");
                    show(datas);
                    delete(datas);

                    break;

                case 5:
                    System.out.println("\nDeskripsi : ");
                    showDescr(datas);

                    break;

                case 6:

                    break;

                default:
                    System.out.print("\nInputan tidak diketahui");
            }

            yesOrNo = "n";
            if (choise != 6) {
                System.out.print("\n\tLagi (halaman utama) ? (y) ");
                yesOrNo = scan.readLine();
            }
        } while (yesOrNo.equalsIgnoreCase("y"));
    }
}
