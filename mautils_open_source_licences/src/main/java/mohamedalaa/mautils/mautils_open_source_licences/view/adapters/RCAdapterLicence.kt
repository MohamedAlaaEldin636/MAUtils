package mohamedalaa.mautils.mautils_open_source_licences.view.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rc_adapter_licence.view.*
import mohamedalaa.mautils.core_android.*
import mohamedalaa.mautils.mautils_open_source_licences.R
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.model.isAuthorExists
import mohamedalaa.mautils.mautils_open_source_licences.model.isLinkExists
import mohamedalaa.mautils.mautils_open_source_licences.view.LicenceDetailsActivity
import mohamedalaa.mautils.mautils_open_source_licences.view.OpenSourceLicencesActivity

/**
 * [RecyclerView.Adapter] for [List]<[Licence]> isa.
 */
internal class RCAdapterLicence(private val context: Context,
                                private var licences: List<Licence>?,
                                intent: Intent)
    : RecyclerView.Adapter<RCAdapterLicence.CustomViewHolder>() {

    private val licenceIntent = Intent(context, LicenceDetailsActivity::class.java).apply { replaceExtras(intent) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val rootView = parent.context.inflateLayout(R.layout.rc_adapter_licence, parent)

        setupDevConfigurations(rootView)

        return CustomViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val licence = licences?.get(position) ?: return

        holder.itemView.licenceName.text = if (licence.isAuthorExists) licence.licenceName else ""

        holder.itemView.licenceAuthor.text = if (licence.isAuthorExists) context.getString(R.string.by_colon_string, licence.licenceAuthor) else ""

        holder.itemView.licenceNameAlone.text = if (licence.isAuthorExists) "" else licence.licenceName

        val linkVisibility = if (licence.isLinkExists) {
            holder.itemView.linkButton.setOnClickListener {
                licence.link?.apply { context.launchWebLink(this, createIntentChooser = true) }
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

    // ---- Private fun

    private fun setupDevConfigurations(rootView: View) {
        // Item Background
        licenceIntent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_BACKGROUND_COLOR)?.apply {
            rootView.rootConstraintLayout.backgroundCompat = ColorDrawable(this)
        } ?: licenceIntent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_BACKGROUND_DRAWABLE_RES)?.apply {
            rootView.rootConstraintLayout.backgroundCompat = context.getDrawableFromRes(this)
        }

        // Toolbar title, subtitle and icon isa.
        val nameTextColor = licenceIntent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_LICENCE_NAME_TEXT_COLOR)
        val authorTextColor = licenceIntent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_LICENCE_AUTHOR_TEXT_COLOR)
        when {
            nameTextColor != null -> {
                rootView.licenceName.setTextColor(nameTextColor)
                rootView.licenceNameAlone.setTextColor(nameTextColor)

                rootView.licenceAuthor.setTextColor(authorTextColor ?: nameTextColor)
            }
            authorTextColor != null -> rootView.licenceAuthor.setTextColor(authorTextColor)
        }

        licenceIntent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_LINK_BUTTON_TEXT_COLOR)?.apply {
            rootView.linkButton.setTextColor(this)
        }
        licenceIntent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_LINK_BUTTON_TINT)?.apply {
            rootView.linkButton.setBackgroundTint(this)
        }
    }

    // ----- Custom View Holder

    class CustomViewHolder(rootView: View): RecyclerView.ViewHolder(rootView)

}