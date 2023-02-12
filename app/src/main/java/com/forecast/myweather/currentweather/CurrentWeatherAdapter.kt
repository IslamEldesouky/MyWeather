import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.forecast.domain.entity.SearchItem
import com.forecast.myweather.databinding.RvSearchItemBinding

class CurrentWeatherAdapter(private val itemSelected: ItemSelected) :
    ListAdapter<SearchItem, CurrentWeatherAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            RvSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),itemSelected)

    }

    class ViewHolder(private val itemBinding: RvSearchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(searchItem: SearchItem, listener: ItemSelected) {
            itemBinding.tvCountry.text = searchItem.cityName
            itemBinding.tvTmp.text = searchItem.tmp
            itemBinding.layoutItem.setOnClickListener { listener.itemSelected(searchItem) }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(
            oldItem: SearchItem,
            newItem: SearchItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchItem,
            newItem: SearchItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemSelected {
        fun itemSelected(searchItem: SearchItem)
    }
}