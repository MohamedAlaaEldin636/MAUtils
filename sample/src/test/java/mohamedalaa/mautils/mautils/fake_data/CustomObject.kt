package mohamedalaa.mautils.mautils.fake_data

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/15/2019.
 *
 */
data class CustomObject(val name: String = "Mohamed",
                        val age: Int = 22,
                        val address :String = "Not your business",
                        val friendsNames: List<String> = listOf("Gendy", "Omar", "Afrah"))

data class CustomWithTypeParam<T, R>(val element1: T? = null,
                                     val element2: R? = null,
                                     val name: String = "name",
                                     val anotherName: String? = null)