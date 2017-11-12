package com.mollin.yapi.flow;

import com.mollin.yapi.enumeration.YeelightFlowAction;
import com.mollin.yapi.flow.transition.YeelightTransition;
import com.mollin.yapi.utils.YeelightUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a flow
 */
public class YeelightFlow {
    /**
     * Represents an infinite count for a flow
     */
    public static int INFINITE_COUNT = 0;

    /**
     * Flow count
     */
    private int count;
    /**
     * Flow action
     */
    private YeelightFlowAction action;
    /**
     * Flow transitions list
     */
    private List<YeelightTransition> transitions;

    /**
     * Constructor for a flow
     * @param count Number of times to run this flow
     * @param action Action after flow stops
     */
    public YeelightFlow(int count, YeelightFlowAction action) {
        this.count = Math.max(0, count);
        this.action = action == null ? YeelightFlowAction.RECOVER : action;
        this.transitions = new ArrayList<>();
    }

    /**
     * Getter for transitions list
     * @return Transitions list
     */
    public List<YeelightTransition> getTransitions() {
        return this.transitions;
    }

    /**
     * Create params array for a command
     * @return Params array
     */
    public Object[] createCommandParams() {
        String flowExpression = this.transitions.stream()
                .map(t -> YeelightUtils.joinIntArray(",", t.getTuple()))
                .collect(Collectors.joining(","));
        return new Object[] {
                this.count * this.transitions.size(),
                this.action.getValue(),
                flowExpression
        };
    }
}
