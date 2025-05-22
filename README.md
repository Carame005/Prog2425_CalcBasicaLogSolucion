# Explicación de que se ha hecho

Bueno cómo el ejercicio pedía he añadido la capacidad de gestionar los logs al proyecto de la calculadora y ahora procederé a comentar que he añadido y cómo funciona

## `model`

Model tiene dos data class nuevas `Operacion` y `Error`, estas data class sirven basicamente para representar sus respectivas tablas en la base de datos y así ccumplir el patrón Dao

## `data`

En el data le agregué una subcarpeta que recoge el datasource factory y el modo en el que quiero que este se ejecute (ya sea Hikari o simple).

A su vez creé las clases `OperacionDaoH2` y `ErrorDao` con sus respectivos intefaces, estos albergan las operaciones requeridas para manipular los datos que se reciben en la base de datos.

## `service`

Aquí simplemente importé las funciones del repo en la clase `ServicioLog` ya que es la que ejecuta todo lo relacionado con la gestión de los logs, podría haber creado un servicio para cada DAO pero no creo que en este caso sea necesario.

## `app`

Lo que hice en la app fué basicamente cambiar las funciones `ejecutarCalculoConArgumentos()` y `bucleCalculosUsuario()` para que guarden los errores si se dan, en la funcion `realizarCalculo()` metí la opcion de guardar las operaciones en la tabla, esta guarda la fecha en la que se realizó, el primer número, el segundo, el operador y el resultado que devuelve la operación.

También agregué un menú que pregunta al usuario nada más ejecutar la aplicación si le gustaría realizar una operación, consultar el historial de operaciones o consultar el hsitorial de errores.
