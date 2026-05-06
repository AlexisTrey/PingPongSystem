# Ping Pong Game

## Autor
**Yulian Alexis Tobar Rios**

Código: 202222448

---

## Tecnologías utilizadas

* Java 21
* Maven
* Arquitectura MVP
* Interfaz gráfica con Java Swing
* Concurrencia con hilos (Thread / Runnable)
* Librería externa de componentes (components-lib)
* FlatLaf (Look & Feel moderno)

---

## Funcionalidades principales

* Juego de ping-pong para un jugador
* Raqueta controlada con el mouse
* Agregar pelotas durante la partida
* Cada pelota corre en su propio hilo
* Iniciar, pausar, reanudar y reiniciar la partida
* Ajustar velocidad de las pelotas con `+` y `-`
* Cambio de tema visual (Claro / Oscuro)
* Panel de información con:
    * Hora de inicio de la partida
    * Tiempo transcurrido en formato hh:mm:ss
    * Rebotes por pelota

---

## Reglas del juego

* El juego inicia con una sola pelota
* Se pueden agregar más pelotas durante la partida
* La partida termina cuando una pelota supera la raqueta

---

## Requisitos

* Java 21 o superior
* Maven

---

## Compilar el proyecto

```bash
mvn clean compile
```

---

## Generar el JAR

```bash
mvn clean package
```

El archivo generado se encontrará en:

```
target/PingPongSystem-1.0.0.jar
```

---

## Ejecutar el proyecto

```bash
java -jar target/PingPongSystem-1.0.0.jar
```

---

## Dependencias

El proyecto utiliza las siguientes dependencias:

* **flatlaf 3.7.1**
* **components-lib 1.0.0** 

---

## Versión

**1.0.0**
