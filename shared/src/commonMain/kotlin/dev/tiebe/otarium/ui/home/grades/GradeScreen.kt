package dev.tiebe.otarium.ui.home.grades

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tiebe.otarium.MR
import dev.tiebe.otarium.logic.root.home.children.grades.GradesComponent
import dev.tiebe.otarium.ui.home.grades.calculation.screen.GradeCalculationChild
import dev.tiebe.otarium.ui.home.grades.recentgrades.RecentGradesChild
import dev.tiebe.otarium.ui.utils.tabIndicatorOffset
import dev.tiebe.otarium.utils.ui.getLocalizedString
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun GradesScreen(component: GradesComponent) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(
                        pagerState,
                        2,
                        tabPositions,
                        shouldShowIndicator = true,
                        pagerState.currentPage
                    )
                )
            }
        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                onClick = { component.changeChild(GradesComponent.GradesChild.RecentGrades) },
                modifier = Modifier.height(53.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = getLocalizedString(MR.strings.gradesItem),
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp
                    )
                }
            }

            Tab(
                selected = pagerState.currentPage == 1,
                onClick = { component.changeChild(GradesComponent.GradesChild.Calculation) },
                modifier = Modifier.height(53.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = getLocalizedString(MR.strings.grade_calculation_item),
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Box(Modifier.fillMaxSize()) {
            val scope = rememberCoroutineScope()

            // Left invisible box
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight()
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(onHorizontalDrag = { it, change ->
                            if(change > 0) {
                                // check if we are not at the first page already
                                if(pagerState.currentPage != 0) {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                            }
                        })
                    }
            )

            // Same for the right side of the screen, but for next page
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight()
                    .fillMaxSize()
                    .align(Alignment.CenterEnd)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(onHorizontalDrag = { it, change ->
                            println(change)

                            if(change < 0) {
                                // check if we are not at the last page already
                                if(pagerState.currentPage != 1) {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            }
                        })
                    }
            )

            // Your existing pager
            HorizontalPager(
                pageCount = 2,
                state = pagerState,
                beyondBoundsPageCount = 1,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                when (page % 2) {
                    0 -> RecentGradesChild(component.recentGradeComponent)
                    1 -> GradeCalculationChild(component.calculationChildComponent)
                }
            }
        }



        component.currentPage.subscribe {
            coroutineScope.launch {
                pagerState.animateScrollToPage(it)
            }
        }
    }
}