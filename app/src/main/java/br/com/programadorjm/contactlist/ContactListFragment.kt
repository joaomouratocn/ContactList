package br.com.programadorjm.contactlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.programadorjm.contactlist.databinding.FragmentContactListBinding


class ContactListFragment : Fragment(), ContactAdapter.OnClick{

     private var binding : FragmentContactListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate using binding
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyleList?.layoutManager = LinearLayoutManager(binding?.root?.context)
        binding?.floatAdd?.setOnClickListener{openDetailFragment(null)}
    }

    override fun onResume() {
        super.onResume()
        binding?.recyleList?.adapter = ContactAdapter(setAdapter(), this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun newInstance() = ContactListFragment()
    }

    private fun setAdapter():List<Contact>{
        val sqlHelper = activity?.applicationContext?.let { SqlHelper.getInstance(it) }
        return sqlHelper?.getAllContacts("") ?: mutableListOf()
    }

    override fun itemClickListener(contact: Contact) {
        openDetailFragment(contact)
    }

    private fun openDetailFragment(contact: Contact? ){
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container_main, FragmentDetail.newInstance(contact))
            ?.addToBackStack(null)
            ?.commit()
    }
}