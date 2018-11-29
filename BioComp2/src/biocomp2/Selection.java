/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biocomp2;

import static biocomp2.Main.random;

/**
 *
 * @author Rhys Howell
 */
public class Selection {
     
    //tournament selection
    public static Individual[] tournament_selection(Individual[] population) {

        Individual[] parents = new Individual[population.length];

        for (int x = 0; x < population.length; x++) {

            Individual first_parent = Individual.duplicate(population[random.nextInt(population.length)]);
            Individual second_parent = Individual.duplicate(population[random.nextInt(population.length)]);

            if (first_parent.getFitness() >= second_parent.getFitness()) {
                parents[x] = first_parent;

            } else {
                parents[x] = second_parent;
            }
        }
        return parents;
    }

//    //roulette selection
//    public static Individual[] roulette_selection(Individual[] population) {
//
//        Individual[] parents = new Individual[population.length];
//
//        for (int x = 0; x < population.length; x++) {
//
//            int running_total = 0;
//            int total_fitness = 0;
//            total_fitness = total_fitness + population[x].getFitness();
//            int selection = random.nextInt(total_fitness);
//
//            int y = 0;
//            while (running_total <= selection) {
//                running_total += population[y].getFitness();
//                y++;
//            }
//            parents[x] = population[y - 1];
//            
//        }
//        return parents;
//    }
            
    
}
