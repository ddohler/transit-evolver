package com.azavea.evolve.transit;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.Stagnation;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Stub so that this will compile.
        // TODO: Put in a "real" city.
        ArrayList<CityCell> cells = new ArrayList<CityCell>();

        City city = new City(cells);

        CandidateFactory<TransitSystem> candidateFactory = new TransitSystemCandidateFactory(city, 1.0);
        List<EvolutionaryOperator<TransitSystem>> operators
                = new LinkedList<EvolutionaryOperator<TransitSystem>>();
        operators.add(new TransitSystemCrossover(1));
        operators.add(new TransitSystemMutation(city, new Probability(0.10)));

        EvolutionaryOperator<TransitSystem> pipeline = new EvolutionPipeline<TransitSystem>(operators);

        FitnessEvaluator<TransitSystem> fitnessEvaluator = new TransitSystemEvaluator(city);

        SelectionStrategy<Object> selectionStrategy = new RouletteWheelSelection();
        Random rng = new MersenneTwisterRNG();

        EvolutionEngine<TransitSystem> engine = new GenerationalEvolutionEngine<TransitSystem>(candidateFactory,
                pipeline,
                fitnessEvaluator,
                selectionStrategy,
                rng);

        engine.addEvolutionObserver(new EvolutionObserver<TransitSystem>() {
            @Override
            public void populationUpdate(PopulationData<? extends TransitSystem> populationData) {
                System.out.printf("Generation %d: %s\n", populationData.getGenerationNumber(),
                        populationData.getBestCandidateFitness());
            }
        });

        // Actually evolve it
        TransitSystem fittest = engine.evolve(50, 1, new Stagnation(1000, true));
        System.out.println("Done.");
    }
}