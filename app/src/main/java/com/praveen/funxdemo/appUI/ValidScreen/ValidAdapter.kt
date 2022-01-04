package com.praveen.funxdemo.appUI.ValidScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.praveen.funxdemo.R
import com.praveen.funxdemo.funx.ValidatorListQuery

internal class ValidAdapter(
    private val dataListListData: List<ValidatorListQuery.Validator>,
    private val mInterfaceValidId: InterfaceValidId,
) : RecyclerView.Adapter<ValidAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_validator, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataListListData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listDataModel: ValidatorListQuery.Validator = dataListListData[position]
        holder.tvHeading.text = "No.${listDataModel.rank}"
        holder.tvChainId.text = listDataModel.chainId
        holder.tvCity.text = listDataModel.validatorName
        holder.tvCurrentAPY.text = listDataModel.rewards.toString()
        holder.tvSelfBonded.text = listDataModel.selfBondedPercent.toString()
        holder.tvVotingPower.text = "${listDataModel.votingPowerPercent}%"
        holder.tvCommissionRate.text = "${listDataModel.commission}%"
    }


    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvHeading = itemView.findViewById(R.id.tvHeading) as TextView
        val tvChainId = itemView.findViewById(R.id.tvChainId) as TextView
        val tvCity = itemView.findViewById(R.id.tvCity) as TextView
        val tvVotingPower= itemView.findViewById(R.id.tvVotingPower) as TextView
        val tvCurrentAPY= itemView.findViewById(R.id.tvCurrentAPY) as TextView
        val tvSelfBonded= itemView.findViewById(R.id.tvSelfBonded) as TextView
        val tvCommissionRate= itemView.findViewById(R.id.tvCommissionRate) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val dataListData: ValidatorListQuery.Validator = dataListListData[layoutPosition]
            mInterfaceValidId.onValidatorClick(dataListData.operateAddress as String)
        }
    }
}