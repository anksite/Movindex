package com.anksite.movindex

import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import com.anksite.movindex.api.InterfaceApi
import com.anksite.movindex.api.ResultCallAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader


class EndpointTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var interfaceApi: InterfaceApi

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        interfaceApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build().create(InterfaceApi::class.java)
    }

    @Test
    fun testDiscover() = runTest {
        println("testDiscover")
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)
        mockResponse.setBody(getJsonFromFile("/discover.json"))
        mockWebServer.enqueue(mockResponse)

        interfaceApi.discover().onSuccess {
            println("Result size: ${it.results.size}")
            assertEquals(true, it.results.isNotEmpty())
        }.onFailure {
            println("onFailure: ${it.localizedMessage}")
            fail()
        }

        mockWebServer.takeRequest()
    }

    @Test
    fun testMovie() = runTest {
        println("testMovie")
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)
        mockResponse.setBody(getJsonFromFile("/movie-id.json"))
        mockWebServer.enqueue(mockResponse)

        interfaceApi.movie("385687").onSuccess {
            println("Title: ${it.title}")
            assertEquals("Fast X", it.title)
        }.onFailure {
            println("onFailure: ${it.localizedMessage}")
            fail()
        }

        mockWebServer.takeRequest()
    }

    @Test
    fun testReview() = runTest {
        println("testReview")
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)
        mockResponse.setBody(getJsonFromFile("/movie-id-reviews.json"))
        mockWebServer.enqueue(mockResponse)

        interfaceApi.reviews("385687").onSuccess {
            println("Result size: ${it.results.size}")
            assertEquals(true, it.results.isNotEmpty())
        }.onFailure {
            println("onFailure: ${it.localizedMessage}")
            fail()
        }

        mockWebServer.takeRequest()
    }

    @Test
    fun testVideo() = runTest {
        println("testVideo")
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)
        mockResponse.setBody(getJsonFromFile("/movie-id-videos.json"))
        mockWebServer.enqueue(mockResponse)

        interfaceApi.video("385687").onSuccess {
            println("Result size: ${it.results.size}")
            assertEquals(true, it.results.isNotEmpty())
        }.onFailure {
            println("onFailure: ${it.localizedMessage}")
            fail()
        }

        mockWebServer.takeRequest()
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    private fun getJsonFromFile(filename: String):String{
        val inputStream = Helper::class.java.getResourceAsStream(filename)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach{
            builder.append(it)
        }
        return builder.toString()
    }
}