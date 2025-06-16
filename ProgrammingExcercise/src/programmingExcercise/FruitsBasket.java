package programmingExcercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FruitsBasket {
	static class Fruit {
		String name, color, shape;
		int size;
		int daysInBasket;

		Fruit(String[] data) {
			this.name = data[0];
			this.size = Integer.parseInt(data[1]);
			this.color = data[2];
			this.shape = data[3];
			this.daysInBasket = Integer.parseInt(data[4]);
		}
	}

	public static void main(String[] args) throws Exception {

		String fileName = System.getProperty("user.dir") + "\\basket 1.csv";
		List<Fruit> fruits = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			br.readLine(); // Skip header
			while ((line = br.readLine()) != null) {
				fruits.add(new Fruit(line.split(",")));
			}
		}

		// Total number of Fruits
		int sumSize = 0;
		for (Fruit f : fruits) {
			try {
				sumSize += f.size;
			} catch (NumberFormatException e) {
				sumSize += 0;
			}
		}
		System.out.println("Total number of fruit: " + sumSize);

		// Type of fruit
		Set<String> uniqueNames = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine(); // skip header
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length > 0) {
					uniqueNames.add(parts[0].trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Type of fruit: " + uniqueNames.size());

		// The number of each type of fruit in descending order
		// Count the number of each fruit type
		Map<String, Integer> countByType = new HashMap<>();
		for (Fruit f : fruits) {
			countByType.put(f.name, countByType.getOrDefault(f.name, 0) + 1);
		}
		// Sort the map entries by count in descending order
		List<Map.Entry<String, Integer>> entries = new ArrayList<>(countByType.entrySet());
		entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

		System.out.println("The number of each type of fruit in descending order:");
		for (Map.Entry<String, Integer> entry : entries) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
        
		//Characteristics(size, color,shape,days) of each fruit by type
		System.out.println("\nCharacteristics (size, color, shape, days) of each fruit by type:");
		Map<String, List<Fruit>> fruitsByType = new HashMap<>();

		// Group fruits by their name
		for (Fruit f : fruits) {
			fruitsByType.computeIfAbsent(f.name, k -> new ArrayList<>()).add(f);
		}

		// For each fruit type, collect unique characteristics and print
		for (Map.Entry<String, List<Fruit>> entry : fruitsByType.entrySet()) {
			String type = entry.getKey();
			List<Fruit> fruitList = entry.getValue();

			Set<String> uniqueChars = new HashSet<>();
			for (Fruit f : fruitList) {
				String characteristics = f.size + ", " + f.color + ", " + f.shape + ", " + f.daysInBasket;
				uniqueChars.add(characteristics);
			}

			System.out.println(type + ": " + uniqueChars);
		}

		// Have any fruits been in the basket for over 3 days
		System.out.println("\nHave any fruits been in the basket for over 3 days:");
		Map<String, Integer> over3Days = new HashMap<>();

		for (Fruit f : fruits) {
			if (f.daysInBasket > 3) {
				over3Days.put(f.name, over3Days.getOrDefault(f.name, 0) + 1);
			}
		}

		if (over3Days.isEmpty()) {
			System.out.println("None");
		} else {
			for (Map.Entry<String, Integer> entry : over3Days.entrySet()) {
				String type = entry.getKey();
				int count = entry.getValue();
				String plural = (count > 1) ? "s" : "";
				System.out.println(count + " " + type + plural + " are over 3 days old");
			}
		}
	}

}
