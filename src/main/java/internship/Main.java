package internship;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
	private static int count = 0;
	private static ArrayList<String> columnValues = new ArrayList<String>();

	// private static ChromeDriver driver;
	private static void readData() {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("pr-data.csv"));
			while (scanner.hasNextLine()) {
				String values = scanner.nextLine();
				columnValues.add(values);
				count++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String status(String url) {
		ChromeDriver driver = new ChromeDriver();
		try {
			driver.get(url);
			String status = driver.findElement(By.className("State")).getText();
			String sstatus;
			if (status.equals("Open"))
				sstatus = "Opened";
			else if (status.equals("Merged"))
				sstatus = "Accepted";
			else
				sstatus = "Rejected";

			return sstatus;
		} catch (NoSuchElementException e) {
			return "Opened";
		} finally {
			driver.close();
		}

	}

	public static void main(String[] args) {
		try {
			// driver=new ChromeDriver();
			readData();
			FileWriter fw;
			String[] status = new String[count];
			status[0] = columnValues.get(0);
			for (int i = 1; i < 5; i++) {
				String[] tmp = columnValues.get(i).split(",", -1);
				if (columnValues.get(i).contains("Opened")) {
					String url = tmp[6];
					tmp[5] = status(url);
					status[i] = String.join(",", tmp);
					System.out.println(status[i]);
				} else if (tmp.length < 6)
					status[i] = columnValues.get(i);
				else
					status[i] = columnValues.get(i);
				System.out.println(i);
				if (i % 200 == 0)
					Thread.sleep(20000);
			}
			fw = new FileWriter("FixedTests_UnfixedODandIDtestsOutput.csv", true);
			int i = 0;
			while (i < count) {
				// System.out.println(status[i]);
				fw.write(status[i] + "\n");
				i++;
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

	}

}
