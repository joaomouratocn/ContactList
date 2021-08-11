package br.com.programadorjm.contactlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import br.com.programadorjm.contactlist.databinding.FragmentDetailBinding

class FragmentDetail : Fragment() {
    private var binding : FragmentDetailBinding? = null

    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contact = it.getParcelable(SELECTED_CONTACT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(contact != null){
            binding?.edtName?.setText(contact?.name)
            binding?.edtPhone?.setText(contact?.phone)
            binding?.btnSave?.setText(R.string.str_update)
            binding?.btnDelete?.isVisible = true

            binding?.btnSave?.setOnClickListener{
                val sqlHelper = activity?.applicationContext?.let { it1 -> SqlHelper.getInstance(it1) }
                sqlHelper?.updateContact(
                    Contact(
                        id = contact?.id as Int,
                        name = binding?.edtName?.text.toString(),
                        phone = binding?.edtPhone?.text.toString()
                    )
                )
                activity?.supportFragmentManager?.popBackStack()
            }

            binding?.btnDelete?.setOnClickListener{
                val sqlHelper = activity?.applicationContext?.let { it2 -> SqlHelper.getInstance(it2) }
                sqlHelper?.deleteContact(contact?.id as Int)
                activity?.supportFragmentManager?.popBackStack()
            }

        }else{
            binding?.btnSave?.setOnClickListener{
                val sqlHelper = activity?.applicationContext?.let { it1 -> SqlHelper.getInstance(it1) }
                sqlHelper?.insertContact(
                    Contact(
                        name = binding?.edtName?.text.toString(),
                        phone = binding?.edtPhone?.text.toString()
                    )
                )
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    companion object {
        private const val SELECTED_CONTACT = "selectedContact"
        fun newInstance(selectedContact: Contact?) =
            FragmentDetail().apply {
                arguments = Bundle().apply {
                    putParcelable(SELECTED_CONTACT, selectedContact)
                }
            }
    }
}