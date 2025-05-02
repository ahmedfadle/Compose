package com.compose.app.common.network


sealed class IResult<out T> {
    data class Progress<T>(val loading: Boolean, val partialData: T? = null) : IResult<T>()
    data class Success<T>(val data: T) : IResult<T>()
    data class Failure<T>(val error: ErrorModel) : IResult<T>()

    companion object {
        fun <T> loading(isLoading: Boolean = true, partialData: T? = null): IResult<T> = Progress(isLoading, partialData)

        fun <T> success(data: T,): IResult<T> = Success(data)

        fun <T> failure(e: ErrorModel): IResult<T> = Failure(e)

        fun <T> failure(e: String): IResult<T> = Failure(
            ErrorModel(
                e, null, null, null, 0
            )
        )
    }

    fun isSuccessful(): Boolean{
        return this is Success
    }

    fun isFailed(): Boolean{
        return this is Failure
    }

    fun isLoading(): Boolean{
        return this is Progress
    }


}
fun <T> IResult<T>.getError(): ErrorModel?{
    if (this is IResult.Failure)
        return this.error

    return null
}


fun <T> IResult<T>.getData(): T?{
    if (this is IResult.Success)
        return this.data

    return null
}



//fun <T, H> IResult<T>.mapData(block: (input: T) -> H): IResult<H> {
//    return when (this) {
//        is IResult.Success -> IResult.success(block.invoke(data))
//        is IResult.Failure -> IResult.failure(error)
//        is IResult.Progress -> IResult.loading(loading, partialData?.let { block.invoke(it) })
//    }
//}