# BuildNews
A white label challenge, empowering you to create custom news apps effortlessly.

# Android News App Challenge

This repository contains the code for an Android application that displays top news headlines for a specific source. This project was undertaken as part of the Android Developer position challenge for Critical TechWorks.

## Data Sources

The application leverages two prominent news APIs to fetch the top headlines:

- **[NewsAPI](https://newsapi.org/docs/endpoints/top-headlines):** The primary data source for fetching top headlines from BBC News.

- **[NYTimesAPI](https://developer.nytimes.com/get-started):** An additional data source used for another flavor of the application that presents news from The New York Times.

## Features

- **Top Headlines:** The app fetches and displays the top headlines from the specific news source using the NewsAPI `newsapi flavor` and NYTimesAPI `nytimesapi flavor`.

- **Article Details:** Tapping on a headline presents a detailed view of the article, displaying the image, title, description, and content (when available). Additionally, users have the option to read the article in more detail by following a provided link to the source of the news.

- **Fingerprint Authentication:** The app prompts the user for fingerprint authentication upon opening, if the feature is available and configured on the device.

- **Support for Multiple News Sources:** New flavors are created to present news from different sources. These flavors are implemented as modules outside of the app module, which provides better organization and maintainability. In the event a new flavor with a new data source is created, it can be easily added to the centralizing module for flavors. This design allows for the easy extension and management of multiple news sources within the application.

## Additional Modules

- **Arch Module:** Contains utility classes such as extensions, testing rules, and other helpers for improving the development experience.

- **Design Module:** Includes a design system encapsulating colors, dimensions (dp, sp), drawables, etc., ensuring a consistent visual design across the application.

## Architecture and Libraries

The application is built using the following technologies:

- **Architecture:** MVVM with Clean Architecture principles.
- **Languages:** Kotlin with Coroutines for async operations.
- **Networking:** Retrofit for network calls.
- **Parsing:** Gson for parsing JSON data.
- **Image Loading:** Coil for loading and caching images.
- **Dependency Injection:** Koin.
- **Unit Testing:** JUnit, Mockk.
- **UI Testing:** Espresso <del>(Not Yet Implemented)</del>
- **Local Database:** Room <del>(Not Yet Implemented)</del>
- **Paging:** Paging 3 <del>(Not Yet Implemented)</del>

## How to Build

To build this project, you will need to generate your API key from NewsAPI and NYTimesAPI and include them in your project:

1. Generate your API keys from [NewsAPI](https://newsapi.org/) and [NYTimesAPI](https://developer.nytimes.com/get-started).
2. Create a file named `api_keys.properties` in the project root directory.
3. Add the following lines to the file:
    ```
    NEWS_API_KEY="Your-Api-Key-Here"
    NY_TIMES_API_KEY="Your-Api-Key-Here"

    ```
4. Replace `Your-NewsAPI-Key-Here` and `Your-NYTimesAPI-Key-Here` with your actual API keys.

Then you can clone this repository and import into Android Studio to build the application.

## Screens
<img src="images/newsapi.png" width="100%"/>
<img src="images/nytimesapi.png" width="100%"/>

This project demonstrates a solid understanding of modern Android development practices, including unit testing, integration testing, and coding to a high standard.
