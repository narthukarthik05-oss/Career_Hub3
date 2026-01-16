package com.example.careerhub

import android.content.ContentValues
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStream

class ResumePreviewActivity : AppCompatActivity() {

    private var savedPdfUri: Uri? = null
    private lateinit var resumeText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_preview)

        val tvResume = findViewById<TextView>(R.id.tvResume)
        val btnDownloadPdf = findViewById<Button>(R.id.btnDownloadPdf)
        val btnSharePdf = findViewById<Button>(R.id.btnSharePdf)

        resumeText = intent.getStringExtra("resume_data")
            ?: "No resume data available"

        tvResume.text = resumeText

        btnDownloadPdf.setOnClickListener {
            savedPdfUri = savePdfToDownloads(resumeText)
            if (savedPdfUri != null) {
                openPdf(savedPdfUri!!)
            }
        }

        btnSharePdf.setOnClickListener {
            if (savedPdfUri != null) {
                sharePdf(savedPdfUri!!)
            } else {
                Toast.makeText(this, "Please download PDF first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ✅ SAVE PDF TO REAL DOWNLOADS (VISIBLE)
    private fun savePdfToDownloads(text: String): Uri? {
        return try {
            val pdfDocument = PdfDocument()
            val paint = Paint().apply { textSize = 14f }

            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas: Canvas = page.canvas

            var y = 40
            val x = 30
            val lineHeight = 20

            text.lines().forEach { line ->
                canvas.drawText(line, x.toFloat(), y.toFloat(), paint)
                y += lineHeight
            }

            pdfDocument.finishPage(page)

            val resumeName = extractName(text)
            val fileName = "${resumeName}_Resume.pdf"

            val values = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.RELATIVE_PATH, "Download/")
                put(MediaStore.Downloads.IS_PENDING, 1)
            }

            val resolver = contentResolver
            val uri = resolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                values
            ) ?: return null

            val outputStream: OutputStream =
                resolver.openOutputStream(uri) ?: return null

            pdfDocument.writeTo(outputStream)
            outputStream.flush()
            outputStream.close()
            pdfDocument.close()

            values.clear()
            values.put(MediaStore.Downloads.IS_PENDING, 0)
            resolver.update(uri, values, null, null)

            Toast.makeText(
                this,
                "PDF saved in Downloads as $fileName",
                Toast.LENGTH_LONG
            ).show()

            uri

        } catch (e: Exception) {
            Toast.makeText(this, "PDF creation failed", Toast.LENGTH_SHORT).show()
            null
        }
    }

    // ✅ AUTO OPEN PDF
    private fun openPdf(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        startActivity(intent)
    }

    // ✅ SHARE PDF
    private fun sharePdf(uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        startActivity(Intent.createChooser(shareIntent, "Share Resume PDF"))
    }

    // ✅ EXTRACT NAME FROM RESUME TEXT
    private fun extractName(text: String): String {
        val line = text.lines().firstOrNull { it.startsWith("Name:") }
        return line?.replace("Name:", "")?.trim()?.replace(" ", "_")
            ?: "My"
    }
}
