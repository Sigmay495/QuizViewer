/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: BlankOutputPanel.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.frame.component

import com.sigmay.quizviewer.common.DEFAULT_FONT_SIZE
import com.sigmay.quizviewer.common.DEFAULT_MARGIN
import com.sigmay.quizviewer.common.DEFAULT_THRESHOLD
import com.sigmay.quizviewer.entity.BlanksQuiz
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.border.EmptyBorder

/**
 * テキストフィールド表示部分のパネル。
 *
 * @constructor
 * 表示したいクイズ [quiz] を入力してテキストフィールドパネルを作成する。
 */
class BlankOutputPanel(quiz: BlanksQuiz, response: Array<String>) : InputPanel() {
    /**
     * 入力するテキストフィールド
     */
    private val textFields = Array(quiz.answers.size) { JTextField(12) }

    init {
        layout = FlowLayout()

        // 答え合わせ
        val result = quiz.markAnswers(response)

        // 全てのテキストフィールドを生成
        for (i in textFields.indices) {
            val rowPanel = JPanel()

            val markLabel = JLabel()
            if (result[i]) {
                markLabel.text = "✔"
                markLabel.foreground = Color.GREEN
            } else if (!result[i] && quiz.record[i] < DEFAULT_THRESHOLD) {
                markLabel.text = "✖"
                markLabel.foreground = Color.RED
            }
            markLabel.preferredSize = Dimension(DEFAULT_FONT_SIZE, (DEFAULT_FONT_SIZE * 1.5).toInt())
            markLabel.horizontalAlignment = JLabel.CENTER
            markLabel.verticalAlignment = JLabel.CENTER
            rowPanel.add(markLabel)

            // 問題番号を表示
            val numberLabel = JLabel("(${quiz.numberIndex[i]})")
            numberLabel.font = Font(numberLabel.font.name, numberLabel.font.style, DEFAULT_FONT_SIZE)
            numberLabel.preferredSize = Dimension(DEFAULT_FONT_SIZE * 3, (DEFAULT_FONT_SIZE * 1.5).toInt())
            numberLabel.horizontalAlignment = JLabel.CENTER
            numberLabel.verticalAlignment = JLabel.CENTER
            rowPanel.add(numberLabel)

            // テキストフィールドを表示
            textFields[i].font = Font(textFields[i].font.name, textFields[i].font.style, DEFAULT_FONT_SIZE)
            textFields[i].text = quiz.answers[i]
            textFields[i].isEditable = false
            if (!result[i] && quiz.record[i] < DEFAULT_THRESHOLD) {
                textFields[i].text = "${textFields[i].text}（${response[i]}）"
                textFields[i].foreground = Color.RED
            }
            rowPanel.add(textFields[i])

            // 問題番号を表示
            val suffixberLabel = JLabel(quiz.suffix[i])
            suffixberLabel.font = Font(suffixberLabel.font.name, suffixberLabel.font.style, DEFAULT_FONT_SIZE)
            suffixberLabel.preferredSize = Dimension(DEFAULT_FONT_SIZE * 5, (DEFAULT_FONT_SIZE * 1.5).toInt())
            suffixberLabel.verticalAlignment = JLabel.CENTER
            rowPanel.add(suffixberLabel)

            add(rowPanel)
        }
        preferredSize = Dimension(this.width, (DEFAULT_FONT_SIZE * 1.25 * quiz.answers.size).toInt() + 10)
        border = EmptyBorder(DEFAULT_MARGIN)
    }

    /**
     * 入力結果を取得する。
     *
     * @return 入力結果の配列
     */
    override fun getResponse() = Array(textFields.size) { textFields[it].text }
}
