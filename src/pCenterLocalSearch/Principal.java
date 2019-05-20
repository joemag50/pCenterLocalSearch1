package pCenterLocalSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Author José Gamboa
public class Principal {

	public static void main(String[] args)
	{
		//Read File
		Scanner sc = new Scanner(System.in);
		System.out.println("Name of the file:");
		String filename = sc.nextLine();
		
		int total_sites = 0;
		int p = 0;
		Integer[] open_sites;
		Integer[] closed_sites;
		ArrayList<Integer[]> sites_clients;
		LocalSearch ls;
		ArrayList<ArrayList<Double>> distancesClientsSites = new ArrayList<ArrayList<Double>>();
		double total_distance = 0;
		double total_time = 0;
		
		try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(filename)))
		{
			String currentLine;
			double i = 0;
			open_sites = new Integer[1];
			sites_clients = new ArrayList<Integer[]>();
			int data = 0;
			
			while ((currentLine = bufferedReader.readLine()) != null) {
				if (currentLine.equals("")) {
					data++;
				} else {
					switch (data) {
						case 0: {
							// Read total sites, and p sites
							String[] renglon = currentLine.split(" ");
							total_sites = Integer.parseInt(renglon[0]);
							p = Integer.parseInt(renglon[1]);
							break;
						}
						case 1: {
							// Make array of open sites
							String[] renglon = currentLine.split(" ");
							open_sites = new Integer[p];
							for (int j = 0; j < renglon.length; j++)
							{
								open_sites[j] = Integer.parseInt(renglon[j]);
							}
							break;
						}
						case 2: {
							String[] renglon = currentLine.split(" ");
							Integer[] sit_cli = new Integer[renglon.length];
							
							for (int j = 0; j < renglon.length; j++)
							{
								sit_cli[j] = Integer.parseInt(renglon[j]);
							}
							
							sites_clients.add(sit_cli);
							break;
						}
						case 3: {
							String[] splittedLine = currentLine.split(" ");
							ArrayList<Double> splittedDouble = new ArrayList<Double>();;
							for (String s : splittedLine) {
								String replace = s.replace(",", ".");
								splittedDouble.add(Double.parseDouble(replace));
							}
							distancesClientsSites.add(splittedDouble);
							break;
						}
						case 4: {
							total_distance = Double.parseDouble(currentLine);
							break;
						}
						case 5: {
							total_time = Double.parseDouble(currentLine);
							break;
						}
					}
				}
			}

			// Search Closed sites
			closed_sites = new Integer[total_sites - p];
			int l = 0;
			Arrays.sort(open_sites);
			for (int k = 0; k < total_sites; k++)
			{
				if (Arrays.binarySearch(open_sites, k) < 0)
				{
					closed_sites[l] = k;
					l++;
				}
			}
			// Class LocalSearch
			ls = new LocalSearch(total_sites, p, open_sites, closed_sites, sites_clients, distancesClientsSites, total_distance);
			// Resolve
			ls.ResolveFirstFound();
			ls.Print();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
