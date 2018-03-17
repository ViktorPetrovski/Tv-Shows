package com.viktorpetrovski.moviesgo.ui.tvShowsList;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.viktorpetrovski.moviesgo.data.remote.api.TvShowService;
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse;
import com.viktorpetrovski.moviesgo.repository.TvShowsRepository;
import com.viktorpetrovski.moviesgo.util.NetworkLoadingStatus;
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

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Victor on 3/16/18.
 */

@RunWith(MockitoJUnitRunner.class)
public class TvShowsListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    @Mock
    TvShowService tvShowService;

    TvShowsRepository tvShowsRepository;

    TvShowsListViewModel viewModel;

    @Before
    public void setUp() {
        tvShowsRepository = new TvShowsRepository(tvShowService);
        viewModel = new TvShowsListViewModel(tvShowsRepository, new TvShowsSchedulerProviderTest());
    }

    @Test
    public void testGetContributors() throws Exception {
        TvShowListResponse tvShowListResponse = TvShowUtil.getPopularTvShows(getClass().getClassLoader());
        assertTrue(tvShowListResponse.getPage() == 1);
    }

    @Test
    public void testPagination() throws UnsupportedEncodingException {
        TvShowListResponse tvShowListResponse = TvShowUtil.getPopularTvShows(getClass().getClassLoader());

        int startingPage = 1;
        when(tvShowService.listPopularTvShows(startingPage)).thenReturn(Observable.just(tvShowListResponse));

        //Testing Loading Success and Pagination increment
        assertTrue(tvShowListResponse.getPage() == startingPage);
        viewModel.loadPopularTvShows();
        assertEquals(viewModel.getLoadingObservable().getValue(), NetworkLoadingStatus.SUCCESS);
        assertEquals(viewModel.getPage(), ++startingPage);

        //Testing Pagination End
        int lastpage = 1002;
        when(tvShowService.listPopularTvShows(lastpage)).thenReturn(Observable.just(tvShowListResponse));
        viewModel.setPage(lastpage);
        viewModel.loadPopularTvShows();
        assertEquals(viewModel.getLoadingObservable().getValue(), NetworkLoadingStatus.ALL_PAGES_LOADED);
        assertEquals(viewModel.getPage(), ++lastpage);
    }

    @Test
    public void testLoadingJsonError() throws UnsupportedEncodingException {
        viewModel.handleError(new Throwable());
        assertEquals(viewModel.getLoadingObservable().getValue(), NetworkLoadingStatus.ERROR);
    }


}


