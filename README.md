# Cuidador de Tamagotchis

Programa de consola en Java que simula a un cuidador con varios Tamagotchis. Cada Tamagotchi es un hilo con vida propia y solo se relaciona con el cuidador, que es el programa principal.

## Qué se puede hacer

Desde el menú, el cuidador puede:

1. **Dar de comer:** cada Tamagotchi tarda un tiempo distinto.
2. **Bañar:** deja la suciedad a 0.
3. **Jugar:** el Tamagotchi plantea una suma y sigue preguntando hasta que se acierta.
4. **Ver estado:** muestra qué hace cada uno y cuánta suciedad tiene.
5. **Acabar:** mata a los Tamagotchis uno por uno, esperando a que estén ociosos, y cierra el programa.

Un Tamagotchi solo hace una acción a la vez. Se va ensuciando con el tiempo: avisa al llegar a 5 de suciedad y muere al llegar a 10. También muere al terminar su tiempo de vida.

## Cómo ejecutarlo

Importa el proyecto en Eclipse como proyecto Maven y ejecuta la clase `App`.

## Valores que se pueden modificar

Son constantes al principio de cada clase.

| Constante | Clase | Qué controla | Valor |
|---|---|---|---|
| `CANTIDAD_TAMAGOTCHIS` | `App` | Número de Tamagotchis | 3 |
| `TIEMPO_COMER_MIN_MS` | `App` | Tiempo mínimo de comer | 1000 ms |
| `TIEMPO_COMER_MAX_MS` | `App` | Tiempo máximo de comer | 5000 ms |
| `TIEMPO_ESPERA_ACABAR_MS` | `App` | Espera antes de cerrar el programa | 5000 ms |
| `TIEMPO_BANO_MS` | `Tamagotchi` | Duración del baño | 5000 ms |
| `SUCIEDAD_AVISO` | `Tamagotchi` | Suciedad a la que avisa | 5 |
| `SUCIEDAD_MUERTE` | `Tamagotchi` | Suciedad a la que muere | 10 |
| `INTERVALO_SUCIEDAD_MS` | `ControladorTiempo` | Cada cuánto aumenta la suciedad | 20000 ms |
| `TIEMPO_VIDA_MS` | `ControladorTiempo` | Tiempo de vida | 300000 ms |
