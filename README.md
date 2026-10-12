# Proyecto Final Estructura de Datos - Grupo #

Proyecto final del curso **Estructuras de Datos (SC-304)**, Ingeniería en Sistemas de Computación, Universidad Fidélitas. Profesor: Lic. Jorge Andres Mora M.

Sistema de gestión de cajas de un banco, desarrollado en Java con NetBeans. La interfaz usa `JOptionPane` (Swing). Todo el código fue escrito desde cero por el grupo, sin frameworks ni código previo.

## Integrantes del grupo

- KEVIN JOSUE QUIROS CHAVARRIA
- GUSTAVO CHAVES
- EMMANUEL FRANCISCO MENA CESPEDES
- EMMANUEL RIOS CASTRO

## Descripción

La herramienta permite gestionar las cajas de atención de un banco. Funciona para cualquier nombre de banco y cualquier cantidad de cajas, con un mínimo de tres. La configuración se guarda en `prod.txt`, y el acceso al sistema requiere login de al menos dos usuarios.

## Módulos

| Módulo | Descripción |
|---|---|
| 0. Configuración | Se ejecuta solo la primera vez. Pide el nombre del banco y la cantidad de cajas, y lo guarda en `prod.txt`. Define una caja preferencial, una caja rápida y el resto para dos o más trámites. |
| 1.1 Creación de tiquetes | Crea el tiquete del cliente (nombre, ID, edad, hora de creación, hora de atención, trámite y tipo) y lo coloca en la cola de la caja correspondiente. |
| 1.2 Atención de tiquetes | Atiende de inmediato si la caja está libre y su cola vacía. La función "Tiquete atendido" pasa al siguiente cliente de la cola y registra la hora de atención. |
| 1.3 Llenado de las colas | Asigna cada tiquete a la caja con menos personas en espera. Los tipos P y A van directo a sus cajas especiales. |
| 1.4 Reportes | Caja que atendió más clientes, total de clientes atendidos, caja con mejor tiempo promedio y tiempo promedio general. |

Funciones complementarias:

- Grafo de productos complementarios según el trámite, con recordatorio al llamar al cliente.
- Persistencia de la configuración y de las filas en archivos `.txt`, y registro histórico de tiquetes atendidos en base de datos.
- Consulta del tipo de cambio del dólar mediante el Web Service del Banco Central de Costa Rica.

## Tipos de tiquete

| Tipo | Significado |
|---|---|
| P | Preferencial |
| A | Un solo trámite |
| B | Dos o más trámites |

Trámites disponibles: Depósitos, Retiros y Cambio de Divisas.

## Distribución de responsabilidades

| Área | Responsable |
|---|---|
| Núcleo de colas (módulos 1.1, 1.2 y 1.3) e integración | Emmanuel Rios |
| Configuración, login y archivos `.txt` | Emmanuel Mena Cespedes |
| Base de datos y reportes | Por definir |
| Interfaz, grafo de complementarios y tipo de cambio | Por definir |

## Flujo de trabajo en Git

1. Todo el código se comparte por GitHub
2. Antes de empezar, hacer `pull` de `main`.
3. Trabajar siempre en una rama propia. No se trabaja directo en `main`.
4. Commits pequeños y frecuentes, con mensajes claros.
5. Al terminar una parte, abrir un pull request. La integración a `main` la revisa y aprueba la persona encargada de la integración.
6. Cada integrante trabaja en su paquete y no modifica el código de otro. Las clases compartidas solo se cambian mediante solicitud.

## Requisitos

- Java JDK (versión por definir)
- NetBeans

## Cómo ejecutar

1. Clonar el repositorio.
2. Abrir el proyecto en NetBeans.
3. Ejecutar la clase principal "Main.java". La primera vez se pedirá la configuración del banco.
