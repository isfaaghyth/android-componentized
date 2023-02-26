package app.isfa.component.di

import app.isfa.component.bus.EventBus
import app.isfa.component.bus.EventBusImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FeatureModule {

    @Provides
    fun providesEventBus(): EventBus {
        return EventBusImpl()
    }
}
