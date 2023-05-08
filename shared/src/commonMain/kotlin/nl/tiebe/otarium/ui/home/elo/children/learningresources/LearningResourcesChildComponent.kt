package nl.tiebe.otarium.ui.home.elo.children.learningresources

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.tiebe.magisterapi.api.learningresource.LearningResourceFlow
import dev.tiebe.magisterapi.response.learningresource.LearningResource
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import nl.tiebe.otarium.Data
import nl.tiebe.otarium.ui.home.elo.ELOChildComponent
import nl.tiebe.otarium.ui.root.componentCoroutineScope
import nl.tiebe.otarium.utils.openUrl

interface LearningResourcesChildComponent : ELOChildComponent {
    val learningResources: Value<List<LearningResource>>
    val isRefreshing: Value<Boolean>

    val scope: CoroutineScope

    fun refreshLearningResources()

    fun openLearningResource(learningResource: LearningResource) {
        scope.launch {
            val url = LearningResourceFlow.getLearningResourceUrl(
                Url(Data.selectedAccount.tenantUrl),
                Data.selectedAccount.tokens.accessToken,
                learningResource.links.first { it.rel == "content" }.href
            )

            openUrl(url ?: return@launch)
        }
    }

}

class DefaultLearningResourcesChildComponent(componentContext: ComponentContext) : LearningResourcesChildComponent, ComponentContext by componentContext {
    override val learningResources: MutableValue<List<LearningResource>> = MutableValue(emptyList())
    override val isRefreshing: MutableValue<Boolean> = MutableValue(false)

    override val scope = componentCoroutineScope()

    override fun refreshLearningResources() {
        scope.launch {
            isRefreshing.value = true
            learningResources.value = LearningResourceFlow.getLearningResources(Url(Data.selectedAccount.tenantUrl), Data.selectedAccount.tokens.accessToken, Data.selectedAccount.accountId)
            isRefreshing.value = false
        }
    }

    init {
        refreshLearningResources()
    }

}