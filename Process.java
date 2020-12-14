package com.company;

import java.util.Random;

public class Process {
    private final int pid;
    private int executionTime;
    private static final int minExecutionTime = 30;
    private static final int maxExecutionTime = 100;
    private static final int minIOTime = 20;
    private static final int maxIOTime = 300;
    private static final Random random = new Random();
    private int[] ioTimes;

    public int getIOTime() {
        int result = 0;
        for (int i = 0; i < ioTimes.length; i++) {
            result += ioTimes[i];
        }
        return result;
    }

    protected Process(int pid, int executionTime, int[] ioTimes) {
        this.pid = pid;
        this.executionTime = executionTime;
        this.ioTimes = new int[ioTimes.length];
        for (int i = 0; i < ioTimes.length; i++) {
            this.ioTimes[i] = ioTimes[i];
        }
    }

    public Process(int pid, int ioThreadCount) {
        this.pid = pid;
        this.executionTime = random.nextInt(maxExecutionTime - minExecutionTime) + minExecutionTime;
        if (ioThreadCount > 0) {
            this.ioTimes = new int[ioThreadCount];
            for (int i = 0; i < ioThreadCount; i++)
                this.ioTimes[i] = random.nextInt(maxIOTime - minIOTime) + minIOTime;
        } else {
            this.ioTimes = new int[0];
        }
    }

    public boolean isCompleted() {
        return executionTime <= 0;
    }

    public boolean isAwaitingIO() {
        return getIOTime() > 0;
    }

    public int getIoTime() {
        return getIOTime();
    }

    public int awaitIO() {
        if (isAwaitingIO()) {
            System.out.print(this.toString());
            System.out.println(" ожидает устроиство ввода/вывода");
            return getIOTime();
        } else {
            return 0;
        }
    }

    public int awaitIO(int amount) {
        if (isAwaitingIO()) {
            for (int i = 0; i < ioTimes.length; i++) {
                if (ioTimes[i] > 0) {
                    System.out.print(this.toString());
                    System.out.println(" ожидает устроиство ввода/вывода #" + i);
                    if (ioTimes[i] <= amount) {
                        int res = amount - ioTimes[i];
                        ioTimes[i] = 0;
                        return res;
                    } else {
                        ioTimes[i] -= amount;
                        return amount;
                    }
                }
            }
            return 0;
        } else {
            return 0;
        }
    }

    public int run(int quantTime) {
        System.out.print(this.toString());
        System.out.print(" время выполненния: ");
        if (executionTime >= quantTime) {
            System.out.println(quantTime);
            executionTime -= quantTime;
            return quantTime;
        } else {
            int res = executionTime;
            System.out.println(executionTime);
            executionTime = 0;
            System.out.println("Процесс завершил свое выполнение");
            return res;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Процесс PID = ");
        sb.append(pid);
        return sb.toString();
    }

    public Process clone() {
        return new Process(this.pid, this.executionTime, ioTimes);
    }
}

