import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.forecast.domain.entity.CurrentWeatherResponse
import com.forecast.domain.entity.ForecastResponse
import com.forecast.domain.entity.SearchItem
import com.forecast.myweather.databinding.RvForecastItemBinding
import com.forecast.myweather.databinding.RvSearchItemBinding

class ForecastAdapter(private val filterTemp: String) :
    ListAdapter<CurrentWeatherResponse, ForecastAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            RvForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, filterTemp)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    class ViewHolder(private val itemBinding: RvForecastItemBinding, filterTemp: String) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var temp = filterTemp
        fun bind(currentWeatherResponse: CurrentWeatherResponse) {
            itemBinding.tvCountry.text = currentWeatherResponse.name
            Log.d("DATATEMP", temp)
            if (temp.equals("metric")) {
                itemBinding.tvTmp.text = currentWeatherResponse.main.temp.toString() + "°C"
            } else
                itemBinding.tvTmp.text = currentWeatherResponse.main.temp.toString() + "°F"
            itemBinding.tvTime.text = currentWeatherResponse.dt_txt.split(" ")[1]
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<CurrentWeatherResponse>() {
        override fun areItemsTheSame(
            oldItem: CurrentWeatherResponse,
            newItem: CurrentWeatherResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CurrentWeatherResponse,
            newItem: CurrentWeatherResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}