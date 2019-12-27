package com.kopyl.blockpc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kopyl.blockpc.R
import com.kopyl.blockpc.models.WorkstationModel
import kotlinx.android.synthetic.main.item_workstation.view.*

class WorkstationAdapter(
    private val mContext: Context,
    private val mWorkstationList: List<WorkstationModel>
): RecyclerView.Adapter<WorkstationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkstationViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_workstation, parent, false)
        return WorkstationViewHolder(view)
    }

    override fun getItemCount(): Int = mWorkstationList.size

    override fun onBindViewHolder(holder: WorkstationViewHolder, position: Int) {
        holder.bind(mWorkstationList[position])
    }

}

class WorkstationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(workstationModel: WorkstationModel){
        itemView.tv_workstation_name.text = workstationModel.name
        itemView.tv_workstation_number.text = workstationModel.code
    }
}