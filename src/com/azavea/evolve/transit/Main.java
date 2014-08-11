package com.azavea.evolve.transit;

import com.sun.xml.internal.ws.api.client.SelectOptimalEncodingFeature;
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
        // TODO: Create a city generator to read in real data.
        ArrayList<CityCell> cells = new ArrayList<CityCell>();
        // Values are:
        // Generation, AbsoluteAttraction, X, Y
        // Column 0, top to bottom
        cells.add(new CityCell(2.0, 0.34, 0, 4));
        cells.add(new CityCell(15.0, 2.55, 0, 3));
        cells.add(new CityCell(1.0, 0.17, 0, 2));
        cells.add(new CityCell(1.0, 0.17, 0, 1));
        cells.add(new CityCell(1.0, 0.17, 0, 0));

        // Column 1, top to bottom
        cells.add(new CityCell(1.0, 0.17, 1, 4));
        cells.add(new CityCell(2.0, 0.34, 1, 3));
        cells.add(new CityCell(5.0, 0.85, 1, 2));
        cells.add(new CityCell(1.0, 0.17, 1, 1));
        cells.add(new CityCell(1.0, 0.17, 1, 0));

        // Column 2, top to bottom
        cells.add(new CityCell(1.0, 0.17, 2, 4));
        cells.add(new CityCell(5.0, 0.85, 2, 3));
        cells.add(new CityCell(25.0, 4.25, 2, 2));
        cells.add(new CityCell(5.0, 0.85, 2, 1));
        cells.add(new CityCell(1.0, 0.17, 2, 0));

        // Column 3
        cells.add(new CityCell(1.0, 0.17, 3, 4));
        cells.add(new CityCell(10.0, 1.7, 3, 3));
        cells.add(new CityCell(50.0, 8.5, 3, 2));
        cells.add(new CityCell(10.0, 1.7, 3, 1));
        cells.add(new CityCell(1.0, 0.17, 3, 0));

        // Column 4
        cells.add(new CityCell(1.0, 0.17, 4, 4));
        cells.add(new CityCell(5.0, 0.85, 4, 3));
        cells.add(new CityCell(10.0, 1.7, 4, 2));
        cells.add(new CityCell(10.0, 1.7, 4, 1));
        cells.add(new CityCell(5.0, 0.85, 4, 0));

        // Column 5
        cells.add(new CityCell(1.0, 0.17, 5, 4));
        cells.add(new CityCell(1.0, 0.17, 5, 3));
        cells.add(new CityCell(1.0, 0.17, 5, 2));
        cells.add(new CityCell(5.0, 0.85, 5, 1));
        cells.add(new CityCell(20.0, 3.4, 5, 0));

        // Scale things so that there is room for the values to oscillate.
        double scaleCost = 1.0;
        double scaleValue = 2.0;
        City city = new City(cells, scaleCost, scaleValue);

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
        TransitSystem fittest = engine.evolve(500, 1, new Stagnation(1000, true));
        System.out.println("Outputting Transit System Links\n");
        for(TransitLink tl: fittest.getLinks()) {
            System.out.println(tl.toString() + "\n");
        }

        System.out.println("Done.");

    }
}
