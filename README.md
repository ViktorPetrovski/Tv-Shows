# Tv Shows   [![CircleCI](https://circleci.com/gh/victorpetrovski/Go-Movies/tree/dev.svg?style=svg)](https://circleci.com/gh/victorpetrovski/Go-Movies/tree/dev)
Android App for listing the most Popular Tv Shows using The Moive DB API (https://www.themoviedb.org/documentation/api)  

This simple app is divided in two sections:
* <b>Tv Shows List</b> 
  This is the first screen when the app is launched. On this screen we have a list of Tv Shows displayed with big poster image along with the Tv Show title and average rating.
* <b>Tv Show Details</b> After selecting item from the Tv Shows List we open a new screen where we display more information about the selected Tv Show along with a Horizontal list of Similar Tv Shows.



Tv Shows List           |  Tv Show Details
:-------------------------:|:-------------------------:
![](https://github.com/victorpetrovski/Go-Movies/blob/master/TvShows_.png?raw=true "Tv Shows List")  | ![](https://github.com/victorpetrovski/Go-Movies/blob/master/midhunter.png?raw=true "Tv Show Details")



## Library reference resources:
1. RxJava2: https://github.com/ReactiveX/RxJava
2. Dagger2: https://google.github.io/dagger/
3. Retrofit: http://square.github.io/retrofit/
4. Glide: https://github.com/bumptech/glide
5. Architecture Components: https://developer.android.com/topic/libraries/architecture/index.html
6. Mockito & JUnit: http://site.mockito.org/
7. Calligraphy: https://github.com/chrisjenx/Calligraphy

<b>Kotlin</b> will be our go to language
## Architecture
We are going to use the MVVM pattern with the help of Google Architecture Components.

### Model
  We are going to use Repositories which will be held responsible for providing data to the ViewModel.
### View
  The View will observe the changes in the ViewModel and display the changes accordingly it also informs the ViewModel about the user’s actions.
### ViewModel
  The ViewModel retrieves the necessary data from the Model (Repository), applies the UI logic and then exposes relevant data for the View to consume. The ViewModel exposes the data via Observables (LiveData).

<p align="center">
  <img src="https://github.com/victorpetrovski/Go-Movies/blob/master/Architecture.png?raw=true">
</p>

#### The app has following packages:
1. **data**: It contains all the data accessing and manipulating components.
2. **di**: Dependency providing classes using Dagger2.
3. **ui**: View classes along with their corresponding ViewModel.
4. **utils**: Utility classes.


