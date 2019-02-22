package mohamedalaa.mautils.mautils_open_source_licences.view.adapters

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rc_adapter_licence.view.*
import mohamedalaa.mautils.core_android.inflateLayout
import mohamedalaa.mautils.core_android.launchWebLink
import mohamedalaa.mautils.core_android.setBackgroundTint
import mohamedalaa.mautils.mautils_open_source_licences.R
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.model.isAuthorExists
import mohamedalaa.mautils.mautils_open_source_licences.model.isLinkExists
import mohamedalaa.mautils.mautils_open_source_licences.view.LicenceDetailsActivity

/**
 * [RecyclerView.Adapter] for [List]<[Licence]> isa.
 */
internal class RCAdapterLicence(private val context: Context,
                                private var licences: List<Licence>?,
                                intent: Intent,
                                @ColorInt private val itemButtonColor: Int? = null)
    : RecyclerView.Adapter<RCAdapterLicence.CustomViewHolder>() {

    private val licenceIntent = Intent(context, LicenceDetailsActivity::class.java).apply { replaceExtras(intent) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val rootView = parent.context.inflateLayout(R.layout.rc_adapter_licence, parent)

        itemButtonColor?.apply { rootView.linkButton.setBackgroundTint(this) }

        return CustomViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val licence = licences?.get(position) ?: return

        holder.itemView.licenceName.text = if (licence.isAuthorExists) licence.licenceName else ""

        holder.itemView.licenceAuthor.text = if (licence.isAuthorExists) context.getString(R.string.by_colon_string, licence.licenceAuthor) else ""

        holder.itemView.licenceNameAlone.text = if (licence.isAuthorExists) "" else licence.licenceName

        val linkVisibility = if (licence.isLinkExists) {
            holder.itemView.linkButton.setOnClickListener {
                licence.link?.apply { context.launchWebLink(this) }
            }

            View.VISIBLE
        } else {
            View.GONE
        }
        holder.itemView.linkButton.visibility = linkVisibility
        holder.itemView.view.visibility = linkVisibility

        holder.itemView.rootConstraintLayout.setOnClickListener {
            licenceIntent.putExtra(LicenceDetailsActivity.INTENT_KEY_LICENCE_NAME, licence.licenceName)
            licenceIntent.putExtra(LicenceDetailsActivity.INTENT_KEY_LICENCE_AUTHOR, licence.licenceAuthor)
            licenceIntent.putExtra(LicenceDetailsActivity.INTENT_KEY_LINK, licence.link)
            licenceIntent.putExtra(LicenceDetailsActivity.INTENT_KEY_LICENCE_CONTENT, licence.licenceContent)

            context.startActivity(licenceIntent)
        }
    }

    override fun getItemCount(): Int = licences?.size ?: 0

    // ---- Public fun

    fun swapList(licences: List<Licence>?) {
        this.licences = licences

        notifyDataSetChanged()
    }

    // ----- Custom View Holder

    class CustomViewHolder(rootView: View): RecyclerView.ViewHolder(rootView)

}