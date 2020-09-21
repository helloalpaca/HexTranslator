package com.minseon.hextranslator.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.minseon.hextranslator.R
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    lateinit var textView: TextView
    lateinit var editText: EditText
    lateinit var btn : ImageButton
    lateinit var btn_paste : ImageButton
    //lateinit var clipboard : ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        textView = root.findViewById(R.id.section_label)
        editText = root.findViewById(R.id.editText)
        btn = root.findViewById(R.id.btn)
        btn_paste = root.findViewById(R.id.btn_paste)
        //clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        var num  = arguments?.getInt(ARG_SECTION_NUMBER) ?:1

        btn.setOnClickListener {
            var str = editText.text.toString()
            if(num==1){
                textView.text = stringTohex(str)
            } else{
                textView.text = hexTostring(str)
            }
        }

        btn_paste.setOnClickListener {
            val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("hexTranslator", textView.text.toString())
            clipboard.setPrimaryClip(clip)
        }

        return root
    }

    fun stringTohex(str: String) : String{
        var result = ""
        var temp = str.toByteArray(Charsets.UTF_8)
        for (b in temp) {
            println(b.toByte())
            val st = String.format("%02X", b)
            result += st
        }
        return result
    }

    fun hexTostring(str: String) : String{
        var result = ""
        if(str.length%2==1)
            return "16진수가 아닙니다."
        val regex = Regex(pattern = "[a-fA-F_0-9]*")
        if(!str.matches(regex))
            return "16진수가 아닙니다."

        var buff = ByteBuffer.allocate(str.length / 2)
        for (i in 0..str.length - 1 step 2) {
            buff.put(Integer.parseInt(str.substring(i, i + 2), 16).toByte())
        }
        buff.rewind()
        var cs = Charsets.UTF_8
        result = cs.decode(buff).toString()

        return result
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}