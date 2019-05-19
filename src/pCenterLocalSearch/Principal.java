package pCenterLocalSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args)
	{
		//Leer archivo
		Scanner sc = new Scanner(System.in);
		System.out.println("Name of the file:");
		String filename = sc.nextLine();
		
		int total_sites = 0;
		int p = 0;
		Integer[] open_sites;
		Integer[] closed_sites;
		ArrayList<Integer[]> sites_clients;
		LocalSearch ls;
		
		try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(filename)))
		{
			String currentLine;
			double i = 0;
			open_sites = new Integer[1];
			sites_clients = new ArrayList<Integer[]>();
			
			while ((currentLine = bufferedReader.readLine()) != null) {
				//Tomar total y cuantos sites tienen que abrirse
				if (i == 0)
				{
					String[] renglon = currentLine.split(" ");
					total_sites = Integer.parseInt(renglon[0]);
					p = Integer.parseInt(renglon[1]);
				}
				
				//Hacer arreglo de los sites abiertos
				if (i == 2)
				{
					String[] renglon = currentLine.split(" ");
					open_sites = new Integer[p];
					for (int j = 0; j < renglon.length; j++)
					{
						open_sites[j] = Integer.parseInt(renglon[j]);
					}
				}
				
				if (i > 2)
				{
					String[] renglon = currentLine.split(" ");
					Integer[] sit_cli = new Integer[renglon.length];
					
					for (int j = 0; j < renglon.length; j++)
					{
						sit_cli[j] = Integer.parseInt(renglon[j]);
					}
					
					sites_clients.add(sit_cli);
				}
				i++;
			}
			//Buscar cuales sites estan cerrados
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
			//Crear Clase LocalSearch
			ls = new LocalSearch(total_sites, p, open_sites, closed_sites, sites_clients);
			//Imprimir resultado
			ls.Resolve();
			ls.Print();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
