package pCenterLocalSearch;

import java.util.ArrayList;

public class LocalSearch {
	
	int Sites;
	int P;
	Integer[] OpenSites;
	Integer[] ClosedSites;
	ArrayList<Integer[]> SitesClients;
	double[][] matrixDistances;
	double TotalDistance;
	double CalulatedTotalDistance;
	ArrayList<ArrayList<Integer>> clientsFacilities;
	
	LocalSearch (int sites, int p, Integer[] open_sites, Integer[] closed_sites, ArrayList<Integer[]> sites_clients, ArrayList<ArrayList<Double>> distancesClientsSites, double total_distance) {
		this.Sites = sites;
		this.P = p;
		this.OpenSites = open_sites;
		this.ClosedSites = closed_sites;
		this.SitesClients = sites_clients;
		this.TotalDistance = total_distance;
		this.CalulatedTotalDistance = 0;
		
        double[][] matrixDistances = new double[distancesClientsSites.size()][];
        for (int i = 0; i < matrixDistances.length; i++) {
            matrixDistances[i] = new double[distancesClientsSites.get(i).size()];
        }
        for (int i = 0; i < distancesClientsSites.size(); i++) {
            for (int j = 0; j < distancesClientsSites.get(i).size(); j++) {
                matrixDistances[i][j] = distancesClientsSites.get(i).get(j);
            }
        }
        this.matrixDistances = matrixDistances;
	}
	
	public void ResolveFirstFound()
	{
		// First Found
		for (int i = 0; i < OpenSites.length; i++)
		{
			for (int j = 0; j < ClosedSites.length; j++)
			{
				Move1(OpenSites[i], ClosedSites[j]);
				Distribute();
				Summatory();
				if (isGoodMove())
				{
					return;
				}
			}
		}
	}
	
	public void Print()
	{
		System.out.println("-----OUTPUT-----");
		System.out.println("Heuristic TotalDistance: " + TotalDistance);
		System.out.println("LocalSearch TotalDistance: " + CalulatedTotalDistance);
		System.out.println("-----NEW OPEN SITES-----");
		for (Integer n : OpenSites)
		{
			System.out.print(n + " ");
		}
	}
	
	void Distribute()
	{
		//4. Distribute the clients to their nearest facilities and compute the total distance
		int nearestFacility = 0;
		double nearestFacilityDistance = 0.0;
		double temporalNearestFacilityDistance = 0.0;
		clientsFacilities = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < Sites; i++) {
			ArrayList<Integer> temporalArray = new ArrayList<Integer>();
			clientsFacilities.add(temporalArray);
		}

		for (int client = 0; client < matrixDistances.length; client++)
		{
			boolean firstOne = true;
			for (int facility : OpenSites) {
				if (firstOne == true) {
					nearestFacility = facility;
					nearestFacilityDistance = matrixDistances[client][facility];
					firstOne = false;
				} else {
					temporalNearestFacilityDistance = matrixDistances[client][facility];
					if (temporalNearestFacilityDistance < nearestFacilityDistance) {
						nearestFacility = facility;
						nearestFacilityDistance = matrixDistances[client][facility];
					}
				}
			}
			clientsFacilities.get(nearestFacility).add(client);
		}
	}
	
	void Summatory()
	{
		CalulatedTotalDistance = 0;
		for (int i = 0; i < OpenSites.length; i++)
		{
			int site = OpenSites[i];
			for (Integer client : clientsFacilities.get(i))
			{
				CalulatedTotalDistance += matrixDistances[client][site];
			}
		}
	}
	
	boolean isGoodMove()
	{
		return CalulatedTotalDistance <= TotalDistance;
	}
	
	void Move1 (int i, int j)
	{
		int tmp = this.OpenSites[i];
		this.OpenSites[i] = this.ClosedSites[j];
		this.ClosedSites[j] = tmp;
		return;
	}
}
