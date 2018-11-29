/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biocomp2;

/**
 *
 * @author Rhys Howell
 */
public class Calc {
    
    
    // below are all calculations for the GA that are used for comparing results
    
    
    public static void find_fitness(Individual[] population, int[] training_data) {
        for (int x = 0; x < population.length; x++) {
            population[x].evaluate_fitness(training_data);
        }
    }

    public static int mean_fitness(Individual[] population) {
        int totalFitness = 0;
        int p_size = population.length;
        int meanFitness;

        for (int x = 0; x < p_size; x++) {
            totalFitness = totalFitness + population[x].getFitness();
        }
        meanFitness = totalFitness / p_size;

        return meanFitness;
    }

    public static Individual best_individual(Individual[] population) {
        Individual bestIndividual = population[0];
        for (int x = 1; x < population.length; x++) {
            if (bestIndividual.getFitness() < population[x].getFitness()) {
                bestIndividual = population[x];
            }
        }
        return bestIndividual;
    }

    public static Individual worst_individual(Individual[] population) {
        Individual worstIndividual = population[0];
        for (int x = 1; x < population.length; x++) {
            if (worstIndividual.getFitness() > population[x].getFitness()) {
                worstIndividual = population[x];
            }
        }
        return worstIndividual;
    }
}
