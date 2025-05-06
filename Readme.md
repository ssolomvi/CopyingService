Смоделируйте систему “копировальный сервис”. 

Сервис умеет обрабатывать запросы пользователей (запросы могут поступать не последовательно) на печать 
(фотографии разных форматов, документы формата А4 (например, диплом) в цветном или чёрно-белом варианте и т. д.). 

Для печати по определённому запросу необходимо отправить запрос на определённый принтер 
(первоначально запрос поступает на ч/б принтер, далее на цветной по необходимости (в зависимости от запроса) - примените
шаблон проектирования “цепочка обязанностей”), 

далее настроить целевой принтер под тип документа в запросе (примените паттерн “состояние”) 

и выполнить печать (имитируйте процесс печати ожиданием). 

При печати фотографий рассмотрите варианты, когда в запросе уже есть фотография или же когда её нет 
(когда нет, необходимо проделегировать запрос сервису фотографирования - примените шаблон проектирования “заместитель”). 
Время в системе дискретно. Начальные настройки частей системы должны быть псевдослучайными. 

Продемонстрируйте работу системы. Обеспечьте наглядный вывод информации о процессе работы и результатах работы системы.

![Схема](img/scheme.png)

```plantuml
@startuml DataModel
skinparam classAttributeIconSize 0

abstract class PrintableInColor {
  + UUID id
  + Color color
}

abstract class PrintableWithFile {
  + String filename
}

class PrintablePaper {
  + PaperSize paperSize
}

class PrintablePhoto {
  + PhotoSize photoSize
}

enum Color {
   BLACK_AND_WHITE
   IN_COLOR
}

enum PaperSize {
A0
    A1
    ...
}

enum PhotoSize {
  SIZE_10X15
SIZE_15X21
...
}

PrintableInColor <|-- PrintableWithFile
PrintableWithFile <|-- PrintablePaper
PrintableWithFile <|-- PrintablePhoto
PrintableInColor *-- Color
PrintablePaper *-- PaperSize
PrintablePhoto *-- PhotoSize
@enduml
```
![Модель](img/model.png)

```plantuml
@startuml Services

class PrintController {
    + printPaper(PrintablePaper request)
    + printPhoto(PrintablePhoto request)
}

interface PrintRequestHandler {
+ canHandle(PrintableInColor request): boolean
  + handle(PrintableInColor request)
  + setNext(PrintRequestHandler successor)
}

class PrintService {
  + void print(PrintableInColor request)
}

abstract class StatefulPrinter {
  - PrintState state
  - BlockingQueue<PrintableInColor> queue
  + void setState(PrintState state)
  + void processRequest(PrintableInColor request)
}

class BlackWhitePrinter
class ColorPrinter

interface PrintState<T extends PrintableInColor> {
  + void print(T printable)
}

class PaperPrintState {
  + void print(PrintablePaper printable)
}

class PhotoPrintState {
  + void print(PrintablePhoto printable)
}

class ProxyPhotoPrintState {
  - PhotoService photoService
  + void print(PrintablePhoto printable)
}

class PhotoService {
    + takePhoto(): String
}

PrintController --> PrintService

PrintService --> ColorPrinter
PrintService --> BlackWhitePrinter

PrintRequestHandler <|.. StatefulPrinter

StatefulPrinter <|-- BlackWhitePrinter
StatefulPrinter <|-- ColorPrinter
StatefulPrinter *-- PrintState

PrintState <|.. PaperPrintState
PrintState <|.. PhotoPrintState

PhotoPrintState <|-- ProxyPhotoPrintState
ProxyPhotoPrintState --> PhotoService

@enduml
```
![Сервисы](service.png)

```plantuml
@startuml CopyServiceSequence

skinparam style strictuml
skinparam maxMessageSize 150
skinparam monochrome true
skinparam responseMessageBelowArrow true

actor Клиент as client
participant "PrintController" as controller
participant "PrintService" as service
participant "BlackWhitePrinter" as bw
participant "ColorPrinter" as color
participant "PaperPrintState" as paperState

client -> controller : POST /paper
controller -> service : print(paper)

alt Чёрно-белый документ
  service -> bw : handle(paper)
  bw -> paperState : print(paper)
  paperState --> bw : Печать завершена
  bw --> service : Успех
else Цветной документ
  service -> bw : handle(paper)
  bw -> color : handle(paper)
  color -> paperState : print(paper)
  paperState --> color : Печать завершена
  color --> bw : Передано дальше
  bw --> service : Успех
end

service --> controller : Успешный ответ
controller --> client : Ответ 200 OK

@enduml
```
![Печать документа](img/printPaper.png)

```plantuml
@startuml PhotoPrintingActivity
skinparam monochrome true

start
  :Клиент отправляет запрос POST /photo;
  :PrintService делегирует запрос\nPrintablePhoto обработчику BlackWhitePrinter;

  if (BlackWhitePrinter может выполнить запрос?) then (Да)
    :Перенаправить в BlackWhitePrinter;
  else (Нет)
    :Перенаправить в ColorPrinter;
  endif

  repeat
    if (Фотография передана) then (Да)
    else (Нет)
      :Получить новую фотографию через PhotoService;
    endif
    :Выполнить печать через состояние PhotoPrintState;
  repeat while (Есть ещё запросы?) is (Да)
  ->Нет;


  :Вернуть статус 200 OK;

stop
@enduml
```
![Печать фотографии](img/printPhoto.png)