package mohamedalaa.mautils.mautils_open_source_licences.view.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rc_adapter_licence.view.*
import mohamedalaa.mautils.core_android.*
import mohamedalaa.mautils.core_kotlin.applyIf
import mohamedalaa.mautils.mautils_open_source_licences.R
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.model.isAuthorExists
import mohamedalaa.mautils.mautils_open_source_licences.model.isLinkExists
import mohamedalaa.mautils.mautils_open_source_licences.view.LicenceDetailsActivity
import mohamedalaa.mautils.mautils_open_source_licences.view.OpenSourceLicencesActivity
import kotlin.properties.Delegates

/**
 * [RecyclerView.Adapter] for [List]<[Licence]> isa.
 */
internal class RCAdapterLicence(private val context: Context,
                                private var licences: List<Licence>?,
                                intent: Intent,
                                searchText: String?)
    : RecyclerView.Adapter<RCAdapterLicence.CustomViewHolder>() {

    private val licenceIntent = Intent(context, LicenceDetailsActivity::class.java).apply { replaceExtras(intent) }

    private val filteredLicences = computeSearchedLicences()

    // todo checked chips for match case and any letter isa, ignore author.
    var searchText: String? by Delegates.observable(searchText) { _, _, _ ->
        filteredLicences.clear()
        filteredLicences.addAll(computeSearchedLicences())

        try {
            notifyDataSetChanged()
        } catch (e: Exception) {
            Log.e("RCAdapter", e.message ?: "Exception msg is null isa.")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val rootView = parent.context.inflateLayout(R.layout.rc_adapter_licence, parent)

        setupDevConfigurations(rootView)

        return CustomViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val (licence, highlightText) = if (searchText.isNullOrEmpty()) {
            (licences?.get(position) ?: return) to false
        }else {
            filteredLicences[position] to true
        }

        val name = licence.licenceName.toSpannable().applyIf(highlightText) {
            val generateNewSpan = { BackgroundColorSpan(Color.YELLOW) }
            val indicesList = spanChars(searchText, ignoreCase = true, allChars = false, generateNewSpan = generateNewSpan)

            holder.itemView.licenceName.postWithReceiver {
                if (layout.getEllipsisCount(0) > 0) {
                    val start = layout.getEllipsisStart(0)
                    val end = licence.licenceName.lastIndex
                    indicesList.forEach {
                        if (it in (start..end)) {
                            this@applyIf[start] = generateNewSpan()

                            holder.itemView.licenceName.text = if (licence.isAuthorExists) this@applyIf else ""
                            holder.itemView.licenceNameAlone.text = if (licence.isAuthorExists) "" else this@applyIf

                            return@postWithReceiver
                        }
                    }
                }
            }
        }
        val author = buildSpannedString {
            append(context.getString(R.string.by_colon_string, "").toSpannable())
            append(
                licence.licenceAuthor?.toSpannable()?.applyIf(highlightText) {
                    val generateNewSpan = { BackgroundColorSpan(Color.YELLOW) }
                    val indicesList = spanChars(searchText, ignoreCase = true, allChars = false, generateNewSpan = generateNewSpan)

                    holder.itemView.licenceAuthor.postWithReceiver {
                        if (layout.getEllipsisCount(0) > 0) {
                            val start = layout.getEllipsisStart(0)
                            val end = context.getString(R.string.by_colon_string, licence.licenceAuthor).lastIndex
                            indicesList.forEach {
                                if (it in (start..end)) {
                                    this@applyIf[start] = generateNewSpan()

                                    holder.itemView.licenceAuthor.text = if (licence.isAuthorExists) {
                                        buildSpannedString {
                                            append(context.getString(R.string.by_colon_string, "").toSpannable())
                                            append(this@applyIf)
                                        }
                                    }else {
                                        ""
                                    }

                                    return@postWithReceiver
                                }
                            }
                        }
                    }
                }
            )
        }
        //Log.e("RR", "$a, ${context.getString(R.string.by_colon_string, "").length};;")
        /*val author = "".toSpannable() + licence.licenceAuthor?.toSpannable()?.applyIf(highlightText) {
            val generateNewSpan = { BackgroundColorSpan(Color.YELLOW) }
            val indicesList = spanChars(searchText, ignoreCase = true, allChars = false, generateNewSpan = generateNewSpan)

            holder.itemView.licenceAuthor.postWithReceiver {
                if (layout.getEllipsisCount(0) > 0) {
                    val start = layout.getEllipsisStart(0)
                    val end = context.getString(R.string.by_colon_string, licence.licenceAuthor).lastIndex
                    indicesList.forEach {
                        if (it in (start..end)) {
                            this@applyIf[start] = generateNewSpan()

                            holder.itemView.licenceAuthor.text = if (licence.isAuthorExists) context.getString(R.string.by_colon_string, author) else ""

                            return@postWithReceiver
                        }
                    }
                }
            }
        }*/

        holder.itemView.licenceName.text = if (licence.isAuthorExists) name else ""

        holder.itemView.licenceAuthor.text = if (licence.isAuthorExists) author else ""

        holder.itemView.licenceNameAlone.text = if (licence.isAuthorExists) "" else name

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

    override fun getItemCount(): Int {
        return if (searchText.isNullOrEmpty()) {
            licences?.size ?: 0
        }else {
            filteredLicences.size
        }
    }

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

    private fun computeSearchedLicences(): MutableList<Licence> = try { searchText } catch (e: Exception) { null }.run {
        if (this.isNullOrEmpty()) {
            return mutableListOf()
        }

        licences?.mapNotNull {
            if (it.licenceName.toLowerCase().contains(this.toLowerCase())
                || it.licenceAuthor?.toLowerCase()?.contains(this.toLowerCase()) == true) {
                it
            }else {
                null
            }
        }?.toMutableList() ?: mutableListOf()
    }

    // ----- Custom View Holder

    class CustomViewHolder(rootView: View): RecyclerView.ViewHolder(rootView)

}