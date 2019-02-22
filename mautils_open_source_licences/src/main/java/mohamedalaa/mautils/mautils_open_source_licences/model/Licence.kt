package mohamedalaa.mautils.mautils_open_source_licences.model

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/22/2019.
 *
 * Note params should not be empty isa.
 */
data class Licence(val licenceName: String,
                   val licenceAuthor: String? = null,
                   val link: String? = null,
                   val licenceContent: String)