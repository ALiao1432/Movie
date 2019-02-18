# Movie
A android version of The Movie DB,
using libraries 
* [RxJava2](https://github.com/ReactiveX/RxJava) : for building the asynchronized request to The Movie DB
* [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson) : building service based on the REST API from THe Movie DB
* [Glide](https://github.com/bumptech/glide) : for loading image
* [NetworkState](https://github.com/ALiao1432/NetworkState) : the library I built for monitoring if the network is connecting via Wifi, Mobile or not connecting. It provide the ability for app to reload the data when reconnection to valid network
* [ColorHintBar](https://github.com/SeamasShih/ColorHintBarLibrary) : the library built from my friend to work with ViewPager to indicate the current page of ViewPager

# Demo gif link
[demo1](https://lh3.googleusercontent.com/Rkand_1Uy8qkSHDNjmGm2U7pedNiUPaPf5aQ4Nz_r5RvYdv48isl-kEw6HpeDXUFZX3u_2R3hlZnLm-h-MLT01QI4mCbTVTc6NZv19dJCxo4Vxq5KjY9ZwE1dv0nEedRzIu-tnZPV58eIFD3vhvKkIb6Y2vrRNEk8atSG8Gkm6C7CqKc1kPlXY7WOyzLhfGzIHgyBXWIVMEhAmTYFS-uH_iC0cwW4Gj90E54CTRdJLsM1WAy-Xp8ZFI69TWGjW6qx7hpdVQvWenJxkaemtcncWM6HKAFn7JOAEIAgZGETmiJ3BP8ng87qM69xT78vV_JtMyV-E7-qcAePiMpJK_VmkKW4FA5npAwB17SN3KbXoYS5dMMMJvVuqgvV-BI5RJs7hcIVaqSxSvCr-a-kgLGD4oiSF5oJHMTumPKPtQjoF_W8TbIL3Eeroorlg8BRKpdxIK8B2oGezGErECNQbwShDgzYQqbMAmb40GnpozMBSFt1LenSjrdn7sfuYpF3i_ksaJs40QubGfv8VwjSBoyZtWgnEZ5NeHRUNtaKXC1-E16GF993xvTG3N8rcwCxjBudcQPep3Qie8aOhthCets0pRq9Z3PqkPy4rWPePyBmDtriNOfgqMnqduH65Cb7W4zHUdgU1cDgJO8pxZjb2Ihoc0hFuNgRQ5H=w489-h867-no)  
[demo2](https://lh3.googleusercontent.com/e5VB5cAulc60Rb6Z10suGPmLyUUNLHsxlBHaG7z1hkHgKhR1LRNtqomz6aBbKXCDfJM5hFsUGEP3d2H5tcoyIWVlTWaigtZeahWfIZhxpXwLhgDHWI-88fVIa3zpX2zYr9Ob5ys5O46JTJ2qCJzCDYrpHw7lyuoNxvr5QvL58VdZvxqh5cJR0MnFjeq68juYJpXaaFs_xK_029sZ-1LpKucQ7gDzuy5iVIkvoGpUaor8pEN2vI2lRK6yzyWyx93LQpJ2zKVM6ocOZYGluMgt-2_PSKNMO9jofGJzFZ0BkRTs3jDeHG_hIXjaN7Dt-IcBbp_bKUYrmW9ku1B2GktU7XO7rtSu8k_d8OUhkuEn5klCY7ADrzV-OBYGbNjJeItc0bByEJt5IB5w4ATise3yYeSVzRy74AQsn8SXHfURW485VW2FtuM3JNNm6_XNS6TJKCg0crPy0PZE53qqCEpJsIU4dH-Y475xan3PpDiRX27Zr6zvC2ylBZA3_EnGrij6nA53vJ7hj6Qx-9E3xK0u7jGVoy_LI-pJeRNKnXJHRaWr4iMYzyINtv12SEKGcEz7gxceTR43vgsffHmHyqM-t6HM3yAYL28ZcUNxlBdJ7-X8T2eII6p9ofSMlka4F87RWNt0UvdRjqLEyUwtgdWu37P9zR6xz_7P=w489-h867-no)  
[demo3](https://lh3.googleusercontent.com/xDEHltt2AtwE63ECLkUn7N8Xf6JTiJF6Pd3zzoZln1NuaepNXSGvvucBB8-nJZwXSFW_-yfHPh-O_CBlXmqKfokuserBKIIDGT_TBpQPvaoUGhFWJdex-hTSL6hFczr3aZpxKTp-5xUiBbjb2_JG-c0GsrnMhGYcvaTos7bCme1QasyXCDfwaFxaNYOgTUtpX6bSuEXZFzs4aKpkEAaoa6inEyxy9XK2WdQO1_GQs2WdZa4xJ9BFUqRht3CSRw6V1sPZ0eeIYzTkw0QZVmFSDBB8faF5dtHPGOaaKbWsJyZ4bJH1xabnwxIpnFxhT-481fD9zdNdKv1ye4DPggkvrGAKR0_xFaftqS7PTA7VmOhpkQhCgDmGQcOOngyH_sKdu6T1UNPkigngJ_5g10YIyGyq8iwapSgx3laKiDRAISxlqwmlsuNYwt13WYOFi9LpFxyHgNKdrefMs3OawHwAYl_kP_uyW21c4LCZbm9jZazPdBq2bxyVXR-VHgxwhK2QTnhUeVq5yK6VdtY5c2neqti0M6vIwdZ0kDT2y2reVcTlsE9wtd3jirA6xlIroiMDIXRXiP5Acx4_Cndi-mYxYt1Xm7nL9BjXwCcnS9y2OUlEtWDWyl6d-ty5cFO2mObqrE9lKtOG1yeRBqM18EwqRr53QnV7lKgR=w489-h867-no)  
[demo4](https://lh3.googleusercontent.com/OqjvenPgsx7ynchntbLBdkfvas4AajlmMDNPvuVH8JVnmGWHYfyG7cU4Hpc2IUHB7NolQ_DjO_DWf1QB7nyF6vJHHOc-b457fb-JBmmj93nJYAMu7Wrk8AHbUVhMb-XCYfyOGG5VeYnknCJ9fneQ5j0d1qJarRspPDk7Emi2gEuGcpeHxN3SWg3HRJxnGyv3VEhHPCR5qTUQLVOnYspBg_Bwzk73BtnHIpajvhMy8lLWABJPZRzkAE4CLe7qcXgKCfByY0rV3PO3FDLQ2unoy9tzJuLwnDZpqa0_jhXVDKUT8qZXZormh0oo0x6xOLBPDjd5BoDf44JggEyVb_K9RpKnP1bPpl1tgoeQApvmNFvDfnCTgglbUOY0Rshz92vy8uP-sCxV1odEIcPnlSxJ_bnlaMHQrIWCKidPbQwEo5yzCKjVe-NmeCXvdPKuT0ekcG0U1X0dONNYoKC7I4Y8a7bqgvGCKK9zVade4CRmPAo1i6OXcA3y5_iiHu7QjaeazWlq7CPZi7kQ1UsOSBUFxB3BHY8AFpAw022QZt9LG3iBOeHN06WhiOCC3AGIwX2gjjsDPdV9GX-yX8QqVcbOq1y2oQl5NMum7o4jvJIE9izt43qhI7eaQWdos3HWOxzDof0M1rVZiWHtZWwUSjFvaoUnN9EnV-fj=w489-h867-no)  
[demo5](https://lh3.googleusercontent.com/-rSwjwNW0LxsQyGtWcr4Bl5fafCBw1Qor1dtJt_HObOHXPfvE5GjW7H2BZuuGg6hxtki7GjDew1jR-3WoalRSn__Z6kKs61iEGuDGOW5MZCUMzw1_asAkemab29n34FGFJiow6y9DJaTGFm8-r_BpljMZ8rKAzw9FzAUUDQuayqUNHygV4Xpow6qvVQLcsOmfZUdp7-GwjE1dRwfoZ1hpPKlnBRRb2uDrTiBJUNKB28yueaAVKWMORkR-kSl7B7FjJ2b3483BhgOJTGG6sPdYb3RgcXquL812AazISGghEydUEtJ6DS1O9JI7Dvq0D4OOSvgFAd2fT5xwmKYCEUM6rVrB7VccAJiR3kzUAB_PBK9Kc1hovI28-EPoqBUb-JkVTxCd6AHGjM7rbeVyE7ZezR9iVjh0Guq_2AheBS9pUtPRcNgQRCk_h-UR9w5CucyzjI0rYQ3iPd5oQFze0nKhT8PcFJRjDcqAF_7_OiTRYeAU1QPhq9qLKfatc4sX7iNqtvrdCe4SLnz5TT07HV2b6JPYlsW6EsfnW7hrI2ArJkH9oBj75rPTmoj3mGSqbZUkYEilZpPUe2O2U55cTOizzlAQCopzcg3XPvkfNoEjB2_44kwfQrebxaAm_pKOvz1yZGMQu2enOMJU0VcMpmusNUr3q4jpjMx=w489-h867-no)  
[demo6](https://lh3.googleusercontent.com/3ykyWoKWWy3Lo7f3s2P7_E0-gSMqgq7c3N7YYtZt3IDwfMsk2uWdoD5rSaXtRglQ4abNG8lzf5R9hOypm521xuTiHZTr-eDkflmhTvLzO6t-MIHrQOuPf1Y9CGdXNknDD4sqZgmjfAP3q4XekviRNTdwuFC0P_QB1OJaY1_7FdbC-yoL5o0-IV4IEMfN5yw-igzKtjXmbiXMMCGfn8I0tyAsqcJUfTjudpqibFZGscWv3Vuw-K7U7Js-1GF-eNNecEfSfVBUXuMbVcp_j-56QMjooJaWQFjl1S7LRxRFMcaTnmIcQxOvIBJ2TCms2QyEIBGuKXK9UJSAWFoogjhN6Li5tA-nk--n-QWdSw2Z-yN7Hl3i-4j7f4k0qzTf2NnDUbjI8jD3Ad8o6OQ421iftb2i_IP5wPuiCbJlAhoaJ_9uAVI7rftQdvkokA-gvIhKyTxQZp1svQE-WNAv-Qem03ieeMOBMIKMFZYjglnEeWJ8ympnJLoDr4-djYVfasRTflX0fIwprHHC8OMtTU30i2UuoMkWXHJnncRilUxhOF0OvGVuAv_oWYVDRFfD-36tkJ--nVAbT_ZiQapWPnw7yX_uLcPuHHHw7whJvlmlDdQEH6hfNqEuCl1ghzgNwb_N2Qz7R1lOQg5R0kX5IkoQMFPn2KHUgBse=w489-h867-no)  

# Architecture
![architecture](https://lh3.googleusercontent.com/lLVRT-IlyOy_webrb9afQlVFUqnusGxf_QdaBr8dwwRIOS95uuIQaO5QBJ431JKVyoRSDRGSfR5uP04vXNYDV7ldfw3YYK6LifUNTA0PSRwM4Xv3rFlgaotYycLwm1Ztj_JeBlhBEpQv_whX_bjbe5GnV83Rl9P72GmXIEzSyxSMOTU3sP-E4IP92xeD_7PGSHcPrAKsa96Xl82qxxboWuDWcYu385W07svdWrtnAxGwGpmnfYtLlhqRmCjM1wZH_zPGMrfYwjVK8BHSbEKNzkMNQCGBIIp-0Ha160BzMMpqNMK7dnBF9Ihaxz5zsOmsRinu7EIwy19543n3MYnI9VqboIptdeE1xr1UNzNxqRENP27K2esRZ306EJSBfNrDwEFdvx8BKBqzA0weW5ajZtg-AYfZenCNKHC7pj9hUTPmXFBo3sI0UtZbkgNB5VIKiAL-bplAEOGl2QaXEni_VBI3qUGzeLtpLZTVo0TibD8YlR4RIhmY87pHSC32-u9vczBmBv-b8q9Hw2fEIV2y0YgNWpX7nz0C9CBPS7QQhzxMMaJQAmuDx10xPdB7MDDZYBpXaN96eO9o_tnhlOs-5HytWWl6sSB6XP_0ouh89owMaBj0dgKcQvnLE9me6f8FnKK8z7ChVDDdOCoLtWJRwvtfLQ3ci0LW=w1099-h267-no)
* adapter : for configuring data returned by asynchronized request
* fragment : the main page for Discover, Movies, Tv Shows and people
* model : Plain Old Java Object(POJO) for JSON data
* service : define available service for this app
* util : helper class like listener and config
* view : custom view for the expandable text and gradient effect for movie poster
* activity : show the detail infomation for movie, tv show or person
