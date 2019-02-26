# Movie
A android version of The Movie DB,
using libraries 
* [RxBinding](https://github.com/JakeWharton/RxBinding) : to interact with UI in reactive way, include RxJava2 and RxAndroid  
* [RxJava2](https://github.com/ReactiveX/RxJava) : for building the asynchronized request to The Movie DB
* [Retrofit](https://square.github.io/retrofit/) & [GSON](https://github.com/google/gson) : building service based on the REST API from THe Movie DB
* [Glide](https://github.com/bumptech/glide) : for loading image
* [NetworkState](https://github.com/ALiao1432/NetworkState) : the library for monitoring if the network is connecting via Wifi, Mobile or not connecting. It provide the ability for app to reload the data when reconnection to valid network
* [ColorHintBar](https://github.com/SeamasShih/ColorHintBarLibrary) : the library built from my friend to work with ViewPager to indicate the current page of ViewPager
* [MorphView](https://github.com/ALiao1432/MorphView) : the library can perform animation define by svg files
# Demo gif link
[demo](https://photos.app.goo.gl/15vsfJmJJp2gVVbJ7)

# Architecture
![architecture](https://i.imgur.com/YumBjOW.jpg)
* adapter : for configuring data returned by asynchronized request
* fragment : the main page for Discover, Movies, Tv Shows and people
* model : Plain Old Java Object(POJO) for JSON data
* service : define available service for this app
* util : helper class like listener and config
* view : custom view for the expandable text and gradient effect for movie poster
* activity : show the detail information for movie, tv show or person
