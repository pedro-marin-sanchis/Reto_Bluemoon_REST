# API RESTful

## Indice

- [Autenticación](#autenticación)
    - [Login](#login)
- [Usuarios](#usuarios)
    - [Obtener usuarios](#obtener-usuarios)
    - [Crear usuarios](#crear-usuarios)
    - [Modificar usuarios](#modificar-usuarios)
    - [Borrar usuarios](#borrar-usuarios)
- [Orders](#orders)
    - [Obtener pedidos](#obtener-pedidos)
    - [Crear pedidos](#crear-pedidos)
    - [Modificar pedidos](#modificar-pedidos)
    - [Borrar pedidos](#borrar-pedidos)
- [Products](#products)
    - [Obtener productos](#obtener-productos)
    - [Crear productos](#crear-productos)
    - [Modificar productos](#modificar-productos)
    - [Borrar productos](#borrar-productos)
- [Trades](#trades)
    - [Obtener trade](#obtener-trade)
    - [Crear trade](#crear-trade)
    - [Modificar trade](#modificar-trade)
    - [Borrar trade](#borrar-trade)
- [Tradeables](#tradeables)
    - [Obtener tradeable](#obtener-tradeable)
    - [Crear tradeable](#crear-tradeable)
    - [Modificar tradeable](#modificar-tradeable)
    - [Borrar tradeable](#borrar-tradeable)
- [SilverTypes](#silvertypes)
    - [Obtener silverType](#obtener-silvertype)
    - [Crear silverType](#crear-silvertype)
    - [Modificar silverType](#modificar-silvertype)
    - [Borrar silverType](#borrar-silvertype)


## Autenticación

### Login

- Para loguearse:
  `POST /api/login`

  Con un JSON con este formato en el body:

  ```json
  {
    "username",
    "password"
  }
  ```

  Si el usuario y la contraseña son correctos, se devolverá un token que se deberá incluir en el header de las peticiones que requieran autenticación.

## Usuarios

### Obtener usuarios

- Para obtener todos los usuarios:
  `GET /api/users` (ADMIN)

- Para obtener un usuario:
  `GET /api/users/{username}` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario que se quiere obtener.

- Para obtener el usuario logueado:
  `GET /api/users/me`

### Crear usuarios

- Para crear un usuario:
  `POST /api/users`

  Con un JSON con este formato en el body:

  ```json
  {
    "username",
    "name",
    "surnames",
    "email",
    "password"
  }
  ```

### Modificar usuarios

- Para modificar un usuario:
  `PUT /api/users/{username}` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario que se quiere modificar.

  Con un JSON con este formato en el body (se pondrán todos los campos en el body incluso los que no se quieran modificar):

  ```json
  {
    "username",
    "name",
    "surnames",
    "email",
    "password"
  }
  ```

### Borrar usuarios

- Para borrar un usuario:
  `DELETE /api/users/{username}` (ADMIN)

  Siendo `{username}` el nombre del usuario que se quiere borrar.


## Carrito

### Obtener carrito

- Para obtener el carrito de un usuario:
  `GET /api/users/{username}/cart-items` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario del que se quiere obtener el carrito.

### Añadir producto al carrito

- Para añadir un producto al carrito de un usuario:
  `POST /api/users/{username}/cart-items` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario al que se le quiere añadir el producto.

  Con un JSON con este formato en el body:

  ```json
  {
    "productId",
    "quantity"
  }
  ```

### Modificar producto del carrito

- Para modificar un producto del carrito de un usuario:
  `PUT /api/users/{username}/cart-items/{productId}` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario al que se le quiere modificar el producto y `{productId}` el id del producto que se quiere modificar.

  Con un JSON con este formato en el body (se pondrán todos los campos en el body incluso los que no se quieran modificar):

  ```json
  {
    "quantity"
  }
  ```

### Borrar producto del carrito

- Para borrar un producto del carrito de un usuario:
  `DELETE /api/users/{username}/cart-items/{productId}` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario al que se le quiere borrar el producto y `{productId}` el id del producto que se quiere borrar.

## Orders

### Obtener pedidos

- Para obtener todos los pedidos:
  `GET /api/orders` (ADMIN)

- Para obtener un pedido:
  `GET /api/orders/{id}` (ADMIN)

  Siendo `{id}` el id del pedido que se quiere obtener.

- Para obtener los pedidos de un usuario:
  `GET /api/users/{username}/orders` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario del que se quieren obtener los pedidos.

- Para obtener un pedido de un usuario:
  `GET /api/users/{username}/orders/{id}` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario del que se quiere obtener el pedido y `{id}` el id del pedido que se quiere obtener.

### Crear pedidos

- Para crear un pedido desde el carrito de un usuario:
  `POST /api/orders` (ADMIN o nombre de usuario = usuario logueado o "me")

  Con un JSON con este formato en el body:

  ```json
  {
    "userId"
  }
  ```

### Modificar pedidos

- Para modificar un pedido:
  `PUT /api/orders/{id}` (ADMIN)

  Siendo `{id}` el id del pedido que se quiere modificar.

  Con un JSON con este formato en el body (se pondrán todos los campos en el body incluso los que no se quieran modificar):

  ```json
  {
    "id",
    "date",
    "address",
    "delivered",
    "accepted"
  }
  ```

### Borrar pedidos

- Para borrar un pedido:
  `DELETE /api/orders/{id}` (ADMIN)

  Siendo `{id}` el id del pedido que se quiere borrar.

## Products

### Obtener productos

- Para obtener todos los productos:
  `GET /api/products` (ADMIN o USER)

- Para obtener un producto:

  `GET /api/products/{id}` (ADMIN o USER)

  Siendo `{id}` el id del producto que se quiere obtener.

### Crear productos

- Para crear un producto:
  `POST /api/products` (ADMIN)

  Con un JSON con este formato en el body:

  ```json
  {
    "name",
    "description",
    "price",
    "img",
    "enabled"
  }
  ```

### Modificar productos

- Para modificar un producto:
  `PUT /api/products/{id}` (ADMIN)

  Siendo `{id}` el id del producto que se quiere modificar.

  Con un JSON con este formato en el body (se pondrán todos los campos en el body incluso los que no se quieran modificar):

  ```json
  {
    "id",
    "name",
    "description",
    "price",
    "img",
    "enabled"
  }
  ```

### Borrar productos

- Para borrar un producto:
  `DELETE /api/products/{id}` (ADMIN)

  Siendo `{id}` el id del producto que se quiere borrar.

## Trades

### Obtener trade

- Para obtener todos los trades:
  `GET /api/trades` (ADMIN)

- Para obtener un trade:
  `GET /api/trades/{id}` (ADMIN)

  Siendo `{id}` el id del trade que se quiere obtener.

- Para obtener los trades de un usuario:
  `GET /api/users/{username}/trades` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario del que se quieren obtener los trades.

- Para obtener un trade de un usuario:
  `GET /api/users/{username}/trades/{id}` (ADMIN o nombre de usuario = usuario logueado o "me")

  Siendo `{username}` el nombre del usuario del que se quiere obtener el trade y `{id}` el id del trade que se quiere obtener.

### Crear trade

- Para crear un trade:
  `POST /api/trades` (ADMIN o nombre de usuario = usuario logueado o "me")

  Con un JSON con este formato en el body:

  ```json
  {
    "userId",
    "tradeables"
  }
  ```

### Modificar trade

- Para modificar un trade:
  `PUT /api/trades/{id}` (ADMIN)

  Siendo `{id}` el id del trade que se quiere modificar.

  Con un JSON con este formato en el body (se pondrán todos los campos en el body incluso los que no se quieran modificar):

  ```json
  {
    "validated"
  }
  ```

### Borrar trade

- Para borrar un trade:
  `DELETE /api/trades/{id}` (ADMIN)

  Siendo `{id}` el id del trade que se quiere borrar.

## Tradeables

### Obtener tradeable

- Para obtener todos los tradeables:
  `GET /api/trade/{tradeId}/tradeables`

  Siendo `{tradeId}` el id del trade del que se quieren obtener los tradeables.

- Para obtener un tradeable:
  `GET /api/trade/{tradeId}/tradeables/{id}`

  Siendo `{tradeId}` el id del trade del que se quiere obtener el tradeable y `{id}` el id del tradeable que se quiere obtener.

### Crear tradeable

- Para crear un tradeable:
  `POST /api/trade/{tradeId}/tradeables`

  Con un JSON con este formato en el body:

  ```json
  {
    "weight",
    "description",
    "silverTypeId"
  }
  ```

### Modificar tradeable

- Para modificar un tradeable:
  `PUT /api/trade/{tradeId}/tradeables/{id}`  (ADMIN)

  Siendo `{tradeId}` el id del trade del que se quiere modificar el tradeable y `{id}` el id del tradeable que se quiere modificar.

  Con un JSON con este formato en el body (se pondrán todos los campos en el body incluso los que no se quieran modificar):

  ```json
  {
    "weight",
    "sellPrice",
    "description",
    "silverTypeId"
  }
  ```

### Borrar tradeable

- Para borrar un tradeable:
  `DELETE /api/trade/{tradeId}/tradeables/{id}`  (ADMIN)

  Siendo `{tradeId}` el id del trade del que se quiere borrar el tradeable y `{id}` el id del tradeable que se quiere borrar.

## SilverTypes

### Obtener silverType

- Para obtener todos los silverTypes:
  `GET /api/silverTypes`

- Para obtener un silverType:
  `GET /api/silverTypes/{id}`

  Siendo `{id}` el id del silverType que se quiere obtener.

### Crear silverType

- Para crear un silverType:
  `POST /api/silverTypes` (ADMIN)

  Con un JSON con este formato en el body:

  ```json
  {
    "name",
    "currentPrice",
  }
  ```

### Modificar silverType

- Para modificar un silverType:
  `PUT /api/silverTypes/{id}` (ADMIN)

  Siendo `{id}` el id del silverType que se quiere modificar.

  Con un JSON con este formato en el body (se pondrán todos los campos en el body incluso los que no se quieran modificar):

  ```json
  {
    "name",
    "disabled",
    "currentPrice"
  }
  ```

### Borrar silverType

- Para borrar un silverType:
  `DELETE /api/silverTypes/{id}` (ADMIN)

  Siendo `{id}` el id del silverType que se quiere borrar.
