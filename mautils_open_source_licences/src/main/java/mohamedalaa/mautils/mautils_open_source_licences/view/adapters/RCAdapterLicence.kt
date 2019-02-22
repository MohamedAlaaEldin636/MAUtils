package mohamedalaa.mautils.mautils_open_source_licences.view.adapters

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.core_android.layoutInflater
import mohamedalaa.mautils.mautils_open_source_licences.R
import mohamedalaa.mautils.mautils_open_source_licences.databinding.RcAdapterLicenceBinding
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.model.isAuthorExists
import mohamedalaa.mautils.mautils_open_source_licences.model.isLinkExists

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/22/2019.
 *
 */
class RCAdapterLicence(private var licences: List<Licence>?)
    : RecyclerView.Adapter<RCAdapterLicence.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<RcAdapterLicenceBinding>(
            parent.context.layoutInflater,
            R.layout.rc_adapter_licence,
            parent,
            false
        )

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val licence = licences?.get(position) ?: return

        holder.binding.licenceName.text = if (licence.isAuthorExists) licence.licenceName else ""

        holder.binding.licenceAuthor.text = if (licence.isAuthorExists) licence.licenceAuthor else ""

        holder.binding.licenceNameAlone.text = if (licence.isAuthorExists) "" else licence.licenceAuthor

        val linkVisibility = if (licence.isLinkExists) View.VISIBLE else View.GONE
        holder.binding.linkButton.visibility = linkVisibility
        holder.binding.view.visibility = linkVisibility
    }

    override fun getItemCount(): Int = licences?.size ?: 0

    // ---- Public fun

    fun swapList(licences: List<Licence>?) {
        this.licences = licences

        notifyDataSetChanged()
    }

    // ----- Custom View Holder

    class CustomViewHolder(val binding: RcAdapterLicenceBinding): RecyclerView.ViewHolder(binding.root)

}