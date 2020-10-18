# NewsMVVM
## Android News App Using Kotlin, Android Jetpack Libraries

#### App emits The Guardian API and shows latest, searched, category based news on Webview. User may save news to read later.
> ### App Features
> MVVM, Room DB, Retrofit, Flow API, Live Data, Paging 3 Library, Repository Pattern, Error Handling


![download](https://user-images.githubusercontent.com/52549784/96355865-9c461980-10ef-11eb-8fe6-2d835c815e51.png)

> ### Architecture of data emit using Paging 3 Library
>  - **PagingSource** object defines a source of data and how to retrieve data from that source.
>  - **The Pager** component provides a public API for constructing instances of PagingData that are exposed in reactive streams, based on a PagingSource object and a **PagingConfig** configuration object.
>  - **PagingData** object is a container for a snapshot of paginated data. It queries a **PagingSource** object and stores the result.
>  - The primary Paging library component in the UI layer is **PagingDataAdapter**, a **RecyclerView** adapter that handles paginated data.

### App Preview

![news](https://user-images.githubusercontent.com/52549784/96355404-0956b080-10ea-11eb-9648-d7b62e90b3ce.jpg)
