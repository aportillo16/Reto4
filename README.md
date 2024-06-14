RETO 4 BI

DESCRIPCIÓN FUNCIONAL: Con la base del reto3, ahora implementar la validacion con un servicio remoto. Implementar retrofit para la invocacion de API y cargar la data que devuelve el servicio en la pantalla de bienvenida (Imagen de perfil, nombre y direccion), si el servicio devuelve error, entonces deben mostrar un toast indicando el mensaje devuelto y no permitir ingresar a la aplicacion.

DESCRIPCIÓN TÉCNICA: Se hace uso de la librería RETROFIT para la validación de las credenciales (usuario: etson@gmail.com clave: 123456), por medio de la URL: http://www.balam-knights.com/accesos_dev/api/Auth/

Se utiliza un servicio tipo POST:

@POST("accesos_dev/api/Auth/")
    fun getResultLogin(@Body parametersDto: ParametersDto): Call<balamKnightsResponse>

Con un headerinterceptor:
.addHeader(
                "x-api-key","81818BBF-C772-411D-9BFA-3CAFA12D6077"
            )
            

LOGIN:

![image](https://github.com/aportillo16/Reto4/assets/166572036/b98cbb9e-b06f-4d08-b4dc-33d7b2a16992)

HOME:

![image](https://github.com/aportillo16/Reto4/assets/166572036/c211da56-3d47-49f1-ad70-0f5ffaf8d112)

