package com.puzzletion.hpuzzlegame.cool8puzzle.di

import com.puzzletion.hpuzzlegame.cool8puzzle.ui.viewmodels.HomeViewModel
import com.puzzletion.hpuzzlegame.cool8puzzle.ui.viewmodels.InfoViewModel
import com.puzzletion.hpuzzlegame.cool8puzzle.ui.viewmodels.SolverStepsViewModel
import com.example.cool8puzzle.ui.viewmodels.SolverViewModel
import com.puzzletion.hpuzzlegame.cool8puzzle.solver.PuzzleSolver
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeFragmentModule = module {
    viewModel { HomeViewModel() }
}

val infoViewModelModule = module {
    viewModel { InfoViewModel() }
}

val solverViewModelModule = module {
    viewModel { SolverViewModel() }
}

val solverStepsViewModelModule = module {
    viewModel { (puzzleData: String) -> SolverStepsViewModel(puzzleData, get()) }
}

val puzzleSolverModule = module {
    single { PuzzleSolver() }
}