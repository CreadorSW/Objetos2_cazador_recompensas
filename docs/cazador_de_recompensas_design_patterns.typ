#import "@preview/octique:0.1.1": *
#import "@preview/codly:1.3.0": *
#import "@preview/codly-languages:0.1.10": *
#codly(languages: codly-languages)


#show: codly-init.with()

#codly(
  zebra-fill: none,
  display-icon: false,
  display-name: false,
)




#set text(size: 12pt, lang: "es", font: "New Computer Modern")

#set page(
  fill: white,
  margin: (top: 2.5cm, right: 2.5cm, left: 3cm, bottom: 3cm),
  numbering: "1 de 1"
)

#show outline.entry.where(level: 1): set text(weight: "bold")
#outline()


#let purpleBranch = rgb("#995BD5")
#let gitBranch = octique-inline("git-branch", color: purpleBranch, width: 0.9em, height: 0.9em, baseline: 25%)


#set table(
    stroke: none,
    gutter: 0.2em,
    fill: (x, y) =>
      if y == 0 {
        gray
      } else if y > 0 {
        rgb("ffdfba").lighten(60%)
      },
    inset: (x: 0.5em, y: 1em),
)

#show table.cell: it => {
    if it.y == 0 {
      set text(
        size: 14pt,
        fill: white,
        weight: "bold"
      )
      it
    } else {
      it
    }
}
#show table.cell.where(x: 0): strong



#v(0.5em)

= Cazador de Recompensas -- (#gitBranch #text(fill: purpleBranch)[decorator-profugos])

== ¿Por qué nos dan la interfaz `IProfugo` desde el comienzo?

Es un indicio fuerte de que vamos a usar Decorator. Sin interfaz, el patrón no es posible, necesitamos un tipo común (`IProfugo`) para que tanto el componente concreto (`Profugo`) como los decoradores sean intercambiables. Si el enunciado fuese distinto (ej: solo un `Profugo` concreto sin evolución), bastaba una clase sola y no hacía falta la interfaz. Al darla desde el principio, nos están diciendo "vas a necesitar polimorfismo sobre `IProfugo`", que es exactamente lo que pide el Decorator cuando los prófugos evolucionan apilando entrenamientos.

== Construcción técnica del Decorator

Tenemos cuatro actores:

#table(
  columns: (auto, auto),
  table.header[Componente][Rol],

  [`IProfugo`],[Contrato. Define todas las operaciones: `getInocencia()`, `getHabilidad()`, `esNervioso()`, `volverseNervioso()`, `dejarDeEstarNervioso()`, `reducirHabilidad()`, `disminuirInocencia()`],
  [`Profugo`],[Componente concreto. Implementa `IProfugo` con estado real. Es el objeto base sin entrenamiento.],
  [`ProfugoDecorator` (abstracta)],[Implementa `IProfugo` y tiene una referencia a un `IProfugo` envuelto (el "wrappee"). Por defecto, delega todos los métodos al wrappee.],
  [Concretos (`ArtesMarciales`, `EntrenamientoElite`, `ProteccionLegal`)],[Extienden `ProfugoDecorator` y overridean solo los métodos que modifican.],
)

`ProfugoDecorator` es la clave: no repite lógica, solo pasa el mensaje al wrappee. En cada override de los concretos hacemos algo antes o después de llamar a `super.metodo()`, o evitamos la llamada si queremos bloquear el comportamiento.

El armado se hace por composición en el constructor:
```java
IProfugo p = new ProteccionLegal(
                new ArtesMarciales(
                    new Profugo(...)));
```

El orden importa: el decorador más externo envuelve al siguiente, que envuelve al siguiente, etc. `p` es un `IProfugo` y el código cliente no sabe ni le importa cuántas capas tiene.

== Cómo resuelve cada entrenamiento su responsabilidad

#table(
  columns: (auto, auto, auto),
  table.header[Decorador][Método overrideado][Comportamiento],

  [`ArtesMarciales`],[`getHabilidad()`],[Obtiene el valor del wrappee (`super.getHabilidad()`), lo duplica, lo capsula a 100 y lo devuelve. El resto de métodos se delegan sin cambios.],
  [`EntrenamientoElite`],[`esNervioso()`],[Siempre devuelve `false`.],
  [`EntrenamientoElite`],[`volverseNervioso()`],[No hace nada (bloquea el efecto).],
  [`EntrenamientoElite`],[`dejarDeEstarNervioso()`],[No hace nada (ya es falso siempre).],
  [`ProteccionLegal`],[`getInocencia()`],[Devuelve `Math.max(40, super.getInocencia())`.],
  [`ProteccionLegal`],[`disminuirInocencia()`],[Puede delegar al wrappee pero la lectura queda pisada por el getter. O bien, directamente implementa el piso en 40.],
)

== Diagrama de clases decorator

#image("uml/diagrama_clases_decorator.svg", width: 100%)

*Leyenda de flechas:*
$arrow.r.dotted$ — implements (realización de interfaz), $arrow.r.filled $ — extends (herencia de clase),
$lozenge arrow.r$ — agregación (envuelve, tiene-un).

`ProfugoDecorator` tiene solo dos cosas que la diferencian de `IProfugo`:

1. *El atributo `wrappee: IProfugo`* — la referencia al objeto envuelto.
2. *Delegación pura* — implementa todos los métodos de `IProfugo` llamando al mismo método del `wrappee`, sin agregar lógica.

Los decoradores concretos extienden `ProfugoDecorator` y *overlinean solo los métodos que modifican*, llamando a `super.metodo()` (que delega al wrappee) y alterando el resultado. Sin el wrappee no hay decoración posible. Es el núcleo del patrón.

== Conclusión: la gracia del patrón

Cada decorador agrega una responsabilidad única sin modificar la clase base, y se pueden combinar arbitrariamente sin explosión de subclases. La interfaz `IProfugo` es el pegamento que hace esto posible: sin ella, no podríamos apilar comportamientos de forma transparente.
