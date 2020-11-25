 package com.platzi.conf.view.ui.Fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.platzi.conf.R
import com.platzi.conf.model.Speaker
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_speakers_detail_dialog.*
import java.util.*


 enum class SwipeDirections {
    UP, DOWN, LEFT, RIGHT
}

class GameFragment : DialogFragment() {

    companion object {
        private const val TOTAL_COLUMNS = 3
        private const val DIMENSIONS = TOTAL_COLUMNS * TOTAL_COLUMNS

        private var boardColumnWidth = 0
        private var boardColumnHeight = 0
    }

    private val tileListIndexes = mutableListOf<Int>()

    private val isSolved: Boolean
        get() {
            var solved = false
            for (i in tileListIndexes.indices) {
                if (tileListIndexes[i] == i) {
                    solved = true
                } else {
                    solved = false
                    break
                }
            }

            return solved
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarGame.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_close)
        toolbarGame.setNavigationOnClickListener {
            dismiss()
        }

        iniciali()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun iniciali(){
        init()
        scrambleTileBoard()
        setTileBoardDimensions()
    }

    private fun init() {
        gesture_detect_grid_view.apply {
            numColumns = TOTAL_COLUMNS
            setOnSwipeListener(object : GestureDetectGridView.OnSwipeListener {
                override fun onSwipe(direction: SwipeDirections, position: Int) {
                    moveTiles(direction, position)
                }
            })
        }

        tileListIndexes += 0 until DIMENSIONS
    }

    private fun scrambleTileBoard() {
        var index: Int
        var tempIndex: Int
        val random = Random()

        for (i in tileListIndexes.size - 1 downTo 1) {
            index = random.nextInt(i + 1)
            tempIndex = tileListIndexes[index]
            tileListIndexes[index] = tileListIndexes[i]
            tileListIndexes[i] = tempIndex
        }
    }

