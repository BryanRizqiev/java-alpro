import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Coba {

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

        for (int i = 0; i<datas.length; i++) {
            if (datas[i] != null) {
                System.out.println((i+1) + ".\t" + datas[i]);
            }
        }

    }

    public static void create(String[] datas) throws Exception {

        FileWriter writer = new FileWriter("databases.txt");
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("\nBerapa list yang ingin anda masukkan ? "); int j = Integer.parseInt(scan.readLine());
        System.out.println("Masukkan list : ");
        show(datas);
        int check = checkLastDataIsNull(datas);
        for (int i=check; i < (j+check); i++) {
            System.out.print((i+1) + ".\t");
            datas[i] = scan.readLine();
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

        System.out.print("\nPilih nomor berapa yang ingin anda ubah : "); int choise = Integer.parseInt(scan.readLine());
        System.out.print("Masukkan list yang baru : "); String data = scan.readLine();
        datas[choise-1] = data;
        for (String list : datas) {
            if (list != null) {
                writer.write(list + "\n");
            }
        }

        writer.close();

    }

    public static void delete(String[] datas) throws Exception {

        FileWriter writer = new FileWriter("databases.txt");
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("\nPilih nomor berapa yang ingin anda hapus : "); int choise = Integer.parseInt(scan.readLine());
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

            System.out.println("1. Tampilkan list");
            System.out.println("2. Menambah list");
            System.out.println("3. Update list");
            System.out.println("4. Hapus list");
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

                default:
                    System.out.print("\nInputan tidak diketahui");
            }

            System.out.print("\n\tLagi ? (y) ");
            yesOrNo = scan.readLine();

        } while (yesOrNo.equalsIgnoreCase("y"));

    }
}
