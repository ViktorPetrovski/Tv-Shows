package com.viktorpetrovski.moviesgo.ui.tvShowsList;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.viktorpetrovski.moviesgo.data.model.TvShow;
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse;
import com.viktorpetrovski.moviesgo.repository.TvShowsRepository;
import com.viktorpetrovski.moviesgo.ui.base.MainActivityNavigationController;
import com.viktorpetrovski.moviesgo.util.NetworkLoadingStatus;
import com.viktorpetrovski.moviesgo.util.TvShowUtil;
import com.viktorpetrovski.moviesgo.util.rx.TvShowsSchedulerProviderTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.util.List;

import io.reactivex.Observable;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Victor on 3/16/18.
 */

@RunWith(MockitoJUnitRunner.class)
public class TvShowsListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MainActivityNavigationController mainActivityNavigationController;

    @Mock
    TvShowsRepository tvShowsRepository;

    @Mock
    Observer<NetworkLoadingStatus> networkStatusObserver;

    TvShowsListViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TvShowsListViewModel(tvShowsRepository, new TvShowsSchedulerProviderTest(), mainActivityNavigationController);
        //Observe Loading Changes
        viewModel.getLoadingObservable().observeForever(networkStatusObserver);

    }

    @Test
    public void testGetContributors() throws Exception {
        TvShowListResponse tvShowListResponse = TvShowUtil.getTvShowsList(getClass().getClassLoader());
        assertTrue(tvShowListResponse.getPage() == 1);
    }

    @Captor
    private ArgumentCaptor<List<TvShow>> tvShowCaptor;

    @Test
    public void testRepositoryCall() throws UnsupportedEncodingException {
        int page = 4;
        viewModel.setPage(page);

        TvShowListResponse tvShowListResponse = TvShowUtil.getTvShowsList(getClass().getClassLoader());

        when(tvShowsRepository.loadPopularTvShows(page)).thenReturn(Observable.just(tvShowListResponse));

        viewModel.loadPopularTvShows();

        verify(tvShowsRepository).loadPopularTvShows(page);
    }

    @Test
    public void testPagination() throws UnsupportedEncodingException {
        TvShowListResponse tvShowListResponse = TvShowUtil.getTvShowsList(getClass().getClassLoader());

        int startingPage = 1;
        when(tvShowsRepository.loadPopularTvShows(anyInt())).thenReturn(Observable.just(tvShowListResponse));


        //Testing Loading Success and Pagination increment
        assertTrue(tvShowListResponse.getPage() == startingPage);
        viewModel.loadPopularTvShows();

        verify(networkStatusObserver).onChanged(NetworkLoadingStatus.LOADING);
        verify(networkStatusObserver).onChanged(NetworkLoadingStatus.SUCCESS);
        assertEquals(viewModel.getPage(), ++startingPage);
        //Verify the items are added in the List
        assertThat(viewModel.getTvShowsList(), contains(tvShowListResponse.getShowsList().toArray()));

    }

    @Test
    public void testPaginationEnd() throws UnsupportedEncodingException {
        TvShowListResponse tvShowListResponse = TvShowUtil.getTvShowsList(getClass().getClassLoader());

        when(tvShowsRepository.loadPopularTvShows(anyInt())).thenReturn(Observable.just(tvShowListResponse));
        //Observe Loading Changes

        //Testing Pagination End
        int lastpage = 1002;
        viewModel.setPage(lastpage);
        viewModel.loadPopularTvShows();
        verify(networkStatusObserver).onChanged(NetworkLoadingStatus.LOADING);
        verify(networkStatusObserver).onChanged(NetworkLoadingStatus.SUCCESS);
        verify(networkStatusObserver).onChanged(NetworkLoadingStatus.ALL_PAGES_LOADED);
        assertEquals(viewModel.getPage(), ++lastpage);

        //Verify the items are added in the List
        assertThat(viewModel.getTvShowsList(), contains(tvShowListResponse.getShowsList().toArray()));

    }


    @Test
    public void testLoadingJsonError() throws UnsupportedEncodingException {
        viewModel.handleError(new Throwable());
        verify(networkStatusObserver).onChanged(NetworkLoadingStatus.ERROR);
    }


    @Test
    public void testTvShowDetailsNavigation() throws Exception {
        TvShow tvShow = TvShowUtil.getTvShowDetails(getClass().getClassLoader());
        viewModel.handleOnTvShowListItemClick(tvShow);
        verify(mainActivityNavigationController).navigateToTvShowDetails(tvShow.getId());
    }

}