    private fun setTileBoardDimensions() {
        val observer = gesture_detect_grid_view.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                gesture_detect_grid_view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val displayWidth = gesture_detect_grid_view.measuredWidth
                val displayHeight = gesture_detect_grid_view.measuredHeight
                val statusbarHeight = getStatusBarHeight(context!!.applicationContext)
                val requiredHeight = displayHeight - statusbarHeight

                boardColumnWidth = displayWidth / TOTAL_COLUMNS
                boardColumnHeight = requiredHeight / TOTAL_COLUMNS

                displayTileBoard()
            }
        })
    }

    private fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }

        return result
    }


    /**
     * Used for both init and every time a new swap move is made by the user.
     */
    private fun displayTileBoard() {

        val speaker = arguments?.getSerializable("speaker") as Speaker
        val tileImages = mutableListOf<ImageView>()
        var tileImage: ImageView


        tileListIndexes.forEach { i ->
            tileImage = ImageView(requireContext().applicationContext)
            if (speaker.category == 1) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3)
                }
            }
            else if (speaker.category == 2) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_2)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_2)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_2)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_2)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_2)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_2)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_2)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_2)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_2)
                }
            }
            else if (speaker.category == 3) {
                when (i) {
                        0 -> tileImage.setBackgroundResource(R.drawable.f1c1_3)
                        1 -> tileImage.setBackgroundResource(R.drawable.f1c2_3)
                        2 -> tileImage.setBackgroundResource(R.drawable.f1c3_3)
                        3 -> tileImage.setBackgroundResource(R.drawable.f2c1_3)
                        4 -> tileImage.setBackgroundResource(R.drawable.f2c2_3)
                        5 -> tileImage.setBackgroundResource(R.drawable.f2c3_3)
                        6 -> tileImage.setBackgroundResource(R.drawable.f3c1_3)
                        7 -> tileImage.setBackgroundResource(R.drawable.f3c2_3)
                        8 -> tileImage.setBackgroundResource(R.drawable.f3c3_3)
                    }
            }
            else if (speaker.category == 4) {
                when (i) {
                        0 -> tileImage.setBackgroundResource(R.drawable.f1c1_4)
                        1 -> tileImage.setBackgroundResource(R.drawable.f1c2_4)
                        2 -> tileImage.setBackgroundResource(R.drawable.f1c3_4)
                        3 -> tileImage.setBackgroundResource(R.drawable.f2c1_4)
                        4 -> tileImage.setBackgroundResource(R.drawable.f2c2_4)
                        5 -> tileImage.setBackgroundResource(R.drawable.f2c3_4)
                        6 -> tileImage.setBackgroundResource(R.drawable.f3c1_4)
                        7 -> tileImage.setBackgroundResource(R.drawable.f3c2_4)
                        8 -> tileImage.setBackgroundResource(R.drawable.f3c3_4)
                    }
            }
            else if (speaker.category == 5) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_5)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_5)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_5)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_5)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_5)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_5)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_5)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_5)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_5)
                }
            }
            else if (speaker.category == 6) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_6)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_6)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_6)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_6)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_6)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_6)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_6)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_6)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_6)
                }
            }
            else if (speaker.category == 7) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_7)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_7)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_7)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_7)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_7)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_7)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_7)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_7)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_7)
                }
            }
            else if (speaker.category == 8) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_8)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_8)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_8)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_8)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_8)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_8)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_8)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_8)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_8)
                }
            }
            else if (speaker.category == 9) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_9)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_9)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_9)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_9)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_9)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_9)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_9)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_9)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_9)
                }
            }
            else if (speaker.category == 10) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_10)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_10)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_10)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_10)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_10)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_10)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_10)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_10)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_10)
                }
            }
            else if (speaker.category == 11) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_11)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_11)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_11)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_11)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_11)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_11)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_11)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_11)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_11)
                }
            }
            else if (speaker.category == 12) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_12)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_12)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_12)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_12)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_12)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_12)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_12)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_12)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_12)
                }
            }
            else if (speaker.category == 16) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_16)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_16)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_16)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_16)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_16)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_16)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_16)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_16)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_16)
                }
            }
            else if (speaker.category == 17) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_17)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_17)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_17)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_17)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_17)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_17)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_17)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_17)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_17)
                }
            }
            else if (speaker.category == 18) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_18)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_18)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_18)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_18)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_18)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_18)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_18)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_18)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_18)
                }
            }
            else if (speaker.category == 19) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_19)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_19)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_19)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_19)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_19)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_19)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_19)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_19)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_19)
                }
            }
            else if (speaker.category == 20) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_20)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_20)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_20)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_20)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_20)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_20)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_20)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_20)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_20)
                }
            }
            else if (speaker.category == 21) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_21)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_21)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_21)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_21)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_21)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_21)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_21)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_21)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_21)
                }
            }
            else if (speaker.category == 22) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_22)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_22)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_22)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_22)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_22)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_22)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_22)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_22)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_22)
                }
            }
            else if (speaker.category == 23) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_23)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_23)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_23)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_23)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_23)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_23)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_23)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_23)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_23)
                }
            }
            else if (speaker.category == 24) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_24)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_24)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_24)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_24)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_24)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_24)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_24)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_24)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_24)
                }
            }
            else if (speaker.category == 25) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_25)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_25)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_25)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_25)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_25)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_25)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_25)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_25)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_25)
                }
            }
            else if (speaker.category == 26) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_26)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_26)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_26)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_26)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_26)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_26)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_26)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_26)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_26)
                }
            }
            else if (speaker.category == 27) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_27)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_27)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_27)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_27)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_27)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_27)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_27)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_27)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_27)
                }
            }
            else if (speaker.category == 28) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_28)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_28)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_28)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_28)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_28)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_28)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_28)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_28)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_28)
                }
            }
            else if (speaker.category == 29) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_29)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_29)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_29)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_29)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_29)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_29)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_29)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_29)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_29)
                }
            }
            else if (speaker.category == 30) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_30)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_30)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_30)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_30)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_30)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_30)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_30)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_30)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_30)
                }
            }
            else if (speaker.category == 31) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_31)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_31)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_31)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_31)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_31)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_31)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_31)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_31)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_31)
                }
            }
            else if (speaker.category == 32) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_32)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_32)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_32)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_32)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_32)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_32)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_32)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_32)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_32)
                }
            }
            else if (speaker.category == 33) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_33)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_33)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_33)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_33)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_33)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_33)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_33)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_33)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_33)
                }
            }
            else if (speaker.category == 34) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_34)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_34)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_34)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_34)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_34)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_34)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_34)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_34)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_34)
                }
            }
            else if (speaker.category == 35) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_35)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_35)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_35)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_35)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_35)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_35)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_35)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_35)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_35)
                }
            }
            else if (speaker.category == 36) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_36)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_36)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_36)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_36)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_36)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_36)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_36)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_36)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_36)
                }
            }
            else if (speaker.category == 37) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_37)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_37)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_37)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_37)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_37)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_37)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_37)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_37)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_37)
                }
            }
            else if (speaker.category == 41) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_41)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_41)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_41)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_41)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_41)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_41)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_41)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_41)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_41)
                }
            }
            else if (speaker.category == 42) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_42)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_42)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_42)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_42)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_42)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_42)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_42)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_42)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_42)
                }
            }
            else if (speaker.category == 43) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_43)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_43)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_43)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_43)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_43)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_43)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_43)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_43)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_43)
                }
            }
            else if (speaker.category == 44) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_44)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_44)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_44)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_44)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_44)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_44)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_44)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_44)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_44)
                }
            }
            else if (speaker.category == 45) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_45)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_45)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_45)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_45)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_45)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_45)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_45)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_45)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_45)
                }
            }
            else if (speaker.category == 46) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_46)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_46)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_46)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_46)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_46)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_46)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_46)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_46)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_46)
                }
            }
            else if (speaker.category == 47) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_47)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_47)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_47)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_47)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_47)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_47)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_47)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_47)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_47)
                }
            }
            else if (speaker.category == 48) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_48)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_48)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_48)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_48)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_48)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_48)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_48)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_48)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_48)
                }
            }
            else if (speaker.category == 49) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_49)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_49)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_49)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_49)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_49)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_49)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_49)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_49)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_49)
                }
            }
            else if (speaker.category == 50) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_50)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_50)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_50)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_50)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_50)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_50)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_50)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_50)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_50)
                }
            }
            else if (speaker.category == 51) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_51)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_51)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_51)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_51)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_51)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_51)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_51)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_51)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_51)
                }
            }
            else if (speaker.category == 52) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_52)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_52)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_52)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_52)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_52)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_52)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_52)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_52)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_52)
                }
            }
            else if (speaker.category == 53) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_53)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_53)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_53)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_53)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_53)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_53)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_53)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_53)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_53)
                }
            }
            else if (speaker.category == 54) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_54)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_54)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_54)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_54)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_54)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_54)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_54)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_54)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_54)
                }
            }
            else if (speaker.category == 55) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_55)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_55)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_55)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_55)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_55)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_55)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_55)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_55)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_55)
                }
            }
            else if (speaker.category == 56) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_56)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_56)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_56)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_56)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_56)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_56)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_56)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_56)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_56)
                }
            }
            else if (speaker.category == 57) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_57)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_57)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_57)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_57)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_57)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_57)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_57)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_57)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_57)
                }
            }
            else if (speaker.category == 58) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_58)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_58)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_58)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_58)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_58)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_58)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_58)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_58)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_58)
                }
            }
            else if (speaker.category == 59) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_59)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_59)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_59)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_59)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_59)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_59)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_59)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_59)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_59)
                }
            }
            else if (speaker.category == 60) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_60)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_60)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_60)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_60)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_60)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_60)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_60)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_60)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_60)
                }
            }
            else if (speaker.category == 61) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_61)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_61)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_61)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_61)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_61)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_61)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_61)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_61)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_61)
                }
            }
            else if (speaker.category == 62) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_62)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_62)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_62)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_62)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_62)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_62)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_62)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_62)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_62)
                }
            }
            else if (speaker.category == 63) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_63)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_63)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_63)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_63)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_63)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_63)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_63)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_63)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_63)
                }
            }
            else if (speaker.category == 64) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_64)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_64)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_64)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_64)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_64)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_64)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_64)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_64)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_64)
                }
            }
            else if (speaker.category == 65) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_65)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_65)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_65)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_65)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_65)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_65)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_65)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_65)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_65)
                }
            }
            else if (speaker.category == 66) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_66)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_66)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_66)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_66)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_66)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_66)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_66)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_66)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_66)
                }
            }
            else if (speaker.category == 67) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_67)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_67)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_67)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_67)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_67)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_67)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_67)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_67)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_67)
                }
            }
            else if (speaker.category == 68) {
                when (i) {
                    0 -> tileImage.setBackgroundResource(R.drawable.f1c1_68)
                    1 -> tileImage.setBackgroundResource(R.drawable.f1c2_68)
                    2 -> tileImage.setBackgroundResource(R.drawable.f1c3_68)
                    3 -> tileImage.setBackgroundResource(R.drawable.f2c1_68)
                    4 -> tileImage.setBackgroundResource(R.drawable.f2c2_68)
                    5 -> tileImage.setBackgroundResource(R.drawable.f2c3_68)
                    6 -> tileImage.setBackgroundResource(R.drawable.f3c1_68)
                    7 -> tileImage.setBackgroundResource(R.drawable.f3c2_68)
                    8 -> tileImage.setBackgroundResource(R.drawable.f3c3_68)
                }
            }

            tileImages.add(tileImage)
        }

        gesture_detect_grid_view.adapter = TileImageAdapter(
            tileImages,
            boardColumnWidth,
            boardColumnHeight
        )
    }

    private fun displayToast(@StringRes textResId: Int) {
        Toast.makeText(
            requireContext().applicationContext,
            getString(textResId),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun moveTiles(direction: SwipeDirections, position: Int) {
        // Upper-left-corner tile
        if (position == 0) {
            when (direction) {
                SwipeDirections.RIGHT -> swapTile(position, 1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS)
                else -> displayToast(R.string.invalid_move)
            }
            // Upper-center tiles
        } else if (position > 0 && position < TOTAL_COLUMNS - 1) {
            when (direction) {
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> displayToast(R.string.invalid_move)
            }
            // Upper-right-corner tile
        } else if (position == TOTAL_COLUMNS - 1) {
            when (direction) {
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS)
                else -> displayToast(R.string.invalid_move)
            }
            // Left-side tiles
        } else if (position > TOTAL_COLUMNS - 1 && position < DIMENSIONS - TOTAL_COLUMNS && position % TOTAL_COLUMNS == 0) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS)
                else -> displayToast(R.string.invalid_move)
            }
            // Right-side AND bottom-right-corner tiles
        } else if (position == TOTAL_COLUMNS * 2 - 1 || position == TOTAL_COLUMNS * 3 - 1) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.DOWN -> {
                    // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                    // right-corner tile.
                    if (position <= DIMENSIONS - TOTAL_COLUMNS - 1) {
                        swapTile(position, TOTAL_COLUMNS)
                    } else {
                        displayToast(R.string.invalid_move)
                    }
                }
                else -> displayToast(R.string.invalid_move)
            }
            // Bottom-left corner tile
        } else if (position == DIMENSIONS - TOTAL_COLUMNS) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> displayToast(R.string.invalid_move)
            }
            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - TOTAL_COLUMNS) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> displayToast(R.string.invalid_move)
            }
            // Center tiles
        } else {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> swapTile(position, TOTAL_COLUMNS)
            }
        }
    }

    private fun swapTile(currentPosition: Int, swap: Int) {
        val newPosition = tileListIndexes[currentPosition + swap]
        tileListIndexes[currentPosition + swap] = tileListIndexes[currentPosition]
        tileListIndexes[currentPosition] = newPosition
        displayTileBoard()

        if (isSolved) {
            displayToast(R.string.winner)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

}
