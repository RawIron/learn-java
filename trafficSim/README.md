generate the UML diagrams with
```
plantuml -tsvg README.md
```

design diagram
```plantuml
@startuml roadsim
!option handwritten true

component UI {
    class RoadSimGui
    class ParameterPanel
    class MetricPanel

    RoadSimGui *-- ParameterPanel
    RoadSimGui *-- MetricPanel
}

component Simulator {
    interface Monitor
    interface Parameters
    interface Animator

    class RoadSim

    class Metrics
    Monitor <-- Metrics
}

MetricPanel ..|> Monitor
ParameterPanel ..|> Parameters
RoadSimGui ..|> Animator
RoadSim <-- Monitor
RoadSim <-- Parameters
RoadSim <-- Animator

component Simulating {
    class Road
    Road <-- Vehicle

    interface Vehicle
    class Car

    Car ..|> Vehicle
}

RoadSim <-- Road
Road <-- Metrics

@enduml
```

![](roadsim.svg)

that is a lot of structure for a small problem.
well, it is a java language exercise.
