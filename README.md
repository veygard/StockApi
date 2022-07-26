# StockApi
![ic_stocks](https://user-images.githubusercontent.com/79571688/180970508-65e3625e-cc8c-4f7b-8923-05ad5a612f3d.png)

Kotlin, hilt, retrofit+okhttp, regex, mvvm, jetpack navigation.


Task requirements: 

1. From rest-api we got raw text data. Like this:

![rawdata](https://user-images.githubusercontent.com/79571688/180969092-48184df0-b655-444f-a262-9fdf980139fe.png)

2. We need to get arguments of the product from text:

![item_param](https://user-images.githubusercontent.com/79571688/180966792-218caa37-f4d5-4c6b-be60-4525fe7d92df.png)

3. Also we need get attributes of the protuct by ID: 

![attr](https://user-images.githubusercontent.com/79571688/180967288-903de9c1-40c0-4a78-82be-119e0263adc4.png)

4. Using this data, needed to make two screens with a list and details. And handle no internet connection also.

-------------------

In this task I preferred to use regex to separate the text values that we needed to create objects:

![regex](https://user-images.githubusercontent.com/79571688/180971308-87a647b2-3692-4601-8e14-8992d0b5b3bf.png)


As result:

![stock_gif](https://user-images.githubusercontent.com/79571688/180969867-b5952263-467a-43c6-a5a8-2335abb849d9.gif)
