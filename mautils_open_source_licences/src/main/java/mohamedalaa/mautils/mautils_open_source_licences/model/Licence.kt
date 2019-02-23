package mohamedalaa.mautils.mautils_open_source_licences.model

/**
 * Licence item data class, represents everything about the licence isa.
 *
 * @param licenceName represents software name isa, Ex. (for this library) MAUtils.
 * @param licenceAuthor author(s) created that software names separated by comma and space or just comma **", "** isa.
 * @param link link where the software can be found isa, Ex. https://github.com/MohamedAlaaEldin636/MAUtils
 * @param licenceContent full licence text of that library isa.
 */
data class Licence(val licenceName: String,
                   val licenceAuthor: String? = null,
                   val link: String? = null,
                   val licenceContent: String) {
    companion object {

        /** Perform reverse conversion of [Licence.toStringList] isa. */
        @JvmStatic
        fun fromStringList(list: List<String?>): Licence? {
            if (list.size != 4) {
                return null
            }

            val name = list[0] ?: return null
            val author = list[1]
            val link = list[2]
            val content = list[3] ?: return null

            return Licence(name, author, link, content)
        }
    }
}

val Licence.isAuthorExists: Boolean
    get() = licenceAuthor.isNullOrEmpty().not()

val Licence.isLinkExists: Boolean
    get() = link.isNullOrEmpty().not()

fun Licence.toStringList(): List<String?>
    = listOf(licenceName, licenceAuthor, link, licenceContent)