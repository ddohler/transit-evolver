package com.azavea.evolve.transit;

import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class TransitSystemEvaluatorTest {
    City thirtyCells = Cities.thirtyCells();
    CostValueRatioEvaluator evaluator = new CostValueRatioEvaluator(thirtyCells);
    Random rng = new MersenneTwisterRNG();
    TransitSystemCandidateFactory candidate_factory = new TransitSystemCandidateFactory(thirtyCells, 0.2);
    List<TransitSystem> population;

    @org.junit.Before
    public void setUp() throws Exception {
        population = candidate_factory.generateInitialPopulation(500, rng);
        for (TransitSystem system: population) {
            List<TransitLink> systemLinks = system.getLinks();
            System.out.println();
        }
    }

    @org.junit.After
    public void tearDown() throws Exception {
        population = null;
    }

    @org.junit.Test
    public void testPopulationFitnessIdempotence() throws Exception {
        // Make sure a population evaluates the same way every time.
        double fitnessSum = popTotalFitness(population);
        double prevSum = fitnessSum;
        for (int i = 0; i < 20; ++i) {
            assertEquals(prevSum, fitnessSum, 0.0);
            prevSum = fitnessSum;
            fitnessSum = popTotalFitness(population);
        }
    }

    @org.junit.Test
    public void testBestCandidateIdempotence() throws Exception {
        // Make sure the best candidate's fitness is the same every time.
        double bestFitness = popBestFitness(population);
        double prevBestFitness = bestFitness;
        for (int i = 0; i < 20; ++i) {
            assertEquals(prevBestFitness, bestFitness, 0.0);
            prevBestFitness = bestFitness;
            bestFitness = popBestFitness(population);
        }
    }

    /**** WARNING: This test is stochastic, so it may fail occasionally. This is normal, but consistent failures should
     * be investigated.
     * @throws Exception
     */
    @org.junit.Test
    public void testMutationProbability() throws Exception {
        // Ensures that a population mutates at no higher a rate than the probability given.
        // Uses fitness as a proxy for mutations; the number of systems with a given fitness shouldn't change
        // too quickly over time.
        TreeMap<Double, Integer> prevFitnessCounts = countFitnesses(population);
        double probability = 0.10;
        Probability mutationProbability = new Probability(probability);
        TransitSystemMutation mutator = new TransitSystemMutation(thirtyCells, mutationProbability);
        List<TransitSystem> newPop = mutator.apply(population, rng);
        TreeMap<Double, Integer> fitnessCounts = countFitnesses(population);
        double percentChangeSum = 0.0;
        int numTimes = 500;
        for (int i = 0; i < numTimes; ++i) {
            double percentChanged = percentChangedFitness(prevFitnessCounts, fitnessCounts);
            percentChangeSum += percentChanged;
            prevFitnessCounts = fitnessCounts;
            newPop = mutator.apply(newPop, rng);
            fitnessCounts = countFitnesses(newPop);
        }
        assertEquals(probability, (percentChangeSum/numTimes),0.01);
    }

    @org.junit.Test
    public void testIsNatural() throws Exception {
        assert evaluator.isNatural() == true;
    }

    /*
     *  Helper methods
     */
    private double percentChangedFitness(TreeMap<Double, Integer> baseCounts, TreeMap<Double, Integer> newCounts) {
        int totalCount = 0;
        int changeCount = 0;
        for (Iterator<Double> iter = newCounts.keySet().iterator(); iter.hasNext();) {
            Double fitness = iter.next();
            Integer baseCountForFitness = baseCounts.get(fitness);
            // Increment changeCount if fitness doesn't exist in the base or the number of occurrences has changed.
            if (baseCountForFitness == null || baseCountForFitness != newCounts.get(fitness)) {
                changeCount++;
            }
            totalCount++;
        }
        return (double)changeCount / (double)totalCount;
    }

    private TreeMap<Double, Integer> countFitnesses(List<TransitSystem> population) {
        TreeMap<Double, Integer> counts = new TreeMap<Double, Integer>();
        for (TransitSystem system: population) {
            Double fitness = evaluator.getFitness(system, population);
            Integer count = counts.get(fitness);
            if (count == null) {
                counts.put(fitness, 1);
            } else {
                counts.put(fitness, count + 1);
            }
        }
        return counts;
    }

    private double popTotalFitness(List<TransitSystem> population) {
        double fitnessSum = 0.0;
        for (TransitSystem system: population) {
            fitnessSum += evaluator.getFitness(system, population);
        }
        return fitnessSum;
    }

    private double popBestFitness(List<TransitSystem> population) {
        double bestFitness = 0.0;
        for (TransitSystem system: population) {
            double currFitness = evaluator.getFitness(system, population);
            if (currFitness > bestFitness) {
                bestFitness = currFitness;
            }
        }
        return bestFitness;
    }
}