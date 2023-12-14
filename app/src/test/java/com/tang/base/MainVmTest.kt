package com.tang.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.logging.Logger

/**
 * @Author tangtao
 * @Description:
 * @Date:2023/12/12 17:34
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MainVmTest {

    private lateinit var mainVm: MainVm
    private val testWatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        mainVm = MainVm()
        Dispatchers.setMain(testWatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testWatcher.cancelChildren()
    }

    @Test
    fun mockRequest() = runTest {

        mainVm.mockRequest(false)
//        val states = mutableListOf<BaseViewModel.UILoadingToastState>()
//        val job1 = backgroundScope.launch(testWatcher) {
//            mainVm.loadingFlow.toList(states)
//        }
//        delay(3000)
//        println("states = $states")
//        job1.cancelAndJoin()


        val job = backgroundScope.launch(testWatcher) {
            mainVm.loadingFlow.collect {
                println("state = $it")
            }
        }

        delay(3000)
        job.cancelAndJoin()


    }
}