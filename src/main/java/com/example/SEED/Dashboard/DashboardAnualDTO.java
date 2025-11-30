package com.example.SEED.Dashboard;

import java.math.BigDecimal;
import java.util.List;

public record DashboardAnualDTO(
        Integer ano,


        Long totalAlunosAno,        // Soma de todos os alunos de todos os meses
        BigDecimal gastoTotalAno,   // Soma do dinheiro gasto
        BigDecimal gastoPorAlunoAno,// Média anual
        BigDecimal gastoFolhaAno,   // Total gasto com RH


        List<DashboardMensalDTO> historico // Lista de Jan a Dez
) {}