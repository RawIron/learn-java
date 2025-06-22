generate the UML diagrams with
```
plantuml -tsvg README.md
```

design diagram
```plantuml
@startuml roadsim
!option handwritten true

class RoadSimGui
class ParameterPanel
class MetricPanel

RoadSimGui *-- ParameterPanel
RoadSimGui *-- MetricPanel

interface Monitor
interface Parameters
interface Animator

MetricPanel ..|> Monitor
ParameterPanel ..|> Parameters
RoadSimGui ..|> Animator

RoadSim <-- Monitor
RoadSim <-- Parameters
RoadSim <-- Animator
class RoadSim
RoadSim --> Road

class Metrics
Monitor <-- Metrics
Road --> Metrics

class Road
Road --> Vehicle

interface Vehicle
class Car

Car ..|> Vehicle

@enduml
```

![](roadsim.svg)

that is a lot of structure for a small problem.
well, it is a java language exercise.
