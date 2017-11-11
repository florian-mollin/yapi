package com.mollin.yapi.command;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tester for YeelightCommand class
 */
public class YeelightCommandTest {
    /**
     * Check if all generated commands have unique ID
     */
    @Test
    public void uniqueIdGenerationTest() {
        int numberOfCommandsToGenerate = 100;
        List<Integer> idsList= IntStream.range(0, numberOfCommandsToGenerate)
                .mapToObj(i -> new YeelightCommand("command_" + i, 1, "p2"))
                .map(YeelightCommand::getId)
                .collect(Collectors.toList());
        assertThat(idsList).doesNotContainNull().doesNotHaveDuplicates();
    }
}
