generate the UML diagrams with
```
plantuml -tsvg README.md
```

design diagram
```plantuml
@startuml roadsim
!option handwritten true

class Panel
class ParameterPanel extends Panel
class MonitorPanel extends Panel

@enduml
```

![](roadsim.svg)
