package pa06;

import java.util.*;
import java.util.ArrayList;
import java.io.*;

public class KMean {
    public static void main(String[] args) throws FileNotFoundException {
        //read file and decide number of clusters
        Scanner console = new Scanner(System.in);
        System.out.println("What is your file name?");
        Scanner input = new Scanner(new File(console.next()));
        System.out.println("How many clusters do you want?");
        int numClusters = console.nextInt();

        //import original data
        ArrayList<Sample> originalData = new ArrayList<>();
        while (input.hasNext()) {
            //System.out.println("Has next line: " + input.hasNextLine());
            //System.out.println(input.nextInt() + " " + input.nextInt() + "\n");
            originalData.add(new Sample((double)input.nextInt(), (double)input.nextInt()));
        }
        System.out.println(originalData.toString());


        //generate random cluster points
        Random rand = new Random();
        Sample[] clusterPoints = new Sample[numClusters];
        for (int i = 0; i < numClusters; i++) {
            //do sth. for repeated sample points
            clusterPoints[i] = originalData.remove(rand.nextInt(originalData.size()));
        }


        //add deleted data back to the original data
        for(int i = 0; i < numClusters; i++){
            originalData.add(clusterPoints[i]);
        }

        //divide into clusters
        //ArrayList<Sample>[] clusters = new ArrayList<Sample>()[numClusters];
        //build array list for each cluster point
        ArrayList[] clusters = new ArrayList[numClusters];
        for (int i = 0; i < numClusters; i++) {
            clusters[i] = new ArrayList<Sample>();
        }

        //repeat 100 times
        for (int i = 0; i < 9; i++) {
            System.out.println("round: " + i);
            //input samples into each cluster
            for (Sample data : originalData) {
                double minDistance = data.getDistance(clusterPoints[0]);
                System.out.println("start minDistance: " + minDistance);
                int index = 0;
                //find the closest points
                System.out.println(data.toString());
                for (int k = 1; k < numClusters; k++) {
                    System.out.println("cluster point # " + k);
                    //System.out.println(" cluster point" + clusterPoints[k]);
                    System.out.println("distance: " + data.getDistance(clusterPoints[k]));
                    if (data.getDistance(clusterPoints[k]) < minDistance) {
                        minDistance = data.getDistance(clusterPoints[k]);
                        index = k;
                        // System.out.println("index: " + index);
                        System.out.println("minDistance now: " + minDistance);
                    }
                    System.out.println();
                }
                clusters[index].add(data);
                System.out.println("this data go to cluster: " + index);
            }
            //replace the old cluster points
            for (int h = 0; h < clusterPoints.length; i++) {
                Cluster cluster = new Cluster(clusterPoints[h], clusters[h]);
                clusterPoints[h] = cluster.getNewCluster();
            }
        }

        for(int i = 0; i < clusterPoints.length; i++){
            System.out.println(clusterPoints[i].toString());
        }
    }
}

