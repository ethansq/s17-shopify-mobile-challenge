# Shopify Mobile Challenge (S17)

Android project for Shopify's Summer 2017 Mobile Development Intership application challenge

# Functionality

<img src="https://github.com/ethansq/s17-shopify-mobile-challenge/blob/master/screens/1_main.png?raw=true" width="300">

### ActivityMain

* onCreate
  * Send simple GET request (`Volley`) to the given URL, and parse response into a `JSONObject`
  * Iterate through the `JSONObject` (actually n array) and put each productJson into local `ArrayList`
* `CoordinatorLayout and` `CollapsingToolbarLayout` for Header/Toolbar content (static image and text)
* `EditText` for search box
* `RecyclerView` with customer `Adapter` for list of products
  * Set adapter using the productJsonList
  * For each product, get its image src, and download and apply the image to the item `ImageView` using `Picasso`, with a quick fade-in on download completion
  * When an item is clicked, open `ActivityDetails` that shows more details for that item

<hr/>

<img src="https://github.com/ethansq/s17-shopify-mobile-challenge/blob/master/screens/2_scroll.png?raw=true" width="300">

### Scrolling

As expected, can collapse the `Toolbar` and scroll through the `RecyclerView`. Search bar at the top for filter functionality.

<hr/>

<img src="https://github.com/ethansq/s17-shopify-mobile-challenge/blob/master/screens/3_search.png?raw=true" width="300">

### Searching

* Our `AdapterProducts` implements `Filterable`, which updates our filtered product list when the user types input to the search box EditText.
* Attaching `addTextChangedListener` to the EditText
  * `onTextChange`, iterate through product list for product titles that match the specified text
* Call `notifyDataSetChanged` to update the Adapter & RecyclerView

<hr/>

<img src="https://github.com/ethansq/s17-shopify-mobile-challenge/blob/master/screens/4_details.png?raw=true" width="300">

### ActivityDetails

* Simple screen that shows the product item's image and title
* Simple `RecyclerView` for scrolling through each of the productJson's String-String key-value pairs. Ignores non-String values like JSONObjects and JSONArrays
  * eg. "variants" refers to a JSONObject, so we don't display it
  * eg. "product_type" refers to a String representing the item's type, so we display it
  
  <hr/>
  
 ### Gradle Dependencies

 * [Volley](https://developer.android.com/training/volley/index.html)
 * [Picasso](https://github.com/square/picasso)
 * [CircularImageView](https://github.com/hdodenhof/CircleImageView)
