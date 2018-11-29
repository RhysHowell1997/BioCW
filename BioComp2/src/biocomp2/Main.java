/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biocomp2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Rhys Howell
 */
public class Main {

    public static Random random = new Random();

   //parameters for GA     
    public static int p_size = 600; //size of population | 600 for data1 and data2
    public static int numGen = 1000; //number of generations
    public static int g_size = 60; //amount of genes | 60 for data1 | 80 for data2 | gives ten rules for each data set
    public static float mr = (float) 0.005; //Mutation Rate | 0.005 for data1 and data2 

    //choose which file you would like: "data1.txt" or "data2.txt"
    public static String file = "data1.txt";

     

    public static void crossover(Individual[] population) {
        int i;
        for (int x = 0; x < population.length - 1; x += 2) {
            for (int y = 0; y < population[x].getGenes().length / 2; y++) {
                i = population[x].getGenes()[y];
                population[x].setGene(population[x + 1].getGenes()[y], y);
                population[x + 1].setGene(i, y);
            }
        }
    }

    public static void mutation(float mr, Individual[] population) {
        for (int x = 0; x < population.length; x++) {
            for (int y = 0; y < population[x].getGenes().length; y++) {
                if (random.nextFloat() <= mr) {
                    if ((y % Individual.num_rules) == Individual.num_rules - 1) {
                        population[x].setGene(((population[x].getGenes()[y] + 1) % 2), y);
                    } else {
                        population[x].setGene(((population[x].getGenes()[y] + 1) % 3), y);
                    }
                }
            }
        }
    }
    
    
        
     //this is where we take the best individual for the next generation
    public static void replacement(Individual best, Individual[] population) {
        int worst = 0;
        for (int x = 0; x < population.length; x++) {
            if (population[worst].getFitness() > population[x].getFitness()) {
                worst = x;
            }
        }

        population[worst] = best;
    }

    
    // This is where data 1 or data2 are read in
    public static void main(String[] args) throws IOException {

        int[] training_data = null;
        Individual bestIndividual;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String allText = "";
            String currentLine = null;
            while ((currentLine = bufferedReader.readLine()) != null) {
                currentLine = currentLine.replaceAll("\\s+", "");
                allText = allText + currentLine;
            }
            int lines = allText.length() / 6;
            training_data = new int[allText.length()];
            for (int x = 0; x < allText.length(); x++) {
                training_data[x] = allText.charAt(x) - '0';
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + file + "'");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

       
        //this where the initail population is created
        Individual[] population = new Individual[p_size];
        for (int x = 0; x < population.length; x++) {
            population[x] = new Individual(new int[g_size]);
        }
        for (int x = 0; x < p_size; x++) {
            population[x].populating();
        }
        Calc.find_fitness(population, training_data);
        int gen0_range = (Calc.best_individual(population).getFitness() - Calc.worst_individual(population).getFitness());
        System.out.println("in generation 0: ");
        System.out.println("the mean fitness is: " + Calc.mean_fitness(population));
        System.out.println("the best is: " + Calc.best_individual(population).getFitness());
        System.out.println("the worst is: " + Calc.worst_individual(population).getFitness());
        System.out.println("the range is: " + gen0_range + "\n");

        bestIndividual = Individual.duplicate(Calc.best_individual(population));

       
        FileWriter fileWriter = new FileWriter("results.csv");
        fileWriter.append("generation,mean,best,worst,range\n");
        fileWriter.append("0" + "," + Calc.mean_fitness(population) + "," + Calc.best_individual(population).getFitness() + "," + Calc.worst_individual(population).getFitness() + "," + gen0_range + "\n");

        
        // This is where the bulk of the GA happens, parents are selected and then a crossover point is chosen at random.
        // Then mutation can be applied to the offspring
        for (int z = 0; z < numGen; z++) {
            
            //choose Selection.tournament_selection or Selection.roulette_selection
            Individual[] parents = Selection.tournament_selection(population);
            crossover(parents);
            Individual[] offspringPop = parents;
            mutation(mr, offspringPop);
            Calc.find_fitness(offspringPop, training_data);
            population = offspringPop;

            replacement(bestIndividual, population);

            int gen = z + 1; 
            int range = (Calc.best_individual(population).getFitness() - Calc.worst_individual(population).getFitness());

            System.out.println("For generation: " + gen + "\nthe mean is: " + Calc.mean_fitness(population));
            System.out.println("the best is: " + Calc.best_individual(population).getFitness());
            System.out.println("the worst is: " + Calc.worst_individual(population).getFitness());
            System.out.println("the range is: " + range + "\n");
            fileWriter.append(gen + "," + Calc.mean_fitness(population) + "," + Calc.best_individual(population).getFitness() + "," + Calc.worst_individual(population).getFitness() + "," + range + "\n");
            bestIndividual = Individual.duplicate(Calc.best_individual(population));
        }

        for (int x = 0; x < bestIndividual.getGenes().length; x++) {
            if ((x % Individual.num_rules) == 0) {
                System.out.println("");
            }
            if ((x % Individual.num_rules) == (Individual.num_rules - 1)) {
                System.out.print(" ");
            }
            if (bestIndividual.getGenes()[x] == 2) {
                System.out.print("#");
            } else {
                System.out.print(bestIndividual.getGenes()[x]);
            }

        }

        fileWriter.flush();
        fileWriter.close();

    }

}
