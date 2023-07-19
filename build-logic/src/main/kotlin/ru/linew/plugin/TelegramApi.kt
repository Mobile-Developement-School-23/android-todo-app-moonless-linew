package ru.linew.plugin

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentDisposition
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File


class TelegramApi(
    private val httpClient: HttpClient
) {

    suspend fun uploadFile(file: File){
        val response = httpClient.post(Constants.sendDocumentMethod) {
            parameter("chat_id", Constants.chatId)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("document", file.readBytes(), Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "${ContentDisposition.Parameters.FileName} = \"${file.name}\""
                            )
                        })
                    }
                )
            )
        }
        println("Success status = ${response.status}")
    }
}