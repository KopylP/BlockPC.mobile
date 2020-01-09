package com.kopyl.blockpc.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kopyl.blockpc.R
import com.kopyl.blockpc.interfaces.WorkstationItemInterface
import com.kopyl.blockpc.models.WorkstationModel
import kotlinx.android.synthetic.main.item_workstation.view.*

class WorkstationAdapter(
    private val context: Context,
    private val workstationList: MutableList<WorkstationModel>,
    private val workstationItemInterface: WorkstationItemInterface
): RecyclerView.Adapter<WorkstationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkstationViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_workstation, parent, false)
        return WorkstationViewHolder(view)
    }

    override fun getItemCount(): Int = workstationList.size

    override fun onBindViewHolder(holder: WorkstationViewHolder, position: Int) {
        holder.bind(workstationList[position], workstationItemInterface)
    }

    fun addItem(workstationModel: WorkstationModel){
        workstationList.add(workstationModel)
        notifyItemInserted(workstationList.size - 1)
    }

    fun deleteItem(position: Int) {
        val model = workstationList[position]
        workstationList.removeAt(position)
        notifyItemRemoved(position)
        workstationItemInterface.deletePC(model)
    }

}

class WorkstationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(workstationModel: WorkstationModel, workstationItemInterface: WorkstationItemInterface){
        itemView.tv_workstation_name.text = workstationModel.name
        itemView.tv_workstation_number.text = workstationModel.code
        itemView.iv_workstation_lock.setOnClickListener {
            Log.i("Firebase", "Listener is work")
            workstationItemInterface.lockPC(workstationModel)
        }
    }
}