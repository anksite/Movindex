package com.anksite.movindex.api

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ResultCall<T>(val delegate: Call<T>) :
    Call<Result<T>> {
    val TAG = "ResultCall"

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        callback.onResponse(
                            this@ResultCall, Response.success(
                                response.code(), Result.success(response.body()!!)
                            )
                        )
                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody.isNullOrEmpty()) {
                            callback.onResponse(
                                this@ResultCall, Response.success(
                                    Result.failure(HttpException(response))
                                )
                            )
                        } else {
                            callback.onResponse(
                                this@ResultCall,
                                Response.success(Result.failure(RuntimeException(errorBody)))
                            )
                        }
                    }
                }


                override fun onFailure(call: Call<T>, t: Throwable) {
                    var errorMessage = "No message!"

                    if(t.message!!.contains("Unable to resolve host")){
                        errorMessage = "Check your network connection!"
                    } else {
                        errorMessage = t.message!!
                    }

                    callback.onResponse(
                        this@ResultCall,
                        Response.success(Result.failure(RuntimeException(errorMessage, t)))
                    )
                }
            }
        )
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun execute(): Response<Result<T>> {
        return Response.success(Result.success(delegate.execute().body()!!))
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun clone(): Call<Result<T>> {
        return ResultCall(delegate.clone())
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }
}
