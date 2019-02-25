# Movie
A android version of The Movie DB,
using libraries 
* [RxBinding](https://github.com/JakeWharton/RxBinding) : to interact with UI in reactive way, include RxJava2 and RxAndroid  
* [RxJava2](https://github.com/ReactiveX/RxJava) : for building the asynchronized request to The Movie DB
* [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson) : building service based on the REST API from THe Movie DB
* [Glide](https://github.com/bumptech/glide) : for loading image
* [NetworkState](https://github.com/ALiao1432/NetworkState) : the library I built for monitoring if the network is connecting via Wifi, Mobile or not connecting. It provide the ability for app to reload the data when reconnection to valid network
* [ColorHintBar](https://github.com/SeamasShih/ColorHintBarLibrary) : the library built from my friend to work with ViewPager to indicate the current page of ViewPager

# Demo gif link
[demo](https://photos.app.goo.gl/15vsfJmJJp2gVVbJ7)

# Architecture
![architecture](https://lh3.googleusercontent.com/SqTvfg3d45IkgNFdGfSUI-uLwluHRrOdB0bHxwOl-dZRIGjCvlOBuLdzB7t1MDLmWHmUshrXC1FTNkFd7-r3yEPHUxixl1TqcNirw3MkZyuSSnvTDR5rHF5N8yI-szv4ci-ksd0iF_u-LD_R1MaLkN7dtmQSfWVZB0klnj7JJkGDtKl3QuC7PGYwqHPS_FT1NRq2ChvW0cxLlAWcUEul5_P-0oJOf215bLpVEnXYMUTC2OrPj1omzjAMsTCq6kcHTWem2ybZNrE1Hb9USwyLWQMVMTdYuS1Em0HMfvvkL9SYekUxoRUd6bUUR-mefXrlKA8HFyoKmPEciNbPhMYc1xwE99J9sVOAtxSgghST0PSjhnT0Wr3zb9RoK96Hc6P1ljEz_f5ouOTUvEIOU7BiipSBL9_g88ujFHEJkH-YMZEVcrqSX7BTYZ50xeZivyX0JbFBnSJP6OFR5U2P0lnNtJIPLSD7evNE-6jlIYKv_56HlrHgUh8bhplMzo_60dUNhry9BQghr5o600W9m-Ns7Ezlo8rumtaaDyn59mTY7A5eb_ym-k-sJlC4GmxqFTfGr9M0a9tQBCqljtlXOn6J4WT9PA8FWwFGiDTI-0nLLKLT1R3_X7Fc076ra_Lj9zAzaJN_MzgeKMaZhhCTQ1Hvf8DPhhCbAw=w1099-h267-no)
* adapter : for configuring data returned by asynchronized request
* fragment : the main page for Discover, Movies, Tv Shows and people
* model : Plain Old Java Object(POJO) for JSON data
* service : define available service for this app
* util : helper class like listener and config
* view : custom view for the expandable text and gradient effect for movie poster
* activity : show the detail infomation for movie, tv show or person
