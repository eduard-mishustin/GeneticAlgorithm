package magym.geneticalgorithm

import android.content.Context
import androidx.core.app.NotificationCompat
import magym.geneticalgorithm.game.presentation.*
import magym.geneticalgorithm.game.view.service.NotificationManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val CHANNEL_ID = "CHANNEL_ID"

val gameModule = module {
    single { NotificationManager(androidContext(), get(), get()) }
    single { NotificationCompat.Builder(androidContext(), CHANNEL_ID) }
    single { androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager }
    
    single<GamePresenter> { GamePresenterImpl(get(), get(), get()) }
    
    single<GameObjectsHolder> { GameObjectsHolderImpl() }
    single<GameObjectsFiller> { GameObjectsFillerImpl(get()) }
    single<BotStepMaker> { BotStepMakerImpl(get(), get()) }
}