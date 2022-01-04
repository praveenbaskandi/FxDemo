package com.praveen.funxdemo.appUI.validDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.praveen.funxdemo.R
import com.praveen.funxdemo.funx.BlockPageQuery

internal class BlockAdapter(
    private val dataListData: List<BlockPageQuery.Block>,
) : RecyclerView.Adapter<BlockAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_block, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataListData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listDataModel: BlockPageQuery.Block = dataListData[position]
        holder.tvChainId.text = listDataModel.chainId
        holder.tvHeight.text = listDataModel.height.toString()
        holder.tvValidatorName.text = listDataModel.validatorName
        holder.tvTx.text = "${listDataModel.txCount} Tx"
    }


    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvChainId = itemView.findViewById(R.id.tvChainId) as TextView
        val tvHeight = itemView.findViewById(R.id.tvHeight) as TextView
        val tvValidatorName = itemView.findViewById(R.id.tvValidatorName) as TextView
        val tvTx = itemView.findViewById(R.id.tvTx) as TextView

    }
}