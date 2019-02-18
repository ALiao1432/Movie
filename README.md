# Movie
A android version of The Movie DB,
use library 
* [RxJava2](https://github.com/ReactiveX/RxJava) : for building the asynchronized request to The Movie DB
* [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson) : building service based on the REST API from THe Movie DB
* [Glide](https://github.com/bumptech/glide) : for loading image
* [NetworkState](https://github.com/ALiao1432/NetworkState) : the library I built for monitoring if the network is connecting via Wifi, Mobile or not connecting
* [ColorHintBar](https://github.com/SeamasShih/ColorHintBarLibrary) : the library built from my friend to work with ViewPager to indicate the current page of ViewPager

# Demo
![](https://lh3.googleusercontent.com/Rkand_1Uy8qkSHDNjmGm2U7pedNiUPaPf5aQ4Nz_r5RvYdv48isl-kEw6HpeDXUFZX3u_2R3hlZnLm-h-MLT01QI4mCbTVTc6NZv19dJCxo4Vxq5KjY9ZwE1dv0nEedRzIu-tnZPV58eIFD3vhvKkIb6Y2vrRNEk8atSG8Gkm6C7CqKc1kPlXY7WOyzLhfGzIHgyBXWIVMEhAmTYFS-uH_iC0cwW4Gj90E54CTRdJLsM1WAy-Xp8ZFI69TWGjW6qx7hpdVQvWenJxkaemtcncWM6HKAFn7JOAEIAgZGETmiJ3BP8ng87qM69xT78vV_JtMyV-E7-qcAePiMpJK_VmkKW4FA5npAwB17SN3KbXoYS5dMMMJvVuqgvV-BI5RJs7hcIVaqSxSvCr-a-kgLGD4oiSF5oJHMTumPKPtQjoF_W8TbIL3Eeroorlg8BRKpdxIK8B2oGezGErECNQbwShDgzYQqbMAmb40GnpozMBSFt1LenSjrdn7sfuYpF3i_ksaJs40QubGfv8VwjSBoyZtWgnEZ5NeHRUNtaKXC1-E16GF993xvTG3N8rcwCxjBudcQPep3Qie8aOhthCets0pRq9Z3PqkPy4rWPePyBmDtriNOfgqMnqduH65Cb7W4zHUdgU1cDgJO8pxZjb2Ihoc0hFuNgRQ5H=w489-h867-no)

# Architecture
![architecture](https://lh3.googleusercontent.com/lLVRT-IlyOy_webrb9afQlVFUqnusGxf_QdaBr8dwwRIOS95uuIQaO5QBJ431JKVyoRSDRGSfR5uP04vXNYDV7ldfw3YYK6LifUNTA0PSRwM4Xv3rFlgaotYycLwm1Ztj_JeBlhBEpQv_whX_bjbe5GnV83Rl9P72GmXIEzSyxSMOTU3sP-E4IP92xeD_7PGSHcPrAKsa96Xl82qxxboWuDWcYu385W07svdWrtnAxGwGpmnfYtLlhqRmCjM1wZH_zPGMrfYwjVK8BHSbEKNzkMNQCGBIIp-0Ha160BzMMpqNMK7dnBF9Ihaxz5zsOmsRinu7EIwy19543n3MYnI9VqboIptdeE1xr1UNzNxqRENP27K2esRZ306EJSBfNrDwEFdvx8BKBqzA0weW5ajZtg-AYfZenCNKHC7pj9hUTPmXFBo3sI0UtZbkgNB5VIKiAL-bplAEOGl2QaXEni_VBI3qUGzeLtpLZTVo0TibD8YlR4RIhmY87pHSC32-u9vczBmBv-b8q9Hw2fEIV2y0YgNWpX7nz0C9CBPS7QQhzxMMaJQAmuDx10xPdB7MDDZYBpXaN96eO9o_tnhlOs-5HytWWl6sSB6XP_0ouh89owMaBj0dgKcQvnLE9me6f8FnKK8z7ChVDDdOCoLtWJRwvtfLQ3ci0LW=w1099-h267-no)
* adapter : for configuring data returned by asynchronized request
* fragment : the main page for Discover, Movies, Tv Shows and people
* model : Plain Old Java Object(POJO) for JSON data
* service : define available service for this app
* util : helper class like listener and config
* view : custom view for the expandable text and gradient effect for movie poster
* activity : show the detail infomation for movie, tv show or person
