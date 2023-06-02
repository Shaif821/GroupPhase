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
import com.example.groupphase.domain.use_case.PlayerUseCases
import com.example.groupphase.domain.use_case.simulation_use_cases.DeleteSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetAllSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByDateUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByIdUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByWeekUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.InsertSimulationUseCase
import com.example.groupphase.domain.use_case.SimulationUseCases
import com.example.groupphase.domain.use_case.TeamUseCases
import com.example.groupphase.domain.use_case.player_use_cases.DeletePlayerUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetAllPlayersUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetPlayerUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetPlayersByStrengthAndTeamUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetPlayersByStrengthUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetPlayersByTeamUseCase
import com.example.groupphase.domain.use_case.player_use_cases.InsertPlayerUseCase
import com.example.groupphase.domain.use_case.player_use_cases.UpdatePlayerUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.DetermineMatchesOrderUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.UpdateSimulationUseCase
import com.example.groupphase.domain.use_case.team_use_cases.DeleteTeamUseCase
import com.example.groupphase.domain.use_case.team_use_cases.GetAllTeamsUseCase
import com.example.groupphase.domain.use_case.team_use_cases.GetTeamByIdUseCase
import com.example.groupphase.domain.use_case.team_use_cases.InsertTeamUseCase
import com.example.groupphase.domain.use_case.team_use_cases.UpdateTeamUseCase
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

    @Provides
    @Singleton
    fun provideSimulationsUseCase(simulationRepository: SimulationRepository) : SimulationUseCases {
        return SimulationUseCases(
            deleteSimulationUseCase = DeleteSimulationUseCase(simulationRepository),
            insertSimulationUseCase = InsertSimulationUseCase(simulationRepository),
            updateSimulationUseCase = UpdateSimulationUseCase(simulationRepository),
            getAllSimulationUseCase = GetAllSimulationUseCase(simulationRepository),
            getSimulationByDateUseCase = GetSimulationByDateUseCase(simulationRepository),
            getSimulationByWeekUseCase = GetSimulationByWeekUseCase(simulationRepository),
            getSimulationByIdUseCase = GetSimulationByIdUseCase(simulationRepository),
            determineMatchesOrderUseCase = DetermineMatchesOrderUseCase(simulationRepository),
        )
    }

    @Provides
    @Singleton
    fun providePlayerUseCase(playerRepository: PlayerRepository) : PlayerUseCases {
        return PlayerUseCases(
            deletePlayerUseCase = DeletePlayerUseCase(playerRepository),
            insertPlayerUseCase = InsertPlayerUseCase(playerRepository),
            updatePlayerUseCase = UpdatePlayerUseCase(playerRepository),
            getAllPlayerUseCase = GetAllPlayersUseCase(playerRepository),
            getPlayersByTeamUseCase = GetPlayersByTeamUseCase(playerRepository),
            getPlayersByStrengthUseCase = GetPlayersByStrengthUseCase(playerRepository),
            getPlayer = GetPlayerUseCase(playerRepository),
            getPlayersByStrengthAndTeamUseCase = GetPlayersByStrengthAndTeamUseCase(playerRepository),
        )
    }

    @Provides
    @Singleton
    fun provideTeamUseCase(teamRepository: TeamRepository) : TeamUseCases {
        return TeamUseCases(
            deleteTeamUseCase = DeleteTeamUseCase(teamRepository),
            insertTeamUseCase = InsertTeamUseCase(teamRepository),
            updateTeamUseCase = UpdateTeamUseCase(teamRepository),
            getAllTeamsUseCase = GetAllTeamsUseCase(teamRepository),
            getTeamByIdUseCase = GetTeamByIdUseCase(teamRepository),
        )
    }
 }