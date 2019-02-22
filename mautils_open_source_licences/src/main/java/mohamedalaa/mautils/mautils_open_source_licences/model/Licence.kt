package mohamedalaa.mautils.mautils_open_source_licences.model

/**
 * Licence item data class, represents everything about the licence isa.
 */
data class Licence(val licenceName: String,
                   val licenceAuthor: String? = null,
                   val link: String? = null,
                   val licenceContent: String)

val Licence.isAuthorExists: Boolean
    get() = licenceAuthor.isNullOrEmpty().not()

val Licence.isLinkExists: Boolean
    get() = link.isNullOrEmpty().not()