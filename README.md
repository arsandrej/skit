# Software Quality and Testing – Laboratory Exercises

## Short Description

This repository contains my solutions to the laboratory exercises for the **Software Quality and Testing** course. The course covers fundamental and advanced software testing techniques, including input space partitioning, graph-based testing, logic-based testing, mutation testing, and testing of object-oriented and web applications. Each lab exercise focuses on a specific testing criterion, requiring both theoretical analysis (e.g., partitioning, coverage criteria) and practical implementation using **JUnit** tests. The repository also includes some automated tests generated with the help of AI tools.

---

## Goals of the Subjects and What I’ve Learned

### Subjects Covered

| Topic | Key Techniques & Criteria |
|-------|---------------------------|
| **Input Space Partitioning (ISP)** | Characteristics, partitions (disjointness, completeness), Base Choice Coverage (BCC) |
| **Graph-based Testing** | Control flow graphs, du-paths, All-Du-Paths coverage, Prime Path Coverage (PPC), Edge-Pair Coverage |
| **Logic-based Testing** | Predicates, clauses, active clauses, GACC (General Active Clause Coverage), RACC (Restricted Active Clause Coverage), truth tables |
| **Mutation Testing** | Generating mutants, kill vs. live mutants |
| **Automated & Modular Testing** | JUnit framework, parameterized tests, test doubles |


### What I Have Learned

- **How to design test suites systematically** using partitioning and coverage criteria, not just ad‑hoc testing.
- **The importance of disjointness and completeness** in input space partitioning – and how to fix partitions when these properties are violated.
- **Graph coverage criteria** (node, edge, edge‑pair, prime path, and data flow – du‑paths) and how to derive minimal test sets from a graph.
- **Logic coverage** (GACC, RACC) by identifying predicates and clauses, building truth tables, and generating parameterized JUnit tests that satisfy specific active clause criteria.
- **Practical JUnit testing** – writing assertions, using `@ParameterizedTest`, `@CsvSource`, and integrating AI tools to speed up test generation.
- **Mutation testing** – how to assess test suite quality by checking how many artificially seeded faults (mutants) are detected.

I also gained confidence in **automating the testing process** and relating theoretical coverage models to executable test code.

---

## Overview of the Repo

The repository is organized into two main folders: `docs/`, which contains the detailed explanations and problem statements for each laboratory exercise, and `src/`, which holds all corresponding Java source code (main classes along with their JUnit 5 test classes). Each lab builds directly on core course topics such as input space partitioning, graph‑based and logic‑based modeling, syntactic modeling, and mutation testing, with all tests written in JUnit 5 to satisfy specific coverage criteria (e.g., Base Choice Coverage, Prime Path Coverage, RACC).
