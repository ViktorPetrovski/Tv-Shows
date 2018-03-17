package com.viktorpetrovski.moviesgo.ui.tvShowDetails;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.viktorpetrovski.moviesgo.data.model.TvShow;
import com.viktorpetrovski.moviesgo.repository.TvShowsRepository;
import com.viktorpetrovski.moviesgo.ui.base.MainActivityNavigationController;
import com.viktorpetrovski.moviesgo.util.TvShowUtil;
import com.viktorpetrovski.moviesgo.util.rx.TvShowsSchedulerProviderTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;

import io.reactivex.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Victor on 3/17/18.
 */

@RunWith(MockitoJUnitRunner.class)
public class TvShowDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    TvShowsRepository tvShowsRepository;

    @Mock
    MainActivityNavigationController mainActivityNavigationController;

    TvShowDetailsViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TvShowDetailsViewModel(tvShowsRepository, new TvShowsSchedulerProviderTest(),mainActivityNavigationController);
    }

    @Test
    public void generateTestForLoadingTvShowDetails() throws Exception{
        TvShow tvShow = TvShowUtil.getTvShowDetails(getClass().getClassLoader());

        when(tvShowsRepository.loadTvShowDetails(anyLong())).thenReturn(Observable.just(tvShow));
        viewModel.getTvShowDetails();

        TvShow viewModelTvShow = viewModel.getTvShow().getValue();

        //Check If Tv Show is properly Loaded
        assertEquals(viewModelTvShow,tvShow);
    }

    @Test
    public void generateTestForHandlingSimilarTvShowsClick(){
        viewModel.handleOnSimilarTvShowListItemClick(mock(TvShow.class));
        verify(mainActivityNavigationController).navigateToTvShowDetails(anyLong());

    }

    @Test
    public void generateTestForGenresString() throws UnsupportedEncodingException {
        TvShow tvShow = TvShowUtil.getTvShowDetails(getClass().getClassLoader());
        assertEquals(viewModel.createGenresString(tvShow), "Action & Adventure  |  Drama  |  Sci-Fi & Fantasy" );
    }


}
