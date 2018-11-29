/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biocomp2;


import java.util.Random;

/**
 *
 * @author Rhys Howell
 */
public class Individual {
    
    public static Random random = new Random();
    
    private int[] genes;
    private int fitness;
    
    
    final public static int num_rules = 6; //rules | 6 for data1 | 8 for data2
    final public static int training = 32; //training data | 32 for data1 | 64 for data2

    public Individual(int[] genes) {
        this.genes = genes;
        this.fitness = 0;
    }
    
    public void populating(){ 
        for(int i =0; i < this.genes.length; i++){
        	if(((i+1) % num_rules) == 0){
        		this.genes[i] = random.nextInt(2);
        	}
        	else{
        		this.genes[i] = random.nextInt(3);
        	}
        }
    }
    
    public static Individual duplicate(Individual duplicated_individual){
    	int[] duplicated_genes = new int[duplicated_individual.getGenes().length];
    	for(int i = 0; i < duplicated_genes.length; i++){
    		duplicated_genes[i] = duplicated_individual.getGenes()[i];
    	}
    	Individual copy = new Individual(duplicated_genes);
    	copy.fitness = duplicated_individual.fitness;
    	
    	return copy;
    }
    
    public int getFitness() {
        return this.fitness;
    }
      
    
   //here is where the fitness is evaluate by measuring through each rule and its conditions 
    public void evaluate_fitness (int[] training_data){
    	
    	int match_cond = 0; 
    	int training_index = 0;
    	int individual_index = 0;
    	
    	this.fitness = 0;
    	for(int i = 0; i < training; i++){ 
    		for(int j = 0; j < Main.g_size/num_rules; j++ ){ 
    			match_cond = 0;
    			for(int k = 0; k < num_rules -1; k++){ 
    				
    				training_index = (i * num_rules) + k;
    				individual_index = (j * num_rules) + k;
    				
    				if((this.genes[individual_index] == training_data[training_index]) || (this.genes[individual_index] == 2)){
    					match_cond++;
    				}
    				
    			}
    			
    			training_index = (i * num_rules) + num_rules -1; 
				individual_index = (j * num_rules) + num_rules -1;
    			
    			if(match_cond == num_rules -1){ 
    				if((this.genes[individual_index] == training_data[training_index])){
    					this.fitness++;
    				}
    				break;
    			}
    			
    		}
    	}
        
    }
    
    public int[] getGenes() {
        return this.genes;
    }

    public void setGenes(int[] gene) {
        this.genes = gene;
    }
    
    public void setGene(int geneValue, int index){
    	this.genes[index] = geneValue;
    }

}
