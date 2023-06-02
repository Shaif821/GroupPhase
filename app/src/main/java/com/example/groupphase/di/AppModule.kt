package com.example.groupphase.di

import android.app.Application
import androidx.room.Room
import com.example.groupphase.data.data_source.dao.SimulationDAO
import com.example.groupphase.data.data_source.database.AppRoomDatabase
import com.example.groupphase.data.repository.SimulationRepositoryImpl
import com.example.groupphase.domain.repository.SimulationRepository
import com.example.groupphase.domain.use_case.simulation_use_cases.DeleteSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetAllSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByDateUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByIdUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByWeekUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.InsertSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.SimulationUseCases
import com.example.groupphase.domain.use_case.simulation_use_cases.UpdateSimulationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) : AppRoomDatabase {
        return Room.databaseBuilder(
            app,
            AppRoomDatabase::class.java,
            AppRoomDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build(
        )
    }

    @Provides
    @Singleton
    fun provideSimulationDao(database: AppRoomDatabase) : SimulationDAO {
        return database.simulationDao(
        )
    }

    @Provides
    @Singleton
    fun provideSimulationRepository(simulationDAO: SimulationDAO) : SimulationRepository {
        return SimulationRepositoryImpl(
            simulationDAO
        )
    }

    @Provides
    @Singleton
    fun provideGetAllSimulationsUseCase(simulationRepository: SimulationRepository) : SimulationUseCases {
        return SimulationUseCases(
            deleteSimulationUseCase = DeleteSimulationUseCase(simulationRepository),
            insertSimulationUseCase = InsertSimulationUseCase(simulationRepository),
            updateSimulationUseCase = UpdateSimulationUseCase(simulationRepository),
            getAllSimulationUseCase = GetAllSimulationUseCase(simulationRepository),
            getSimulationByDateUseCase = GetSimulationByDateUseCase(simulationRepository),
            getSimulationByWeekUseCase = GetSimulationByWeekUseCase(simulationRepository),
            getSimulationById = GetSimulationByIdUseCase(simulationRepository),
        )
    }
}