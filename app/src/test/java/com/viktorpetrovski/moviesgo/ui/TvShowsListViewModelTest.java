package com.viktorpetrovski.moviesgo.ui;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.viktorpetrovski.moviesgo.data.remote.api.TvShowService;
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse;
import com.viktorpetrovski.moviesgo.repository.TvShowsRepository;
import com.viktorpetrovski.moviesgo.ui.tvShowsList.TvShowsListViewModel;
import com.viktorpetrovski.moviesgo.util.TvShowUtil;
import com.viktorpetrovski.moviesgo.util.rx.TvShowsSchedulerProviderTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
    public void setUp() throws Exception {
        tvShowsRepository = new TvShowsRepository(tvShowService);
        viewModel = new TvShowsListViewModel(tvShowsRepository, new TvShowsSchedulerProviderTest());
    }

    @Test
    public void testGetContributors() throws Exception {
        TvShowListResponse tvShowListResponse = TvShowUtil.getPopularTvShows(getClass().getClassLoader());
        assertTrue(tvShowListResponse.getPage() == 1);
    }

//    @Test
//    public void testPagination() throws UnsupportedEncodingException {
//        TvShowListResponse tvShowListResponse = TvShowUtil.getPopularTvShows(getClass().getClassLoader());
//
//        int startingPage = 1;
//
//        assertTrue(tvShowListResponse.getPage() == startingPage);
//        when(tvShowService.listPopularTvShows(startingPage)).thenReturn(Observable.just(tvShowListResponse));
//        viewModel.loadPopularTvShows();
//        assertEquals(viewModel.getLoadingObservable().getValue(), NetworkLoadingStatus.SUCCESS);
//        assertEquals(viewModel.getPage(), ++startingPage);
//    }

    @Test
    public void testLastPageLoaded() throws UnsupportedEncodingException {
        TvShowListResponse tvShowListResponse = TvShowUtil.getPopularTvShows(getClass().getClassLoader());

        int lastpage = 1002;
        //when(tvShowService.listPopularTvShows(lastpage)).thenReturn(Observable.just(tvShowListResponse));
        viewModel.setPage(lastpage);
        viewModel.handleResults(tvShowListResponse);
        //assertEquals(viewModel.getLoadingObservable().getValue(), NetworkLoadingStatus.ALL_PAGES_LOADED);
        assertEquals(viewModel.getPage(), ++lastpage);
    }

//    @Test
//    public void isChangingLoadingStatus() {
//        Observable<TvShowListResponse> result = mock(Observable.class);
//        when(tvShowService.listPopularTvShows(1)).thenReturn(Observable.just(mock(TvShowListResponse.class)));
//        viewModel.loadPopularTvShows();
//        assertEquals(viewModel.getLoadingObservable().getValue(), NetworkLoadingStatus.LOADING);
//    }
//
//    @Test
//    public void isCallingSuccessStatus(){
//        TvShowListResponse result = mock(TvShowListResponse.class);
//        viewModel.handleResults(result);
//        assertEquals(viewModel.getLoadingObservable().getValue(), NetworkLoadingStatus.SUCCESS);
//    }

}


