# Movie
A android version of [The Movie DB](https://www.themoviedb.org/). This project is aim to create an app that can access the  movie, tv show, actor and actress database from source  in an easy way.

# Screenshot
![initial screen](https://i.imgur.com/D09pj3a.png) ![FragmentSearch](https://i.imgur.com/F9O83s6.png)  
![FragmentMovie](https://i.imgur.com/Yn6tVHa.png) ![MovieDetailActivity](https://i.imgur.com/WlCMhuK.png)

# Used libraries
* [RxBinding](https://github.com/JakeWharton/RxBinding) : let us to interact with UI in reactive way, also include RxJava2 and RxAndroid

* [Retrofit](https://square.github.io/retrofit/) : building service based on the RESTful API defined by THe Movie DB, we also need [GsonConverterFactory](https://github.com/square/retrofit/blob/master/retrofit-converters/gson/src/main/java/retrofit2/converter/gson/GsonConverterFactory.java) and [RxJava2CallAdapterFactory](https://github.com/square/retrofit/blob/master/retrofit-adapters/rxjava2/src/main/java/retrofit2/adapter/rxjava2/RxJava2CallAdapterFactory.java) to convert JSON type result to java object and make it observable


* [GSON](https://github.com/google/gson) : for converting JSON data to java objects easily


* [Glide](https://github.com/bumptech/glide) : for loading image


* [NetworkState](https://github.com/ALiao1432/NetworkState) : the library for monitoring if the network is connecting via Wifi, Mobile or not connecting. It provide the ability for app to reload the data when reconnection to valid network


* [ColorHintBar](https://github.com/SeamasShih/ColorHintBarLibrary) : the library built from my friend to work with ViewPager to indicate the current page of ViewPager


* [MorphView](https://github.com/ALiao1432/MorphView) : the library can perform animation defined by svg files

# More detail
![architecture](https://i.imgur.com/YumBjOW.jpg)
App architecture
* adapter : for configuring data returned by asynchronized request

* fragment : the main page for Discover, Movies, Tv Shows and people

* model : Plain Old Java Object(POJO) for JSON data

* service : define available service for this app

* util : helper class like listener and config

* view : custom view for the expandable text and gradient effect for movie poster

* activity : show the detail information for movie, tv show or person

- - -

![Overall working flow](https://i.imgur.com/3sfo1vb.png)

- - -

![Imgur](https://i.imgur.com/mnFz4wu.png)

[demo](https://photos.app.goo.gl/6ZXQQnLXXFW6az5E7)
