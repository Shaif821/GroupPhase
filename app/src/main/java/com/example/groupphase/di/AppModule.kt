package com.example.groupphase.di

import android.app.Application
import androidx.room.Room
import com.example.groupphase.data.data_source.dao.PlayerDAO
import com.example.groupphase.data.data_source.dao.SimulationDAO
import com.example.groupphase.data.data_source.dao.TeamDAO
import com.example.groupphase.data.data_source.database.AppRoomDatabase
import com.example.groupphase.data.repository.PlayerRepositoryImpl
import com.example.groupphase.data.repository.SimulationRepositoryImpl
import com.example.groupphase.data.repository.TeamRepositoryImpl
import com.example.groupphase.domain.repository.PlayerRepository
import com.example.groupphase.domain.repository.SimulationRepository
import com.example.groupphase.domain.repository.TeamRepository
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
        return database.simulationDao()
    }

    @Provides
    @Singleton
    fun providePlayerDao(database: AppRoomDatabase) : PlayerDAO {
        return database.playerDao()
    }

    @Provides
    @Singleton
    fun provideTeamDao(database: AppRoomDatabase) : TeamDAO {
        return database.teamDao()
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
    fun providePlayerRepository(playerDAO: PlayerDAO) : PlayerRepository {
        return PlayerRepositoryImpl(playerDAO)
    }

    @Provides
    @Singleton
    fun provideTeamRepository(teamDAO: TeamDAO) : TeamRepository {
        return TeamRepositoryImpl(teamDAO)
    }
 }