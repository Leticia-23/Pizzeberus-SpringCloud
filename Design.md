# API Pizza Write Microservice

- [ ] Create pizza

- [x] Modify pizza

# API Pizza Read Microservice

- [ ] List pizzas

- [ ] Get concrete pizza

# API Users Microservice

-[x] Create user
-[x] Modify user
-[x] Delete user
-[x] List users

-[ ] Check pizza as favorite
  - POST
  - Users tendrá que llamar a microservicio PizzaRead y si existe la/s pizzas se añaden
  - Opción 1: POST con Request Parameters idUser e idPizza
    - Marca de una en una las favoritas, en caso por ejemplo desde un front, recorre la lista y va haciendo la petición de una en una.

  - Opción 2 : POST Body: 
      {
      "favPizzas": [1,3,4]
      } 
    -  hace con body lista para poder añadi 1 o más pizzas como favoritas
    - ¿Qué pasa si en una lista hay pizzas que existen y otras que no, cuando se comprueba en el microservicio de pizzas Read?
      - solución posible marcar las que sí están y devolver un código de ok pero con mensaje de esta no estaba y no se ha hecho, pero creo que está un poco feo
      - Hacerlo transactional y si no existe alguna no se hace nada

-[ ] Uncheck pizza as favorite
  - PATCH / (PUT)
  - Desmarca una pizza como favorita en caso de que exista en la lista del usuario. No es necesario feign client
  - Opción 1: POST con Request Parameters idUser e idPizza
 
  - Opción 2: Elimina directamente las pizzas que estén en esa lista de la lista del usuario
    - Body:
      {
      "favPizzas": [1,3,4]
      }

# Feign Client
- Cuando se marcan las pizzas como favoritas para ver si realmente existe esa pizza.

# Circuit Braker
- Listar usuarios devolver vacío si no está el micro de users-crud activo → esto no es posible porque si no está activo ni siquiera se va a poder hacer la petición ¿?

# Ideas

- Cuando se ``marca una pizza como favorita``, enviar un ok con el mensaje de la pizza que se ha marcado (info completa) -> de momento no lo hago porque no sé hasta qué punto es eficiente y sirve para algo, en el sentido de que si ya le has dicho que marque la pizza con ID 3 por ejemplo, si te devuelve un 200 ok, es que lo ha hecho bien, y mandar la info queda bonito saber qué pizza has marcado para verlo por ejemplo en swagger, pero luego a la hora de que sea funcional, si tienes una web donde marcas esa pizza supongo que ya tienes ahí el nombre de la pizza no el id, y el id se pasa al hacer la acción de marcar.

- Se podría cuando se devuelva un usuario o la lista de usuarios devolver las pizzas con la info completa y no solo su lista de Ids, pero es menos eficiente, se podría hacer luego un get pizza of user y entonces se tendría otro uso de feign client para coger la pizza del micro de pizzas-read.

# Ampliación API
- [x] Get user 
- Delete pizza → habría que utilizar feign client para eliminar luego esa pizza de las favortas de los usuarios que la tuvieran


# Notas + TODO
- Notación @Data incluye varias anotaciones como @Getter y @Setter 

- [x] Poner dependencias y configuraciones BBDD en pom y yml de los 2 pizza micros y user micro

- [x] Meter las dependencias de mapper en los 2 micros de pizzas y user micro

- [ ] Configurar textos swagger

## Dudas opciones
### Update de datos parcialmente
- [ ] Hacer PUT y solo modificar en la base de datos realmente lo que se quiere, y se está dando al usuario la opción de poner cualquiera de los datos del ObjectDTO 

- [x] Hacer PATCH poniendo la opción de todo el ObjectDTO y cambiar en base de datos solo lo que se quiera cambiar.

## PROBLEMAS
- Para hacer post de las pizzas, se supone que se tiene que generar solo el campo id, pero si no se envía en el json un id da error ( The given id must not be null!). Y debería de generarse el valor cuando es null, o cuando se le da algún valor.
  - Si se envía el json con un id, entonces lo crea, y no lo crea con ese id sino con el que le toque con el @GeneratedValue(strategy = GenerationType.AUTO).
  - Si luego quieres hacer post de otro y metes un id que ya exista en la bbdd entonces da conflicto, cuando lo debería de crear igualmente siguiendo lo otro
  - He probado a poner en el Dto el builder, constructores con y sin args y getter y setters por si acaso pero tampoco lo hace.

- Al actualizar los valores de pizza se crean nuevas pizzas en la base de datos aunque se coja la oldPizza y se le cambie el nombre. Esto según chatgp: 
  - El problema que estás experimentando se debe a que la estrategia de generación de identificadores (@GeneratedValue) está configurada en GenerationType.AUTO, lo que permite que la base de datos decida la estrategia de generación del ID. Dependiendo del sistema de base de datos que estés utilizando, esta estrategia podría estar generando un nuevo ID incluso si se actualiza una entidad existente. Para evitar que se generen nuevos IDs al actualizar una entidad, puedes cambiar la estrategia de generación del ID a GenerationType.IDENTITY, que es la estrategia más comúnmente utilizada cuando deseas que la base de datos maneje la generación de IDs de manera automática y consecutiva.