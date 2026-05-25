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

ProfugoDecorator implementa *todos* los métodos de `IProfugo` porque es un *wrapper completo*. Desde afuera, un `ProfugoDecorator` se comporta exactamente como un `IProfugo` — el cliente (cualquier código que use un `IProfugo`) no sabe si está hablando con un `Profugo` base, un decorador, o una pila de decoradores.

== Conclusión: la gracia del patrón

Cada decorador agrega una responsabilidad única sin modificar la clase base, y se pueden combinar arbitrariamente sin explosión de subclases. La interfaz `IProfugo` es el pegamento que hace esto posible: sin ella, no podríamos apilar comportamientos de forma transparente.

== Patrón Singleton en `Agencia`

La Agencia usa Singleton porque existe una única agencia coordinando a todos los cazadores, consistente con el README.

```java
public class Agencia {

    // Atributo estático privado: guarda la única instancia de la clase.
    // static: el campo existe sin necesidad de una instancia previa.
    //         Sin static haría falta new Agencia() para que exista,
    //         pero el constructor privado lo impide.
    // final:  la referencia no se puede reasignar. Sin final, alguien
    //         podría pisar INSTANCIA con otra instancia y romper el
    //         singleton.
    private static final Agencia INSTANCIA = new Agencia();

    // Constructor privado: evita que externos creen instancias con new
    private Agencia() {}

    // Método estático público: punto de acceso global a la única instancia.
    // static: al pertenecer a la clase y no a una instancia, puede
    //         llamarse con Agencia.getInstancia() desde cualquier parte.
    public static Agencia getInstancia() {
        return INSTANCIA;
    }
}
```

Los tres ingredientes del Singleton:

1. *Atributo estático privado* (`private static final`) — guarda la única instancia.
2. *Constructor privado* (`private Agencia()`) — nadie afuera puede hacer `new`.
3. *Método estático público* (`public static Agencia getInstancia()`) — punto de acceso global.

== Parte III — Reportería con `Agencia` y `flatMap`

Para consolidar las capturas usamos `Agencia` que centraliza todos los cazadores.

=== ¿Por qué `flatMap` para obtener todos los prófugos capturados?

Cada cazador tiene su propio `Set<IProfugo> profugosCapturados`.
Tenemos una lista de cazadores → una lista de listas de profugos.

Con `map` obtendríamos un `Stream<Set<IProfugo>>` (stream de sets),
pero nosotros queremos un `Stream<IProfugo>` con todos los profugos
aplanados en un solo flujo. Ahí entra `flatMap`:

```java
public Set<IProfugo> getProfugos() {
    return cazadores.stream()
        .flatMap(c -> c.getProfugosCapturados().stream())
        .collect(Collectors.toSet());
}
```

Visualmente:

```
cazadores = [cazador1,      cazador2,      cazador3]
                |               |              |
           {p1, p2}         {p3, p4}        {p5}

flatMap → [p1, p2, p3, p4, p5]
```

Sin `flatMap` tendríamos que anidar dos loops (cazadores + profugos).
`flatMap` hace exactamente eso, pero funcional.

=== ¿Cómo funciona `max()` con `Comparator`?

`max()` arranca con el primer elemento como "ganador". Después agarra
cada elemento de la lista de a uno y le pregunta al comparador:

"Entre este nuevo (`candidato`) y el que va ganando (`ganador`),
¿cuál es más grande?"

El comparador devuelve un número:
- *Positivo* → el candidato es más grande → `max()` reemplaza al
  ganador (`ganador = candidato`).
- *Negativo* → el candidato es más chico → el ganador sigue igual.
- *Cero* → son iguales → el ganador sigue igual.

Al final, el que sobrevivió a todas las comparaciones es el resultado.

Ejemplo con lista `[3, 7, 2, 9, 1]`:

```
Arranca: ganador = null

Paso 1: agarra el 3
        ganador es null → 3 es el nuevo ganador
        ganador = 3

Paso 2: agarra el 7
        comparador.compare(7, 3) → 7.compareTo(3)
        → devuelve 1 (positivo, 7 > 3)
        positivo → 7 es el nuevo ganador
        ganador = 7

Paso 3: agarra el 2
        comparador.compare(2, 7) → 2.compareTo(7)
        → devuelve -1 (negativo, 2 < 7)
        negativo → 7 sigue siendo ganador
        ganador = 7

Paso 4: agarra el 9
        comparador.compare(9, 7) → 9.compareTo(7)
        → devuelve 1 (positivo, 9 > 7)
        positivo → 9 es el nuevo ganador
        ganador = 9

Paso 5: agarra el 1
        comparador.compare(1, 9) → 1.compareTo(9)
        → devuelve -1 (negativo, 1 < 9)
        negativo → 9 sigue siendo ganador
        ganador = 9

Resultado: 9
```

Cada vez que `max()` agarra un elemento, lo compara con el ganador
actual usando la función que le pasaste. Esa función siempre recibe
*dos* valores: el nuevo candidato y el que va ganando hasta ese momento.

==== `Comparator.comparing` en `getProfugoMasHabil`

En la Agencia:

```java
public IProfugo getProfugoMasHabil() {
    return getProfugos()
        .stream()
        .max(Comparator.comparing(IProfugo::getHabilidad))
        .orElse(null);
}
```

`Comparator.comparing(IProfugo::getHabilidad)` internamente equivale a:

```java
new Comparator<IProfugo>() {
    @Override
    public int compare(IProfugo a, IProfugo b) {
        return a.getHabilidad().compareTo(b.getHabilidad());
    }
}
```

`comparing` extrae el `Integer` de cada profugo usando `getHabilidad()`
y luego usa el `compareTo` nativo de `Integer` para decidir cuál es más
grande. Es `a.compareTo(b)` pero aplicado a las habilidades extraídas,
no a los profugos directamente.
