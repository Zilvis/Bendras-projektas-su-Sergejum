
> [!NOTE]
> Auto skelbimai
> - [x] Skelbimu rusiavimas pagal : make, model, fuelType, maxMillage
> - [x] Skeblimu rusiavimas nurodant min max vertes: yearFrom - yearTo, priceFrom - priceTo

> [!IMPORTANT]
> API UZKLAUSOS:
> - [ ] <b>GET MAPPING</b> <code>/car/all</code>
> - [ ] <b>GET MAPPING</b> <code>/car/all?priceFrom=10&priceTo=30</code>
> - [ ] <b>GET MAPPING</b> <code>/car/all?maxMillage=5000</code>
> - [ ] <b>GET MAPPING</b> <code>/car/all?yearFrom=2000-06-01&yearTo=2023-01-03</code>
> - [ ] <b>GET MAPPING</b> <code>/car/models</code> :arrow_right: Grazina modelius ir skelbimu skaiciu
> - [ ] ---
> - [ ] <b>POST MAPPING</b> <code>/car/new</code> :arrow_right: @RequestBody <code>userEntityEmail, model, year, millage, price, fuelType, description, byte[] image</code>


https://www.dailycodebuffer.com/using-spring-responsestatus/#google_vignette
https://www.dailycodebuffer.com/spring-requestmapping-new-annotations/
