# API Pizza Write Microservice

#### Create pizza

#### Modify pizza

# API Pizza Read Microservice

#### List pizzas

#### Get concrete pizza

# API Users Microservice

#### Create user
#### Modify user
#### Delete user
#### List users

#### Check pizza as favorite
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

#### Check pizza as favorite
- PUT
- Desmarca una pizza como favorita en caso de que exista en la lista del usuario. No es necesario feign client
- Opción 1: POST con Request Parameters idUser e idPizza
 
- Opción 2: Elimina directamente las pizzas que estén en esa lista de la lista del usuario
  - Body:
    {
    "favPizzas": [1,3,4]
    }

# Feign Client
- Ccuando se marcan las pizzas como favoritas para ver si realmente existe esa pizza.

# Circuit Braker
- Listar usuarios devolver vacío si no está el micro de users-crud activo

# Ideas

- Cuando se ``marca una pizza como favorita``, enviar un ok con el mensaje de la pizza que se ha marcado (info completa) -> de momento no lo hago porque no sé hasta qué punto es eficiente y sirve para algo, en el sentido de que si ya le has dicho que marque la pizza con ID 3 por ejemplo, si te devuelve un 200 ok, es que lo ha hecho bien, y mandar la info queda bonito saber qué pizza has marcado para verlo por ejemplo en swagger, pero luego a la hora de que sea funcional, si tienes una web donde marcas esa pizza supongo que ya tienes ahí el nombre de la pizza no el id, y el id se pasa al hacer la acción de marcar.

- Se puee cuando se devuelva un usuario o la lista de usuarios devolver las pizzas con la info completa y no solo su lista de Ids, pero es menos eficiente, se podría hacer luego un get pizza of user y entonces se tendría otro uso de feign client para coger la pizza del micro de pizzas-read.

# Ampliación API
- Get user 
- Delete pizza -> habría que utilizar feign client para eliminar luego esa pizza de las favortias de los usuarios que la tuvieran


# Notas
- Notación @Data incluye varias anotaciones como @Getter y @Setter 