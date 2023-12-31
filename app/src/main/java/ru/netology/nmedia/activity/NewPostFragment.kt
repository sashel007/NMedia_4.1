package ru.netology.nmedia.activity

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

//    companion object {
//        var Bundle.text: String? by StringArg
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by activityViewModels()

        val args: NewPostFragmentArgs by navArgs()
        val postId = args.postId
        val postContent = args.postContent

//        val text = arguments?.text
        if (!postContent.isNullOrEmpty()) {
            binding.edit.setText(postContent)
        }
        binding.edit.requestFocus()
        binding.edit.postDelayed({
            showKeyBoard(binding.edit)
        }, 200)
        binding.ok.setOnClickListener {
            if (!binding.edit.text.isNullOrBlank()) {
                val content = binding.edit.text.toString()
                if (postId != 0L) {
                    viewModel.updatePost(postId, content)
                } else {
                    viewModel.addNewPost(content)
                }
                findNavController().navigateUp()
            }
        }

        return binding.root
    }

    private fun showKeyBoard(view: View) {
        val context = requireContext()
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}



