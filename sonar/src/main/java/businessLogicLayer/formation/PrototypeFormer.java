package businessLogicLayer.formation;

import dataAccessLayer.IOpa;
import dataAccessLayer.IProtocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
/**
 * Created by connorrohricht on 11.01.16.
 * Prototype central processor for semicentralized auction-based team formation in the SONAR cycle.
 * NOTE this version is a prototype and only supports up to 63 different operations.
 * NOTE operations are enumerated internally and 'Long' bitmaps are used to represent plans for simple equality checks.
 */
public class PrototypeFormer {
    //Structure class for root tasks
    private static class Task {
        private final IOpa operator;
        private final IProtocol protocol;
        private Task(IOpa operator, IProtocol protocol) {
            this.operator = operator;
            this.protocol = protocol;
        }
    }

    //The root tasks for the organigram
    private ArrayList<Task> roots;
    private ArrayList<IOpa> operators;
    private ConcurrentSkipListMap<Integer, Long> estimatedBudgets = new ConcurrentSkipListMap<Integer, Long>(); //NOTE for lack of an equivalent with list semantics, add inner scale for collision avoidance in prototype
    private ConcurrentHashMap<Long, Integer> knownBudgets = new ConcurrentHashMap<Long, Integer>();
    private ConcurrentSkipListMap<Long, Integer> discardedFormations = new ConcurrentSkipListMap<Long, Integer>(); //NOTE Assuming a monotonic heuristic for the moment, so no recovery
    private List<TeamOperation> operations = Collections.synchronizedList(new ArrayList<TeamOperation>());
    private static final int scaleFactor = 0x100; //NOTE temporary workaround for the collision issue

    public PrototypeFormer(List<Task> roots, List<IOpa> operators) {
        this.roots = new ArrayList<Task>(roots);
        this.operators = new ArrayList<IOpa>(operators);
    }

    private synchronized void assessBestFormation() {
        Map.Entry<Integer, Long> bestFormation = estimatedBudgets.firstEntry();
        //Only do this when an estimated formation exists
        if (bestFormation == null) return;
        //TODO if formation is complete return as final formation //NOTE should wait for all results?
        //Remove from open list
        estimatedBudgets.remove(bestFormation.getKey());
        knownBudgets.remove(bestFormation.getValue());
        discardedFormations.add(bestFormation.getValue());
        //TODO if the set of operations is known, calculate the set of potential operators (caching recommended) and define as recipients
        //TODO otherwise, broadcast to all: Either the new set of roots or the full formation (access control decision)
    }

    public void processOffer(TeamOperation operation, long baseFormation, int cost, int estimatedRemain) {
        int operationIndex = operations.indexOf(operation);
        if (operationIndex == -1) {
            operations.add(operation);
            operationIndex = operations.indexOf(operation);
        }
        if ((baseFormation & operationIndex) != 0) return;
        //TODO baseFormation must not contain the index
        //TODO baseFormation must be known and estimated
        long offeredFormation = baseFormation | (1 << operationIndex);
        if (discardedFormations.containsKey(offeredFormation)) return; //NOTE Assuming monotonic heuristic
        int knownBudget = knownBudgets.get(baseFormation) + cost;
        if (!estimatedBudgets.containsKey(offeredFormation)) {
            knownBudgets.put(offeredFormation, knownBudget);
            int estimatedBudgetAdjusted = (knownBudget + estimatedRemain) * scaleFactor; //NOTE includes collision workaround
            while (estimatedBudgets.putIfAbsent(estimatedBudgetAdjusted, offeredFormation) != null) estimatedBudgetAdjusted++; //NOTE includes collision workaround
        }
    }
}
