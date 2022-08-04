package com.example.ordermatic.model.database

import com.example.ordermatic.model.*
import de.thm.tp.library.firebase.firestore.TPFirebaseFirestore
import de.thm.tp.library.firebase.firestore.TPFirebaseFirestoreQueryBuilder

class DatabaseService {
    companion object {
        /****************************************
         * Company
         ***************************************/

        fun setCompany(id: String, company: Company, callback: (String?) -> Unit) {
            TPFirebaseFirestore.setDocument(
                Company.COLLECTION_NAME,
                id,
                Company.toMap(company),
                callback
            )
        }

        fun getCompany(id: String, callback: (Company?) -> Unit) {
            TPFirebaseFirestore.getDocument(
                Company.COLLECTION_NAME,
                id
            ) { result, error ->
                result?.let { resultObject ->
                    callback(Company.toObject(resultObject.documentId, resultObject.data))
                }
                if (error != null) {
                    callback(null)
                }
            }
        }

        /****************************************
         * Dish
         ***************************************/

        fun addDish(dish: Dish, callback: (Dish?) -> Unit) {
            TPFirebaseFirestore.addDocument(
                Dish.COLLECTION_NAME,
                Dish.toMap(dish)
            ) { result, error ->
                result?.let { resultObject ->
                    callback(Dish.toObject(resultObject.documentId, resultObject.data))
                }
                if (error != null) {
                    callback(null)
                }
            }
        }

        fun getDish(
            id: String,
            callback: (Dish?) -> Unit
        ) {
            TPFirebaseFirestore.getDocument(Dish.COLLECTION_NAME, id) { result, error ->
                result?.let { resultObject ->
                    callback(Dish.toObject(resultObject.documentId, resultObject.data))
                }
                if (error != null) {
                    callback(null)
                }
            }
        }

        fun getDishes(companyAccount: CompanyAccount, callback: (List<Dish>) -> Unit) {
            TPFirebaseFirestore.getDocuments(
                TPFirebaseFirestoreQueryBuilder(Dish.COLLECTION_NAME)
                    .whereEqualTo("companyReference", companyAccount.id)
            ) { result, _ ->
                result?.let {  resultObject ->
                    val dishes = mutableListOf<Dish>()
                    for (element in resultObject) {
                       Dish.toObject(element.documentId, element.data)?.let { temp ->
                           dishes.add(temp)
                       }
                    }
                    callback(dishes)
                }
            }
        }

        fun getDishes(
            companyAccount: CompanyAccount,
            dishesType: DishesType,
            callback: (List<Dish>) -> Unit
        ) {
            TPFirebaseFirestore.getDocuments(
                TPFirebaseFirestoreQueryBuilder(Dish.COLLECTION_NAME)
                    .whereEqualTo("companyReference", companyAccount.id)
                    .whereEqualTo("type", dishesType.rawValue)
            ) { result, _ ->
                result?.let { resultObject ->
                    val dishes = mutableListOf<Dish>()
                    for (element in resultObject) {
                        Dish.toObject(element.documentId, element.data)?.let { temp ->
                            dishes.add(temp)
                        }
                    }
                    callback(dishes)
                }
            }
        }

        fun getDishesIdsByTypes(
            companyAccount: CompanyAccount,
            dishesTypes: List<DishesType>,
            callback: (List<String>) -> Unit
        ) {
            if (dishesTypes.size > 0) {
                val list = dishesTypes.map { type -> type.rawValue}
                TPFirebaseFirestore.getDocuments(
                    TPFirebaseFirestoreQueryBuilder(Dish.COLLECTION_NAME)
                        .whereEqualTo("companyReference", companyAccount.id)
                        .whereIn("type", list)
                ) { result, _ ->
                    result?.let { resultObject ->
                        val ids = mutableListOf<String>()
                        for (element in resultObject) {
                            val dishObject = Dish.toObject(element.documentId, element.data)
                            if (dishObject != null){
                                ids.add(element.documentId)
                            }
                        }
                        callback(ids)
                    }
                }
            }
            callback(mutableListOf())
        }

        fun getDishesByTypes(
            companyAccount: CompanyAccount,
            dishesTypes: List<DishesType>,
            callback: (List<Dish>) -> Unit
        ) {
            if (dishesTypes.size > 0) {
                val list = dishesTypes.map { type -> type.rawValue}
                TPFirebaseFirestore.getDocuments(
                    TPFirebaseFirestoreQueryBuilder(Dish.COLLECTION_NAME)
                        .whereEqualTo("companyReference", companyAccount.id)
                        .whereIn("type", list)
                ) { result, _ ->
                    result?.let { resultObject ->
                        val dishes = mutableListOf<Dish>()
                        for (element in resultObject) {
                            Dish.toObject(element.documentId, element.data)?.let { temp ->
                                dishes.add(temp)
                            }
                        }
                        callback(dishes)
                    }
                }
            }
            callback(mutableListOf())
        }

        fun addDishesSnapshotListener(
            companyAccount: CompanyAccount,
            dishesType: DishesType,
            callback: (List<Dish>) -> Unit
        ) {
            TPFirebaseFirestore.addCollectionSnapshotListener(
                TPFirebaseFirestoreQueryBuilder(Dish.COLLECTION_NAME)
                    .whereEqualTo("companyReference", companyAccount.id)
                    .whereEqualTo("type", dishesType.rawValue)
            ) { result, _ ->
                result?.let { resultObject ->
                    val dishes = mutableListOf<Dish>()
                    for (element in resultObject) {
                        Dish.toObject(element.documentId, element.data)?.let { temp ->
                            dishes.add(temp)
                        }
                    }
                    callback(dishes)
                }
            }
        }

        fun editDish(dish: Dish, callback: (String?) -> Unit) {
            dish.documentId?.let { dishDocumentId ->
                TPFirebaseFirestore.updateDocument(
                    Dish.COLLECTION_NAME,
                    dishDocumentId,
                    Dish.toMap(dish)
                ) { error ->
                    callback(error)
                }
            }
            if (dish.documentId == null) {
                callback("Object is malformed")
            }
        }

        fun deleteDish(dish: Dish, callback: (String?) -> Unit) {
            dish.documentId?.let { dishDocumentId ->
                TPFirebaseFirestore.deleteDocument(
                    Dish.COLLECTION_NAME,
                    dishDocumentId
                ) { error ->
                    callback(error)
                }
            }
            if (dish.documentId == null) {
                callback("Object is malformed")
            }
        }

        /****************************************
         * Order
         ***************************************/

        fun addOrder(order: Order, callback: (Order?) -> Unit) {
            TPFirebaseFirestore.addDocument(
                Order.COLLECTION_NAME,
                Order.toMap(order)
            ) { result, error ->
                result?.let { resultObject ->
                    callback(Order.toObject(resultObject.documentId, resultObject.data))
                }
                if (error != null) {
                    callback(null)
                }
            }
        }

        fun addOrderSnapshotListener(
            companyAccount: CompanyAccount,
            isActive: Boolean,
            callback: (List<Order>) -> Unit
        ) {
            TPFirebaseFirestore.addCollectionSnapshotListener(
                TPFirebaseFirestoreQueryBuilder(Order.COLLECTION_NAME)
                    .whereEqualTo("companyReference", companyAccount.id)
                    .whereEqualTo("isActive", isActive)

            ) { result, _ ->
                result?.let { resultObject ->
                    val orders = mutableListOf<Order>()
                    for (element in resultObject) {
                        Order.toObject(element.documentId, element.data)?.let { temp ->
                            orders.add(temp)
                        }
                    }
                    callback(orders)
                }
            }
        }

        fun getActiveOrderByTableNumber(
            companyAccount: CompanyAccount,
            tableNumber: String,
            callback: (Order?) -> Unit
        ) {
            TPFirebaseFirestore.getDocuments(
                TPFirebaseFirestoreQueryBuilder(Order.COLLECTION_NAME)
                    .whereEqualTo("companyReference", companyAccount.id)
                    .whereEqualTo("tableNumber", tableNumber)
                    .whereEqualTo("isActive", true)

            ) { result, error ->
                result?.let { resultObject ->
                    if (resultObject.size > 0) {
                        callback(Order.toObject(resultObject[0].documentId, resultObject[0].data))
                    } else {
                        callback(null)
                    }
                }
                if (error != null) {
                    callback(null)
                }
            }
        }

        fun editOrder(order: Order, callback: (String?) -> Unit) {
            order.documentId?.let { orderDocumentId ->
                TPFirebaseFirestore.updateDocument(
                    Order.COLLECTION_NAME,
                    orderDocumentId,
                    Order.toMap(order)
                ) { error ->
                    callback(error)
                }
            }
            if (order.documentId == null) {
                callback("Object is malformed")
            }
        }

        /****************************************
         * OrderedDish
         ***************************************/

        fun addOrderedDish(
            orderedDish: OrderedDish,
            callback: (OrderedDish?) -> Unit
        ) {
            TPFirebaseFirestore.addDocument(
                OrderedDish.COLLECTION_NAME,
                OrderedDish.toMap(orderedDish)
            ) { result, error ->
                result?.let { resultObject ->
                    callback(OrderedDish.toObject(resultObject.documentId, resultObject.data))
                }
                if (error != null) {
                    callback(null)
                }
            }
        }

        fun getOrderedDishes(
            order: Order,
            callback: (List<OrderedDish>) -> Unit
        ) {
            order.documentId?.let { orderDocumentId ->
                TPFirebaseFirestore.getDocuments(
                    TPFirebaseFirestoreQueryBuilder(OrderedDish.COLLECTION_NAME)
                        .whereEqualTo("orderReference", orderDocumentId)
                ) { result, _ ->
                    result?.let { resultObject ->
                        val orders = mutableListOf<OrderedDish>()
                        for (element in resultObject) {
                            OrderedDish.toObject(element.documentId, element.data)?.let { temp ->
                                orders.add(temp)
                            }
                        }
                        callback(orders)
                    }
                }
            }
            if (order.documentId == null){
                callback(mutableListOf())
            }
        }

        fun getAllOrderedDishesOfOrderSnapshotListener(
            companyAccount: CompanyAccount,
            order: Order,
            callback: (List<OrderedDish>) -> Unit
        ) {

            order.documentId?.let { orderDocumentId ->
                TPFirebaseFirestore.addCollectionSnapshotListener(
                    TPFirebaseFirestoreQueryBuilder(OrderedDish.COLLECTION_NAME)
                        .whereEqualTo("companyReference", companyAccount.id)
                        .whereEqualTo("orderReference", orderDocumentId)
                ) { result, error ->
                    result?.let { resultObject ->
                        val orderedDishes = mutableListOf<OrderedDish>()
                        for (element in resultObject) {
                           OrderedDish.toObject(element.documentId, element.data)?.let { temp ->
                                orderedDishes.add(temp)
                            }
                        }
                        callback(orderedDishes)
                    }
                    if (error != null) {
                        callback(mutableListOf())
                    }
                }
            }
        }

        fun getOrderDishesByStatusSnapshotListener(
            companyAccount: CompanyAccount,
            order: Order,
            status: OrderDishesStatus,
            callback: (List<OrderedDish>) -> Unit
        ) {
            order.documentId?.let { orderDocumentId ->
                TPFirebaseFirestore.addCollectionSnapshotListener(
                    TPFirebaseFirestoreQueryBuilder(OrderedDish.COLLECTION_NAME)
                        .whereEqualTo("companyReference", companyAccount.id)
                        .whereEqualTo("orderReference", orderDocumentId)
                        .whereEqualTo("status", status.rawValue)
                ) { result, error ->
                    result?.let { resultObject ->
                        val orderedDishes = mutableListOf<OrderedDish>()
                        for (element in resultObject) {
                            OrderedDish.toObject(element.documentId, element.data)?.let { temp ->
                                orderedDishes.add(temp)
                            }
                        }
                        callback(orderedDishes)
                    }
                    if (error != null) {
                        callback(mutableListOf())
                    }
                }
            }
        }

        fun getAllOrderDishesOfMultipleOrdersSnapshotListener(
            companyAccount: CompanyAccount,
            orderIds: List<String>,
            callback: (List<OrderedDish>) -> Unit
        ) {
            if (orderIds.size > 0) {
                TPFirebaseFirestore.addCollectionSnapshotListener(
                    TPFirebaseFirestoreQueryBuilder(OrderedDish.COLLECTION_NAME)
                        .whereEqualTo("companyReference", companyAccount.id)
                        .whereIn("orderReference", orderIds)
                ) { result, error ->
                    result?.let { resultObject ->
                        val orderedDishes = mutableListOf<OrderedDish>()
                        for (element in resultObject) {
                            OrderedDish.toObject(element.documentId, element.data)?.let { temp ->
                                orderedDishes.add(temp)
                            }
                        }
                        callback(orderedDishes)
                    }
                    if (error != null) {
                        callback(mutableListOf())
                    }
                }
            }
        }

        fun getAllOrderDishesByStatusSnapshotListener(
            companyAccount: CompanyAccount,
            orderIds: List<String>,
            status: OrderDishesStatus,
            callback: (List<OrderedDish>) -> Unit
        ) {
            if (orderIds.size > 0) {
                TPFirebaseFirestore.addCollectionSnapshotListener(
                    TPFirebaseFirestoreQueryBuilder(OrderedDish.COLLECTION_NAME)
                        .whereEqualTo("companyReference", companyAccount.id)
                        .whereIn("orderReference", orderIds)
                        .whereEqualTo("status", status.rawValue)
                ) { result, error ->
                    result?.let { resultObject ->
                        val orderedDishes = mutableListOf<OrderedDish>()
                        for (element in resultObject) {
                            OrderedDish.toObject(element.documentId, element.data)?.let { temp ->
                                orderedDishes.add(temp)
                            }
                        }
                        callback(orderedDishes)
                    }
                    if (error != null) {
                        callback(mutableListOf())
                    }
                }
            }
        }

        fun getAllOrderDishesByStatus(
            companyAccount: CompanyAccount,
            orderIds: List<String>,
            status: OrderDishesStatus,
            callback: (List<OrderedDish>) -> Unit
        ) {
            if (orderIds.size > 0) {
                TPFirebaseFirestore.getDocuments(
                    TPFirebaseFirestoreQueryBuilder(OrderedDish.COLLECTION_NAME)
                        .whereEqualTo("companyReference", companyAccount.id)
                        .whereIn("orderReference", orderIds)
                        .whereEqualTo("status", status.rawValue)
                ) { result, error ->
                    result?.let { resultObject ->
                        val orderedDishes = mutableListOf<OrderedDish>()
                        for (element in resultObject) {
                            OrderedDish.toObject(element.documentId, element.data)?.let { temp ->
                                orderedDishes.add(temp)
                            }
                        }
                        callback(orderedDishes)
                    }
                    if (error != null) {
                        callback(mutableListOf())
                    }
                }
            }
        }

        fun editOrderedDish(orderedDish: OrderedDish, callback: (String?) -> Unit) {
            orderedDish.documentId?.let { orderedDishDocumentId ->
                TPFirebaseFirestore.updateDocument(
                    OrderedDish.COLLECTION_NAME,
                    orderedDishDocumentId,
                    OrderedDish.toMap(orderedDish)
                ) { error ->
                    callback(error)
                }
            }
            if (orderedDish.documentId == null) {
                callback("Object is malformed")
            }
        }

        /****************************************
         * User
         ***************************************/

        fun addUser(user: User, callback: (User?) -> Unit) {
            TPFirebaseFirestore.addDocument(
                User.COLLECTION_NAME,
                User.toMap(user)
            ) { result, error ->
                result?.let { resultObject ->
                    callback(User.toObject(resultObject.documentId, resultObject.data))
                }
                if (error != null) {
                    callback(null)
                }
            }
        }

        fun addUsersSnapshotListener(companyAccount: CompanyAccount, callback: (List<User>) -> Unit) {
            TPFirebaseFirestore.addCollectionSnapshotListener(
                TPFirebaseFirestoreQueryBuilder(User.COLLECTION_NAME)
                    .whereEqualTo("companyReference", companyAccount.id)
            ) { result, _ ->
                result?.let { resultObject ->
                    val users = mutableListOf<User>()
                    for (element in resultObject) {
                        User.toObject(element.documentId, element.data)?.let { temp ->
                            users.add(temp)
                        }
                    }
                    callback(users)
                }
            }
        }

        fun addOtherUsersSnapshotListener(companyAccount: CompanyAccount, callback: (List<User>) -> Unit) {
            TPFirebaseFirestore.addCollectionSnapshotListener(
                TPFirebaseFirestoreQueryBuilder(User.COLLECTION_NAME)
                    .whereEqualTo("companyReference", companyAccount.id)
                    .whereNotEqualTo("role", UserRole.ADMIN.rawValue)
            ) { result, _ ->
                result?.let { resultObject ->
                    val users = mutableListOf<User>()
                    for (element in resultObject) {
                        User.toObject(element.documentId, element.data)?.let { temp ->
                            users.add(temp)
                        }
                    }
                    callback(users)
                }
            }
        }

        fun editUser(user: User, callback: (String?) -> Unit) {
            user.documentId?.let { userDocumentId ->
                TPFirebaseFirestore.updateDocument(
                    User.COLLECTION_NAME,
                    userDocumentId,
                    User.toMap(user)
                ) { error ->
                    callback(error)
                }
            }
            if (user.documentId == null) {
                callback("Object is malformed")
            }
        }

        fun deleteUser(user: User, callback: (String?) -> Unit) {
            user.documentId?.let { userDocumentId ->
                TPFirebaseFirestore.deleteDocument(
                    User.COLLECTION_NAME,
                    userDocumentId
                ) { error ->
                    callback(error)
                }
            }
            if (user.documentId == null) {
                callback("Object is malformed")
            }
        }
    }
}